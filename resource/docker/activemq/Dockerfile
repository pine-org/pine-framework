FROM openjdk:8-jdk-alpine

COPY ./apache-activemq-5.16.0-bin.tar.gz /opt/
RUN tar -xzf /opt/apache-activemq-5.16.0-bin.tar.gz -C /opt/
RUN chmod -X /opt/apache-activemq-5.16.0
RUN apk add --update curl

WORKDIR /opt/apache-activemq-5.16.0

EXPOSE 8161
EXPOSE 61616

ENTRYPOINT ["/bin/sh", "-c", "bin/activemq console"]



