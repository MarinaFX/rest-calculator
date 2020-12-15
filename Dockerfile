FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD build/libs/rest-calculator-0.0.1-SNAPSHOT.jar /home/MyApp/App.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "/home/MyApp/App.jar"]