package com.maomao2.simplegateway.http;

import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

public class RequestContext extends ConcurrentHashMap<String, Object> {

    private static Class<? extends RequestContext> contextClass = RequestContext.class;
    private static final ThreadLocal<? extends RequestContext> threadLocal = new ThreadLocal<RequestContext>() {
        protected RequestContext initialValue() {
            try {
                return contextClass.newInstance();
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        };

    };

    public static RequestContext getCurrentContext() {
        return threadLocal.get();
    }

    public void set(String key, Object value) {
        if (value != null) {
            put(key, value);
        } else {
            remove(key);
        }
    }

    public void unset() {
        threadLocal.remove();
    }

    public HttpServletRequest getRequest() {
        return (HttpServletRequest) get("request");
    }

    public void setRequest(HttpServletRequest request) {
        set("request", request);
    }

    public HttpServletResponse getResponse() {
        return (HttpServletResponse) get("response");
    }

    public void setResponse(HttpServletResponse response) {
        set("response", response);
    }

    public void setRequestEntity(RequestEntity<byte[]> requestEntity) {
        set("requestEntity", requestEntity);
    }

    public RequestEntity<byte[]> getRequestEntity() {
        return (RequestEntity<byte[]>) get("requestEntity");
    }

    public void setResponseEntity(ResponseEntity<byte[]> responseEntity) {
        set("responseEntity", responseEntity);
    }

    public ResponseEntity<byte[]> getResponseEntity() {
        return (ResponseEntity<byte[]>) get("responseEntity");
    }

}
