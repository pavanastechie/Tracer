/**
 * @author Jp
 *
 */
package com.tracer.webui.presentation.action;

import static com.tracer.common.Constants.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.tracer.dao.model.UserAuthCodeDetails;
import com.tracer.dao.model.UserDetails;
import com.tracer.service.LoginManager;
import com.tracer.util.AuthCodeGenerator;
import com.tracer.util.PasswordEncy;
import com.tracer.util.StringUtil;
import com.tracer.webui.presentation.form.LoginForm;

public class LoginAction extends BaseDispatchAction {
  
  protected transient final Log log = LogFactory.getLog(getClass());
  
  //========================================================================
  
  public ActionForward showLogin(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward showLogin");
    
    try {
      saveToken(request);
    } catch (Exception e) {
      log.error("Problem in the ActionForward showLogin"); 
      log.error(e);
    }
    log.info("END of the ActionForward showLogin");
    return mapping.findForward(LOGIN_PAGE);
  }

  //========================================================================
  
  public ActionForward areValidCredentials(ActionMapping mapping, ActionForm form, 
      HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward areValidCredentials");
    LoginManager loginManager = null;
    LoginForm loginForm = null;
    String loginUserName = null;
    String loginUserPassword = null;
    UserDetails userDetails = null;
    PasswordEncy passwordEncy = null;
    HttpSession session = null;
    String pageToForward = LOGIN_PAGE;
    String accountStatus = null;
    long roleId = 0L;
    ActionMessages actionMessages = new ActionMessages();
    UserAuthCodeDetails userAuthCodeDetails = null;
    Map<String, Object> assignedComponentsMap = null;
    Long assignedCircleId = null;
    Long assignedRegionId = null;
    Long assignedZoneId = null;
    Long assignedHubId = null;
    String result = null;
    
    try {
      if (isTokenValid(request)) {
        loginForm = (LoginForm) form;
        loginUserName = loginForm.getUserName();
        loginUserPassword = loginForm.getPassword();
        
        if(StringUtil.isNotNull(loginUserName) && StringUtil.isNotNull(loginUserPassword)) {
          loginUserName = loginUserName.trim();
          loginUserName = loginUserName.toLowerCase();
          loginUserPassword = loginUserPassword.trim();
          loginManager = (LoginManager) getBean(LOGIN_MANAGER);
          passwordEncy = PasswordEncy.getInstance();
          userDetails = loginManager.getUserDetails(loginUserName, passwordEncy.encrypt(loginUserPassword));
          
          if(userDetails != null) {
            accountStatus = userDetails.getStatus();
            roleId = userDetails.getRoleDetails().getRoleId().longValue();
            
            if(IN_ACTIVE.equals(accountStatus)) {
              pageToForward = ACCOUNT_INACTIVE_PAGE;
            } else if(roleId == RUNNER) {
              pageToForward = ACCESS_DENIED;
            } else {
              roleId = userDetails.getRoleDetails().getRoleId().longValue();
              // Need to check whether the assigned components are active or not.
              assignedComponentsMap = loginManager.getAssignedComponets(userDetails.getUserId(), roleId);
              session = request.getSession(false);
              
              if(assignedComponentsMap != null && !(assignedComponentsMap.isEmpty())) {
                result = (String) assignedComponentsMap.get(RESULT);
                  
                if(assignedComponentsMap.get(ASSIGNED_CIRCLE_ID) != null) {
                  assignedCircleId = (Long) assignedComponentsMap.get(ASSIGNED_CIRCLE_ID);
                  session.setAttribute(ASSIGNED_CIRCLE_ID, assignedCircleId);
                }
                if(assignedComponentsMap.get(ASSIGNED_REGION_ID) != null) {
                  assignedRegionId = (Long) assignedComponentsMap.get(ASSIGNED_REGION_ID);
                  session.setAttribute(ASSIGNED_REGION_ID, assignedRegionId);
                }
                if(assignedComponentsMap.get(ASSIGNED_ZONE_ID) != null) {
                  assignedZoneId = (Long) assignedComponentsMap.get(ASSIGNED_ZONE_ID);
                  session.setAttribute(ASSIGNED_ZONE_ID, assignedZoneId);
                }
                if(assignedComponentsMap.get(ASSIGNED_HUB_ID) != null) {
                  assignedHubId = (Long) assignedComponentsMap.get(ASSIGNED_HUB_ID);
                  session.setAttribute(ASSIGNED_HUB_ID, assignedHubId);
                  session.setAttribute(ASSIGNED_HUB_NAME, (String) assignedComponentsMap.get(ASSIGNED_HUB_NAME));
                }
              }

              if(result == ACTIVE) {
                session.setAttribute(USER_ID, userDetails.getUserId());
                session.setAttribute(USER_NAME, userDetails.getName().concat(" ").concat(userDetails.getLastName()));
                session.setAttribute(ROLE_ID, roleId);
                userAuthCodeDetails = loginManager.getUserAuthDetails(userDetails.getUserId());
              
                if(userAuthCodeDetails != null) {
                  session.setAttribute(AUTH_CODE, userAuthCodeDetails.getAuthCode());
                  userAuthCodeDetails.setLoginTimestamp(new Timestamp(new Date().getTime()));
                  userAuthCodeDetails.setLogoutTimestamp(null);
                  userAuthCodeDetails.setStatus(ACTIVE);
                  loginManager.updateAuthDetails(userAuthCodeDetails);
                } else {
                  UserAuthCodeDetails uacDetails = new UserAuthCodeDetails();
                  uacDetails.setUserDetails(userDetails);
                  uacDetails.setAuthCode(AuthCodeGenerator.getInstance().getGeneratedAuthCode());
                  uacDetails.setLoginTimestamp(new Timestamp(new Date().getTime()));
                  loginManager.saveAuthDetails(uacDetails);
                  session.setAttribute(AUTH_CODE, uacDetails.getAuthCode());
                }
                pageToForward = SHOW_DASHBOARD_METHOD_FORWARD;  
              } else {
                if(roleId == CIRCLE_MANAGER) {
                  actionMessages.add("circle.inactive.access.denied", new ActionMessage("circle.inactive.access.denied"));
                } else if(roleId == REGION_MANAGER) {
                  actionMessages.add("region.inactive.access.denied", new ActionMessage("region.inactive.access.denied"));
                } else if(roleId == ZONE_MANAGER) {
                  actionMessages.add("zone.inactive.access.denied", new ActionMessage("zone.inactive.access.denied"));
                } else if (roleId == HUB_MANAGER || roleId == TEAM_LEADER) {
                  actionMessages.add("hub.inactive.access.denied", new ActionMessage("hub.inactive.access.denied"));
                }
                log.error("Assigned circle / region / zone / hub is in-active");
                saveMessages(request, actionMessages);
                resetToken(request);
                saveToken(request);
              }
            }
          } else {
            actionMessages.add("error.invalid.credentials", new ActionMessage("error.invalid.credentials"));
            log.error("Invalid Credentials");
            saveMessages(request, actionMessages);
            resetToken(request);
            saveToken(request);
          }
        } else {
          log.error("User Name and (or) Password cannot be null");
        }
      } else {
        pageToForward = UNAUTH_INVALID_TOKEN_ERROR_PAGE;
      }
    } catch (Exception e) {
      log.error("Problem in the ActionForward areValidCredentials");
      log.error(e);
      ActionMessages errors = new ActionMessages();
      saveErrors(request,errors);
      throw e;
    }
    log.info("END of the ActionForward areValidCredentials");
    return mapping.findForward(pageToForward);
  }

