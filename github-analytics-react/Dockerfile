# pull official base image
FROM node:13.12.0-alpine

# set working directory
WORKDIR /app

# add /app/node_modules/.bin to $PATH
ENV PATH /app/node_modules/.bin:$PATH
EXPOSE 3000

# install app dependencies
COPY package.json ./
COPY package-lock.json ./
RUN npm install

# add app
COPY . ./

ENV EXPRESS_APP_SERVER_PORT 3000


# start app
CMD ["npm", "start"]
