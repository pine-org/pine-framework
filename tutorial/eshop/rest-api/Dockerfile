FROM openjdk:8-jdk-alpine

ARG JAR_PATH=./target
ARG JAR_NAME=eshop
ARG JAR_VERSION=1.0.0-SNAPSHOT
ARG TARGET_PATH=/app
ENV APPLICATION=${TARGET_PATH}/application.jar
ENV PORT=9091

CMD mkdir /certs
CMD mkdir ${TARGET_PATH}

COPY ./src/main/resources/server.crt /certs/
COPY ./src/main/resources/client.crt /certs/

RUN keytool -import -trustcacerts -alias client -file /certs/client.crt -keystore $JAVA_HOME/jre/lib/security/cacerts -storepass changeit -noprompt
RUN keytool -import -trustcacerts -alias server -file /certs/server.crt -keystore $JAVA_HOME/jre/lib/security/cacerts -storepass changeit -noprompt
RUN apk add --update curl

ADD ${JAR_PATH}/${JAR_NAME}-${JAR_VERSION}.jar ${TARGET_PATH}/application.jar

EXPOSE ${PORT}
ENTRYPOINT java -jar ${APPLICATION}