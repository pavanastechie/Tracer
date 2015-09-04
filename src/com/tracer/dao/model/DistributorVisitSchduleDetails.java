/**
 * @author Bhargava
 *
 */
package com.tracer.dao.model;

import java.io.Serializable;

public class DistributorVisitSchduleDetails implements Serializable {

  private static final long serialVersionUID = 1L;
  private long distTempId;
  private String scheduleTime;

  public long getDistTempId() {
    return distTempId;
  }

  public void setDistTempId(long distTempId) {
    this.distTempId = distTempId;
  }

  public String getScheduleTime() {
    return scheduleTime;
  }

  public void setScheduleTime(String scheduleTime) {
    this.scheduleTime = scheduleTime;
  }
}
