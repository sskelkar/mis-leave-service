package com.maxxton.mis.leave.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component
public class CorsConfig implements Filter {

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    
  }

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
    HttpServletResponse response = (HttpServletResponse) res;
    HttpServletRequest request = (HttpServletRequest) req;
    
    response.setHeader("Access-Control-Allow-Origin", request.getHeader("origin"));
    response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
    response.setHeader("Access-Control-Max-Age", "3600");
    response.setHeader("Access-Control-Allow-Headers", "x-requested-with, accept, authorization, locale, content-type");

    if("OPTIONS".equalsIgnoreCase(request.getMethod()))
      response.setStatus(HttpServletResponse.SC_OK);
    else
      chain.doFilter(req,res);    
  }

  @Override
  public void destroy() {
    
  }

}
