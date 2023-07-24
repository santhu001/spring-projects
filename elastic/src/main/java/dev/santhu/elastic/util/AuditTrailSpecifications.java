package dev.santhu.elastic.util;

import co.elastic.clients.elasticsearch._types.aggregations.Aggregate;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import dev.santhu.elastic.constants.FilterConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class AuditTrailSpecifications {
    public Map<String, Aggregation> getAggregations() {
        log.info("building aggregation filters starts");
        int aggSize = 50;
        Map<String, Aggregation> aggregationMap = new HashMap<>();
        aggregationMap.put(FilterConstants.FILTER_LABEL_TRANSACTION_TYPE, Aggregation.of(builder -> builder
                .terms(terms-> terms
                        .field(FilterConstants.FILTER_TRANSACTION_TYPE)
                        .size(aggSize))));
        aggregationMap.put(FilterConstants.FILTER_LABEL_STATUS, Aggregation.of(builder -> builder
                .terms(terms-> terms
                        .field(FilterConstants.FILTER_STATUS)
                        .size(aggSize))));
//        aggregationMap.put(FilterConstants.FILTER_LABEL_MIN_DATE, Aggregation.of(builder -> builder
//                .min(min-> min.field(FilterConstants.FILTER_TIME_STAMP))));
//        aggregationMap.put(FilterConstants.FILTER_LABEL_MAX_DATE, Aggregation.of(builder -> builder
//                .max(max-> max.field(FilterConstants.FILTER_TIME_STAMP))));
        aggregationMap.put(FilterConstants.FILTER_LABEL_MIN_AMOUNT, Aggregation.of(builder -> builder
                .min(min-> min.field(FilterConstants.FILTER_AMOUNT))));
        aggregationMap.put(FilterConstants.FILTER_LABEL_MAX_AMOUNT, Aggregation.of(builder -> builder
                .max(max-> max.field(FilterConstants.FILTER_AMOUNT))));
        aggregationMap.put(FilterConstants.FILTER_LABEL_BRAND, Aggregation.of(builder -> builder.terms(terms-> terms
                .field(FilterConstants.FILTER_BRAND)
                .size(aggSize))));
        aggregationMap.put(FilterConstants.FILTER_LABEL_REGION_CODE, Aggregation.of(builder -> builder.terms(terms-> terms
                .field(FilterConstants.FILTER_REGION_CODE)
                .size(aggSize))));
        aggregationMap.put(FilterConstants.FILTER_LABEL_REGION_AREA_CODE, Aggregation.of(builder -> builder.terms(terms-> terms
                .field(FilterConstants.FILTER_REGION_AREA_CODE)
                .size(aggSize))));
        aggregationMap.put(FilterConstants.FILTER_LABEL_COUNTRY, Aggregation.of(builder -> builder.terms(terms-> terms
                .field(FilterConstants.FILTER_COUNTRY)
                .size(aggSize))));

        aggregationMap.put(FilterConstants.FILTER_LABEL_SHOPID, Aggregation.of(builder -> builder.terms(terms-> terms
                .field(FilterConstants.FILTER_SHOPID)
                .size(aggSize))));
        aggregationMap.put(FilterConstants.FILTER_LABEL_USER_NAME, Aggregation.of(builder -> builder.terms(terms-> terms
                .field(FilterConstants.FILTER_USER_NAME)
                .size(aggSize))));
        aggregationMap.put(FilterConstants.FILTER_LABEL_TERMINALID, Aggregation.of(builder -> builder.terms(terms-> terms
                .field(FilterConstants.FILTER_TERMINALID)
                .size(aggSize))));
        aggregationMap.put(FilterConstants.FILTER_LABEL_CURRENCY, Aggregation.of(builder -> builder.terms(terms-> terms
                .field(FilterConstants.FILTER_CURRENCY)
                .size(aggSize))));
        return aggregationMap;

    }
}
