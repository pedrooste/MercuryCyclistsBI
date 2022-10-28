package com.mercuryCyclists.businessIntelligence.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Create test data using REST endpoints for Product and Sales MS
 */
@Service
public class SaleStreamGenerator {

    private static final String POSTPRODUCT = "http://localhost:8081/api/v1/product";
    private static final String POSTBACKORDER = "http://localhost:8082/api/v1/online-sale/backorder";
    private static final RestTemplate restTemplate = new RestTemplate();
    private static final JsonParser jsonParser = new JsonParser();

    /**
     * Creates test product
     */
    public Long createTestProduct(String name, Long price, String comment, Long quantity){
        JsonObject product = new JsonObject();
        product.addProperty("name", name);
        product.addProperty("comment", comment);
        product.addProperty("price", price);
        product.addProperty("quantity", quantity);

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>(product.toString(), headers);
            String resp = restTemplate.postForObject(POSTPRODUCT, entity, String.class);

            JsonObject productResp = jsonParser.parse(resp).getAsJsonObject();
            return productResp.get("id").getAsLong();
        } catch(Exception exception) {
            throw new IllegalArgumentException(String.format("Failed to create product with name: %d, exception: %s", product.get("name").getAsLong(), exception));
        }
    }

    /**
     * Creates test backorder
     */
    public void createTestBackorder(String customerName, String address, Long product, Long quantity){
        JsonObject backorder = new JsonObject();
        backorder.addProperty("customerName", customerName);
        backorder.addProperty("address", address);
        backorder.addProperty("productId", product);
        backorder.addProperty("quantity", quantity);

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>(backorder.toString(), headers);
            restTemplate.postForObject(POSTBACKORDER, entity, String.class);
        } catch(Exception exception) {
            throw new IllegalArgumentException(String.format("Failed to create sale with productId: %d, exception: %s", backorder.get("productId").getAsLong(), exception));
        }
    }
}
