# Build
FROM arm64v8/maven:3.6-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

# Final
FROM arm64v8/openjdk:11-jre-slim
COPY --from=build /home/app/target/league-friend-stats-1.0-SNAPSHOT.jar /usr/local/lib/league-friend-stats.jar
ENTRYPOINT ["java","-jar","/usr/local/lib/league-friend-stats.jar"]
