FROM openjdk:11
EXPOSE 8080
ARG JAR_FILE=mainApp/build/libs/mainApp.jar
COPY ${JAR_FILE} mainApp/
ENTRYPOINT ["java","-jar","mainApp/mainApp.jar"]