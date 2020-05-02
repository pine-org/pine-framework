# Pin Framework
Pine Framework is microservices framework based on Java language.
This framework is implementing for microservices products.

## Prerequisites
 - Git
 - Java 8
 - Maven 3 
 - Docker
 - SonarQube
 - Jenkins
 - JFrog
 - Database (MySQL)

### Install Git

### Install Java

### Install Maven

### Install Docker

### Install SonarQube

### Install Jenkins
Download [Jenkins](https://jenkins.io/download/) in WAR format then execute command as follow:

    java -jar ./jenkins.war --httpPort=8585

### Install JFrog

### Install Database

### Install Artifactory

 - Download [JFrog](https://jfrog.com/open-source/)
 - [Getting Start](https://www.jfrog.com/confluence/display/JFROG/Installing+Artifactory)

### Install ActiveMQ

## Build
If you want to deploy products on your machine and every tools installed on your machine

    mvn clean install -P Development,Localhost,ConfigServer,ActiveMQ,English-Check-style

If you want to deploy products on docker that installed on your machine and every tools installed on your machine

    mvn clean install -P Development,English-Check-style,Docker

## Run
### Development Environment (deploy on localhost)
Run all tools on your machine.

Build the project:
 - `mvn -f ./config-server-address/pom.xml clean install -P Development,Localhost`
 - `mvn -f ./properties/pom.xml clean install -P Development,Localhost`
 - `mvn clean install -P Development,Localhost,ConfigServer,ActiveMQ,English-Check-style`

Run configuration service on your machine:
 - `mvn -f ./config-server/pom.xml spring-boot:run`

 - [Configuration repository](https://github.com/pine-org/pine-framework/tree/master/configuration-repository)

 - [Configuration Service Console](http://127.0.0.1:8888/config-server/profile-name)

Run your modules on your machine:

 - Add config-server profile to application.properties 
 - `mvn -f ./your module/pom.xml spring-boot:run`

### Development Environment (deploy on docker)
Build the project:

 - ``

Run configuration service and modules on your machine:

 - `docker-compose up --build` 

## Test
 - `mvn test -P Test`