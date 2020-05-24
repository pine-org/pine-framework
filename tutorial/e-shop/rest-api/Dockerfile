FROM openjdk:8
COPY ./target/e-shop-1.0.0-SNAPSHOT.jar e-shop.jar
WORKDIR ./
EXPOSE 9091
CMD ["java", "-jar", "e-shop.jar"]