package com.maomao2.simplegateway.filter.post;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import com.maomao2.simplegateway.filter.Filter;
import com.maomao2.simplegateway.http.RequestContext;

public class SendResponseFilter extends Filter {

    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 1000;
    }

    @Override
    public void run() {

        addResponseHeaders();
        try {
            writeResponse();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeResponse() throws IOException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletResponse servletResponse = requestContext.getResponse();
        ResponseEntity<byte[]> responseEntity = requestContext.getResponseEntity();

        if (servletResponse.getCharacterEncoding() == null) {
            servletResponse.setCharacterEncoding("UTF-8");
        }

        if (responseEntity.hasBody()) {
            byte[] body = responseEntity.getBody();
            ServletOutputStream outputStream = servletResponse.getOutputStream();
            outputStream.write(body);
            outputStream.flush();
        }
    }

    private void addResponseHeaders() {

        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletResponse servletResponse = requestContext.getResponse();
        ResponseEntity<byte[]> responseEntity = requestContext.getResponseEntity();
        HttpHeaders httpHeaders = responseEntity.getHeaders();

        for (Map.Entry<String, List<String>> entry : httpHeaders.entrySet()) {
            String headerName = entry.getKey();
            List<String> headerValues = entry.getValue();
            for (String headerValue : headerValues) {
                servletResponse.setHeader(headerName, headerValue);
            }
        }

    }

}
