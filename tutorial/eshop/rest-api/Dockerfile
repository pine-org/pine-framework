FROM openjdk:8-jdk-alpine
EXPOSE 9091
WORKDIR ./app
ARG JAR_FILE=./target/eshop-1.0.0-SNAPSHOT.jar
ADD ${JAR_FILE} application.jar
ENTRYPOINT ["java","-jar","/app/application.jar"]