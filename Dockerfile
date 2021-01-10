FROM openjdk:8-jdk-alpine
RUN addgroup -S fireflies && adduser -S fireflies -G fireflies
USER fireflies:fireflies
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
