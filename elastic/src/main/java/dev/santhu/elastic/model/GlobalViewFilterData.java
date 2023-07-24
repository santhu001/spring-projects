package dev.santhu.elastic.model;

import lombok.Data;

import java.util.List;

@Data
public class GlobalViewFilterData {
    private List<FilterItem> brand;
    private List<FilterItem> country;
    private List<FilterItem> regionCode;
    private List<FilterItem> regionAreaCode;
    private List<FilterItem> transactionType;
    private List<FilterItem> status;
    private List<FilterItem> shopId;
    private List<FilterItem> userName;
    private List<FilterItem> currency;
    private FilterItem terminalId;
    private ValueRangeResponse amountRange;
    private ValueRangeResponse dateRange;
    // id is betslip/vt id
    private List<FilterItem> id;
}
