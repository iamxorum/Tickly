# Use OpenJDK 23 image
FROM openjdk:23-jdk

# Set working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY backend/target/TickLy-0.3.0-SNAPSHOT.jar /app/TickLy-0.3.0-SNAPSHOT.jar

# Expose the port for the app (if your app uses port 8080)
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "/app/TickLy-0.3.0-SNAPSHOT.jar"]
