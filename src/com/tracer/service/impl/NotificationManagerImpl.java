/**
 * @author Jp
 *
 */
package com.tracer.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tracer.dao.model.SmsCategoryDetails;
import com.tracer.dao.model.SmsTemplateDetails;
import com.tracer.dao.persistence.NotificationDAO;
import com.tracer.service.NotificationManager;

public class NotificationManagerImpl implements NotificationManager {
  protected transient final Log log = LogFactory.getLog(NotificationManagerImpl.class);
  protected NotificationDAO notificationDAO = null;
  
  //========================================================================
  
  public void setNotificationDAO(NotificationDAO notificationDAO) {
    
    try {
      this.notificationDAO = notificationDAO;
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  //========================================================================
  
  @Override
  public List<SmsCategoryDetails> getSMSCategories() throws Exception {
    return notificationDAO.getSMSCategories();
  }

  //========================================================================
  
  @Override
  public List<SmsTemplateDetails> getSMSTemplates() throws Exception {
    return notificationDAO.getSMSTemplates();
  }
 
  //========================================================================
  
  @Override
  public String addSMSTemplate(SmsTemplateDetails smsTemplateDetails) throws Exception {
    return notificationDAO.addSMSTemplate(smsTemplateDetails);
  }

  //========================================================================
  
  @Override
  public String updateSMSTemplate(SmsTemplateDetails smsTemplateDetails) throws Exception {
    return notificationDAO.updateSMSTemplate(smsTemplateDetails);
  }

  //========================================================================
  
  @Override
  public SmsTemplateDetails getSMSTemplate(Long smsTemplateId) throws Exception {
    return notificationDAO.getSMSTemplate(smsTemplateId);
  }
 
  //========================================================================
  
  @Override
  public String deleteSMSTemplate(Long smsTemplateId) throws Exception {
    return notificationDAO.deleteSMSTemplate(smsTemplateId);
  }

  //========================================================================
  
  @Override
  public SmsTemplateDetails getSMSTemplateDetails(Long smsCategoryId) throws Exception {
    return notificationDAO.getSMSTemplateDetails(smsCategoryId);
  }

  //========================================================================

}