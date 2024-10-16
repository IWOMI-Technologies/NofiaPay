# Stage 1: Build the application using Maven
FROM maven:3.9.9-eclipse-temurin-21-alpine AS builder

# Set the working directory
WORKDIR /app

# Copy the Maven project files
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Stage 2: Create a minimal runtime environment with a slim JDK
FROM eclipse-temurin:21-jre-alpine

# Set the working directory
WORKDIR /app

# Copy the Spring Boot jar file from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the port your application will run on
EXPOSE 8082

# Command to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
