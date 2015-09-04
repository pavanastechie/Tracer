/**
 * @author Jp
 *
 */
package com.tracer.dao.model;

public class DashboardDetails implements java.io.Serializable {
  private static final long serialVersionUID = -1979823225700616292L;
  private Long dashboardDetailsId;
  private UserDetails userDetails;
  private String status;
  private String attendanceReport;
  private String visitsVsBeatPlanReport;
  private String nonPerformedRunnersReport;
  private String topRunnersReport;
  private String topDistributorsReport;
  private String topRegionsReport;
  private String topZonesReport;
  private String topHubsReport;

  public DashboardDetails() {
  }

  public DashboardDetails(UserDetails userDetails, String status,
      String attendanceReport, String visitsVsBeatPlanReport,
      String nonPerformedRunnersReport, String topRunnersReport,
      String topDistributorsReport, String topRegionsReport,
      String topZonesReport, String topHubsReport) {
    this.userDetails = userDetails;
    this.status = status;
    this.attendanceReport = attendanceReport;
    this.visitsVsBeatPlanReport = visitsVsBeatPlanReport;
    this.nonPerformedRunnersReport = nonPerformedRunnersReport;
    this.topRunnersReport = topRunnersReport;
    this.topDistributorsReport = topDistributorsReport;
    this.topRegionsReport = topRegionsReport;
    this.topZonesReport = topZonesReport;
    this.topHubsReport = topHubsReport;
  }
  public Long getDashboardDetailsId() {
    return this.dashboardDetailsId;
  }

  public void setDashboardDetailsId(Long dashboardDetailsId) {
    this.dashboardDetailsId = dashboardDetailsId;
  }

  public UserDetails getUserDetails() {
    return this.userDetails;
  }

  public void setUserDetails(UserDetails userDetails) {
    this.userDetails = userDetails;
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getAttendanceReport() {
    return this.attendanceReport;
  }

  public void setAttendanceReport(String attendanceReport) {
    this.attendanceReport = attendanceReport;
  }

  public String getVisitsVsBeatPlanReport() {
    return this.visitsVsBeatPlanReport;
  }

  public void setVisitsVsBeatPlanReport(String visitsVsBeatPlanReport) {
    this.visitsVsBeatPlanReport = visitsVsBeatPlanReport;
  }

  public String getNonPerformedRunnersReport() {
    return this.nonPerformedRunnersReport;
  }

  public void setNonPerformedRunnersReport(String nonPerformedRunnersReport) {
    this.nonPerformedRunnersReport = nonPerformedRunnersReport;
  }

  public String getTopRunnersReport() {
    return this.topRunnersReport;
  }

  public void setTopRunnersReport(String topRunnersReport) {
    this.topRunnersReport = topRunnersReport;
  }

  public String getTopDistributorsReport() {
    return this.topDistributorsReport;
  }

  public void setTopDistributorsReport(String topDistributorsReport) {
    this.topDistributorsReport = topDistributorsReport;
  }

  public String getTopRegionsReport() {
    return this.topRegionsReport;
  }

  public void setTopRegionsReport(String topRegionsReport) {
    this.topRegionsReport = topRegionsReport;
  }

  public String getTopZonesReport() {
    return this.topZonesReport;
  }

  public void setTopZonesReport(String topZonesReport) {
    this.topZonesReport = topZonesReport;
  }

  public String getTopHubsReport() {
    return this.topHubsReport;
  }

  public void setTopHubsReport(String topHubsReport) {
    this.topHubsReport = topHubsReport;
  }
}