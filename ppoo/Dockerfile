FROM gradle:9.8.2-jdk21-alpine AS BUILDER
WORKDIR /home/gradle/src
COPY --chown=gradle:gradle . .
RUN gradle bootJar --no-daemon

FROM eclipse-temurin:21-jre-alpine
EXPOSE 8080
WORKDIR /app

COPY --from=BUILDER /home/gradle/src/build/libs/*-SNAPSHOT.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]