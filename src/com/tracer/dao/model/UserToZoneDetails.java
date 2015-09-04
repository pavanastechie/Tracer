/**
 * @author Jp
 *
 */
package com.tracer.dao.model;

public class UserToZoneDetails implements java.io.Serializable {
  private static final long serialVersionUID = 6554120127554060018L;
  private Long userToZoneDetailsId;
  private UserDetails userDetails;
  private ZoneDetails zoneDetails;
  private String status;

  // Constructors

  /** default constructor */
  public UserToZoneDetails() {
  }

  /** full constructor */
  public UserToZoneDetails(UserDetails userDetails, ZoneDetails zoneDetails,
      String status) {
    this.userDetails = userDetails;
    this.zoneDetails = zoneDetails;
    this.status = status;
  }

  // Property accessors

  public Long getUserToZoneDetailsId() {
    return this.userToZoneDetailsId;
  }

  public void setUserToZoneDetailsId(Long userToZoneDetailsId) {
    this.userToZoneDetailsId = userToZoneDetailsId;
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

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

}