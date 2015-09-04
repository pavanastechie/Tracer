/**
 * @author Jp
 *
 */
package com.tracer.webui.presentation.action;

import static com.tracer.common.Constants.ACTIVE;
import static com.tracer.common.Constants.ADD_SMS_TEMPLATE;
import static com.tracer.common.Constants.ADD_SMS_TEMPLATE_ERROR_MESSAGE;
import static com.tracer.common.Constants.ADD_SMS_TEMPLATE_SUCCESS_MESSAGE;
import static com.tracer.common.Constants.EDIT_SMS_TEMPLATE_ERROR_MESSAGE;
import static com.tracer.common.Constants.EDIT_SMS_TEMPLATE_SUCCESS_MESSAGE;
import static com.tracer.common.Constants.ERROR_MESSAGE;
import static com.tracer.common.Constants.INVALID_TOKEN_ERROR_PAGE;
import static com.tracer.common.Constants.MANAGE_SMS_TEMPLATE;
import static com.tracer.common.Constants.NOTIFICATION_MANAGER;
import static com.tracer.common.Constants.SESSION_INACTIVE_PAGE;
import static com.tracer.common.Constants.SUCCESS_MESSAGE;
import static com.tracer.common.Constants.USER_ID;
import static com.tracer.common.Constants.USER_MANAGER;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.json.JSONArray;
import org.json.JSONObject;
import com.tracer.dao.model.SmsCategoryDetails;
import com.tracer.dao.model.SmsTemplateDetails;
import com.tracer.dao.model.UserDetails;
import com.tracer.service.NotificationManager;
import com.tracer.service.UserManager;
import com.tracer.util.SMSManager;
import com.tracer.webui.presentation.form.NotificationForm;

public class NotificationAction extends BaseDispatchAction {

  protected transient final Log log = LogFactory.getLog(getClass());
  
  //========================================================================

