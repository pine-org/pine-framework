FROM openjdk:8
COPY ./target/config-server_1.0.0-SNAPSHOT.jar config-server.jar
WORKDIR ./
EXPOSE 8888
CMD ["java", "-jar", "config-server.jar"]