FROM openjdk:15-jdk-alpine
COPY build/libs/*.jar ReadingIsGood.jar
ENTRYPOINT ["java","-jar","/ReadingIsGood.jar"]