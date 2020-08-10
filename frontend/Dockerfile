# FROM node:lts-alpine

# # make the 'app' folder the current working directory
# WORKDIR /app

# # copy both 'package.json' and 'package-lock.json' (if available)
# COPY package*.json ./

# # install project dependencies...,,,
# RUN yarn install

# # copy project files and folders to the current working directory (i.e. 'app' folder)
# COPY . .

# EXPOSE 8100
# CMD [ "yarn", "run", "serve", "--port", "8100" ]

# build stage
# FROM node:lts-alpine as build-stage
# WORKDIR /app
# COPY package*.json ./
# RUN yarn install
# COPY . .
# RUN yarn run build

# # production stage
# FROM nginx:stable-alpine as production-stage
# COPY --from=build-stage /app/dist /usr/share/nginx/html
# EXPOSE 80
# CMD ["nginx", "-g", "daemon off;"]