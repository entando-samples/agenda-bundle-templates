#FROM openjdk:17
FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY target/springboot-agenda.jar /app/springboot-agenda.jar

CMD ["java", "-jar", "springboot-agenda.jar"]

EXPOSE 8081
