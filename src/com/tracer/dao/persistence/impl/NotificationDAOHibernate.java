/**
 * @author Jp
 *
 */
package com.tracer.dao.persistence.impl;

import static com.tracer.common.Constants.ACTIVE;
import static com.tracer.common.Constants.FAILURE;
import static com.tracer.common.Constants.IN_ACTIVE;
import static com.tracer.common.Constants.SUCCESS;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tracer.dao.model.SmsCategoryDetails;
import com.tracer.dao.model.SmsTemplateDetails;
import com.tracer.dao.persistence.NotificationDAO;

public class NotificationDAOHibernate extends BaseDAOHibernate implements NotificationDAO {
  protected transient final Log log = LogFactory.getLog(NotificationDAOHibernate.class);

  //========================================================================
  
  @SuppressWarnings("unchecked")
  @Override
  public List<SmsCategoryDetails> getSMSCategories() throws Exception {
    log.info("START of the method getSMSCategories");
    List<SmsCategoryDetails> smsCategories = null;
    StringBuffer hqlQuery;

    try {
      hqlQuery = new StringBuffer("SELECT smsCategoryDetails FROM SmsCategoryDetails smsCategoryDetails WHERE ");
      hqlQuery.append(" smsCategoryDetails.status='"+ACTIVE+"'");
      smsCategories = getHibernateTemplate().find(hqlQuery.toString());
    } catch (Exception e) {
      log.error("PROBLEM in the method getSMSCategories");
      log.error(e);
      e.printStackTrace();
      throw e;
    }
    log.info("END of the method getSMSCategories");
    return smsCategories;
  }

  //========================================================================
  
  @SuppressWarnings("unchecked")
  @Override
  public List<SmsTemplateDetails> getSMSTemplates() throws Exception {
    log.info("START of the method getSMSTemplates");
    List<SmsTemplateDetails> smsTemplates = null;
    StringBuffer hqlQuery;

    try {
      hqlQuery = new StringBuffer("SELECT smsTemplateDetails FROM SmsTemplateDetails smsTemplateDetails WHERE ");
      hqlQuery.append(" smsTemplateDetails.status='"+ACTIVE+"'");
      smsTemplates = getHibernateTemplate().find(hqlQuery.toString());
    } catch (Exception e) {
      log.info("PROBLEM in the method getSMSTemplates");
      e.printStackTrace();
      throw e;
    }
    log.info("END of the method getSMSTemplates");
    return smsTemplates;
  }

  //========================================================================
  
  @Override
  public String addSMSTemplate(SmsTemplateDetails smsTemplateDetails) throws Exception {
    log.info("START of the method addSMSTemplate");
    String result = SUCCESS;
    
    try {
      if(smsTemplateDetails != null) {
        getHibernateTemplate().save(smsTemplateDetails);
      }
    } catch (Exception e) {
      result = FAILURE;
      log.info("PROBLEM in the method addSMSTemplate");
      e.printStackTrace();
      throw e;
    }
    log.info("END of the method addSMSTemplate");
    return result;
  }

  //========================================================================
  
  @Override
  public String updateSMSTemplate(SmsTemplateDetails smsTemplateDetails) throws Exception {
    log.info("START of the method updateSMSTemplate");
    String result = SUCCESS;
    
    try {
      if(smsTemplateDetails != null && smsTemplateDetails.getSmsTemplateId() > 0) {
        getHibernateTemplate().update(smsTemplateDetails);
      }
    } catch (Exception e) {
      result = FAILURE;
      log.info("PROBLEM in the method updateSMSTemplate");
      e.printStackTrace();
      throw e;
    }
    log.info("END of the method updateSMSTemplate");
    return result;
  }

  //========================================================================
  
  @Override
  public SmsTemplateDetails getSMSTemplate(Long smsTemplateId) throws Exception {
  log.info("START of the method getSMSTemplate");
    SmsTemplateDetails smsTemplateDetails = null;
    
    try {
      if(smsTemplateId > 0) {
        smsTemplateDetails = (SmsTemplateDetails) getHibernateTemplate().get(SmsTemplateDetails.class, smsTemplateId);
      }
    } catch (Exception e) {
      log.info("PROBLEM in the method getSMSTemplate");
      e.printStackTrace();
      throw e;
    }
    log.info("END of the method getSMSTemplate");
    return smsTemplateDetails;
  }

  //========================================================================
  
  @Override
  public String deleteSMSTemplate(Long smsTemplateId) throws Exception {
    log.info("START of the method deleteSMSTemplate");
    String result = SUCCESS;
    SmsTemplateDetails smsTemplateDetails;
    
    try {
      if(smsTemplateId > 0) {
        smsTemplateDetails = (SmsTemplateDetails) getHibernateTemplate().get(SmsTemplateDetails.class, smsTemplateId);
        smsTemplateDetails.setStatus(IN_ACTIVE);
        getHibernateTemplate().save(smsTemplateDetails);
      }
    } catch (Exception e) {
      result = FAILURE;
      log.info("PROBLEM in the method deleteSMSTemplate");
      e.printStackTrace();
      throw e;
    }
    log.info("END of the method deleteSMSTemplate");
    return result;
  }

  //========================================================================
  
  @SuppressWarnings("unchecked")
  @Override
  public SmsTemplateDetails getSMSTemplateDetails(Long smsCategoryId) throws Exception {
    log.info("START of the method deleteSMSTemplate");
    StringBuffer hqlQuery;
    SmsTemplateDetails smsTemplateDetails = null;
    List<SmsTemplateDetails> templatesList = null;
    
    try {
      if(smsCategoryId != null && smsCategoryId.longValue() > 0) {
        hqlQuery = new StringBuffer(" SELECT smsTemplateDetials FROM SmsTemplateDetails as smsTemplateDetials WHERE smsTemplateDetials.status='"+ACTIVE+"' ");
        hqlQuery.append(" AND  smsTemplateDetials.smsCategoryDetails.smsCategoryId="+smsCategoryId);
        templatesList = getHibernateTemplate().find(hqlQuery.toString());
        
        if(templatesList != null && templatesList.size() > 0) {
          smsTemplateDetails = templatesList.get(0);
        }
      }
    } catch (Exception e) {
      log.info("PROBLEM in the method deleteSMSTemplate");
      e.printStackTrace();
      throw e;
    }
    log.info("END of the method deleteSMSTemplate");
    return smsTemplateDetails;
  }

  //========================================================================

}
