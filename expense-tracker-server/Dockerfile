FROM maven:3.8.7-openjdk-18 AS build

# Setup environment
ENV HOME=/home/usr/app
RUN mkdir -p $HOME
WORKDIR $HOME

# Build application
COPY pom.xml $HOME
COPY src $HOME/src
RUN --mount=type=cache,target=/root/.m2 mvn clean package -Dmaven.test.skip

# Start application
FROM openjdk:19-alpine AS deploy
WORKDIR /opt/expense-tracker-server
COPY --from=build /home/usr/app/target/expense-tracker-server-1.0.jar backend.jar
ENTRYPOINT ["java", "-jar", "backend.jar"]