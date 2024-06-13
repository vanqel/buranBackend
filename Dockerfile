FROM gradle:8.6-jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle clean
RUN gradle build -x test

FROM openjdk:21-slim-buster
ARG VERSION=1.0.0
ARG PROJECT=buran
ENV JARNAME=$PROJECT-$VERSION.jar
COPY --from=build /home/gradle/src/build/libs/$JARNAME  /
ENTRYPOINT java -jar $JARNAME

