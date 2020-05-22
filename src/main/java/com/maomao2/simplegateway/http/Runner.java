package com.maomao2.simplegateway.http;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.maomao2.simplegateway.filter.Filter;
import com.maomao2.simplegateway.filter.post.SendResponseFilter;
import com.maomao2.simplegateway.filter.pre.RequestWrapperFilter;
import com.maomao2.simplegateway.filter.route.RoutingFilter;

public class Runner {

    private static  ConcurrentHashMap<String, List<Filter>> hashFiltersByType = new ConcurrentHashMap<String, List<Filter>>() {
        {
            put("pre", new ArrayList<Filter>() {
                {
                    add(new RequestWrapperFilter());
                }
            });
            put("route", new ArrayList<Filter>() {
                {
                    add(new RoutingFilter());
                }
            });

            put("post", new ArrayList<Filter>() {
                {
                    add(new SendResponseFilter());
                }
            });

        }
    };

    public void init(HttpServletRequest req, HttpServletResponse resp) {
        RequestContext context = RequestContext.getCurrentContext();
        context.setRequest(req);
        context.setResponse(resp);
    }

    public void destroy() {
        RequestContext context = RequestContext.getCurrentContext();
        context.unset();
    }

    public void preRoute() {
        runFilters("pre");
    }

    public void route() {
        runFilters("route");
    }

    public void postRoute() {
        runFilters("post");
    }

    private void runFilters(String type) {

        List<Filter> filterList = this.hashFiltersByType.get(type);
        if (filterList != null) {
            for (Filter filter : filterList) {
                filter.run();
            }
        }
    }

}
