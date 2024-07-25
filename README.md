# Spring Boot Blog Management Application
<img src="Secure-blogs.gif" alt="SecureBlogs GIF" width="600" height="800">
This repository contains the source code for a comprehensive blog management application developed using the Spring Boot framework. The application follows a monolithic architecture and incorporates various features and technologies to enable efficient management of blogs and user interactions.

## Features

- **User Management**: Implemented user authentication, registration, login, and profile management functionalities.
- **Category Management**: Developed features to organize blog posts into different categories.
- **Post Management**: Implemented CRUD operations for managing blog posts, including creation, editing, and deletion.
- **Commenting System**: Integrated functionality for users to interact with posts through comments.
- **Role-Based Access Control**: Utilized role-based access control to define user permissions and access levels.
- **Validation**: Implemented validation techniques to ensure data integrity and security.
- **API Documentation**: Integrated Swagger for API documentation and testing purposes.
- **OTP Authentication**: Utilized Twilio API to enable OTP-based authentication for enhanced security.
- **Simple Messaging**: Integrated Twilio for sending OTP via SMS and email for user verification.
- **Database**: Utilized MySQL as the relational database management system.
- **Development Environment**: Utilized Spring Tool Suite 4 for development and debugging purposes.
- **Deployment**: Hosted the application on AWS EC2 for scalability and reliability.

## Technologies Used

- Spring Boot
- Spring Security
- MySQL
- Java Persistence API (JPA)
- Swagger
- Twilio API
- Spring Tool Suite 4
- AWS EC2

## Getting Started

1. Clone the repository:

   ```
   git clone https://github.com/tusharmahat/SpringBoot_Blog_RestAPI.git
   ```

2. Navigate to the project directory:

   ```
   cd SpringBoot_Blog_RestAPI
   ```
3. Make sure you have mysql installed, and create a schema "BlogRestAPI"

4. Build and run the application:

   ```
   mvn spring-boot:run
   ```

   OR

    ```
   docker-compose build
   docker-compose up
   ```

6. Access the application at `http://localhost:8080`, which shows nothing, go to API Documentation to explore endpoints.
Login using email: email@gmail.com password: 12345

## Documentation

- [API Documentation](http://localhost:8080/swagger-ui/index.html#/) - Swagger UI for exploring and testing the API endpoints.

## Contributing

Contributions are welcome! Please feel free to submit a pull request or open an issue for any feature requests, bug fixes, or improvements.

