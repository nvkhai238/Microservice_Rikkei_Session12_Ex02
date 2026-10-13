# Session 12 - Bai 2: Tao don hang qua Kafka

Chay `docker compose up -d --wait`, sau do chay 3 terminal:

```bash
./gradlew :order-service:bootRun
./gradlew :inventory-service:bootRun
./gradlew :email-service:bootRun
```

Gui `POST http://localhost:8090/api/orders` voi JSON `{"productId":1,"quantity":2,"customerEmail":"buyer@example.com"}`. Order-Service luu don vao PostgreSQL, gui `OrderCreatedEvent` vao topic `order-events`, tra HTTP 202. Inventory-Service group `inventory-service` tru ton kho ID 1, Email-Service group `email-service` gui mail qua Mailpit tai http://localhost:18025. Hai consumer doc cung event doc lap. Ton kho mau la 100. Inventory xu ly event trung lap theo orderId.
