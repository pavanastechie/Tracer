/**
 * @author Jp
 *
 */
package com.tracer.dao.persistence.impl;

import java.util.List;

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

public class BeatPlanBulkUploadDAOHibernate extends BaseDAOHibernate implements BeatPlanBulkUploadDAO {
  protected transient final Log log = LogFactory.getLog(BeatPlanBulkUploadDAOHibernate.class);
  
  // ========================================================================

  @SuppressWarnings({ "unchecked" })
@Override
  public CircleDetails isCircleExists(String circleName) {
    log.info("Start of the method isCircleExists");
    StringBuffer hqlQuery;
    List<CircleDetails> circles = null;
    CircleDetails circleDetails = null;
    try {
      hqlQuery = new StringBuffer("SELECT circleDetails FROM CircleDetails circleDetails WHERE circleDetails.circleName='" + circleName + "' ");
      circles = getHibernateTemplate().find(hqlQuery.toString());
    } catch (Exception e) {
      log.error("PROBLEM in the method getCircles");
      log.error(e);
      e.printStackTrace();
    }
    log.info("End of the method isCircleExists");
    return circleDetails;
  }

  // ========================================================================

  @Override
  public CircleDetails createCircle(CircleDetails circleDetails) {
    return null;
  }

  // ========================================================================

  @SuppressWarnings("unchecked")
@Override
  public RegionDetails isRegionExists(String regionName) {
    log.info("Start of the method isRegionExists");
    StringBuffer hqlRegionsQuery;
    List<RegionDetails> regions = null;
    RegionDetails regionDetails = null;
    CircleDetails circleDetails = null;
    
    try {
      hqlRegionsQuery = new StringBuffer("SELECT regionDetails FROM RegionDetails regionDetails WHERE regionDetails.regionId='" + regionName + "' ");
      regions = getHibernateTemplate().find(hqlRegionsQuery.toString());
      regionDetails = new RegionDetails();
      for (int i=0; i<regions.size(); i++) {
        circleDetails = regions.get(i).getCircleDetails();
        circleDetails.setCircleName(circleDetails.getCircleName());
        circleDetails.setCircleId(circleDetails.getCircleId());
        regionDetails.setCircleDetails(circleDetails);
        regionDetails.setRegionId(regions.get(i).getRegionId());
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method getCircles");
      log.error(e);
      e.printStackTrace();
    }
    log.info("End of the method isRegionExists");
    return regionDetails;
  }

  // ========================================================================

  @Override
  public RegionDetails createRegion(RegionDetails regionDetails) {
    return null;
  }

  // ========================================================================

  @SuppressWarnings("unchecked")
@Override
  public ZoneDetails isZoneExists(String zoneName) {
    StringBuffer hqlQuery;
    List<ZoneDetails> zones = null;
    ZoneDetails zoneDetails = null;
    
    try {
      hqlQuery = new StringBuffer("SELECT zoneDetails FROM ZoneDetails zoneDetails WHERE zoneDetails.zoneId='" + zoneName + "' ");
      zones = getHibernateTemplate().find(hqlQuery.toString());
      zoneDetails = new ZoneDetails();
      for (int i=0; i<zones.size(); i++) {
        zoneDetails.setRegionDetails(zones.get(i).getRegionDetails());
        zoneDetails.setZoneId(zones.get(i).getZoneId());
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method getCircles");
      log.error(e);
      e.printStackTrace();
    }
    return zoneDetails;
  }

  @Override
  public ZoneDetails createZone(ZoneDetails zoneDetails) {
    return null;
  }

  // ========================================================================

  @SuppressWarnings("unchecked")
@Override
  public HubDetails isHubExists(String hubName) {
    StringBuffer hqlQuery;
    List<HubDetails> hubs = null;
    HubDetails hubDetails = null;
    try {
      log.info("hubName ---> " + hubName);
      hqlQuery = new StringBuffer("SELECT hubDetails FROM HubDetails hubDetails WHERE hubDetails.hubName='" + hubName + "' ");
      hubs = getHibernateTemplate().find(hqlQuery.toString());
      hubDetails = new HubDetails();
      for (int i=0; i<hubs.size(); i++) {
        hubDetails.setHubId(hubs.get(i).getHubId());
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method getCircles");
      log.error(e);
      e.printStackTrace();
    }
    return hubDetails;
  }

  // ========================================================================

  @Override
  public HubDetails createHub(HubDetails hubDetails) {
    StringBuffer hqlQuery;
    try {
      log.info("hubName in isHubExists() ---> " + hubDetails.getHubName());
      isHubExists(hubDetails.getHubName());
      //hubDetails = 
      //hqlQuery = new StringBuffer("INSERT INTO HubDetails(hubId,hubName,hubCode,status,zoneDetails,userDetails,dateTime) SELECT WHERE hubDetails.hubName='" + hubName + "' ");
    } catch (Exception e) {
      
    }
    return null;
  }

  // ========================================================================

  @SuppressWarnings("unchecked")
@Override
  public DistributorDetails isDistributorExists(String distributorName) {
    StringBuffer hqlQuery;
    List<DistributorDetails> distributors = null;
    DistributorDetails distributorDetails = null;
    try {
      log.info("distributorName ---> " + distributorName);
      distributorDetails = new DistributorDetails();
      hqlQuery = new StringBuffer("SELECT distributorDetails FROM DistributorDetails distributorDetails WHERE distributorDetails.distributorName='" + distributorName + "' ");
      distributors = getHibernateTemplate().find(hqlQuery.toString());
      for (int i=0; i<distributors.size(); i++) {
        distributorDetails.setDistributorId(distributors.get(i).getDistributorId());
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method getCircles");
      log.error(e);
      e.printStackTrace();
    }
    return distributorDetails;
  }

  // ========================================================================

  @Override
  public DistributorDetails createDistributor(DistributorDetails distributorDetails) {
    return null;
  }

  // ========================================================================
  
  @SuppressWarnings("unchecked")
@Override
  public UserDetails isUserExists(String userName) {
    StringBuffer hqlQuery;
    List<UserDetails> users = null;
    UserDetails userDetails = null;
    try {
      log.info("userName ---> " + userName);
      userDetails = new UserDetails();
      hqlQuery = new StringBuffer("SELECT userDetails FROM UserDetails userDetails WHERE userDetails.userName='" + userName + "' ");
      users = getHibernateTemplate().find(hqlQuery.toString());
      for (int i=0; i<users.size(); i++) {
        userDetails.setUserId(users.get(i).getUserId());
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method getCircles");
      log.error(e);
      e.printStackTrace();
    }
    return userDetails;
  }

  // ========================================================================

  @Override
  public UserDetails createUser(UserDetails userDetails) {
    return null;
  }

  // ========================================================================

  @Override
  public String createBeatPlan(BeatPlanCircleDetails beatPlanCircleDetails) {
    return null;
  }

  // ========================================================================

}
