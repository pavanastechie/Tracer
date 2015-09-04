/**
 * @author Jp
 *
 */
package com.tracer.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tracer.dao.model.CircleDetails;
import com.tracer.dao.model.DashboardDetails;
import com.tracer.dao.model.HubDetails;
import com.tracer.dao.model.RegionDetails;
import com.tracer.dao.model.RoleDetails;
import com.tracer.dao.model.UserDetails;
import com.tracer.dao.model.UserPasswordHistoryDetails;
import com.tracer.dao.model.ZoneDetails;
import com.tracer.dao.persistence.UserDAO;
import com.tracer.service.UserManager;

public class UserManagerImpl implements UserManager {
  protected transient final Log log = LogFactory.getLog(UserManagerImpl.class);
  protected UserDAO userDAO = null;
  
  //========================================================================
  
  public void setUserDAO(UserDAO userDAO) {
    
    try {
      this.userDAO = userDAO;
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  //========================================================================
  
  @Override
  public Map<String, String> addUser(Map<String, Object> userDetailsMap) throws Exception {
    return userDAO.addUser(userDetailsMap);
  }
  
  //========================================================================
  
  @Override
  public Map<String, String> updateUser(Map<String, Object> userDetailsMap) throws Exception {
    return userDAO.updateUser(userDetailsMap);
  }
  
  //========================================================================
  
  @Override
  public Map<String, Object> getUser(Long userId) throws Exception {
    return userDAO.getUser(userId);
  }
  
  //========================================================================
  
  @Override
  public String deleteUser(Long userId) throws Exception {
    return userDAO.deleteUser(userId);
  }
  
  //========================================================================
  
  @Override
  public String activateUser(Long userId) throws Exception {
    return userDAO.activateUser(userId);
  }
  
  //========================================================================
  
  @Override
  public List<UserDetails> getUsers(Long userId, Long roleId) throws Exception {
    return userDAO.getUsers(userId, roleId);
  }
  
  //========================================================================
  
  @Override
  public boolean isUserExists(String userName) throws Exception {
    return userDAO.isUserExists(userName);
  }
  
  //========================================================================
  
  @Override
  public boolean isUserCodeExists(String userCode) throws Exception {
    return userDAO.isUserCodeExists(userCode);
  }

  //========================================================================
  
  @Override
  public List<HubDetails> getHubs(Long userId, Long roleId, Long zoneId) throws Exception {
    return userDAO.getHubs(userId, roleId, zoneId);
  }

  //========================================================================
  
  @Override
  public List<ZoneDetails> getZones(Long userId, Long roleId, Long regionId) throws Exception {
    return userDAO.getZones(userId, roleId, regionId);
  }

  //========================================================================
  
  @Override
  public List<RegionDetails> getRegions(Long userId, Long roleId, Long circleId) throws Exception {
    return userDAO.getRegions(userId, roleId, circleId);
  }

  //========================================================================
  
  @Override
  public List<RoleDetails> getRoles(Long roleId) throws Exception {
    return userDAO.getRoles(roleId);
  }

  //========================================================================
  
  @Override
  public List<UserDetails> getTeamLeaders(Long userId, Long roleId) throws Exception {
    return userDAO.getTeamLeaders(userId, roleId);
  }

  //========================================================================
  @Override
  public UserDetails getUserDetails(Long userId) throws Exception {
    return userDAO.getUserDetails(userId);
}

 //========================================================================
  @Override
  public String updatePassword(UserDetails userDetails) throws Exception {
    return userDAO.updatePassword(userDetails);
  }

  //========================================================================
  
  @Override
  public String updateProfile(UserDetails userDetails) throws Exception {
  return userDAO.updateProfile(userDetails);
  }

  //========================================================================
  
  @Override
  public List<UserDetails> getHubManagers(Long hubId) throws Exception {
    return userDAO.getHubManagers(hubId);
  }

  //========================================================================
  
  @Override
  public List<UserDetails> getZoneManagers(Long hubId) throws Exception {
    return userDAO.getZoneManagers(hubId);
  }
  
  //========================================================================
  
  @Override
  public List<UserDetails> getRegionManagers(Long zoneId) throws Exception {
    return userDAO.getRegionManagers(zoneId);
  }
  
  //========================================================================
  
  @Override
  public List<UserDetails> getCircleManagers(Long regionId) throws Exception {
    return userDAO.getCircleManagers(regionId);
  }

  //========================================================================
  
  @Override
  public List<UserDetails> getTeamLeaders(Long hubId) throws Exception {
  return userDAO.getTeamLeaders(hubId);
  }

  //========================================================================
  
  @Override
  public DashboardDetails getDashboardDetails(Long userId) throws Exception {
    return userDAO.getDashboardDetails(userId);
  }
  
  //========================================================================
  
  @Override
  public String updateDashboardDetails(DashboardDetails dashboardDetails) throws Exception {
    return userDAO.updateDashboardDetails(dashboardDetails);
  }

  //========================================================================
  
  @Override
  public List<CircleDetails> getCircles() throws Exception {
  return userDAO.getCircles();
  }

  //========================================================================
  
  @Override
  public boolean isPhoneNumberExists(Long userId, int role, String phoneNumber) throws Exception {
    return userDAO.isPhoneNumberExists(userId, role, phoneNumber);
  }

  //========================================================================
  
  @Override
  public List<CircleDetails> getCircles(Long userId) throws Exception {
    return userDAO.getCircles(userId);
  }
  //========================================================================
	@Override
	public UserDetails getDetailsWithUserName(String userName) throws Exception {
		return userDAO.getDetailsWithUserName(userName);
	}

	//========================================================================
	@Override
	public String storePassword(UserPasswordHistoryDetails userPasswordDetails)
			throws Exception {
		return userDAO.storePassword(userPasswordDetails);
	}

	//========================================================================
	@Override
	public List<UserPasswordHistoryDetails> getFivePassword(UserDetails userDetails) throws Exception {
		
		return userDAO.getFivePassword(userDetails);
	}



}
