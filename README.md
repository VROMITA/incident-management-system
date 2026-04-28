# Incident Management System (IMS)

![Java](https://img.shields.io/badge/Java-21_LTS-orange?logo=openjdk)
![Maven](https://img.shields.io/badge/Maven-3.9-blue?logo=apachemaven)
![Release](https://img.shields.io/github/v/release/VROMITA/incident-management-system)
![License](https://img.shields.io/badge/License-MIT-green)

# 🎫 Incident Management System - CLI Version

> **Final Version: v3.1**  
> A command-line incident management system built to master Java fundamentals before frameworks.

---

## 🎯 Project Purpose

This project was built to deeply understand Java core concepts **without framework magic**:
- Manual JDBC connection management (no Spring Data JPA)
- Database schema design and SQL
- Enum behavioral patterns
- Business logic implementation
- Try-with-resources and proper resource handling

**Next evolution:** Professional REST API version with Spring Boot (separate repository)

---

## ✨ Features

### Core Functionality
- ✅ **CRUD Operations** - Create, Read, Update, Delete incidents
- ✅ **Priority System** - CRITICAL (4h) / HIGH (12h) / MEDIUM (24h) / LOW (48h) SLA
- ✅ **Status Lifecycle** - NEW → ASSIGNED → INVESTIGATING → IN_PROGRESS → CLOSED
- ✅ **Source Tracking** - USER_REPORT vs INTERNAL_TEAM
- ✅ **SLA Monitoring** - Auto-calculation with at-risk detection

### Advanced Features  
- 📊 **Reporting System** - Incidents by status/priority with Stream API
- 🔍 **Dynamic Menus** - Enum-driven UI with `Enum.values()`
- ⚡ **Performance** - EnumMap optimization for enum-keyed collections
- ✅ **Validation** - Input validation with proper error handling
- 📝 **Logging** - java.util.logging integration
- 📚 **Documentation** - Full Javadoc coverage

---

## 🏗️ Architecture

┌──────────────────────────────────────┐
│  CLI Layer (IncidentCLI, ReportCLI)  │  ← User interaction
├──────────────────────────────────────┤
│  Service Layer (IncidentService)     │  ← Business logic
├──────────────────────────────────────┤
│  Repository Layer (IncidentRepo)     │  ← Data access
├──────────────────────────────────────┤
│  Database (SQLite)                   │  ← Persistence
└──────────────────────────────────────┘

Main

**Packages:**
- `model` - Domain entities (Incident, Priority, IncidentStatus, IncidentSource)
- `service` - Business logic (IncidentService, SlaMonitor)
- `repository` - Database operations (IncidentRepository)
- `cli` - User interface (IncidentCLI, ReportCLI)
  
---

## 🚀 Quick Start

### Prerequisites
- Java 21 or higher
- Maven 3.8+

### Installation

1. Clone the repository:
```bash
git clone https://github.com/VROMITA/incident-management-system.git
cd incident-management-system
```

2. Build the project:
```bash
mvn clean install
```

3. Run the application:
```bash
mvn exec:java
```

The database (`incidents.db`) is created automatically on first run.

---

## 📚 What I Learned

### JDBC & Database
- `Connection`, `PreparedStatement`, `ResultSet` lifecycle
- SQL injection prevention with parameterized queries
- Try-with-resources for automatic resource cleanup
- Transaction management concepts
- Connection leak detection and prevention

### Java Core
- Enum behavioral patterns (methods and properties in enums)
- `Optional<T>` for null safety
- Stream API (`groupingBy`, `Collectors`)
- `EnumMap` for type-safe enum-keyed maps
- Method references (`Priority::getSlaHours`)

### Software Engineering
- Single Responsibility Principle (SRP)
- Separation of concerns (layers)
- Git branching strategy (master/develop/feature)
- Semantic versioning
- Professional commit messages

### Real-World Context
Based on enterprise support experience with:
- Jira incident workflows
- Salesforce case management  
- L1/L2/L3 support escalation
- SLA monitoring and alerting

---

## 📊 Project Evolution

| Version | Focus | Status |
|---------|-------|--------|
| v1.0 | CLI + JDBC CRUD | ✅ Released |
| v2.0 | SLA Monitoring | ✅ Released |
| v2.1 | Validation + Logging + Javadoc | ✅ Released |
| v3.0 | Reporting System | ✅ Released |
| v3.1 | Refactoring + Stream API | ✅ Released (Final) |

---

## 🔜 Next Steps

This CLI version is **complete and production-ready** for its scope.

**Next evolution:** Professional REST API built with:
- Spring Boot
- PostgreSQL  
- JPA/Hibernate
- REST endpoints
- Docker deployment

Repository: _(will be linked when created)_

---

## 👨‍💻 Author

**Valerio Romita**  
Building Java expertise from fundamentals to enterprise frameworks.

[GitHub](https://github.com/VROMITA) | [LinkedIn](https://www.linkedin.com/in/valerio-romita)

---

## 📄 License

This project is for portfolio and educational purposes.

---

**Built with ☕ and determination to understand Java deeply before using frameworks.**
