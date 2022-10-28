package com.mercuryCyclists.businessIntelligence.service;

import com.mercuryCyclists.businessIntelligence.dto.SaleSummary;
import com.mercuryCyclists.businessIntelligence.entity.SaleEvent;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SaleInteractiveQuery {

    private final InteractiveQueryService interactiveQueryService;

    @Autowired
    public SaleInteractiveQuery(InteractiveQueryService interactiveQueryService){
        this.interactiveQueryService = interactiveQueryService;
    }

    /**
     * Iterates through all sale events, calculating the total price of all units sold
     */
    public Collection<SaleSummary> getSaleSummary() {
        Map<String, SaleSummary> saleList = new HashMap<>();
        KeyValueIterator<String, SaleEvent> sales = getSales().all();

        while (sales.hasNext()) {
            SaleEvent saleEvent = sales.next().value;
            String product = saleEvent.getProductName();
            double quanity = saleEvent.getQuantity();
            double price = saleEvent.getPrice();
            double totalSales = quanity*price;

            if (saleList.get(product) != null){
                SaleSummary saleSummary = saleList.get(product);
                saleSummary.setTotalSalesPrice(saleSummary.getTotalSalesPrice() + totalSales);
            }
            else{
                saleList.put(product, new SaleSummary(product, totalSales));
            }

        }

        return saleList.values();
    }

    /**
     * Returns Store KTable
     */
    private ReadOnlyKeyValueStore<String, SaleEvent> getSales() {
        return this.interactiveQueryService.getQueryableStore(SaleStreamProcessing.SALE_STATE_STORE, QueryableStoreTypes.keyValueStore());
    }
}
