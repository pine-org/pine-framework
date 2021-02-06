FROM openjdk:8-jdk-alpine

CMD mkdir /certs
COPY ./src/main/resources/server.crt /certs/
COPY ./src/main/resources/client.crt /certs/

RUN keytool -import -trustcacerts -alias client -file /certs/client.crt -keystore $JAVA_HOME/jre/lib/security/cacerts -storepass changeit -noprompt
RUN keytool -import -trustcacerts -alias server -file /certs/server.crt -keystore $JAVA_HOME/jre/lib/security/cacerts -storepass changeit -noprompt

RUN apk add --update curl

EXPOSE 8761
CMD mkdir /app
ARG JAR_FILE=./target/discovery-server-eureka-1.0.0-SNAPSHOT.jar
ADD ${JAR_FILE} /app/application.jar
ENTRYPOINT ["java","-jar","/app/application.jar"]