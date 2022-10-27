package com.mercuryCyclists.businessIntelligence.service;

import com.mercuryCyclists.businessIntelligence.entity.SaleEvent;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Configuration
public class SaleStreamProcessing {

    public final static String SALE_STATE_STORE = "sale-store";

    @Bean
    public Consumer<KStream<?, SaleEvent>> process() {
        return inputStream -> {

            inputStream.map((k, v) -> {
                String productName = v.getProductName();
                String quantity = v.getQuantity().toString();
                String price = v.getPrice().toString();
                String newKey = productName + "_" + quantity + "_" + price;
                return KeyValue.pair(newKey, v);
            }).toTable(
                    Materialized.<String, SaleEvent, KeyValueStore<Bytes, byte[]>>as(SALE_STATE_STORE)
                            .withKeySerde(Serdes.String())
                            .withValueSerde(saleEventSerde())
            );

            inputStream.print(Printed.toSysOut());
        };
    }

    public Serde<SaleEvent> saleEventSerde() {
        final JsonSerde<SaleEvent> saleEventJsonSerde = new JsonSerde<>();
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.mercuryCyclists.businessIntelligence.entity.SaleEvent");
        configProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        saleEventJsonSerde.configure(configProps, false);
        return saleEventJsonSerde;
    }
}
