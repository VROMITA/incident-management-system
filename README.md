# Incident Management System (IMS)

![Java](https://img.shields.io/badge/Java-21_LTS-orange?logo=openjdk)
![Maven](https://img.shields.io/badge/Maven-3.9-blue?logo=apachemaven)
![Release](https://img.shields.io/github/v/release/VROMITA/incident-management-system)
![License](https://img.shields.io/badge/License-MIT-green)

A Java Incident Management System that translates years of enterprise
support experience into production-grade code. Built in progressive
milestones — mastering JDBC and layered architecture fundamentals
before abstracting them with Spring Boot.

## Background

Built by a professional with 4+ years of hands-on L1/L2/L3 support
experience in an enterprise IoT environment, using Jira and Salesforce
daily. The domain model and workflows (SLA monitoring, incident
escalation, closing comments) come from real support processes, not
textbook examples.

## Technologies

- Java 21
- SQLite
- JDBC
- Maven
- Git & GitHub

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
- Close an incident with validation

### v2.0 - SLA Monitor
- Automatic SLA deadline calculation based on priority
- Real-time SLA classification (OK, AT RISK, BREACH)
- SLA status report from CLI

### v2.1 - Validation + Logging
- Input validation on all numeric inputs (NumberFormatException handling)
- Empty title validation with loop in create and update
- Operational logging via java.util.logging (INFO, WARNING)
- Javadoc on all public methods (Service, Repository, SlaMonitor)

### v3.0 - Reporting
- Count incidents by Status
- Count incidents by Priority
- Average resolution time calculation
- Incidents by date range with input validation
- Business logic validation (start date cannot be after end date)

## Getting Started

1. Clone the repository
2. Open with any Maven-compatible IDE
3. Maven will download dependencies automatically
4. Run `Main.java`

> No database configuration needed, SQLite is embedded.

## Roadmap

- **v1.0** ✅ | Released | CLI core
- **v2.0** ✅ | Released | SLA Monitor
- **v2.1** ✅ | Released | Validation + Logging + Javadoc
- **v3.0** ✅ | Released | Reporting - incident statistics + date range + validation
- **v3.1** 🔄 | Planned  | SRP Refactor + Code Quality
- **v3.5** 💬 | Planned  | Comment System
- **v4.0** 🚀 | Planned  | Spring Boot REST API + PostgreSQL