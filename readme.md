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
```

### Dependencies Camel 2.17.6
```
camel.version = 2.17.6
cxf.version = 3.1.9
jetty.server = 9.2.15.v20160210
```