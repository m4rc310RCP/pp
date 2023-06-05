# syntax=docker/dockerfile:1
ARG APP_NAME=cmo-panel

# FROM maven:3.8-jdk-8 AS build
FROM maven:3.8-jdk-8 AS build

#FROM maven:3.6.0-jdk-11-slim 
#FROM openjdk:1.8-jdk-alpine

#RUN addgroup -S spring && adduser -S spring -G spring
#USER spring:spring

#FROM redis:7.0.5



#COPY .mvn/ .mvn
#RUN ./mvnw dependency:resolve
WORKDIR /app/cmo
COPY pom.xml ./
COPY src ./src

# build the app and download dependencies only when these are new (thanks to the cache)
RUN --mount=type=cache,target=/root/.m2  mvn clean package -Dmaven.test.skip

#RUN mvn -Dmaven.repo.local=~/.m2/repository clean install

#RUN mvn install -nsu
#RUN mvn install --settings /home/dokku/.m2/settings.xml
#RUN mvn -Dmaven.repo.local=/home/dokku/.m2/repository clean install
#RUN mvn install:install-file -DlocalRepositoryPath=~/.m2 -DcreateChecksum=true -Dpackaging=jar -Dfile=%2 -DgroupId=%3 -DartifactId=%4 -Dversion=%5

FROM openjdk:8-alpine
COPY --from=build /app/cmo/target/*-exec.jar /app/cmo/app.jar
EXPOSE 8080



ENTRYPOINT ["java","-jar","/app/cmo/app.jar"]

#ARG JAR_FILE=target/*.jar
#COPY target/*.jar  ./app.jar

#ENTRYPOINT ["java","-jar","/app.jar"]

#CMD ["./mvnw", "spring-boot:build-image"]
#CMD ["./mvnw", "spring-boot:run"]
#CMD "./mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=springio/gs-spring-boot-docker"
#CMD ["java","-jar","/home/smartling/flagship/repo-connector-1.5.4/repo-connector-1.5.4.jar -start&"]
