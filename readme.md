## Run the test example
- run class src/main/java/eu.mpelikan.camel.springboot.MySpringBootApplication
- invoke POST endpoint

`curl -d "&lt;test/&gt;" -H "Content-Type: application/xml" -X POST http://localhost:5555/rest/mytest`

## Expected behaviour (as in Camel 2.17.6)
```Response HTTP 200
Success```


## Reality (as in Camel 2.20.1 and higher)
Exception is thrown:
javax.ws.rs.client.ResponseProcessingException: No message body reader has been found for class javax.ws.rs.core.Response, ContentType: application/xml

