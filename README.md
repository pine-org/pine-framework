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

    mvn clean install -P Development,English-Check-style,Localhost

If you want to deploy products on docker that installed on your machine and every tools installed on your machine

    mvn clean install -P Development,English-Check-style,Docker

## Run
Run on local machine


## Test
    mvn test