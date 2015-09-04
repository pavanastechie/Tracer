/**
 * @author Jp
 *
 */
package com.tracer.dao.model;

import java.util.Date;

/**
 * RunnerVisittedLocationDetails entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class RunnerVisittedLocationDetails implements java.io.Serializable {
  private static final long serialVersionUID = -1347828383302491336L;
  private Long runnerVisittedLocationId;
  private UserDetails userDetails;
  private String lattitude;
  private String longitude;
  private String location;
  private Date dateTime;

  /** default constructor */
  public RunnerVisittedLocationDetails() {
  }

  /** full constructor */
  public RunnerVisittedLocationDetails(UserDetails userDetails,
      String lattitude, String longitude, String location, Date dateTime) {
    this.userDetails = userDetails;
    this.lattitude = lattitude;
    this.longitude = longitude;
    this.location = location;
    this.dateTime = dateTime;
  }

  public Long getRunnerVisittedLocationId() {
    return this.runnerVisittedLocationId;
  }

  public void setRunnerVisittedLocationId(Long runnerVisittedLocationId) {
    this.runnerVisittedLocationId = runnerVisittedLocationId;
  }

  public UserDetails getUserDetails() {
    return this.userDetails;
  }

  public void setUserDetails(UserDetails userDetails) {
    this.userDetails = userDetails;
  }

  public String getLattitude() {
    return this.lattitude;
  }

  public void setLattitude(String lattitude) {
    this.lattitude = lattitude;
  }

  public String getLongitude() {
    return this.longitude;
  }

  public void setLongitude(String longitude) {
    this.longitude = longitude;
  }

  public String getLocation() {
    return this.location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public Date getDateTime() {
    return this.dateTime;
  }

  public void setDateTime(Date dateTime) {
    this.dateTime = dateTime;
  }

}