/**
 * @author Jp
 *
 */
package com.tracer.dao.model;

import java.util.Date;

public class RunnerCAFCollectionDetails implements java.io.Serializable {
  private static final long serialVersionUID = 7205755393411505145L;
  private String distributorName;
  private String distributorCode;
  private int cafCollected;
  private Date collectionTime;
  private int delay;
  private Long runnerId;
  public String getDistributorName() {
    return distributorName;
  }
  public void setDistributorName(String distributorName) {
    this.distributorName = distributorName;
  }
  public String getDistributorCode() {
    return distributorCode;
  }
  public void setDistributorCode(String distributorCode) {
    this.distributorCode = distributorCode;
  }
  public int getCafCollected() {
    return cafCollected;
  }
  public void setCafCollected(int cafCollected) {
    this.cafCollected = cafCollected;
  }
  public Date getCollectionTime() {
    return collectionTime;
  }
  public void setCollectionTime(Date collectionTime) {
    this.collectionTime = collectionTime;
  }
  public int getDelay() {
    return delay;
  }
  public void setDelay(int delay) {
    this.delay = delay;
  }
  public Long getRunnerId() {
    return runnerId;
  }
  public void setRunnerId(Long runnerId) {
    this.runnerId = runnerId;
  }
}
