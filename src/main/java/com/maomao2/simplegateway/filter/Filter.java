package com.maomao2.simplegateway.filter;

public abstract class Filter {
    public abstract String filterType();

    public abstract int filterOrder();

    public abstract void run();
}
