/**
 * @author Jp
 *
 */
package com.tracer.service;

import java.util.List;

import com.tracer.dao.model.SmsCategoryDetails;
import com.tracer.dao.model.SmsTemplateDetails;

public interface NotificationManager {
  
  public List<SmsCategoryDetails> getSMSCategories() throws Exception;
  
  public List<SmsTemplateDetails> getSMSTemplates() throws Exception;
  
  public String addSMSTemplate(SmsTemplateDetails smsTemplateDetails) throws Exception;
  
  public String updateSMSTemplate(SmsTemplateDetails smsTemplateDetails) throws Exception;
  
  public SmsTemplateDetails getSMSTemplate(Long smsTemplateId) throws Exception;
  
  public String deleteSMSTemplate(Long smsTemplateId) throws Exception;
  
  public SmsTemplateDetails getSMSTemplateDetails(Long smsCategoryId) throws Exception;

}
