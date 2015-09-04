/**
 * @author Jp
 *
 */
package com.tracer.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tracer.dao.model.BeatPlanCircleDetails;
import com.tracer.dao.model.CircleDetails;
import com.tracer.dao.model.DistributorDetails;
import com.tracer.dao.model.HubDetails;
import com.tracer.dao.model.RegionDetails;
import com.tracer.dao.model.UserDetails;
import com.tracer.dao.model.ZoneDetails;
import com.tracer.dao.persistence.BeatPlanBulkUploadDAO;
import com.tracer.service.BeatPlanBulkUploadManager;

public class BeatPlanBulkUploadManagerImpl implements BeatPlanBulkUploadManager {
  protected transient final Log log = LogFactory.getLog(BeatPlanBulkUploadManagerImpl.class);
  protected BeatPlanBulkUploadDAO beatPlanBulkUploadDAO = null;

  // ========================================================================

  public void setBeatPlanBulkUploadDAO(BeatPlanBulkUploadDAO beatPlanBulkUploadDAO) {

    try {
      this.beatPlanBulkUploadDAO = beatPlanBulkUploadDAO;
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // ========================================================================
  
  @Override
  public CircleDetails isCircleExists(String circleName) {
    return beatPlanBulkUploadDAO.isCircleExists(circleName);
  }

  // ========================================================================
  
  @Override
  public CircleDetails createCircle(CircleDetails circleDetails) {
    return beatPlanBulkUploadDAO.createCircle(circleDetails);
  }

  // ========================================================================
  
  @Override
  public RegionDetails isRegionExists(String regionName) {
    return beatPlanBulkUploadDAO.isRegionExists(regionName);
  }

  // ========================================================================
  
  @Override
  public RegionDetails createRegion(RegionDetails regionDetails) {
    return beatPlanBulkUploadDAO.createRegion(regionDetails);
  }

  // ========================================================================
  
  @Override
  public ZoneDetails isZoneExists(String zoneName) {
    return beatPlanBulkUploadDAO.isZoneExists(zoneName);
  }

  @Override
  public ZoneDetails createZone(ZoneDetails zoneDetails) {
    return beatPlanBulkUploadDAO.createZone(zoneDetails);
  }

  // ========================================================================
  
  @Override
  public HubDetails isHubExists(String hubName) {
    return beatPlanBulkUploadDAO.isHubExists(hubName);
  }

  // ========================================================================
  
  @Override
  public HubDetails createHub(HubDetails hubDetails) {
    return beatPlanBulkUploadDAO.createHub(hubDetails);
  }

  // ========================================================================
  
  @Override
  public DistributorDetails isDistributorExists(String distributorName) {
    return beatPlanBulkUploadDAO.isDistributorExists(distributorName);
  }

  // ========================================================================
  
  @Override
  public DistributorDetails createDistributor(DistributorDetails distributorDetails) {
    return beatPlanBulkUploadDAO.createDistributor(distributorDetails);
  }

  // ========================================================================
  
  @Override
  public UserDetails isUserExists(String userName) {
    return beatPlanBulkUploadDAO.isUserExists(userName);
  }

  // ========================================================================
  
  @Override
  public UserDetails createUser(UserDetails userDetails) {
    return beatPlanBulkUploadDAO.createUser(userDetails);
  }

  // ========================================================================
  
  @Override
  public String createBeatPlan(BeatPlanCircleDetails beatPlanCircleDetails) {
    return beatPlanBulkUploadDAO.createBeatPlan(beatPlanCircleDetails);
  }

  // ========================================================================

}
