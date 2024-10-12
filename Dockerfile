## Use JDK 21 as the base image
#FROM openjdk:21-oracle
## Set the working directory inside the container
#WORKDIR /app
#
## Copy the Spring Boot jar file into the container
#COPY target/*.jar app.jar
#
## Expose the port your Spring Boot app will run on (default is 8080)
##EXPOSE 8080
#
## Command to run the Spring Boot application
#ENTRYPOINT ["java", "-jar", "/app/app.jar"]


# Use Maven as the build stage
FROM maven:3.9.4-eclipse-temurin-21 AS build
# Set the working directory inside the container
WORKDIR /app

# Copy the entire project into the container (excluding files ignored by .dockerignore)
COPY . .

# Run Maven to build the package
RUN mvn clean package -DskipTests

# Use JDK 21 as the base image for the final stage
FROM openjdk:21-oracle
# Set the working directory inside the container
WORKDIR /app

# Copy the Spring Boot jar file from the build stage into the container
COPY --from=build /app/target/*.jar app.jar

# Expose the port your Spring Boot app will run on (default is 8080)
EXPOSE 8082

# Command to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]