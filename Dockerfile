# Use an official JDK runtime as a parent image
FROM openjdk:21-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/SWKOM_Paperless_GroupF-0.0.1-SNAPSHOT.jar paperless.jar

# Expose port 8081
EXPOSE 8081

# Run the application
ENTRYPOINT ["java", "-jar", "paperless.jar"]