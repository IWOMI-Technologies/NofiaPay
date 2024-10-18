#!/bin/bash

## Stop and remove specific containers related to this deployment if they are running
echo "******* STOPPING AND REMOVING EXISTING CONTAINERS *******"
docker stop nofia-container db_nofia 2>/dev/null || true
docker rm nofia-container db_nofia 2>/dev/null || true

## create network
#echo "******* ATTEMPT NETWORK CREATION *******"
#docker network create nofia_network

## run nofia db
#echo "******* RUNNING NOFIA DB *******"
#docker run -d \
#  --name db_nofia \
#  --network nofia_network \
#  -e MYSQL_ROOT_PASSWORD=root \
#  -e MYSQL_DATABASE=nofia \
#  -p 3308:3306 \
#  mysql:8.0-oracle
#
### recreate package (jar file)
#echo "******* GENERATING JAR FILE *******"
#./mvnw clean package -DskipTests

## build nofia image
echo "******* BUILDING NOFIA IMAGE *******"
docker build -t nofia-app .

## run Nofia MS
#echo "******* RUNNING OR STARTING NOFIA CONTAINER *******"
#docker run -d \
#  --name nofia-container \
#  --network nofia_network \
#  -p 8082:8082 \
#  nofia-app

## generate tar file
echo "******* CREATING TAR FILE *******"
docker save nofia-app > nofia.tar