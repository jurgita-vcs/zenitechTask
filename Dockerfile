FROM openjdk:8
ADD target/zenitechTask-0.1.jar zenitechTask-0.1.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "zenitechTask-0.1.jar"]