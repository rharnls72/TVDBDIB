#!/bin/bash

TITLE=tvility
# FRONT_IMG=${TITLE}/front
BACK_IMG=${TITLE}/back
# FRONT_DOCKER=${TITLE}-frontend
BACK_DOCKER=${TITLE}-backend

pwd

# sudo docker rmi -f $FRONT_IMG || true
sudo docker rmi -f $BACK_IMG || true

# sudo docker rm -f $FRONT_DOCKER || true
sudo docker rm -f $BACK_DOCKER || true

cd backend
pwd

./mvnw package
sudo docker build -t $BACK_IMG .
# sudo docker run -it --name $BACK_DOCKER --network="host" -d $BACK_IMG

# cd ../frontend
# pwd
# sudo docker build -t $FRONT_IMG .
# sudo docker run --name $FRONT_DOCKER -p 80:8100 -d $FRONT_IMG