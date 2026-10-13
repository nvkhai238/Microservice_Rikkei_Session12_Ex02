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


@RestController
class InventoryController {
 private final InventoryRepository repository;
 InventoryController(InventoryRepository repository) { this.repository=repository; }
 @GetMapping("/api/inventory/{id}") StockView get(@PathVariable Long id) {
  InventoryProduct p=repository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
  return new StockView(id,p.stock);
 }
}
