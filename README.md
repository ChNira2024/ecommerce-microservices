# 🚀 E-Commerce Microservices (Kafka Event-Driven)

## 📌 Overview

This project is a **Spring Boot-based E-Commerce Microservices system** designed using an **event-driven architecture with Apache Kafka**.
Each service operates independently and communicates asynchronously, ensuring **scalability, loose coupling, and fault tolerance**.

---

## 🏗️ Architecture Flow

```
Product → Inventory → Payment → Order → Notification
```

### 🔄 Workflow

* Product created → sent to Kafka
* Inventory updates stock
* Order placed → sent to Kafka
* Inventory validates stock
* Payment processed (success/failure)
* Order updated accordingly
* Notification sent to user

---

## 🧩 Services

* **Product Service** → Manage products, publish product events
* **Inventory Service** → Manage stock, validate orders
* **Order Service** → Create orders, update status
* **Payment Service** → Handle payment processing
* **Notification Service** → Send success/failure alerts

---

## 📡 Kafka Topics

| Topic                                | Producer  | Consumer            |
| ------------------------------------ | --------- | ------------------- |
| product-topic-microservices-producer | Product   | Inventory           |
| order-topic-microservices-producer   | Order     | Inventory           |
| inventory-success                    | Inventory | Payment             |
| inventory-failed                     | Inventory | Payment             |
| payment-success                      | Payment   | Order, Notification |
| payment-failed                       | Payment   | Order, Notification |

---

## ⚙️ Tech Stack

* Java 17
* Spring Boot
* Spring Cloud (Feign Client)
* Apache Kafka
* JPA / Hibernate
* MySQL / PostgreSQL
* Resilience4j
* SLF4J Logging

---

## ▶️ Run Instructions

### 1. Start Kafka

```
zookeeper-server-start.bat config\zookeeper.properties
kafka-server-start.bat config\server.properties
```

### 2. Start Services

```
Product → Inventory → Order → Payment → Notification
```

---

## 🧪 API Testing

* Create Product → `POST /products`
* Place Order → `POST /orders`

---

## 🧠 Key Highlights

* Event-driven microservices architecture
* Asynchronous communication using Kafka
* Independent service deployment
* Circuit breaker for resilience
* Centralized logging and exception handling

---

## 🚨 Notes

* Each Kafka topic handles a single event type
* Avoid using global `value.default.type`
* Use separate consumers for different events
* Clear Kafka topics when schema changes

---

## 👨‍💻 Author

**Niranjana Charty**