  //========================================================================
  
  /*public ActionForward saveAttendance(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response)throws Exception {
    log.info("START of the ActionForward saveAttendance");
    HttpSession session = null; 
    Long userId = null;
    UserAuthCodeDetails userAuthCodeDetails = null;
    UserDetails userDetails = new UserDetails();
    LoginManager loginManager = null;
    String authCode = null;
    
    try {
      session = request.getSession(false);
      if(session.getAttribute(USER_ID) != null) {
        loginManager = (LoginManager) getBean(LOGIN_MANAGER);
        userId = (Long) session.getAttribute(USER_ID);
        authCode = AuthCodeGenerator.getInstance().getGeneratedAuthCode();
        userAuthCodeDetails = new UserAuthCodeDetails();
        userDetails.setUserId(userId);
        userAuthCodeDetails.setUserDetails(userDetails);
        userAuthCodeDetails.setAuthCode(authCode);
        userAuthCodeDetails.setLoginTimestamp(new Timestamp(new Date().getTime()));
        loginManager.saveOrUpdateAuthDetails(userAuthCodeDetails);
        session.setAttribute(AUTH_CODE, userAuthCodeDetails.getAuthCode());
      }
    } catch (Exception e) {
      log.error("Problem in the ActionForward saveAttendance");
      log.error(e);
      ActionMessages errors = new ActionMessages();
      saveErrors(request,errors);
      throw e;
    }
    log.info("END of the ActionForward saveAttendance");
    return mapping.findForward(SHOW_DASHBOARD_METHOD_FORWARD);
  }*/

  //========================================================================
  
  public ActionForward logoutUser(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward logoutUser");
    HttpSession session = null; 
    UserAuthCodeDetails userAuthCodeDetails = null;
    LoginManager loginManager = null;
    Long userId = null;
    ActionMessages actionMessages = new ActionMessages();
    
    try {
      session = request.getSession(false);
      if(session.getAttribute(USER_ID) != null) {
        userId = (Long) session.getAttribute(USER_ID);
        loginManager = (LoginManager) getBean(LOGIN_MANAGER);
        userAuthCodeDetails = loginManager.getUserAuthDetails(userId);
        
        if(userAuthCodeDetails != null) {
          userAuthCodeDetails.setLogoutTimestamp(new Timestamp(new Date().getTime()));
          userAuthCodeDetails.setStatus(IN_ACTIVE);
          loginManager.updateAuthDetails(userAuthCodeDetails);    
        }
      }
      session.invalidate();
      actionMessages.add("logout.successful", new ActionMessage("logout.successful"));
      saveMessages(request, actionMessages);
    } catch (Exception e) {
      log.error("Problem in the ActionForward logoutUser");
      log.error(e);
      ActionMessages errors = new ActionMessages();
      saveErrors(request,errors);
      throw e;
    }
    log.info("END of the ActionForward logoutUser");
    return mapping.findForward(SHOW_LOGIN_METHOD_FORWARD);
  }
  
  //========================================================================
  
}