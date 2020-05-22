package com.maomao2.simplegateway.filter.pre;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StreamUtils;

import com.maomao2.simplegateway.filter.Filter;
import com.maomao2.simplegateway.http.RequestContext;

public class RequestWrapperFilter extends Filter {

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return -1;
    }

    @Override
    public void run() {

        String rootURL = "http://localhost:9090";
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest servletRequest = context.getRequest();
        String targetURL = rootURL + servletRequest.getRequestURI();

        RequestEntity<byte[]> requestEntity = null;
        try {
            requestEntity = createRequestEntity(servletRequest, targetURL);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

        context.setRequestEntity(requestEntity);
    }

    private RequestEntity<byte[]> createRequestEntity(HttpServletRequest servletRequest, String targetURL)
            throws IOException, URISyntaxException {
        String method = servletRequest.getMethod();
        HttpMethod httpMethod = HttpMethod.resolve(method);

        MultiValueMap<String, String> headers = createRequestHeaders(servletRequest);
        byte[] body = createRequestBody(servletRequest);

        return new RequestEntity<byte[]>(body, headers, httpMethod, new URI(targetURL));
    }

    private byte[] createRequestBody(HttpServletRequest servletRequest) throws IOException {
        InputStream inputStream = servletRequest.getInputStream();
        return StreamUtils.copyToByteArray(inputStream);
    }

    private MultiValueMap<String, String> createRequestHeaders(HttpServletRequest servletRequest) {
        HttpHeaders headers = new HttpHeaders();
        List<String> headerNames = Collections.list(servletRequest.getHeaderNames());
        for (String headerName : headerNames) {
            List<String> headerValues = Collections.list(servletRequest.getHeaders(headerName));
            for (String headerValue : headerValues) {
                headers.add(headerName, headerValue);
            }
        }
        return headers;
    }

}
