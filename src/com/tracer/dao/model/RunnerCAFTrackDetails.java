package com.tracer.dao.model;

public class RunnerCAFTrackDetails implements java.io.Serializable {
  private static final long serialVersionUID = -499405148913294541L;
  private Long runnerUserId;
  private String runnerName;
  private int totalVisits;
  private int totalVisitsCompleted;
  private int totalCAFCollected;
  
  private int slot1Target;
  private int slot1Achieved;
  
  private int slot2Target;
  private int slot2Achieved;
  
  private int slot3Target;
  private int slot3Achieved;
  
  private int slot4Target;
  private int slot4Achieved;
  
  private int slot5Target;
  private int slot5Achieved;
  
  private String slot1TargetPercentage;
  private String slot1AchievedPercentage;

  private String slot2TargetPercentage;
  private String slot2AchievedPercentage;
  
  private String slot3TargetPercentage;
  private String slot3AchievedPercentage;
  
  private String slot4TargetPercentage;
  private String slot4AchievedPercentage;
  
  private String slot5TargetPercentage;
  private String slot5AchievedPercentage;
  
  public Long getRunnerUserId() {
    return runnerUserId;
  }
  public void setRunnerUserId(Long runnerUserId) {
    this.runnerUserId = runnerUserId;
  }
  public String getRunnerName() {
    return runnerName;
  }
  public void setRunnerName(String runnerName) {
    this.runnerName = runnerName;
  }
  public int getTotalVisits() {
    return totalVisits;
  }
  public void setTotalVisits(int totalVisits) {
    this.totalVisits = totalVisits;
  }
  
  public int getTotalVisitsCompleted() {
    return totalVisitsCompleted;
  }
  public void setTotalVisitsCompleted(int totalVisitsCompleted) {
    this.totalVisitsCompleted = totalVisitsCompleted;
  }
  public int getTotalCAFCollected() {
    return totalCAFCollected;
  }
  public void setTotalCAFCollected(int totalCAFCollected) {
    this.totalCAFCollected = totalCAFCollected;
  }
  public int getSlot1Target() {
    return slot1Target;
  }
  public void setSlot1Target(int slot1Target) {
    this.slot1Target = slot1Target;
  }
  public int getSlot1Achieved() {
    return slot1Achieved;
  }
  public void setSlot1Achieved(int slot1Achieved) {
    this.slot1Achieved = slot1Achieved;
  }
  public int getSlot2Target() {
    return slot2Target;
  }
  public void setSlot2Target(int slot2Target) {
    this.slot2Target = slot2Target;
  }
  public int getSlot2Achieved() {
    return slot2Achieved;
  }
  public void setSlot2Achieved(int slot2Achieved) {
    this.slot2Achieved = slot2Achieved;
  }
  public int getSlot3Target() {
    return slot3Target;
  }
  public void setSlot3Target(int slot3Target) {
    this.slot3Target = slot3Target;
  }
  public int getSlot3Achieved() {
    return slot3Achieved;
  }
  public void setSlot3Achieved(int slot3Achieved) {
    this.slot3Achieved = slot3Achieved;
  }
  public int getSlot4Target() {
    return slot4Target;
  }
  public void setSlot4Target(int slot4Target) {
    this.slot4Target = slot4Target;
  }
  public int getSlot4Achieved() {
    return slot4Achieved;
  }
  public void setSlot4Achieved(int slot4Achieved) {
    this.slot4Achieved = slot4Achieved;
  }
  public int getSlot5Target() {
    return slot5Target;
  }
  public void setSlot5Target(int slot5Target) {
    this.slot5Target = slot5Target;
  }
  public int getSlot5Achieved() {
    return slot5Achieved;
  }
  public void setSlot5Achieved(int slot5Achieved) {
    this.slot5Achieved = slot5Achieved;
  }
  public String getSlot1TargetPercentage() {
    return slot1TargetPercentage;
  }
  public void setSlot1TargetPercentage(String slot1TargetPercentage) {
    this.slot1TargetPercentage = slot1TargetPercentage;
  }
  public String getSlot1AchievedPercentage() {
    return slot1AchievedPercentage;
  }
  public void setSlot1AchievedPercentage(String slot1AchievedPercentage) {
    this.slot1AchievedPercentage = slot1AchievedPercentage;
  }
  public String getSlot2TargetPercentage() {
    return slot2TargetPercentage;
  }
  public void setSlot2TargetPercentage(String slot2TargetPercentage) {
    this.slot2TargetPercentage = slot2TargetPercentage;
  }
  public String getSlot2AchievedPercentage() {
    return slot2AchievedPercentage;
  }
  public void setSlot2AchievedPercentage(String slot2AchievedPercentage) {
    this.slot2AchievedPercentage = slot2AchievedPercentage;
  }
  public String getSlot3TargetPercentage() {
    return slot3TargetPercentage;
  }
  public void setSlot3TargetPercentage(String slot3TargetPercentage) {
    this.slot3TargetPercentage = slot3TargetPercentage;
  }
  public String getSlot3AchievedPercentage() {
    return slot3AchievedPercentage;
  }
  public void setSlot3AchievedPercentage(String slot3AchievedPercentage) {
    this.slot3AchievedPercentage = slot3AchievedPercentage;
  }
  public String getSlot4TargetPercentage() {
    return slot4TargetPercentage;
  }
  public void setSlot4TargetPercentage(String slot4TargetPercentage) {
    this.slot4TargetPercentage = slot4TargetPercentage;
  }
  public String getSlot4AchievedPercentage() {
    return slot4AchievedPercentage;
  }
  public void setSlot4AchievedPercentage(String slot4AchievedPercentage) {
    this.slot4AchievedPercentage = slot4AchievedPercentage;
  }
  public String getSlot5TargetPercentage() {
    return slot5TargetPercentage;
  }
  public void setSlot5TargetPercentage(String slot5TargetPercentage) {
    this.slot5TargetPercentage = slot5TargetPercentage;
  }
  public String getSlot5AchievedPercentage() {
    return slot5AchievedPercentage;
  }
  public void setSlot5AchievedPercentage(String slot5AchievedPercentage) {
    this.slot5AchievedPercentage = slot5AchievedPercentage;
  }
}