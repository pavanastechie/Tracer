/**
 * @author Bhargava
 *
 */
package com.tracer.dao.model;

import java.io.Serializable;
import java.util.List;

public class BeatPlanRegionDetails implements Serializable {

  private static final long serialVersionUID = 1L;
  private long regionId;
  private String regionName;
  private List<BeatPlanZoneDetails> beatPlanZones;

  public long getRegionId() {
    return regionId;
  }

  public void setRegionId(long regionId) {
    this.regionId = regionId;
  }

  public String getRegionName() {
    return regionName;
  }

  public void setRegionName(String regionName) {
    this.regionName = regionName;
  }

  public List<BeatPlanZoneDetails> getBeatPlanZones() {
    return beatPlanZones;
  }

  public void setBeatPlanZones(List<BeatPlanZoneDetails> beatPlanZones) {
    this.beatPlanZones = beatPlanZones;
  }

}
