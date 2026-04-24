# Incident Management System (IMS)

![Java](https://img.shields.io/badge/Java-21_LTS-orange?logo=openjdk)
![Maven](https://img.shields.io/badge/Maven-3.9-blue?logo=apachemaven)
![Release](https://img.shields.io/github/v/release/VROMITA/incident-management-system)
![License](https://img.shields.io/badge/License-MIT-green)

A Java Incident Management System that translates years of enterprise support experience into code.
Built in progressive milestones, mastering JDBC and layered architecture fundamentals
before abstracting them with Spring Boot.

## Background

Built by a professional with multiple years of hands-on L1/L2/L3 support
experience in an enterprise IoT environment, using Jira and Salesforce
daily. The domain model and workflows (SLA monitoring, incident
escalation, closing comments) come from real support processes, not
textbook examples.

## Technologies

- **Java 21 LTS** — Core language
- **Maven 3.9** — Build automation and dependency management
- **SQLite via JDBC** — Embedded database (manual connection management)

## Architecture

The project follows a layered architecture:

- **Model** - domain objects (Incident, enums)
- **Repository** - database layer, handles all SQLite operations via JDBC
- **Service** - business logic and validation rules
- **CLI** - interactive command-line interface for user interaction

## Features

### v1.0 - CLI Core
- Create a new incident
- View all incidents
- Find an incident by ID
- Delete an incident
- Update an incident (title, description, status, priority, source, assigned to)
- Close an incident with business validation
- Persistent storage via SQLite (embedded, zero configuration)

### v2.0 - SLA Monitor
- Automatic SLA deadline calculation based on priority
- SLA status classification on incident retrieval (OK / AT RISK / BREACH)
- SLA status report from CLI

### v2.1 - Validation + Logging
- Input validation on all numeric inputs (NumberFormatException handling)
- Empty title validation with loop in create and update
- Operational logging via java.util.logging (INFO, WARNING)
- Javadoc on all public methods (Service, Repository, SlaMonitor)

### v3.0 - Reporting
- Incident volume breakdown by status and priority
- Average resolution time calculation
- Incidents by date range with input validation
- Business logic validation (start date cannot be after end date)

## Getting Started

### Prerequisites
- Java 21 or higher
- Maven 3.9+

### Run
```bash
mvn clean compile exec:java
```

> No database configuration needed, SQLite is embedded.

## Roadmap

- ✅ **v1.0** — CLI Core
- ✅ **v2.0** — SLA Monitoring
- ✅ **v2.1** — Validation + Logging + Javadoc
- ✅ **v3.0** — Reporting - incident statistics, date range filters, validation
- 🔄 **v3.1** — Code Quality Refactor - apply Single Responsibility Principle across service and repository layers
- 💬 **v3.5** — Comment System
- 🚀 **v4.0** — Spring Boot REST API + PostgreSQL

## Architecture

Layered architecture with clear separation of concerns. The CLI is a temporary presentation layer, in v4.0 it will be replaced by REST endpoints without touching the service or repository layers.

## About

Built by **Valerio Romita** — Application Support Specialist transitioning to Java Backend Development.

💼 [LinkedIn](https://www.linkedin.com/in/valerio-romita)