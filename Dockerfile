FROM openjdk:22
MAINTAINER rajan.com
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]