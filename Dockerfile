#Base image
FROM openjdk:8-alpine
#Copy "target/spreading-api.jar" file from the host machine into /opt/app/app.jar docker image
ADD target/github-analytics-0.0.1-SNAPSHOT.jar opt/app/app.jar

WORKDIR opt/app

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]
