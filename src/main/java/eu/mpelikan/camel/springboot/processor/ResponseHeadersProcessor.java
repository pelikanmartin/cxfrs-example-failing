package eu.mpelikan.camel.springboot.processor;


import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ResponseHeadersProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        final List<String> headerNames = (List<String>)exchange.getProperty("headerNames");
        // XX-* headers will be used later for separating response and request headers
        Map<String, Object> newHeaders = exchange
                .getIn()
                .getHeaders()
                .entrySet()
                .stream()
                .filter(e -> !headerNames.contains(e.getKey()))
                .collect(Collectors.toMap(
                        entry->"XX-"+entry.getKey(),
                        entry->entry.getValue()
                ));
        // maybe this intermediary step is not needed as we use XX- prefix
        newHeaders.put("XX-Content-Type", exchange.getIn().getHeaders().get("CustomContentType"));
        newHeaders.remove("XX-CustomContentType");
        /*
            If original object (ResponseImpl object) is set as a body and then returned to the caller,
            ResponseImpl contains original REQ/RESP mix of headers. Content-Type is always of Request one.
            This code tries to filter request and cxf headers and returns only valid headers to the caller.
        */
        /*Response.ResponseBuilder builder = Response.status((Integer) exchange.getIn().getHeaders().get(Exchange.HTTP_RESPONSE_CODE));
        builder.type((String) exchange.getIn().getHeaders().get(Exchange.CONTENT_TYPE));
        builder.entity(exchange.getIn().getBody());
        Response response = builder.build();
        response.getHeaders().putAll(
                newHeaders.entrySet().stream()
                .collect(Collectors.toMap(
                        entry->entry.getKey(),
                        entry-> Arrays.asList(entry.getValue())
                        )
                )
        );*/

        exchange.getOut().setBody(exchange.getIn().getBody());
        // Even with empty Headers headers are present in response to caller
        // if headers are not empty, they will be added to jetty.server.Response fields
        exchange.getOut().setHeaders(newHeaders);


    }
}
