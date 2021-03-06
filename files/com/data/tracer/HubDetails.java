package com.data.tracer;

// Generated 15 Jun, 2015 3:57:49 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * HubDetails generated by hbm2java
 */
public class HubDetails implements java.io.Serializable {

	private Long hubId;
	private UserDetails userDetails;
	private ZoneDetails zoneDetails;
	private String hubName;
	private String hubCode;
	private char status;
	private Date dateTime;
	private Set<UserToHubDetails> userToHubDetailses = new HashSet<UserToHubDetails>(
			0);
	private Set<DistributorToHubDetails> distributorToHubDetailses = new HashSet<DistributorToHubDetails>(
			0);

	public HubDetails() {
	}

	public HubDetails(UserDetails userDetails, ZoneDetails zoneDetails,
			String hubName, String hubCode, char status, Date dateTime) {
		this.userDetails = userDetails;
		this.zoneDetails = zoneDetails;
		this.hubName = hubName;
		this.hubCode = hubCode;
		this.status = status;
		this.dateTime = dateTime;
	}

	public HubDetails(UserDetails userDetails, ZoneDetails zoneDetails,
			String hubName, String hubCode, char status, Date dateTime,
			Set<UserToHubDetails> userToHubDetailses,
			Set<DistributorToHubDetails> distributorToHubDetailses) {
		this.userDetails = userDetails;
		this.zoneDetails = zoneDetails;
		this.hubName = hubName;
		this.hubCode = hubCode;
		this.status = status;
		this.dateTime = dateTime;
		this.userToHubDetailses = userToHubDetailses;
		this.distributorToHubDetailses = distributorToHubDetailses;
	}

	public Long getHubId() {
		return this.hubId;
	}

	public void setHubId(Long hubId) {
		this.hubId = hubId;
	}

	public UserDetails getUserDetails() {
		return this.userDetails;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

	public ZoneDetails getZoneDetails() {
		return this.zoneDetails;
	}

	public void setZoneDetails(ZoneDetails zoneDetails) {
		this.zoneDetails = zoneDetails;
	}

	public String getHubName() {
		return this.hubName;
	}

	public void setHubName(String hubName) {
		this.hubName = hubName;
	}

	public String getHubCode() {
		return this.hubCode;
	}

	public void setHubCode(String hubCode) {
		this.hubCode = hubCode;
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

	public Set<UserToHubDetails> getUserToHubDetailses() {
		return this.userToHubDetailses;
	}

	public void setUserToHubDetailses(Set<UserToHubDetails> userToHubDetailses) {
		this.userToHubDetailses = userToHubDetailses;
	}

	public Set<DistributorToHubDetails> getDistributorToHubDetailses() {
		return this.distributorToHubDetailses;
	}

	public void setDistributorToHubDetailses(
			Set<DistributorToHubDetails> distributorToHubDetailses) {
		this.distributorToHubDetailses = distributorToHubDetailses;
	}

}
