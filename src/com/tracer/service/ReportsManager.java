/**
 * @author Jp
 *
 */
package com.tracer.service;

import java.util.List;

import com.tracer.dao.model.PerformanceReportDetails;
import com.tracer.dao.model.ZoneDetails;

public interface ReportsManager {

  public List<PerformanceReportDetails> getHubPerformanceReport(String fromDate, String toDate, long hubId) throws Exception;
  
  public List<PerformanceReportDetails> getZonePerformanceReport(String fromDate, String toDate, long zoneId) throws Exception;
  
  public List<ZoneDetails> getZones(long userDetailsId) throws Exception;
  
  
}
