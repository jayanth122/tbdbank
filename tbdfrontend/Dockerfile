FROM node:16

WORKDIR /frontendApp

COPY package*.json ./

RUN npm install -g @angular/cli
RUN npm install


COPY . .
RUN ng serve --port 8080
EXPOSE 8080
CMD ["npm", "start"]
