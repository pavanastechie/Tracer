/**
 * @author Jp
 *
 */
package com.tracer.webui.presentation.action;

import static com.tracer.common.Constants.APP_DATA_PATH;
import static com.tracer.common.Constants.CAF_DATA_ENTRY_PAGE;
import static com.tracer.common.Constants.DEO_DASHBOARD_PAGE;
import static com.tracer.common.Constants.SESSION_INACTIVE_PAGE;
import static com.tracer.common.Constants.SUCCESS_MESSAGE;
import static com.tracer.common.Constants.USER_ID;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.json.JSONArray;
import org.json.JSONObject;


public class DataEntryAction extends BaseDispatchAction {

  protected transient final Log log = LogFactory.getLog(getClass());
  
  //========================================================================

  public ActionForward showCAFDataEntryPage(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response)throws Exception {
    log.info("START of the ActionForward showCAFDataEntryPage");
    HttpSession session = null;
    String pageToForward = null;
    
    try {
      session = request.getSession(false);
        
      if(session != null && session.getAttribute(USER_ID) != null && ((Long)session.getAttribute(USER_ID)).longValue() > 0 ) {
        pageToForward = CAF_DATA_ENTRY_PAGE;  
      } else {
        pageToForward = SESSION_INACTIVE_PAGE;
      }
    } catch (Exception e) {
      log.error("Problem in the ActionForward showCAFDataEntryPage");
      log.error(e);
      ActionMessages errors = new ActionMessages();
      saveErrors(request,errors);
      throw e;
    }
    log.info("END of the ActionForward showCAFDataEntryPage");
    return mapping.findForward(pageToForward);
  }
  
  //========================================================================
  
  public ActionForward getCAFsToDataEntry(ActionMapping mapping, ActionForm form,
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward getCAFsToDataEntry");
    HttpSession session = null;
    
    try {
      session = request.getSession(false);
      if(session != null && session.getAttribute(USER_ID) != null && ((Long)session.getAttribute(USER_ID)).longValue() > 0 ) {
        JSONArray jsonArray = new JSONArray();
        for(int i = 0 ; i < 5 ; i++) {
          JSONObject jsonObject = new JSONObject();
          jsonObject.put("barCode", "987325614300"+i);
          jsonObject.put("scannedCAFLocation", "need to place a sample scanned caf");
          jsonObject.put("cafId", i + 1);
          jsonArray.put(jsonObject);
        }
        response.setContentType("application/Json");
        response.getWriter().print(jsonArray);
      } else {
        return mapping.findForward(SESSION_INACTIVE_PAGE);	
      }
    } catch (Exception e) {
     response.setContentType("text/html");
     response.getWriter().print(e.getMessage());
     log.error("Problem in the ActionForward getCAFsToDataEntry"); 
     log.error(e);
     ActionMessages errors = new ActionMessages();
     saveErrors(request,errors);
     throw e;
    }
    log.info("END of the ActionForward getCAFsToDataEntry");
    return null;
  }
  
  //========================================================================
  
