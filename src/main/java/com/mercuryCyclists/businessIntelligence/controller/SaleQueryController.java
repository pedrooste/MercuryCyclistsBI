package com.mercuryCyclists.businessIntelligence.controller;

import com.mercuryCyclists.businessIntelligence.dto.SaleSummary;
import com.mercuryCyclists.businessIntelligence.service.SaleInteractiveQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * Controller for Business Intelligence service
 */
@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/businessIntelligence")
public class SaleQueryController {

    SaleInteractiveQuery saleInteractiveQuery;

    @Autowired
    public SaleQueryController(SaleInteractiveQuery saleInteractiveQuery) {
        this.saleInteractiveQuery = saleInteractiveQuery;
    }

    @GetMapping("/summary")
    public Collection<SaleSummary> getSaleSummary() {
        return saleInteractiveQuery.getSaleSummary();
    }

    @GetMapping("/summary/{productName}")
    public SaleSummary getSaleSummary(@PathVariable("productName") String productName) {
        return saleInteractiveQuery.getProductSaleSummary(productName);
    }
}
