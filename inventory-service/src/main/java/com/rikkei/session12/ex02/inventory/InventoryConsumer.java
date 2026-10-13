package com.rikkei.session12.ex02.inventory;

import jakarta.persistence.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@Service
class InventoryConsumer {
 private final InventoryRepository inventory;
 private final ProcessedRepository processed;
 private final ObjectMapper mapper;
 InventoryConsumer(InventoryRepository inventory,ProcessedRepository processed,ObjectMapper mapper) { this.inventory=inventory;this.processed=processed;this.mapper=mapper; }
 @KafkaListener(topics="order-events",groupId="inventory-service")
 @Transactional public void consume(String json) throws Exception {
  OrderCreatedEvent event=mapper.readValue(json,OrderCreatedEvent.class);
  if(processed.existsById(event.orderId())) return;
  InventoryProduct product=inventory.findById(event.productId()).orElseThrow(()->new IllegalStateException("Khong tim thay san pham"));
  if(product.stock<event.quantity()) throw new IllegalStateException("Ton kho khong du cho don "+event.orderId());
  product.stock-=event.quantity();processed.save(new ProcessedOrder(event.orderId()));
 }
}
