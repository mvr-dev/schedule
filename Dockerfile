# Stage 1: Build the application with Maven
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Run the application with Tomcat
FROM tomcat:10.1-jdk17-openjdk
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy the built WAR file from builder stage
COPY --from=builder /app/target/ROOT.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080
CMD ["catalina.sh", "run"]