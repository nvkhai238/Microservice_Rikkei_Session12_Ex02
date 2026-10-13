package com.rikkei.session12.ex02.order;

import java.util.UUID;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


@Entity
@Table(name="orders")
class OrderEntity {
 @Id String id;
 @Column(nullable=false) Long productId;
 @Column(nullable=false) int quantity;
 @Column(nullable=false) String customerEmail;
 protected OrderEntity() {}
 OrderEntity(String id,OrderRequest r) { this.id=id;productId=r.productId();quantity=r.quantity();customerEmail=r.customerEmail(); }
}
