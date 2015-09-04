/**
 * @author Jp
 *
 */
package com.tracer.dao.model;

public class UserToHubDetails implements java.io.Serializable {
  private static final long serialVersionUID = -4388894790171108740L;
  private Long userToHubDetailsId;
  private UserDetails userDetails;
  private HubDetails hubDetails;
  private String status;

  /** default constructor */
  public UserToHubDetails() {
  }

  /** full constructor */
  public UserToHubDetails(UserDetails userDetails, HubDetails hubDetails,
      String status) {
    this.userDetails = userDetails;
    this.hubDetails = hubDetails;
    this.status = status;
  }

  public Long getUserToHubDetailsId() {
    return this.userToHubDetailsId;
  }

  public void setUserToHubDetailsId(Long userToHubDetailsId) {
    this.userToHubDetailsId = userToHubDetailsId;
  }

  public UserDetails getUserDetails() {
    return this.userDetails;
  }

  public void setUserDetails(UserDetails userDetails) {
    this.userDetails = userDetails;
  }

  public HubDetails getHubDetails() {
    return this.hubDetails;
  }

  public void setHubDetails(HubDetails hubDetails) {
    this.hubDetails = hubDetails;
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

}