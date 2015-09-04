/**
 * @author Jp
 *
 */
package com.tracer.dao.persistence.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tracer.util.StringUtil;
import com.tracer.dao.model.UserAuthCodeDetails;
import com.tracer.dao.model.UserDetails;
import com.tracer.dao.model.UserToCircleDetails;
import com.tracer.dao.model.UserToHubDetails;
import com.tracer.dao.model.UserToRegionDetails;
import com.tracer.dao.model.UserToZoneDetails;
import com.tracer.dao.persistence.LoginDAO;
import com.tracer.dao.persistence.impl.BaseDAOHibernate;
import com.tracer.dao.persistence.impl.LoginDAOHibernate;
import static com.tracer.common.Constants.*;

public class LoginDAOHibernate extends BaseDAOHibernate implements LoginDAO {
  protected transient final Log log = LogFactory.getLog(LoginDAOHibernate.class);

  //========================================================================
  
  @SuppressWarnings("rawtypes")
  @Override
  public UserDetails getUserDetails(String userName, String encPassword) throws Exception {
    log.info("START of the method getUserDetails");
    StringBuffer hqlQuery;
    List resultsList = null;
    UserDetails userDetails = null;
    
    try {
     if(StringUtil.isNotNull(userName) && StringUtil.isNotNull(encPassword)) {
        hqlQuery = new StringBuffer("SELECT userDetails FROM UserDetails userDetails WHERE ");
        hqlQuery.append(" userDetails.userName=\'"+userName+"\'");
        hqlQuery.append(" and userDetails.password=\'"+encPassword+"\'");
        resultsList = getHibernateTemplate().find(hqlQuery.toString());
        
        if(resultsList != null && resultsList.size() > 0) {
            userDetails = (UserDetails) resultsList.get(0);
        }
      }    
    } catch (Exception e) {
      log.error("Problem in getUserDetails");
      log.error(e);
    }
    log.info("END of the method getUserDetails");
    return userDetails;
  }

  //========================================================================
  
  @SuppressWarnings("unchecked")
  @Override
  public UserAuthCodeDetails getUserAuthDetails(Long userId) throws Exception {
    log.info("START of the method getUserDetails");
    StringBuffer hqlQuery = null;
    List<UserAuthCodeDetails> resultList = null;
    UserAuthCodeDetails userAuthCodeDetails = null;
    
    try {
      if(userId != null && userId.longValue() > 0) {
        hqlQuery = new StringBuffer(" SELECT userAuthCodeDetails from UserAuthCodeDetails userAuthCodeDetails WHERE ");
        hqlQuery.append(" userAuthCodeDetails.userDetails.userId="+userId);
        hqlQuery.append(" and DATE(userAuthCodeDetails.loginTimestamp) = DATE(NOW()) "); 
        resultList = getHibernateTemplate().find(hqlQuery.toString());
        
        if(resultList != null && resultList.size() > 0) {
          userAuthCodeDetails = (UserAuthCodeDetails) resultList.get(0);
        }
      }
    } catch (Exception e) {
      log.error("Problem in getUserDetails");
      log.error(e);
    }
    log.info("END of the method getUserDetails");
    return userAuthCodeDetails;
  }

  //========================================================================
  
  @Override
  public void saveAuthDetails(UserAuthCodeDetails userAuthCodeDetails) throws Exception {
    log.info("START of the method saveAuthDetails");
    
    try {
      if(userAuthCodeDetails != null) {
        userAuthCodeDetails.setUserAuthCodeId(null);
        userAuthCodeDetails.setStatus(ACTIVE);
        getHibernateTemplate().save(userAuthCodeDetails);
      }
    } catch (Exception e) {
      log.error("Problem in saveAuthDetails");
      log.error(e);
    }
    log.info("END of the method saveAuthDetails");
  }

  //========================================================================
  
  @Override
  public void updateAuthDetails(UserAuthCodeDetails userAuthCodeDetails) throws Exception {
    log.info("START of the method updateAuthDetails");
    
    try {
      if(userAuthCodeDetails != null && userAuthCodeDetails.getUserAuthCodeId() > 0) {
       /* userAuthCodeDetails = (UserAuthCodeDetails) getHibernateTemplate().get(UserAuthCodeDetails.class, userAuthCodeDetails.getUserAuthCodeId());
        userAuthCodeDetails.setLogoutTimestamp(new Timestamp(new Date().getTime()));
        userAuthCodeDetails.setStatus(IN_ACTIVE);*/
        getHibernateTemplate().merge(userAuthCodeDetails);
      }
    } catch (Exception e) {
      log.error("Problem in updateAuthDetails");
      log.error(e);
    }
    log.info("END of the method updateAuthDetails");
  }

  //========================================================================
  
