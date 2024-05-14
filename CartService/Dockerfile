FROM openjdk:17
LABEL authors="aleksandrbirukov"
ADD build/libs/CartService-0.0.1-SNAPSHOT.jar backend.jar
ENTRYPOINT ["java", "-jar", "backend.jar"]