FROM amazoncorretto:11

LABEL maintainer="smatla@uwaterloo.ca"

CMD ["--name", "tbdBank-container"]

# Copy project files and Gradle wrapper
COPY . /app
COPY gradlew /app/gradlew
COPY gradle /app/gradle

# Set working directory to project directory
WORKDIR /app

# Run Gradle build
RUN chmod +x ./gradlew && \
    ./gradlew build

RUN mkdir -p /var/config
COPY tbdbank.properties /var/config/tbdbank.properties

# Copy the jar file to the app directory
RUN mkdir -p /app && \
    cp ./build/libs/TBDBank.jar /app/TBDBank.jar

# Set default command to run when starting the container
CMD ["java", "-jar", "/app/TBDBank.jar"]
