FROM maven:3.8.5-openjdk-17 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

# adding flags to skip the frontend-maven-plugin and tests
# This ensures Maven ONLY builds the Java code.
RUN mvn clean package -DskipTests -P!build-frontend




FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar
COPY startup.sh .

RUN chmod +x startup.sh

EXPOSE 8080

ENTRYPOINT ["./startup.sh"]