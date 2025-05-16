# AWS SQS Demo Project

## Table of Contents
- [English](#english)
  - [Project Overview](#project-overview)
  - [Technologies Used](#technologies-used)
  - [Prerequisites](#prerequisites)
  - [Setup and Installation](#setup-and-installation)
  - [Project Structure](#project-structure)
  - [API Endpoints](#api-endpoints)
  - [SQS Queue Flow](#sqs-queue-flow)
  - [Testing](#testing)
- [Türkçe](#türkçe)
  - [Proje Genel Bakış](#proje-genel-bakış)
  - [Kullanılan Teknolojiler](#kullanılan-teknolojiler)
  - [Ön Gereksinimler](#ön-gereksinimler)
  - [Kurulum](#kurulum)
  - [Proje Yapısı](#proje-yapısı)
  - [API Uç Noktaları](#api-uç-noktaları)
  - [SQS Kuyruk Akışı](#sqs-kuyruk-akışı)
  - [Test Etme](#test-etme)

## English

### Project Overview
This project demonstrates the implementation of AWS SQS (Simple Queue Service) using Spring Boot and LocalStack for local development. It showcases a user registration system where user activation is handled asynchronously through SQS queues.

### Technologies Used
- Java 21
- Spring Boot 3.4.5
- Spring Cloud AWS 3.3.0
- PostgreSQL
- Docker & Docker Compose
- LocalStack (for AWS services simulation)
- Maven

### Prerequisites
- Java 21 or higher
- Docker and Docker Compose
- Maven
- PostgreSQL (if running without Docker)

### Setup and Installation
1. Clone the repository
2. Navigate to the project directory
3. Start the required services using Docker Compose:
   ```bash
   cd docker
   docker compose up -d
   ```
4. Initialize AWS SQS queues:
   ```bash
   ./aws-init.sh
   ```
5. Start the Spring Boot application:
   ```bash
   ./mvnw spring-boot:run
   ```

### Project Structure
```
src/
├── main/
│   ├── java/
│   │   └── org/pehlivan/mert/awssqs/
│   │       ├── aws/           # AWS SQS related classes
│   │       ├── controller/    # REST controllers
│   │       ├── dto/          # Data Transfer Objects
│   │       ├── exception/    # Custom exceptions
│   │       ├── model/        # Entity classes
│   │       ├── repository/   # JPA repositories
│   │       └── service/      # Business logic
│   └── resources/
│       └── application.yml   # Application configuration
```

### API Endpoints
1. Register User:
   ```bash
   POST /api/users/register
   {
       "email": "test@example.com",
       "username": "testuser",
       "password": "password123",
       "deadLetterQueueTest": false
   }
   ```

2. Get User by Email:
   ```bash
   GET /api/users?email=test@example.com
   ```

### SQS Queue Flow
1. User Registration:
   - User data is saved to the database
   - A message is sent to the SQS queue
   - User is initially inactive

2. Queue Processing:
   - SQS Listener receives the message
   - User is activated
   - Validation token is cleared

3. Dead Letter Queue:
   - Failed messages are moved to DLQ
   - DLQ listener processes failed messages
   - Maximum retry count: 3

### Testing
1. Test User Registration:
   ```bash
   curl -X POST http://localhost:8080/api/users/register \
   -H "Content-Type: application/json" \
   -d '{
       "email": "test@example.com",
       "username": "testuser",
       "password": "password123",
       "deadLetterQueueTest": false
   }'
   ```

2. Test Dead Letter Queue:
   ```bash
   curl -X POST http://localhost:8080/api/users/register \
   -H "Content-Type: application/json" \
   -d '{
       "email": "test@example.com",
       "username": "testuser",
       "password": "password123",
       "deadLetterQueueTest": true
   }'
   ```

## Türkçe

### Proje Genel Bakış
Bu proje, Spring Boot ve LocalStack kullanarak AWS SQS (Simple Queue Service) implementasyonunu göstermektedir. Kullanıcı kayıt sisteminde, kullanıcı aktivasyonu SQS kuyrukları aracılığıyla asenkron olarak gerçekleştirilmektedir.

### Kullanılan Teknolojiler
- Java 21
- Spring Boot 3.4.5
- Spring Cloud AWS 3.3.0
- PostgreSQL
- Docker & Docker Compose
- LocalStack (AWS servislerinin simülasyonu için)
- Maven

### Ön Gereksinimler
- Java 21 veya üstü
- Docker ve Docker Compose
- Maven
- PostgreSQL (Docker olmadan çalıştırılacaksa)

### Kurulum
1. Projeyi klonlayın
2. Proje dizinine gidin
3. Docker Compose ile gerekli servisleri başlatın:
   ```bash
   cd docker
   docker compose up -d
   ```
4. AWS SQS kuyruklarını başlatın:
   ```bash
   ./aws-init.sh
   ```
5. Spring Boot uygulamasını başlatın:
   ```bash
   ./mvnw spring-boot:run
   ```

### Proje Yapısı
```
src/
├── main/
│   ├── java/
│   │   └── org/pehlivan/mert/awssqs/
│   │       ├── aws/           # AWS SQS ile ilgili sınıflar
│   │       ├── controller/    # REST kontrolcüleri
│   │       ├── dto/          # Veri Transfer Nesneleri
│   │       ├── exception/    # Özel istisnalar
│   │       ├── model/        # Varlık sınıfları
│   │       ├── repository/   # JPA repository'leri
│   │       └── service/      # İş mantığı
│   └── resources/
│       └── application.yml   # Uygulama yapılandırması
```

### API Uç Noktaları
1. Kullanıcı Kaydı:
   ```bash
   POST /api/users/register
   {
       "email": "test@example.com",
       "username": "testuser",
       "password": "password123",
       "deadLetterQueueTest": false
   }
   ```

2. Email ile Kullanıcı Sorgulama:
   ```bash
   GET /api/users?email=test@example.com
   ```

### SQS Kuyruk Akışı
1. Kullanıcı Kaydı:
   - Kullanıcı verisi veritabanına kaydedilir
   - SQS kuyruğuna mesaj gönderilir
   - Kullanıcı başlangıçta pasif durumdadır

2. Kuyruk İşleme:
   - SQS Listener mesajı alır
   - Kullanıcı aktifleştirilir
   - Doğrulama token'ı temizlenir

3. Dead Letter Kuyruğu:
   - Başarısız mesajlar DLQ'ya taşınır
   - DLQ listener başarısız mesajları işler
   - Maksimum yeniden deneme sayısı: 3

### Test Etme
1. Kullanıcı Kaydı Testi:
   ```bash
   curl -X POST http://localhost:8080/api/users/register \
   -H "Content-Type: application/json" \
   -d '{
       "email": "test@example.com",
       "username": "testuser",
       "password": "password123",
       "deadLetterQueueTest": false
   }'
   ```

2. Dead Letter Kuyruğu Testi:
   ```bash
   curl -X POST http://localhost:8080/api/users/register \
   -H "Content-Type: application/json" \
   -d '{
       "email": "test@example.com",
       "username": "testuser",
       "password": "password123",
       "deadLetterQueueTest": true
   }'
   ``` 