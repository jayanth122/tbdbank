#! /bin/bash
./gradlew -stop ; ./gradlew bootrun &
cd tbdfrontend || exit ; ng serve --port 8080