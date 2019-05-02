package eu.mpelikan.camel.springboot.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InitialHeaderProcessor implements Processor {
    public void process(Exchange exchange) throws Exception {
        List<String> headerNames = exchange
                .getIn()
                .getHeaders()
                .entrySet()
                .stream()
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        headerNames.remove("Content-Type");
        headerNames.remove("Content-Length");
        exchange.setProperty("headerNames", headerNames);

    }
}
