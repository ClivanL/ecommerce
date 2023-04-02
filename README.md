# ecommerce
creating an ecommerce app as part of learning springboot and angular


## learning intent
Modify original intent for front-end react. 
To learn microservices- deploy on docker

## application checklist (backend)
App users(basic) to be able to:
- CRU user
- CRUD item 
- CR purchaseLog 
- CRUD cart 

App users(superuser)
- CRUD user
- CRUD item
- CRUD purchaseLog
- CRUD cart

### to implement
- JWT

### progress
- Run a single container in docker
- Running multiple containers using docker-compose.yml (setting conditions for individual container run depending on status of another container)
- Communication between different containers using exchange and postForObject, postForEntity etc
- Communication with front end  (CORS error)
- Setting up session creation, validation and invalidation
- Error handling, throwing and handling errors (in progress)
- Meet requirements set (in progress)

#### Steps
https://www.baeldung.com/dockerizing-spring-boot-application

- 'localhost' on application.properties to be replaced with 'host.docker.internal' 
- To run mvn clean compile -DskipTests (running with test will lead to compilation failure due to the changes in datasource url.
- Amendments to Dockerfile:
FROM openjdk:17-jdk-slim
MAINTAINER clivan
COPY target/ecommerce-0.0.1-SNAPSHOT.jar docker-ecommerce-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/docker-ecommerce-0.0.1-SNAPSHOT.jar"]
- Docker file to be saved in 1 level below target.

##### learning opportunities by chance (not mentioned in progress)
- configured windows, ubuntu and zsh

