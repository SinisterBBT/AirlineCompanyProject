#! /bin/bash
set -e

imageTag=$1
if [ -z "$1" ]
	then
		echo No image tag provided. Latest will be used
		imageTag=latest
fi

(exec "${BASH_SOURCE%/*}/mainApp/pushImage.sh" $imageTag)