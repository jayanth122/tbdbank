ssh -i ~/Documents/Waterloo\ Academic/ece651/santhoshpvtkey.txt ubuntu@132.145.103.186 'cd /home/ubuntu/tbdbank/tbdbank &&
 git checkout main &&
  git fetch && git pull &&
   cp -f /var/config/tbdbank.properties tbdbank.properties &&
   container_id=$(docker ps -q --filter "publish=8081")

  # Stop the container
  if [ ! -z "$container_id" ]; then
    docker stop "$container_id"
    echo "Docker container stopped successfully"
  else
    echo "No Docker container running on port 8081"
  fi &&
   docker build -t tbdbank-image --build-arg REDIS_HOST=redis-stack . &&
    docker run -d --net san_network -p 8081:8081 tbdbank-image'
