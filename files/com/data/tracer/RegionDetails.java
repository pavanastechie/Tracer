package com.data.tracer;

// Generated 15 Jun, 2015 3:57:49 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * RegionDetails generated by hbm2java
 */
public class RegionDetails implements java.io.Serializable {

	private Long regionId;
	private UserDetails userDetails;
	private CircleDetails circleDetails;
	private String regionName;
	private String regionCode;
	private char status;
	private Date dateTime;
	private Set<ZoneDetails> zoneDetailses = new HashSet<ZoneDetails>(0);
	private Set<UserToRegionDetails> userToRegionDetailses = new HashSet<UserToRegionDetails>(
			0);

	public RegionDetails() {
	}

	public RegionDetails(UserDetails userDetails, CircleDetails circleDetails,
			String regionName, String regionCode, char status, Date dateTime) {
		this.userDetails = userDetails;
		this.circleDetails = circleDetails;
		this.regionName = regionName;
		this.regionCode = regionCode;
		this.status = status;
		this.dateTime = dateTime;
	}

	public RegionDetails(UserDetails userDetails, CircleDetails circleDetails,
			String regionName, String regionCode, char status, Date dateTime,
			Set<ZoneDetails> zoneDetailses,
			Set<UserToRegionDetails> userToRegionDetailses) {
		this.userDetails = userDetails;
		this.circleDetails = circleDetails;
		this.regionName = regionName;
		this.regionCode = regionCode;
		this.status = status;
		this.dateTime = dateTime;
		this.zoneDetailses = zoneDetailses;
		this.userToRegionDetailses = userToRegionDetailses;
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

	public char getStatus() {
		return this.status;
	}

	public void setStatus(char status) {
		this.status = status;
	}

	public Date getDateTime() {
		return this.dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public Set<ZoneDetails> getZoneDetailses() {
		return this.zoneDetailses;
	}

	public void setZoneDetailses(Set<ZoneDetails> zoneDetailses) {
		this.zoneDetailses = zoneDetailses;
	}

	public Set<UserToRegionDetails> getUserToRegionDetailses() {
		return this.userToRegionDetailses;
	}

	public void setUserToRegionDetailses(
			Set<UserToRegionDetails> userToRegionDetailses) {
		this.userToRegionDetailses = userToRegionDetailses;
	}

}
