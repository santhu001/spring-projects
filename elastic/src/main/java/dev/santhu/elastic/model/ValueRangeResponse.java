package dev.santhu.elastic.model;

import lombok.Data;

@Data
public class ValueRangeResponse {
    private String minValue;
    private String maxValue;
    private Boolean selected=Boolean.FALSE;
}
