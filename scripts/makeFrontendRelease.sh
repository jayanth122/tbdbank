ssh -i santhoshpvtkey.txt ubuntu@132.145.103.186 'cd /home/ubuntu/tbdbank/tbdbank &&
 git checkout main &&
  git fetch && git pull &&
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