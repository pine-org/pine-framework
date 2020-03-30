FROM openjdk:8
COPY ./target/activemq-sample_1.0.0-SNAPSHOT.jar activemq-sample_1.0.0-SNAPSHOT.jar
WORKDIR ./
EXPOSE 9091
CMD ["java", "-jar", "activemq-sample_1.0.0-SNAPSHOT.jar"]