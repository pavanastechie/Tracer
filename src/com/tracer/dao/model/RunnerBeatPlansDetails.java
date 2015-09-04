/**
 * @author Jp
 *
 */
package com.tracer.dao.model;

public class RunnerBeatPlansDetails implements java.io.Serializable {
  private static final long serialVersionUID = -4059084105312555310L;
  private String beatPlanName;
  private String beatPlanCode;
  private Long beatPlanId;
  private String hubName;
  private String zoneName;
  private String regionName;
  private String circleName;

  public String getBeatPlanName() {
    return beatPlanName;
  }
  public void setBeatPlanName(String beatPlanName) {
    this.beatPlanName = beatPlanName;
  }
  public String getBeatPlanCode() {
    return beatPlanCode;
  }
  public void setBeatPlanCode(String beatPlanCode) {
    this.beatPlanCode = beatPlanCode;
  }
  public Long getBeatPlanId() {
    return beatPlanId;
  }
  public void setBeatPlanId(Long beatPlanId) {
    this.beatPlanId = beatPlanId;
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