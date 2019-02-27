##The purpose of this project

The purpose of this project is to perform integration testing of all services inside company. The project contains the following functionality:
- Clients for backend services
- DTOs
- Abstract/common validators
- Utils to generate some data
- Packages with tests for each service

## How to run tests

```
mvn clean test -Pqa -Dgroups="ufo-service"
```
where:
- Pqa - maven profile, could be dev, qa
- Dgroups="ufo-service" - list of services should be tested

To work with tests in IDEA should install lombok plugin.

## How to generate allure-report locally

Generate result after test run is finished.
```
allure generate allure-results/
```