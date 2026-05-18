# spring-data-pattern

A reusable Spring Boot library that centralizes database connectivity, HikariCP pool configuration, JPA setup, and transaction management.

## Overview

Part of the **MDS Shared Core** ecosystem (`com.mds`). This library standardizes how microservices configure and interact with relational databases, reducing duplicated setup and promoting consistency.

---

## Features

- **HikariCP DataSource** — configurable connection pool with min/max size, timeouts, and leak detection
- **JPA / Hibernate** — entity manager factory with naming strategies, DDL-auto, and SQL formatting
- **Transaction Management** — `PlatformTransactionManager` bean auto-configured
- **Secret Extraction** — `SecretExtractorHelper` reads credentials from file paths or plain text
- **Externalized Configuration** — all settings bound via `database.config.properties.*`

---

## Installation

```xml
<dependency>
    <groupId>com.mds</groupId>
    <artifactId>spring-data-pattern</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

---

## Configuration Example

```yaml
database:
  config:
    base: com.example.repository
    properties:
      url: jdbc:oracle:thin:@localhost:1521/XEPDB1
      username: /run/secrets/db-user   # file path or plain value
      password: /run/secrets/db-pass
      driver-class-name: oracle.jdbc.OracleDriver
      schema: MY_SCHEMA
      pool-name: app-pool
      min-pool-size: 2
      max-pool-size: 10
      max-lifetime: 1800000
      validation-timeout: 5000
      connection-timeout: 30000
      idle-timeout: 600000
      leak-detection-threshold: 0
      show-sql: false
      format-sql: true
      ddl-auto: none
      packages-to-scan:
        - com.example.entity
```

---

## Project Structure

```text
com.mds.data
├── DataAutoConfiguration         — auto-config entry point
├── configuration/
│   └── DatabaseConfiguration     — DataSource, EntityManagerFactory, TransactionManager beans
├── helper/
│   └── SecretExtractorHelper     — secret file/plain-text resolver
└── properties/
    └── DatabaseProperties        — @ConfigurationProperties binding
```

---

## Author

Martins Desenvolvimento de Sistemas (MDS)
