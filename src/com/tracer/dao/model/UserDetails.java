/**
 * @author Jp
 *
 */
package com.tracer.dao.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
@SuppressWarnings("rawtypes")
public class UserDetails implements java.io.Serializable {
  private static final long serialVersionUID = 4319192361978956523L;
  private Long userId;
  private RoleDetails roleDetails;
  private String userCode;
  private String userName;
  private String password;
  private String lastName;
  private String name;
  private String gender;
  private Date dateOfBirth;
  private String maritalStatus;
  private String bloodGroup;
  private Date dateOfAnniversary;
  private String address;
  private String city;
  private String state;
  private String district;
  private String country;
  private String pinCode;
  private String photoPath;
  private String primaryContactNo;
  private String primaryEmail;
  private String secondaryContactNo;
  private String secondaryEmail;
  private String status;
  private Date dateTime;
  private String policeStationAddress;
  private String imeiNo;
  private String officePhone;
  private String nearestPoliceStation;
  private Set runnerVisittedLocationDetailses = new HashSet(0);
  private Set zoneDetailses = new HashSet(0);
  private Set beatPlanDetailses = new HashSet(0);
  private Set regionDetailses = new HashSet(0);
  private Set hubDetailses = new HashSet(0);
  private Set beatPlanUserAssignmentses = new HashSet(0);
  private Set distributorDetailses = new HashSet(0);
  private Set userToRegionDetailses = new HashSet(0);
  private Set userReportingToDetailses = new HashSet(0);
  private Set userToZoneDetailses = new HashSet(0);
  private Set smsTemplateDetailses = new HashSet(0);
  private Set circleDetailses = new HashSet(0);
  private Set userToCircleDetailses = new HashSet(0);
  private Set userToHubDetailses = new HashSet(0);
  private Set userAuthCodeDetailses = new HashSet(0);
  private Set userPasswordHistoryDetailses = new HashSet(0);

  /** default constructor */
  public UserDetails() {
  }

  /** minimal constructor */
  public UserDetails(RoleDetails roleDetails, String userCode,
      String userName, String password, String lastName, String name,
      String gender, Date dateOfBirth, String address,
      String primaryContactNo, String primaryEmail, String status,
      Date dateTime) {
    this.roleDetails = roleDetails;
    this.userCode = userCode;
    this.userName = userName;
    this.password = password;
    this.lastName = lastName;
    this.name = name;
    this.gender = gender;
    this.dateOfBirth = dateOfBirth;
    this.address = address;
    this.primaryContactNo = primaryContactNo;
    this.primaryEmail = primaryEmail;
    this.status = status;
    this.dateTime = dateTime;
  }

