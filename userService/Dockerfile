FROM openjdk:17
ADD target/userService-0.0.1-SNAPSHOT.jar userService.jar
ENTRYPOINT ["java", "-jar","userService.jar"]
EXPOSE 8080