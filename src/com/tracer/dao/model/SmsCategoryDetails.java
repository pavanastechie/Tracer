/**
 * @author Jp
 *
 */
package com.tracer.dao.model;

import java.util.HashSet;
import java.util.Set;

public class SmsCategoryDetails implements java.io.Serializable {
  private static final long serialVersionUID = -8686986367637995184L;
  private Long smsCategoryId;
  private String smsCategoryName;
  private String status;
  @SuppressWarnings("rawtypes")
  private Set smsTemplateDetailses = new HashSet(0);

  /** default constructor */
  public SmsCategoryDetails() {
  }

  /** minimal constructor */
  public SmsCategoryDetails(String smsCategoryName, String status) {
    this.smsCategoryName = smsCategoryName;
    this.status = status;
  }

  /** full constructor */
  @SuppressWarnings("rawtypes")
  public SmsCategoryDetails(String smsCategoryName, String status,
      Set smsTemplateDetailses) {
    this.smsCategoryName = smsCategoryName;
    this.status = status;
    this.smsTemplateDetailses = smsTemplateDetailses;
  }

  public Long getSmsCategoryId() {
    return this.smsCategoryId;
  }

  public void setSmsCategoryId(Long smsCategoryId) {
    this.smsCategoryId = smsCategoryId;
  }

  public String getSmsCategoryName() {
    return this.smsCategoryName;
  }

  public void setSmsCategoryName(String smsCategoryName) {
    this.smsCategoryName = smsCategoryName;
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  @SuppressWarnings("rawtypes")
  public Set getSmsTemplateDetailses() {
    return this.smsTemplateDetailses;
  }

  @SuppressWarnings("rawtypes")
  public void setSmsTemplateDetailses(Set smsTemplateDetailses) {
    this.smsTemplateDetailses = smsTemplateDetailses;
  }

}