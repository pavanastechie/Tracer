/**
 * @author Dileepkumar
 */

package com.tracer.webui.presentation.form;

import org.apache.struts.upload.FormFile;
import com.tracer.dao.model.DistributorDetails;

public class UploadForm extends BaseForm {
  private static final long serialVersionUID = -1243472755975898318L;
  private Long uploadCafDetailsId;
  private DistributorDetails distributorDetails;
  private String mobileNo;
  private String orderNumber;
  private String subAgentCode;
  private String cafType;
  private String connectionStatus;
  private String cafStatus;
  private String status;
  private String dateTime;
  private FormFile file;
  private String ofscCode;
  private String distributorName;
  private int recordId;
  private String formattedDate;
    
  public Long getUploadCafDetailsId() {
    return uploadCafDetailsId;
  }
  public void setUploadCafDetailsId(Long uploadCafDetailsId) {
    this.uploadCafDetailsId = uploadCafDetailsId;
  }
  public DistributorDetails getDistributorDetails() {
    return distributorDetails;
  }
  public void setDistributorDetails(DistributorDetails distributorDetails) {
    this.distributorDetails = distributorDetails;
  }
  public String getMobileNo() {
    return mobileNo;
  }
  public void setMobileNo(String mobileNo) {
    this.mobileNo = mobileNo;
  }
  public String getOrderNumber() {
    return orderNumber;
  }
  public void setOrderNumber(String orderNumber) {
    this.orderNumber = orderNumber;
  }
  public String getSubAgentCode() {
    return subAgentCode;
  }
  public void setSubAgentCode(String subAgentCode) {
    this.subAgentCode = subAgentCode;
  }
  public String getCafType() {
    return cafType;
  }
  public void setCafType(String cafType) {
    this.cafType = cafType;
  }
  public String getConnectionStatus() {
    return connectionStatus;
  }
  public void setConnectionStatus(String connectionStatus) {
    this.connectionStatus = connectionStatus;
  }
  public String getCafStatus() {
    return cafStatus;
  }
  public void setCafStatus(String cafStatus) {
    this.cafStatus = cafStatus;
  }
  public String getStatus() {
    return status;
  }
  public void setStatus(String status) {
    this.status = status;
  }
  public String getDateTime() {
    return dateTime;
  }
  public void setDateTime(String dateTime) {
    this.dateTime = dateTime;
  }
  public FormFile getFile() {
    return file;
  }
  public void setFile(FormFile file) {
    this.file = file;
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
  public int getRecordId() {
    return recordId;
  }
  public void setRecordId(int recordId) {
    this.recordId = recordId;
  }
  public String getFormattedDate() {
    return formattedDate;
  }
  public void setFormattedDate(String formattedDate) {
    this.formattedDate = formattedDate;
  }
}
