package dev.santhu.elastic.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.client.elc.Aggregation;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.AggregationContainer;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ReactiveSearchHits;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class AuditTrailCustomRepository {
    private final ReactiveElasticsearchOperations elasticsearchOperations;

    public AuditTrailCustomRepository(ReactiveElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    public <T> Mono<Map<String, Aggregation>> getAggregationData(NativeQuery queryBuilder, Class<T> clazz) {
        return elasticsearchOperations
                .aggregate(queryBuilder, clazz)
                .map(AggregationContainer::aggregation)
                .filter(org.springframework.data.elasticsearch.client.elc.Aggregation.class::isInstance)
                .collect(Collectors.toMap(key-> ((Aggregation)key).getName(), Aggregation.class::cast));
    }
    public <T> Mono<ReactiveSearchHits<T>> search(NativeQuery query, Class<T> clazz) {
        return elasticsearchOperations.searchForHits(query, clazz);
    }
}
