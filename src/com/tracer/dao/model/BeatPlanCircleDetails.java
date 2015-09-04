/**
 * @author Bhargava
 *
 */
package com.tracer.dao.model;

import java.io.Serializable;
import java.util.List;

public class BeatPlanCircleDetails implements Serializable {

  private static final long serialVersionUID = 1L;
  private long circleId;
  private String circleName;
  private List<BeatPlanRegionDetails> beatPlanRegions;

  public long getCircleId() {
    return circleId;
  }

  public void setCircleId(long circleId) {
    this.circleId = circleId;
  }

  public String getCircleName() {
    return circleName;
  }

  public void setCircleName(String circleName) {
    this.circleName = circleName;
  }

  public List<BeatPlanRegionDetails> getBeatPlanRegions() {
    return beatPlanRegions;
  }

  public void setBeatPlanRegions(List<BeatPlanRegionDetails> beatPlanRegions) {
    this.beatPlanRegions = beatPlanRegions;
  }

}
