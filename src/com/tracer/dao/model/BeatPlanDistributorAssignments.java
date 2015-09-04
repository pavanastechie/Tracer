/**
 * @author Jp
 *
 */
package com.tracer.dao.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class BeatPlanDistributorAssignments implements java.io.Serializable {
  private static final long serialVersionUID = 1909065437381794648L;
  private Long bpDistAssignmentsId;
  private DistributorDetails distributorDetails;
  private BeatPlanDetails beatPlanDetails;
  private String status;
  private String visitFrequency;
  private int visitNo;
  public int getVisitNo() {
		return visitNo;
	}

	public void setVisitNo(int visitNo) {
		this.visitNo = visitNo;
	}

	private Date dateTime;
  @SuppressWarnings("rawtypes")
 private Set beatPlanUserAssignmentses = new HashSet(0);

  /** default constructor */
  public BeatPlanDistributorAssignments() {
  }

  /** minimal constructor */
  public BeatPlanDistributorAssignments(
      DistributorDetails distributorDetails,
      BeatPlanDetails beatPlanDetails, String status,
      String visitFrequency, Date dateTime) {
    this.distributorDetails = distributorDetails;
    this.beatPlanDetails = beatPlanDetails;
    this.status = status;
    this.visitFrequency = visitFrequency;
    this.dateTime = dateTime;
  }

  /** full constructor */
  @SuppressWarnings("rawtypes")
  public BeatPlanDistributorAssignments(
      DistributorDetails distributorDetails,
      BeatPlanDetails beatPlanDetails, String status,
      String visitFrequency, Date dateTime, Set beatPlanUserAssignmentses) {
    this.distributorDetails = distributorDetails;
    this.beatPlanDetails = beatPlanDetails;
    this.status = status;
    this.visitFrequency = visitFrequency;
    this.dateTime = dateTime;
    this.beatPlanUserAssignmentses = beatPlanUserAssignmentses;
  }

  public Long getBpDistAssignmentsId() {
    return this.bpDistAssignmentsId;
  }

  public void setBpDistAssignmentsId(Long bpDistAssignmentsId) {
    this.bpDistAssignmentsId = bpDistAssignmentsId;
  }

  public DistributorDetails getDistributorDetails() {
    return this.distributorDetails;
  }

  public void setDistributorDetails(DistributorDetails distributorDetails) {
    this.distributorDetails = distributorDetails;
  }

  public BeatPlanDetails getBeatPlanDetails() {
    return this.beatPlanDetails;
  }

  public void setBeatPlanDetails(BeatPlanDetails beatPlanDetails) {
    this.beatPlanDetails = beatPlanDetails;
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getVisitFrequency() {
    return this.visitFrequency;
  }

  public void setVisitFrequency(String visitFrequency) {
    this.visitFrequency = visitFrequency;
  }

  public Date getDateTime() {
    return this.dateTime;
  }

  public void setDateTime(Date dateTime) {
    this.dateTime = dateTime;
  }

  @SuppressWarnings("rawtypes")
  public Set getBeatPlanUserAssignmentses() {
    return this.beatPlanUserAssignmentses;
  }

  @SuppressWarnings("rawtypes")
  public void setBeatPlanUserAssignmentses(Set beatPlanUserAssignmentses) {
    this.beatPlanUserAssignmentses = beatPlanUserAssignmentses;
  }
}