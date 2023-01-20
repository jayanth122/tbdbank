# Description
TBD bank software is an easy and convenient system which helps customers to navigate easily using user friendly interfaces.  It aims for continuously improving customer experience and satisfaction along with streamlining banking operations digitally and making banking operations simple for customers

## Add your files
cd existing_repo  
git remote add origin https://gitlab.com/tbdbank/tbdbank.git   
git branch -M main   
git push -uf origin main

## How to Install and Run the project
First the git repo should be cloned to the local system.
We need to build the project with gradle clean build which will delete build directory, all your classes will be removed for a fresh compile.
Then we need to install npm  package and dependencies. Once the node server is running we can view the project in browser using the local host link.

Below are the commands which can be used to run the project on respective Operating systems:
## Windows:
gradle clean build   
cd frontend  
npm install  
npm run serve
Open the Local host Link on browser : http://localhost:8080/    

Now you should be able to view the project on browser

## MacOS:
./gradle clean build   
cd frontend   
npm install   
npm run serve   
Open the Local host Link on browser : http://localhost:8080/

Now you should be able to view the project on browser