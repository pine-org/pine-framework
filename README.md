# Pin Framework
Pine Framework is microservices framework based on Java language.
This framework is implementing for microservices products.

## Prerequisites
 - java 8
 - maven 3 
 - Jenkins

### Install Jenkins
Download [Jenkins](https://jenkins.io/download/) in WAR format then execute command as follow:

    java -jar ./jenkins.war --httpPort=8585
### Install Artifactory

 - Download [JFrog](https://jfrog.com/open-source/)
 - [Getting Start](https://www.jfrog.com/confluence/display/JFROG/Installing+Artifactory)


## Build
    mvn clean install

## Test
    mvn test