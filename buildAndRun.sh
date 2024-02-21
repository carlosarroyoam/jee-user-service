#!/bin/sh
mvn clean package && docker build -t com.carlosarroyoam/jee-user-service .
docker container rm -f jee-user-service || true && docker container run -dp 8080:8080 -p 4848:4848 --name jee-user-service com.carlosarroyoam/jee-user-service
