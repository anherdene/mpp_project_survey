FROM node:10.15.3

WORKDIR /usr/app

COPY package*.json ./

RUN npm install -qy

COPY . .

RUN touch .env
RUN echo "REACT_APP_WS_URL=http://springappserver:8080" >> .env

EXPOSE 3000

CMD ["npm", "start"]