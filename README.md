# ReadingIsGood
ReadingIsGood is an online books retail firm that operates only on the Internet. Main target of ReadingIsGood is to deliver books from its one centralized warehouse to their
customers within the same day. That is why stock consistency is the first priority for their vision operations.

## Description
ReadingIsGood is a REST API that provides you can create and manage customers, books and orders. It can also serve statistics of orders by customers.

In this project, **Spring Boot**, **Spring Data JPA** with **MySQL** and **Spring Security** is used. Also it has **OpenAPI Specification** for API documentation and **Docker** to containerize.

## Used Technologies
- Java 11
- Spring Boot
- Spring Data JPA
- Spring Security
- MySQL
- Docker
- JWT (JSON Web Token)
- OpenAPI (Swagger)
- Postman
- Git

## Installation and Run (Docker)
### Prerequisites
- Gradle must be installed, [if not](https://docs.docker.com/get-docker/)
- Docker must be installed, [if not](https://gradle.org/install/)

Clone this repository to your local:
```
$ git clone https://github.com/vlknakkaya/ReadingIsGood.git
```
Build it using **Gradle** (skipping tests because of database is not available): 
```
$ ./gradlew clean build -x test
```
Create Docker environment and start up the application on it:
```
$ docker-compose build
$ docker-compose up
```
After successful start of the application, you can access it's OpenAPI UI:
> http://localhost:8080/api-doc.html

## Installation and Run (STS)
### Prerequisites
- STS must be installed, [if not](https://spring.io/tools)
- MySQL database is a must for running the application
  - I used [XAMPP](https://www.apachefriends.org/tr/index.html) because of it includes MariaDB and manageable from browser (it makes somethings easier :slightly_smiling_face:)
  - You have to create `reading` database on it

Clone this repository to your local:
```
$ git clone https://github.com/vlknakkaya/ReadingIsGood.git
```
Import the project into STS:
```
File -> Import -> Gradle -> Existing Gradle Project
```
Build it from Gradle Tasks (it takes time based on dependencies) and Start!

## API Usage
You can use the API using **Postman** with the [collection](docs/ReadingIsGood.postman_collection.json) or it's OpenAPI UI.

### AuthController
Because of API has `authentication`, you need to create token to use it. AuthController provides token creation.

You can create your token by giving credentials to `/auth`:
> http://localhost:8080/auth

> NOTE: Application has 3 in-memory users. You can use one of them to create token.
> | username  | password |
> | --------- | -------- |
> |   user1   |   1111   |
> |   user2   |   2222   |
> |   user3   |   3333   |

After creating token, you can use it by giving to `Header` with `Authorization` key and `Bearer ` (includes whitespace) prefix.

### BookController
You can use this controller's methods to manage Book. You can display, create, update or delete a Book or update stocks.

### CustomerController
You can use this controller's method to manage Customer. You can display, create, update or delete a Customer or display it's orders.

### OrderController
You can use this controller's methods to manage Order. You can display, create, update or delete a Order, search by date or set it's status.

### StatisticsController
You can use this controller's methods to display statistics. You can display all or customer based statistics. Statistics value is grouped by months.
