# ecommerce
creating an ecommerce app as part of learning springboot and angular


## intent
Modify original intent for front-end react. 
To learn microservices- deploy on docker

### progress
Run a single container in docker

#### Steps
https://www.baeldung.com/dockerizing-spring-boot-application

-'localhost' on application.properties to be replaced with 'host.docker.internal' 
-To run mvn clean compile -DskipTests (running with test will lead to compilation failure due to the changes in datasource url.
-Amendments to Dockerfile:
FROM openjdk:17-jdk-slim
MAINTAINER clivan
COPY target/ecommerce-0.0.1-SNAPSHOT.jar docker-ecommerce-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/docker-ecommerce-0.0.1-SNAPSHOT.jar"]
-Docker file to be saved in 1 level below target.



