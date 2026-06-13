# Stage 1: Build stage
FROM gradle:8.5-jdk17 AS build

WORKDIR /app

# Copy project files
COPY settings.gradle .
COPY build.gradle .
COPY src ./src

# Build the application
RUN gradle bootJar --no-daemon

# Stage 2: Run image with slim JDK
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy built jar from build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# Start the application
ENTRYPOINT ["java", "-jar", "app.jar"]
