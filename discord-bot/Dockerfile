FROM openjdk:17-oracle

ARG JAR_FILE=discord-bot/target/*.jar
COPY ${JAR_FILE} app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app.jar"]