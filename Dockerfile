# Use JDK 21 as the base image
FROM openjdk:21-oracle
# Set the working directory inside the container
WORKDIR /app

# Copy the Spring Boot jar file into the container
COPY target/*.jar app.jar

# Expose the port your Spring Boot app will run on (default is 8080)
#EXPOSE 8080

# Command to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
