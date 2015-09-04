package com.data.tracer;

// Generated 15 Jun, 2015 3:57:49 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * ZoneDetails generated by hbm2java
 */
public class ZoneDetails implements java.io.Serializable {

	private Long zoneId;
	private RegionDetails regionDetails;
	private UserDetails userDetails;
	private String zoneName;
	private String zoneCode;
	private char status;
	private Date dateTime;
	private Set<HubDetails> hubDetailses = new HashSet<HubDetails>(0);
	private Set<UserToZoneDetails> userToZoneDetailses = new HashSet<UserToZoneDetails>(
			0);

	public ZoneDetails() {
	}

	public ZoneDetails(RegionDetails regionDetails, UserDetails userDetails,
			String zoneName, String zoneCode, char status, Date dateTime) {
		this.regionDetails = regionDetails;
		this.userDetails = userDetails;
		this.zoneName = zoneName;
		this.zoneCode = zoneCode;
		this.status = status;
		this.dateTime = dateTime;
	}

	public ZoneDetails(RegionDetails regionDetails, UserDetails userDetails,
			String zoneName, String zoneCode, char status, Date dateTime,
			Set<HubDetails> hubDetailses,
			Set<UserToZoneDetails> userToZoneDetailses) {
		this.regionDetails = regionDetails;
		this.userDetails = userDetails;
		this.zoneName = zoneName;
		this.zoneCode = zoneCode;
		this.status = status;
		this.dateTime = dateTime;
		this.hubDetailses = hubDetailses;
		this.userToZoneDetailses = userToZoneDetailses;
	}

	public Long getZoneId() {
		return this.zoneId;
	}

	public void setZoneId(Long zoneId) {
		this.zoneId = zoneId;
	}

	public RegionDetails getRegionDetails() {
		return this.regionDetails;
	}

	public void setRegionDetails(RegionDetails regionDetails) {
		this.regionDetails = regionDetails;
	}

	public UserDetails getUserDetails() {
		return this.userDetails;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

	public String getZoneName() {
		return this.zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public String getZoneCode() {
		return this.zoneCode;
	}

	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
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

	public Set<HubDetails> getHubDetailses() {
		return this.hubDetailses;
	}

	public void setHubDetailses(Set<HubDetails> hubDetailses) {
		this.hubDetailses = hubDetailses;
	}

	public Set<UserToZoneDetails> getUserToZoneDetailses() {
		return this.userToZoneDetailses;
	}

	public void setUserToZoneDetailses(
			Set<UserToZoneDetails> userToZoneDetailses) {
		this.userToZoneDetailses = userToZoneDetailses;
	}

}
