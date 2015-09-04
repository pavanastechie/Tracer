/**
 * @author Jp
 *
 */
package com.tracer.dao.persistence.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;

import com.tracer.dao.model.BeatPlanUserAssignments;
import com.tracer.dao.model.CircleDetails;
import com.tracer.dao.model.DashboardDetails;
import com.tracer.dao.model.HubDetails;
import com.tracer.dao.model.RegionDetails;
import com.tracer.dao.model.RoleDetails;
import com.tracer.dao.model.UserDetails;
import com.tracer.dao.model.UserPasswordHistoryDetails;
import com.tracer.dao.model.UserReportingToDetails;
import com.tracer.dao.model.UserToCircleDetails;
import com.tracer.dao.model.UserToHubDetails;
import com.tracer.dao.model.UserToRegionDetails;
import com.tracer.dao.model.UserToZoneDetails;
import com.tracer.dao.model.ZoneDetails;
import com.tracer.dao.persistence.UserDAO;
import com.tracer.util.StringUtil;

import static com.tracer.common.Constants.*;

public class UserDAOHibernate extends BaseDAOHibernate implements UserDAO {
  protected transient final Log log = LogFactory.getLog(UserDAOHibernate.class);

  //========================================================================
    
  @Override
  public Map<String, String> addUser(Map<String, Object> userDetailsMap) throws Exception {
    log.info("START of the method addUser");
    String result = FAILURE;
    UserDetails userDetails = null;
    int reprotingUsersAssignmentsCount = 0;
    int userToAreaAssignmentsCount = 0;
    String errorMessage = "";
    Map<String, String> resultMap = null;
    
    try {
      resultMap = new HashMap<String, String>();
      
      if(userDetailsMap != null && !(userDetailsMap.isEmpty())) {
        
        if(userDetailsMap.get(USER_DETAILS) != null) {
          //Saving the user
          userDetails = (UserDetails) userDetailsMap.get(USER_DETAILS);
          
          if(StringUtil.isNotNull(userDetails.getUserCode())) {
            
        	if(isUserExists(userDetails.getUserName())) {
        	  errorMessage = "Username already exists.";
        	} else {
    		  getHibernateTemplate().save(userDetails);
            
              // Saving the User Reporting to details
              if(userDetailsMap.get(USER_REPORTING_TO_DETAILS) != null) {
                reprotingUsersAssignmentsCount = saveUserReportingToDetails((UserReportingToDetails) userDetailsMap.get(USER_REPORTING_TO_DETAILS), userDetails);
              }

              // Saving the User to Hubs details
              if(userDetailsMap.get(USER_TO_HUB_DETAILS) != null) {
                userToAreaAssignmentsCount = userToAreaAssignmentsCount + saveOrUpdateUserToHubDetails((UserToHubDetails) userDetailsMap.get(USER_TO_HUB_DETAILS), userDetails);
              }

              // Saving the User to Zones details
              if(userDetailsMap.get(USER_TO_ZONE_DETAILS) != null) {
                userToAreaAssignmentsCount = userToAreaAssignmentsCount + saveOrUpdateUserToZoneDetails((UserToZoneDetails) userDetailsMap.get(USER_TO_ZONE_DETAILS), userDetails);
              }

              // Saving the User to Regions details
              if(userDetailsMap.get(USER_TO_REGION_DETAILS) != null) {
                userToAreaAssignmentsCount = userToAreaAssignmentsCount + saveOrUpdateUserToRegionDetails((UserToRegionDetails) userDetailsMap.get(USER_TO_REGION_DETAILS), userDetails);
              }

              // Saving the User to Circles details
              if(userDetailsMap.get(USER_TO_CIRCLE_DETAILS) != null) {
                userToAreaAssignmentsCount = saveOrUpdateUserToCircleDetails((UserToCircleDetails) userDetailsMap.get(USER_TO_CIRCLE_DETAILS), userDetails);
              }

              if(reprotingUsersAssignmentsCount == 0) {
                errorMessage = "User should be assigned to atleast one Manager";
              }

              if(userToAreaAssignmentsCount == 0) {
                errorMessage = "User should be assigned to a atleast one Hub or Zone or Region or Circle";
              }
        	}
          } else {
            errorMessage = "Unable to add the user as the user code is empty";  
          }

          if(StringUtil.isNotNull(errorMessage)) {
            // Need to roll back the user.

          } else {
            // Need to save the dash board details table with default data.
            DashboardDetails dashboardDetails = new DashboardDetails();
            dashboardDetails.setUserDetails(userDetails);
            Long roleId = userDetails.getRoleDetails().getRoleId();
            dashboardDetails.setStatus(ACTIVE);
            dashboardDetails.setAttendanceReport(IN_ACTIVE);
            dashboardDetails.setNonPerformedRunnersReport(IN_ACTIVE);
            dashboardDetails.setTopDistributorsReport(IN_ACTIVE);
            dashboardDetails.setTopHubsReport(IN_ACTIVE);
            dashboardDetails.setTopRegionsReport(IN_ACTIVE);
            dashboardDetails.setTopRunnersReport(IN_ACTIVE);
            dashboardDetails.setTopZonesReport(IN_ACTIVE);
            dashboardDetails.setVisitsVsBeatPlanReport(IN_ACTIVE);
            
            if(roleId.longValue() == TEAM_LEADER) {
              dashboardDetails.setAttendanceReport(ACTIVE);
              dashboardDetails.setNonPerformedRunnersReport(ACTIVE);
              dashboardDetails.setTopRunnersReport(ACTIVE);
              dashboardDetails.setVisitsVsBeatPlanReport(ACTIVE);
            } else if(roleId.longValue() == CIRCLE_MANAGER || roleId.longValue() == SYSTEM_ADMIN) {
              dashboardDetails.setNonPerformedRunnersReport(ACTIVE);
              dashboardDetails.setTopDistributorsReport(ACTIVE);
              dashboardDetails.setTopRegionsReport(ACTIVE);
              dashboardDetails.setTopZonesReport(ACTIVE);
            } else if(roleId.longValue() == REGION_MANAGER) {
              dashboardDetails.setTopDistributorsReport(ACTIVE);
              dashboardDetails.setTopRegionsReport(ACTIVE);
              dashboardDetails.setTopRunnersReport(ACTIVE);
              dashboardDetails.setTopZonesReport(ACTIVE);
            } else if(roleId.longValue() == ZONE_MANAGER) {
              dashboardDetails.setTopRunnersReport(ACTIVE);
              dashboardDetails.setTopDistributorsReport(ACTIVE);
              dashboardDetails.setTopZonesReport(ACTIVE);
              dashboardDetails.setTopHubsReport(ACTIVE);
            } else if(roleId.longValue() == HUB_MANAGER) {
              dashboardDetails.setVisitsVsBeatPlanReport(ACTIVE);
              dashboardDetails.setNonPerformedRunnersReport(ACTIVE);
              dashboardDetails.setTopDistributorsReport(ACTIVE);
              dashboardDetails.setTopHubsReport(ACTIVE);
            }
            getHibernateTemplate().save(dashboardDetails);
            result = SUCCESS;
          }
        } else {
            result = INVALID_INPUT;
        }
      } else {
        result = INVALID_INPUT;
      }
      resultMap.put(RESULT, result);
      resultMap.put(ERROR_MESSAGE, errorMessage);
    } catch (Exception e) {
      log.error("PROBLEM in the method addUser");
      log.error(e);
      e.printStackTrace();
    }
    log.info("END of the method addUser");
    return resultMap;
  }
  
  //========================================================================
  
