@echo off
setlocal

set "IMAGE=com.carlosarroyoam/jee-user-service"
set "CONTAINER=jee-user-service"

REM Get the version from pom.xml
for /f "delims=" %%a in ('mvn help:evaluate "-Dexpression=project.version" -q -DforceStdout') do (
  set "VERSION=%%a"
)

REM If version is empty, exit
if not defined VERSION (
  echo ERROR: Could not retrieve project version.
  exit /b 1
)

REM Build Docker image and run container
docker build -t "%IMAGE%:%VERSION%" . || exit /b 1
docker container rm -f "%CONTAINER%" >nul 2>&1
docker run --detach -p 8081:8080 -p 4849:4848 --name "%CONTAINER%" "%IMAGE%:%VERSION%" || exit /b 1

endlocal
exit /b 0
