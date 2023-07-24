package dev.santhu.elastic.controller;

import co.elastic.clients.elasticsearch._types.aggregations.Buckets;
import co.elastic.clients.elasticsearch._types.aggregations.StringTermsBucket;
import dev.santhu.elastic.constants.FilterConstants;
import dev.santhu.elastic.model.AuditTrial;
import dev.santhu.elastic.model.FilterItem;
import dev.santhu.elastic.model.GlobalViewFilterData;
import dev.santhu.elastic.service.AuditTrailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.client.elc.Aggregation;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/audit")
@Slf4j
public class AuditTrailController {
    private final AuditTrailService auditTrailService;


    public AuditTrailController( AuditTrailService auditTrailService) {
        this.auditTrailService = auditTrailService;

    }

    @GetMapping("/view-data/{brand}/{shopId}")
    public Flux<AuditTrial> findByShopIdAndBrand(@PathVariable String brand,
                                                 @PathVariable String shopId,
                                                 @RequestHeader(defaultValue = "0") Integer page,
                                                 @RequestHeader(defaultValue = "50") Integer size) {
        return auditTrailService.findAuditTrialByBrandAndShopId(brand, shopId,page, size);

    }
    @GetMapping("/global-view")
    public Mono<GlobalViewFilterData> getViewData() {
       return  auditTrailService.getGlobalViewFilterData();
    }

    @GetMapping("/global-view/query")
    public Mono<GlobalViewFilterData> getShopIdDataWithPrefix(@RequestParam(name = "shopId") String shopId) {
        return  auditTrailService.getTypeheadSearchData(shopId);
    }

}
