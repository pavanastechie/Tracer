/**
 * @author Jp
 *
 */
package com.tracer.dao.model;

import java.util.Date;

public class SmsTemplateDetails implements java.io.Serializable {
  private static final long serialVersionUID = 4701958727094413506L;
  private Long smsTemplateId;
  private UserDetails userDetails;
  private SmsCategoryDetails smsCategoryDetails;
  private String smsTemplate;
  private String status;
  private Date dateTime;

  /** default constructor */
  public SmsTemplateDetails() {
  }

  /** full constructor */
  public SmsTemplateDetails(UserDetails userDetails, SmsCategoryDetails smsCategoryDetails, 
      String smsTemplate, String status, Date dateTime) {
    this.userDetails = userDetails;
    this.smsCategoryDetails = smsCategoryDetails;
    this.smsTemplate = smsTemplate;
    this.status = status;
    this.dateTime = dateTime;
  }

  public Long getSmsTemplateId() {
    return this.smsTemplateId;
  }

  public void setSmsTemplateId(Long smsTemplateId) {
    this.smsTemplateId = smsTemplateId;
  }

  public UserDetails getUserDetails() {
    return this.userDetails;
  }

  public void setUserDetails(UserDetails userDetails) {
    this.userDetails = userDetails;
  }

  public SmsCategoryDetails getSmsCategoryDetails() {
    return this.smsCategoryDetails;
  }

  public void setSmsCategoryDetails(SmsCategoryDetails smsCategoryDetails) {
    this.smsCategoryDetails = smsCategoryDetails;
  }

  public String getSmsTemplate() {
    return this.smsTemplate;
  }

  public void setSmsTemplate(String smsTemplate) {
    this.smsTemplate = smsTemplate;
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Date getDateTime() {
    return this.dateTime;
  }

  public void setDateTime(Date dateTime) {
    this.dateTime = dateTime;
  }

}