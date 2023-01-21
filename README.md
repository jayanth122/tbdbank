# Description
TBD bank is a digital banking software is an easy and convenient system which helps customers to navigate easily using user friendly interfaces.  It aims for continuously improving customer experience and satisfaction along with streamlining banking operations digitally and making banking operations simple for customers


## Requirements
- Amazon Jdk 11 11.0.17-amzn
- Intellij Ultimate --> IDE
- Docker
  - Redis --> For data caching
- RabbitMQ -->  Message Queue Broker

## How to Build and Run the project

- In project root directory run ./gradlew clean build(linux) OR gradle clean build(windows) --> To Build the backend project
- Switch to frontend directory 
  - npm install --> Only run the first time after clone, this will install npm
  - npm run serve --> To start the frontend server
- Open the frontend page on browser : http://localhost:8080/ --> 8080 is the server port


Now you should be able to view the project on browser