# Dockerfile for Production Deployment (STORY-108)
FROM openjdk:25-jre-slim

WORKDIR /app

COPY target/webofpolitics-integration.jar webofpolitics-integration.jar

EXPOSE 8080

ENV JAVA_TOOL_OPTIONS="-Dspring.profiles.active=production"

ENTRYPOINT ["java", "-jar", "webofpolitics-integration.jar"]
