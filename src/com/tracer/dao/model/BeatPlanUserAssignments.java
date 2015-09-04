/**
 * @author Jp
 *
 */
package com.tracer.dao.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class BeatPlanUserAssignments implements java.io.Serializable {
  private static final long serialVersionUID = 6687983618657877851L;
  private Long bpUserAssignmentId;
  private UserDetails userDetails;
  private BeatPlanDistributorAssignments beatPlanDistributorAssignments;
  private String status;
  private Date scheduleTime;
  private String visitNo;
  
  private Long distributorId;
  
  private String distributorName;
  private String beatPlanName;
  
  //To display in Track Module
  private String hubName;
  private String zoneName;
  private String regionName;
  private String circleName;
  
  @SuppressWarnings("rawtypes")
  private Set runnerVisitDetailses = new HashSet(0);

  /** default constructor */
  public BeatPlanUserAssignments() {
  }

  /** minimal constructor */
  public BeatPlanUserAssignments(UserDetails userDetails,
      BeatPlanDistributorAssignments beatPlanDistributorAssignments,
      String status, Date scheduleTime) {
    this.userDetails = userDetails;
    this.beatPlanDistributorAssignments = beatPlanDistributorAssignments;
    this.status = status;
    this.scheduleTime = scheduleTime;
  }

  /** full constructor */
  @SuppressWarnings("rawtypes")
  public BeatPlanUserAssignments(UserDetails userDetails,
      BeatPlanDistributorAssignments beatPlanDistributorAssignments,
      String status, Date scheduleTime, String visitNo,Set runnerVisitDetailses) {
    this.userDetails = userDetails;
    this.beatPlanDistributorAssignments = beatPlanDistributorAssignments;
    this.status = status;
    this.scheduleTime = scheduleTime;
    this.visitNo = visitNo;
    this.runnerVisitDetailses = runnerVisitDetailses;
  }

  public Long getBpUserAssignmentId() {
    return this.bpUserAssignmentId;
  }

  public void setBpUserAssignmentId(Long bpUserAssignmentId) {
    this.bpUserAssignmentId = bpUserAssignmentId;
  }

  public UserDetails getUserDetails() {
    return this.userDetails;
  }

  public void setUserDetails(UserDetails userDetails) {
    this.userDetails = userDetails;
  }

  public BeatPlanDistributorAssignments getBeatPlanDistributorAssignments() {
    return this.beatPlanDistributorAssignments;
  }

  public void setBeatPlanDistributorAssignments(
      BeatPlanDistributorAssignments beatPlanDistributorAssignments) {
    this.beatPlanDistributorAssignments = beatPlanDistributorAssignments;
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Date getScheduleTime() {
    return this.scheduleTime;
  }

  public void setScheduleTime(Date scheduleTime) {
    this.scheduleTime = scheduleTime;
  }

  @SuppressWarnings("rawtypes")
  public Set getRunnerVisitDetailses() {
    return this.runnerVisitDetailses;
  }

  @SuppressWarnings("rawtypes")
  public void setRunnerVisitDetailses(Set runnerVisitDetailses) {
    this.runnerVisitDetailses = runnerVisitDetailses;
  }

  public Long getDistributorId() {
    return distributorId;
  }
  
  public void setDistributorId(Long distributorId) {
    this.distributorId = distributorId;
  }
  
  public String getVisitNo() {
    return visitNo;
  }
  
  public void setVisitNo(String visitNo) {
    this.visitNo = visitNo;
  }

  public String getDistributorName() {
  return distributorName;
  }

  public void setDistributorName(String distributorName) {
  this.distributorName = distributorName;
  }

  public String getBeatPlanName() {
    return beatPlanName;
  }
  
  public void setBeatPlanName(String beatPlanName) {
    this.beatPlanName = beatPlanName;
  }

  public String getHubName() {
    return hubName;
  }
  
  public void setHubName(String hubName) {
    this.hubName = hubName;
  }
  
  public String getZoneName() {
    return zoneName;
  }
  
  public void setZoneName(String zoneName) {
    this.zoneName = zoneName;
  }
  
  public String getRegionName() {
    return regionName;
  }
  
  public void setRegionName(String regionName) {
    this.regionName = regionName;
  }
  
  public String getCircleName() {
    return circleName;
  }
  
  public void setCircleName(String circleName) {
    this.circleName = circleName;
  }
}