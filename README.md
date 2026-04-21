# Incident Management System (IMS)

A CLI-based Incident Management System for support and development teams
to track, manage, and resolve incidents, inspired by real-world workflows
from tools like Jira and Salesforce.

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


### v1.0 - CLI core
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

## Getting Started

1. Clone the repository
2. Open with any Maven-compatible IDE
3. Maven will download dependencies automatically
4. Run `Main.java`

> No database configuration needed, SQLite is embedded.

## Roadmap

- **v1.0** ✅ | Released | CLI core - create, view, find, update, delete, close incidents
- **v2.0** ✅ | Released | SLA Monitor - track and alert on incident resolution deadlines
- **v3.0** 🔄 | Planned  | Reports - incident statistics and export
- **v4.0** 🚀 | Planned  | Spring Boot REST API - migrate to web API with PostgreSQL


## Planned Improvements

- **v2.1** Input Validation + Logging
- **v3.5** Comment System