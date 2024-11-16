# Develop
To start developing you will need to install the following tools on your dev local machine:
	
	- Java 8 Development Kit (JDK 8)
	- GlassFish Server 5.1 Full Platform
	- MySQL 8.0.33 or higher
	- Docker

If you are using Eclipse IDE you'll need to install GlassFish tools. You can follow the Baeldung: Configuring the GlassFish Server in Eclipse guide on
the following link.

[Configuring the GlassFish Server in Eclipse](https://www.baeldung.com/eclipse-glassfish-setup)

# Build
mvn clean package && docker build -t com.carlosarroyoam/jee-user-service:latest .

# Run
docker container rm -f jee-user-service || true &&
docker container run -dp 8081:8080 -p 4849:4848 --name jee-user-service com.carlosarroyoam/jee-user-service:latest
