/**
 * @author Jp
 *
 */
package com.tracer.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.RequestUtils;

import com.tracer.util.CommonConverter;

public class WebappUtil {
  protected static transient final Log log = LogFactory.getLog(WebappUtil.class);
  
  //==========================================================================
  
  public static synchronized void copyProperties(Object dest, Object orig, HttpServletRequest request) throws IllegalAccessException, InvocationTargetException {
    boolean requestSpecified = request != null;
    CommonConverter converter = new CommonConverter();

    if ( requestSpecified ) {
      Locale locale = RequestUtils.getUserLocale(request, null);
      converter = new CommonConverter(locale);
    }
    ConvertUtils.register(converter, Date.class);
    ConvertUtils.register(converter, String.class);
    BeanUtils.copyProperties(dest, orig);

    if ( requestSpecified ) {
      // Register default converters
      ConvertUtils.register(new CommonConverter(), String.class);
      ConvertUtils.register(new CommonConverter(), Date.class);
    }
  }
  
  //==========================================================================

  public static boolean isNotNull(String check) {
    if (check != null && !check.equals("")  ) {
      return true;
    } else {
      return false;
    }
      
  }
  
  //==========================================================================

  public static String getCookieValue(HttpServletRequest request, String name) {
    boolean found = false;
    String result = null;
    Cookie[] cookies = request.getCookies();
    
    if (cookies!=null) {
      int i = 0;
      while (!found && i < cookies.length) {
        if (cookies[i].getName().equals(name)) {
          found=true;
          result = cookies[i].getValue();
        }
        i++;
      }
    }
    return (result);
  }
  
  //==========================================================================
  
  public static void freeMemory() {
    @SuppressWarnings("unused") 
    long initalFreeMemory = Runtime.getRuntime().freeMemory();
    Runtime.getRuntime().gc();
  }
  
  //==========================================================================
}
