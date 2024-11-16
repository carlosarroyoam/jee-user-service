#!/bin/sh
mvn clean package && docker build -t com.carlosarroyoam/jee-user-service:latest .
docker container rm -f jee-user-service || true && docker container run -dp 8081:8080 -p 4849:4848 --name jee-user-service com.carlosarroyoam/jee-user-service:latest
