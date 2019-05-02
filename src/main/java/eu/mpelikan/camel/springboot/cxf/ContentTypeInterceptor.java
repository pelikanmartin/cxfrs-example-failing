package eu.mpelikan.camel.springboot.cxf;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.eclipse.jetty.http.HttpField;
import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.server.Response;

public class ContentTypeInterceptor extends AbstractPhaseInterceptor<Message> {

    public ContentTypeInterceptor(String phase) {
        super(Phase.POST_PROTOCOL);
    }

    public void handleMessage(Message message) throws Fault {
        try {
            Response response = (Response) message.get("HTTP.RESPONSE");
            String contentType = response.getContentType();
            HttpFields fields = response.getHttpFields();
            String customContent = fields.get("CustomContentType");
            fields.remove("Content-Type");

            fields.add(new HttpField("Content-Type","application/json"));

            System.out.println(contentType);

        } catch (Exception e) {
            e.printStackTrace();
        }



    }
}
