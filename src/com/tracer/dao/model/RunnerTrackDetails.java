/**
 * @author Jp
 *
 */
package com.tracer.dao.model;

import java.util.Date;

public class RunnerTrackDetails implements java.io.Serializable {
  private static final long serialVersionUID = -9064265510726786106L;
  private Long beatPlanUserAssignmentId = null;
  private Long runnerId = null;
  private Date scheduleTime = null;
  private Date visittedTime = null;
  private String distributorName = null;
  private Integer totalCafCount = null;
  private Integer visitNo = null;
  private long delayTime = 0;
  private String visittedDateTime = null;
  private String scheduleDateTime = null;
  private Long cafCollectionId;
  private String remarks;
  private boolean earlyVisit = false;
  private String distributorLattitude = null;
  private String distributorLongitude = null;
  private int distributorNo;
  private String distributorOfscCode = null;

  public String getDelayHours() {
    return delayHours;
  }

  public void setDelayHours(String delayHours) {
    this.delayHours = delayHours;
  }

  private String delayHours = null;

  public String getVisittedDateTime() {
    return visittedDateTime;
  }

  public void setVisittedDateTime(String visittedDateTime) {
    this.visittedDateTime = visittedDateTime;
  }

  public String getScheduleDateTime() {
    return scheduleDateTime;
  }

  public void setScheduleDateTime(String scheduleDateTime) {
    this.scheduleDateTime = scheduleDateTime;
  }

  public Long getBeatPlanUserAssignmentId() {
    return beatPlanUserAssignmentId;
  }

  public void setBeatPlanUserAssignmentId(Long beatPlanUserAssignmentId) {
    this.beatPlanUserAssignmentId = beatPlanUserAssignmentId;
  }

  public Long getRunnerId() {
    return runnerId;
  }

  public void setRunnerId(Long runnerId) {
    this.runnerId = runnerId;
  }

  public Date getScheduleTime() {
    return scheduleTime;
  }

  public void setScheduleTime(Date scheduleTime) {
    this.scheduleTime = scheduleTime;
  }

  public Date getVisittedTime() {
    return visittedTime;
  }

  public void setVisittedTime(Date visittedTime) {
    this.visittedTime = visittedTime;
  }

  public String getDistributorName() {
    return distributorName;
  }

  public void setDistributorName(String distributorName) {
    this.distributorName = distributorName;
  }

  public Integer getTotalCafCount() {
    return totalCafCount;
  }

  public void setTotalCafCount(Integer totalCafCount) {
    this.totalCafCount = totalCafCount;
  }

  public Integer getVisitNo() {
    return visitNo;
  }

  public void setVisitNo(Integer visitNo) {
    this.visitNo = visitNo;
  }

  public long getDelayTime() {
    return delayTime;
  }

  public void setDelayTime(long delayTime) {
    this.delayTime = delayTime;
  }

  public Long getCafCollectionId() {
    return cafCollectionId;
  }

  public void setCafCollectionId(Long cafCollectionId) {
    this.cafCollectionId = cafCollectionId;
  }

  public String getRemarks() {
    return remarks;
  }

  public void setRemarks(String remarks) {
    this.remarks = remarks;
  }

  public boolean isEarlyVisit() {
    return earlyVisit;
  }

  public void setEarlyVisit(boolean earlyVisit) {
    this.earlyVisit = earlyVisit;
  }

  public String getDistributorLattitude() {
    return distributorLattitude;
  }

  public void setDistributorLattitude(String distributorLattitude) {
    this.distributorLattitude = distributorLattitude;
  }

  public String getDistributorLongitude() {
    return distributorLongitude;
  }

  public void setDistributorLongitude(String distributorLongitude) {
    this.distributorLongitude = distributorLongitude;
  }

  public int getDistributorNo() {
    return distributorNo;
  }

  public void setDistributorNo(int distributorNo) {
    this.distributorNo = distributorNo;
  }

  public String getDistributorOfscCode() {
    return distributorOfscCode;
  }

  public void setDistributorOfscCode(String distributorOfscCode) {
    this.distributorOfscCode = distributorOfscCode;
  }
}