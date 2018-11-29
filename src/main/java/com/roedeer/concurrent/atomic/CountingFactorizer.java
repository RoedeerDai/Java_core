package com.roedeer.concurrent.atomic;

import javax.servlet.*;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by U6071369 on 7/25/2018.
 */
public class CountingFactorizer implements Servlet {

    private final AtomicLong count = new AtomicLong(0);

    public AtomicLong getCount() {
        return count;
    }

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        count.incrementAndGet();  //
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
