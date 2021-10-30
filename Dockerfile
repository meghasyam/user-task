# Maven build container 

FROM maven:3.6.3-openjdk-11 AS maven_build

COPY pom.xml /tmp/
COPY src /tmp/src/

WORKDIR /tmp/

RUN mvn package

#pull base image
FROM openjdk

#expose port 8080
EXPOSE 8081

#default command
CMD java -jar /data/hello-world-0.1.0.jar

#copy hello world to docker image from builder image
COPY --from=maven_build /tmp/target/hello-world-0.1.0.jar /data/hello-world-0.1.0.jar