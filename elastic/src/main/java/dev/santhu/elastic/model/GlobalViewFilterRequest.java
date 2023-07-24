package dev.santhu.elastic.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GlobalViewFilterRequest {
    private List<String> brand = new ArrayList<>();
    private List<String> country= new ArrayList<>();
    private List<String> regionCode= new ArrayList<>();
    private List<String> regionAreaCode= new ArrayList<>();
    private List<String> transactionType= new ArrayList<>();
    private List<String> status= new ArrayList<>();
    private List<String> shopId= new ArrayList<>();
    private List<String> userName= new ArrayList<>();
    private String terminalId;
    private ValueRangeRequest amountRange;
    private ValueRangeRequest dateRange;
    // id is betslip/vt id
    private List<String> id= new ArrayList<>();
    private List<String> currency= new ArrayList<>();
}
