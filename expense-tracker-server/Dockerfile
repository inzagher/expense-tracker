FROM maven:3.8.5-openjdk-18 as build

# Setup environment
ENV HOME=/home/usr/app
RUN mkdir -p $HOME
WORKDIR $HOME

# Download dependencies
COPY pom.xml $HOME
RUN mvn dependency:resolve

# Build application
COPY . $HOME
RUN mvn clean package -Dmaven.test.skip

# Start application
FROM openjdk:18-alpine AS deploy
WORKDIR /opt/expense-tracker-server
COPY --from=build /home/usr/app/target/expense-tracker-server-1.0.jar backend.jar
ENTRYPOINT ["java", "--enable-preview", "-jar", "backend.jar"]