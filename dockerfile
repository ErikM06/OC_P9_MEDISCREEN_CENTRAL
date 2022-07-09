FROM openjdk:11
WORKDIR /usr/app
COPY build/libs/central-0.0.1-SNAPSHOT.jar mediscreen-gateway.jar
CMD  java -jar mediscreen-gateway.jar