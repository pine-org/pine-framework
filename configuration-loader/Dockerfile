FROM openjdk:8
COPY ./target/configuration-loader_1.0.0-SNAPSHOT.jar configuration-loader_1.0.0-SNAPSHOT.jar
WORKDIR ./
EXPOSE 8888
CMD ["java", "-jar", "configuration-loader-0.0.1-SNAPSHOT.jar"]