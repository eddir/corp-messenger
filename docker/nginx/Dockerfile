### STAGE 1: Build APP ###
FROM node:lts-alpine as builder-app

RUN npm config set unsafe-perm true && mkdir /app

COPY ./frontend /app/frontend

WORKDIR /app/frontend

RUN npm install
RUN npm run build

### STAGE 2: Nginx ###
FROM nginx:alpine as main
LABEL maintainer="ea@rostkov.me"

ENV TZ=UTC+3
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

## Copy our default nginx config
COPY ./docker/nginx/ /etc/nginx/conf.d/
COPY --from=builder-app /app/frontend/dist /app/vue-client

CMD ["nginx", "-g", "daemon off;"]
EXPOSE 443 80