/**
 * @author Jp
 */
package com.tracer.dao.model;

import java.io.Serializable;

public class PerformanceReportDetails implements Serializable {
  private static final long serialVersionUID = 4678550547295645566L;
  private String zoneName = "";
  private String hubName = "";
  private String runnerName;
  private String distributorName;
  private String ofscCode;
  private String scheduleTime;
  private String visitedTime;
  private int cafsCollected = 0;
  private String date;

  public String getZoneName() {
    return zoneName;
  }

  public void setZoneName(String zoneName) {
    this.zoneName = zoneName;
  }

  public String getHubName() {
    return hubName;
  }

  public void setHubName(String hubName) {
    this.hubName = hubName;
  }

  public String getRunnerName() {
    return runnerName;
  }

  public void setRunnerName(String runnerName) {
    this.runnerName = runnerName;
  }

  public String getDistributorName() {
    return distributorName;
  }

  public void setDistributorName(String distributorName) {
    this.distributorName = distributorName;
  }

  public String getOfscCode() {
    return ofscCode;
  }

  public void setOfscCode(String ofscCode) {
    this.ofscCode = ofscCode;
  }

  public String getScheduleTime() {
    return scheduleTime;
  }

  public void setScheduleTime(String scheduleTime) {
    this.scheduleTime = scheduleTime;
  }

  public String getVisitedTime() {
    return visitedTime;
  }

  public void setVisitedTime(String visitedTime) {
    this.visitedTime = visitedTime;
  }

  public int getCafsCollected() {
    return cafsCollected;
  }

  public void setCafsCollected(int cafsCollected) {
    this.cafsCollected = cafsCollected;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

}