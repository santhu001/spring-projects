package dev.santhu.elastic.repository;

import dev.santhu.elastic.model.Customer;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;

public interface CustomerRepository extends ReactiveElasticsearchRepository<Customer, String> {
}
