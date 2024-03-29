FROM gradle:jdk21-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build -x test
FROM openjdk:21
COPY --from=build /home/gradle/src/build/libs/openstore-1.0.0.jar   /app/build
ENTRYPOINT java -jar /app/build/openstore-1.0.0.jar
