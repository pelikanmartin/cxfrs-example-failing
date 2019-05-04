## Observations
Code works good in all cases except binary attachment returned. Does not matter what media type is (application/pdf or application/xml)
When debugging code and hit post-logical interceptor, message is already received by curl! Is my post-logical interceptor too late for this use case?

Message is created/serialized in JAXRSOutInterceptor. Headers are taken from MessageContentList.
In case of binary message, message is dispatched in JAXRSOutInterceptor

We can either:
1) create custom interceptor to modify the message before JAXRSOutInterceptor kicks in
2) create responseFilter to filter responseContext and overwrite headers there


### chain of interceptors for working scenarion
268 [qtp2050715938-28] DEBUG org.apache.cxf.interceptor.OutgoingChainInterceptor - Interceptors contributed by bus: [org.apache.cxf.ws.policy.PolicyOutInterceptor@2c4ca991, org.apache.cxf.interceptor.LoggingOutInterceptor@1b83316f]
08:50:07.268 [qtp2050715938-28] DEBUG org.apache.cxf.interceptor.OutgoingChainInterceptor - Interceptors contributed by service: []
08:50:07.268 [qtp2050715938-28] DEBUG org.apache.cxf.interceptor.OutgoingChainInterceptor - Interceptors contributed by endpoint: [org.apache.cxf.interceptor.MessageSenderInterceptor@15e8c709, eu.mpelikan.camel.springboot.cxf.ContentTypeInterceptor@140c4e41, org.apache.camel.component.cxf.jaxrs.CxfRsConsumer$UnitOfWorkCloserInterceptor@6810c593, org.apache.cxf.interceptor.LoggingOutInterceptor@4a8ff46e]
08:50:07.268 [qtp2050715938-28] DEBUG org.apache.cxf.interceptor.OutgoingChainInterceptor - Interceptors contributed by binding: [org.apache.cxf.jaxrs.interceptor.JAXRSOutInterceptor@198ca2b9]
08:50:07.269 [qtp2050715938-28] DEBUG org.apache.cxf.phase.PhaseInterceptorChain - Adding interceptor org.apache.cxf.ws.policy.PolicyOutInterceptor@2c4ca991 to phase setup
08:50:07.269 [qtp2050715938-28] DEBUG org.apache.cxf.phase.PhaseInterceptorChain - Adding interceptor org.apache.cxf.interceptor.LoggingOutInterceptor@1b83316f to phase pre-stream
08:50:07.269 [qtp2050715938-28] DEBUG org.apache.cxf.phase.PhaseInterceptorChain - Adding interceptor org.apache.cxf.interceptor.MessageSenderInterceptor@15e8c709 to phase prepare-send
08:50:07.269 [qtp2050715938-28] DEBUG org.apache.cxf.phase.PhaseInterceptorChain - Adding interceptor eu.mpelikan.camel.springboot.cxf.ContentTypeInterceptor@140c4e41 to phase post-protocol
08:50:07.269 [qtp2050715938-28] DEBUG org.apache.cxf.phase.PhaseInterceptorChain - Adding interceptor org.apache.camel.component.cxf.jaxrs.CxfRsConsumer$UnitOfWorkCloserInterceptor@6810c593 to phase post-logical-ending
08:50:07.269 [qtp2050715938-28] DEBUG org.apache.cxf.phase.PhaseInterceptorChain - Adding interceptor org.apache.cxf.interceptor.LoggingOutInterceptor@4a8ff46e to phase pre-stream
08:50:07.269 [qtp2050715938-28] DEBUG org.apache.cxf.phase.PhaseInterceptorChain - Adding interceptor org.apache.cxf.jaxrs.interceptor.JAXRSOutInterceptor@198ca2b9 to phase marshal
08:50:07.269 [qtp2050715938-28] DEBUG org.apache.cxf.phase.PhaseInterceptorChain - Chain org.apache.cxf.phase.PhaseInterceptorChain@6f04898f was created. Current flow:
setup [PolicyOutInterceptor]
  prepare-send [MessageSenderInterceptor]
  pre-stream [LoggingOutInterceptor]
  marshal [JAXRSOutInterceptor]
  post-protocol [ContentTypeInterceptor]
  prepare-send-ending [MessageSenderEndingInterceptor]
  post-logical-ending [UnitOfWorkCloserInterceptor]
  
 
  
### chain of interceptors for non-working scenario
  setup [PolicyOutInterceptor]
  prepare-send [MessageSenderInterceptor]
  pre-stream [LoggingOutInterceptor]
  marshal [JAXRSOutInterceptor]
  post-protocol [ContentTypeInterceptor]
  prepare-send-ending [MessageSenderEndingInterceptor]
  post-logical-ending [UnitOfWorkCloserInterceptor]
  
  