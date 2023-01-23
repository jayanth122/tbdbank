#! /bin/bash
./gradlew -stop ; ./gradlew bootrun &
cd frontend || exit ; npm run serve