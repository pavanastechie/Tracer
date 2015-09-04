/**
 * @author Jp
 *
 */
package com.tracer.dao.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("rawtypes")
public class CircleDetails implements java.io.Serializable {
  private static final long serialVersionUID = -9147135737350954916L;
  private Long circleId;
  private UserDetails userDetails;
  private String circleName;
  private String circleCode;
  private String status;
  private Date dateTime;

  private Set regionDetailses = new HashSet(0);
  private Set userToCircleDetailses = new HashSet(0);

  /** default constructor */
  public CircleDetails() {
  }

  /** minimal constructor */
  public CircleDetails(UserDetails userDetails, String circleName,
      String circleCode, String status, Date dateTime) {
    this.userDetails = userDetails;
    this.circleName = circleName;
    this.circleCode = circleCode;
    this.status = status;
    this.dateTime = dateTime;
  }

  /** full constructor */
  public CircleDetails(UserDetails userDetails, String circleName,
      String circleCode, String status, Date dateTime,
      Set regionDetailses, Set userToCircleDetailses) {
    this.userDetails = userDetails;
    this.circleName = circleName;
    this.circleCode = circleCode;
    this.status = status;
    this.dateTime = dateTime;
    this.regionDetailses = regionDetailses;
    this.userToCircleDetailses = userToCircleDetailses;
  }

  public Long getCircleId() {
    return this.circleId;
  }

  public void setCircleId(Long circleId) {
    this.circleId = circleId;
  }

  public UserDetails getUserDetails() {
    return this.userDetails;
  }

  public void setUserDetails(UserDetails userDetails) {
    this.userDetails = userDetails;
  }

  public String getCircleName() {
    return this.circleName;
  }

  public void setCircleName(String circleName) {
    this.circleName = circleName;
  }

  public String getCircleCode() {
    return this.circleCode;
  }

  public void setCircleCode(String circleCode) {
    this.circleCode = circleCode;
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Date getDateTime() {
    return this.dateTime;
  }

  public void setDateTime(Date dateTime) {
    this.dateTime = dateTime;
  }

  public Set getRegionDetailses() {
    return this.regionDetailses;
  }

  public void setRegionDetailses(Set regionDetailses) {
    this.regionDetailses = regionDetailses;
  }

  public Set getUserToCircleDetailses() {
    return this.userToCircleDetailses;
  }

  public void setUserToCircleDetailses(Set userToCircleDetailses) {
    this.userToCircleDetailses = userToCircleDetailses;
  }
  
  // Transient Variables
  private int regionsCount;
  private int zonesCount;
  private int hubsCount;
  private String[] managers; 
  
  public int getRegionsCount() {
    return regionsCount;
  }
  
  public void setRegionsCount(int regionsCount) {
    this.regionsCount = regionsCount;
  }
  
  public int getZonesCount() {
    return zonesCount;
  }
  
  public void setZonesCount(int zonesCount) {
    this.zonesCount = zonesCount;
  }
  
  public int getHubsCount() {
    return hubsCount;
  }
  
  public void setHubsCount(int hubsCount) {
    this.hubsCount = hubsCount;
  }

  public String[] getManagers() {
    return managers;
  }

  public void setManagers(String[] managers) {
    this.managers = managers;
  }
}