  private int saveUserReportingToDetails(UserReportingToDetails userReportingToDetails, UserDetails userDetails) {
    int savedRecordsCount = 0;
    
    try {
      //if(userReportingToDetailsList != null && userReportingToDetailsList.size() > 0) {
        //for(UserReportingToDetails userReportingToDetails : userReportingToDetailsList) {
          if(userReportingToDetails != null) {
            userReportingToDetails.setUserId(userDetails.getUserId());
            getHibernateTemplate().save(userReportingToDetails);
            savedRecordsCount++;
          }
        //}
      //}
    } catch (Exception e) {
      log.error("PROBLEM in the method saveOrUpdateUserReportingToDetails");
      log.error(e);
    }
    return savedRecordsCount;
  }
  
  //========================================================================
  
  @SuppressWarnings("unchecked")
  private int updateUserReportingToDetails(UserReportingToDetails userReportingToDetails, UserDetails userDetails) {
    int updatedRecordsCount = 0;
    boolean foundCombination = false;
    StringBuffer hqlQuery = null;
    List<UserReportingToDetails> userReportingToDetailsList = null;
    
    try {
      hqlQuery = new StringBuffer(" SELECT urtud from UserReportingToDetails urtud WHERE urtud.userId="+userDetails.getUserId());
      userReportingToDetailsList = getHibernateTemplate().find(hqlQuery.toString());
        
      if(userReportingToDetailsList != null && userReportingToDetailsList.size() > 0) {
        for(UserReportingToDetails urtud : userReportingToDetailsList) {
            
          if(urtud.getUserDetails().getUserId().longValue() == userReportingToDetails.getUserDetails().getUserId().longValue()) {
            foundCombination = true;
            urtud.setStatus(ACTIVE);
            getHibernateTemplate().merge(urtud);
          } else {
            urtud.setStatus(IN_ACTIVE);
            getHibernateTemplate().merge(urtud);
          }
        }
      } 
      if(foundCombination == false) {
        userReportingToDetails.setUserId(userDetails.getUserId());
        getHibernateTemplate().save(userReportingToDetails);
      }
      updatedRecordsCount++;
  } catch (Exception e) {
    log.error("PROBLEM in the method updateUserReportingToDetails");
    log.error(e);
  }
    return updatedRecordsCount;
  }
  
  //========================================================================
  
