/**
 * @author Jp
 *
 */
package com.tracer.dao.persistence;

import java.util.List;
import java.util.Map;

import com.tracer.dao.model.CircleDetails;
import com.tracer.dao.model.DashboardDetails;
import com.tracer.dao.model.HubDetails;
import com.tracer.dao.model.RegionDetails;
import com.tracer.dao.model.RoleDetails;
import com.tracer.dao.model.UserDetails;
import com.tracer.dao.model.UserPasswordHistoryDetails;
import com.tracer.dao.model.ZoneDetails;

public interface UserDAO {

	 /* This Map contains the all the user related objects, UserDetails, List<UserReportingToDetails>,
	  List<UserToCircleDetails> or List<UserToRegionDetails> or List<UserToZoneDetails> or List<UserToHubDetails>*/
  public Map<String, String> addUser(Map<String, Object> userDetailsMap) throws Exception;
  
  /* This Map contains the all the user related objects, UserDetails, List<UserReportingToDetails>,
  List<UserToCircleDetails> or List<UserToRegionDetails> or List<UserToZoneDetails> or List<UserToHubDetails>*/
  public Map<String, String> updateUser(Map<String, Object> userDetailsMap) throws Exception;
  
  /* The returning Map contains the all the user related objects, UserDetails, UserReportingToDetails,
  UserToCircleDetails or UserToRegionDetails or UserToZoneDetails or UserToHubDetails*/
  public Map<String, Object> getUser(Long userId) throws Exception;
  
  /*This method deletes(soft) the user*/
  public String deleteUser(Long userId) throws Exception;
  
  /*This method activates the user*/
  public String activateUser(Long userId) throws Exception;
  
  /*This method returns the list of userDetails objects, with the basic information of the user that
   needs to display in the list page*/
  public List<UserDetails> getUsers(Long userId, Long roleId) throws Exception;
  
  /*This method checks whether the user already exists or not*/
  public boolean isUserExists(String userName) throws Exception;
  
  /*This method checks whether the user code already exists or not*/
  public boolean isUserCodeExists(String userCode) throws Exception;
  
  /*This method returns the list of hubs assigned to the user */
  public List<HubDetails> getHubs(Long userId, Long roleId, Long zoneId) throws Exception;
  
  /*This method returns the list of Zones assigned to the user */
  public List<ZoneDetails> getZones(Long userId, Long roleId, Long regionId) throws Exception;
  
  /*This method returns the list of Regions assigned to the user */
  public List<RegionDetails> getRegions(Long userId, Long roleId, Long circleId) throws Exception;
  
  /*This method returns the list of roles */
  public List<RoleDetails> getRoles(Long roleId) throws Exception;
  
  /*This method returns the list of team leaders assigned to the user */
  public List<UserDetails> getTeamLeaders(Long userId, Long roleId) throws Exception;
  
  public List<UserDetails> getTeamLeaders(Long hubId) throws Exception;

  /*This method gives only the details of the user */
  public UserDetails getUserDetails(Long userId) throws Exception;
  
  /*This method is used to update the password */
  public String updatePassword(UserDetails userDetails) throws Exception;
  
  /*This method is used to update the profile details */
  public String updateProfile(UserDetails userDetails) throws Exception;
  
  public List<UserDetails> getHubManagers(Long hubId) throws Exception;
  
  public List<UserDetails> getZoneManagers(Long hubId) throws Exception;
  
  public List<UserDetails> getRegionManagers(Long zoneId) throws Exception;
  
  public List<UserDetails> getCircleManagers(Long regionId) throws Exception;
  
  public DashboardDetails getDashboardDetails(Long userId) throws Exception;
  
  public String updateDashboardDetails(DashboardDetails dashboardDetails) throws Exception;
  
  public List<CircleDetails> getCircles() throws Exception;
  
  /*This method checks whether phone number already exists or not*/
  public boolean isPhoneNumberExists(Long userId, int role, String phoneNumber) throws Exception;
  
  public List<CircleDetails> getCircles(Long userId) throws Exception;
  
  /* get Data with UserName */
  public UserDetails getDetailsWithUserName(String userName) throws Exception;
  
  /* pushing password to db for check last five passwords */
  public String storePassword(UserPasswordHistoryDetails userPasswordDetails) throws Exception;
  
  /* get last five Password Query */
  
  public List<UserPasswordHistoryDetails> getFivePassword(UserDetails userDetails) throws Exception;
  
}
