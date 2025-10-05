FROM maven:3.8.5-openjdk-17

WORKDIR /inditextest
COPY target/inditextest-0.0.2-SNAPSHOT.jar inditextest.jar
EXPOSE 8080

CMD ["java", "-jar", "inditextest.jar"]