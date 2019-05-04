# About
This example is workaround for Response change of behaviour/bug in camel 2.20.1 onwards. Response Interface in serviceClass stopped working unless cxfrs synchronous=true is set. Also content-type is not set correctly on the way back to the client.
When used Response interface in serviceClass, CXF bounces back also request headers (regardless the version).
Project uses CXF Interceptor to to mark response Headers and then Interceptor/ResponseFilter to filter out all non-prefixed headers.
CXFRS uses Response Interface Service Class. It works only if producer and consumer are both synchronous. This behaviour changed since 2.17.6

## Note
When returned binary attachment,  JAXRSOutInterceptor generates and commits message! We have to modify:
1) response headers in Phase.Marshal
2) response headers in responseFilter - in the middle of JAXRSOutInterceptor processing (filters are provided here)

## Run the test example
- run class src/main/java/eu.mpelikan.camel.springboot.MySpringBootApplication
- invoke POST endpoint and obtain xml response
`curl -d "<test>" -H "Content-Type: application/xml" -X POST http://localhost:5555/rest/mytest`
- invoke GET endpoint and obtain PDF file
`curl -v -d "<test>" -H "Content-Type: application/xml" -X GET http://localhost:5555/rest/mytest`
## Response
```
martin  
url -v -d "<test>" -H "Content-Type: application/xml" -X POST http://localhost:5555/rest/mytest

Note: Unnecessary use of -X or --request, POST is already inferred.
*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to localhost (127.0.0.1) port 5555 (#0)
> POST /rest/mytest HTTP/1.1
> Host: localhost:5555
> User-Agent: curl/7.54.0
> Accept: */*
> Content-Type: application/xml
> Content-Length: 6
>
* upload completely sent off: 6 out of 6 bytes
< HTTP/1.1 200 OK
< Date: Thu, 02 May 2019 20:20:00 GMT
< Pragma: no-cache
< Cache-Control: no-cache
< Content-Type: application/json
< CamelHttpResponseCode: 200
< Connection: keep-alive
< Server: Jetty(9.4.12.v20180830)
< Transfer-Encoding: chunked
<
* Connection #0 to host localhost left intact
```

Response would contain headers like Content-Type (of incoming message) Agent, Accept, Authorization (if present) and so on without filtering request headers.


