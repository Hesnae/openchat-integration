FROM openjdk:17-jdk-alpine

# Copy the JAR file into the image
COPY build/libs/chat-gpt-integration-0.0.1-SNAPSHOT.jar /app/chat-gpt-integration.jar

# Set the working directory
WORKDIR /app

# Directory that holds csv containing results
RUN mkdir csv-data

# Persist its
VOLUME /app/csv-data

# Expose the port that the service runs on
EXPOSE 8085

# Start the service
CMD ["java", "-jar", "chat-gpt-integration.jar"]