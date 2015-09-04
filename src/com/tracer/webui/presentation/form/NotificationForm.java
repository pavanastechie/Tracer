/**
 * @author Jp
 *
 */
package com.tracer.webui.presentation.form;

import java.util.List;

import com.tracer.dao.model.SmsCategoryDetails;

public class NotificationForm extends BaseForm {
	private static final long serialVersionUID = -2099944339677576811L;
	private String smsContent;
	private String smsCategoryName;
	private Long smsTemplateId;
	private Long smsCategoryId;
	
	private List<SmsCategoryDetails> categoryDetails;

	public Long getSmsTemplateId() {
		return smsTemplateId;
	}
	public void setSmsTemplateId(Long smsTemplateId) {
		this.smsTemplateId = smsTemplateId;
	}
	public Long getSmsCategoryId() {
		return smsCategoryId;
	}
	public void setSmsCategoryId(Long smsCategoryId) {
		this.smsCategoryId = smsCategoryId;
	}

	public String getSmsContent() {
		return smsContent;
	}
	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}

	public String getSmsCategoryName() {
	  return smsCategoryName;
  }
	public void setSmsCategoryName(String smsCategoryName) {
	  this.smsCategoryName = smsCategoryName;
  }
	public List<SmsCategoryDetails> getCategoryDetails() {
		return categoryDetails;
	}
	public void setCategoryDetails(List<SmsCategoryDetails> categoryDetails) {
		this.categoryDetails = categoryDetails;
	}
}
