#Build Gradle
FROM gradle:8.0.2-jdk19
WORKDIR /app
COPY build.gradle .
COPY settings.gradle .
COPY src ./src
RUN gradle build

#Build Java
FROM amazoncorretto:17-al2-full as final
WORKDIR /app
COPY build/libs/backend-0.0.1-SNAPSHOT.jar /backend.jar
ENTRYPOINT ["java","-jar", "/backend.jar"]
