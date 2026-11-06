#!/bin/sh

IMAGE="com.carlosarroyoam/jee-user-service"
CONTAINER="jee-user-service"

# Get the version from pom.xml
VERSION=$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout)

# If version is empty, exit
if [ -z "$VERSION" ]; then
  echo "ERROR: Could not retrieve project version."
  exit 1
fi

# Build Docker image and run container
docker build -t "$IMAGE:$VERSION" . || exit 1
docker container rm -f "$CONTAINER" >/dev/null 2>&1 || true
docker run --detach -p 8081:8080 -p 4849:4848 --name "$CONTAINER" "$IMAGE:$VERSION" || exit 1