  /**
   * Save SMS Template
   * @param mapping MANAGE_SMS_TEMPLATE
   * @param form NotificationForm
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  public ActionForward showAddSMSTemplate(ActionMapping mapping, ActionForm form,
  		HttpServletRequest request, HttpServletResponse response) throws Exception {
   log.info("START of the ActionForward showAddSMSTemplate");
   NotificationManager notificationManager;
   NotificationForm smsForm;
   HttpSession session;
   List<SmsCategoryDetails>  categoryDetails = new ArrayList<SmsCategoryDetails>();

   try {
	   session = request.getSession(false);
		 if(session != null && session.getAttribute(USER_ID) != null && ((Long)session.getAttribute(USER_ID)).longValue() > 0 ) {
  	    smsForm = (NotificationForm)form;
  	    BeanUtils.copyProperties(smsForm, new NotificationForm());
  	    notificationManager = (NotificationManager) getBean(NOTIFICATION_MANAGER );
  	    categoryDetails = notificationManager.getSMSCategories();
  	    smsForm.setCategoryDetails(categoryDetails);
  	 }
  	 else {
  		  return mapping.findForward(SESSION_INACTIVE_PAGE);	
  	 }

   } catch (Exception e) {
     log.error("Problem in the ActionForward showAddSMSTemplate"); 
     log.error(e);
     ActionMessages errors = new ActionMessages();
	 saveErrors(request,errors);
	    throw e;
   }
   log.info("END of the ActionForward showAddSMSTemplate");
   return mapping.findForward(ADD_SMS_TEMPLATE);
 }

  //========================================================================
  /**
   * Save SMS Template
   * @param mapping MANAGE_SMS_TEMPLATE
   * @param form NotificationForm
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  public ActionForward addSmsTemplate(ActionMapping mapping, ActionForm form,
  		HttpServletRequest request, HttpServletResponse response) throws Exception {
   log.info("START of the ActionForward addSMSTemplate");
   NotificationForm smsForm;
   NotificationManager notificationManager;
   SmsCategoryDetails smsCategoryDetails;
   SmsTemplateDetails smsTempalteDetails;
   UserDetails userDetails;
   String message = "";
   String FORWARD_PAGE = MANAGE_SMS_TEMPLATE;
   HttpSession session =null;
   try {
	   session = request.getSession(false);
		 if(session != null && session.getAttribute(USER_ID) != null && ((Long)session.getAttribute(USER_ID)).longValue() > 0 ) {
  		 if (isTokenValid(request)) {
  	    notificationManager = (NotificationManager) getBean(NOTIFICATION_MANAGER );
  	    smsTempalteDetails = new SmsTemplateDetails();
  	    smsCategoryDetails = new SmsCategoryDetails();
  	    smsForm = (NotificationForm)form;
  	    session.removeAttribute(SUCCESS_MESSAGE);
  	    session.removeAttribute(ERROR_MESSAGE);
  	     smsCategoryDetails.setSmsCategoryId(smsForm.getSmsCategoryId());
  	     userDetails = new UserDetails();
  	     Long userId = (Long)session.getAttribute(USER_ID);

  	     userDetails.setUserId(userId);
  	     smsTempalteDetails.setUserDetails(userDetails);
  	     smsTempalteDetails.setSmsCategoryDetails(smsCategoryDetails);
  	     smsTempalteDetails.setSmsTemplate(smsForm.getSmsContent());
  	     smsTempalteDetails.setStatus(ACTIVE);
  	     smsTempalteDetails.setDateTime(new Date());
  	     Long templateId = smsForm.getSmsTemplateId();
  	     smsTempalteDetails.setSmsTemplateId(templateId);
  	     if(templateId != null) {
  	      message = notificationManager.updateSMSTemplate(smsTempalteDetails);
  	     }
  	     else {
  	      message = notificationManager.addSMSTemplate(smsTempalteDetails);
  	     }
  	     if(message.equalsIgnoreCase("success")) {

  	     }
  	     if(message.equals("success")) {
  	      if(templateId != null) {
  	    	  session.setAttribute(ERROR_MESSAGE, EDIT_SMS_TEMPLATE_SUCCESS_MESSAGE);
  	      }
  	      else {
  	    	  session.setAttribute(SUCCESS_MESSAGE, ADD_SMS_TEMPLATE_SUCCESS_MESSAGE);
  	      }
          resetToken(request);
          saveToken(request);
  	     }
  	     else {
  	      if(templateId != null) {
  	      	session.setAttribute(ERROR_MESSAGE, EDIT_SMS_TEMPLATE_ERROR_MESSAGE);
  	      }
  	      else {
  	    	  session.setAttribute(ERROR_MESSAGE, ADD_SMS_TEMPLATE_ERROR_MESSAGE);
  	      }
  	      FORWARD_PAGE = ADD_SMS_TEMPLATE;
  	     }
  		 }
  		 else {
  			 FORWARD_PAGE = INVALID_TOKEN_ERROR_PAGE; 
       }
  	 }
  	 else {
  		 log.info("NotificationAction.java::addSmsTemplate() Session is null");
  		  return mapping.findForward(SESSION_INACTIVE_PAGE);	
  	 }


   } catch (Exception e) {
     log.error("Problem in the ActionForward addSMSTemplate"); 
     log.error(e);
     ActionMessages errors = new ActionMessages();
	 saveErrors(request,errors);
	    throw e;
   }
   log.info("END of the ActionForward addSMSTemplate");
   return mapping.findForward(FORWARD_PAGE);
 }

  //========================================================================

  /**editSMSTemplate
   * Shows Edit Page with SMS Details to edit
   * 
   */
  public ActionForward showEditSmsTemplate(ActionMapping mapping, ActionForm form,
  		HttpServletRequest request, HttpServletResponse response) throws Exception {
   log.info("START of the ActionForward editSMSTemplate");
   NotificationForm smsForm = (NotificationForm)form;

   NotificationManager notificationManager;
   try {
    notificationManager = (NotificationManager) getBean(NOTIFICATION_MANAGER );
    String templateId = request.getParameter("id");
    long smsTemplateId = Long.parseLong(templateId);
    smsForm.setCategoryDetails(notificationManager.getSMSCategories());
    SmsTemplateDetails smsTemplateDetails = notificationManager.getSMSTemplate(smsTemplateId);
    SmsCategoryDetails smsCategoryDetails = smsTemplateDetails.getSmsCategoryDetails();

     smsForm.setSmsContent(smsTemplateDetails.getSmsTemplate());
     smsForm.setSmsCategoryName(smsCategoryDetails.getSmsCategoryName());
     smsForm.setSmsTemplateId(smsTemplateDetails.getSmsTemplateId());
     smsForm.setSmsCategoryId(smsCategoryDetails.getSmsCategoryId());
   } catch (Exception e) {
     log.error("Problem in the ActionForward editSMSTemplate"); 
     log.error(e);
     ActionMessages errors = new ActionMessages();
	 saveErrors(request,errors);
	    throw e;
   }
   log.info("END of the ActionForward editSMSTemplate");
   return mapping.findForward(ADD_SMS_TEMPLATE);
 }

//========================================================================

