# Library REST API

A Spring Boot REST API for managing a library system, including books, members, and borrowing records.

## Technology Stack

- **Java** 21
- **Spring Boot** 4.0.5
- **Build Tool**: Maven

## Getting Started

### Prerequisites

- Java 21 or higher
- Maven 3.6+

### Running the Application

```bash
./mvnw spring-boot:run
```

The application starts on `http://localhost:8080` by default.

### Running Tests

```bash
./mvnw test
```

## Project Structure

```
src/
└── main/
    └── java/com/app/library/
        ├── LibraryApplication.java       # Application entry point
        ├── LibraryController.java        # REST controller
        ├── model/
        │   ├── Book.java                 # Book entity
        │   ├── Member.java               # Member entity
        │   └── BorrowingRecord.java      # Borrowing record entity
        └── service/
            └── LibraryService.java       # Business logic (in-memory storage)
```

## Data Models

### Book

| Field            | Type   | Description                        |
|------------------|--------|------------------------------------|
| id               | Long   | Unique identifier                  |
| title            | String | Book title                         |
| author           | String | Book author                        |
| publicationYear  | int    | Year of publication                |
| genre            | String | Book genre                         |
| availableCopies  | int    | Number of copies available         |

### Member

| Field       | Type      | Description                    |
|-------------|-----------|--------------------------------|
| id          | Long      | Unique identifier              |
| name        | String    | Member's full name             |
| email       | String    | Member's email address         |
| phoneNumber | String    | Member's phone number          |
| startDate   | LocalDate | Membership start date          |
| endDate     | LocalDate | Membership end date            |

### BorrowingRecord

| Field      | Type      | Description                          |
|------------|-----------|--------------------------------------|
| id         | Long      | Unique identifier                    |
| book       | Book      | The borrowed book                    |
| member     | Member    | The member who borrowed the book     |
| borrowDate | LocalDate | Date the book was borrowed           |
| dueDate    | LocalDate | Due date (14 days from borrow date)  |
| returnDate | LocalDate | Date the book was returned           |

## API Endpoints

Base URL: `http://localhost:8080/api`

### Books

| Method | Endpoint        | Description          | Status Codes       |
|--------|-----------------|----------------------|--------------------|
| GET    | /books          | Get all books        | 200                |
| GET    | /books/{id}     | Get a book by ID     | 200, 404           |
| POST   | /books          | Add a new book       | 201                |
| PUT    | /books/{id}     | Update a book        | 200, 404           |
| DELETE | /books/{id}     | Delete a book        | 204, 404           |

### Members

| Method | Endpoint        | Description            | Status Codes       |
|--------|-----------------|------------------------|--------------------|
| GET    | /members        | Get all members        | 200                |
| GET    | /members/{id}   | Get a member by ID     | 200, 404           |
| POST   | /members        | Add a new member       | 201                |
| PUT    | /members/{id}   | Update a member        | 200, 404           |
| DELETE | /members/{id}   | Delete a member        | 204, 404           |

### Borrowing

| Method | Endpoint               | Description                    | Status Codes |
|--------|------------------------|--------------------------------|--------------|
| GET    | /borrowing-records     | Get all borrowing records      | 200          |
| POST   | /borrow                | Borrow a book                  | 201          |
| PUT    | /return/{recordId}     | Return a borrowed book         | 200          |

## Example Requests

### Add a Book

```http
POST /api/books
Content-Type: application/json

{
  "id": 1,
  "title": "The Great Gatsby",
  "author": "F. Scott Fitzgerald",
  "publicationYear": 1925,
  "genre": "Fiction",
  "availableCopies": 3
}
```

### Add a Member

```http
POST /api/members
Content-Type: application/json

{
  "id": 1,
  "name": "Jane Doe",
  "email": "jane.doe@example.com",
  "phoneNumber": "555-1234",
  "startDate": "2024-01-01",
  "endDate": "2025-01-01"
}
```

### Borrow a Book

```http
POST /api/borrow
Content-Type: application/json

{
  "id": 1,
  "book": { "id": 1, "title": "The Great Gatsby", "author": "F. Scott Fitzgerald", "publicationYear": 1925, "genre": "Fiction", "availableCopies": 3 },
  "member": { "id": 1, "name": "Jane Doe", "email": "jane.doe@example.com", "phoneNumber": "555-1234", "startDate": "2024-01-01", "endDate": "2025-01-01" }
}
```

The `borrowDate` is automatically set to today and `dueDate` is set to 14 days from today.

### Return a Book

```http
PUT /api/return/1
```

## Notes

- Data is stored **in-memory** and will be lost when the application restarts.
- Borrowing a book with no available copies throws an `IllegalStateException`.
