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


@Service
class OrderService {
 private final OrderWriter writer;
 private final KafkaTemplate<String,String> kafka;
 private final ObjectMapper mapper;
 OrderService(OrderWriter writer,KafkaTemplate<String,String> kafka,ObjectMapper mapper) { this.writer=writer;this.kafka=kafka;this.mapper=mapper; }
 OrderResponse create(OrderRequest r) throws Exception {
  OrderCreatedEvent event=writer.save(r);
  kafka.send("order-events",event.orderId(),mapper.writeValueAsString(event))
   .whenComplete((result,error)->{ if(error!=null) System.err.println("Kafka publish failed for "+event.orderId()+": "+error); });
  return new OrderResponse(event.orderId(),"ACCEPTED");
 }
}
