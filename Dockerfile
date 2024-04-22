# Stage 1: Build the application
FROM openjdk:17-jdk-slim as build

# Install Git and Maven
RUN apt-get update && apt-get install -y git maven && rm -rf /var/lib/apt/lists/*

# Set the working directory
WORKDIR /blog

# Clone the project from GitHub
RUN git clone ${REPO_URL} .

# Build the project using Maven
RUN mvn clean install -DskipTests

# Stage 2: Create the runtime image with Maven included
FROM openjdk:17-jdk-slim

# Install Git, Maven, and MySQL client
RUN apt-get update && \
    apt-get install -y git maven mariadb-client && \
    rm -rf /var/lib/apt/lists/*

# Set the working directory
WORKDIR /app

# Copy the entire project directory including the .git directory and pom.xml from the build stage
COPY --from=build /blog .

# Copy the entrypoint.sh script
COPY entrypoint.sh /app/entrypoint.sh

# Make entrypoint.sh executable
RUN chmod +x /app/entrypoint.sh

# Copy the wait-for-mysql.sh script
COPY wait-for-mysql.sh /app/wait-for-mysql.sh

# Make wait-for-mysql.sh executable
RUN chmod +x /app/wait-for-mysql.sh

# Command to run the project
CMD ["/app/wait-for-mysql.sh", "db", "/app/entrypoint.sh"]