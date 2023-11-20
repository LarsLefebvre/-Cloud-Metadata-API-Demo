# Use the official OpenJDK base image
FROM eclipse-temurin:21

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the application will run on
EXPOSE 8080

# Specify the command to run the application
CMD ["java", "-jar", "app.jar"]
