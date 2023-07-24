package dev.santhu.elastic.model;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@Data
@Document(indexName = "#{@auditIndexName}")
public class AuditTrial{
    @Field(type = FieldType.Date, name = "@timestamp", format = DateFormat.basic_ordinal_date_time)
    private String timestamp;
    private AuditLog auditlog;

    public AuditTrial(AuditLog auditlog) {
        this.auditlog= auditlog;
        this.timestamp = ZonedDateTime.now().withZoneSameInstant(ZoneOffset.UTC).toString();
    }
}
