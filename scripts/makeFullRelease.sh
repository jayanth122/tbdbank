ssh -i santhoshpvtkey.txt ubuntu@132.145.103.186  'cd /home/ubuntu/tbdbank/tbdbank &&
 git checkout main &&
  git fetch && git pull &&
   cp -f /var/config/tbdbank.properties tbdbank.properties &&
   container_id=$(docker ps -q --filter "publish=8081")

  # Stop the container
  if [ ! -z "$container_id" ]; then
    docker stop "$container_id"
    docker rm "$container_id"
  fi &&
  docker start mysql-container redis-stack
  mysql_port=$(docker inspect -f "{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}" mysql-container)
  mysql_url="jdbc:mysql://${mysql_port}:3306/tbdbank"
   docker build -t tbdbank-image --build-arg REDIS_HOST=redis-stack --build-arg SPRING_DATASOURCE_URL=${mysql_url} . &&
    docker run --log-driver json-file --log-opt max-size=10m --log-opt max-file=3 -v /var/tbd651/logs/bank.log:/var/tbd651/logs/bank.log \
     -d --net san_network -p 8081:8081 tbdbank-image

     # Frontend Release
     cd /usr/share/nginx/html
            if [ -d "tbdfrontend" ]; then
              echo "Directory exists, deleting..."
              sudo rm -r tbdfrontend
            fi
        cd /home/ubuntu/tbdbank/tbdbank/tbdfrontend &&

        container_id=$(docker ps -aq -f name=tbdbankfrontend-container)

       # Stop the container
       if [ ! -z "$container_id" ]; then
         docker stop "$container_id"
         docker rm "$container_id"
       fi &&
        docker build -t tbdbankfrontend-image . &&
        docker run -d -p 8080:80 --name tbdbankfrontend-container tbdbankfrontend-image
        sudo docker cp tbdbankfrontend-container:/frontendApp/dist/tbdfrontend /usr/share/nginx/html
        docker system prune -f'