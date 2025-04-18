FROM gradle:8.5-jdk17 AS build
WORKDIR /app
COPY . /app/
RUN gradle bootJar --no-daemon
RUN gradle copyJavaagent --no-daemon

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
COPY --from=build /app/build/agent/opentelemetry-javaagent.jar opentelemetry-javaagent.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]