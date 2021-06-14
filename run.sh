#! /bin/bash
set -e

imageTag=$1
if [ -z "$1" ]
  then
    echo "No image tag provided. latest will be used"
    imageTag="latest"
fi

repositoryName=758121016477.dkr.ecr.us-east-2.amazonaws.com/mainapp
imageFullName=$repositoryName:$imageTag

echo [Main App STARTING] building $imageFullName...

echo [Main App] creating jar...

./gradlew clean
./gradlew check
./gradlew bootJar

echo [Main App] creating docker image...
docker build -t $imageFullName "${BASH_SOURCE%/*}"

echo [Main App FINISHED] image has been built

echo [Main App] launching
docker run $imageFullName
