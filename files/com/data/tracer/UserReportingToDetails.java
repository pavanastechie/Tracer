package com.data.tracer;

// Generated 15 Jun, 2015 3:57:49 PM by Hibernate Tools 3.4.0.CR1

/**
 * UserReportingToDetails generated by hbm2java
 */
public class UserReportingToDetails implements java.io.Serializable {

	private Long userReportingToDetailsId;
	private UserDetails userDetails;
	private long userId;
	private char status;

	public UserReportingToDetails() {
	}

	public UserReportingToDetails(UserDetails userDetails, long userId,
			char status) {
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

	public long getUserId() {
		return this.userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public char getStatus() {
		return this.status;
	}

	public void setStatus(char status) {
		this.status = status;
	}

}
