FROM maven:3.8.4-openjdk-17 as builder
COPY . /tmp/
WORKDIR /tmp
RUN ["mvn", "clean", "install"]

FROM openjdk:17-alpine AS server
WORKDIR /opt/expense-tracker
COPY --from=builder /tmp/target/expense-tracker-server-1.0.jar backend.jar
RUN ["mkdir", "/database"]
RUN ["mkdir", "/backups"]
ENTRYPOINT ["java", "-jar", "backend.jar"]