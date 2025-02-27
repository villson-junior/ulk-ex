# ulk-ex

This project is a simple Tweet Manager developed with Java and Spring Boot, using Spring Security 6,
OAuth 2.0 for authentication, and JSON Web Tokens (JWT) for secure access control. The system allows
users to create, list, and delete tweets, with specific access and action rules based on the user's
role.

## Technologies

- Java 21
- Spring Boot 3.4.3
- Spring Security 6
- OAuth 2.0
- JWT (JSON Web Token)
- MySQL
- Flyway
- Lombok

## Requirements

Before you start, make sure you have the following tools installed:

- Java 21
- Docker/Compose
- Maven

## Getting Started

1. Clone the Repository

```console
   git clone https://github.com/yourusername/tweet-manager.git
   cd tweet-manager
   ```

2. Configure private/public keys (inside the resources folder)

```console
   openssl genrsa > private-key.key
   openssl rsa -in private-key.key -pbout -out public-key.pub
   ```

3. Set Up the Database (inside docker folder)

```console
   docker-compose up
   ```

4. Run the Application

```console
   mvn spring-boot:run
   ```