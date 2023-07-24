package dev.santhu.elastic.repository;

import dev.santhu.elastic.model.AuditTrial;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface AuditTrailRepository extends ReactiveElasticsearchRepository<AuditTrial, String> {
    Flux<AuditTrial> findByAuditlogShopIdAndAuditlogBrand(String shopId, String brand, Pageable paging);
}
