FROM node:16

WORKDIR /frontendApp

COPY package*.json ./

RUN npm install -g @angular/cli
RUN npm install


COPY . .
RUN cd /frontendApp
RUN ng build
CMD ["docker", "cp", "tbdbankfrontend-container:frontendApp/dist", "/usr/share/nginx/html"]
