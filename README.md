# Mithra_Casestudy

# RoadReady – Car Rental System

RoadReady is a full-stack web-based car rental platform that enables users to register, log in, search for cars, make reservations, process payments, and manage bookings. Admins have access to manage users, cars, and reservations.

## Tech Stack

### Backend
- Java 17
- Spring Boot
- Spring Security
- JSON Web Token (JWT)
- MySQL

### Frontend
- Angular 15.2
- HTML5, CSS3

## Key Features

- User Registration and Login with JWT authentication
- Car Search with filters for make, model, availability, and price
- Car Booking and Reservation system with pickup/drop-off date and time
- Secure Payment Integration (placeholder)
- User Profile Management
- Admin Dashboard to manage users, cars, bookings, and reports

## Setup Instructions

### Clone the Repository


### Backend Setup (Spring Boot)

1. Navigate to the backend folder:


2. Update the `application.properties` file in `src/main/resources`:

spring.datasource.url=jdbc:mysql://localhost:3306/<your_database_name>
spring.datasource.username=<your_mysql_username>
spring.datasource.password=<your_mysql_password>


3. Create the database in MySQL:

```sql
CREATE DATABASE <your_database_name>;

4. Run the backend server
./mvnw spring-boot:run
http://localhost:8080


Frontend Setup (Angular)
Open a new terminal and navigate to the frontend folder:

cd ../frontend

Run the frontend:

ng serve

The frontend will run on:

http://localhost:4200

Make sure the backend is running before starting the frontend.




