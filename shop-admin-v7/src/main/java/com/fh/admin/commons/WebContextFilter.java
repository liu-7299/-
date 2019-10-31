package com.fh.admin.commons;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WebContextFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        WebContext.setThread((HttpServletRequest) servletRequest);
        WebContext.setThread1((HttpServletResponse) servletResponse);
        try {
            //继续执行
            filterChain.doFilter(servletRequest,servletResponse);
        }finally {
            WebContext.remove();
            WebContext.remove1();
        }
    }

    @Override
    public void destroy() {

    }
}
