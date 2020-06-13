package com.jaxon.demo.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "test",urlPatterns = "/*" )
public class MyFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("过滤器初始化");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        String requestURI = httpServletRequest.getRequestURI();
        if(requestURI.contains("invalid")){
            System.out.println("不合法的请求格式");
            request.getRequestDispatcher("/common/fail").forward(request,response);
        }
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {
        System.out.println("过滤器消亡");
    }
}
