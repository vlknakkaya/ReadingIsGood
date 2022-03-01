# ReadingIsGood
ReadingIsGood is an online books retail firm that operates only on the Internet. Main target of ReadingIsGood is to deliver books from its one centralized warehouse to their
customers within the same day. That is why stock consistency is the first priority for their vision operations.

## Description
ReadingIsGood is a REST API that provides you can create and manage customers, books and orders. It can also serve statistics of orders by customers.

In this project, **Spring Boot**, **Spring Data JPA** with **MySQL** and **Spring Security** is used. Also it has **OpenAPI Specification** for API documentation and **Docker** to containerize.

## Table of Contents

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
After successful start of the application, you can access the OpenAPI UI on:
> http://localhost:8080/api-doc.html

## Installation and Run (STS)
### Prerequisites
- STS must be installed, [if not](https://spring.io/tools)
- MySQL database is a must for running the application
  - I used XAMPP because of it includes MariaDB and manageable from browser (it makes somethings easier :slightly_smiling_face:)
  - You have to create `reading` database on

Clone this repository to your local:
```
$ git clone https://github.com/vlknakkaya/ReadingIsGood.git
```
Import the project into STS:
```
File -> Import -> Gradle -> Existing Gradle Project
```
Build it from Gradle Tasks (it takes time based on dependencies) and Start!

## Usage
You can use the API using **Postman** with the collection or it's OpenAPI UI.
