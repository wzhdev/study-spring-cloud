FROM openjdk:8-jdk-alpine
ADD app.jar app.jar
ADD jmx_prometheus_javaagent-0.14.0.jar jmx_prometheus_javaagent-0.14.0.jar
ADD config.yaml config.yaml
ENV JAVA_OPTS=""
ENTRYPOINT exec java $JAVA_OPTS -javaagent:./jmx_prometheus_javaagent-0.14.0.jar=19090:config.yaml -jar /app.jar
