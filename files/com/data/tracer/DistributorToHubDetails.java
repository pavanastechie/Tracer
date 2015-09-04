package com.data.tracer;

// Generated 15 Jun, 2015 3:57:49 PM by Hibernate Tools 3.4.0.CR1

/**
 * DistributorToHubDetails generated by hbm2java
 */
public class DistributorToHubDetails implements java.io.Serializable {

	private Long distributorToHubId;
	private HubDetails hubDetails;
	private DistributorDetails distributorDetails;
	private char status;

	public DistributorToHubDetails() {
	}

	public DistributorToHubDetails(HubDetails hubDetails,
			DistributorDetails distributorDetails, char status) {
		this.hubDetails = hubDetails;
		this.distributorDetails = distributorDetails;
		this.status = status;
	}

	public Long getDistributorToHubId() {
		return this.distributorToHubId;
	}

	public void setDistributorToHubId(Long distributorToHubId) {
		this.distributorToHubId = distributorToHubId;
	}

	public HubDetails getHubDetails() {
		return this.hubDetails;
	}

	public void setHubDetails(HubDetails hubDetails) {
		this.hubDetails = hubDetails;
	}

	public DistributorDetails getDistributorDetails() {
		return this.distributorDetails;
	}

	public void setDistributorDetails(DistributorDetails distributorDetails) {
		this.distributorDetails = distributorDetails;
	}

	public char getStatus() {
		return this.status;
	}

	public void setStatus(char status) {
		this.status = status;
	}

}