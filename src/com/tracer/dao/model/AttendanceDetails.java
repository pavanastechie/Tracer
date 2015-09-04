/**
 * @author Jp
 *
 */
package com.tracer.dao.model;

import java.util.Date;

/**
 * AttendanceDetails entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class AttendanceDetails implements java.io.Serializable {
  private static final long serialVersionUID = 1L;
  private Long attendanceDetailsId;
  private UserDetails userDetails;
  private Date dateTime;
  private String status;
  private String userPhotoPath;
  private String lattitude;
  private String longitude;
  private String location;

  // Constructors

  /** default constructor */
  public AttendanceDetails() {
  }

  /** full constructor */
  public AttendanceDetails(UserDetails userDetails, Date dateTime,
      String status, String userPhotoPath, String lattitude,
      String longitude, String location) {
    this.userDetails = userDetails;
    this.dateTime = dateTime;
    this.status = status;
    this.userPhotoPath = userPhotoPath;
    this.lattitude = lattitude;
    this.longitude = longitude;
    this.location = location;
  }

  // Property accessors

  public Long getAttendanceDetailsId() {
    return this.attendanceDetailsId;
  }

  public void setAttendanceDetailsId(Long attendanceDetailsId) {
    this.attendanceDetailsId = attendanceDetailsId;
  }

  public UserDetails getUserDetails() {
    return this.userDetails;
  }

  public void setUserDetails(UserDetails userDetails) {
    this.userDetails = userDetails;
  }

  public Date getDateTime() {
    return this.dateTime;
  }

  public void setDateTime(Date dateTime) {
    this.dateTime = dateTime;
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getUserPhotoPath() {
    return this.userPhotoPath;
  }

  public void setUserPhotoPath(String userPhotoPath) {
    this.userPhotoPath = userPhotoPath;
  }

  public String getLattitude() {
    return lattitude;
  }

  public void setLattitude(String lattitude) {
    this.lattitude = lattitude;
  }

  public String getLongitude() {
    return longitude;
  }

  public void setLongitude(String longitude) {
    this.longitude = longitude;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }
}