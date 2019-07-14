FROM openjdk:8-jdk-alpine
ARG JAR_FILE
ADD ${JAR_FILE} app.jar
EXPOSE 8081 8081
ENTRYPOINT [ "java", "-jar", "/app.jar"]
