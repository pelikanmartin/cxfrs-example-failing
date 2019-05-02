package eu.mpelikan.camel.springboot.processor;


import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ResponseHeadersProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        final List<String> headerNames = (List<String>)exchange.getProperty("headerNames");
        Map<String, Object> newHeaders = exchange
                .getIn()
                .getHeaders()
                .entrySet()
                .stream()
                .filter(e -> !headerNames.contains(e.getKey()))
                .collect(Collectors.toMap(
                        entry->entry.getKey(),
                        entry->entry.getValue()
                ));
        exchange.getOut().setHeaders(newHeaders);
        exchange.getOut().setBody(exchange.getIn().getBody());


    }
}
