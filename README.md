# Pin Framework
Pine Framework is microservices framework based on Java language.
This framework is implementing for microservices products.

## Prerequisites

- Java 8
- Maven 3
- Database (MySQL)
- Active MQ
- Git
- Docker
- SonarQube
- Jenkins
- JFrog

### Install Java

### Install Maven

### Install Database

### Install ActiveMQ

### Install Git

### Install Docker

### Install SonarQube

### Install Jenkins

Download [Jenkins](https://jenkins.io/download/) in WAR format then execute command as follow:

    java -jar ./jenkins.war --httpPort=8585

### Install JFrog

- Download [JFrog](https://jfrog.com/open-source/)
- [Getting Start](https://www.jfrog.com/confluence/display/JFROG/Installing+Artifactory)

## Build

    mvn -f ./core-java8/pom.xml clean install -DskipTests=true -P English-Check-style
    mvn -f ./core-security/pom.xml clean install -DskipTests=true -P English-Check-style
    mvn -f ./core-spring/pom.xml clean install -DskipTests=true -P English-Check-style
    mvn -f ./tutorial/pom.xml clean install -DskipTests=true -P English-Check-style

## Test

- `file: run-test`

## Getting Start

- `file: run-gettingstart`

## Run

### Development Environment (Tutorial: eshop)
- `file: run-eshop`
