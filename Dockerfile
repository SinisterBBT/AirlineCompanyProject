FROM openjdk:11
EXPOSE 8080
ARG JAR_FILE=mainApp/build/libs/*.jar
COPY ${JAR_FILE} mainApp.jar
ENTRYPOINT ["java","-jar","mainApp.jar"]