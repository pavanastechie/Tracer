/**
 * @author Jp
 *
 */
package com.tracer.webui.presentation.form;

import java.util.List;

import org.apache.struts.upload.FormFile;

import com.tracer.dao.model.HubDetails;

public class DistributorForm extends BaseForm {
  private static final long serialVersionUID = -2099944339677576811L;
  private Long distributorId;
  private String distributorName;
  private String distributorCode;
  private String distributorBarCode;
  private String ofscCode;
  private String lattitude;
  private String longitude;
  private String licenceInfo;
  private String address;
  private String location;
  private String city;
  private String state;
  private String district;
  private Integer country;
  private String pinCode;
  private String photoPath;
  private String primaryContactNo;
  private String primaryEmail;
  private String secondaryContactNo;
  private String secondaryEmail;
  private List<HubDetails> hubDetailsList;
  private String hubName;
  private boolean assignedToBeatPlan;
  private FormFile file;
  
  public FormFile getFile() {
		return file;
	}
	public void setFile(FormFile file) {
		this.file = file;
	}
	public String getHubName() {
    return hubName;
  }
  public void setHubName(String hubName) {
    this.hubName = hubName;
  }
  public Long getDistributorId() {
    return distributorId;
  }
  public void setDistributorId(Long distributorId) {
    this.distributorId = distributorId;
  }
  public String getDistributorName() {
    return distributorName;
  }
  public void setDistributorName(String distributorName) {
    this.distributorName = distributorName;
  }
  public String getDistributorCode() {
    return distributorCode;
  }
  public void setDistributorCode(String distributorCode) {
    this.distributorCode = distributorCode;
  }
  public String getDistributorBarCode() {
    return distributorBarCode;
  }
  public void setDistributorBarCode(String distributorBarCode) {
    this.distributorBarCode = distributorBarCode;
  }
  public String getOfscCode() {
    return ofscCode;
  }
  public void setOfscCode(String ofscCode) {
    this.ofscCode = ofscCode;
  }
  public String getLattitude() {
    return lattitude;
  }
  public void setLattitude(String latitude) {
    this.lattitude = latitude;
  }
  public String getLongitude() {
    return longitude;
  }
  public void setLongitude(String longitude) {
    this.longitude = longitude;
  }
  public String getLicenceInfo() {
    return licenceInfo;
  }
  public void setLicenceInfo(String licenceInfo) {
    this.licenceInfo = licenceInfo;
  }
  public String getAddress() {
    return address;
  }
  public void setAddress(String address) {
    this.address = address;
  }
  public String getLocation() {
    return location;
  }
  public void setLocation(String location) {
    this.location = location;
  }
  public String getCity() {
    return city;
  }
  public void setCity(String city) {
    this.city = city;
  }
  public String getState() {
    return state;
  }
  public void setState(String state) {
    this.state = state;
  }
  public String getDistrict() {
    return district;
  }
  public void setDistrict(String district) {
    this.district = district;
  }
  public Integer getCountry() {
    return country;
  }
  public void setCountry(Integer country) {
    this.country = country;
  }
  public String getPinCode() {
    return pinCode;
  }
  public void setPinCode(String pinCode) {
    this.pinCode = pinCode;
  }
  public String getPhotoPath() {
    return photoPath;
  }
  public void setPhotoPath(String photoPath) {
    this.photoPath = photoPath;
  }
  public String getPrimaryContactNo() {
    return primaryContactNo;
  }
  public void setPrimaryContactNo(String primaryContactNo) {
    this.primaryContactNo = primaryContactNo;
  }
  public String getPrimaryEmail() {
    return primaryEmail;
  }
  public void setPrimaryEmail(String primaryEmail) {
    this.primaryEmail = primaryEmail;
  }
  public String getSecondaryContactNo() {
    return secondaryContactNo;
  }
  public void setSecondaryContactNo(String secondaryContactNo) {
    this.secondaryContactNo = secondaryContactNo;
  }
  public String getSecondaryEmail() {
    return secondaryEmail;
  }
  public List<HubDetails> getHubDetailsList() {
    return hubDetailsList;
  }
  public void setHubDetailsList(List<HubDetails> hubDetailsList) {
    this.hubDetailsList = hubDetailsList;
  }
  public void setSecondaryEmail(String secondaryEmail) {
    this.secondaryEmail = secondaryEmail;
  }
public boolean isAssignedToBeatPlan() {
	return assignedToBeatPlan;
}
public void setAssignedToBeatPlan(boolean assignedToBeatPlan) {
	this.assignedToBeatPlan = assignedToBeatPlan;
}

}
