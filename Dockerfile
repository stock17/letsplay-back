FROM openjdk:11
RUN mkdir /opt/letsplay
WORKDIR /opt/letsplay
COPY target/letsplay-0.1-SNAPSHOT.jar /opt/letsplay/app.jar
EXPOSE 8080 27017
ENTRYPOINT ["java","-jar","app.jar"]