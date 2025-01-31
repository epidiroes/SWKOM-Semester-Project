# 1. Build Stage
FROM maven:3.9.6-eclipse-temurin-21 AS builder

# Setze das Arbeitsverzeichnis
WORKDIR /app

# Kopiere das Maven-Projekt ins Container-Image
COPY . .

# Baue die Anwendung mit Maven
RUN mvn clean package -DskipTests

# 2. Runtime Stage
FROM openjdk:21-jdk-slim

# Setze das Arbeitsverzeichnis
WORKDIR /app

# Kopiere die fertige JAR-Datei aus dem vorherigen Build-Container
COPY --from=builder /app/target/RestUI-0.0.1-SNAPSHOT.jar restui.jar

# Exponiere Port 8081
EXPOSE 8081

# Starte die Anwendung
ENTRYPOINT ["java", "-jar", "restui.jar"]
