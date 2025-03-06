FROM maven:3.9.5-eclipse-temurin-21-alpine AS build
LABEL authors="nini"
WORKDIR /app
COPY pom.xml .
# Download all required dependencies
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/online-library-system-1.0-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]