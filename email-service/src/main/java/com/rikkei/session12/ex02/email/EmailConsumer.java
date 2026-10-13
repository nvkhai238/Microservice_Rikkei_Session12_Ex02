package com.rikkei.session12.ex02.email;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
class EmailConsumer {
 private final ObjectMapper mapper;
 private final JavaMailSender mail;
 EmailConsumer(ObjectMapper mapper,JavaMailSender mail) { this.mapper=mapper;this.mail=mail; }
 @KafkaListener(topics="order-events",groupId="email-service")
 public void consume(String json) throws Exception {
  OrderCreatedEvent event=mapper.readValue(json,OrderCreatedEvent.class);
  SimpleMailMessage message=new SimpleMailMessage();
  message.setFrom("shop@example.com");message.setTo(event.customerEmail());
  message.setSubject("Don hang "+event.orderId());
  message.setText("Da tiep nhan don hang gom "+event.quantity()+" san pham.");
  mail.send(message);
 }
}
