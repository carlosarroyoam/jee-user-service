# =========================
# Build stage
# =========================
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /build

COPY pom.xml ./

RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn package -DskipTests -B

# =========================
# Runtime stage
# =========================
FROM payara/server-full:6.2025.11-jdk21

ENV JAVA_OPTS="-Xmx2g -Xms1g -XX:+UseG1GC"

COPY --from=build /build/target/jee-user-service.war $DEPLOY_DIR/
