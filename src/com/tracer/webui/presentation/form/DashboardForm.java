/**
 * @author Jp
 *
 */
package com.tracer.webui.presentation.form;

import java.util.List;

import com.tracer.dao.model.RunnerCAFTrackDetails;

public class DashboardForm extends BaseForm {
  private static final long serialVersionUID = -6003307225090335658L;
  public String attendanceReport;
  public String visitsVsBeatPlanReport;
  public String nonPerformedRunnersReport;
  public String topRunnersReport;
  public String topDistributorsReport;
  public String topRegionsReport;
  public String topZonesReport;
  public String topHubsReport;
  private Long dashboardDetailsId;
  
  private List<RunnerCAFTrackDetails> runnersCAFTrack;
  
  public Long getDashboardDetailsId() {
    return dashboardDetailsId;
  }
  public void setDashboardDetailsId(Long dashboardDetailsId) {
    this.dashboardDetailsId = dashboardDetailsId;
  }
  public String getAttendanceReport() {
    return attendanceReport;
  }
  public void setAttendanceReport(String attendanceReport) {
    this.attendanceReport = attendanceReport;
  }
  public String getVisitsVsBeatPlanReport() {
    return visitsVsBeatPlanReport;
  }
  public void setVisitsVsBeatPlanReport(String visitsVsBeatPlanReport) {
    this.visitsVsBeatPlanReport = visitsVsBeatPlanReport;
  }
  public String getNonPerformedRunnersReport() {
    return nonPerformedRunnersReport;
  }
  public void setNonPerformedRunnersReport(String nonPerformedRunnersReport) {
    this.nonPerformedRunnersReport = nonPerformedRunnersReport;
  }
  public String getTopRunnersReport() {
    return topRunnersReport;
  }
  public void setTopRunnersReport(String topRunnersReport) {
    this.topRunnersReport = topRunnersReport;
  }
  public String getTopDistributorsReport() {
    return topDistributorsReport;
  }
  public void setTopDistributorsReport(String topDistributorsReport) {
    this.topDistributorsReport = topDistributorsReport;
  }
  public String getTopRegionsReport() {
    return topRegionsReport;
  }
  public void setTopRegionsReport(String topRegionsReport) {
    this.topRegionsReport = topRegionsReport;
  }
  public String getTopZonesReport() {
    return topZonesReport;
  }
  public void setTopZonesReport(String topZonesReport) {
    this.topZonesReport = topZonesReport;
  }
  public String getTopHubsReport() {
    return topHubsReport;
  }
  public void setTopHubsReport(String topHubsReport) {
    this.topHubsReport = topHubsReport;
  }
  public List<RunnerCAFTrackDetails> getRunnersCAFTrack() {
    return runnersCAFTrack;
  }
  public void setRunnersCAFTrack(List<RunnerCAFTrackDetails> runnersCAFTrack) {
    this.runnersCAFTrack = runnersCAFTrack;
  }
}