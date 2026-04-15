# BUILD
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /build

# copy wrapper of Gradle for the docker cache
COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./

# copy clean code layers
COPY application application
COPY domain domain
COPY infrastructure infrastructure

# permission to wrapper
RUN chmod +x ./gradlew

# compile
RUN ./gradlew :app-service:bootJar -x test

# RUN
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Change internal time zone
RUN apk add --no-cache tzdata
ENV TZ="America/Bogota"

# Create an user without permissions
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copy only the jar wich generate in the previuos step
COPY --from=builder /build/application/app-service/build/libs/*SNAPSHOT.jar app.jar

# set port
EXPOSE 8080

ENTRYPOINT ["java", "-Duser.timezone=America/Bogota", "-jar", "app.jar"]