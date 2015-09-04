/**
 * @author Bhargava
 *
 */
package com.tracer.dao.model;

import java.io.Serializable;

public class BeatPlanDistributorDetails implements Serializable {
  private static final long serialVersionUID = 8508732316773374394L;
  private long distributorId;
  private String distributorName;
  private String ofscCode;
  private String pickupFrequency;
  private String distributorStatus;
  private String mobileNumber;
  private String town;
  private String district;
  private int distTempId;
  private String errorMessage;

  public long getDistributorId() {
    return distributorId;
  }

  public void setDistributorId(long distributorId) {
    this.distributorId = distributorId;
  }

  public String getDistributorName() {
    return distributorName;
  }

  public void setDistributorName(String distributorName) {
    this.distributorName = distributorName;
  }

  public String getOfscCode() {
    return ofscCode;
  }

  public void setOfscCode(String ofscCode) {
    this.ofscCode = ofscCode;
  }

  public String getPickupFrequency() {
    return pickupFrequency;
  }

  public void setPickupFrequency(String pickupFrequency) {
    this.pickupFrequency = pickupFrequency;
  }

  public String getDistributorStatus() {
    return distributorStatus;
  }

  public void setDistributorStatus(String distributorStatus) {
    this.distributorStatus = distributorStatus;
  }

  public String getMobileNumber() {
    return mobileNumber;
  }

  public void setMobileNumber(String mobileNumber) {
    this.mobileNumber = mobileNumber;
  }

  public String getTown() {
    return town;
  }

  public void setTown(String town) {
    this.town = town;
  }

  public String getDistrict() {
    return district;
  }

  public void setDistrict(String district) {
    this.district = district;
  }

  public int getDistTempId() {
    return distTempId;
  }

  public void setDistTempId(int distTempId) {
    this.distTempId = distTempId;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

}
