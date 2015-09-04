/**
 * @author Jp
 *
 */
package com.tracer.dao.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("rawtypes")
public class DistributorDetails implements java.io.Serializable {
  private static final long serialVersionUID = -1243472755979858318L;
  private Long distributorId;
  private UserDetails userDetails;
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
  private String status;
  private String photoPath;
  private String primaryContactNo;
  private String primaryEmail;
  private String secondaryContactNo;
  private String secondaryEmail;
  private Date dateTime;
  
  private Set distributorToHubDetailses = new HashSet(0);
  private Set beatPlanDistributorAssignmentses = new HashSet(0);
  
  /** default constructor */
  public DistributorDetails() {
  }

  /** minimal constructor */
  public DistributorDetails(UserDetails userDetails, String distributorName,
      String distributorCode, String distributorBarCode, String ofscCode,
      String lattitude, String longitude, String address,
      Integer country, String pinCode, String status, Date dateTime) {
    this.userDetails = userDetails;
    this.distributorName = distributorName;
    this.distributorCode = distributorCode;
    this.distributorBarCode = distributorBarCode;
    this.ofscCode = ofscCode;
    this.lattitude = lattitude;
    this.longitude = longitude;
    this.address = address;
    this.country = country;
    this.pinCode = pinCode;
    this.status = status;
    this.dateTime = dateTime;
  }

  /** full constructor */
  public DistributorDetails(UserDetails userDetails, String distributorName,
      String distributorCode, String distributorBarCode, String ofscCode,
      String lattitude, String longitude, String licenceInfo,
      String address, String location, String city, String state,
      String district, Integer country, String pinCode, String status,
      String photoPath, String primaryContactNo, String primaryEmail,
      String secondaryContactNo, String secondaryEmail, Date dateTime,
      Set distributorToHubDetailses, Set beatPlanDistributorAssignmentses) {
    this.userDetails = userDetails;
    this.distributorName = distributorName;
    this.distributorCode = distributorCode;
    this.distributorBarCode = distributorBarCode;
    this.ofscCode = ofscCode;
    this.lattitude = lattitude;
    this.longitude = longitude;
    this.licenceInfo = licenceInfo;
    this.address = address;
    this.location = location;
    this.city = city;
    this.state = state;
    this.district = district;
    this.country = country;
    this.pinCode = pinCode;
    this.status = status;
    this.photoPath = photoPath;
    this.primaryContactNo = primaryContactNo;
    this.primaryEmail = primaryEmail;
    this.secondaryContactNo = secondaryContactNo;
    this.secondaryEmail = secondaryEmail;
    this.dateTime = dateTime;
    this.distributorToHubDetailses = distributorToHubDetailses;
    this.beatPlanDistributorAssignmentses = beatPlanDistributorAssignmentses;
  }

  public Long getDistributorId() {
    return this.distributorId;
  }

  public void setDistributorId(Long distributorId) {
    this.distributorId = distributorId;
  }

  public UserDetails getUserDetails() {
    return this.userDetails;
  }

  public void setUserDetails(UserDetails userDetails) {
    this.userDetails = userDetails;
  }

  public String getDistributorName() {
    return this.distributorName;
  }

  public void setDistributorName(String distributorName) {
    this.distributorName = distributorName;
  }

  public String getDistributorCode() {
    return this.distributorCode;
  }

  public void setDistributorCode(String distributorCode) {
    this.distributorCode = distributorCode;
  }

  public String getDistributorBarCode() {
    return this.distributorBarCode;
  }

  public void setDistributorBarCode(String distributorBarCode) {
    this.distributorBarCode = distributorBarCode;
  }

  public String getOfscCode() {
    return this.ofscCode;
  }

  public void setOfscCode(String ofscCode) {
    this.ofscCode = ofscCode;
  }

  public String getLattitude() {
    return this.lattitude;
  }

  public void setLattitude(String lattitude) {
    this.lattitude = lattitude;
  }

  public String getLongitude() {
    return this.longitude;
  }

  public void setLongitude(String longitude) {
    this.longitude = longitude;
  }

  public String getLicenceInfo() {
    return this.licenceInfo;
  }

  public void setLicenceInfo(String licenceInfo) {
    this.licenceInfo = licenceInfo;
  }

  public String getAddress() {
    return this.address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getLocation() {
    return this.location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getCity() {
    return this.city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getState() {
    return this.state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getDistrict() {
    return this.district;
  }

  public void setDistrict(String district) {
    this.district = district;
  }

  public Integer getCountry() {
    return this.country;
  }

  public void setCountry(Integer country) {
    this.country = country;
  }

  public String getPinCode() {
    return this.pinCode;
  }

  public void setPinCode(String pinCode) {
    this.pinCode = pinCode;
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getPhotoPath() {
    return this.photoPath;
  }

  public void setPhotoPath(String photoPath) {
    this.photoPath = photoPath;
  }

  public String getPrimaryContactNo() {
    return this.primaryContactNo;
  }

  public void setPrimaryContactNo(String primaryContactNo) {
    this.primaryContactNo = primaryContactNo;
  }

  public String getPrimaryEmail() {
    return this.primaryEmail;
  }

  public void setPrimaryEmail(String primaryEmail) {
    this.primaryEmail = primaryEmail;
  }

  public String getSecondaryContactNo() {
    return this.secondaryContactNo;
  }

  public void setSecondaryContactNo(String secondaryContactNo) {
    this.secondaryContactNo = secondaryContactNo;
  }

  public String getSecondaryEmail() {
    return this.secondaryEmail;
  }

  public void setSecondaryEmail(String secondaryEmail) {
    this.secondaryEmail = secondaryEmail;
  }

  public Date getDateTime() {
    return this.dateTime;
  }

  public void setDateTime(Date dateTime) {
    this.dateTime = dateTime;
  }

  public Set getDistributorToHubDetailses() {
    return this.distributorToHubDetailses;
  }

  public void setDistributorToHubDetailses(Set distributorToHubDetailses) {
    this.distributorToHubDetailses = distributorToHubDetailses;
  }

  public Set getBeatPlanDistributorAssignmentses() {
    return this.beatPlanDistributorAssignmentses;
  }

  public void setBeatPlanDistributorAssignmentses(
      Set beatPlanDistributorAssignmentses) {
    this.beatPlanDistributorAssignmentses = beatPlanDistributorAssignmentses;
  }

  private String hubName;

  public String getHubName() {
    return hubName;
  }
  
  public void setHubName(String hubName) {
    this.hubName = hubName;
  }
}