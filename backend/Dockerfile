FROM maven:3.8.3-openjdk-17 AS builder

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:17-jdk

WORKDIR /app

COPY .env .
COPY --from=builder /app/target/eventify-0.0.1-SNAPSHOT.jar eventify-0.0.1-SNAPSHOT.jar

CMD ["java", "-jar", "eventify-0.0.1-SNAPSHOT.jar"]
