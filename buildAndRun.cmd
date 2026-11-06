@echo off
setlocal

set "IMAGE=com.carlosarroyoam/jee-user-service"
set "CONTAINER=jee-user-service"

REM Checks if Maven and Docker are installed
for %%c in (mvn docker) do (
  where %%c >nul 2>&1 || (
    echo ERROR: '%%c' not installed. 1>&2
    exit /b 1
  )
)

REM Get the version from pom.xml
for /f "delims=" %%a in ('mvn help:evaluate "-Dexpression=project.version" -q -ntp -B -DforceStdout') do (
  set "VERSION=%%a"
)

REM If version is empty, exit
if not defined VERSION (
  echo ERROR: Could not retrieve project version. 1>&2
  exit /b 1
)

REM Build Docker image and run container
docker build -t "%IMAGE%:%VERSION%" . || exit /b 1
docker container rm -f "%CONTAINER%" >nul 2>&1
docker run --detach --name "%CONTAINER%" -p 8081:8080 -p 127.0.0.1:4848:4848 "%IMAGE%:%VERSION%" || exit /b 1

exit /b 0
