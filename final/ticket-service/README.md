#Project solution for Java in practice course

The task was to implement a simple CL based application, which simulates a simple cinema database system.

##Usage

There are 3 ways to run the program. 
* If you want the program to use in-memory database, use the command below.
```
mvn spring-boot:run -D"spring-boot.run.profiles"=ci
```
* If you want the program to use mySQL database, use the command below. This is the default option
```
mvn spring-boot:run
```
* Otherwise, use IDE to run and compile.

##Database
The program uses mySQL persistent database by default. To use it, you need a Docker. After that, follow the instructions below.

* There is a `docker-compose.yml` file in the project folder, it is for starting up a MySql server, and a phpMyAdmin service.
* To start the MySql server, and the phpMyAdmin service, please execute the following command: `docker-compose up`
* To connect to the phpMyAdmin, please visit the localhost:8080 URL and login into the service.

##Acceptance test score

The program tested with the following integration test profiles :

* grade2-requirement - Succeeded
* grade3-requirement - Succeeded
* grade4-requirement - Succeeded
* grade5-requirement - Failed
