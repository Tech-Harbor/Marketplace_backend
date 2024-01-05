#Build Gradle
FROM gradle:8.0-jdk17 AS builder
WORKDIR /app
COPY build.gradle .
COPY settings.gradle .
COPY src ./src
RUN gradle build --no-daemon

#Build Java
FROM openjdk:17-alpine
WORKDIR /app
COPY --from=build /app/build/libs/backend-0.0.1-SNAPSHOT.jar ./backend.jar
ENTRYPOINT ["java","-jar", "/backend.jar"]