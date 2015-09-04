/**
 * @author Jp
 *
 */
package com.tracer.dao.model;

import java.util.Date;

public class RunnerOfflineVisitDetails implements java.io.Serializable {
  private static final long serialVersionUID = -493405148913294541L;
  private Long runnerOfflineVisitId;
  private String offlineVisitCode;
  private String distributorCode;
  private String beatPlanCode;
  private String runnerCode;
  private String status;
  private Date visitTimeStamp;

  /** default constructor */
  public RunnerOfflineVisitDetails() {
  }

  /** full constructor */
  public RunnerOfflineVisitDetails(String offlineVisitCode,
      String distributorCode, String beatPlanCode, String runnerCode,
      String status, Date visitTimeStamp) {
    this.offlineVisitCode = offlineVisitCode;
    this.distributorCode = distributorCode;
    this.beatPlanCode = beatPlanCode;
    this.runnerCode = runnerCode;
    this.status = status;
    this.visitTimeStamp = visitTimeStamp;
  }

  public Long getRunnerOfflineVisitId() {
    return this.runnerOfflineVisitId;
  }

  public void setRunnerOfflineVisitId(Long runnerOfflineVisitId) {
    this.runnerOfflineVisitId = runnerOfflineVisitId;
  }

  public String getOfflineVisitCode() {
    return this.offlineVisitCode;
  }

  public void setOfflineVisitCode(String offlineVisitCode) {
    this.offlineVisitCode = offlineVisitCode;
  }

  public String getDistributorCode() {
    return this.distributorCode;
  }

  public void setDistributorCode(String distributorCode) {
    this.distributorCode = distributorCode;
  }

  public String getBeatPlanCode() {
    return this.beatPlanCode;
  }

  public void setBeatPlanCode(String beatPlanCode) {
    this.beatPlanCode = beatPlanCode;
  }

  public String getRunnerCode() {
    return this.runnerCode;
  }

  public void setRunnerCode(String runnerCode) {
    this.runnerCode = runnerCode;
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Date getVisitTimeStamp() {
    return this.visitTimeStamp;
  }

  public void setVisitTimeStamp(Date visitTimeStamp) {
    this.visitTimeStamp = visitTimeStamp;
  }

}