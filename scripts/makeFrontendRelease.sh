ssh -i santhoshpvtkey.txt ubuntu@132.145.103.186 'cd /home/ubuntu/tbdbank/tbdbank &&
 git checkout main &&
  git fetch && git pull &&
   cd tbdfrontend
   container_id=$(docker ps -q --filter "publish=8080")

  # Stop the container
  if [ ! -z "$container_id" ]; then
    docker stop "$container_id"
    docker rm "$container_id"
  fi &&
   docker build -t tbdbankFrontend-image . &&
    docker run -d --net san_network --name tbdbankFrontend-container -p 8080:8080 tbdbankFrontend-image'