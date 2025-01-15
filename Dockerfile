FROM openjdk:23-jdk
WORKDIR /app
COPY backend/target/TickLy-0.5.0-SNAPSHOT.jar /app/TickLy-0.5.0-SNAPSHOT.jar
EXPOSE 8080
CMD ["java", "-jar", "/app/TickLy-0.5.0-SNAPSHOT.jar"]
