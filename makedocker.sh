#!/bin/bash

TITLE=tvility

# FRONT_IMG=${TITLE}/front
BACK_IMG=${TITLE}/back
AI_IMG=${TITLE}/ai

# FRONT_DOCKER=${TITLE}-frontend
BACK_DOCKER=${TITLE}-backend
AI_DOCKER=${TITLE}-ai

pwd

# sudo docker rmi -f $FRONT_IMG || true
sudo docker rmi -f $BACK_IMG || true
sudo docker rmi -f $AI_IMG || true

# sudo docker rm -f $FRONT_DOCKER || true
sudo docker rm -f $BACK_DOCKER || true
sudo docker rm -f $AI_DOCKER || true

cd backend
pwd

./mvnw package
sudo docker build -t $BACK_IMG .
sudo docker run --name $BACK_DOCKER --network="host" -v tvility:/tvility -d $BACK_IMG

cd ../frontend
pwd

yarn install
yarn run build

# sudo docker build -t $FRONT_IMG .
# sudo docker run --name $FRONT_DOCKER -p 80:80 -d $FRONT_IMG

cd ../ai
pwd

sudo docker build -t $AI_IMG .
sudo docker run --name $AI_DOCKER --network="host" -v tvility:/tvility -d $AI_IMG