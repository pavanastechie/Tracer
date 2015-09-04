package com.tracer.dao.model;

import java.util.Date;

/**
 * UploadedCafDetails entity.
 * 
 * @author MyEclipse Persistence Tools
 */
/**
 * @author Jp
 */
public class UploadedCafDetails implements java.io.Serializable {
  private static final long serialVersionUID = 4319192361998756523L;
  
  // Fields
  private Long uploadCafDetailsId;
  private DistributorDetails distributorDetails;
  private String mobileNo;
  private String orderNumber;
  private String subAgentCode;
  private String cafType;
  private String connectionStatus;
  private String cafStatus;
  private String status;
  private Date dateTime;

  // Transient variables
  private String ofscCode;
  private String distributorName; 
  private long uploadedTimeStamp;
  private int recordId;
  
  // Constructors
  /** default constructor */
  public UploadedCafDetails() {
  }

  /** minimal constructor */
  public UploadedCafDetails(DistributorDetails distributorDetails,
      String mobileNo, String orderNumber, String cafStatus,
      String status, Date dateTime) {
    this.distributorDetails = distributorDetails;
    this.mobileNo = mobileNo;
    this.orderNumber = orderNumber;
    this.cafStatus = cafStatus;
    this.status = status;
    this.dateTime = dateTime;
  }

  /** full constructor */
  public UploadedCafDetails(DistributorDetails distributorDetails,
      String mobileNo, String orderNumber, String subAgentCode,
      String cafType, String connectionStatus, String cafStatus,
      String status, Date dateTime) {
    this.distributorDetails = distributorDetails;
    this.mobileNo = mobileNo;
    this.orderNumber = orderNumber;
    this.subAgentCode = subAgentCode;
    this.cafType = cafType;
    this.connectionStatus = connectionStatus;
    this.cafStatus = cafStatus;
    this.status = status;
    this.dateTime = dateTime;
  }

  // Property accessors
  public Long getUploadCafDetailsId() {
    return this.uploadCafDetailsId;
  }

  public void setUploadCafDetailsId(Long uploadCafDetailsId) {
    this.uploadCafDetailsId = uploadCafDetailsId;
  }

  public DistributorDetails getDistributorDetails() {
    return this.distributorDetails;
  }

  public void setDistributorDetails(DistributorDetails distributorDetails) {
    this.distributorDetails = distributorDetails;
  }

  public String getMobileNo() {
    return this.mobileNo;
  }

  public void setMobileNo(String mobileNo) {
    this.mobileNo = mobileNo;
  }

  public String getOrderNumber() {
    return this.orderNumber;
  }

  public void setOrderNumber(String orderNumber) {
    this.orderNumber = orderNumber;
  }

  public String getSubAgentCode() {
    return this.subAgentCode;
  }

  public void setSubAgentCode(String subAgentCode) {
    this.subAgentCode = subAgentCode;
  }

  public String getCafType() {
    return this.cafType;
  }

  public void setCafType(String cafType) {
    this.cafType = cafType;
  }

  public String getConnectionStatus() {
    return this.connectionStatus;
  }

  public void setConnectionStatus(String connectionStatus) {
    this.connectionStatus = connectionStatus;
  }

  public String getCafStatus() {
    return this.cafStatus;
  }

  public void setCafStatus(String cafStatus) {
    this.cafStatus = cafStatus;
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

  public String getOfscCode() {
    return ofscCode;
  }

  public void setOfscCode(String ofscCode) {
    this.ofscCode = ofscCode;
  }

  public String getDistributorName() {
    return distributorName;
  }

  public void setDistributorName(String distributorName) {
    this.distributorName = distributorName;
  }

  public long getUploadedTimeStamp() {
    return uploadedTimeStamp;
  }

  public void setUploadedTimeStamp(long uploadedTimeStamp) {
    this.uploadedTimeStamp = uploadedTimeStamp;
}

public int getRecordId() {
	return recordId;
}

public void setRecordId(int recordId) {
	this.recordId = recordId;
}
  
}