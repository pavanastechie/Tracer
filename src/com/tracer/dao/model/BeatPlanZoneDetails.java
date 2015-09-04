/**
 * @author Bhargava
 *
 */
package com.tracer.dao.model;

import java.io.Serializable;
import java.util.List;

public class BeatPlanZoneDetails implements Serializable {

  private static final long serialVersionUID = 1L;
  private long zoneId;
  private String zoneName;
  private List<BeatPlanHubDetails> beatPlanHubs;

  public long getZoneId() {
    return zoneId;
  }

  public void setZoneId(long zoneId) {
    this.zoneId = zoneId;
  }

  public String getZoneName() {
    return zoneName;
  }

  public void setZoneName(String zoneName) {
    this.zoneName = zoneName;
  }

  public List<BeatPlanHubDetails> getBeatPlanHubs() {
    return beatPlanHubs;
  }

  public void setBeatPlanHubs(List<BeatPlanHubDetails> beatPlanHubs) {
    this.beatPlanHubs = beatPlanHubs;
  }

}
