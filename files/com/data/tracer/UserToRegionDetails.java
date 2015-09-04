package com.data.tracer;

// Generated 15 Jun, 2015 3:57:49 PM by Hibernate Tools 3.4.0.CR1

/**
 * UserToRegionDetails generated by hbm2java
 */
public class UserToRegionDetails implements java.io.Serializable {

	private Long userToRegionDetailsId;
	private UserDetails userDetails;
	private RegionDetails regionDetails;
	private char status;

	public UserToRegionDetails() {
	}

	public UserToRegionDetails(UserDetails userDetails,
			RegionDetails regionDetails, char status) {
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

	public char getStatus() {
		return this.status;
	}

	public void setStatus(char status) {
		this.status = status;
	}

}
