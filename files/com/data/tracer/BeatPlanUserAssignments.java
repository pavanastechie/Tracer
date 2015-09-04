package com.data.tracer;

// Generated 15 Jun, 2015 3:57:49 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * BeatPlanUserAssignments generated by hbm2java
 */
public class BeatPlanUserAssignments implements java.io.Serializable {

	private Long bpUserAssignmentId;
	private UserDetails userDetails;
	private BeatPlanDistributorAssignments beatPlanDistributorAssignments;
	private char status;
	private Date scheduleTime;
	private char visitNo;
	private Set<RunnerVisitDetails> runnerVisitDetailses = new HashSet<RunnerVisitDetails>(
			0);

	public BeatPlanUserAssignments() {
	}

	public BeatPlanUserAssignments(UserDetails userDetails,
			BeatPlanDistributorAssignments beatPlanDistributorAssignments,
			char status, Date scheduleTime, char visitNo) {
		this.userDetails = userDetails;
		this.beatPlanDistributorAssignments = beatPlanDistributorAssignments;
		this.status = status;
		this.scheduleTime = scheduleTime;
		this.visitNo = visitNo;
	}

	public BeatPlanUserAssignments(UserDetails userDetails,
			BeatPlanDistributorAssignments beatPlanDistributorAssignments,
			char status, Date scheduleTime, char visitNo,
			Set<RunnerVisitDetails> runnerVisitDetailses) {
		this.userDetails = userDetails;
		this.beatPlanDistributorAssignments = beatPlanDistributorAssignments;
		this.status = status;
		this.scheduleTime = scheduleTime;
		this.visitNo = visitNo;
		this.runnerVisitDetailses = runnerVisitDetailses;
	}

	public Long getBpUserAssignmentId() {
		return this.bpUserAssignmentId;
	}

	public void setBpUserAssignmentId(Long bpUserAssignmentId) {
		this.bpUserAssignmentId = bpUserAssignmentId;
	}

	public UserDetails getUserDetails() {
		return this.userDetails;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

	public BeatPlanDistributorAssignments getBeatPlanDistributorAssignments() {
		return this.beatPlanDistributorAssignments;
	}

	public void setBeatPlanDistributorAssignments(
			BeatPlanDistributorAssignments beatPlanDistributorAssignments) {
		this.beatPlanDistributorAssignments = beatPlanDistributorAssignments;
	}

	public char getStatus() {
		return this.status;
	}

	public void setStatus(char status) {
		this.status = status;
	}

	public Date getScheduleTime() {
		return this.scheduleTime;
	}

	public void setScheduleTime(Date scheduleTime) {
		this.scheduleTime = scheduleTime;
	}

	public char getVisitNo() {
		return this.visitNo;
	}

	public void setVisitNo(char visitNo) {
		this.visitNo = visitNo;
	}

	public Set<RunnerVisitDetails> getRunnerVisitDetailses() {
		return this.runnerVisitDetailses;
	}

	public void setRunnerVisitDetailses(
			Set<RunnerVisitDetails> runnerVisitDetailses) {
		this.runnerVisitDetailses = runnerVisitDetailses;
	}

}