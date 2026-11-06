# Develop

To start developing you will need to install the following tools on your dev local machine:

	- Java 21 Development Kit (JDK 21)
	- Payara Server 6 Full (Jakarta EE 10)
	- MySQL 8.0.33 or higher
	- Docker

If you are using Eclipse IDE you'll need to install the Payara Tools plugin (available on the Eclipse Marketplace) to
register and manage a
Payara Server 6 instance from the IDE. See the Payara documentation for the setup details.

[Payara Documentation](https://docs.payara.fish)

# Build

docker build -t com.carlosarroyoam/jee-user-service:1.0-SNAPSHOT .

# Run

docker container rm -f jee-user-service || true &&
docker run -dp 8081:8080 -p 4849:4848 --name jee-user-service com.carlosarroyoam/jee-user-service:1.0-SNAPSHOT
