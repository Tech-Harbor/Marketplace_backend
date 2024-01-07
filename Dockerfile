FROM gradle:8.0.2-jdk17 AS builder

WORKDIR /app

COPY ./src src/
COPY ./build.gradle build.gradle
COPY ./settings.gradle settings.gradle

RUN gradle clean build -x test

FROM openjdk:17-alpine

WORKDIR /app

COPY --from=builder /app/build/libs/backend-0.0.1-SNAPSHOT.jar backend.jar

EXPOSE 8080

CMD ["java","-jar","backend.jar"]