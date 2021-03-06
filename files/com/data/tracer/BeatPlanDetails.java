package com.data.tracer;

// Generated 15 Jun, 2015 3:57:49 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * BeatPlanDetails generated by hbm2java
 */
public class BeatPlanDetails implements java.io.Serializable {

	private Long beatPlanId;
	private UserDetails userDetails;
	private String beatPlanName;
	private char beatPlanType;
	private String beatPlanCode;
	private char status;
	private Date dateTime;
	private Set<BeatPlanDistributorAssignments> beatPlanDistributorAssignmentses = new HashSet<BeatPlanDistributorAssignments>(
			0);

	public BeatPlanDetails() {
	}

	public BeatPlanDetails(UserDetails userDetails, char beatPlanType,
			String beatPlanCode, char status, Date dateTime) {
		this.userDetails = userDetails;
		this.beatPlanType = beatPlanType;
		this.beatPlanCode = beatPlanCode;
		this.status = status;
		this.dateTime = dateTime;
	}

	public BeatPlanDetails(UserDetails userDetails, String beatPlanName,
			char beatPlanType, String beatPlanCode, char status, Date dateTime,
			Set<BeatPlanDistributorAssignments> beatPlanDistributorAssignmentses) {
		this.userDetails = userDetails;
		this.beatPlanName = beatPlanName;
		this.beatPlanType = beatPlanType;
		this.beatPlanCode = beatPlanCode;
		this.status = status;
		this.dateTime = dateTime;
		this.beatPlanDistributorAssignmentses = beatPlanDistributorAssignmentses;
	}

	public Long getBeatPlanId() {
		return this.beatPlanId;
	}

	public void setBeatPlanId(Long beatPlanId) {
		this.beatPlanId = beatPlanId;
	}

	public UserDetails getUserDetails() {
		return this.userDetails;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

	public String getBeatPlanName() {
		return this.beatPlanName;
	}

	public void setBeatPlanName(String beatPlanName) {
		this.beatPlanName = beatPlanName;
	}

	public char getBeatPlanType() {
		return this.beatPlanType;
	}

	public void setBeatPlanType(char beatPlanType) {
		this.beatPlanType = beatPlanType;
	}

	public String getBeatPlanCode() {
		return this.beatPlanCode;
	}

	public void setBeatPlanCode(String beatPlanCode) {
		this.beatPlanCode = beatPlanCode;
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

	public Set<BeatPlanDistributorAssignments> getBeatPlanDistributorAssignmentses() {
		return this.beatPlanDistributorAssignmentses;
	}

	public void setBeatPlanDistributorAssignmentses(
			Set<BeatPlanDistributorAssignments> beatPlanDistributorAssignmentses) {
		this.beatPlanDistributorAssignmentses = beatPlanDistributorAssignmentses;
	}

}
