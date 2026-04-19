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

- Create a new incident
- View all incidents
- Find an incident by ID
- Delete an incident
- Update an incident (title, description, status, priority, source, assigned to)
- Close an incident with validation

## Getting Started

1. Clone the repository
2. Open with any Maven-compatible IDE
3. Maven will download dependencies automatically
4. Run `Main.java`

> No database configuration needed, SQLite is embedded.

## Roadmap

- **v1.0** ✅ CLI core - create, view, find, update, delete, close incidents
- **v2.0** 🔄 SLA Monitor - track and alert on incident resolution deadlines + comments system
- **v3.0** 📊 Reports - incident statistics and export
- **v4.0** 🚀 Spring Boot REST API - migrate to web API with PostgreSQL