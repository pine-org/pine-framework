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

 - Download [JFrog](https://jfrog.com/open-source/)
 - [Getting Start](https://www.jfrog.com/confluence/display/JFROG/Installing+Artifactory)

### Install Database

### Install ActiveMQ

## Build
If you want to deploy products on your machine and every tools installed on your machine

### Build based on spring and properties files
- `mvn -f ./core/pom.xml clean install -P ...`
- `mvn -f ./spring-core/pom.xml clean install -P ...`
- `mvn -f ./tutorial/pom.xml clean install -P ...,PropertiesFile`

### Build based on spring and config server (Cloud)
- `mvn -f ./core/pom.xml clean install -P ...`
- `mvn -f ./spring-core/pom.xml clean install -P ...`
- `mvn -f ./tutorial/pom.xml clean install -P ...,ConfigServer`

## Run
### Development Environment (deploy on localhost)
Run all tools on your machine.

Build the project:
- `mvn -f ./core/pom.xml clean install -P Development,Localhost,...`
- `mvn -f ./spring-core/pom.xml clean install -P Development,Localhost,...`
- `mvn -f ./tutorial/pom.xml clean install -P Development,Localhost,...,PropertiesFile`

Run your modules on your machine:
 - `java -jar ./tutorial/e-shop/rest-api/target/e-shop-*.jar`

Build the cloud base project:
- `mvn -f ./core/pom.xml clean install -P Development,Localhost,...`
- `mvn -f ./spring-core/pom.xml clean install -P Development,Localhost,...`
- `mvn -f ./tutorial/pom.xml clean install -P Development,Localhost,...,ConfigServer`

Run config server on your machine:
 - `java -jar ./spring-core/config-server/target/config-server-*.jar`

 - [Configuration repository](https://github.com/pine-org/pine-framework/tree/master/config-repo)

 - [Configuration Service Console](http://127.0.0.1:8888/config-server/common)

Run your modules on your machine:
 - `java -jar ./tutorial/e-shop/rest-api/target/e-shop-*.jar`

## Test
 - `mvn test -P Test`