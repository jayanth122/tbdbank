git checkout main

git fetch && git pull

cp -f /var/config/tbdbank.properties tbdbank.properties

docker build -t tbdbank-image .

docker run -p 8081:8081 tbdbank-image