  @SuppressWarnings("unchecked")
  private int saveOrUpdateUserToHubDetails(UserToHubDetails userToHubDetails, UserDetails userDetails) {
    int saveOrUpdatedRecordsCount = 0;
    boolean foundCombination = false;
    StringBuffer hqlQuery = null;
    List<UserToHubDetails> userToHubsList = null;
    
    try {
      hqlQuery = new StringBuffer(" SELECT uthd from UserToHubDetails uthd WHERE uthd.userDetails.userId="+userDetails.getUserId());
      userToHubsList = getHibernateTemplate().find(hqlQuery.toString());
      
      if(userToHubsList != null && userToHubsList.size() > 0) {
        for(UserToHubDetails uthd : userToHubsList) {
          if(uthd != null) {
            if(uthd.getHubDetails().getHubId().longValue() == userToHubDetails.getHubDetails().getHubId().longValue()) {
              foundCombination = true;
              uthd.setStatus(ACTIVE);
              getHibernateTemplate().merge(uthd);
              saveOrUpdatedRecordsCount++;
            } else {
              uthd.setStatus(IN_ACTIVE);
              getHibernateTemplate().merge(uthd);
            }
          }
        }
      }
      
      if(foundCombination == false) {
        userToHubDetails.setUserDetails(userDetails);
        getHibernateTemplate().save(userToHubDetails);
        saveOrUpdatedRecordsCount++;
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method saveOrUpdateUserToHubDetails");
      log.error(e);
    }
    return saveOrUpdatedRecordsCount;
  }
  
  //========================================================================
  
  @SuppressWarnings("unchecked")
  private int saveOrUpdateUserToZoneDetails(UserToZoneDetails userToZoneDetails, UserDetails userDetails) {
    int saveOrUpdatedRecordsCount = 0;
    List<UserToZoneDetails> userToZonesList = null;
    boolean foundCombination = false;
    StringBuffer hqlQuery = null;
    
    try {
      hqlQuery = new StringBuffer(" SELECT utzd from UserToZoneDetails utzd WHERE utzd.userDetails.userId="+userDetails.getUserId());
      userToZonesList = getHibernateTemplate().find(hqlQuery.toString());
    
      if(userToZonesList != null && userToZonesList.size() > 0) {
        for(UserToZoneDetails utzd : userToZonesList) {
          if(utzd.getZoneDetails().getZoneId().longValue() == userToZoneDetails.getZoneDetails().getZoneId().longValue()) {
            foundCombination = true;
            utzd.setStatus(ACTIVE);
            getHibernateTemplate().merge(utzd);
            saveOrUpdatedRecordsCount++;
          } else {
            utzd.setStatus(IN_ACTIVE);
            getHibernateTemplate().merge(utzd);
          }
        }
      }
    
      if(foundCombination == false) {
        userToZoneDetails.setUserDetails(userDetails);
        getHibernateTemplate().save(userToZoneDetails);
        saveOrUpdatedRecordsCount++;
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method saveOrUpdateUserToZoneDetails");
      log.error(e);
    }
    return saveOrUpdatedRecordsCount;
  }
  
  //========================================================================
  
  @SuppressWarnings("unchecked")
  private int saveOrUpdateUserToRegionDetails(UserToRegionDetails userToRegionDetails, UserDetails userDetails) {
    int saveOrUpdatedRecordsCount = 0;
    List<UserToRegionDetails> userToRegionsList = null;
    boolean foundCombination = false;
    StringBuffer hqlQuery = null;
    
    
    try {
      hqlQuery = new StringBuffer(" SELECT utrd from UserToRegionDetails utrd WHERE utrd.userDetails.userId="+userDetails.getUserId());
      userToRegionsList = getHibernateTemplate().find(hqlQuery.toString());
    
      if(userToRegionsList != null && userToRegionsList.size() > 0) {
        for(UserToRegionDetails utrd : userToRegionsList) {
          if(utrd.getRegionDetails().getRegionId().longValue() == userToRegionDetails.getRegionDetails().getRegionId().longValue()) {
            foundCombination = true;
            utrd.setStatus(ACTIVE);
            getHibernateTemplate().merge(utrd);
            saveOrUpdatedRecordsCount++;
          } else {
            utrd.setStatus(IN_ACTIVE);
            getHibernateTemplate().merge(utrd);
          }
        }
      }
    
      if(foundCombination == false) {
        userToRegionDetails.setUserDetails(userDetails);
        getHibernateTemplate().save(userToRegionDetails);
        saveOrUpdatedRecordsCount++;
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method saveOrUpdateUserToRegionDetails");
      log.error(e);
    }
    return saveOrUpdatedRecordsCount;
  }
  
  //========================================================================
  
  @SuppressWarnings("unchecked")
  private int saveOrUpdateUserToCircleDetails(UserToCircleDetails userToCircleDetails, UserDetails userDetails) {
    int saveOrUpdatedRecordsCount = 0;
    List<UserToCircleDetails> userToCirclesList = null;
    boolean foundCombination = false;
    StringBuffer hqlQuery = null;
    
    
    try {
      hqlQuery = new StringBuffer(" SELECT utcd from UserToCircleDetails utcd WHERE utcd.userDetails.userId="+userDetails.getUserId());
      userToCirclesList = getHibernateTemplate().find(hqlQuery.toString());
    
      if(userToCirclesList != null && userToCirclesList.size() > 0) {
        for(UserToCircleDetails utcd : userToCirclesList) {
          if(utcd.getCircleDetails().getCircleId().longValue() == userToCircleDetails.getCircleDetails().getCircleId().longValue()) {
            foundCombination = true;
            utcd.setStatus(ACTIVE);
            getHibernateTemplate().merge(utcd);
            saveOrUpdatedRecordsCount++;
          } else {
            utcd.setStatus(IN_ACTIVE);
            getHibernateTemplate().merge(utcd);
          }
        }
      }
    
      if(foundCombination == false) {
        userToCircleDetails.setUserDetails(userDetails);
        getHibernateTemplate().save(userToCircleDetails);
        saveOrUpdatedRecordsCount++;
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method saveOrUpdateUserToCircleDetails");
      log.error(e);
      e.printStackTrace();
    }
    return saveOrUpdatedRecordsCount;
  }
  
  //========================================================================
  
  @Override
  public Map<String, String> updateUser(Map<String, Object> userDetailsMap) throws Exception {
    log.info("START of the method updateUser");
    String result = FAILURE;
    UserDetails userDetails = null;
    int reprotingUsersAssignmentsCount = 0;
    int userToAreaAssignmentsCount = 0;
    String errorMessage = "";
    Map<String, String> resultMap = null;
    
    try {
      resultMap = new HashMap<String, String>();
      
      if(userDetailsMap != null && !(userDetailsMap.isEmpty())) {
        if(userDetailsMap.get(USER_DETAILS) != null) {
          //Saving the user
          userDetails = (UserDetails) userDetailsMap.get(USER_DETAILS);
          if(StringUtil.isNull(userDetails.getUserCode())) {
            UserDetails existingUserDetails = (UserDetails) getHibernateTemplate().get(UserDetails.class, userDetails.getUserId());
            userDetails.setUserCode(existingUserDetails.getUserCode());
          }
          getHibernateTemplate().merge(userDetails);
          
          // Updating the User Reporting to details
          if(userDetailsMap.get(USER_REPORTING_TO_DETAILS) != null) {
            reprotingUsersAssignmentsCount = updateUserReportingToDetails((UserReportingToDetails) userDetailsMap.get(USER_REPORTING_TO_DETAILS), userDetails);
          }

          // Updating the User to Hubs details
          if(userDetailsMap.get(USER_TO_HUB_DETAILS) != null) {
            userToAreaAssignmentsCount = userToAreaAssignmentsCount + saveOrUpdateUserToHubDetails((UserToHubDetails) userDetailsMap.get(USER_TO_HUB_DETAILS), userDetails);
          }

          // Updating the User to Zones details
          if(userDetailsMap.get(USER_TO_ZONE_DETAILS) != null) {
            userToAreaAssignmentsCount = userToAreaAssignmentsCount + saveOrUpdateUserToZoneDetails((UserToZoneDetails) userDetailsMap.get(USER_TO_ZONE_DETAILS), userDetails);
          }

          // Updating the User to Regions details
          if(userDetailsMap.get(USER_TO_REGION_DETAILS) != null) {
            userToAreaAssignmentsCount = userToAreaAssignmentsCount + saveOrUpdateUserToRegionDetails((UserToRegionDetails) userDetailsMap.get(USER_TO_REGION_DETAILS), userDetails);
          }

          // Updating the User to Circles details
          if(userDetailsMap.get(USER_TO_CIRCLE_DETAILS) != null) {
            userToAreaAssignmentsCount = saveOrUpdateUserToCircleDetails((UserToCircleDetails) userDetailsMap.get(USER_TO_CIRCLE_DETAILS), userDetails);
          }

          if(reprotingUsersAssignmentsCount == 0) {
            errorMessage = "User should be assigned to atleast one Manager";
          }

          if(userToAreaAssignmentsCount == 0) {
            errorMessage = "User should be assigned to a atleast one Hub or Zone or Region or Circle";
          }

          if(StringUtil.isNotNull(errorMessage)) {
            // Need to roll back the user.

          } else {
            result = SUCCESS;
          }
        } else {
            result = INVALID_INPUT;
        }
      } else {
        result = INVALID_INPUT;
      }
      resultMap.put(RESULT, result);
      resultMap.put(ERROR_MESSAGE, errorMessage);
    } catch (Exception e) {
      log.error("PROBLEM in the method updateUser");
      log.error(e);
      e.printStackTrace();
    }
    log.info("END of the method updateUser");
    return resultMap;
  }
  
  //========================================================================
  
  @SuppressWarnings("unchecked")
  @Override
  public Map<String, Object> getUser(Long userId) throws Exception {
    log.info("START of the method getUser");
    UserDetails userDetails = null;
    Map<String, Object> userDetailsMap = null;
    Long roleId = 0L;
    StringBuffer hqlQuery;
    List<UserReportingToDetails> userReportingToDetailsList = null;
    List<UserToHubDetails> userToHubsList = null;
    List<UserToZoneDetails> userToZonesList = null;
    List<UserToRegionDetails> userToRegionsList = null;
    List<UserToCircleDetails> userToCirclesList = null;
     
    try {
      if(userId != null && userId > 0) {
        userDetails = (UserDetails) getHibernateTemplate().get(UserDetails.class, userId);
        
        if(userDetails != null) {
          userDetailsMap = new HashMap<String, Object>();
          userDetailsMap.put(USER_DETAILS, userDetails);
          roleId = userDetails.getRoleDetails().getRoleId();
          
          // Getting the reporting user details
          hqlQuery = new StringBuffer(" SELECT userReportingToDetails from UserReportingToDetails userReportingToDetails WHERE ");
          hqlQuery.append(" userReportingToDetails.userId="+userDetails.getUserId());
          hqlQuery.append(" and userReportingToDetails.status='"+ACTIVE+"'");
          userReportingToDetailsList = getHibernateTemplate().find(hqlQuery.toString());
          userDetailsMap.put(USER_REPORTING_TO_DETAILS, userReportingToDetailsList.get(0));
          
          // Getting the hubs of user
          if(roleId == 7 || roleId == 6 || roleId == 5) {
            hqlQuery = new StringBuffer(" SELECT userToHubDetails from UserToHubDetails userToHubDetails WHERE ");  
            hqlQuery.append(" userToHubDetails.userDetails.userId="+userDetails.getUserId());
            hqlQuery.append(" and userToHubDetails.status='"+ACTIVE+"'");
            userToHubsList = getHibernateTemplate().find(hqlQuery.toString());
            userDetailsMap.put(USER_TO_HUB_DETAILS, userToHubsList.get(0));
          } else if(roleId == 4) {
            hqlQuery = new StringBuffer(" SELECT userToZoneDetails from UserToZoneDetails userToZoneDetails WHERE ");  
            hqlQuery.append(" userToZoneDetails.userDetails.userId="+userDetails.getUserId());
            hqlQuery.append(" and userToZoneDetails.status='"+ACTIVE+"'");
            userToZonesList = getHibernateTemplate().find(hqlQuery.toString());
            userDetailsMap.put(USER_TO_ZONE_DETAILS, userToZonesList.get(0));  
          } else if(roleId == 3) {
            hqlQuery = new StringBuffer(" SELECT userToRegionDetails from UserToRegionDetails userToRegionDetails WHERE ");  
            hqlQuery.append(" userToRegionDetails.userDetails.userId="+userDetails.getUserId());
            hqlQuery.append(" and userToRegionDetails.status='"+ACTIVE+"'");
            userToRegionsList = getHibernateTemplate().find(hqlQuery.toString());
            userDetailsMap.put(USER_TO_REGION_DETAILS, userToRegionsList.get(0)); 
          } else if(roleId == 2) {
            hqlQuery = new StringBuffer(" SELECT userToCircleDetails from UserToCircleDetails userToCircleDetails WHERE ");  
            hqlQuery.append(" userToCircleDetails.userDetails.userId="+userDetails.getUserId());
            hqlQuery.append(" and userToCircleDetails.status='"+ACTIVE+"'");
            userToCirclesList = getHibernateTemplate().find(hqlQuery.toString());
            userDetailsMap.put(USER_TO_CIRCLE_DETAILS, userToCirclesList.get(0));
          } else if(roleId == 1) {
            
          } 
        }
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method getUser");
      log.error(e);
      e.printStackTrace();
    }
    log.info("END of the method getUser");
    return userDetailsMap;
  }
  
  //========================================================================
  
  @SuppressWarnings("unchecked")
  @Override
  public String deleteUser(Long userId) throws Exception {
    log.info("START of the method deleteUser");
    String result = FAILURE;
    UserDetails userDetails = null;
    StringBuffer hqlQuery;
    List<BeatPlanUserAssignments> bpuaList = null;
    
    try {
      if(userId != null && userId > 0) {
        userDetails = (UserDetails) getHibernateTemplate().get(UserDetails.class, userId);
        if(userDetails != null ) {
        
          if(RUNNER == userDetails.getRoleDetails().getRoleId().intValue()) {
            hqlQuery = new StringBuffer("SELECT bpua FROM BeatPlanUserAssignments bpua where bpua.status='" + ACTIVE + "'");
            hqlQuery.append(" AND bpua.userDetails.userId=" + userId);
            bpuaList = getHibernateTemplate().find(hqlQuery.toString());
            
            if (bpuaList != null && bpuaList.size() > 0) {
              result = "RunnerAssignedToBeatPlan";
            } else {
              userDetails.setStatus(IN_ACTIVE);
              getHibernateTemplate().merge(userDetails);
              result = SUCCESS;
            }
          } else {
            userDetails.setStatus(IN_ACTIVE);
            getHibernateTemplate().merge(userDetails);
            result = SUCCESS;
          }
        }
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method deleteUser");
      log.error(e);
      e.printStackTrace();
    }
    log.info("END of the method deleteUser");
    return result;
  }
  
  //========================================================================
  
  @Override
  public String activateUser(Long userId) throws Exception {
    log.info("START of the method activateUser");
    String result = FAILURE;
    UserDetails userDetails = null;
    
    try {
      if(userId != null && userId > 0) {
        userDetails = (UserDetails) getHibernateTemplate().get(UserDetails.class, userId);
        
        if(userDetails != null ) {
          userDetails.setStatus(ACTIVE);
          getHibernateTemplate().merge(userDetails);
          result = SUCCESS;
        }
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method activateUser");
      log.error(e);
      e.printStackTrace();
    }
    log.info("END of the method activateUser");
    return result;
  }
  
  //========================================================================
  
  @Override
  public List<UserDetails> getUsers(Long userId, Long roleId) throws Exception {
    log.info("START of the method getUsers");
    List<UserDetails> users = null;
    StringBuffer nativeQuery;
    Query hibQuery = null;
    @SuppressWarnings("rawtypes")
    List resultList = null;
    StringBuffer userIds = new StringBuffer("");
    
    try {
      if(userId != null && userId.longValue() > 0 && roleId != null && roleId.longValue() > 0) {
        nativeQuery = new StringBuffer("select ud.user_id, ud.gender, ud.last_name, ud.name, rd.role_id, rd.role_name, rd.alias_name, ");
        nativeQuery.append(" ud.office_phone, ud.status, urtd.reporting_to_user_id from user_details as ud, role_details as rd, ");
        nativeQuery.append(" user_reporting_to_details as urtd where ud.user_id !="+userId);
        nativeQuery.append(" and ud.role_id = rd.role_id and urtd.user_id = ud.user_id and urtd.status='"+ACTIVE+"' ");
        //nativeQuery.append(" and ud.status='"+ACTIVE+"'");  
        
        if(roleId.longValue() == HUB_MANAGER) {
          nativeQuery.append(" and ud.role_id > "+roleId); 
          String hubManagerAssignedUserIds = getHubManagerAssignedUserIds(String.valueOf(userId));
          
          if(hubManagerAssignedUserIds != null && hubManagerAssignedUserIds.length() > 0) {
          userIds.append(hubManagerAssignedUserIds);
          }
        } else if(roleId.longValue() == ZONE_MANAGER) {
          nativeQuery.append(" and ud.role_id = "+HUB_MANAGER); 
          String zoneManagerAssignedUserIds = getManagerAssignedUserIds(String.valueOf(userId));
          
          if(zoneManagerAssignedUserIds != null && zoneManagerAssignedUserIds.length() > 0) {
            String hubManagerAssignedUserIds = getHubManagerAssignedUserIds(zoneManagerAssignedUserIds);
              
            if(hubManagerAssignedUserIds != null && hubManagerAssignedUserIds.length() > 0) {
              userIds.append(hubManagerAssignedUserIds);
              userIds.append(",");
              userIds.append(zoneManagerAssignedUserIds);
            } else {
              userIds.append(zoneManagerAssignedUserIds);
            }
          }
        } else if(roleId.longValue() == REGION_MANAGER) {
          nativeQuery.append(" and ud.role_id = "+ZONE_MANAGER); 
          String regionManagerAssignedUserIds = getManagerAssignedUserIds(String.valueOf(userId));

          if(regionManagerAssignedUserIds != null && regionManagerAssignedUserIds.length() > 0) {
            String zoneManagerAssignedUserIds = getManagerAssignedUserIds(regionManagerAssignedUserIds);
            
            if(zoneManagerAssignedUserIds != null && zoneManagerAssignedUserIds.length() > 0) {
              String hubManagerAssignedUserIds = getHubManagerAssignedUserIds(zoneManagerAssignedUserIds);
                
              if(hubManagerAssignedUserIds != null && hubManagerAssignedUserIds.length() > 0) {
                userIds.append(hubManagerAssignedUserIds);
                userIds.append(",");
                userIds.append(zoneManagerAssignedUserIds);
                userIds.append(",");
                userIds.append(regionManagerAssignedUserIds);
              } else {
                userIds.append(zoneManagerAssignedUserIds);
                userIds.append(",");
                userIds.append(regionManagerAssignedUserIds);
              }
            } else {
              userIds.append(regionManagerAssignedUserIds);
            }
          }
        } else if(roleId.longValue() == CIRCLE_MANAGER) {
          nativeQuery.append(" and ud.role_id = "+REGION_MANAGER); 
          String circleManagerAssignedUserIds = getManagerAssignedUserIds(String.valueOf(userId));
          
          if(circleManagerAssignedUserIds != null && circleManagerAssignedUserIds.length() > 0) {
          String regionManagerAssignedUserIds = getManagerAssignedUserIds(circleManagerAssignedUserIds);

            if(regionManagerAssignedUserIds != null && regionManagerAssignedUserIds.length() > 0) {
              String zoneManagerAssignedUserIds = getManagerAssignedUserIds(regionManagerAssignedUserIds);
              
              if(zoneManagerAssignedUserIds != null && zoneManagerAssignedUserIds.length() > 0) {
                String hubManagerAssignedUserIds = getHubManagerAssignedUserIds(zoneManagerAssignedUserIds);
                
                if(hubManagerAssignedUserIds != null && hubManagerAssignedUserIds.length() > 0) {
                  userIds.append(hubManagerAssignedUserIds);
                  userIds.append(",");
                  userIds.append(zoneManagerAssignedUserIds);
                  userIds.append(",");
                  userIds.append(regionManagerAssignedUserIds);
                  userIds.append(",");
                  userIds.append(circleManagerAssignedUserIds);
                } else {
                  userIds.append(zoneManagerAssignedUserIds);
                  userIds.append(",");
                  userIds.append(regionManagerAssignedUserIds);
                  userIds.append(",");
                  userIds.append(circleManagerAssignedUserIds);
                }
              } else {
                userIds.append(regionManagerAssignedUserIds);
                userIds.append(",");
                userIds.append(circleManagerAssignedUserIds);
              }
            } else {
              userIds.append(circleManagerAssignedUserIds);
            } 
          }
        }
        
        if(userIds.length() > 0 || roleId.longValue() == SYSTEM_ADMIN) {
          
        	if(userIds.length() > 0) {
            nativeQuery.append(" and ud.user_id in ("+userIds.toString()+")");  
          }
          nativeQuery.append(" order by ud.role_id");
          hibQuery = getSession().createSQLQuery(nativeQuery.toString());
          resultList = hibQuery.list();
          
          if(resultList != null && resultList.size() > 0) {
            users = new ArrayList<UserDetails>();
            
            for(int i = 0; i < resultList.size(); i++) {
              UserDetails userDetails = new UserDetails();
              RoleDetails roleDetails = new RoleDetails();
              Object[] result = (Object[]) resultList.get(i);
              userDetails.setUserId(Long.parseLong(result[0].toString()));
              userDetails.setGender(result[1].toString());
              userDetails.setLastName(result[2].toString());
              userDetails.setName(result[3].toString());
              roleDetails.setRoleId(Long.parseLong(result[4].toString()));
              roleDetails.setRoleName(result[5].toString());
              roleDetails.setAliasName(result[6].toString());
              userDetails.setRoleDetails(roleDetails);
              
              if(result[7] != null) {
                userDetails.setOfficePhone(result[7].toString());
              } else {
                userDetails.setOfficePhone("");
              }
              userDetails.setStatus(result[8].toString());
              UserDetails reprotingToUserDetails = (UserDetails) getHibernateTemplate().get(UserDetails.class, Long.parseLong(result[9].toString()));
              userDetails.setReportingTo(reprotingToUserDetails.getLastName().concat(" ").concat(reprotingToUserDetails.getName()));
              users.add(userDetails);
            }
          }
        }
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method getUsers");
      log.error(e);
      e.printStackTrace();
    }
    log.info("END of the method getUsers");
    return users;
  }
  
  //========================================================================
  
  private String getHubManagerAssignedUserIds(String userIdsString) {
    StringBuffer nativeQuery;
    Query hibQuery = null;
    @SuppressWarnings("rawtypes")
    List resultList = null;
    StringBuffer hubManagerAssignedUserIds = new StringBuffer("");
    Set<Long> userIds = new HashSet<Long>();
  
    try {
      nativeQuery = new StringBuffer(" select tlurtd.user_id, tlurtd.reporting_to_user_id from user_reporting_to_details as tlurtd  ");
      nativeQuery.append(" where tlurtd.status='"+ACTIVE+"' and tlurtd.reporting_to_user_id in");
      //nativeQuery.append(" where tlurtd.status='"+ACTIVE+"' and tlurtd.user_id in");
      nativeQuery.append(" (select hmurtd.user_id from user_reporting_to_details as hmurtd where ");
      nativeQuery.append(" hmurtd.status='"+ACTIVE+"' and hmurtd.reporting_to_user_id in ("+userIdsString+"))");
      hibQuery = getSession().createSQLQuery(nativeQuery.toString());
      resultList = hibQuery.list();
      
      if(resultList != null && resultList.size() > 0) {
      for(int i = 0; i < resultList.size(); i++) {
        Object[] result = (Object[]) resultList.get(i);
        userIds.add(Long.parseLong(result[0].toString()));
        userIds.add(Long.parseLong(result[1].toString()));
      }
      for (Long uid : userIds) {
        hubManagerAssignedUserIds.append(uid);
        hubManagerAssignedUserIds.append(",");
    }
      }
      
  } catch (Exception e) {
    log.error("PROBLEM in the method getHubManagerAssignedUserIds");
    log.error(e);
    e.printStackTrace();
  }
  return StringUtil.removeTrailingValue(hubManagerAssignedUserIds, ",");
  }
  
  //========================================================================
  
  private String getManagerAssignedUserIds(String userIdsString) {
    StringBuffer nativeQuery;
    Query hibQuery = null;
    @SuppressWarnings("rawtypes")
    List resultList = null;
    StringBuffer managerAssignedUserIds = new StringBuffer("");
    Set<Long> userIds = new HashSet<Long>();
  
    try {
      nativeQuery = new StringBuffer(" select murtd.user_id from user_reporting_to_details as murtd ");
      nativeQuery.append(" where murtd.status='"+ACTIVE+"' and murtd.reporting_to_user_id in ("+userIdsString+")");
      hibQuery = getSession().createSQLQuery(nativeQuery.toString());
      resultList = hibQuery.list();
      
      if(resultList != null && resultList.size() > 0) {
      for(int i = 0; i < resultList.size(); i++) {
        userIds.add(((BigInteger) resultList.get(i)).longValue());
      }
      for (Long uid : userIds) {
        managerAssignedUserIds.append(uid);
        managerAssignedUserIds.append(",");
    }
      }
      
  } catch (Exception e) {
    log.error("PROBLEM in the method getZoneManagerAssignedUserIds");
    log.error(e);
    e.printStackTrace();
  }
  return StringUtil.removeTrailingValue(managerAssignedUserIds, ",");
  }
  //========================================================================
  
  @SuppressWarnings("unchecked")
  @Override
  public boolean isUserExists(String userName) throws Exception {
    log.info("START of the method isUserExists");
    StringBuffer hqlQuery;
    List<UserDetails> resultList = null;
    boolean isUserExists = true;
    
    try {
      if(StringUtil.isNotNull(userName)) {
        userName = userName.toLowerCase();
        hqlQuery = new StringBuffer("SELECT ud FROM UserDetails ud WHERE ud.userName='"+userName+"'");
        resultList = getHibernateTemplate().find(hqlQuery.toString());
        
        if(resultList != null && resultList.size() > 0) {
          isUserExists = true;
        } else {
          isUserExists = false;
        }
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method isUserExists");
      log.error(e);
      e.printStackTrace();
   }
    log.info("END of the method isUserExists");
    return isUserExists;
  }
  
  //========================================================================
  
  @SuppressWarnings("unchecked")
  @Override
  public boolean isPhoneNumberExists(Long userId, int role, String phoneNumber) throws Exception {
    log.info("START of the method isPhoneNumberExists");
    StringBuffer hqlQuery;
    List<UserDetails> resultList = null;
    boolean isPhoneNumberExists = true;
    
    try {
      if(StringUtil.isNotNull(phoneNumber)) {
        hqlQuery = new StringBuffer("SELECT ud FROM UserDetails ud WHERE ");
        
        if(role == TEAM_LEADER || role == RUNNER) {
          hqlQuery.append(" ud.officePhone='"+phoneNumber+"' ");
        } else {
          hqlQuery.append(" ud.primaryContactNo='"+phoneNumber+"' ");
        }
        
        if(userId != null && userId.longValue() > 0) {
          hqlQuery.append(" AND ud.userId !="+userId);  
        }
        resultList = getHibernateTemplate().find(hqlQuery.toString());
        
        if(resultList != null && resultList.size() > 0) {
          isPhoneNumberExists = true;
        } else {
          isPhoneNumberExists = false;
        }
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method isPhoneNumberExists");
      log.error(e);
      e.printStackTrace();
   }
    log.info("END of the method isPhoneNumberExists");
    return isPhoneNumberExists;
  }
  
  //========================================================================
  
  @SuppressWarnings("unchecked")
  @Override
  public boolean isUserCodeExists(String userCode) throws Exception {
    log.info("START of the method isUserExists");
    StringBuffer hqlQuery;
    List<UserDetails> resultList = null;
    boolean isUserCodeExists = true;
    
    try {
      if(StringUtil.isNotNull(userCode)) {
        hqlQuery = new StringBuffer("SELECT ud FROM UserDetails ud WHERE ud.userCode='"+userCode+"'");
        resultList = getHibernateTemplate().find(hqlQuery.toString());
        
        if(resultList != null && resultList.size() > 0) {
          isUserCodeExists = true;
        } else {
          isUserCodeExists = false;
        }
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method isUserExists");
      log.error(e);
      e.printStackTrace();
   }
    log.info("START of the method isUserExists");
    return isUserCodeExists;
  }
  
  //========================================================================
  
  @SuppressWarnings("unchecked")
  @Override
  public List<HubDetails> getHubs(Long userId, Long roleId, Long zoneId) throws Exception {
    log.info("START of the method getHubs");
    List<HubDetails> hubs = null;
    StringBuffer hqlQuery;
    List<UserToHubDetails> resultList = null;
    
    try {
      if(userId != null && userId > 0 && roleId != null && roleId > 0) {
        if(roleId.longValue() == SYSTEM_ADMIN) {
          hqlQuery = new StringBuffer("SELECT hubDetails FROM HubDetails hubDetails WHERE ");
          hqlQuery.append(" hubDetails.status='"+ACTIVE+"'");
          hubs = getHibernateTemplate().find(hqlQuery.toString());
        } else if (roleId.longValue() == CIRCLE_MANAGER || roleId.longValue() == REGION_MANAGER ||roleId.longValue() == ZONE_MANAGER) {
          if(zoneId != null && zoneId.longValue() > 0) {
            hqlQuery = new StringBuffer("SELECT hubDetails FROM HubDetails hubDetails WHERE ");
            hqlQuery.append(" hubDetails.status='"+ACTIVE+"'");
            hqlQuery.append(" AND hubDetails.zoneDetails.zoneId="+zoneId);
            hubs = getHibernateTemplate().find(hqlQuery.toString());  
          }
        } else {
          hqlQuery = new StringBuffer("SELECT userToHubDetails FROM UserToHubDetails userToHubDetails WHERE ");
          hqlQuery.append(" userToHubDetails.status='"+ACTIVE+"' AND ");
          hqlQuery.append(" userToHubDetails.userDetails.userId="+userId);
          resultList = getHibernateTemplate().find(hqlQuery.toString());

          if(resultList != null && resultList.size() > 0) {
            hubs = new ArrayList<>();
            for(UserToHubDetails details : resultList) {
              if(details != null) {
                hubs.add(details.getHubDetails());
              }
            }
          }  
        }
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method getHubs");
      log.error(e);
      e.printStackTrace();
    }
    log.info("END of the method getHubs");
    return hubs;
  }
  
  //========================================================================
  
  @SuppressWarnings("unchecked")
  @Override
  public List<ZoneDetails> getZones(Long userId, Long roleId, Long regionId) throws Exception {
    log.info("START of the method getZones");
    List<ZoneDetails> zones = null;
    StringBuffer hqlQuery;
    List<UserToZoneDetails> resultList = null;
    
    try {
      if(userId != null && userId > 0 && roleId != null && roleId > 0) {
        if(roleId.longValue() == SYSTEM_ADMIN) {
          hqlQuery = new StringBuffer("SELECT zoneDetails FROM ZoneDetails zoneDetails WHERE ");
          hqlQuery.append(" zoneDetails.status='"+ACTIVE+"'");
          zones = getHibernateTemplate().find(hqlQuery.toString());
        } else if(roleId.longValue() == CIRCLE_MANAGER || roleId.longValue() == REGION_MANAGER) {
          if(regionId != null && regionId.longValue() > 0) {
            hqlQuery = new StringBuffer("SELECT zoneDetails FROM ZoneDetails zoneDetails WHERE ");
            hqlQuery.append(" zoneDetails.status='"+ACTIVE+"'");
            hqlQuery.append(" AND zoneDetails.regionDetails.regionId="+regionId);
            zones = getHibernateTemplate().find(hqlQuery.toString());
          }
        } else {
          hqlQuery = new StringBuffer("SELECT userToZoneDetails FROM UserToZoneDetails userToZoneDetails WHERE ");
          hqlQuery.append(" userToZoneDetails.status='"+ACTIVE+"' AND ");
          hqlQuery.append(" userToZoneDetails.userDetails.userId="+userId);
          resultList = getHibernateTemplate().find(hqlQuery.toString());

          if(resultList != null && resultList.size() > 0) {
            zones = new ArrayList<>();
            for(UserToZoneDetails details : resultList) {
              if(details != null) {
                zones.add(details.getZoneDetails());
              }
            }
          }
        }
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method getZones");
      log.error(e);
      e.printStackTrace();
    }
    log.info("END of the method getZones");
    return zones;
  }
  
  //========================================================================
  
  @SuppressWarnings("unchecked")
  @Override
  public List<RegionDetails> getRegions(Long userId, Long roleId, Long circleId) throws Exception {
    log.info("START of the method getRegions");
    List<RegionDetails> regions = null;
    StringBuffer hqlQuery;
    List<UserToRegionDetails> resultList = null;
    
    try {
      if(userId != null && userId > 0 && roleId != null && roleId > 0) {
        if(roleId.longValue() == SYSTEM_ADMIN) {
          hqlQuery = new StringBuffer("SELECT regionDetails FROM RegionDetails regionDetails WHERE ");
          hqlQuery.append(" regionDetails.status='"+ACTIVE+"'");
          regions = getHibernateTemplate().find(hqlQuery.toString());  
        } else if(roleId.longValue() == CIRCLE_MANAGER) {
          if(circleId != null && circleId.longValue() > 0) {
            hqlQuery = new StringBuffer("SELECT regionDetails FROM RegionDetails regionDetails WHERE ");
            hqlQuery.append(" regionDetails.status='"+ACTIVE+"' and regionDetails.circleDetails.circleId="+circleId);
            regions = getHibernateTemplate().find(hqlQuery.toString());   
          }
        } else {
          hqlQuery = new StringBuffer("SELECT userToRegionDetails FROM UserToRegionDetails userToRegionDetails WHERE ");
          hqlQuery.append(" userToRegionDetails.status='"+ACTIVE+"' AND ");
          hqlQuery.append(" userToRegionDetails.userDetails.userId="+userId);
          resultList = getHibernateTemplate().find(hqlQuery.toString());

          if(resultList != null && resultList.size() > 0) {
            regions = new ArrayList<>();
            for(UserToRegionDetails details : resultList) {
              if(details != null) {
                regions.add(details.getRegionDetails());
              }
            }
          }
        }
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method getRegions");
      log.error(e);
      e.printStackTrace();
    }
    log.info("END of the method getRegions");
    return regions;
  }
  
  //========================================================================
  
  @SuppressWarnings("unchecked")
  @Override 
  public List<RoleDetails> getRoles(Long roleId) throws Exception {
    log.info("START of the method getRoles");
    List<RoleDetails> roles = null;
    StringBuffer hqlQuery;
    
    try {
      if(roleId != null && roleId > 0) {
        hqlQuery = new StringBuffer("SELECT roleDetails FROM RoleDetails roleDetails WHERE ");
        hqlQuery.append(" roleDetails.status='"+ACTIVE+"'");
        hqlQuery.append(" AND roleDetails.roleId > "+roleId.longValue());
        roles = getHibernateTemplate().find(hqlQuery.toString());
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method getRoles");
      log.error(e);
      e.printStackTrace();
    }
    log.info("END of the method getRoles");
    return roles;
  }
  
  //========================================================================
  
  @SuppressWarnings("unchecked")
  @Override
  public List<UserDetails> getTeamLeaders(Long userId, Long roleId) throws Exception {
    log.info("START of the method getTeamLeaders");
    List<UserDetails> reportingToUsersList = null;
    StringBuffer hqlQuery;
    List<UserReportingToDetails> resultList = null;
    
    try {
      if(userId != null && userId > 0 && roleId != null && roleId > 0) {
        if(roleId.longValue() == SYSTEM_ADMIN) {
          hqlQuery = new StringBuffer("SELECT userDetails FROM UserDetails userDetails WHERE ");
          hqlQuery.append(" userDetails.status='"+ACTIVE+"' AND ");
          hqlQuery.append(" userDetails.roleDetails.roleId="+TEAM_LEADER);
          reportingToUsersList = getHibernateTemplate().find(hqlQuery.toString());
        } else {
          hqlQuery = new StringBuffer("SELECT userReportingToDetails FROM UserReportingToDetails userReportingToDetails WHERE ");
          hqlQuery.append(" userReportingToDetails.status='"+ACTIVE+"' AND ");
          hqlQuery.append(" userReportingToDetails.userDetails.userId="+userId);
          resultList = getHibernateTemplate().find(hqlQuery.toString());

          if(resultList != null && resultList.size() > 0) {
            reportingToUsersList = new ArrayList<>();
            for(UserReportingToDetails details : resultList) {
              if(details != null) {
                reportingToUsersList.add(details.getUserDetails());
              }
            }
          }
        }
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method getTeamLeaders");
      log.error(e);
      e.printStackTrace();
    }
    log.info("END of the method getTeamLeaders");
    return reportingToUsersList;
  }

  //========================================================================
  
  @Override
  public UserDetails getUserDetails(Long userId) throws Exception {
    log.info("START of the method getUserDetails");
    UserDetails userDetails = null;
    
    try {
      if(userId != null && userId > 0) {
        userDetails = (UserDetails) getHibernateTemplate().get(UserDetails.class, userId);
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method getUserDetails");
      log.error(e);
      e.printStackTrace();
    }
    log.info("START of the method getUserDetails");
    return userDetails;
  }
  
  //========================================================================
  
  @Override
  public String updatePassword(UserDetails userDetails) throws Exception {
    log.info("START of the method updatePassword");
    String result = FAILURE;
    
    try {
      if(userDetails != null) {
        getHibernateTemplate().merge(userDetails);
        result = SUCCESS;
      } else {
          result = INVALID_INPUT;
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method updatePassword");
      log.error(e);
      e.printStackTrace();
    }
    log.info("END of the method updatePassword");
    return result;
  }

  //========================================================================
  
  @Override
  public String updateProfile(UserDetails userDetails) throws Exception {
    log.info("START of the method updateProfile");
    String result = FAILURE;
    
    try {
      if(userDetails != null) {
        getHibernateTemplate().merge(userDetails);
        result = SUCCESS;
      } else {
          result = INVALID_INPUT;
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method updateProfile");
      log.error(e);
      e.printStackTrace();
    }
    log.info("END of the method updateProfile");
    return result;
  }

  //========================================================================
  
  @SuppressWarnings("unchecked")
  @Override
  public List<UserDetails> getHubManagers(Long hubId) throws Exception {
    log.info("START of the method getHubManagers");
    StringBuffer hqlQuery;
    List<UserToHubDetails> uthdsList = null;
    List<UserDetails> hubManagers = null;
    
    try {
      if(hubId != null && hubId.longValue() > 0) {
        hqlQuery = new StringBuffer("SELECT uthd from UserToHubDetails uthd WHERE uthd.hubDetails.hubId="+hubId);
        hqlQuery.append(" and uthd.status='"+ACTIVE+"'");
        hqlQuery.append(" and uthd.userDetails.roleDetails.roleId="+HUB_MANAGER);
        hqlQuery.append(" and uthd.userDetails.status='"+ACTIVE+"'");
        uthdsList = getHibernateTemplate().find(hqlQuery.toString());
        
        if(uthdsList != null && uthdsList.size() > 0) {
         hubManagers = new ArrayList<UserDetails>();
         
         for(UserToHubDetails userToHubDetails : uthdsList) {
           hubManagers.add(userToHubDetails.getUserDetails()); 
         }
        }
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method getHubManagers");
      log.error(e);
      e.printStackTrace();
    }
    log.info("END of the method getHubManagers");
    return hubManagers;
  }
  
  //========================================================================
  
  @SuppressWarnings("unchecked")
  @Override
  public List<UserDetails> getZoneManagers(Long hubId) throws Exception {
    log.info("START of the method getZoneManagers");
    StringBuffer hqlQuery;
    List<UserToZoneDetails> utzdsList = null;
    List<UserDetails> zoneManagers = null;
    HubDetails hubDetails = null;
    
    try {
      if(hubId != null && hubId.longValue() > 0) {
        hubDetails = (HubDetails) getHibernateTemplate().get(HubDetails.class, hubId.longValue());
        hqlQuery = new StringBuffer("SELECT utzd from UserToZoneDetails utzd WHERE utzd.zoneDetails.zoneId="+hubDetails.getZoneDetails().getZoneId());
        hqlQuery.append(" and utzd.status='"+ACTIVE+"'");
        hqlQuery.append(" and utzd.userDetails.roleDetails.roleId="+ZONE_MANAGER);
        hqlQuery.append(" and utzd.userDetails.status='"+ACTIVE+"'");
        utzdsList = getHibernateTemplate().find(hqlQuery.toString());
        
        if(utzdsList != null && utzdsList.size() > 0) {
            zoneManagers = new ArrayList<UserDetails>();
         
         for(UserToZoneDetails userToZoneDetails : utzdsList) {
             zoneManagers.add(userToZoneDetails.getUserDetails()); 
         }
        }
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method getZoneManagers");
      log.error(e);
      e.printStackTrace();
    }
    log.info("END of the method getZoneManagers");
    return zoneManagers;
  }
  
  //========================================================================
  
  @SuppressWarnings("unchecked")
  @Override
  public List<UserDetails> getRegionManagers(Long zoneId) throws Exception {
    log.info("START of the method getRegionManagers");
    StringBuffer hqlQuery;
    List<UserToRegionDetails> utrdsList = null;
    List<UserDetails> regionManagers = null;
    ZoneDetails zoneDetails = null;
    
    try {
      if(zoneId != null && zoneId.longValue() > 0) {
        zoneDetails = (ZoneDetails) getHibernateTemplate().get(ZoneDetails.class, zoneId.longValue());  
        hqlQuery = new StringBuffer("SELECT utrd from UserToRegionDetails utrd WHERE utrd.regionDetails.regionId="+zoneDetails.getRegionDetails().getRegionId());
        hqlQuery.append(" and utrd.status='"+ACTIVE+"'");
        hqlQuery.append(" and utrd.userDetails.roleDetails.roleId="+REGION_MANAGER);
        hqlQuery.append(" and utrd.userDetails.status='"+ACTIVE+"'");
        utrdsList = getHibernateTemplate().find(hqlQuery.toString());
        
        if(utrdsList != null && utrdsList.size() > 0) {
          regionManagers = new ArrayList<UserDetails>();
         
         for(UserToRegionDetails userToRegionDetails : utrdsList) {
           regionManagers.add(userToRegionDetails.getUserDetails()); 
         }
        }
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method getRegionManagers");
      log.error(e);
      e.printStackTrace();
    }
    log.info("END of the method getRegionManagers");
    return regionManagers;
  }
  
  //========================================================================
  
  @SuppressWarnings("unchecked")
  @Override
  public List<UserDetails> getCircleManagers(Long regionId) throws Exception {
    log.info("START of the method getCircleManagers");
    StringBuffer hqlQuery;
    List<UserToCircleDetails> utcdsList = null;
    List<UserDetails> circleManagers = null;
    RegionDetails regionDetails = null;
    
    try {
      if(regionId != null && regionId.longValue() > 0) {
        regionDetails = (RegionDetails) getHibernateTemplate().get(RegionDetails.class, regionId.longValue());  
        hqlQuery = new StringBuffer("SELECT utcd from UserToCircleDetails utcd WHERE utcd.circleDetails.circleId="+regionDetails.getCircleDetails().getCircleId());
        hqlQuery.append(" and utcd.status='"+ACTIVE+"'");
        hqlQuery.append(" and utcd.userDetails.roleDetails.roleId="+CIRCLE_MANAGER);
        hqlQuery.append(" and utcd.userDetails.status='"+ACTIVE+"'");
        utcdsList = getHibernateTemplate().find(hqlQuery.toString());
        
        if(utcdsList != null && utcdsList.size() > 0) {
          circleManagers = new ArrayList<UserDetails>();
         
         for(UserToCircleDetails userToCircleDetails : utcdsList) {
           circleManagers.add(userToCircleDetails.getUserDetails()); 
         }
        }
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method getCircleManagers");
      log.error(e);
      e.printStackTrace();
    }
    log.info("END of the method getCircleManagers");
    return circleManagers;
  }

  //========================================================================
  
  @SuppressWarnings("unchecked")
  @Override
  public List<UserDetails> getTeamLeaders(Long hubId) throws Exception {
    log.info("START of the method getTeamLeaders");
    StringBuffer hqlQuery;
    List<UserToHubDetails> uthdsList = null;
    List<UserDetails> hubManagers = null;
    
    try {
      if(hubId != null && hubId.longValue() > 0) {
        hqlQuery = new StringBuffer("SELECT uthd from UserToHubDetails uthd WHERE uthd.hubDetails.hubId="+hubId);
        hqlQuery.append(" and uthd.status='"+ACTIVE+"'");
        hqlQuery.append(" and uthd.userDetails.roleDetails.roleId="+TEAM_LEADER);
        hqlQuery.append(" and uthd.userDetails.status='"+ACTIVE+"'");
        uthdsList = getHibernateTemplate().find(hqlQuery.toString());
        
        if(uthdsList != null && uthdsList.size() > 0) {
         hubManagers = new ArrayList<UserDetails>();
         
         for(UserToHubDetails userToHubDetails : uthdsList) {
           hubManagers.add(userToHubDetails.getUserDetails()); 
         }
        }
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method getTeamLeaders");
      log.error(e);
      e.printStackTrace();
    }
    log.info("END of the method getTeamLeaders");
    return hubManagers;
  }

  //========================================================================
  
  @SuppressWarnings("unchecked")
  @Override
  public DashboardDetails getDashboardDetails(Long userId) throws Exception {
    log.info("START of the method getDashboardDetails");
    DashboardDetails dashboardDetails = null;
    StringBuffer hqlQuery = null;
    List<DashboardDetails> resultList = null;
    
    try {
      if(userId != null && userId.longValue() > 0) {
        hqlQuery = new StringBuffer(" SELECT dd FROM DashboardDetails as dd where dd.status='"+ACTIVE+"' AND ");
        hqlQuery.append(" dd.userDetails.userId="+userId);
        resultList = getHibernateTemplate().find(hqlQuery.toString());
        
        if(resultList != null && resultList.size() > 0) {
          dashboardDetails = resultList.get(0);
        }
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method getDashboardDetails");
      log.error(e);
      e.printStackTrace();
    }
    log.info("END of the method getDashboardDetails");
    return dashboardDetails;
  }

  //========================================================================
  
  @Override
  public String updateDashboardDetails(DashboardDetails dashboardDetails) throws Exception {
    log.info("START of the method updateDashboardDetails");
    String result = FAILURE;
    
    try {
      if(dashboardDetails != null) {
        getHibernateTemplate().merge(dashboardDetails);
        result = SUCCESS;
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method updateDashboardDetails");
      log.error(e);
      e.printStackTrace();
    }
    log.info("END of the method updateDashboardDetails");
    return result;
  }

  //========================================================================
  
  @SuppressWarnings("unchecked")
  @Override
  public List<CircleDetails> getCircles() throws Exception {
    log.info("START of the method getCircles");
    List<CircleDetails> circles = null;
    StringBuffer hqlQuery;
    
    try {
      hqlQuery = new StringBuffer("SELECT circleDetails FROM CircleDetails circleDetails WHERE circleDetails.status='"+ACTIVE+"' ");
      circles = getHibernateTemplate().find(hqlQuery.toString());
    } catch (Exception e) {
      log.error("PROBLEM in the method getCircles");
      log.error(e);
      e.printStackTrace();
    }
    
    log.info("END of the method getCircles");
    return circles;
  }

  //========================================================================
  
  @SuppressWarnings("unchecked")
  @Override
  public List<CircleDetails> getCircles(Long userId) throws Exception {
    log.info("START of the method getCircles");
    StringBuffer hqlQuery;
    List<UserToCircleDetails> utcList = null;
    List<CircleDetails> circles = null;
    
    try {
      if(userId != null && userId.longValue() > 0) {
        hqlQuery = new StringBuffer("SELECT utc FROM UserToCircleDetails utc WHERE utc.status='"+ACTIVE+"' AND utc.userDetails.userId="+userId);
        utcList = getHibernateTemplate().find(hqlQuery.toString());	
        
        if(utcList != null && utcList.size() > 0) {
          circles = new ArrayList<CircleDetails>();
          
          for(UserToCircleDetails utc : utcList) {
            circles.add(utc.getCircleDetails());  
          }
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
  //========================================================================
  @SuppressWarnings("unchecked")
	@Override
	public UserDetails getDetailsWithUserName(String userName) throws Exception {
	  log.info("START of the method getDetailsWithUserName");
	    StringBuffer hqlQuery;
	    UserDetails userDetails = null;
	    List<UserDetails> resultList = null;	    
	    try {
	      if(StringUtil.isNotNull(userName)) {
	        userName = userName.toLowerCase();
	        hqlQuery = new StringBuffer("SELECT ud FROM UserDetails ud WHERE ud.userName='"+userName+"'");
	        resultList =  getHibernateTemplate().find(hqlQuery.toString());
	        if(resultList.size() >0){
	        userDetails =  resultList.get(0);
	        }
	        else{
	        	 userDetails =  null;
	        }
	      }
	    } catch (Exception e) {
	      log.error("PROBLEM in the method getDetailsWithUserName");
	      log.error(e);
	      e.printStackTrace();
	   }
	    log.info("END of the method getDetailsWithUserName");
	    return userDetails;
	}

	// ========================================================================
	@Override
	public String storePassword(UserPasswordHistoryDetails userPasswordDetails)
			throws Exception {
		String successMess = "failed";
		try {
			
		      //if(userReportingToDetailsList != null && userReportingToDetailsList.size() > 0) {
		        //for(UserReportingToDetails userReportingToDetails : userReportingToDetailsList) {
		          if(userPasswordDetails != null) {
		            getHibernateTemplate().save(userPasswordDetails);
		            successMess = "success";
		          }
		        //}
		      //}
		    } catch (Exception e) {
		      log.error("PROBLEM in the method storePassword");
		      log.error(e);
		    }
		    return successMess;
	}

	// ========================================================================
	@SuppressWarnings("unchecked")
	@Override
	public List<UserPasswordHistoryDetails> getFivePassword(UserDetails userDetails)
			throws Exception {
		 StringBuffer hqlQuery;
		// Long userId =userPasswordHistoryDetails.setUserId();
		 List<UserPasswordHistoryDetails> updList = null;
		 hqlQuery = new StringBuffer("SELECT upd FROM UserPasswordHistoryDetails upd WHERE upd.userDetails.userId='"+userDetails.getUserId()+"'ORDER BY upd.userPasswordHistoryDetailsId DESC");
	        updList = getHibernateTemplate().find(hqlQuery.toString());	
	        
	       
	        
		return updList;
	}

 
}