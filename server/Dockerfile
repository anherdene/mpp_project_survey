FROM maven:3.5-jdk-8
#FROM java:8
#FROM maven:3-jdk-8-alpine

WORKDIR /app

COPY . /app

RUN mvn -v
RUN mvn clean install -DskipTests
EXPOSE 8080
LABEL maintainer=“anherdene@gmail.com”
ENTRYPOINT ["java","-jar","/app/target/mpp-survey-0.0.1-SNAPSHOT.jar"]