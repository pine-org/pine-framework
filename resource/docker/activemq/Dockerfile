FROM openjdk:8-jdk-alpine

RUN apk add --update curl
RUN apk update && apk add wget

RUN wget -O /opt/apache-activemq.tar.gz https://downloads.apache.org//activemq/5.16.1/apache-activemq-5.16.1-bin.tar.gz
RUN tar -xzf /opt/apache-activemq.tar.gz -C /opt/ && mv /opt/apache-activemq-* /opt/apache-activemq && rm /opt/apache-activemq.tar.gz
RUN chmod -X /opt/apache-activemq

WORKDIR /opt/apache-activemq

EXPOSE 8161 61616 5672 61613 1833

ENTRYPOINT ["/bin/sh", "-c", "bin/activemq console"]



