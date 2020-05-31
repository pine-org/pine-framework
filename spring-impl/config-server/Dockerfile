FROM openjdk:8
COPY ./target/config-server-1.0.0-SNAPSHOT.jar config-server.jar
WORKDIR ./
EXPOSE 8888
CMD ["java", "-jar", "config-server.jar"]