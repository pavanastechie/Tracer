/**
 * @author Jp
 *
 */
package com.tracer.dao.model;

import java.util.Date;

public class UserAuthCodeDetails implements java.io.Serializable {
  private static final long serialVersionUID = -1648272871138081246L;
  private Long userAuthCodeId;
  private UserDetails userDetails;
  private String authCode;
  private Date loginTimestamp;
  private Date logoutTimestamp;
  private String status;

  /** default constructor */
  public UserAuthCodeDetails() {
  }

  /** minimal constructor */
  public UserAuthCodeDetails(UserDetails userDetails, String authCode,
      Date loginTimestamp) {
    this.userDetails = userDetails;
    this.authCode = authCode;
    this.loginTimestamp = loginTimestamp;
  }

  /** full constructor */
  public UserAuthCodeDetails(UserDetails userDetails, String authCode,
      Date loginTimestamp, Date logoutTimestamp) {
    this.userDetails = userDetails;
    this.authCode = authCode;
    this.loginTimestamp = loginTimestamp;
    this.logoutTimestamp = logoutTimestamp;
  }

  public Long getUserAuthCodeId() {
    return this.userAuthCodeId;
  }

  public void setUserAuthCodeId(Long userAuthCodeId) {
    this.userAuthCodeId = userAuthCodeId;
  }

  public UserDetails getUserDetails() {
    return this.userDetails;
  }

  public void setUserDetails(UserDetails userDetails) {
    this.userDetails = userDetails;
  }

  public String getAuthCode() {
    return this.authCode;
  }

  public void setAuthCode(String authCode) {
    this.authCode = authCode;
  }

  public Date getLoginTimestamp() {
    return this.loginTimestamp;
  }

  public void setLoginTimestamp(Date loginTimestamp) {
    this.loginTimestamp = loginTimestamp;
  }

  public Date getLogoutTimestamp() {
    return this.logoutTimestamp;
  }

  public void setLogoutTimestamp(Date logoutTimestamp) {
    this.logoutTimestamp = logoutTimestamp;
  }
  
  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

}