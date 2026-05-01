RESTful API built with **Spring Boot**. This project provides endpoints to manage books, library members, and book borrowing/returning processes
**Java 21**
**Maven** (Build Tool)


Main API Endpoints

**Books**
GET /api/books - get all books (supports ?author= and ?genre= filters)
GET /api/books/{id}
POST /api/books - add a new book
PUT /api/books/{id} -update a book
DELETE /api/books/{id}
GET /api/bookavailabileDate?bookId={id} - Check when a book will be available

**Members**
GET /api/members
GET /api/members/{id}
POST /api/members
PUT /api/members/{id}
DELETE /api/members/{id}

**Borrowing System**
GET /api/borrowing-records - Get all borrowing records
POST /api/borrow - Borrow a book