  public ActionForward getCAFsToSubmit(ActionMapping mapping, ActionForm form,
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward getCAFsToSubmit");
    HttpSession session = null;
    String[] saleDates = {"26-Feb-15", "22-Feb-15", "25-Feb-15", "26-Feb-15"};
    String[] cellNos = {"9000143256", "9000400923", "8019533234", "9703541622"};
    String[] firstNames = {"Jaya Prakash", "Raghu", "Poornima", "Vivek"};
    String[] lastNames = {"Manne", "Narsimha", "L", "Kanth"};
    String[] pinCodes = {"500018", "500081", "500026", "500081"};
    
    try {
      session = request.getSession(false);
      if(session != null && session.getAttribute(USER_ID) != null && ((Long)session.getAttribute(USER_ID)).longValue() > 0 ) {
        JSONArray cafsToSubmitJSONArray = new JSONArray();
        for(int i = 0 ; i < 4 ; i++) {
          JSONObject cafsToSubmitJSONObj = new JSONObject();
          cafsToSubmitJSONObj.put("barCode", "987325614300"+ i + 9);
          cafsToSubmitJSONObj.put("saleDate", saleDates[i]);
          cafsToSubmitJSONObj.put("cellNo", cellNos[i]);
          cafsToSubmitJSONObj.put("firstName", firstNames[i]);
          cafsToSubmitJSONObj.put("lastName", lastNames[i]);
          cafsToSubmitJSONObj.put("city", "Hyderabad");
          cafsToSubmitJSONObj.put("state", "Telangana");
          cafsToSubmitJSONObj.put("pinCode", pinCodes[i]);
          cafsToSubmitJSONObj.put("cafId", i + 1);
          cafsToSubmitJSONArray.put(cafsToSubmitJSONObj);
        }
        response.setContentType("application/Json");
        response.getWriter().print(cafsToSubmitJSONArray);
      } else {
        return mapping.findForward(SESSION_INACTIVE_PAGE);	
      }
    } catch (Exception e) {
     response.setContentType("text/html");
     response.getWriter().print(e.getMessage());
     log.error("Problem in the ActionForward getCAFsToSubmit"); 
     log.error(e);
     ActionMessages errors = new ActionMessages();
     saveErrors(request,errors);
     throw e;
    }
    log.info("END of the ActionForward getCAFsToSubmit");
    return null;
  }
  
  //========================================================================
  
  public ActionForward showDEODashboardPage(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response)throws Exception {
    log.info("START of the ActionForward showDEODashboardPage");
    HttpSession session = null;
    String pageToForward = null;
    String actionPerformed = null;
    
    try {
      session = request.getSession(false);
      
      if(session != null && session.getAttribute(USER_ID) != null && ((Long)session.getAttribute(USER_ID)).longValue() > 0 ) {
        pageToForward = DEO_DASHBOARD_PAGE;
        actionPerformed = request.getParameter("actionPerformed");
        
        if("save".equalsIgnoreCase(actionPerformed)) {
          request.setAttribute(SUCCESS_MESSAGE, "CAF Details saved Successfully");
        } else if("submit".equalsIgnoreCase(actionPerformed)) {
          request.setAttribute(SUCCESS_MESSAGE, "CAFs submitted to DMS Successfully");
        }
      } else {
        pageToForward = SESSION_INACTIVE_PAGE;
      }
    } catch (Exception e) {
      log.error("Problem in the ActionForward showDEODashboardPage");
      log.error(e);
      ActionMessages errors = new ActionMessages();
      saveErrors(request,errors);
      throw e;
    }
    log.info("END of the ActionForward showDEODashboardPage");
    return mapping.findForward(pageToForward);
  }
  
  //========================================================================
  
  public ActionForward downloadCAF(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward downloadCAF");
    HttpSession session;
    ServletOutputStream servletOutputStream = null;
    
    try {
      session = request.getSession(false);
      
      if(session != null && session.getAttribute(USER_ID) != null && ((Long)session.getAttribute(USER_ID)).longValue() > 0 ) {
        String  filePath = APP_DATA_PATH+"/temp/APMA0870277_1424328750148.jpg";
        File file = new File(filePath);
        int length   = 0;
        servletOutputStream = response.getOutputStream();
        response.setContentType("image/jpeg");
        response.setContentLength((int) file.length());
        String fileName = (new File(filePath)).getName();
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        byte[] byteBuffer = new byte[4096];
        DataInputStream dataInputStream = new DataInputStream(new FileInputStream(file));
        while ((dataInputStream != null) && ((length = dataInputStream.read(byteBuffer)) != -1)) {
          servletOutputStream.write(byteBuffer,0,length);
        }
        dataInputStream.close();
        servletOutputStream.flush();
        servletOutputStream.close();
      } else {
        return mapping.findForward(SESSION_INACTIVE_PAGE);
      }
    } catch (Exception e) {
      log.error("Problem in the ActionForward downloadCAF");
      log.error(e);
    }
    log.info("END of the ActionForward downloadCAF");
    return null;
  }
    
  //==========================================================================
}
