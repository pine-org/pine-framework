FROM openjdk:8
COPY ./target/activemq-sample-1.0.0-SNAPSHOT.jar activemq-sample.jar
WORKDIR ./
EXPOSE 9091
CMD ["java", "-jar", "activemq-sample.jar"]