  /** full constructor */
  public UserDetails(RoleDetails roleDetails, String userCode,
      String userName, String password, String lastName, String name,
      String gender, Date dateOfBirth, String maritalStatus,
      String bloodGroup, Date dateOfAnniversary, String address,
      String city, String state, String district, String country,
      String pinCode, String photoPath, String primaryContactNo,
      String primaryEmail, String secondaryContactNo,
      String secondaryEmail, String status, Date dateTime, String imeiNo,
      String officePhone, String nearestPoliceStation, Set runnerVisittedLocationDetailses,
      Set userPasswordHistoryDetailses,
      Set zoneDetailses, Set beatPlanDetailses, Set regionDetailses,
      Set hubDetailses, Set beatPlanUserAssignmentses,
      Set distributorDetailses, Set userToRegionDetailses,
      Set userReportingToDetailses, Set userToZoneDetailses,
      Set smsTemplateDetailses, Set circleDetailses,
      Set userToCircleDetailses, Set userToHubDetailses,
      Set userAuthCodeDetailses) {
    this.roleDetails = roleDetails;
    this.userCode = userCode;
    this.userName = userName;
    this.password = password;
    this.lastName = lastName;
    this.name = name;
    this.gender = gender;
    this.dateOfBirth = dateOfBirth;
    this.maritalStatus = maritalStatus;
    this.bloodGroup = bloodGroup;
    this.dateOfAnniversary = dateOfAnniversary;
    this.address = address;
    this.city = city;
    this.state = state;
    this.district = district;
    this.country = country;
    this.pinCode = pinCode;
    this.photoPath = photoPath;
    this.primaryContactNo = primaryContactNo;
    this.primaryEmail = primaryEmail;
    this.secondaryContactNo = secondaryContactNo;
    this.secondaryEmail = secondaryEmail;
    this.status = status;
    this.dateTime = dateTime;
    this.imeiNo = imeiNo;
    this.officePhone = officePhone;
    this.nearestPoliceStation = nearestPoliceStation;
    this.runnerVisittedLocationDetailses = runnerVisittedLocationDetailses;
    this.zoneDetailses = zoneDetailses;
    this.userPasswordHistoryDetailses = userPasswordHistoryDetailses;
    this.beatPlanDetailses = beatPlanDetailses;
    this.regionDetailses = regionDetailses;
    this.hubDetailses = hubDetailses;
    this.beatPlanUserAssignmentses = beatPlanUserAssignmentses;
    this.distributorDetailses = distributorDetailses;
    this.userToRegionDetailses = userToRegionDetailses;
    this.userReportingToDetailses = userReportingToDetailses;
    this.userToZoneDetailses = userToZoneDetailses;
    this.smsTemplateDetailses = smsTemplateDetailses;
    this.circleDetailses = circleDetailses;
    this.userToCircleDetailses = userToCircleDetailses;
    this.userToHubDetailses = userToHubDetailses;
    this.userAuthCodeDetailses = userAuthCodeDetailses;
  }

