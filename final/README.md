# Requirements for the final task

In this task, you are going to create an interactive command line 
cinema ticket booking application.

## Requirements towards the app:

### For grade 2:
1. The app is available in a public repo.
1. The maven project for the app can be built. For this, you should have:
    1. A compilable source code
    1. All the tests should pass
    1. At least 40% of the lines and branches covered by unit tests - check your JaCoCo coverage reports.
1. The app implements the commands specified in the feature files in 
the ticket-service-acceptance-tests/src/test/resources/features directory,
where the scenario is marked as `@required`
    1. `mvn clean verify` on the ticket-service-acceptance-tests module
    should be successful.
1. The class structure is designed with extensibility in focus. You should be able to reason about your design decisions.
1. Clean code and OOP principles are considered.
1. The app uses Spring to manage DI
1. The app uses Spring Data JPA with an in-memory database

### For grade 3:
1. Every requirement for grade 2 is fulfilled.
1. The app uses Spring Shell
1. At least 50% of the lines and branches are covered by unit tests
1. There are no checkstyle errors. Suppressions may be used when reasonable.

### For grade 4:
1. Every requirement for grade 3 is fulfilled.
1. The app allows configuring the database using Spring profiles. The 'ci' profile uses an in-memory database.
If no profile is specified, a persistent database is used. TODO: Update `com.training.epam.ticketservice.at.ProcessUnderTest` to use the profile.
1. The app implements the commands specified in the feature files where the scenario is marked as `@optional`.
    1. `mvn clean verify -Pverify-optional` on the ticket-service-acceptance-tests module 
    should be successful.

### For grade 5:
1. Every requirement for grade 4 is fulfilled.
1. At least 70% of the lines and branches are covered by unit tests.