  /**deleteSMSTemplate
   * delete SMS Template
   * 
   */
  public ActionForward deleteSMSTemplate(ActionMapping mapping, ActionForm form,
  		HttpServletRequest request, HttpServletResponse response) throws Exception {
   log.info("START of the ActionForward deleteSMSTemplate");
   NotificationManager notificationManager;
   HttpSession session = null;
   try {
	   session = request.getSession(false);
		 if(session != null && session.getAttribute(USER_ID) != null && ((Long)session.getAttribute(USER_ID)).longValue() > 0 ) {
    notificationManager = (NotificationManager) getBean(NOTIFICATION_MANAGER );
    String templateId = request.getParameter("id");
    long smsTemplateId = Long.parseLong(templateId);
    String deleteSms = notificationManager.deleteSMSTemplate(smsTemplateId);
    log.info("Template deletion for "+smsTemplateId+" is "+deleteSms);
		 } else {
				return mapping.findForward(SESSION_INACTIVE_PAGE);	
			  }

   } catch (Exception e) {
     log.error("Problem in the ActionForward deleteSMSTemplate"); 
     log.error(e);
     ActionMessages errors = new ActionMessages();
	 saveErrors(request,errors);
	    throw e;
   }
   log.info("END of the ActionForward deleteSMSTemplate");
   
   return mapping.findForward(MANAGE_SMS_TEMPLATE);
 }

  //========================================================================

  /**listSMSTemplate
   * List All SMS
   * 
   */
  public ActionForward manageSmsTemplate(ActionMapping mapping, ActionForm form,
  		HttpServletRequest request, HttpServletResponse response) throws Exception {
   log.info("START of the ActionForward listSMS");
   NotificationForm smsForm = (NotificationForm)form;
   HttpSession session = request.getSession();
   session.setAttribute(mapping.getAttribute(), smsForm);
   try {

   } catch (Exception e) {
     log.error("Problem in the ActionForward listSMS"); 
     log.error(e);
     ActionMessages errors = new ActionMessages();
	 saveErrors(request,errors);
	    throw e;
   }
   log.info("END of the ActionForward listSMS");
   return mapping.findForward(MANAGE_SMS_TEMPLATE);
 }
  
//========================================================================

  /**listSMSTemplate
   * List All SMS
   * 
   */
  public ActionForward getSmsData(ActionMapping mapping, ActionForm form,
  		HttpServletRequest request, HttpServletResponse response) throws Exception {
   log.info("START of the ActionForward getSmsData");

   NotificationManager notificationManager;
   HttpSession session = null;
   try {
	   session = request.getSession(false);
		 if(session != null && session.getAttribute(USER_ID) != null && ((Long)session.getAttribute(USER_ID)).longValue() > 0 ) {
    notificationManager = (NotificationManager) getBean(NOTIFICATION_MANAGER );
    JSONObject jsonObject = new JSONObject();
    JSONArray jsonArray = new JSONArray();
    if(notificationManager.getSMSTemplates()!= null) {
	    List<SmsTemplateDetails> smsTemplateDetails = notificationManager.getSMSTemplates();
	    for(int i = 0 ; i< smsTemplateDetails.size() ; i++)
	    {
	  	  SmsTemplateDetails templateDetails = smsTemplateDetails.get(i);
	  	  SmsCategoryDetails categoryDetails = templateDetails.getSmsCategoryDetails();
	        jsonObject.put("smsCategory", categoryDetails.getSmsCategoryName());
	        jsonObject.put("smsContent", templateDetails.getSmsTemplate());
	        jsonObject.put("smsTemplateId", templateDetails.getSmsTemplateId());
	        jsonArray.put(jsonObject);
	        jsonObject = new JSONObject();
	    }
    }
     response.setContentType("application/Json");
     response.getWriter().print(jsonArray);
		 } else {
				return mapping.findForward(SESSION_INACTIVE_PAGE);	
			  }
   } catch (Exception e) {
     response.setContentType("text/html");
     response.getWriter().print(e.getMessage());
     log.error("Problem in the ActionForward getSmsData"); 
     log.error(e);
     ActionMessages errors = new ActionMessages();
	 saveErrors(request,errors);
	    throw e;
   }
   log.info("END of the ActionForward getSmsData");
   return null;
 }
  
  
//========================================================================

  /**getSMSTemplate::
   * input: categoryId
   * output: SMS Template, result in JSON format
   * This method is used to get the sms template details when sms
   * categoryId is given.
 */
  public ActionForward getSMSTemplate(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response) throws Exception {
   log.info("START of the ActionForward getSMSTemplate");

   NotificationManager notificationManager;
   NotificationForm smsForm;
   HttpSession session =null;
   String content = null;
   Long categoryId = null;
   JSONObject jsonObject = null;
   SmsTemplateDetails smsTemplateDetails= null;
   try {
	   session = request.getSession(false);
		 if(session != null && session.getAttribute(USER_ID) != null && ((Long)session.getAttribute(USER_ID)).longValue() > 0 ) {
       notificationManager = (NotificationManager) getBean(NOTIFICATION_MANAGER );
       smsForm = (NotificationForm)form;
       BeanUtils.copyProperties(smsForm, new NotificationForm());
       jsonObject = new JSONObject();
       smsTemplateDetails = new SmsTemplateDetails();
       categoryId =  Long.parseLong(request.getParameter("smsCategoryId"));
       if(categoryId != null && categoryId != 0) {
         smsTemplateDetails = notificationManager.getSMSTemplateDetails(categoryId);
         content = smsTemplateDetails.getSmsTemplate();
         if(content != "" && content != null) {
           jsonObject.put("result", "Ok");
           jsonObject.put("smsContent", content);
         }
       }
       response.setContentType("application/Json");
       response.getWriter().print(jsonObject);
     }
     else {
       return mapping.findForward(SESSION_INACTIVE_PAGE);
     }
   } catch (Exception e) {
     response.setContentType("text/html");
     response.getWriter().print(e.getMessage());
     log.error("Problem in the ActionForward getSMSTemplate"); 
     log.error(e);
     ActionMessages errors = new ActionMessages();
	 saveErrors(request,errors);
	    throw e;
   }
   log.info("END of the ActionForward getSMSTemplate");
   return null;
 }
//========================================================================

