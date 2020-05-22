package com.maomao2.simplegateway.http;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "eatuul", urlPatterns = "/*")
public class Servlet extends HttpServlet {

    private Runner runner = new Runner();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        runner.init(req, resp);
        runner.preRoute();
        runner.route();
        runner.postRoute();
        runner.destroy();
    }

}
