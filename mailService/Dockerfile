FROM openjdk:17
ADD target/MailService-0.0.1-SNAPSHOT.jar mailService.jar
ENTRYPOINT ["java", "-jar","mailService.jar"]
EXPOSE 8080