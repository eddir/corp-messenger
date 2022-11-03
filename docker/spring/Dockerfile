FROM maven:3.8.6-openjdk-18 AS builder
COPY ./backend/ ./
RUN mvn clean package -DskipTests
FROM openjdk:18-jdk-slim
COPY --from=builder /target/backend-0.0.1-SNAPSHOT.war /app.war
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.war"]