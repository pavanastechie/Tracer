/**
 * @author Jp
 *
 */
package com.tracer.dao.model;

import java.util.Date;
import java.util.List;

public class CafCollectionDetails implements java.io.Serializable {
  private static final long serialVersionUID = 1188918323619692566L;
  private Long cafCollectionId;
  private RunnerVisitDetails runnerVisitDetails;
  private Integer totalCafCount;
  private Integer acceptedCafCount;
  private Integer rejectedCafCount;
  private Integer returnedCafCount;
  private String distributorPhotoPath;
  private String dstributorSignPath;
  private String runnerPhotoPath;
  private String cafProviderMobNum;
  private String remarks;
  private Date dateTime;
  
  private List<CafScanDetails> scannedCafs = null;

  /** default constructor */
  public CafCollectionDetails() {
  }

  /** minimal constructor */
  public CafCollectionDetails(RunnerVisitDetails runnerVisitDetails,
      Integer totalCafCount, Integer acceptedCafCount,
      Integer rejectedCafCount, Integer returnedCafCount,
      String distributorPhotoPath, String dstributorSignPath,
      String runnerPhotoPath, String cafProviderMobNum, Date dateTime) {
    this.runnerVisitDetails = runnerVisitDetails;
    this.totalCafCount = totalCafCount;
    this.acceptedCafCount = acceptedCafCount;
    this.rejectedCafCount = rejectedCafCount;
    this.returnedCafCount = returnedCafCount;
    this.distributorPhotoPath = distributorPhotoPath;
    this.dstributorSignPath = dstributorSignPath;
    this.runnerPhotoPath = runnerPhotoPath;
    this.cafProviderMobNum = cafProviderMobNum;
    this.dateTime = dateTime;
  }

  /** full constructor */
  public CafCollectionDetails(RunnerVisitDetails runnerVisitDetails,
      Integer totalCafCount, Integer acceptedCafCount,
      Integer rejectedCafCount, Integer returnedCafCount,
      String distributorPhotoPath, String dstributorSignPath,
      String runnerPhotoPath, String cafProviderMobNum, String remarks,
      Date dateTime) {
    this.runnerVisitDetails = runnerVisitDetails;
    this.totalCafCount = totalCafCount;
    this.acceptedCafCount = acceptedCafCount;
    this.rejectedCafCount = rejectedCafCount;
    this.returnedCafCount = returnedCafCount;
    this.distributorPhotoPath = distributorPhotoPath;
    this.dstributorSignPath = dstributorSignPath;
    this.runnerPhotoPath = runnerPhotoPath;
    this.cafProviderMobNum = cafProviderMobNum;
    this.remarks = remarks;
    this.dateTime = dateTime;
  }

  public Long getCafCollectionId() {
    return this.cafCollectionId;
  }

  public void setCafCollectionId(Long cafCollectionId) {
    this.cafCollectionId = cafCollectionId;
  }

  public RunnerVisitDetails getRunnerVisitDetails() {
    return this.runnerVisitDetails;
  }

  public void setRunnerVisitDetails(RunnerVisitDetails runnerVisitDetails) {
    this.runnerVisitDetails = runnerVisitDetails;
  }

  public Integer getTotalCafCount() {
    return this.totalCafCount;
  }

  public void setTotalCafCount(Integer totalCafCount) {
    this.totalCafCount = totalCafCount;
  }

  public Integer getAcceptedCafCount() {
    return this.acceptedCafCount;
  }

  public void setAcceptedCafCount(Integer acceptedCafCount) {
    this.acceptedCafCount = acceptedCafCount;
  }

  public Integer getRejectedCafCount() {
    return this.rejectedCafCount;
  }

  public void setRejectedCafCount(Integer rejectedCafCount) {
    this.rejectedCafCount = rejectedCafCount;
  }

  public Integer getReturnedCafCount() {
    return this.returnedCafCount;
  }

  public void setReturnedCafCount(Integer returnedCafCount) {
    this.returnedCafCount = returnedCafCount;
  }

  public String getDistributorPhotoPath() {
    return this.distributorPhotoPath;
  }

  public void setDistributorPhotoPath(String distributorPhotoPath) {
    this.distributorPhotoPath = distributorPhotoPath;
  }

  public String getDstributorSignPath() {
    return this.dstributorSignPath;
  }

  public void setDstributorSignPath(String dstributorSignPath) {
    this.dstributorSignPath = dstributorSignPath;
  }

  public String getRunnerPhotoPath() {
    return this.runnerPhotoPath;
  }

  public void setRunnerPhotoPath(String runnerPhotoPath) {
    this.runnerPhotoPath = runnerPhotoPath;
  }

  public String getCafProviderMobNum() {
    return this.cafProviderMobNum;
  }

  public void setCafProviderMobNum(String cafProviderMobNum) {
    this.cafProviderMobNum = cafProviderMobNum;
  }

  public String getRemarks() {
    return this.remarks;
  }

  public void setRemarks(String remarks) {
    this.remarks = remarks;
  }

  public Date getDateTime() {
    return this.dateTime;
  }

  public void setDateTime(Date dateTime) {
    this.dateTime = dateTime;
  }

  public List<CafScanDetails> getScannedCafs() {
    return scannedCafs;
  }
  
  public void setScannedCafs(List<CafScanDetails> scannedCafs) {
    this.scannedCafs = scannedCafs;
  }
}