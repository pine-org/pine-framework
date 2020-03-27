FROM openjdk:8
COPY ./target/configuration-server-0.0.1-SNAPSHOT.jar configuration-server-0.0.1-SNAPSHOT.jar
WORKDIR ./
EXPOSE 8888
CMD ["java", "-jar", "configuration-server-0.0.1-SNAPSHOT.jar"]