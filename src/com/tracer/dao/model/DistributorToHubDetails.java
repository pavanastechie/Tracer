/**
 * @author Jp
 *
 */
package com.tracer.dao.model;

public class DistributorToHubDetails implements java.io.Serializable {
  private static final long serialVersionUID = -7232821281948146407L;
  private Long distributorToHubId;
  private HubDetails hubDetails;
  private DistributorDetails distributorDetails;
  private String status;

  /** default constructor */
  public DistributorToHubDetails() {
  }

  /** full constructor */
  public DistributorToHubDetails(HubDetails hubDetails,
      DistributorDetails distributorDetails, String status) {
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

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

}