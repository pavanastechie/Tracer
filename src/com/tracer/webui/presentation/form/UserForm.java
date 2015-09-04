/**
 * @author Jp
 *
 */
package com.tracer.webui.presentation.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.tracer.dao.model.CircleDetails;
import com.tracer.dao.model.HubDetails;
import com.tracer.dao.model.RegionDetails;
import com.tracer.dao.model.RoleDetails;
import com.tracer.dao.model.UserDetails;
import com.tracer.dao.model.ZoneDetails;

public class UserForm extends BaseForm {
  private static final long serialVersionUID = -2099944339677576811L;

  private Long userId;
  private String roleName;
  private Long hubId;
  private Long zoneId;
  private Long regionId;
  private Long circleId;
  private String userCode;
  private Long reportingToUserID;
  private String userName;
  private String password;
  private String newPassword;
  private String confirmNewPassword;
  private String currentPassword;
  private String lastName;
  private String name;
  private String gender;
  private String dateOfBirth;
  private String maritalStatus;
  private String bloodGroup;
  private String dateOfAnniversary;
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
  private String imeiNo;
  private String policeStationAddress;
  private String idProof;
  private String officePhone;
  private String nearestPoliceStation;
  private String status = "1";

  private RoleDetails roleDetails;
  private List<HubDetails> hubDetailsList;
  private List<ZoneDetails> zoneDetailsList;
  private List<RegionDetails> regionDetailsList;
  private List<CircleDetails> circleDetailsList;
  private List<UserDetails> userReportingToList;
  private FormFile file = null;
   

  public void reset(ActionMapping mapping, HttpServletRequest request) {
    this.currentPassword = null;
    this.newPassword = null;
    this.confirmNewPassword = null;
  }

  /** Setters and Getters **/

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

  public String getUserCode() {
    return userCode;
  }

  public void setUserCode(String userCode) {
    this.userCode = userCode;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(String dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public String getMaritalStatus() {
    return maritalStatus;
  }

  public void setMaritalStatus(String maritalStatus) {
    this.maritalStatus = maritalStatus;
  }

  public String getBloodGroup() {
    return bloodGroup;
  }

  public void setBloodGroup(String bloodGroup) {
    this.bloodGroup = bloodGroup;
  }

  public String getDateOfAnniversary() {
    return dateOfAnniversary;
  }

  public void setDateOfAnniversary(String dateOfAnniversary) {
    this.dateOfAnniversary = dateOfAnniversary;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
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

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
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

  public Long getHubId() {
    return hubId;
  }

  public void setHubId(Long hubId) {
    this.hubId = hubId;
  }
  
  public Long getZoneId() {
    return zoneId;
  }

  public void setZoneId(Long zoneId) {
    this.zoneId = zoneId;
  }

  public Long getRegionId() {
    return regionId;
  }

  public void setRegionId(Long regionId) {
    this.regionId = regionId;
  }

  public Long getCircleId() {
    return circleId;
  }

  public void setCircleId(Long circleId) {
    this.circleId = circleId;
  }


  public Long getReportingToUserID() {
    return reportingToUserID;
  }

  public void setReportingToUserID(Long reportingToUserID) {
    this.reportingToUserID = reportingToUserID;
  }

  public String getPoliceStationAddress() {
    return policeStationAddress;
  }

  public void setPoliceStationAddress(String policeStationAddress) {
    this.policeStationAddress = policeStationAddress;
  }

  public String getIdProof() {
    return idProof;
  }

  public void setIdProof(String idProof) {
    this.idProof = idProof;
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

  public void setSecondaryEmail(String secondaryEmail) {
    this.secondaryEmail = secondaryEmail;
  }

  public String getImeiNo() {
    return imeiNo;
  }

  public void setImeiNo(String imeiNo) {
    this.imeiNo = imeiNo;
  }

  public String getOfficePhone() {
    return officePhone;
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

  public RoleDetails getRoleDetails() {
    return roleDetails;
  }

  public void setRoleDetails(RoleDetails roleDetails) {
    this.roleDetails = roleDetails;
  }

  public List<HubDetails> getHubDetailsList() {
    return hubDetailsList;
  }

  public void setHubDetailsList(List<HubDetails> hubDetailsList) {
    this.hubDetailsList = hubDetailsList;
  }

  public List<UserDetails> getUserReportingToList() {
    return userReportingToList;
  }

  public void setUserReportingToList(List<UserDetails> userReportingToList) {
    this.userReportingToList = userReportingToList;
  }

  public String getConfirmNewPassword() {
    return confirmNewPassword;
  }

  public void setConfirmNewPassword(String confirmNewPassword) {
    this.confirmNewPassword = confirmNewPassword;
  }

  public String getNewPassword() {
    return newPassword;
  }

  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }

  public String getCurrentPassword() {
    return currentPassword;
  }

  public void setCurrentPassword(String currentPassword) {
    this.currentPassword = currentPassword;
  }

  public List<ZoneDetails> getZoneDetailsList() {
    return zoneDetailsList;
  }

  public void setZoneDetailsList(List<ZoneDetails> zoneDetailsList) {
    this.zoneDetailsList = zoneDetailsList;
  }

  public List<RegionDetails> getRegionDetailsList() {
    return regionDetailsList;
  }

  public void setRegionDetailsList(List<RegionDetails> regionDetailsList) {
    this.regionDetailsList = regionDetailsList;
  }

  public List<CircleDetails> getCircleDetailsList() {
    return circleDetailsList;
  }

  public void setCircleDetailsList(List<CircleDetails> circleDetailsList) {
    this.circleDetailsList = circleDetailsList;
  }
  public void setFile(FormFile file) {
    this.file = file;
  }

  public FormFile getFile() {
    return this.file;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

}