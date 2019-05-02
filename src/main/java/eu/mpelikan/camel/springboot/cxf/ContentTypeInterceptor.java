package eu.mpelikan.camel.springboot.cxf;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.eclipse.jetty.http.HttpField;
import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.server.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Interceptor to replace Content-Type and remove all original request Headers.
 * */
public class ContentTypeInterceptor extends AbstractPhaseInterceptor<Message> {

    public ContentTypeInterceptor(String phase) {
        super(Phase.POST_PROTOCOL);
    }

    public void handleMessage(Message message) throws Fault {
        try {
            Response response = (Response) message.get("HTTP.RESPONSE");
            HttpFields fields = response.getHttpFields();

            List<HttpField> tempFields = fields.stream()
                    .filter(field -> field.getName().startsWith("XX-"))
                    .map(field -> new HttpField(
                            field.getName().substring(3),
                            field.getValue()))
                    .collect(Collectors.toList());

            fields.clear();

            tempFields.stream()
                    .forEach(field->fields.add(field));

        } catch (Exception e) {
            e.printStackTrace();
        }



    }
}
