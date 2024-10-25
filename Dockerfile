# Start from an OpenJDK base image
FROM openjdk:21-jdk-slim

# Update package list and install Maven
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven build file (pom.xml) first to leverage Docker layer caching
COPY pom.xml .

# Download all the dependencies (this step helps to leverage Docker's cache)
RUN mvn dependency:go-offline

# Copy the entire source code
COPY src ./src

# Build the Maven project (this runs clean and package)
RUN mvn clean package

# Expose the port that your application runs on (e.g., 8081 for a Spring Boot app)
EXPOSE 8081

# Set the entrypoint to run the jar file produced by Maven
CMD ["java", "-jar", "target/SWKOM_Paperless_GroupF-0.0.1-SNAPSHOT.jar"]
