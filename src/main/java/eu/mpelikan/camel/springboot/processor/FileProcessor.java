package eu.mpelikan.camel.springboot.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.io.InputStream;

public class FileProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {

        InputStream is = this.getClass().getClassLoader()
                .getResourceAsStream("files/file.pdf");
        exchange.getIn().setBody(is);
    }
}
