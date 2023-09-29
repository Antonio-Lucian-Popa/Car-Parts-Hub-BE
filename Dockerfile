# Use the official OpenJDK base image
FROM openjdk:17

# Metadata as described above
LABEL maintainer="antonio.popa.dev@gmail.com" version="1.0" description="Spring Boot Ecommerce App"

# Specify the directory inside the container where the app will reside
WORKDIR /app

# Copy the packaged jar file into our Docker image
# COPY target/user-api-0.0.1-SNAPSHOT*.jar app.jar
COPY src/user-api-0.0.1-SNAPSHOT*.jar app.jar

# Expose the port on which the app runs. This is usually 8080 for Spring Boot web apps
EXPOSE 8080

# The command to run our app when the container is started
ENTRYPOINT ["java", "-jar", "app.jar"]
