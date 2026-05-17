# spring-data-pattern

A reusable Spring Boot library that centralizes database connectivity, datasource configuration, JPA setup, migrations, and persistence standards across applications.

## Overview

This library standardizes how applications configure and interact with relational databases by providing reusable datasource and persistence configurations.

It reduces duplicated setup and promotes consistency across services.

---

## Features

- Datasource configuration
- Multi-database support
- JPA configuration
- Hibernate settings
- HikariCP pool configuration
- Flyway integration
- Liquibase support
- Audit configuration
- Transaction management
- Database health checks

---

## Supported Databases

- Oracle
- PostgreSQL
- MySQL
- SQL Server
- H2

---

## Example Configuration

application.yml

spring:

 datasource:
   url: jdbc:oracle:thin:@localhost:1521/XEPDB1
   username: admin
   password: password

 jpa:
   hibernate:
      ddl-auto: none

---

## Project Structure

src/
├── datasource/
├── configuration/
├── migration/
├── health/
├── audit/
└── transaction/

---

## Architecture

Application
↓
spring-data-pattern
↓
Datasource Configuration
↓
Database

---

## Future Features

- Read/Write database split
- Dynamic datasource
- Tenant-aware datasource
- Database sharding
