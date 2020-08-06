FROM openjdk:14-jdk-alpine
EXPOSE 9000
VOLUME /tmp
ARG JAR_FILE=target/TvilityBack.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]