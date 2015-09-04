/**
 * @author Jp
 *
 */
package com.tracer.dao.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class HubDetails implements java.io.Serializable {
  private static final long serialVersionUID = 1488936366750891588L;
  private Long hubId;
  private UserDetails userDetails;
  private ZoneDetails zoneDetails;
  private String hubName;
  private String hubCode;
  private String status;
  private Date dateTime;
  @SuppressWarnings("rawtypes")
  private Set userToHubDetailses = new HashSet(0);
  @SuppressWarnings("rawtypes")
  private Set distributorToHubDetailses = new HashSet(0);

  /** default constructor */
  public HubDetails() {
  }

  /** minimal constructor */
  public HubDetails(UserDetails userDetails, ZoneDetails zoneDetails,
      String hubName, String hubCode, String status, Date dateTime) {
    this.userDetails = userDetails;
    this.zoneDetails = zoneDetails;
    this.hubName = hubName;
    this.hubCode = hubCode;
    this.status = status;
    this.dateTime = dateTime;
  }

  /** full constructor */
  @SuppressWarnings("rawtypes")
  public HubDetails(UserDetails userDetails, ZoneDetails zoneDetails,
      String hubName, String hubCode, String status, Date dateTime,
      Set userToHubDetailses, Set distributorToHubDetailses) {
    this.userDetails = userDetails;
    this.zoneDetails = zoneDetails;
    this.hubName = hubName;
    this.hubCode = hubCode;
    this.status = status;
    this.dateTime = dateTime;
    this.userToHubDetailses = userToHubDetailses;
    this.distributorToHubDetailses = distributorToHubDetailses;
  }

  public Long getHubId() {
    return this.hubId;
  }

  public void setHubId(Long hubId) {
    this.hubId = hubId;
  }

  public UserDetails getUserDetails() {
    return this.userDetails;
  }

  public void setUserDetails(UserDetails userDetails) {
    this.userDetails = userDetails;
  }

  public ZoneDetails getZoneDetails() {
    return this.zoneDetails;
  }

  public void setZoneDetails(ZoneDetails zoneDetails) {
    this.zoneDetails = zoneDetails;
  }

  public String getHubName() {
    return this.hubName;
  }

  public void setHubName(String hubName) {
    this.hubName = hubName;
  }

  public String getHubCode() {
    return this.hubCode;
  }

  public void setHubCode(String hubCode) {
    this.hubCode = hubCode;
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

  @SuppressWarnings("rawtypes")
  public Set getUserToHubDetailses() {
    return this.userToHubDetailses;
  }

  @SuppressWarnings("rawtypes")
  public void setUserToHubDetailses(Set userToHubDetailses) {
    this.userToHubDetailses = userToHubDetailses;
  }

  @SuppressWarnings("rawtypes")
  public Set getDistributorToHubDetailses() {
    return this.distributorToHubDetailses;
  }

  @SuppressWarnings("rawtypes")
  public void setDistributorToHubDetailses(Set distributorToHubDetailses) {
    this.distributorToHubDetailses = distributorToHubDetailses;
  }

}