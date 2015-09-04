/**
 * @author Jp
 *
 */
package com.tracer.common;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import static com.tracer.common.Constants.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class SessionFilter implements Filter {
  protected transient final Log log = LogFactory.getLog(SessionFilter.class);
  protected FilterConfig config = null;

  //========================================================================
  
  public void init(FilterConfig config) throws ServletException {
    log.info("Starting of the init method");
    this.config = config;
    log.info("End of the init method");
  }

  //========================================================================
  
  public void destroy() {
    log.info("Starting of the destroy method");
    config = null;
    log.info("End of the destroy method");
  }
  
  //========================================================================
  
  public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
    log.info("START of the doFilter method");
    HttpServletRequest request = null;
    HttpServletResponse response = null;
    HttpSession session = null;
    
    try {
      request = (HttpServletRequest) req;
      response = (HttpServletResponse) resp;
      session = request.getSession(false);
		String accessToken = request.getParameter("guid");
          
      if (session != null && !session.isNew()) {
        if(request.getParameter(METHOD)!= null && request.getQueryString() != null) {
          session.setAttribute(METHOD, request.getParameter(METHOD));
        }
        chain.doFilter(request, response);
      }
      else if( accessToken != null ) {
    	chain.doFilter(request, response);
    }
      else {
          log.info("Session time out");
          request.getRequestDispatcher("/login.do?method=showLogin").forward(request,response);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    log.info("END of the doFilter method");
    return;
  } // End of the doFilter method
  
  //========================================================================
  
} // End of the class SessionFilter 

