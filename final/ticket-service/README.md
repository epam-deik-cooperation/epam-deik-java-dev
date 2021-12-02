#Project solution for Java in practice course

The task was to implement a simple CL based application, which simulates a simple cinema system.

##Usage

There are 4 ways to run the program. 
* If you want the program to use in-memory database, use the command below.
```
mvn spring-boot:run -D"spring-boot.run.profiles"=ci
```
* If you want the program to use in-memory database with pre-initialized tables, use the command below.
```
mvn spring-boot:run -D"spring-boot.run.profiles"=init
```
* If you want the program to use persistent database, use the command below.
```
mvn spring-boot:run
```
* Otherwise, use IDE to run and compile.

##Database
The program uses H2 persistent database by default. To use it, follow the instructions below:

* You can connect to the database with `http://localhost:8090/h2` URL. 
* You will see a login page. Insert the right server address which can be
  * `jdbc:h2:file:./data/db` if the application started with persistent data.
  * `jdbc:h2:mem:ticket-service` if the application started with in-memory database.
* Insert the correct username, password, and you will see the database.

##Acceptance test score

The program was tested with the following integration test profiles :

* ``grade2-requirement`` - Succeeded
* ``grade3-requirement`` - Succeeded
* ``grade4-requirement`` - Succeeded
* ``grade5-requirement`` - Failed
