/**
 * @author Jp
 *
 */
package com.tracer.dao.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class BeatPlanDetails implements java.io.Serializable {
  private static final long serialVersionUID = -5473128338661952778L;
  private Long beatPlanId;
  private UserDetails userDetails;
  private String beatPlanName;
  private String beatPlanType;
  private String beatPlanCode;
  private String status;
  private Date dateTime;
  @SuppressWarnings("rawtypes")
  private Set beatPlanDistributorAssignmentses = new HashSet(0);

  /** default constructor */
  public BeatPlanDetails() {
  }

  /** minimal constructor */
  public BeatPlanDetails(UserDetails userDetails, String beatPlanType,
      String beatPlanCode, String status, Date dateTime) {
    this.userDetails = userDetails;
    this.beatPlanType = beatPlanType;
    this.beatPlanCode = beatPlanCode;
    this.status = status;
    this.dateTime = dateTime;
  }

  /** full constructor */
  @SuppressWarnings("rawtypes")
  public BeatPlanDetails(UserDetails userDetails, String beatPlanName,
      String beatPlanType, String beatPlanCode, String status,
      Date dateTime, Set beatPlanDistributorAssignmentses) {
    this.userDetails = userDetails;
    this.beatPlanName = beatPlanName;
    this.beatPlanType = beatPlanType;
    this.beatPlanCode = beatPlanCode;
    this.status = status;
    this.dateTime = dateTime;
    this.beatPlanDistributorAssignmentses = beatPlanDistributorAssignmentses;
  }

  public Long getBeatPlanId() {
    return this.beatPlanId;
  }

  public void setBeatPlanId(Long beatPlanId) {
    this.beatPlanId = beatPlanId;
  }

  public UserDetails getUserDetails() {
    return this.userDetails;
  }

  public void setUserDetails(UserDetails userDetails) {
    this.userDetails = userDetails;
  }

  public String getBeatPlanName() {
    return this.beatPlanName;
  }

  public void setBeatPlanName(String beatPlanName) {
    this.beatPlanName = beatPlanName;
  }

  public String getBeatPlanType() {
    return this.beatPlanType;
  }

  public void setBeatPlanType(String beatPlanType) {
    this.beatPlanType = beatPlanType;
  }

  public String getBeatPlanCode() {
    return this.beatPlanCode;
  }

  public void setBeatPlanCode(String beatPlanCode) {
    this.beatPlanCode = beatPlanCode;
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

  @SuppressWarnings("rawtypes")
  public Set getBeatPlanDistributorAssignmentses() {
    return this.beatPlanDistributorAssignmentses;
  }

  @SuppressWarnings("rawtypes")
  public void setBeatPlanDistributorAssignmentses(
      Set beatPlanDistributorAssignmentses) {
    this.beatPlanDistributorAssignmentses = beatPlanDistributorAssignmentses;
  }

}