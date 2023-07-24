package dev.santhu.elastic.repository;

import dev.santhu.elastic.model.AuditTrial;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.client.elc.Aggregation;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.AggregationContainer;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
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

    public Mono<Map<String, Aggregation>> getAllFieldsAggregation(NativeQuery queryBuilder) {
        return elasticsearchOperations
                .aggregate(queryBuilder, AuditTrial.class)
                .map(AggregationContainer::aggregation)
                .filter(org.springframework.data.elasticsearch.client.elc.Aggregation.class::isInstance)
                .collect(Collectors.toMap(key-> ((Aggregation)key).getName(), Aggregation.class::cast));
    }

    public Mono<Map<String, Aggregation>> getTypeAheadData(NativeQuery query) {
        return elasticsearchOperations.aggregate(query, AuditTrial.class)
                .map(AggregationContainer::aggregation)
                .filter(org.springframework.data.elasticsearch.client.elc.Aggregation.class::isInstance)
                .collect(Collectors.toMap(key-> ((Aggregation)key).getName(), Aggregation.class::cast));
    }
}
