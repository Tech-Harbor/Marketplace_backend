#Build Gradle
FROM gradle:8.0.2 AS builder
WORKDIR /app
COPY build.gradle .
COPY settings.gradle .
COPY src ./src
RUN gradle build --no-daemon

#Build Java
FROM openjdk:17 AS final
WORKDIR /app
COPY --from=build /build/libs/backend-0.0.1-SNAPSHOT.jar /backend.jar
ENTRYPOINT ["java","-jar", "/backend.jar"]