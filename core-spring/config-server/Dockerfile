FROM openjdk:8-jdk-alpine
EXPOSE 8888
WORKDIR ./app
ARG JAR_FILE=./target/config-server-1.0.0-SNAPSHOT.jar
ADD ${JAR_FILE} application.jar
ENTRYPOINT ["java","-jar","/app/application.jar"]