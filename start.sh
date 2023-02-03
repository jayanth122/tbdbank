#! /bin/bash
#./gradlew -stop ; ./gradlew bootrun &
#cd tbdfrontend || exit ; ng serve --port 8080



#Build frontend
AngularBuild(){
    cd tbdfrontend
    echo "building angular"
    npm cache clean --force
    npm install
    npm run build   --dev
    cd ..

    
}

BuildDocker(){
    cd docker/dockercompose
    docker-compose build --no-cache --progress=plain
    docker compose up -d

}

AngularBuild
BuildDocker