  public Long getUserId() {
    return this.userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public RoleDetails getRoleDetails() {
    return this.roleDetails;
  }

  public void setRoleDetails(RoleDetails roleDetails) {
    this.roleDetails = roleDetails;
  }

  public String getUserCode() {
    return this.userCode;
  }

  public void setUserCode(String userCode) {
    this.userCode = userCode;
  }

  public String getUserName() {
    return this.userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getLastName() {
    return this.lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getGender() {
    return this.gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public Date getDateOfBirth() {
    return this.dateOfBirth;
  }

  public void setDateOfBirth(Date dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public String getMaritalStatus() {
    return this.maritalStatus;
  }

  public void setMaritalStatus(String maritalStatus) {
    this.maritalStatus = maritalStatus;
  }

  public String getBloodGroup() {
    return this.bloodGroup;
  }

  public void setBloodGroup(String bloodGroup) {
    this.bloodGroup = bloodGroup;
  }

  public Date getDateOfAnniversary() {
    return this.dateOfAnniversary;
  }

  public void setDateOfAnniversary(Date dateOfAnniversary) {
    this.dateOfAnniversary = dateOfAnniversary;
  }

  public String getAddress() {
    return this.address;
  }

  public void setAddress(String address) {
    this.address = address;
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

  public String getCountry() {
    return this.country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getPinCode() {
    return this.pinCode;
  }

  public void setPinCode(String pinCode) {
    this.pinCode = pinCode;
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

  public String getPoliceStationAddress() {
  return policeStationAddress;
  }

  public void setPoliceStationAddress(String policeStationAddress) {
  this.policeStationAddress = policeStationAddress;
  }
  
  public String getImeiNo() {
    return this.imeiNo;
  }

  public void setImeiNo(String imeiNo) {
    this.imeiNo = imeiNo;
  }

  public String getOfficePhone() {
    return this.officePhone;
  }

  public void setOfficePhone(String officePhone) {
    this.officePhone = officePhone;
  }

  public String getNearestPoliceStation() {
	return nearestPoliceStation;
  }

  public void setNearestPoliceStation(String nearestPoliceStation) {
	this.nearestPoliceStation = nearestPoliceStation;
  }

  public Set getRunnerVisittedLocationDetailses() {
    return this.runnerVisittedLocationDetailses;
  }

  public void setRunnerVisittedLocationDetailses(
      Set runnerVisittedLocationDetailses) {
    this.runnerVisittedLocationDetailses = runnerVisittedLocationDetailses;
  }

  public Set getZoneDetailses() {
    return this.zoneDetailses;
  }

  public void setZoneDetailses(Set zoneDetailses) {
    this.zoneDetailses = zoneDetailses;
  }

  public Set getBeatPlanDetailses() {
    return this.beatPlanDetailses;
  }

  public void setBeatPlanDetailses(Set beatPlanDetailses) {
    this.beatPlanDetailses = beatPlanDetailses;
  }

  public Set getRegionDetailses() {
    return this.regionDetailses;
  }

  public void setRegionDetailses(Set regionDetailses) {
    this.regionDetailses = regionDetailses;
  }

  public Set getHubDetailses() {
    return this.hubDetailses;
  }

  public void setHubDetailses(Set hubDetailses) {
    this.hubDetailses = hubDetailses;
  }

  public Set getBeatPlanUserAssignmentses() {
    return this.beatPlanUserAssignmentses;
  }

  public void setBeatPlanUserAssignmentses(Set beatPlanUserAssignmentses) {
    this.beatPlanUserAssignmentses = beatPlanUserAssignmentses;
  }

  public Set getDistributorDetailses() {
    return this.distributorDetailses;
  }

  public void setDistributorDetailses(Set distributorDetailses) {
    this.distributorDetailses = distributorDetailses;
  }

  public Set getUserToRegionDetailses() {
    return this.userToRegionDetailses;
  }

  public void setUserToRegionDetailses(Set userToRegionDetailses) {
    this.userToRegionDetailses = userToRegionDetailses;
  }

  public Set getUserReportingToDetailses() {
    return this.userReportingToDetailses;
  }

  public void setUserReportingToDetailses(Set userReportingToDetailses) {
    this.userReportingToDetailses = userReportingToDetailses;
  }

  public Set getUserToZoneDetailses() {
    return this.userToZoneDetailses;
  }

  public void setUserToZoneDetailses(Set userToZoneDetailses) {
    this.userToZoneDetailses = userToZoneDetailses;
  }

  public Set getSmsTemplateDetailses() {
    return this.smsTemplateDetailses;
  }

  public void setSmsTemplateDetailses(Set smsTemplateDetailses) {
    this.smsTemplateDetailses = smsTemplateDetailses;
  }

  public Set getCircleDetailses() {
    return this.circleDetailses;
  }

  public void setCircleDetailses(Set circleDetailses) {
    this.circleDetailses = circleDetailses;
  }

  public Set getUserToCircleDetailses() {
    return this.userToCircleDetailses;
  }

  public void setUserToCircleDetailses(Set userToCircleDetailses) {
    this.userToCircleDetailses = userToCircleDetailses;
  }

  public Set getUserToHubDetailses() {
    return this.userToHubDetailses;
  }

  public void setUserToHubDetailses(Set userToHubDetailses) {
    this.userToHubDetailses = userToHubDetailses;
  }

  public Set getUserAuthCodeDetailses() {
    return this.userAuthCodeDetailses;
  }

  public void setUserAuthCodeDetailses(Set userAuthCodeDetailses) {
    this.userAuthCodeDetailses = userAuthCodeDetailses;
  }

  private String reportingTo;

  public String getReportingTo() {
    return reportingTo;
  }
  
  public void setReportingTo(String reportingTo) {
    this.reportingTo = reportingTo;
  }

public Set getUserPasswordHistoryDetailses() {
	return userPasswordHistoryDetailses;
}

public void setUserPasswordHistoryDetailses(Set userPasswordHistoryDetailses) {
	this.userPasswordHistoryDetailses = userPasswordHistoryDetailses;
}
  
}