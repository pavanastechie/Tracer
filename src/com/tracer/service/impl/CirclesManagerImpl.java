/**
 * @author Mohini
 *
 */
package com.tracer.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tracer.dao.model.CircleDetails;
import com.tracer.dao.model.HubDetails;
import com.tracer.dao.model.RegionDetails;
import com.tracer.dao.model.ZoneDetails;
import com.tracer.dao.persistence.CirclesDAO;
import com.tracer.service.CirclesManager;
import com.tracer.service.impl.CirclesManagerImpl;

public class CirclesManagerImpl implements CirclesManager {
  protected transient final Log log = LogFactory.getLog(CirclesManagerImpl.class);
  protected CirclesDAO circlesDAO = null;

  //==========================================================================
  
  public void setCirclesDAO(CirclesDAO circlesDAO) {

    try {
      this.circlesDAO = circlesDAO;
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  //==========================================================================
  
  @Override
  public boolean isCircleCodeExists(String circleCode) throws Exception {
    return circlesDAO.isCircleCodeExists(circleCode);
  }
  
  //==========================================================================
  
  @Override
  public boolean isRegionCodeExists(String regionCode) throws Exception {
    return circlesDAO.isRegionCodeExists(regionCode);
  }
  
  //==========================================================================
  
  @Override
  public boolean isZoneCodeExists(String zoneCode) throws Exception {
    return circlesDAO.isZoneCodeExists(zoneCode);
  }
  
  //==========================================================================
  
  @Override
  public boolean isHubCodeExists(String hubCode) throws Exception {
    return circlesDAO.isHubCodeExists(hubCode);
  }
  
  //==========================================================================
  
  @Override
  public List<CircleDetails> getCircles(Long userId, Long roleId) throws Exception {
    return circlesDAO.getCircles(userId, roleId);
  }
  
  //==========================================================================
  
  @Override
  public List<RegionDetails> getRegions(Long circleId) throws Exception {
    return circlesDAO.getRegions(circleId);
  }
  
  //==========================================================================
  
  @Override
  public List<ZoneDetails> getZones(Long regionId) throws Exception {
    return circlesDAO.getZones(regionId);
  }
  
  //==========================================================================
  
  @Override
  public List<HubDetails> getHubs(Long zoneId) throws Exception {
    return circlesDAO.getHubs(zoneId);
  }
  
  //==========================================================================
  
  @Override
  public Map<String, String> addCircle(Map<String, Object> circleDetailsMap) throws Exception {
    return circlesDAO.addCircle(circleDetailsMap);
  }
  
  //==========================================================================
  
  @Override
  public Map<String, String> updateCircle(Map<String, Object> circleDetailsMap) throws Exception {
    return circlesDAO.updateCircle(circleDetailsMap);
  }

  //==========================================================================
  
  @Override
  public String deleteCircle(Long circleId) throws Exception {
    return circlesDAO.deleteCircle(circleId);
  }

  //==========================================================================
  
  @Override
  public Map<String, Object> getCircleDetails(Long circleId) throws Exception {
  return circlesDAO.getCircleDetails(circleId);
  }

  //==========================================================================
  
  @Override
  public boolean isCircleNameExists(String circleName) throws Exception {
  return circlesDAO.isCircleNameExists(circleName);
  }

  //==========================================================================
  
  @Override
  public String isCircleDeletable(Long circleId) throws Exception {
    return circlesDAO.isCircleDeletable(circleId);
  }
  
  //==========================================================================
  
  @Override
  public String isZoneDeletable(Long regionId) throws Exception {
    return circlesDAO.isZoneDeletable(regionId);
  }
  
  //==========================================================================
  
  @Override
  public String isRegionDeletable(Long zoneId) throws Exception {
    return circlesDAO.isRegionDeletable(zoneId);
  }
  
  //==========================================================================
  
  @Override
  public String isHubDeletable(Long hubId) throws Exception {
    return circlesDAO.isHubDeletable(hubId);
  }

  //==========================================================================
}