/**
 * @author Jp
 *
 */
package com.tracer.dao.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@SuppressWarnings("rawtypes")
public class ZoneDetails implements java.io.Serializable {
  private static final long serialVersionUID = -6147272406314716772L;
  private Long zoneId;
  private UserDetails userDetails;
  private RegionDetails regionDetails;
  private String zoneName;
  private String zoneCode;
  private String status;
  private Date dateTime;
  
  private Set userToZoneDetailses = new HashSet(0);
  private Set hubDetailses = new HashSet(0);

  /** default constructor */
  public ZoneDetails() {
  }

  /** minimal constructor */
  public ZoneDetails(UserDetails userDetails, RegionDetails regionDetails,
      String zoneName, String zoneCode, String status, Date dateTime) {
    this.userDetails = userDetails;
    this.regionDetails = regionDetails;
    this.zoneName = zoneName;
    this.zoneCode = zoneCode;
    this.status = status;
    this.dateTime = dateTime;
  }

  /** full constructor */
  public ZoneDetails(UserDetails userDetails, RegionDetails regionDetails,
      String zoneName, String zoneCode, String status, Date dateTime,
      Set userToZoneDetailses, Set hubDetailses) {
    this.userDetails = userDetails;
    this.regionDetails = regionDetails;
    this.zoneName = zoneName;
    this.zoneCode = zoneCode;
    this.status = status;
    this.dateTime = dateTime;
    this.userToZoneDetailses = userToZoneDetailses;
    this.hubDetailses = hubDetailses;
  }

  public Long getZoneId() {
    return this.zoneId;
  }

  public void setZoneId(Long zoneId) {
    this.zoneId = zoneId;
  }

  public UserDetails getUserDetails() {
    return this.userDetails;
  }

  public void setUserDetails(UserDetails userDetails) {
    this.userDetails = userDetails;
  }

  public RegionDetails getRegionDetails() {
    return this.regionDetails;
  }

  public void setRegionDetails(RegionDetails regionDetails) {
    this.regionDetails = regionDetails;
  }

  public String getZoneName() {
    return this.zoneName;
  }

  public void setZoneName(String zoneName) {
    this.zoneName = zoneName;
  }

  public String getZoneCode() {
    return this.zoneCode;
  }

  public void setZoneCode(String zoneCode) {
    this.zoneCode = zoneCode;
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

  public Set getUserToZoneDetailses() {
    return this.userToZoneDetailses;
  }

  public void setUserToZoneDetailses(Set userToZoneDetailses) {
    this.userToZoneDetailses = userToZoneDetailses;
  }

  public Set getHubDetailses() {
    return this.hubDetailses;
  }

  public void setHubDetailses(Set hubDetailses) {
    this.hubDetailses = hubDetailses;
  }

}