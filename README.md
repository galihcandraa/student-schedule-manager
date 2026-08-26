# Personal Planner API

A Spring Boot-based REST API designed to manage personal schedules such as university coursework, organization tasks, work routines, and personal activities in a unified system, featuring automated time-conflict detection.

This project evolved from an initial CLI-based implementation in earlier commits into a modular REST API ready for React frontend integration.

---

## Features

- **Schedule CRUD:** Create, read, update, and delete schedules.
- **Schedule Categories:** Coursework, Organization, Work, and Personal.
- **Schedule Frequency:** Always, Once, or Custom Date Range.
- **Automated Conflict Detection:** Prevents conflicting schedules based on day and time.
- **Search Capabilities:** Search records by ID, category, title, or day.
- **Data Sorting:** Sort schedules by category, title, day, time, or start date.

---

## Tech Stack

- **Backend:** Java 25, Spring Boot 4 (Spring Web, Spring Data JPA, Validation)
- **Database:** MySQL
- **Testing:** JUnit 5, Mockito
- **Build Tool:** Maven

---

## Getting Started Locally

### Prerequisites

- JDK 25
- MySQL Server running

### Database Configuration

Configured via Environment Variables with local fallbacks:

| Variable | Default | Description |
| --- | --- | --- |
| `DB_HOST` | `localhost` | MySQL host |
| `DB_PORT` | `3306` | MySQL port |
| `DB_NAME` | `personal_planner_db` | Target database name |
| `DB_USERNAME` | `root` | MySQL user |
| `DB_PASSWORD` | `(empty)` | MySQL password |

### Running the Application

```bash
# Clone the repository
git clone https://github.com/galihcandraa/personal-planner.git
cd personal-planner

# Start the server
./mvnw spring-boot:run
```

The server will run on `http://localhost:8080`.

### Running Tests

```bash
./mvnw test
```

---

## API Endpoints

| Method | Endpoint | Description |
| --- | --- | --- |
| `GET` | `/api/jadwal` | Fetch all schedules |
| `GET` | `/api/jadwal/{id}` | Fetch schedule by ID |
| `POST` | `/api/jadwal` | Create a new schedule |
| `PUT` | `/api/jadwal/{id}` | Update an existing schedule |
| `DELETE` | `/api/jadwal/{id}` | Delete schedule by ID |
| `DELETE` | `/api/jadwal/reset` | Delete all schedules |
| `GET` | `/api/jadwal/search?type={type}&value={val}` | Search records (`ID`, `KATEGORI`, `JUDUL`, `HARI`) |
| `GET` | `/api/jadwal/sort?type={type}&order={order}` | Sort records by criteria (`ASC`/`DESC`) |

---

## Project Structure

```text
src/
├── main/java/com/github/galihcandraa/personal_planner/
│   ├── controller/   # REST Controllers
│   ├── dto/          # Data Transfer Objects & Request Payloads
│   ├── exception/    # Global Exception Handlers
│   ├── model/        # JPA Entities & Enums
│   ├── repository/   # Spring Data JPA Repositories
│   ├── service/      # Business Logic & Validation Engine
│   └── util/         # Date-time Formatting Utilities
└── test/java/        # Unit & Slice Tests (JUnit 5 + Mockito)

```

---

## Next Milestones

- [ ] Configure CORS (Cross-Origin Resource Sharing)
- [ ] Build and integrate React UI client

---

## Version History

The initial CLI prototype can be reviewed via the [Initial Repository Commits](https://www.google.com/search?q=https://github.com/galihcandraa/personal-planner/commits/main).

---

## Author

- GitHub: [@galihcandraa](https://www.google.com/search?q=https://github.com/galihcandraa)
- Repository: [personal-planner](https://www.google.com/search?q=https://github.com/galihcandraa/personal-planner)
