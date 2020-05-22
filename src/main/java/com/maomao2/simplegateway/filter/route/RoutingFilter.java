package com.maomao2.simplegateway.filter.route;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.maomao2.simplegateway.filter.Filter;
import com.maomao2.simplegateway.http.RequestContext;

public class RoutingFilter extends Filter {

    @Override
    public String filterType() {
        return "route";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public void run() {

        RequestContext context = RequestContext.getCurrentContext();
        RequestEntity requestEntity = context.getRequestEntity();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity responseEntity = restTemplate.exchange(requestEntity, byte[].class);

        context.setResponseEntity(responseEntity);
    }
}
