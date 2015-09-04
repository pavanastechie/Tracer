/**
 * @author Mohini
 *
 */
package com.tracer.dao.persistence.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;

import com.tracer.dao.model.CircleDetails;
import com.tracer.dao.model.DistributorToHubDetails;
import com.tracer.dao.model.HubDetails;
import com.tracer.dao.model.RegionDetails;
import com.tracer.dao.model.UserToCircleDetails;
import com.tracer.dao.model.UserToHubDetails;
import com.tracer.dao.model.UserToRegionDetails;
import com.tracer.dao.model.UserToZoneDetails;
import com.tracer.dao.model.ZoneDetails;
import com.tracer.dao.persistence.CirclesDAO;
import com.tracer.dao.persistence.impl.BaseDAOHibernate;
import com.tracer.dao.persistence.impl.CirclesDAOHibernate;
import com.tracer.util.StringUtil;
import static com.tracer.common.Constants.*;

public class CirclesDAOHibernate extends BaseDAOHibernate implements CirclesDAO {
  protected transient final Log log = LogFactory.getLog(CirclesDAOHibernate.class);

  //==========================================================================
  
  @SuppressWarnings("unchecked")
  @Override
  public boolean isCircleCodeExists(String circleCode) throws Exception {
    log.info("START of the method isCircleCodeExists");
    StringBuffer hqlQuery;
    List<CircleDetails> resultList = null;
    boolean isCircleCodeExists = true;
    
    try {
      if(StringUtil.isNotNull(circleCode)) {
        hqlQuery = new StringBuffer("SELECT cd FROM CircleDetails cd WHERE cd.circleCode='"+circleCode+"'");
        resultList = getHibernateTemplate().find(hqlQuery.toString());
        
        if(resultList != null && resultList.size() > 0) {
            isCircleCodeExists = true;
        } else {
            isCircleCodeExists = false;
        }
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method isCircleCodeExists");
      log.error(e);
      e.printStackTrace();
    }
    log.info("END of the method isCircleCodeExists");
    return isCircleCodeExists;
  }
  
  //==========================================================================
  
  @SuppressWarnings("unchecked")
  @Override
  public boolean isRegionCodeExists(String regionCode) throws Exception {
    log.info("START of the method isRegionCodeExists");
    StringBuffer hqlQuery;
    List<RegionDetails> resultList = null;
    boolean isRegionCodeExists = true;
    
    try {
      if(StringUtil.isNotNull(regionCode)) {
        hqlQuery = new StringBuffer("SELECT rd FROM RegionDetails rd WHERE rd.regionCode='"+regionCode+"'");
        resultList = getHibernateTemplate().find(hqlQuery.toString());
        
        if(resultList != null && resultList.size() > 0) {
            isRegionCodeExists = true;
        } else {
            isRegionCodeExists = false;
        }
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method isRegionCodeExists");
      log.error(e);
      e.printStackTrace();
    }
    log.info("END of the method isRegionCodeExists");
    return isRegionCodeExists;
  }
  
  //==========================================================================
  
  @SuppressWarnings("unchecked")
  @Override
  public boolean isZoneCodeExists(String zoneCode) throws Exception {
    log.info("START of the method isZoneCodeExists");
    StringBuffer hqlQuery;
    List<ZoneDetails> resultList = null;
    boolean isZoneCodeExists = true;
    
    try {
      if(StringUtil.isNotNull(zoneCode)) {
        hqlQuery = new StringBuffer("SELECT zd FROM ZoneDetails zd WHERE zd.zoneCode='"+zoneCode+"'");
        resultList = getHibernateTemplate().find(hqlQuery.toString());
        
        if(resultList != null && resultList.size() > 0) {
            isZoneCodeExists = true;
        } else {
            isZoneCodeExists = false;
        }
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method isZoneCodeExists");
      log.error(e);
      e.printStackTrace();
    }
    log.info("END of the method isZoneCodeExists");
    return isZoneCodeExists;
  }
  
  //==========================================================================
  
  @SuppressWarnings("unchecked")
  @Override
  public boolean isHubCodeExists(String hubCode) throws Exception {
    log.info("START of the method isHubCodeExists");
    StringBuffer hqlQuery;
    List<HubDetails> resultList = null;
    boolean isHubCodeExists = true;
    
    try {
      if(StringUtil.isNotNull(hubCode)) {
        hqlQuery = new StringBuffer("SELECT hd FROM HubDetails hd WHERE hd.hubCode='"+hubCode+"'");
        resultList = getHibernateTemplate().find(hqlQuery.toString());
        
        if(resultList != null && resultList.size() > 0) {
            isHubCodeExists = true;
        } else {
            isHubCodeExists = false;
        }
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method isHubCodeExists");
      log.error(e);
      e.printStackTrace();
    }
    log.info("END of the method isHubCodeExists");
    return isHubCodeExists;
  }
  
  //==========================================================================
  
  @SuppressWarnings("unchecked")
  @Override
  public List<CircleDetails> getCircles(Long userId, Long roleId) throws Exception {
    log.info("START of the method getCircles");
    StringBuffer hqlQuery;
    List<CircleDetails> circlesList = null;
    List<CircleDetails> circles = null;
    List<UserToCircleDetails> utcdList = null;
    List<UserToRegionDetails> utrdList = null;

    try {
      if(userId != null && userId.longValue() > 0 && roleId != null && roleId.longValue() > 0) {
        
        if(roleId.longValue() == SYSTEM_ADMIN) {
          hqlQuery = new StringBuffer("SELECT cd FROM CircleDetails cd WHERE cd.status="+ACTIVE);
          circlesList = getHibernateTemplate().find(hqlQuery.toString());  
        } else if(roleId.longValue() == CIRCLE_MANAGER) {
          hqlQuery = new StringBuffer("SELECT utcd FROM UserToCircleDetails utcd WHERE utcd.status="+ACTIVE);
          hqlQuery.append(" AND utcd.userDetails.userId="+userId +" AND utcd.userDetails.status='"+ACTIVE+"' ");
          hqlQuery.append("AND utcd.circleDetails.status='"+ACTIVE+"'");
          utcdList = getHibernateTemplate().find(hqlQuery.toString());
          
          if(utcdList != null && utcdList.size() > 0) {
            circlesList = new ArrayList<CircleDetails>(); 
            
            for(UserToCircleDetails utcd : utcdList) {
              circlesList.add(utcd.getCircleDetails());  
            }
          }
        } else if(roleId.longValue() == REGION_MANAGER) {
        hqlQuery = new StringBuffer("SELECT utrd FROM UserToRegionDetails utrd WHERE utrd.status="+ACTIVE);
          hqlQuery.append(" AND utrd.userDetails.userId="+userId +" AND utrd.userDetails.status='"+ACTIVE+"' ");
          hqlQuery.append("AND utrd.regionDetails.status='"+ACTIVE+"'");
          utrdList = getHibernateTemplate().find(hqlQuery.toString());
        
          if(utrdList != null && utrdList.size() > 0) {
            circlesList = new ArrayList<CircleDetails>(); 
          
            for(UserToRegionDetails utrd : utrdList) {
              circlesList.add(utrd.getRegionDetails().getCircleDetails());  
            }
          }
        }
      }
      if(circlesList != null && circlesList.size() > 0) {
        circles = new ArrayList<CircleDetails>();
       
        for(CircleDetails circleDetails : circlesList) {
          circleDetails.setRegionsCount(getRegionsCount(circleDetails.getCircleId()));
          circleDetails.setZonesCount(getZonesCount(circleDetails.getCircleId()));
          circleDetails.setHubsCount(getHubsCount(circleDetails.getCircleId()));
          circleDetails.setManagers(getManagers(circleDetails.getCircleId()));
          circles.add(circleDetails);
        }
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method getCircles");
      log.error(e);
      e.printStackTrace();
    }
    log.info("END of the method getCircles");
    return circles;
  }
  
  //==========================================================================
  
  private int getRegionsCount(Long circleId) {
    int regionsCount = 0;
    StringBuffer nativeQuery;
    Query hibQuery = null;
    @SuppressWarnings("rawtypes")
    List resultList = null;
    
    try {
      if(circleId != null && circleId.longValue() > 0) {
        /*select count(*) from region_details as rd where rd.status=1 and rd.circle_id=1;*/    
        nativeQuery = new StringBuffer(" select count(*) from region_details as rd where rd.status='"+ACTIVE+"' and rd.circle_id="+circleId);
        hibQuery = getSession().createSQLQuery(nativeQuery.toString());
        resultList = hibQuery.list();
        
        if(resultList != null && resultList.size() > 0) {
          BigInteger resultBigInt = (BigInteger) resultList.get(0);
          regionsCount = resultBigInt.intValue();
        }
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method getRegionsCount");
      log.error(e);
      e.printStackTrace();
    }
    return regionsCount;
  }
  
  //==========================================================================
  
  private int getZonesCount(Long circleId) {
    int zonesCount = 0;
    StringBuffer nativeQuery;
    Query hibQuery = null;
    @SuppressWarnings("rawtypes")
    List resultList = null;
    
    try {
      if(circleId != null && circleId.longValue() > 0) {
        /*select count(*) from zone_details as zd where zd.status=1 and 
        zd.region_id in (select rd.region_id from region_details as rd where rd.status=1 and rd.circle_id=1);*/    
        nativeQuery = new StringBuffer(" select count(*) from zone_details as zd where zd.status='"+ACTIVE+"' and ");
        nativeQuery.append(" zd.region_id in (select rd.region_id from region_details as rd where rd.status='"+ACTIVE+"' and rd.circle_id="+circleId+")");
        hibQuery = getSession().createSQLQuery(nativeQuery.toString());
        resultList = hibQuery.list();
        
        if(resultList != null && resultList.size() > 0) {
          BigInteger resultBigInt = (BigInteger) resultList.get(0);
          zonesCount = resultBigInt.intValue();
        }
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method getZonesCount");
      log.error(e);
      e.printStackTrace();
    }
    return zonesCount;
  }
  
  //==========================================================================
  
  private int getHubsCount(Long circleId) {
    int hubsCount = 0;
    StringBuffer nativeQuery;
    Query hibQuery = null;
    @SuppressWarnings("rawtypes")
    List resultList = null;
    
    try {
      if(circleId != null && circleId.longValue() > 0) {
        /*select count(*) from hub_details as hd where hd.status=1 and 
        hd.zone_id in (select zd.zone_id from zone_details as zd where zd.status=1 and 
        zd.region_id in (select rd.region_id from region_details as rd where rd.status=1 and rd.circle_id=1));*/    
        nativeQuery = new StringBuffer(" select count(*) from hub_details as hd where hd.status='"+ACTIVE+"' and ");
        nativeQuery.append(" hd.zone_id in (select zd.zone_id from zone_details as zd where zd.status='"+ACTIVE+"' and ");
        nativeQuery.append(" zd.region_id in (select rd.region_id from region_details as rd where rd.status='"+ACTIVE+"' and rd.circle_id="+circleId+"))");
        hibQuery = getSession().createSQLQuery(nativeQuery.toString());
        resultList = hibQuery.list();
        
        if(resultList != null && resultList.size() > 0) {
          BigInteger resultBigInt = (BigInteger) resultList.get(0);
          hubsCount = resultBigInt.intValue();
        }
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method getHubsCount");
      log.error(e);
      e.printStackTrace();
    }
    return hubsCount;
  }
  
  //==========================================================================
  
  private String[] getManagers(Long circleId) {
    String[] managers = null;
    StringBuffer nativeQuery;
    Query hibQuery = null;
    @SuppressWarnings("rawtypes")
    List resultList = null;
    
    try {
      if(circleId != null && circleId.longValue() > 0) {
      /*select ud.last_name, ud.name from user_to_circle_details as utcd, user_details as ud 
        where utcd.status=1 and utcd.circle_id=1
        and ud.user_id=utcd.user_id
        and ud.status=1;*/  
      nativeQuery = new StringBuffer(" select ud.last_name, ud.name from user_to_circle_details as utcd, user_details as ud ");
      nativeQuery.append(" where utcd.status='"+ACTIVE+"' and utcd.circle_id="+circleId);
      nativeQuery.append(" and ud.user_id=utcd.user_id and ud.status='"+ACTIVE+"'");
      nativeQuery.append(" ");
      hibQuery = getSession().createSQLQuery(nativeQuery.toString());
        resultList = hibQuery.list();
        
        if(resultList != null && resultList.size() > 0) {
          managers = new String[resultList.size()];
          for(int i = 0; i < resultList.size(); i++) {
            Object[] result = (Object[]) resultList.get(i);
            managers[i] = result[1].toString().concat(" ").concat(result[0].toString());
          }
        }
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method getManagers");
      log.error(e);
      e.printStackTrace();
    }
    return managers;
  }
  
  //==========================================================================
  @SuppressWarnings("unchecked")
  @Override
  public List<RegionDetails> getRegions(Long circleId) throws Exception {
    log.info("START of the method getRegions");
    StringBuffer hqlQuery;
    List<RegionDetails> regions = null;
    
    try {
      if(circleId != null && circleId.longValue() > 0) {
        hqlQuery = new StringBuffer("SELECT rd FROM RegionDetails rd WHERE rd.status='"+ACTIVE+"' and rd.circleDetails.circleId="+circleId);
        regions = getHibernateTemplate().find(hqlQuery.toString());
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method getRegions");
      log.error(e);
      e.printStackTrace();
    }
    log.info("END of the method getRegions");
    return regions;
  }
  
  //==========================================================================
  
  @SuppressWarnings("unchecked")
  @Override
  public List<ZoneDetails> getZones(Long regionId) throws Exception {
    log.info("START of the method getZones");
    StringBuffer hqlQuery;
    List<ZoneDetails> zones = null;
    
    try {
      if(regionId != null && regionId.longValue() > 0) {
        hqlQuery = new StringBuffer("SELECT zd FROM ZoneDetails zd WHERE zd.status='"+ACTIVE+"' and zd.regionDetails.regionId="+regionId);
        zones = getHibernateTemplate().find(hqlQuery.toString());
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method getZones");
      log.error(e);
      e.printStackTrace();
    }
    log.info("END of the method getZones");
    return zones;
  }
  
  //==========================================================================
  
  @SuppressWarnings("unchecked")
  @Override
  public List<HubDetails> getHubs(Long zoneId) throws Exception {
    log.info("START of the method getHubs");
    StringBuffer hqlQuery;
    List<HubDetails> hubs = null;
    
    try {
      if(zoneId != null && zoneId.longValue() > 0) {
        hqlQuery = new StringBuffer("SELECT hd FROM HubDetails hd WHERE hd.status='"+ACTIVE+"' and hd.zoneDetails.zoneId="+zoneId);
        hubs = getHibernateTemplate().find(hqlQuery.toString());
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method getHubs");
      log.error(e);
      e.printStackTrace();
    }
    log.info("END of the method getHubs");
    return hubs;
  }
  
  //==========================================================================
  
  @SuppressWarnings("unchecked")
  @Override
  public Map<String, String> addCircle(Map<String, Object> circleDetailsMap) throws Exception {
    log.info("START of the method addCircle");
    String result = FAILURE;
    String errorMessage = "";
    Map<String, String> resultMap = null;
    CircleDetails circleDetails = null;
    List<RegionDetails> regions = null;
    List<ZoneDetails> zones = null;
    List<HubDetails> hubs = null;
    
    try {
      resultMap = new HashMap<String, String>();
      
      if(circleDetailsMap != null && !(circleDetailsMap.isEmpty())) {
        if(circleDetailsMap.get(CIRCLE_DETAILS) != null && circleDetailsMap.get(REGIONS) != null 
          && circleDetailsMap.get(ZONES) != null && circleDetailsMap.get(HUBS) != null) { 
          circleDetails = (CircleDetails) circleDetailsMap.get(CIRCLE_DETAILS);
          getHibernateTemplate().save(circleDetails);
          regions = ((List<RegionDetails>) circleDetailsMap.get(REGIONS));
          zones = ((List<ZoneDetails>) circleDetailsMap.get(ZONES));
          hubs = ((List<HubDetails>) circleDetailsMap.get(HUBS));
          
          if(regions != null && zones != null && hubs != null && regions.size() > 0 && zones.size() > 0 && hubs.size() > 0) {
            for(RegionDetails regionDetails : regions) {
              if(regionDetails != null) {
                getHibernateTemplate().save(regionDetails);
                List<ZoneDetails> zonesOfRegion = getZonesOfRegion(zones, regionDetails.getRegionCode());
                
                if(zonesOfRegion != null && zonesOfRegion.size() > 0) {
                  for(ZoneDetails zoneDetails : zonesOfRegion) {
                    zoneDetails.setRegionDetails(regionDetails);
                    getHibernateTemplate().save(zoneDetails);
                    List<HubDetails> hubsOfZone = getHubsOfZone(hubs, zoneDetails.getZoneCode());
                    
                    if(hubsOfZone != null && hubsOfZone.size() > 0) {
                      for(HubDetails hubDetails : hubsOfZone) {
                        hubDetails.setZoneDetails(zoneDetails);
                        getHibernateTemplate().save(hubDetails);
                      }    
                    } else  {
                      errorMessage = "Zone must have atleast one Hub";
                    }
                  }
                } else {
                  errorMessage = "Region must have atleast one Zone";    
                }
              }
            }  
          } else {
              errorMessage = INVALID_INPUT;
          }
        } else {
            errorMessage = INVALID_INPUT;
        }
      } else {
          errorMessage = INVALID_INPUT;
      }
      
      if(StringUtil.isNotNull(errorMessage)) {
      // Need to roll back the circle.

      } else {
        result = SUCCESS;
      }
      resultMap.put(RESULT, result);
      resultMap.put(ERROR_MESSAGE, errorMessage);
    } catch (Exception e) {
      log.error("PROBLEM in the method addCircle");
      log.error(e);
      e.printStackTrace();
    }
    log.info("END of the method addCircle");
    return resultMap;
  }
  
  //==========================================================================
  
  private List<ZoneDetails> getZonesOfRegion(List<ZoneDetails> zones, String regionCode) throws Exception {
    log.info("START of the method getZonesOfRegion");
    List<ZoneDetails> zonesOfRegion = null;
    
    try {
      if(zones != null && zones.size() > 0 && StringUtil.isNotNull(regionCode)) {
        zonesOfRegion = new ArrayList<ZoneDetails>();
        for(ZoneDetails zoneDetails : zones) {
          if(zoneDetails != null && zoneDetails.getRegionDetails().getRegionCode().equals(regionCode)) {
            zonesOfRegion.add(zoneDetails); 
          }
        }
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method getZonesOfRegion");
      log.error(e);
      e.printStackTrace();
    }
    log.info("END of the method getZonesOfRegion");
    return zonesOfRegion;
  }
  
  //==========================================================================
  
  private List<HubDetails> getHubsOfZone(List<HubDetails> hubs, String zoneCode) throws Exception {
    log.info("START of the method getHubsOfZone");
    List<HubDetails> hubsOfZone = null;
    
    try {
      if(hubs != null && hubs.size() > 0 && StringUtil.isNotNull(zoneCode)) {
        hubsOfZone = new ArrayList<HubDetails>();
        for(HubDetails hubDetails : hubs) {
          if(hubDetails != null && hubDetails.getZoneDetails().getZoneCode().equals(zoneCode)) {
            hubsOfZone.add(hubDetails); 
          }
        }
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method getHubsOfZone");
      log.error(e);
      e.printStackTrace();
    }
    log.info("END of the method getHubsOfZone");
    return hubsOfZone;
  }
  
  //==========================================================================
  
  @SuppressWarnings("unchecked")
  @Override
  public Map<String, String> updateCircle(Map<String, Object> circleDetailsMap) throws Exception {
    log.info("START of the method updateCircle");
    String result = FAILURE;
    String errorMessage = "";
    Map<String, String> resultMap = null;
    CircleDetails circleDetails = null;
    List<RegionDetails> regions = null;
    List<ZoneDetails> zones = null;
    List<HubDetails> hubs = null;
    
    try {
      resultMap = new HashMap<String, String>();
      
      if(circleDetailsMap != null && !(circleDetailsMap.isEmpty())) {
        if(circleDetailsMap.get(CIRCLE_DETAILS) != null && circleDetailsMap.get(REGIONS) != null 
          && circleDetailsMap.get(ZONES) != null && circleDetailsMap.get(HUBS) != null) { 
          circleDetails = (CircleDetails) circleDetailsMap.get(CIRCLE_DETAILS);
          getHibernateTemplate().merge(circleDetails);
          regions = ((List<RegionDetails>) circleDetailsMap.get(REGIONS));
          zones = ((List<ZoneDetails>) circleDetailsMap.get(ZONES));
          hubs = ((List<HubDetails>) circleDetailsMap.get(HUBS));
          
          if(regions != null && zones != null && hubs != null && regions.size() > 0 && zones.size() > 0 && hubs.size() > 0) {
            for(RegionDetails regionDetails : regions) {
              if(regionDetails != null) {
                if(regionDetails.getRegionId() != null) {
                  getHibernateTemplate().merge(regionDetails);
                } else {
                  getHibernateTemplate().save(regionDetails);
                }
                List<ZoneDetails> zonesOfRegion = getZonesOfRegion(zones, regionDetails.getRegionCode());
                
                if(zonesOfRegion != null && zonesOfRegion.size() > 0) {
                  for(ZoneDetails zoneDetails : zonesOfRegion) {
                    zoneDetails.setRegionDetails(regionDetails);
                    if(zoneDetails.getZoneId() != null) {
                      getHibernateTemplate().merge(zoneDetails);
                    } else {
                      getHibernateTemplate().save(zoneDetails);
                    }
                    List<HubDetails> hubsOfZone = getHubsOfZone(hubs, zoneDetails.getZoneCode());
                    
                    if(hubsOfZone != null && hubsOfZone.size() > 0) {
                      for(HubDetails hubDetails : hubsOfZone) {
                        hubDetails.setZoneDetails(zoneDetails);
                        if (hubDetails.getHubId() != null) {
                          getHibernateTemplate().merge(hubDetails);
                        } else {
                          getHibernateTemplate().save(hubDetails);
                        }
                      }
                    } else  {
                      errorMessage = "Zone must have atleast one Hub";
                    }
                  }
                } else {
                  errorMessage = "Region must have atleast one Zone";    
                }
              }
            }  
          } else {
              errorMessage = INVALID_INPUT;
          }
        } else {
            errorMessage = INVALID_INPUT;
        }
      } else {
          errorMessage = INVALID_INPUT;
      }
      
      if(StringUtil.isNotNull(errorMessage)) {
        // Need to roll back the circle.

      } else {
        result = SUCCESS;
      }
      resultMap.put(RESULT, result);
      resultMap.put(ERROR_MESSAGE, errorMessage);
    } catch (Exception e) {
      log.error("PROBLEM in the method updateCircle");
      log.error(e);
      e.printStackTrace();
    }
    log.info("END of the method updateCircle");
    return resultMap;
  }

  //==========================================================================
  
  @SuppressWarnings("unchecked")
  @Override
  public String deleteCircle(Long circleId) throws Exception {
    log.info("START of the method deleteCircle");
    String result = FAILURE;
    CircleDetails circleDetails = null;
    List<RegionDetails> regions = null;
    List<ZoneDetails> zones = null;
    List<HubDetails> hubs = null;
    StringBuffer regionIds = null;
    StringBuffer hqlQuery = null;
    StringBuffer zoneIds = null;
    
    try {
      if(circleId != null && circleId > 0) {
      regions = getRegions(circleId);
      
        if(regions != null && regions.size() > 0) {
          regionIds = new StringBuffer("");
          for(RegionDetails regionDetails : regions) {
            regionDetails.setStatus(IN_ACTIVE);
            regionIds.append(regionDetails.getRegionId());
            regionIds.append(",");
          }
          getHibernateTemplate().saveOrUpdateAll(regions);
          hqlQuery = new StringBuffer("SELECT zd FROM ZoneDetails zd WHERE zd.status='"+ACTIVE+"' and zd.regionDetails.regionId in("+StringUtil.removeTrailingValue(regionIds, ",")+")");
          zones = getHibernateTemplate().find(hqlQuery.toString());
        
          if(zones != null && zones.size() > 0) {
            zoneIds = new StringBuffer("");
          
            for(ZoneDetails zoneDetails : zones) {
              zoneDetails.setStatus(IN_ACTIVE);
              zoneIds.append(zoneDetails.getZoneId());
              zoneIds.append(",");
            } 
            getHibernateTemplate().saveOrUpdateAll(zones);
            hqlQuery = new StringBuffer("SELECT hd FROM HubDetails hd WHERE hd.status='"+ACTIVE+"' and hd.zoneDetails.zoneId in("+StringUtil.removeTrailingValue(zoneIds, ",")+")");
            hubs = getHibernateTemplate().find(hqlQuery.toString());
          
            if(hubs != null && hubs.size() > 0) {
              for(HubDetails hubDetails : hubs) {
                hubDetails.setStatus(IN_ACTIVE);
              }
              getHibernateTemplate().saveOrUpdateAll(hubs);
            }
          }
        }
        circleDetails = (CircleDetails) getHibernateTemplate().get(CircleDetails.class, circleId);
        circleDetails.setStatus(IN_ACTIVE);
        getHibernateTemplate().merge(circleDetails);
        result= SUCCESS;
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method deleteCircle");
      log.error(e);
      e.printStackTrace();
    }
    log.info("END of the method deleteCircle");
    return result;
  }

  //==========================================================================
  
  @SuppressWarnings("unchecked")
@Override
  public Map<String, Object> getCircleDetails(Long circleId) throws Exception {
    log.info("START of the method getCircleDetails");
    Map<String, Object> circleDetailsMap = null;
    CircleDetails circleDetails = null;
    List<RegionDetails> regions = null;
    List<ZoneDetails> zones = null;
    List<HubDetails> hubs = null;
    StringBuffer regionIds = null;
    StringBuffer hqlQuery = null;
    StringBuffer zoneIds = null;
    
    
    try {
      if(circleId != null && circleId.longValue() > 0) {
        circleDetails = (CircleDetails) getHibernateTemplate().get(CircleDetails.class, circleId);
        regions = getRegions(circleId);
        
        if(regions != null && regions.size() > 0) {
          regionIds = new StringBuffer("");
          for(RegionDetails regionDetails : regions) {
            regionIds.append(regionDetails.getRegionId());
            regionIds.append(",");
          }
          hqlQuery = new StringBuffer("SELECT zd FROM ZoneDetails zd WHERE zd.status='"+ACTIVE+"' and zd.regionDetails.regionId in("+StringUtil.removeTrailingValue(regionIds, ",")+")");
          zones = getHibernateTemplate().find(hqlQuery.toString());
          
          if(zones != null && zones.size() > 0) {
            zoneIds = new StringBuffer("");
            
            for(ZoneDetails zoneDetails : zones) {
              zoneIds.append(zoneDetails.getZoneId());
              zoneIds.append(",");
            } 
            hqlQuery = new StringBuffer("SELECT hd FROM HubDetails hd WHERE hd.status='"+ACTIVE+"' and hd.zoneDetails.zoneId in("+StringUtil.removeTrailingValue(zoneIds, ",")+")");
            hubs = getHibernateTemplate().find(hqlQuery.toString());
            
            if(hubs != null && hubs.size() > 0) {
              circleDetailsMap = new HashMap<String, Object>();
              circleDetailsMap.put(CIRCLE_DETAILS, circleDetails);
              circleDetailsMap.put(REGIONS, regions);
              circleDetailsMap.put(ZONES, zones);
              circleDetailsMap.put(HUBS, hubs);
            }
          }
        }
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method getCircleDetails");
      log.error(e);
      e.printStackTrace();
    }
    log.info("END of the method getCircleDetails");  
    return circleDetailsMap;
  }

  //==========================================================================
  
  @SuppressWarnings("unchecked")
  @Override
  public boolean isCircleNameExists(String circleName) throws Exception {
    log.info("START of the method isCircleCodeExists");
    StringBuffer hqlQuery;
    List<CircleDetails> circles = null;
    boolean isCircleCodeExists = false;
    
    try {
      if(StringUtil.isNotNull(circleName)) {
        hqlQuery = new StringBuffer("SELECT cd FROM CircleDetails cd ");
        circles = getHibernateTemplate().find(hqlQuery.toString());
        
        if(circles != null && circles.size() > 0) {
          for(CircleDetails circleDetails : circles) {
            if(circleDetails.getCircleName().equalsIgnoreCase(circleName)) {
              isCircleCodeExists = true;
              break;
            }
          }
        } 
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method isCircleCodeExists");
      log.error(e);
      e.printStackTrace();
    }
    log.info("END of the method isCircleCodeExists");
    return isCircleCodeExists;
  }
  
  //==========================================================================
  
  @Override
  public String isCircleDeletable(Long circleId) throws Exception {
    log.info("START of the method isCircleDeletable");
    String result = NO;
    
    try {
        
    } catch (Exception e) {
      log.error("PROBLEM in the method isCircleDeletable");
      log.error(e);
      e.printStackTrace();
    }
    log.info("END of the method isCircleDeletable");
    return result;
  }
  
  //==========================================================================
  
  @SuppressWarnings("unchecked")
  @Override
  public String isRegionDeletable(Long regionId) throws Exception {
    log.info("START of the method isRegionDeletable");
    String result = NO;
    StringBuffer hqlQuery;
    List<UserToRegionDetails> utrList = null;
    List<ZoneDetails> zdList = null;
    StringBuffer zoneIds = new StringBuffer(" ");
    List<UserToZoneDetails> utzList = null;
    List<HubDetails> hdList = null;
    StringBuffer hubIds = new StringBuffer(" ");
    List<UserToHubDetails> uthList = null;
    List<DistributorToHubDetails> dthList = null;
    
    try {
      if(regionId != null && regionId.longValue() > 0) {
        hqlQuery = new StringBuffer(" SELECT utr FROM UserToRegionDetails utr WHERE utr.regionDetails.regionId="+regionId);
        utrList = getHibernateTemplate().find(hqlQuery.toString());
        
        if(utrList != null && utrList.size() > 0) {
          result = "Region cannot be deleted as it is being to assigned to user(s)";		
        } else {
          hqlQuery = new StringBuffer(" SELECT zd FROM ZoneDetails zd WHERE zd.regionDetails.regionId="+regionId);
          zdList = getHibernateTemplate().find(hqlQuery.toString());
          
          if(zdList != null && zdList.size() > 0) {
            for(ZoneDetails zoneDetails : zdList) {
              zoneIds.append(zoneDetails.getZoneId());
              zoneIds.append(",");	
            }
            hqlQuery = new StringBuffer(" SELECT utz FROM UserToZoneDetails utz WHERE utz.zoneDetails.zoneId in ("+StringUtil.removeTrailingValue(zoneIds, ",")+")");
            utzList = getHibernateTemplate().find(hqlQuery.toString());
            
            if(utzList != null && utzList.size() > 0) {
              result = "Region cannot be deleted as the zone(s) in this Region are being to assigned to user(s)";  
            } else {
              hqlQuery = new StringBuffer(" SELECT hd FROM HubDetails hd WHERE hd.zoneDetails.zoneId in ("+StringUtil.removeTrailingValue(zoneIds, ",")+")");
              hdList = getHibernateTemplate().find(hqlQuery.toString());
                
              if(hdList != null && hdList.size() > 0) {
                for(HubDetails hubDetails : hdList) {
                  hubIds.append(hubDetails.getHubId());
                  hubIds.append(",");
                }
                hqlQuery = new StringBuffer(" SELECT uth FROM UserToHubDetails uth WHERE uth.hubDetails.hubId in ("+StringUtil.removeTrailingValue(hubIds, ",")+")");
                uthList = getHibernateTemplate().find(hqlQuery.toString());
                  
                if(uthList != null && uthList.size() > 0) {
                  result = "Region cannot be deleted as the Hub in this Region are being to assigned to user(s)";  
                } else {
                  hqlQuery = new StringBuffer(" SELECT dth FROM DistributorToHubDetails dth WHERE dth.hubDetails.hubId  in ("+StringUtil.removeTrailingValue(hubIds, ",")+")");
                  dthList = getHibernateTemplate().find(hqlQuery.toString());
                    
                  if(dthList != null && dthList.size() > 0) {
                    result = "Region cannot be deleted as the Hub in this Region are being to assigned to Distributor(s)";
                  }
                }
              }
            }
            
          }
        }
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method isRegionDeletable");
      log.error(e);
      e.printStackTrace();
    }
    log.info("END of the method isRegionDeletable");
    return result;
  }
  
  //==========================================================================
  
  @SuppressWarnings("unchecked")
  @Override
  public String isZoneDeletable(Long zoneId) throws Exception {
    log.info("START of the method isZoneDeletable");
    String result = OK;
    StringBuffer hqlQuery;
    List<UserToZoneDetails> utzList = null;
    List<HubDetails> hdList = null;
    StringBuffer hubIds = new StringBuffer(" ");
    List<UserToHubDetails> uthList = null;
    List<DistributorToHubDetails> dthList = null;
    
    try {
      if(zoneId != null && zoneId.longValue() > 0) {
        hqlQuery = new StringBuffer(" SELECT utz FROM UserToZoneDetails utz WHERE utz.zoneDetails.zoneId="+zoneId);
        utzList = getHibernateTemplate().find(hqlQuery.toString());
        
        if(utzList != null && utzList.size() > 0) {
          result = "Zone cannot be deleted as it is being to assigned to user(s)";	
        } else {
          hqlQuery = new StringBuffer(" SELECT hd FROM HubDetails hd WHERE hd.zoneDetails.zoneId="+zoneId);
          hdList = getHibernateTemplate().find(hqlQuery.toString());
          
          if(hdList != null && hdList.size() > 0) {
            for(HubDetails hubDetails : hdList) {
              hubIds.append(hubDetails.getHubId());
              hubIds.append(",");
            }
            hqlQuery = new StringBuffer(" SELECT uth FROM UserToHubDetails uth WHERE uth.hubDetails.hubId in ("+StringUtil.removeTrailingValue(hubIds, ",")+")");
            uthList = getHibernateTemplate().find(hqlQuery.toString());
            
            if(uthList != null && uthList.size() > 0) {
              result = "Zone cannot be deleted as the Hub in this Zone are being to assigned to user(s)";  
            } else {
              hqlQuery = new StringBuffer(" SELECT dth FROM DistributorToHubDetails dth WHERE dth.hubDetails.hubId  in ("+StringUtil.removeTrailingValue(hubIds, ",")+")");
              dthList = getHibernateTemplate().find(hqlQuery.toString());
              
              if(dthList != null && dthList.size() > 0) {
                result = "Zone cannot be deleted as the Hub in this Zone are being to assigned to Distributor(s)";
              }
            }
          }
        }
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method isZoneDeletable");
      log.error(e);
      e.printStackTrace();
    }
    log.info("END of the method isZoneDeletable");
    return result;
  }
  
  //==========================================================================
  
  @SuppressWarnings("unchecked")
  @Override
  public String isHubDeletable(Long hubId) throws Exception {
    log.info("START of the method isHubDeletable");
    String result = OK;
    StringBuffer hqlQuery;
    List<UserToHubDetails> uthList = null;
    List<DistributorToHubDetails> dthList = null;
    
    
    try {
      if(hubId != null && hubId.longValue() > 0) {
        hqlQuery = new StringBuffer(" SELECT uth FROM UserToHubDetails uth WHERE uth.hubDetails.hubId="+hubId);
        uthList = getHibernateTemplate().find(hqlQuery.toString());
        
        if(uthList != null && uthList.size() > 0) {
          result = "Hub cannot be deleted as it is being to assigned to user(s)";  
        } else {
          hqlQuery = new StringBuffer(" SELECT dth FROM DistributorToHubDetails dth WHERE dth.hubDetails.hubId="+hubId);
          dthList = getHibernateTemplate().find(hqlQuery.toString());
          
          if(dthList != null && dthList.size() > 0) {
            result = "Hub cannot be deleted as it is being to assigned to Distributor(s)";
          }
        }
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method isHubDeletable");
      log.error(e);
      e.printStackTrace();
    }
    log.info("END of the method isHubDeletable");
    return result;
  }

  //==========================================================================

}
