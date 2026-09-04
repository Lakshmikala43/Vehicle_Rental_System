# Multi-stage Docker Build for RentRide Enterprise Platform
FROM maven:3.9-eclipse-temurin-17 AS backend-build
WORKDIR /app/backend
COPY backend/pom.xml .
COPY backend/src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=backend-build /app/backend/target/*.jar ./app.jar
COPY frontend ./frontend
EXPOSE 8080 3000

ENV PORT=8080
ENTRYPOINT ["java", "-jar", "./app.jar"]
