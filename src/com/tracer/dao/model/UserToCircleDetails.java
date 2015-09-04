/**
 * @author Jp
 *
 */
package com.tracer.dao.model;

public class UserToCircleDetails implements java.io.Serializable {
  private static final long serialVersionUID = -4532628847699931580L;
  private Long userToCircleDetailsId;
  private UserDetails userDetails;
  private CircleDetails circleDetails;
  private String status;

  /** default constructor */
  public UserToCircleDetails() {
  }

  /** full constructor */
  public UserToCircleDetails(UserDetails userDetails,
      CircleDetails circleDetails, String status) {
    this.userDetails = userDetails;
    this.circleDetails = circleDetails;
    this.status = status;
  }

  public Long getUserToCircleDetailsId() {
    return this.userToCircleDetailsId;
  }

  public void setUserToCircleDetailsId(Long userToCircleDetailsId) {
    this.userToCircleDetailsId = userToCircleDetailsId;
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

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

}