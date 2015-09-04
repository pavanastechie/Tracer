/**
 * @author Jp
 *
 */
package com.tracer.webui.presentation.form;

import java.util.List;

import com.tracer.dao.model.CafCollectionDetails;
import com.tracer.dao.model.RunnerTrackDetails;
import com.tracer.dao.model.RunnerVisittedLocationDetails;
import com.tracer.dao.model.UserDetails;

public class TrackForm extends BaseForm {
  private static final long serialVersionUID = -2099944339677576811L;
  private List<RunnerTrackDetails> runnerTrackDetails;
  private List<RunnerVisittedLocationDetails> runnervisittedLocationDetails;
  private String runnerDate;
  private String runnerTime;
  private String runnerName;
  private String runnerId;
  private Long runnersHeadUserId;
  private Long runnerUserId;
  private String hubName;
  private String runnerCode;
  private String beatPlanName;
  private List<UserDetails> runnersList;
  private CafCollectionDetails cafCollectionDetails = null;
  
  public List<RunnerTrackDetails> getRunnerTrackDetails() {
    return runnerTrackDetails;
  }
  public void setRunnerTrackDetails(List<RunnerTrackDetails> runnerTrackDetails) {
    this.runnerTrackDetails = runnerTrackDetails;
  }
  public List<RunnerVisittedLocationDetails> getRunnervisittedLocationDetails() {
    return runnervisittedLocationDetails;
  }
  public void setRunnervisittedLocationDetails(
      List<RunnerVisittedLocationDetails> runnervisittedLocationDetails) {
    this.runnervisittedLocationDetails = runnervisittedLocationDetails;
  }
  public String getRunnerDate() {
    return runnerDate;
  }
  public void setRunnerDate(String runnerDate) {
    this.runnerDate = runnerDate;
  }
  public String getRunnerTime() {
    return runnerTime;
  }
  public void setRunnerTime(String runnerTime) {
    this.runnerTime = runnerTime;
  }
  public String getRunnerName() {
    return runnerName;
  }
  public void setRunnerName(String runnerName) {
    this.runnerName = runnerName;
  }
  public String getRunnerId() {
    return runnerId;
  }
  public void setRunnerId(String runnerId) {
    this.runnerId = runnerId;
  }
  public Long getRunnersHeadUserId() {
    return runnersHeadUserId;
  }
  public void setRunnersHeadUserId(Long runnersHeadUserId) {
    this.runnersHeadUserId = runnersHeadUserId;
  }
  public Long getRunnerUserId() {
    return runnerUserId;
  }
  public void setRunnerUserId(Long runnerUserId) {
    this.runnerUserId = runnerUserId;
  }
  public String getHubName() {
    return hubName;
  }
  public void setHubName(String hubName) {
    this.hubName = hubName;
  }
  public String getRunnerCode() {
    return runnerCode;
  }
  public void setRunnerCode(String runnerCode) {
    this.runnerCode = runnerCode;
  }
  public String getBeatPlanName() {
    return beatPlanName;
  }
  public void setBeatPlanName(String beatPlanName) {
    this.beatPlanName = beatPlanName;
  }
  public List<UserDetails> getRunnersList() {
    return runnersList;
  }
  public void setRunnersList(List<UserDetails> runnersList) {
    this.runnersList = runnersList;
  }
  public CafCollectionDetails getCafCollectionDetails() {
    return cafCollectionDetails;
  }
  public void setCafCollectionDetails(CafCollectionDetails cafCollectionDetails) {
    this.cafCollectionDetails = cafCollectionDetails;
  }
}