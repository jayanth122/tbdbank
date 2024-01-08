# TBD Bank Digital Banking Application

Welcome to TBD Bank's Digital Banking Application - a state-of-the-art digital banking platform designed for ease of use and superior customer experience. Our software utilizes user-friendly interfaces to simplify your banking needs, ensuring that your financial management is both effortless and efficient.

Our commitment is to continuously enhance customer satisfaction by digitalizing banking operations, streamlining processes, and offering simple, yet powerful banking solutions right at your fingertips.

## System Requirements
To ensure smooth operation of the TBD Bank application, please ensure your system meets the following requirements:

- **Java Development Kit**: Amazon JDK 11 (version 11.0.17-amzn)
- **Angular CLI**: Version 15.1.3
- **Node.js**: Version 16.13.0
- **Integrated Development Environment (IDE)**: Intellij Ultimate
- **Docker**:
  - **Redis**: Used for data caching (Port 6379)
  - **RabbitMQ**: Acts as a Message Queue Broker

## Building and Running the Application
Follow these steps to set up and run the TBD Bank application:

1. **Backend Configuration**:
   - Update the username and password for the database in `application.properties`.
   - In the project's root directory, execute:
     - For Linux: `./gradlew clean build`
     - For Windows: `gradlew clean build`
   - This builds the backend project.

2. **Frontend Setup**:
   - Navigate to the `tbdfrontend` directory.
   - Run `npm install -g @angular/cli` (needed only after the first clone to install Angular packages).
   - Execute `npm install` to install required Node modules.
   - Start the frontend server with `ng serve --port 8080`.

3. **Accessing the Application**:
   - Open your web browser and go to `http://localhost:8080/`.
   - The application runs on server port 8080.

## Starting Redis
To use Redis for data caching, follow these instructions:

1. Install and start the Docker Desktop Application.
2. Pull the Redis image (only required during initial setup): `docker pull redis/redis-stack-server:latest`.
3. Configure and run Redis on PORT 6379: `docker run -d --name redis-stack -p 6379:6379 redis/redis-stack-server:latest`.
4. To view all running containers: `docker ps -a`.

## Running the Application Using Automated Bash Script
For a simplified setup, use the provided Bash script:

1. Update the database username and password in `application.properties`.
2. Ensure Redis is running on port 6379.
3. In the project root directory, execute `bash start.sh`.

You can now view and interact with the TBD Bank application in your browser.

Enjoy the convenience and efficiency of TBD Bank's Digital Banking Application!
