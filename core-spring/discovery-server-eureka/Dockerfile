FROM openjdk:8
COPY ./target/discovery-server-eureka-1.0.0-SNAPSHOT.jar discovery-server-eureka.jar
WORKDIR ./
EXPOSE 8761
CMD ["java", "-jar", "discovery-server-eureka.jar"]