FROM openjdk:17
ADD target/auditService-0.0.1-SNAPSHOT.jar auditService.jar
ENTRYPOINT ["java", "-jar","auditService.jar"]
EXPOSE 8080