# Description
TBD bank is a digital banking software is an easy and convenient system which helps customers to navigate easily using user friendly interfaces.  It aims for continuously improving customer experience and satisfaction along with streamlining banking operations digitally and making banking operations simple for customers


## Requirements
- Amazon Jdk 11 11.0.17-amzn
- Angular CLI: 15.1.3  
- Node: 16.13.0
- Intellij Ultimate --> IDE
- Docker
  - Redis --> For data caching --> Port 6379
- RabbitMQ -->  Message Queue Broker

## How to Build and Run the project
- Update username and password for database in application.properties
- In project root directory run --> ./gradlew clean build(linux) OR gradlew clean build(windows) --> To Build the backend project
- Switch to tbdfrontend directory to run the frontend application
  - npm install -g @angular/cli --> Only run the first time after clone, this will install Angular packages
  - npm install (This will install required node modules)
  - ng serve --port 8080 --> To start the frontend server
- Open the frontend page on browser : http://localhost:8080/ --> 8080 is the server port

## How to run application using Automated Bash Script
- Update username and password for database in application.properties
- Make sure redis is running on port 6379
- In project root directory run --> bash start.sh

Now you should be able to view the project on browser