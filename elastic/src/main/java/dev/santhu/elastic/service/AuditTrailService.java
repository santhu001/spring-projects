package dev.santhu.elastic.service;

import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.aggregations.Buckets;
import co.elastic.clients.elasticsearch._types.aggregations.StringTermsBucket;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import dev.santhu.elastic.constants.FilterConstants;
import dev.santhu.elastic.model.AuditLog;
import dev.santhu.elastic.model.AuditTrial;
import dev.santhu.elastic.model.FilterItem;
import dev.santhu.elastic.model.GlobalViewFilterData;
import dev.santhu.elastic.repository.AuditTrailCustomRepository;
import dev.santhu.elastic.repository.AuditTrailRepository;
import dev.santhu.elastic.util.AuditTrailSpecifications;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ReactiveSearchHits;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Service
@Slf4j
public class AuditTrailService {
    private final AuditTrailRepository auditTrailRepository;
    private final AuditTrailCustomRepository auditTrailCustomRepository;
    @Autowired
    private AuditTrailSpecifications specifications;


    public AuditTrailService(AuditTrailRepository auditTrailRepository, AuditTrailCustomRepository auditTrailCustomRepository) {
        this.auditTrailRepository = auditTrailRepository;
        this.auditTrailCustomRepository = auditTrailCustomRepository;
    }
    public Flux<AuditTrial> findAuditTrialByBrandAndShopId(String brand, String shopId, Integer page, Integer size) {
        Pageable paging = PageRequest.of(page, size, Sort.by("auditlog.timestamp.keyword").descending());
        return auditTrailRepository.findByAuditlogShopIdAndAuditlogBrand(shopId, brand, paging);
    }

    public Mono<GlobalViewFilterData> getAllFieldsAggregationData() {
        Map<String, Aggregation> aggregationMap = specifications.getAggregations();
        NativeQueryBuilder queryBuilder = NativeQuery.builder();
        aggregationMap.forEach(queryBuilder::withAggregation);
        log.info("data received from elastic");
        return auditTrailCustomRepository.getAggregationData(queryBuilder.build(), AuditTrial.class)
                .map(entry -> {
                    GlobalViewFilterData globalViewFilterData = new GlobalViewFilterData();
                    globalViewFilterData.setCountry(getSTerms(entry, FilterConstants.FILTER_LABEL_COUNTRY));
                    globalViewFilterData.setCurrency(getSTerms(entry, FilterConstants.FILTER_LABEL_CURRENCY));
                    globalViewFilterData.setRegionCode(getSTerms(entry, FilterConstants.FILTER_LABEL_REGION_CODE));
                    globalViewFilterData.setRegionAreaCode(getSTerms(entry, FilterConstants.FILTER_LABEL_REGION_AREA_CODE));
                    return globalViewFilterData;
                });
    }
    public Mono<GlobalViewFilterData> getAggregationWithMatchPhrasePrefix(String searchValue) {
        NativeQuery query = NativeQuery.builder()
                .withAggregation(FilterConstants.FILTER_LABEL_SHOPID, Aggregation.of(builder ->  builder
                        .terms(terms -> terms
                                .field(FilterConstants.FILTER_SHOPID)
                                .size(10))
                        )
                )
                .withQuery(Query.of(builder -> builder
                        .matchPhrasePrefix(match -> match
                                .field(FilterConstants.FILTER_SHOP_ID)
                                .query(searchValue.toUpperCase()))
                        )
                )
                .build();
        return auditTrailCustomRepository.getAggregationData(query, AuditTrial.class)
                .map(entry -> {
                    GlobalViewFilterData globalViewFilterData = new GlobalViewFilterData();
                    globalViewFilterData.setShopId(getSTerms(entry, FilterConstants.FILTER_LABEL_SHOPID));
                    return globalViewFilterData;
                });
    }
    private static List<FilterItem> getSTerms(Map<String, org.springframework.data.elasticsearch.client.elc.Aggregation> entry, String key) {
        org.springframework.data.elasticsearch.client.elc.Aggregation aggregation =  entry.get(key);
        Buckets<StringTermsBucket> buckets = aggregation.getAggregate().sterms().buckets();
        return buckets.array().stream().map(strbkt -> new FilterItem(strbkt.key().stringValue(), false)).toList();
    }

    public Flux<AuditTrial> getDataByBrandIdAndShopId(String shopId, String brand , Integer page, Integer size) {
        Pageable paging = PageRequest.of(page, size, Sort.by("auditlog.timestamp.keyword").descending());
        NativeQuery nativeQuery = NativeQuery.builder()
                        .withQuery(Query.of(query -> query
                                .bool(bool -> bool
                                        .must(must -> must
                                                .match(MatchQuery.of(match ->  match
                                                        .query(brand)
                                                        .field(FilterConstants.FILTER_BRAND)
                                                        )
                                                )
                                        )
                                        .must(must -> must
                                                .match(MatchQuery.of(match ->  match
                                                        .query(shopId)
                                                        .field(FilterConstants.FILTER_SHOP_ID)
                                                        )
                                                )
                                        )
                        )))
                .withPageable(paging)
                .build();
        return auditTrailCustomRepository.search(nativeQuery, AuditTrial.class)
                .map(ReactiveSearchHits::getSearchHits)
                .flatMapMany(searchHit-> searchHit
                        .map(SearchHit::getContent));

    }
    @EventListener(ApplicationReadyEvent.class)
    public void storeData() {

        List<AuditTrial> auditTrials = IntStream.range(0, 50).mapToObj(i -> {
            AuditLog auditLog = new AuditLog();
            Faker faker = new Faker();
            auditLog.setTimestamp(faker.date().past(5, TimeUnit.DAYS).getTime());
            auditLog.setMessage(faker.text().text(50));
            auditLog.setBrand(faker.text().text(5, 10,true,false, false));
            auditLog.setShopId(faker.number().digits(4));
            auditLog.setTerminalId(faker.number().digits(2));
            auditLog.setCountry(faker.country().name());
            auditLog.setCurrency(faker.country().currency());
            auditLog.setRegionCode(faker.country().countryCode2());
            auditLog.setRegionAreaCode(faker.country().countryCode3());
            return new AuditTrial(auditLog);
        }).toList();
        auditTrailRepository.deleteAll()
                .thenMany(auditTrailRepository.saveAll(auditTrials))
                .subscribe(auditTrial -> log.info("inserted record: {}", auditTrial));
    }

}
