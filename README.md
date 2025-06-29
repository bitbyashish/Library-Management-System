Library Inventory Management API
=================================

A Spring Boot RESTful API to manage a library’s book inventory, including:
- CRUD operations
- Search
- Asynchronous notifications when a wishlisted book is returned

------------------------------------------------------------
FEATURES
------------------------------------------------------------

- Books Management:
  * Create, update, delete books
  * Retrieve paginated and filtered lists
  * Search books by title or author (partial match)

- Wishlist Notifications:
  * Users can wishlist books
  * When a book is marked as AVAILABLE, notifications are prepared asynchronously (logged)

- Validation:
  * ISBN uniqueness
  * Valid published year

- Swagger/OpenAPI:
  * Interactive API documentation

------------------------------------------------------------
TECH STACK
------------------------------------------------------------

- Java 17+
- Spring Boot 3.x
- Spring Data JPA
- Hibernate 6.x
- MySQL
- Lombok
- Springdoc OpenAPI
- JUnit 5 + Mockito

------------------------------------------------------------
GETTING STARTED
------------------------------------------------------------

1. Prerequisites:
   - Java 17 or higher
   - Maven
   - MySQL

2. Database Setup:

   Create the database:

   CREATE DATABASE librarydb;

   Configure application.properties:

   spring.datasource.url=jdbc:mysql://localhost:3306/librarydb
   spring.datasource.username=your_db_user
   spring.datasource.password=your_db_password

3. Build & Run:

   Build:
   mvn clean install -DskipTests

   Run:
   mvn spring-boot:run

   or

   java -jar target/library-0.0.1-SNAPSHOT.jar

------------------------------------------------------------
SWAGGER API DOCS
------------------------------------------------------------

Visit:

http://localhost:8080/swagger-ui/index.html

------------------------------------------------------------
API ENDPOINTS
------------------------------------------------------------

Books:

- GET    /api/books         List books (pagination, filters)
- GET    /api/books/search  Search by title or author
- POST   /api/books         Create a new book
- PUT    /api/books/{id}    Update book details
- DELETE /api/books/{id}    Delete a book

------------------------------------------------------------
NOTIFICATIONS
------------------------------------------------------------

When a book’s availabilityStatus changes from BORROWED to AVAILABLE:
- Finds all users who have wishlisted it
- Logs notification messages asynchronously, e.g.:

Notification prepared for user_id: 1: Book [Effective Java] is now available.

------------------------------------------------------------
RUNNING TESTS
------------------------------------------------------------

Run:

mvn test

------------------------------------------------------------
FUTURE IMPROVEMENTS
------------------------------------------------------------

- User authentication and roles
- Email or push notifications
- Admin dashboard
- Advanced search and filtering
