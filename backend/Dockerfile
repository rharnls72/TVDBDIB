FROM openjdk:14-jdk-alpine

EXPOSE 9100

ARG JAR_FILE=target/TvilityBack.jar

COPY ${JAR_FILE} app.jar

COPY serviceAccountKey.json /tvility/serviceAccountKey.json

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]