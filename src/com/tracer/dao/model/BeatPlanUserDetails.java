/**
 * @author Bhargava
 *
 */
package com.tracer.dao.model;

import java.io.Serializable;
import java.util.List;

public class BeatPlanUserDetails implements Serializable {
  private static final long serialVersionUID = 1L;
  private long userId;
  private String userName;
  private String role;
  private String mobileNumber;
  private List<DistributorVisitSchduleDetails> distributorVisitSchdule;
  private String errorMessage;

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public String getMobileNumber() {
    return mobileNumber;
  }

  public void setMobileNumber(String mobileNumber) {
    this.mobileNumber = mobileNumber;
  }

  public List<DistributorVisitSchduleDetails> getDistributorVisitSchdule() {
    return distributorVisitSchdule;
  }

  public void setDistributorVisitSchdule(List<DistributorVisitSchduleDetails> distributorVisitSchdule) {
    this.distributorVisitSchdule = distributorVisitSchdule;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

}
