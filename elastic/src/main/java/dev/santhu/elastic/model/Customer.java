package dev.santhu.elastic.model;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "customer")
public record Customer(String name,
                       String id,
                       Integer employeeId,
                       String designation,
                       @Field(name = "@timestamp", type = FieldType.Date) long timestamp)
{
}
