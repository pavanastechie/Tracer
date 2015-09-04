/**
 * @author Jp
 *
 */
package com.tracer.dao.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class RegionDetails implements java.io.Serializable {
  private static final long serialVersionUID = 1738661824405878812L;
  private Long regionId;
  private UserDetails userDetails;
  private CircleDetails circleDetails;
  private String regionName;
  private String regionCode;
  private String status;
  private Date dateTime;
  @SuppressWarnings("rawtypes")
  private Set userToRegionDetailses = new HashSet(0);
  @SuppressWarnings("rawtypes")
  private Set zoneDetailses = new HashSet(0);

  /** default constructor */
  public RegionDetails() {
  }

  /** minimal constructor */
  public RegionDetails(UserDetails userDetails, CircleDetails circleDetails,
      String regionName, String regionCode, String status, Date dateTime) {
    this.userDetails = userDetails;
    this.circleDetails = circleDetails;
    this.regionName = regionName;
    this.regionCode = regionCode;
    this.status = status;
    this.dateTime = dateTime;
  }

  /** full constructor */
  @SuppressWarnings("rawtypes")
  public RegionDetails(UserDetails userDetails, CircleDetails circleDetails,
      String regionName, String regionCode, String status, Date dateTime,
      Set userToRegionDetailses, Set zoneDetailses) {
    this.userDetails = userDetails;
    this.circleDetails = circleDetails;
    this.regionName = regionName;
    this.regionCode = regionCode;
    this.status = status;
    this.dateTime = dateTime;
    this.userToRegionDetailses = userToRegionDetailses;
    this.zoneDetailses = zoneDetailses;
  }

  public Long getRegionId() {
    return this.regionId;
  }

  public void setRegionId(Long regionId) {
    this.regionId = regionId;
  }

  public UserDetails getUserDetails() {
    return this.userDetails;
  }

  public void setUserDetails(UserDetails userDetails) {
    this.userDetails = userDetails;
  }

  public CircleDetails getCircleDetails() {
    return this.circleDetails;
  }

  public void setCircleDetails(CircleDetails circleDetails) {
    this.circleDetails = circleDetails;
  }

  public String getRegionName() {
    return this.regionName;
  }

  public void setRegionName(String regionName) {
    this.regionName = regionName;
  }

  public String getRegionCode() {
    return this.regionCode;
  }

  public void setRegionCode(String regionCode) {
    this.regionCode = regionCode;
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
  public Set getUserToRegionDetailses() {
    return this.userToRegionDetailses;
  }

  @SuppressWarnings("rawtypes")
  public void setUserToRegionDetailses(Set userToRegionDetailses) {
    this.userToRegionDetailses = userToRegionDetailses;
  }

  @SuppressWarnings("rawtypes")
  public Set getZoneDetailses() {
    return this.zoneDetailses;
  }

  @SuppressWarnings("rawtypes")
  public void setZoneDetailses(Set zoneDetailses) {
    this.zoneDetailses = zoneDetailses;
  }

}