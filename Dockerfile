FROM openjdk:8-jdk-alpine

ENV DISTRIBUTION=service/build/distributions \
    APP_HOME=/opt/service \
    DUMBINIT_VERSION=1.0.3 \
    JAVA_OPTS=-Dlog4j.configurationFile=/opt/config/log4j2.xml

RUN apk --no-cache add bash

RUN mkdir -p ${APP_HOME}

WORKDIR ${APP_HOME}

ADD build/libs/stock-exchange-service.jar /opt/service

EXPOSE 8080


CMD [ "java", "-jar", "/opt/service/stock-exchange-service.jar" ]