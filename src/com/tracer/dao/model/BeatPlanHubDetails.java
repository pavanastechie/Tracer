/**
 * @author Bhargava
 *
 */
package com.tracer.dao.model;

import java.io.Serializable;
import java.util.List;

public class BeatPlanHubDetails implements Serializable {
  private static final long serialVersionUID = -1373276147080014130L;
  private long hubId;
  private String hubName;
  private List<BeatPlanUserDetails> beatPlanUsers;
  private List<BeatPlanDistributorDetails> beatPlanDistributors;

  public long getHubId() {
    return hubId;
  }

  public void setHubId(long hubId) {
    this.hubId = hubId;
  }

  public String getHubName() {
    return hubName;
  }

  public void setHubName(String hubName) {
    this.hubName = hubName;
  }

  public List<BeatPlanUserDetails> getBeatPlanUsers() {
    return beatPlanUsers;
  }

  public void setBeatPlanUsers(List<BeatPlanUserDetails> beatPlanUsers) {
    this.beatPlanUsers = beatPlanUsers;
  }

  public List<BeatPlanDistributorDetails> getBeatPlanDistributors() {
    return beatPlanDistributors;
  }

  public void setBeatPlanDistributors(
      List<BeatPlanDistributorDetails> beatPlanDistributors) {
    this.beatPlanDistributors = beatPlanDistributors;
  }

}
