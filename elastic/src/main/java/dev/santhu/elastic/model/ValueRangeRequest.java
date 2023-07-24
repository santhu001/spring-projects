package dev.santhu.elastic.model;

import lombok.Data;

@Data
public class ValueRangeRequest {
    private String minValue;
    private String maxValue;
}
