/**
 * @author Jp
 *
 */
package com.tracer.dao.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class RunnerVisitDetails implements java.io.Serializable {
	private static final long serialVersionUID = 2479687443616636055L;
	private Long runnerVisitId;
	private BeatPlanUserAssignments beatPlanUserAssignments;
	private String visitNo;
	private Date visitTimeStamp;
	private String status;
	@SuppressWarnings("rawtypes")
	private Set cafCollectionDetailses = new HashSet(0);

	// Constructors

	/** default constructor */
	public RunnerVisitDetails() {
	}

	/** minimal constructor */
	public RunnerVisitDetails(BeatPlanUserAssignments beatPlanUserAssignments,
			String visitNo, Date visitTimeStamp, String status) {
		this.beatPlanUserAssignments = beatPlanUserAssignments;
		this.visitNo = visitNo;
		this.visitTimeStamp = visitTimeStamp;
		this.status = status;
	}

	/** full constructor */
	@SuppressWarnings("rawtypes")
	public RunnerVisitDetails(BeatPlanUserAssignments beatPlanUserAssignments,
			String visitNo, Date visitTimeStamp, String status,
			Set cafCollectionDetailses) {
		this.beatPlanUserAssignments = beatPlanUserAssignments;
		this.visitNo = visitNo;
		this.visitTimeStamp = visitTimeStamp;
		this.status = status;
		this.cafCollectionDetailses = cafCollectionDetailses;
	}

	public Long getRunnerVisitId() {
		return this.runnerVisitId;
	}

	public void setRunnerVisitId(Long runnerVisitId) {
		this.runnerVisitId = runnerVisitId;
	}

	public BeatPlanUserAssignments getBeatPlanUserAssignments() {
		return this.beatPlanUserAssignments;
	}

	public void setBeatPlanUserAssignments(
			BeatPlanUserAssignments beatPlanUserAssignments) {
		this.beatPlanUserAssignments = beatPlanUserAssignments;
	}

	public String getVisitNo() {
		return this.visitNo;
	}

	public void setVisitNo(String visitNo) {
		this.visitNo = visitNo;
	}

	public Date getVisitTimeStamp() {
		return this.visitTimeStamp;
	}

	public void setVisitTimeStamp(Date visitTimeStamp) {
		this.visitTimeStamp = visitTimeStamp;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@SuppressWarnings("rawtypes")
	public Set getCafCollectionDetailses() {
		return this.cafCollectionDetailses;
	}

	@SuppressWarnings("rawtypes")
	public void setCafCollectionDetailses(Set cafCollectionDetailses) {
		this.cafCollectionDetailses = cafCollectionDetailses;
	}

}