  @SuppressWarnings("unchecked")
  @Override
  public Map<String, Object> getAssignedComponets(Long userId, Long roleId) throws Exception {
    log.info("START of the method getAssignedComponets");
    int role = 0;
    String result = IN_ACTIVE;
    StringBuffer hqlQuery = null;
    List<UserToCircleDetails> userToCirclesList = null;
    List<UserToRegionDetails> userToRegionsList = null;
    List<UserToZoneDetails> userToZonesList = null;
    List<UserToHubDetails> userToHubsList = null;
    Map<String, Object> assignedComponentsMap = null;
    Long assignedCircleId = null;
    Long assignedRegionId = null;
    Long assignedZoneId = null;
    Long assignedHubId = null;
    String assignedHubName = null;
    
    try {
      if(userId != null && roleId != null) {
        role = (int) roleId.longValue();
        assignedComponentsMap = new HashMap<String, Object>();
        
        if(role > SYSTEM_ADMIN && role < RUNNER) {
          if(role == CIRCLE_MANAGER) {
            hqlQuery = new StringBuffer(" SELECT utc FROM UserToCircleDetails as utc where utc.status='"+ACTIVE+"' and utc.userDetails.userId="+userId);
            userToCirclesList = getHibernateTemplate().find(hqlQuery.toString());
            
            if(userToCirclesList != null && userToCirclesList.size() > 0) {
              for(UserToCircleDetails utc : userToCirclesList) {
                if(utc.getCircleDetails().getStatus().equals(IN_ACTIVE)) {
                  result = IN_ACTIVE;
                  break;
                } else {
                  assignedCircleId = utc.getCircleDetails().getCircleId();
                  result = ACTIVE;
                }
              }
            }
          } else if (role == REGION_MANAGER) {
            hqlQuery = new StringBuffer(" SELECT utr FROM UserToRegionDetails as utr where utr.status='"+ACTIVE+"' and utr.userDetails.userId="+userId);
            userToRegionsList = getHibernateTemplate().find(hqlQuery.toString());
          
            if(userToRegionsList != null && userToRegionsList.size() > 0) {
              for(UserToRegionDetails utr : userToRegionsList) {
                if(utr.getRegionDetails().getStatus().equals(IN_ACTIVE)) {
                  result = IN_ACTIVE;
                  break;
                } else {
                  result = ACTIVE;
                  assignedCircleId = utr.getRegionDetails().getCircleDetails().getCircleId();
                  assignedRegionId = utr.getRegionDetails().getRegionId();
                }
              }
            }
          } else if (role == ZONE_MANAGER) {
            hqlQuery = new StringBuffer(" SELECT utz FROM UserToZoneDetails as utz where utz.status='"+ACTIVE+"' and utz.userDetails.userId="+userId);
            userToZonesList = getHibernateTemplate().find(hqlQuery.toString());
          
            if(userToZonesList != null && userToZonesList.size() > 0) {
              for(UserToZoneDetails utz : userToZonesList) {
                if(utz.getZoneDetails().getStatus().equals(IN_ACTIVE)) {
                  result = IN_ACTIVE;
                  break;
                } else {
                  result = ACTIVE;
                  assignedCircleId = utz.getZoneDetails().getRegionDetails().getCircleDetails().getCircleId();
                  assignedRegionId = utz.getZoneDetails().getRegionDetails().getRegionId();
                  assignedZoneId = utz.getZoneDetails().getZoneId();
                }
              }
            }
          } else if (role == HUB_MANAGER || role == TEAM_LEADER) {
            hqlQuery = new StringBuffer(" SELECT uth FROM UserToHubDetails as uth where uth.status='"+ACTIVE+"' and uth.userDetails.userId="+userId);
            userToHubsList = getHibernateTemplate().find(hqlQuery.toString());
          
            if(userToHubsList != null && userToHubsList.size() > 0) {
              for(UserToHubDetails uth : userToHubsList) {
                if(uth.getHubDetails().getStatus().equals(IN_ACTIVE)) {
                  result = IN_ACTIVE;
                  break;
                } else {
                  result = ACTIVE;
                  assignedCircleId = uth.getHubDetails().getZoneDetails().getRegionDetails().getCircleDetails().getCircleId();
                  assignedRegionId = uth.getHubDetails().getZoneDetails().getRegionDetails().getRegionId();
                  assignedZoneId = uth.getHubDetails().getZoneDetails().getZoneId();
                  assignedHubId = uth.getHubDetails().getHubId();
                  assignedHubName = uth.getHubDetails().getHubName();
                }
              }
            }
          }
        } else if(role == SYSTEM_ADMIN) {
          result = ACTIVE;
        } else if(role == DEO) {
          result = ACTIVE;
        }
        assignedComponentsMap.put(RESULT, result);
        assignedComponentsMap.put(ASSIGNED_CIRCLE_ID, assignedCircleId);
        assignedComponentsMap.put(ASSIGNED_REGION_ID, assignedRegionId);
        assignedComponentsMap.put(ASSIGNED_ZONE_ID, assignedZoneId);
        assignedComponentsMap.put(ASSIGNED_HUB_ID, assignedHubId);
        assignedComponentsMap.put(ASSIGNED_HUB_NAME, assignedHubName);
      }
    } catch (Exception e) {
      log.error("Problem in getAssignedComponets");
      log.error(e);
    }
    log.info("END of the method getAssignedComponets");
    return assignedComponentsMap;
  }

  //========================================================================
}
