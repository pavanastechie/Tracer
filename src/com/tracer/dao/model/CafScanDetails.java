/**
 * @author Jp
 *
 */
package com.tracer.dao.model;

public class CafScanDetails implements java.io.Serializable {
  private static final long serialVersionUID = 8331120839671374172L;
  private Long cafScanDetailsId;
  private Long cafCollectionDetailsId;
  private String cafBarCode;
  private String cafScanImagePath;

  // Constructors

  /** default constructor */
  public CafScanDetails() {
  }

  /** full constructor */
  public CafScanDetails(Long cafCollectionDetailsId, String cafBarCode,
      String cafScanImagePath) {
    this.cafCollectionDetailsId = cafCollectionDetailsId;
    this.cafBarCode = cafBarCode;
    this.cafScanImagePath = cafScanImagePath;
  }

  // Property accessors

  public Long getCafScanDetailsId() {
    return this.cafScanDetailsId;
  }

  public void setCafScanDetailsId(Long cafScanDetailsId) {
    this.cafScanDetailsId = cafScanDetailsId;
  }

  public Long getCafCollectionDetailsId() {
    return this.cafCollectionDetailsId;
  }

  public void setCafCollectionDetailsId(Long cafCollectionDetailsId) {
    this.cafCollectionDetailsId = cafCollectionDetailsId;
  }

  public String getCafBarCode() {
    return this.cafBarCode;
  }

  public void setCafBarCode(String cafBarCode) {
    this.cafBarCode = cafBarCode;
  }

  public String getCafScanImagePath() {
    return this.cafScanImagePath;
  }

  public void setCafScanImagePath(String cafScanImagePath) {
    this.cafScanImagePath = cafScanImagePath;
  }

}