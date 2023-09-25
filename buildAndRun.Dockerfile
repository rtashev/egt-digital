# step 1) build app
FROM maven:3.9.4-eclipse-temurin-17-alpine as build
WORKDIR /app

COPY pom.xml .
COPY / ./
RUN mvn install -DskipTests

# step 2) run app
FROM eclipse-temurin:17.0.3_7-jre-alpine
WORKDIR /app
EXPOSE 8080
COPY --from=build /app/server/target/gateway.jar /app/gateway.jar
ENTRYPOINT ["java", "-jar", "/app/gateway.jar" ]