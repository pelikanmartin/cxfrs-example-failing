package eu.mpelikan.camel.springboot.cxf;

import org.apache.cxf.jaxrs.utils.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Provider
@Priority(value = 10)
public class CustomResponseFilter implements ContainerResponseFilter {

    final private static Logger LOG = LoggerFactory.getLogger(CustomResponseFilter.class.getName());

    public CustomResponseFilter() {
        LOG.trace("CustomResponseFilter created");
    }

    @Override
    public void filter(
            ContainerRequestContext requestContext,
            ContainerResponseContext responseContext
    )
    {
        // This filter replaces the response message body with a fixed string
        if (responseContext.hasEntity()) {
            MultivaluedMap<String, Object> responseHeaders = responseContext.getHeaders();

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
            /*if (responseHeaders.containsKey("CustomContentType")) {
                responseHeaders.put("Content-Type", responseHeaders.get("CustomContentType"));
                responseHeaders.remove("CustomContentType");
            }*/
        }

    }
}