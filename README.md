# iMail: Email App Backend

Backend for an email client app, built for a FIAP academic challenge in partnership with **Locaweb**. The full solution is a two-app system: an iOS client written in Swift (not in this repo) and this Java/Spring Boot API, which handles users, inbox, labels, and spam control.

## The Challenge

> The challenge is to plug the email app into a robust back-end solution. This phase of the project focuses on integrating an intuitive, efficient, and customizable user interface with a back-end architecture capable of supporting advanced user settings, spam control, and email service simulation.

**Evaluation criteria:**
- Completeness of the API solution
- Efficiency of the spam control mechanism
- Creativity in the preferences functionality
- Code standards (back-end and app)
- Overall project organization

This backend was built from scratch: data modeling, entities, endpoints, spam logic, and the rest of what's below. Given more time, the first thing I'd expand is user preferences. The model (`temaCorUsuario`) currently only supports the color theme (default / dark / high-contrast), but it's built to be extended with more user-configurable settings.

## Features

- **User accounts**: register, update, delete, look up by email, and a basic login check.
- **Emails (inbox)**: create, read, update, delete emails, and list all emails for a given user.
- **Labels (marcadores)**: tag emails with custom labels; many-to-many relationship between emails and labels.
- **Spam detection**: incoming emails are automatically flagged with a `usuarioSpam` label when they:
  - contain spam-associated keywords (e.g. "oferta", "ganhe", "prêmio"),
  - come from a sender with a high send frequency, or
  - contain more than 3 links in the body.
- **Demo data generator**: `POST /api/emails/generate` creates a randomized email (sender, subject, body, date) for a given user, mixing legitimate and spam-like content, useful for testing the spam filter and simulating the email service end-to-end without wiring up real email delivery.

## Tech Stack

- Java 21
- Spring Boot 3.3 (Web, Data JPA, Validation)
- Oracle Database (via `ojdbc11`)
- Flyway (database migrations)
- Lombok
- Maven

The iOS client (Swift) that consumes this API lives in a separate project.

## Architecture

Standard layered structure: `controller` → `service` → `repository` → `model`, with `dto`s for request payloads.

| Entity | Table | Description |
|---|---|---|
| `Usuario` | `t_usuario` | Application user (email, password, color theme preference) |
| `EmailEntity` | `t_email` | An email (subject, sender, recipient, body, received date) linked to a `Usuario` |
| `Marcador` | `t_marcador` | A label/tag, linked to a `Usuario` and many-to-many with emails |

Join table `t_marcador_email` implements the email ↔ label relationship, with cascade delete on both sides.

## API Endpoints

**Users**: `/api/usuarios`
| Method | Path | Description |
|---|---|---|
| GET | `/` | List all users |
| GET | `/{id}` | Get user by ID |
| POST | `/register` | Create a user |
| PUT | `/{id}` | Update a user |
| DELETE | `/{id}` | Delete a user (cascades their emails and labels) |
| GET | `/verificar?emailUsuario=` | Check if an email is already registered |
| POST | `/login` | Log in with email + password |

**Emails**: `/api/emails`
| Method | Path | Description |
|---|---|---|
| GET | `/` | List all emails |
| GET | `/{id}` | Get email by ID |
| GET | `/user/{userId}` | List emails for a user |
| POST | `/` | Create an email (runs it through the spam filter) |
| PUT | `/{id}` | Update an email |
| DELETE | `/{id}` | Delete an email |
| POST | `/generate?userId=` | Generate a random demo email for a user |

**Labels**: `/api/marcadores`
| Method | Path | Description |
|---|---|---|
| GET | `/` | List all labels |
| GET | `/{id}` | Get label by ID |
| POST | `/` | Create a label |
| DELETE | `/{id}` | Delete a label |

## Getting Started

### Prerequisites

- Java 21
- Maven (or use the bundled `./mvnw`)
- An Oracle database instance

### Configuration

Database credentials are read from `src/main/resources/application.properties`. Don't commit real credentials; use environment variables or a local, git-ignored properties file instead:

```properties
spring.datasource.url=jdbc:oracle:thin:@<host>:<port>:<sid>
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
```

Flyway runs the migrations in `src/main/resources/db/migration` automatically on startup (`spring.flyway.enabled=true`).

### Run

```bash
./mvnw spring-boot:run
```

The API starts on `http://localhost:8080` by default.

### Test

```bash
./mvnw test
```

## Known Limitations

- Passwords are currently stored and compared in plain text, fine for an academic demo, but would need hashing (e.g. BCrypt) before any real-world use.
- User preferences are limited to the color theme; this is the natural next area to expand.
- The H2 dependency is included but the project is currently wired to Oracle; swap the datasource properties to run against H2 for local development without an Oracle instance.
