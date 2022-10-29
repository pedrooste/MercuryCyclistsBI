package com.mercuryCyclists.businessIntelligence.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * SaleSummary data transfer object, used to represent the total return from sales relating to a product
 */
@AllArgsConstructor
@Getter
@Setter
public class SaleSummary {

    String product;
    double totalSalesPrice;
}
