package com.data.tracer;

// Generated 15 Jun, 2015 3:57:49 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * SmsTemplateDetails generated by hbm2java
 */
public class SmsTemplateDetails implements java.io.Serializable {

	private Long smsTemplateId;
	private UserDetails userDetails;
	private SmsCategoryDetails smsCategoryDetails;
	private String smsTemplate;
	private char status;
	private Date dateTime;

	public SmsTemplateDetails() {
	}

	public SmsTemplateDetails(UserDetails userDetails,
			SmsCategoryDetails smsCategoryDetails, String smsTemplate,
			char status, Date dateTime) {
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

	public char getStatus() {
		return this.status;
	}

	public void setStatus(char status) {
		this.status = status;
	}

	public Date getDateTime() {
		return this.dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

}