  /**getSMSCategories
   * 
   * 
   */
  public ActionForward getSMSCategories(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response) throws Exception {
   log.info("START of the ActionForward getSMSCategories");

   NotificationManager notificationManager;
   HttpSession session;
   List<SmsCategoryDetails> smsCategoryDetails = null;
   try {
	   session = request.getSession(false);
		 if(session != null && session.getAttribute(USER_ID) != null && ((Long)session.getAttribute(USER_ID)).longValue() > 0 ) {
       notificationManager = (NotificationManager) getBean(NOTIFICATION_MANAGER );
       JSONObject jsonObject = new JSONObject();
       JSONArray jsonArray = new JSONArray();
       smsCategoryDetails = new ArrayList<SmsCategoryDetails>();
       smsCategoryDetails = notificationManager.getSMSCategories();
       if(smsCategoryDetails!= null) {
         for(int i=0;i<smsCategoryDetails.size();i++) {
           SmsCategoryDetails categoryDetails = smsCategoryDetails.get(i);
           jsonObject.put("smsCategoryId", categoryDetails.getSmsCategoryId());
           jsonObject.put("smsCategoryName", categoryDetails.getSmsCategoryName());
           jsonArray.put(jsonObject);
           jsonObject = new JSONObject();
         }
       }
       response.setContentType("application/Json");
       response.getWriter().print(jsonArray);
     }
     else {
       return mapping.findForward(SESSION_INACTIVE_PAGE);
     }


   } catch (Exception e) {
     e.printStackTrace();
     response.setContentType("text/html");
     response.getWriter().print(e.getMessage());
     log.error("Problem in the ActionForward getSMSCategories"); 
     log.error(e);
     ActionMessages errors = new ActionMessages();
	 saveErrors(request,errors);
	    throw e;
   }
   log.info("END of the ActionForward getSMSCategories");
   return null;
 }
  
//========================================================================

  /**sendSMS
   * 
   * 
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public ActionForward sendSMS(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response) throws Exception {
   log.info("START of the ActionForward sendSMS");

   HttpSession session = null;
   net.sf.json.JSONArray jsonArray = null;
   String content = null;
   SMSManager smsManager;
   String smsContent = null;
   String jsonResponse = null;
   Long runnerId;
   UserDetails userDetails = null;
   UserManager userManager = null;

   try {
	   session = request.getSession(false);
		 if(session != null && session.getAttribute(USER_ID) != null && ((Long)session.getAttribute(USER_ID)).longValue() > 0 ) {
       jsonResponse = request.getParameter("smsData");
       jsonArray = net.sf.json.JSONArray.fromObject(jsonResponse);
       smsManager = SMSManager.getInstance();
       userManager = (UserManager) getBean(USER_MANAGER);

      Collection<Map<String, Object>> collection = net.sf.json.JSONArray.toCollection(jsonArray, HashMap.class);
       for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
         Map<String, Object> mapObject = (Map<String, Object>) iterator.next();
         String id = (String) mapObject.get("runnerId");
         runnerId = Long.parseLong(id);
         userDetails =  userManager.getUserDetails(runnerId);
         String phoneNumber = userDetails.getOfficePhone().trim();
         smsContent = (String) mapObject.get("smsContent");
         smsManager.sendSMS(phoneNumber, smsContent);
         content = "OK";
      }
      response.setContentType("application/Json");
      response.getWriter().print(content);
     }
     else {
       return mapping.findForward(SESSION_INACTIVE_PAGE);
     }


   } catch (Exception e) {
     response.setContentType("text/html");
     response.getWriter().print(e.getMessage());
     log.error("Problem in the ActionForward sendSMS"); 
     log.error(e);
     ActionMessages errors = new ActionMessages();
	 saveErrors(request,errors);
	    throw e;
   }
   log.info("END of the ActionForward sendSMS");
   return null;
 }

}
