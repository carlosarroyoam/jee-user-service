# =========================
# Build stage
# =========================
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /build

COPY pom.xml ./

RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn package -DskipTests -B

# =========================
# Runtime stage
# =========================
FROM airhacks/glassfish

COPY --from=build /build/target/jee-user-service.war ${DEPLOYMENT_DIR}
