/**
 * @author Jp
 *
 */
package com.tracer.service.impl;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tracer.dao.model.UserAuthCodeDetails;
import com.tracer.dao.model.UserDetails;
import com.tracer.dao.persistence.LoginDAO;
import com.tracer.service.LoginManager;
import com.tracer.service.impl.LoginManagerImpl;

public class LoginManagerImpl implements LoginManager {
  protected transient final Log log = LogFactory.getLog(LoginManagerImpl.class);
  protected LoginDAO loginDAO = null;
  
  //========================================================================
  
  public void setLoginDAO(LoginDAO loginDAO) {
    
    try {
      this.loginDAO = loginDAO;
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  //========================================================================
  
  public UserDetails getUserDetails(String userName, String encPassword) throws Exception {
    return loginDAO.getUserDetails(userName, encPassword);
  }

  //========================================================================
  @Override
  public UserAuthCodeDetails getUserAuthDetails(Long userId) throws Exception {
    return loginDAO.getUserAuthDetails(userId);
  }

  //========================================================================
  
  @Override
  public void saveAuthDetails(UserAuthCodeDetails userAuthCodeDetails) throws Exception {
    loginDAO.saveAuthDetails(userAuthCodeDetails);
  }

  //========================================================================
  
  @Override
  public void updateAuthDetails(UserAuthCodeDetails userAuthCodeDetails) throws Exception {
    loginDAO.updateAuthDetails(userAuthCodeDetails);
  }

  //========================================================================
  
  @Override
  public Map<String, Object> getAssignedComponets(Long userId, Long roleId) throws Exception {
    return loginDAO.getAssignedComponets(userId, roleId);
  }

  //========================================================================

}
