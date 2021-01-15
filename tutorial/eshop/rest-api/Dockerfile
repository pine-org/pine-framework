FROM openjdk:8
COPY ./target/eshop-1.0.0-SNAPSHOT.jar eshop.jar
WORKDIR ./
EXPOSE 9091
CMD ["java", "-jar", "eshop.jar"]