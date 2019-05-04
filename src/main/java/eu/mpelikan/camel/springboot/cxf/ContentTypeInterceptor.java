package eu.mpelikan.camel.springboot.cxf;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.jaxrs.interceptor.JAXRSOutInterceptor;
import org.apache.cxf.jaxrs.utils.HttpUtils;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageContentsList;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Interceptor to replace Content-Type and remove all original request Headers.
 * */
public class ContentTypeInterceptor extends AbstractPhaseInterceptor<Message> {

    public ContentTypeInterceptor(String phase) {
        super(Phase.MARSHAL);
        getBefore().add(JAXRSOutInterceptor.class.getName());
    }

    public void handleMessage(Message message) throws Fault {
        try {
            MessageContentsList objs = MessageContentsList.getContentsList(message);

            if (objs != null && objs.size() != 0) {
                Object responseObj = objs.get(0);
                javax.ws.rs.core.Response response = null;
                if (responseObj instanceof javax.ws.rs.core.Response) {
                    response = (Response) responseObj;

                    // protocol headers
                    MultivaluedMap<String, Object> responseHeaders = response.getMetadata();

                    // map is now effectively Map<String, List<String>>
                    HttpUtils.convertHeaderValuesToString(responseHeaders,false);

                    // flatten the map into Map<String, String>
                    Map<String, String> flattenedHeaders = new HashMap<>();
                    for (Map.Entry<String, List<Object>> e : responseHeaders.entrySet()){
                        if (e.getKey().startsWith("_")) {
                            flattenedHeaders.put(
                                    e.getKey().substring(1),
                                    e.getValue()
                                            .stream()
                                            .map(v -> (String) v)
                                            .collect(Collectors.joining(",")));
                        }
                    }

                    responseHeaders.clear();
                    flattenedHeaders.forEach((k,v)->responseHeaders.add(k,v));

                    // Override Content-Type in case request Content-Type was set
                    if (responseHeaders.containsKey("CustomContentType")) {
                        responseHeaders.put("Content-Type", responseHeaders.get("CustomContentType"));
                        responseHeaders.remove("CustomContentType");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }



    }
}
