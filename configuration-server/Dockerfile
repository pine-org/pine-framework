FROM openjdk:8
COPY ./target/configuration-server_1.0.0-SNAPSHOT.jar configuration-server_1.0.0-SNAPSHOT.jar
WORKDIR ./
EXPOSE 8888
CMD ["java", "-jar", "configuration-server-0.0.1-SNAPSHOT.jar"]