/**
 * @author Jp
 *
 */
package com.tracer.dao.model;

public class UserToRegionDetails implements java.io.Serializable {
  private static final long serialVersionUID = -5706877081284468890L;
  private Long userToRegionDetailsId;
  private UserDetails userDetails;
  private RegionDetails regionDetails;
  private String status;

  /** default constructor */
  public UserToRegionDetails() {
  }

  /** full constructor */
  public UserToRegionDetails(UserDetails userDetails,
      RegionDetails regionDetails, String status) {
    this.userDetails = userDetails;
    this.regionDetails = regionDetails;
    this.status = status;
  }

  public Long getUserToRegionDetailsId() {
    return this.userToRegionDetailsId;
  }

  public void setUserToRegionDetailsId(Long userToRegionDetailsId) {
    this.userToRegionDetailsId = userToRegionDetailsId;
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

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

}