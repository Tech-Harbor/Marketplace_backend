#Build Gradle
FROM gradle:7.3-amazoncorretto-21 AS builder
WORKDIR /app
COPY build.gradle .
COPY settings.gradle .
COPY src ./src
RUN gradle build --no-daemon

#Build Java
FROM amazoncorretto:21-al2-full as final
WORKDIR /app
COPY ./build/libs/backend-0.0.1-SNAPSHOT.jar /backend.jar
ENTRYPOINT ["java","-jar", "/backend.jar"]