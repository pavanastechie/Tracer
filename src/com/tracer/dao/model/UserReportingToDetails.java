/**
 * @author Jp
 *
 */
package com.tracer.dao.model;

public class UserReportingToDetails implements java.io.Serializable {
  private static final long serialVersionUID = -2455281197966906236L;
  private Long userReportingToDetailsId;
  private UserDetails userDetails;
  private Long userId;
  private String status;

  public UserReportingToDetails() {
  }

  public UserReportingToDetails(UserDetails userDetails, Long userId, String status) {
    this.userDetails = userDetails;
    this.userId = userId;
    this.status = status;
  }

  public Long getUserReportingToDetailsId() {
    return this.userReportingToDetailsId;
  }

  public void setUserReportingToDetailsId(Long userReportingToDetailsId) {
    this.userReportingToDetailsId = userReportingToDetailsId;
  }

  public UserDetails getUserDetails() {
    return this.userDetails;
  }

  public void setUserDetails(UserDetails userDetails) {
    this.userDetails = userDetails;
  }

  public Long getUserId() {
    return this.userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}