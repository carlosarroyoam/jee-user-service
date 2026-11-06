#!/bin/sh

IMAGE="com.carlosarroyoam/jee-user-service"
CONTAINER="jee-user-service"

# Checks if Maven and Docker are installed
for cmd in mvn docker; do
  command -v "$cmd" >/dev/null 2>&1 || { 
    echo "ERROR: '$cmd' not installed." >&2; 
    exit 1;
  }
done

# Get the version from pom.xml
VERSION=$(mvn help:evaluate -Dexpression=project.version -q -ntp -B -DforceStdout)

# If version is empty, exit
if [ -z "$VERSION" ]; then
  echo "ERROR: Could not retrieve project version." >&2
  exit 1
fi

# Build Docker image and run container
docker build -t "$IMAGE:$VERSION" . || exit 1
docker container rm -f "$CONTAINER" >/dev/null 2>&1 || true
docker run --detach -p 8081:8080 -p 127.0.0.1:4849:4848 --name "$CONTAINER" "$IMAGE:$VERSION" || exit 1
