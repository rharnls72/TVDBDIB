# FROM openjdk:14-jdk-alpine

FROM ubuntu:latest

RUN apt-get update && apt-get install -y openjdk-14 && apt-get install -y python3.8

RUN pip install pip --upgrade
RUN pip install setuptools --upgrade
RUN pip install tensorflow
RUN pip install pandas

EXPOSE 8999

ARG JAR_FILE=target/TvilityBack.jar

COPY ${JAR_FILE} app.jar

COPY serviceAccountKey.json /tvility/serviceAccountKey.json

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]