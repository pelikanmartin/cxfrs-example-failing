package eu.mpelikan.camel.springboot.cxf;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;

import java.util.Map;
import java.util.TreeMap;


/**
 * Interceptor marks response headers with _ prefix for further processing
 * */
public class InitialHeaderInInterceptor extends AbstractPhaseInterceptor<Message> {
    public InitialHeaderInInterceptor(String phase) {
        super(Phase.READ);
    }

    @Override
    public void handleMessage(Message message) throws Fault {
        message.getAttachments();

        Map<String, Object> headers = (Map<String, Object>) message.get(Message.PROTOCOL_HEADERS);
        Map<String, Object> copiedHeaders = new TreeMap<>(headers);
        copiedHeaders.entrySet()
                .stream()
                .filter(e->!e.getKey().startsWith("_"))
                .forEach(e->headers.put("_"+e.getKey(),e.getValue()));
    }
}
