/**
 * @author Jp
 *
 */
package com.tracer.dao.persistence;

import java.util.Map;

import com.tracer.dao.model.UserAuthCodeDetails;
import com.tracer.dao.model.UserDetails;;

public interface LoginDAO {
  public UserDetails getUserDetails(String userName, String encPassword) throws Exception;
  public UserAuthCodeDetails getUserAuthDetails(Long userId) throws Exception;
  public void saveAuthDetails(UserAuthCodeDetails userAuthCodeDetails) throws Exception;
  public void updateAuthDetails(UserAuthCodeDetails userAuthCodeDetails) throws Exception;
  public Map<String, Object> getAssignedComponets(Long userId, Long roleId) throws Exception;
}
