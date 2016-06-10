# Service Acceptance Tests

## Running

### Mocked Service

The test suite can be run by executing the following command:

```
mvn verify
```

This will run the test suite against a mocked server.

### External Service

To run the test suite against an external service, set the environment variables as follows:

```
export MOVIE_SERVICE_HOST=http://some_host
export MOVIE_SERVICE_PORT=some_port
mvn verify
```



## Test Results

The test results can be found in the following folder
 
```
target/cucumber-html-report/index.html
```