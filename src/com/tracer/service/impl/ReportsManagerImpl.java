/**
 * @author Jp
 *
 */
package com.tracer.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tracer.dao.model.PerformanceReportDetails;
import com.tracer.dao.model.ZoneDetails;
import com.tracer.dao.persistence.ReportsDAO;
import com.tracer.service.ReportsManager;

public class ReportsManagerImpl implements ReportsManager {
  protected transient final Log log = LogFactory.getLog(ReportsManagerImpl.class);
  protected ReportsDAO reportsDAO = null;
  
  //========================================================================
  
  public void setReportsDAO(ReportsDAO reportsDAO) {
    
    try {
      this.reportsDAO = reportsDAO;
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  //========================================================================
  
  @Override
  public List<PerformanceReportDetails> getHubPerformanceReport(String fromDate, String toDate, long hubId) throws Exception {
    return reportsDAO.getHubPerformanceReport(fromDate, toDate, hubId);
  }

  //========================================================================
  
  @Override
  public List<PerformanceReportDetails> getZonePerformanceReport(String fromDate, String toDate, long zoneId) throws Exception {
    return reportsDAO.getZonePerformanceReport(fromDate, toDate, zoneId);
  }
  
  //========================================================================
  @Override
  public List<ZoneDetails> getZones(long userDetailsId) throws Exception {
    return reportsDAO.getZones(userDetailsId);
  }

  //========================================================================

}
