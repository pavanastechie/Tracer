/**
 * @author Mohini
 *
 */
package com.tracer.service;

import java.util.List;
import java.util.Map;

import com.tracer.dao.model.CircleDetails;
import com.tracer.dao.model.HubDetails;
import com.tracer.dao.model.RegionDetails;
import com.tracer.dao.model.ZoneDetails;

public interface CirclesManager {
  public boolean isCircleCodeExists(String circleCode) throws Exception;
  public boolean isRegionCodeExists(String regionCode) throws Exception;
  public boolean isZoneCodeExists(String zoneCode) throws Exception;
  public boolean isHubCodeExists(String hubCode) throws Exception;
  public boolean isCircleNameExists(String circleName) throws Exception;
  
  /*This method is very specific to get the circles details to show in the manage circles page*/
  public List<CircleDetails> getCircles(Long userId, Long roleId) throws Exception;
  public List<RegionDetails> getRegions(Long circleId) throws Exception;
  public List<ZoneDetails> getZones(Long regionId) throws Exception;
  public List<HubDetails> getHubs(Long zoneId) throws Exception;
  
  public Map<String, String> addCircle(Map<String, Object> circleDetailsMap) throws Exception;
  public Map<String, String> updateCircle(Map<String, Object> circleDetailsMap) throws Exception;
  
  public String deleteCircle(Long circleId) throws Exception;
  public Map<String, Object> getCircleDetails(Long circleId) throws Exception;
  
  public String isCircleDeletable(Long circleId) throws Exception;
  public String isZoneDeletable(Long regionId) throws Exception;
  public String isRegionDeletable(Long zoneId) throws Exception;
  public String isHubDeletable(Long hubId) throws Exception;
}
