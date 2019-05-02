# About
This example shows changed behaviour/bug after when using Camel CXFRS in new version of Camel 2.20.1


## Run the test example
- run class src/main/java/eu.mpelikan.camel.springboot.MySpringBootApplication
- invoke POST endpoint

`curl -d "<test>" -H "Content-Type: application/xml" -X POST http://localhost:5555/rest/mytest`

## Expected behaviour (as in Camel 2.17.6)
```
Response HTTP 200
Success
```


## Reality (as in Camel 2.20.1 and higher)
Exception is thrown:
javax.ws.rs.client.ResponseProcessingException: No message body reader has been found for class javax.ws.rs.core.Response, ContentType: application/xml

### Dependencies Camel 2.23.2
```
camel.version = 2.23.2
cxf.version = 3.2.7
spring.boot-version = 2.1.0.RELEASE
jetty.server = 9.4.12.v20180830
```
### Dependencies Camel 2.20.1
```
camel.version = 2.20.1
cxf.version = 3.2.1
jetty.server = 9.4.6.v20170531
jetty.http = 9.4.6.v20170531
spring.boot-version = 1.5.8.RELEASE
```

### Dependencies Camel 2.17.6
```
camel.version = 2.17.6
cxf.version = 3.1.9
jetty.server = 9.2.15.v20160210
jetty.http = 9.2.17.v20160517
spring.boot-version = 1.3.7.RELEASE
```