# Build
mvn clean package && docker build -t com.carlosarroyoam/jee-user-service .

# RUN
docker rm -f jee-user-service || true && docker run -d -p 8080:8080 -p 4848:4848 --name jee-user-service com.carlosarroyoam/jee-user-service 