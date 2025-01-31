# 1. Build Stage: Maven nutzt JDK 17 zum Bauen der Anwendung
FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /app

# Kopiere das gesamte Projekt in das Image
COPY . .

# Baue die Anwendung mit Maven
RUN mvn clean package -DskipTests

# 2. Runtime Stage: Nur das Nötigste für die fertige Anwendung
FROM openjdk:17-jdk-slim

# Installiere Ghostscript und Tesseract
RUN apt-get update && apt-get install -y tesseract-ocr tesseract-ocr-eng ghostscript && rm -rf /var/lib/apt/lists/*

WORKDIR /app

# Kopiere die fertige JAR-Datei aus dem vorherigen Build-Container
COPY --from=builder /app/target/OCRWorker-0.0.1-SNAPSHOT.jar OCRWorker.jar

# Expose Port 8082 (optional)
EXPOSE 8082

# Starte die Anwendung
CMD ["java", "-jar", "OCRWorker.jar"]
