FROM node:16

WORKDIR /frontendApp

COPY package*.json ./

RUN npm install -g @angular/cli
RUN npm install


COPY . .
RUN ng build
CMD ["sudo", "cp", "-R", "dist/*", "/usr/share/nginx/html"]
