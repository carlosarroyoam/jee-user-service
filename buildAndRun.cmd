@echo off

call mvn clean package -DskipTests -B || exit /b 1

docker build -t com.carlosarroyoam/jee-user-service:latest . || exit /b 1

docker container rm -f jee-user-service >nul 2>&1

docker run -dp 8081:8080 -p 4849:4848 --name jee-user-service com.carlosarroyoam/jee-user-service:latest

exit /b 0
