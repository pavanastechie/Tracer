/**
 * @author Jp
 *
 */
package com.tracer.webui.presentation.action;

import com.tracer.common.Constants;
import com.tracer.common.WebAppConstants;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

public class BaseDispatchAction extends DispatchAction {
  @SuppressWarnings("unused")
  private static final String SECURE = "secure";
  private ApplicationContext ctx = null;

  //========================================================================
  
  protected Object getBean(String name) {
    if (ctx == null) {
      ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(servlet.getServletContext());
    }
    return ctx.getBean(name);
  }

  //========================================================================
  
  protected ActionMessages getMessages(HttpServletRequest request) {
    ActionMessages messages = null;
    HttpSession session = request.getSession();

    if (request.getAttribute(Globals.MESSAGE_KEY) != null) {
      messages = (ActionMessages) request.getAttribute(Globals.MESSAGE_KEY);
      saveMessages(request, messages);
    } else if (session.getAttribute(Globals.MESSAGE_KEY) != null) {
      messages = (ActionMessages) session.getAttribute(Globals.MESSAGE_KEY);
      saveMessages(request, messages);
      session.removeAttribute(Globals.MESSAGE_KEY);
    } else {
      messages = new ActionMessages();
    }
    return messages;
  }

  //========================================================================

  protected void saveErrors(HttpServletRequest request, ActionMessages errors) {
    super.saveErrors(request, errors);
  }

  //========================================================================

  protected void saveErrors(HttpServletRequest request, ActionMessages errors, boolean saveToSession) {
    HttpSession session = request.getSession();
    if (errors == null || errors.isEmpty()) {
      request.removeAttribute(Globals.ERROR_KEY);
      session.removeAttribute(WebAppConstants.ERROR_KEY);
    } else {
      request.setAttribute(Globals.ERROR_KEY, errors);
      if (saveToSession) {
        session.setAttribute(WebAppConstants.ERROR_KEY, errors);
      }
    }
  }

  //========================================================================

  protected void saveMessages(HttpServletRequest request, ActionMessages messages) {
    HttpSession session = request.getSession();
    
    if (messages == null || messages.isEmpty()) {
      request.removeAttribute(Globals.MESSAGE_KEY);
      session.removeAttribute(WebAppConstants.MESSAGE_KEY);
    } else {
      request.setAttribute(Globals.MESSAGE_KEY, messages);
      session.setAttribute(WebAppConstants.MESSAGE_KEY, messages);
    }
  } 

  //========================================================================
  
  protected ActionForm getActionForm(ActionMapping mapping, HttpServletRequest request) {
    ActionForm actionForm = null;

    if (mapping.getAttribute() != null) {
      if ("request".equals(mapping.getScope())) {
        actionForm = (ActionForm) request.getAttribute(mapping.getAttribute());
      } else {
        HttpSession session = request.getSession();
        actionForm = (ActionForm) session.getAttribute(mapping.getAttribute());
      }
    } 
    return actionForm;
  } 

  //========================================================================
  
  @SuppressWarnings("rawtypes")
  protected HashMap getConfiguration() {
    return (HashMap) this.getServlet().getServletContext().getAttribute(Constants.CONFIG);
  }

  //========================================================================
  
  protected void removeFormBean(ActionMapping mapping, HttpServletRequest request) {
    if (mapping.getAttribute() != null) {
      if ("request".equals(mapping.getScope())) {
        request.removeAttribute(mapping.getAttribute());
      } else {
        HttpSession session = request.getSession();
        session.removeAttribute(mapping.getAttribute());
      }
    }
  }

  //========================================================================
  
  protected void updateFormBean(ActionMapping mapping, HttpServletRequest request, ActionForm form) {
    if (mapping.getAttribute() != null) {
      if ("request".equals(mapping.getScope())) {
        request.setAttribute(mapping.getAttribute(), form);
      } else {
        HttpSession session = request.getSession();
        session.setAttribute(mapping.getAttribute(), form);
      }
    }
  }

  //========================================================================
  
  public boolean isBackPressed(HttpServletRequest request) {
    return (request.getAttribute(WebAppConstants.BACK_KEY) != null);
  }

  //========================================================================
  
  protected ActionForm getActionForm(HttpServletRequest request, String formName) {
    ActionForm actionForm = (ActionForm) request.getSession().getAttribute(formName);
    return actionForm;
  }

  //========================================================================
  
  protected void updateActionForm(HttpServletRequest request, String formName, ActionForm form) {
    request.getSession().setAttribute(formName, form);
  }
  
  //========================================================================
  
  protected HttpSession getSession(HttpServletRequest request) {
    return request.getSession();
  }
  
  //========================================================================
}