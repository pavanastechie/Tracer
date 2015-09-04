/**
 * @author Jp
 *
 */
package com.tracer.webui.presentation.action;

import static com.tracer.common.Constants.*;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;
import org.json.JSONArray;
import org.json.JSONObject;

import com.tracer.dao.model.CircleDetails;
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
import com.tracer.service.UserManager;
import com.tracer.util.PasswordEncy;
import com.tracer.util.PasswordGenerator;
import com.tracer.util.SMSManager;
import com.tracer.util.StringUtil;
import com.tracer.util.UniqueCodeGenerator;
import com.tracer.webui.presentation.form.UserForm;

public class UserAction extends BaseDispatchAction {

  protected transient final Log log = LogFactory.getLog(getClass());

  // ========================================================================

  public ActionForward showAddRunnerPage(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward showAddRunnerPage");
    UserManager userManager = null;
    UserForm userForm = null;
    HttpSession session = null;
    List<HubDetails> hubDetailsList = null;
    Long loggedInUserId = 0L;
    String pageToForward = RUNNER_ADD_PAGE;
    
    try {
      session = request.getSession(false);
      
      if(session != null && session.getAttribute(USER_ID) != null && ((Long)session.getAttribute(USER_ID)).longValue() > 0 ) {
        userManager = (UserManager) getBean(USER_MANAGER);
        hubDetailsList = new ArrayList<HubDetails>();
        userForm = (UserForm) form;
        BeanUtils.copyProperties(userForm, new UserForm()); // Resetting the form
        loggedInUserId = (Long) session.getAttribute(USER_ID);
        userForm.setGender("M");
        // Getting hub details that are assigned to logged in user, and
        // displaying these details into Hub drop down while adding Runner
        hubDetailsList = userManager.getHubs(loggedInUserId, (Long) session.getAttribute(ROLE_ID), (Long) session.getAttribute(ASSIGNED_ZONE_ID));
        
        // by setting hub details list to the user form, will populate these details into Hub drop down
        if (hubDetailsList != null && hubDetailsList.size() > 0) {
          userForm.setHubDetailsList(hubDetailsList);
          saveToken(request);
        } else {
          // Need to show an information to the user that there are no hubs
          pageToForward = INFO_OR_ERROR_PAGE;
          ActionMessages actionMessages = new ActionMessages();
          actionMessages.add("info.hubs.not.available", new ActionMessage("info.hubs.not.available"));
          saveMessages(request, actionMessages);
        }
      } else {
        pageToForward = SESSION_INACTIVE_PAGE;
      }
    } catch (Exception e) {
      log.error("Problem in the ActionForward showAddRunnerPage");
      log.error(e);
      ActionMessages errors = new ActionMessages();
      saveErrors(request,errors);
      throw e;
    }
    log.info("END of the ActionForward showAddRunnerPage");
    return mapping.findForward(pageToForward);
  }

  // ========================================================================

  public ActionForward addRunner(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward addRunner");
    UserForm userForm = null;
    UserDetails userDetails = null;
    UserReportingToDetails userReportingToDetails = null;
    UserToHubDetails userToHubDetails = null;
    HubDetails hubDetails = null;
    SimpleDateFormat formatter = null;
    RoleDetails roleDetails = null;
    UserManager userManager = null;
    Map<String, Object> userDetailsMap = null;
    HttpSession session = null;
    Map<String, String> resultMap = null;
    String forwardPage = MANAGE_USERS_PAGE;
    FormFile formFile = null;

    try {
      session = request.getSession(false);
      
      if(session != null && session.getAttribute(USER_ID) != null && ((Long)session.getAttribute(USER_ID)).longValue() > 0 ) {
        if (isTokenValid(request)) {
          formatter = new SimpleDateFormat(DATE_FORMAT);
          userForm = (UserForm) form;
          userManager = (UserManager) getBean(USER_MANAGER);
        
          // initializing user details obj and setting values
          userDetails = new UserDetails();

          // authentication details
          userDetails.setUserName(userForm.getUserName().toLowerCase());
          PasswordEncy passwordEncy = PasswordEncy.getInstance();
          userDetails.setPassword(passwordEncy.encrypt(userForm.getPassword())); 
          String userCode = checkAndGetUniqueUserCode(userManager, RUNNER, UniqueCodeGenerator.getInstance().getRunnerCode());
      
          if (userCode != null) {
            userDetails.setUserCode(userCode);
          } else {
            log.error("Generated Runner code is null");
          }

          // personal details
          userDetails.setName(userForm.getName());
          userDetails.setLastName(userForm.getLastName());
          userDetails.setGender(userForm.getGender());
          userDetails.setDateOfBirth(formatter.parse(userForm.getDateOfBirth()));
          userDetails.setBloodGroup(userForm.getBloodGroup());
          
          formFile = userForm.getFile();
          
          if(StringUtil.isNotNull(formFile.getFileName())) {
            String fileName = userForm.getUserName().concat("_").concat(System.currentTimeMillis() + "").concat(formFile.getFileName().substring(formFile.getFileName().lastIndexOf('.')));
            saveUploadedImage(formFile, fileName);
            userDetails.setPhotoPath(fileName);
          } else {
            userDetails.setPhotoPath(userForm.getPhotoPath());
          }
          userDetails.setMaritalStatus(userForm.getMaritalStatus());
          
          if (userForm.getDateOfAnniversary() != null && !userForm.getDateOfAnniversary().equals("")) {
            userDetails.setDateOfAnniversary(formatter.parse(userForm.getDateOfAnniversary()));
          }
          
          // address details
          userDetails.setAddress(userForm.getAddress());
          userDetails.setCity(userForm.getCity());
          userDetails.setDistrict(userForm.getDistrict());
          userDetails.setState(userForm.getState());
          userDetails.setCountry(userForm.getCountry());
          userDetails.setPinCode(userForm.getPinCode());
          
          // primary contact details
          userDetails.setPrimaryContactNo(userForm.getPrimaryContactNo());
          userDetails.setPrimaryEmail(userForm.getPrimaryEmail());
          
          // secondary contact details
          userDetails.setSecondaryContactNo(userForm.getSecondaryContactNo());
          userDetails.setSecondaryEmail(userForm.getSecondaryEmail());
          
          // other details
          userDetails.setNearestPoliceStation(userForm.getNearestPoliceStation());
          userDetails.setOfficePhone(userForm.getOfficePhone());
          userDetails.setImeiNo(userForm.getImeiNo());
          userDetails.setStatus(userForm.getStatus());
          userDetails.setDateTime(new Date());

          // role details
          roleDetails = new RoleDetails();
          roleDetails.setStatus(ACTIVE);
          roleDetails.setRoleId(Long.valueOf(RUNNER));
          userDetails.setRoleDetails(roleDetails);

          userReportingToDetails = new UserReportingToDetails();
          userReportingToDetails.setUserDetails(userDetails);
          userReportingToDetails.setStatus(ACTIVE);
          UserDetails reportingToUserDetails = new UserDetails();
          reportingToUserDetails.setUserId(userForm.getReportingToUserID());
          userReportingToDetails.setUserDetails(reportingToUserDetails);

          userToHubDetails = new UserToHubDetails();
          userToHubDetails.setStatus(ACTIVE);
          userToHubDetails.setUserDetails(userDetails);
          
          // hub details
          hubDetails = new HubDetails();
          hubDetails.setHubId(userForm.getHubId());
          hubDetails.setStatus(ACTIVE);
          userToHubDetails.setHubDetails(hubDetails);

          userDetailsMap = new HashMap<String, Object>();
          userDetailsMap.put(USER_DETAILS, userDetails);
          userDetailsMap.put(USER_REPORTING_TO_DETAILS, userReportingToDetails);
          userDetailsMap.put(USER_TO_HUB_DETAILS, userToHubDetails);
          
          if(StringUtil.isNull(userDetails.getUserCode())) {
            for(int i = 0; i < 3; i++){
              userCode = checkAndGetUniqueUserCode(userManager, RUNNER, UniqueCodeGenerator.getInstance().getRunnerCode());
              
              if(StringUtil.isNotNull(userCode)){
                userDetails.setUserCode(userCode);
                break;
              }
            }
          }
              
          if(StringUtil.isNotNull(userDetails.getUserCode())) {
            resultMap = userManager.addUser(userDetailsMap);
                
            if (resultMap.get(RESULT) != null && resultMap.get(RESULT).equals(SUCCESS)) {
            	log.info(userDetails.getUserId());
            	UserPasswordHistoryDetails userPass = new UserPasswordHistoryDetails();
            	userPass.setUserDetails(userDetails);
            	userPass.setPassword(userDetails.getPassword());
            	String resultIs = userManager.storePassword(userPass);
            	log.info("is Password is saved"+resultIs);
              log.info("Add user success");
              request.setAttribute(SUCCESS_MESSAGE, ADD_USER_SUCCESS_MESSAGE);
              String smsMessage = "Credentials to access TraceR app are, ";
              smsMessage = smsMessage.concat("Username: "+userDetails.getOfficePhone().trim());
              smsMessage = smsMessage.concat(" and Password: "+PasswordEncy.getInstance().decrypt(userDetails.getPassword()));
              SMSManager.getInstance().sendSMS(userDetails.getOfficePhone(), smsMessage);
            } else if (resultMap.get(ERROR_MESSAGE) != null) {
              request.setAttribute(ERROR_MESSAGE, resultMap.get(ERROR_MESSAGE));
            }
          } else {
            request.setAttribute(ERROR_MESSAGE, "Unable to add Runner at this time. Please try again later");	
          }
          resetToken(request);
          saveToken(request);
        } else {
          forwardPage = INVALID_TOKEN_ERROR_PAGE;
        }
      } else {
        return mapping.findForward(SESSION_INACTIVE_PAGE);  
      }
    } catch (Exception e) {
      log.error("Problem in the ActionForward addRunner");
      log.error(e);
      ActionMessages errors = new ActionMessages();
      saveErrors(request,errors);
      throw e;
    }
    log.info("END of the ActionForward addRunner");
    return mapping.findForward(forwardPage);
  }

  // ========================================================================

  public ActionForward showAddTeamLeaderPage(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward showAddTeamLeaderPage");
    UserManager userManager = null;
    UserForm userForm = null;
    HttpSession session = null;
    List<HubDetails> hubDetailsList = null;
    Long loggedInUserId = 0L;
    
    try {
      session = request.getSession(false);
      
      if(session != null && session.getAttribute(USER_ID) != null && ((Long)session.getAttribute(USER_ID)).longValue() > 0 ) {
        userManager = (UserManager) getBean(USER_MANAGER);
        hubDetailsList = new ArrayList<HubDetails>();
        userForm = (UserForm) form;
        BeanUtils.copyProperties(userForm, new UserForm()); // Resetting the form
        userForm.setGender("M");
        loggedInUserId = (Long) session.getAttribute(USER_ID);
      
        if (loggedInUserId != 0) {
          // Getting hub details that are assigned to logged in user, and
          // displaying these details into Hub drop down while adding Team leader
          hubDetailsList = userManager.getHubs(loggedInUserId, (Long) session.getAttribute(ROLE_ID), (Long) session.getAttribute(ASSIGNED_ZONE_ID));

          // by setting hub details list to the user form, will populate these details into Hub drop down
          if (hubDetailsList != null) {
            userForm.setHubDetailsList(hubDetailsList);
          }
        } else {
          log.error("showAddTeamLeaderPage: Logged in user id is 0 ");
        }
        saveToken(request);
      } else {
        return mapping.findForward(SESSION_INACTIVE_PAGE);  
      }
    } catch (Exception e) {
      log.error("Problem in the ActionForward showAddTeamLeaderPage");
      log.error(e);
      ActionMessages errors = new ActionMessages();
      saveErrors(request,errors);
      throw e;
    }
    log.info("END of the ActionForward showAddTeamLeaderPage");
    return mapping.findForward(TEAM_LEADER_ADD_PAGE);
  }

  // =========================================================================

  public ActionForward addTeamLeader(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward addTeamLeader");
    UserForm userForm = null;
    UserDetails userDetails = null;
    UserReportingToDetails userReportingToDetails = null;
    UserToHubDetails userToHubDetails = null;
    HubDetails hubDetails = null;
    SimpleDateFormat formatter = null;
    RoleDetails roleDetails = null;
    UserManager userManager = null;
    Map<String, Object> userDetailsMap = null;
    HttpSession session = null;
    Map<String, String> resultMap = null;
    String forwardPage = MANAGE_USERS_PAGE;
    FormFile formFile = null;
    
    try {
      session = request.getSession(false);
      
      if(session != null && session.getAttribute(USER_ID) != null && ((Long)session.getAttribute(USER_ID)).longValue() > 0 ) {
        if (isTokenValid(request)) {
          formatter = new SimpleDateFormat(DATE_FORMAT);
          userForm = (UserForm) form;
          userManager = (UserManager) getBean(USER_MANAGER);
        
          if (userManager != null) {
            // initializing user details obj and setting values
            userDetails = new UserDetails();

            // authentication details
            userDetails.setUserName(userForm.getUserName().toLowerCase());
            PasswordEncy passwordEncy = PasswordEncy.getInstance();
            userDetails.setPassword(passwordEncy.encrypt(PasswordGenerator.getInstance().getGeneratedPassword()));
            String userCode = checkAndGetUniqueUserCode(userManager, TEAM_LEADER, UniqueCodeGenerator.getInstance().getTeamLeaderCode());
            
            if (userCode != null) {
              userDetails.setUserCode(userCode);
            } else {
              log.error("Generated Team Leader code is null");
            }
            
            // personal details
            userDetails.setName(userForm.getName());
            userDetails.setLastName(userForm.getLastName());
            userDetails.setGender(userForm.getGender());
            userDetails.setDateOfBirth(formatter.parse(userForm.getDateOfBirth()));
            userDetails.setBloodGroup(userForm.getBloodGroup());
          
            formFile = userForm.getFile();
            
            if(StringUtil.isNotNull(formFile.getFileName())) {
              String fileName = userForm.getUserName().concat("_").concat(System.currentTimeMillis() + "").concat(formFile.getFileName().substring(formFile.getFileName().lastIndexOf('.')));
              saveUploadedImage(formFile, fileName);
              userDetails.setPhotoPath(fileName);
            } else {
              userDetails.setPhotoPath(userForm.getPhotoPath());
            }
            userDetails.setMaritalStatus(userForm.getMaritalStatus());
  
            if (userForm.getDateOfAnniversary() != null && !userForm.getDateOfAnniversary().equals("")) {
              userDetails.setDateOfAnniversary(formatter.parse(userForm.getDateOfAnniversary()));
            }
          
            // address details
            userDetails.setAddress(userForm.getAddress());
            userDetails.setCity(userForm.getCity());
            userDetails.setDistrict(userForm.getDistrict());
            userDetails.setState(userForm.getState());
            userDetails.setCountry(userForm.getCountry());
            userDetails.setPinCode(userForm.getPinCode());
          
            // primary contact details
            userDetails.setPrimaryContactNo(userForm.getPrimaryContactNo());
            userDetails.setPrimaryEmail(userForm.getPrimaryEmail());
          
            // secondary contact details
            userDetails.setSecondaryContactNo(userForm.getSecondaryContactNo());
            userDetails.setSecondaryEmail(userForm.getSecondaryEmail());
          
            // other details
            userDetails.setNearestPoliceStation(userForm.getNearestPoliceStation());
            userDetails.setOfficePhone(userForm.getOfficePhone());
            userDetails.setImeiNo(userForm.getImeiNo());
          
            userDetails.setStatus(userForm.getStatus());
            userDetails.setDateTime(new Date());
          
            // role details
            roleDetails = new RoleDetails();
            roleDetails.setRoleName(userForm.getRoleName());
            roleDetails.setAliasName(userForm.getRoleName());
            roleDetails.setStatus(ACTIVE);
            roleDetails.setRoleId(Long.valueOf(TEAM_LEADER));
            userDetails.setRoleDetails(roleDetails);
            userReportingToDetails = new UserReportingToDetails();
            userReportingToDetails.setUserDetails(userDetails);
            userReportingToDetails.setStatus(ACTIVE);
            UserDetails reportingToUserDetails = new UserDetails();
            reportingToUserDetails.setUserId(userForm.getReportingToUserID());
            userReportingToDetails.setUserDetails(reportingToUserDetails);

            // user to hub details
            userToHubDetails = new UserToHubDetails();
            userToHubDetails.setStatus(ACTIVE);
            userToHubDetails.setUserDetails(userDetails);
      
            // hub details
            hubDetails = new HubDetails();
            hubDetails.setHubId(userForm.getHubId());
            hubDetails.setStatus(ACTIVE);
            userToHubDetails.setHubDetails(hubDetails);

            userDetailsMap = new HashMap<String, Object>();
            userDetailsMap.put(USER_DETAILS, userDetails);
            userDetailsMap.put(USER_REPORTING_TO_DETAILS, userReportingToDetails);
            userDetailsMap.put(USER_TO_HUB_DETAILS, userToHubDetails);
          
            if(StringUtil.isNull(userDetails.getUserCode())) {
              for(int i = 0; i < 3; i++){
                userCode = checkAndGetUniqueUserCode(userManager, RUNNER, UniqueCodeGenerator.getInstance().getRunnerCode());
                if(StringUtil.isNotNull(userCode)){
                  userDetails.setUserCode(userCode);
                  break;
                }
              }
            }
            
            if(StringUtil.isNotNull(userDetails.getUserCode())) {
              resultMap = userManager.addUser(userDetailsMap);
              
              if (resultMap.get(RESULT) != null && resultMap.get(RESULT).equals(SUCCESS)) {
                log.info("Add user success");
                request.setAttribute(SUCCESS_MESSAGE, ADD_USER_SUCCESS_MESSAGE);
                String smsMessage = "Credentials to access TraceR application are, ";
                smsMessage = smsMessage.concat("Username: "+userDetails.getUserName());
                smsMessage = smsMessage.concat(" and Password: "+PasswordEncy.getInstance().decrypt(userDetails.getPassword()));
                SMSManager.getInstance().sendSMS(userDetails.getOfficePhone().trim(), smsMessage);
              } else if (resultMap.get(ERROR_MESSAGE) != null) {
                request.setAttribute(ERROR_MESSAGE, resultMap.get(ERROR_MESSAGE));
              }
            } else {
              request.setAttribute(ERROR_MESSAGE, "Unable to add Team leader at this time. Please try again later");	
            }
        } else {
          log.error("UserManager is null");
        }
        resetToken(request);
        saveToken(request);
      } else {
        forwardPage = INVALID_TOKEN_ERROR_PAGE;
      }
      } else {
        return mapping.findForward(SESSION_INACTIVE_PAGE);  
      }
    } catch (Exception e) {
      log.error("Problem in the ActionForward addTeamLeader");
      log.error(e);
      ActionMessages errors = new ActionMessages();
      saveErrors(request,errors);
      throw e;  
    }
    log.info("END of the ActionForward addTeamLeader");
    return mapping.findForward(forwardPage);
  }

  // ========================================================================

  public ActionForward showAddManagerPage(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward showAddManagerPage");
    UserForm userForm = null;
    HttpSession session = null;
    
    try {
      session = request.getSession(false);
      
      if(session != null && session.getAttribute(USER_ID) != null && ((Long)session.getAttribute(USER_ID)).longValue() > 0 ) {
        userForm = (UserForm) form;
        BeanUtils.copyProperties(userForm, new UserForm()); // Resetting the form
        userForm.setGender("M");
        saveToken(request);
       } else {
         return mapping.findForward(SESSION_INACTIVE_PAGE);  
       }
    } catch (Exception e) {
      log.error("Problem in the ActionForward showAddManagerPage");
      log.error(e);
      ActionMessages errors = new ActionMessages();
      saveErrors(request,errors);
      throw e;
    }
    log.info("END of the ActionForward showAddManagerPage");
    return mapping.findForward(MANAGER_ADD_PAGE);
  }

  //==========================================================================
  
  public ActionForward addManager(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward addManager");
    UserForm userForm = null;
    UserDetails userDetails = null;
    UserReportingToDetails userReportingToDetails = null;
    UserToHubDetails userToHubDetails = null;
    UserToZoneDetails userToZoneDetails = null;
    UserToRegionDetails userToRegionDetails = null;
    UserToCircleDetails userToCircleDetails = null;
    HubDetails hubDetails = null;
    SimpleDateFormat formatter = null;
    RoleDetails roleDetails = null;
    UserManager userManager = null;
    Map<String, Object> userDetailsMap = null;
    Map<String, String> resultMap = null;
    String forwardPage = MANAGE_USERS_PAGE;
    int roleType = 0;
    HttpSession session = null;
    FormFile formFile = null;
    
    try {
      session = request.getSession(false);
      if(session != null && session.getAttribute(USER_ID) != null && ((Long)session.getAttribute(USER_ID)).longValue() > 0 ) {
        if (isTokenValid(request)) {
          formatter = new SimpleDateFormat(DATE_FORMAT);
          userForm = (UserForm) form;
          userManager = (UserManager) getBean(USER_MANAGER);
          userDetails = new UserDetails();
          userDetails.setUserName(userForm.getUserName().toLowerCase());
          
          PasswordEncy passwordEncy = PasswordEncy.getInstance();
          userDetails.setPassword(passwordEncy.encrypt(PasswordGenerator.getInstance().getGeneratedPassword()));

          int roleId = Integer.parseInt(userForm.getRoleName());
          String userCode = null;
          switch (roleId) {
            case CIRCLE_MANAGER:
            roleType = CIRCLE_MANAGER;
            userCode = checkAndGetUniqueUserCode(userManager, roleType, UniqueCodeGenerator.getInstance().getCircleManagerCode());
            break;
            case REGION_MANAGER:
            roleType = REGION_MANAGER;
            userCode = checkAndGetUniqueUserCode(userManager, roleType, UniqueCodeGenerator.getInstance().getRegionManagerCode());
            break;
            case ZONE_MANAGER:
            roleType = ZONE_MANAGER;
            userCode = checkAndGetUniqueUserCode(userManager, roleType, UniqueCodeGenerator.getInstance().getZoneManagerCode());
            break;
            case HUB_MANAGER:
            roleType = HUB_MANAGER;
            userCode = checkAndGetUniqueUserCode(userManager, roleType, UniqueCodeGenerator.getInstance().getHubManagerCode());
            break;
            default:break;
          }
        
          if (userCode != null) {
            userDetails.setUserCode(userCode);
          } else {
            log.error("Generated Manager code is null");
          }
          // role details
          roleDetails = new RoleDetails();
          roleDetails.setRoleName(ROLE_NAMES[roleId - 1]);
          roleDetails.setAliasName(ROLES[roleId - 1]);
          roleDetails.setStatus(ACTIVE);
          roleDetails.setRoleId((long) roleId);
          userDetails.setRoleDetails(roleDetails);
          
          // personal details
          userDetails.setName(userForm.getName());
          userDetails.setLastName(userForm.getLastName());
          userDetails.setGender(userForm.getGender());
          userDetails.setDateOfBirth(formatter.parse(userForm.getDateOfBirth()));
          userDetails.setBloodGroup(userForm.getBloodGroup());
        
          formFile = userForm.getFile();
          
          if(formFile != null && StringUtil.isNotNull(formFile.getFileName())) {
            String fileName = userForm.getUserName().concat("_").concat(System.currentTimeMillis() + "").concat(formFile.getFileName().substring(formFile.getFileName().lastIndexOf('.')));
            saveUploadedImage(formFile, fileName);
            userDetails.setPhotoPath(fileName);
          } else {
            userDetails.setPhotoPath(userForm.getPhotoPath());
          }
          userDetails.setMaritalStatus(userForm.getMaritalStatus());
          
          if (userForm.getDateOfAnniversary() != null && !userForm.getDateOfAnniversary().equals("")) {
            userDetails.setDateOfAnniversary(formatter.parse(userForm.getDateOfAnniversary()));
          }
        
          // address details
          userDetails.setAddress(userForm.getAddress());
          userDetails.setCity(userForm.getCity());
          userDetails.setDistrict(userForm.getDistrict());
          userDetails.setState(userForm.getState());
          userDetails.setCountry(userForm.getCountry());
          userDetails.setPinCode(userForm.getPinCode());
        
          // primary contact details
          userDetails.setPrimaryContactNo(userForm.getPrimaryContactNo());
          userDetails.setPrimaryEmail(userForm.getPrimaryEmail());
        
          // secondary contact details
          userDetails.setSecondaryContactNo(userForm.getSecondaryContactNo());
          userDetails.setSecondaryEmail(userForm.getSecondaryEmail());
        
          userDetails.setOfficePhone(userDetails.getPrimaryContactNo());
          userDetails.setStatus(userForm.getStatus());
          userDetails.setDateTime(new Date());
        
          if (roleId == HUB_MANAGER) {
            userToHubDetails = new UserToHubDetails();
            userToHubDetails.setStatus(ACTIVE);
            userToHubDetails.setUserDetails(userDetails);
      
            // hub details
            hubDetails = new HubDetails();
            hubDetails.setHubId(userForm.getHubId());
            hubDetails.setStatus(ACTIVE);
            userToHubDetails.setHubDetails(hubDetails);
          } else if (roleId == ZONE_MANAGER) {
            userToZoneDetails = new UserToZoneDetails();
            userToZoneDetails.setStatus(ACTIVE);
            userToZoneDetails.setUserDetails(userDetails);
      
            // zone details
            ZoneDetails zoneDetails = new ZoneDetails();
            zoneDetails.setZoneId(userForm.getHubId());
            zoneDetails.setStatus(ACTIVE);
            userToZoneDetails.setZoneDetails(zoneDetails);
          } else if (roleId == REGION_MANAGER) {
            userToRegionDetails = new UserToRegionDetails();
            userToRegionDetails.setStatus(ACTIVE);
            userToRegionDetails.setUserDetails(userDetails);
      
            // region details
            RegionDetails regionDetails = new RegionDetails();
            regionDetails.setRegionId(userForm.getHubId());
            regionDetails.setStatus(ACTIVE);
            userToRegionDetails.setRegionDetails(regionDetails);
          } else if (roleId == CIRCLE_MANAGER) {
            userToCircleDetails = new UserToCircleDetails();
            userToCircleDetails.setStatus(ACTIVE);
            userToCircleDetails.setUserDetails(userDetails);
      
            // circle details
            CircleDetails circleDetails = new CircleDetails();
            circleDetails.setCircleId(userForm.getHubId());
            circleDetails.setStatus(ACTIVE);
            userToCircleDetails.setCircleDetails(circleDetails);
          }
          userReportingToDetails = new UserReportingToDetails();
          if (roleId == CIRCLE_MANAGER) {
            userReportingToDetails.setUserDetails(userDetails);
            userReportingToDetails.setStatus(ACTIVE);
            UserDetails reportingToUserDetails = new UserDetails();
            reportingToUserDetails.setUserId(1L);
            userReportingToDetails.setUserDetails(reportingToUserDetails);
          } else {
            userReportingToDetails.setUserDetails(userDetails);
            userReportingToDetails.setStatus(ACTIVE);
            UserDetails reportingToUserDetails = new UserDetails();
            reportingToUserDetails.setUserId(userForm.getReportingToUserID());
            userReportingToDetails.setUserDetails(reportingToUserDetails);
          }
          userDetailsMap = new HashMap<String, Object>();
          userDetailsMap.put(USER_DETAILS, userDetails);
          userDetailsMap.put(USER_REPORTING_TO_DETAILS, userReportingToDetails);
          userDetailsMap.put(USER_TO_HUB_DETAILS, userToHubDetails);
          userDetailsMap.put(USER_TO_ZONE_DETAILS, userToZoneDetails);
          userDetailsMap.put(USER_TO_REGION_DETAILS, userToRegionDetails);
          userDetailsMap.put(USER_TO_CIRCLE_DETAILS, userToCircleDetails);
          
          resultMap = userManager.addUser(userDetailsMap);
        
          if (resultMap.get(RESULT) != null && resultMap.get(RESULT).equals(SUCCESS)) {
            log.info("Add Manger success");
            request.setAttribute(SUCCESS_MESSAGE, ADD_USER_SUCCESS_MESSAGE);
            String smsMessage = "Credentials to access TraceR application are, ";
            smsMessage = smsMessage.concat("Username: "+userDetails.getUserName());
            smsMessage = smsMessage.concat(" and Password: "+PasswordEncy.getInstance().decrypt(userDetails.getPassword()));
            SMSManager.getInstance().sendSMS(userDetails.getPrimaryContactNo().trim(), smsMessage);
          } else {
            log.error("Unable to add the manager at this time.");
            forwardPage = MANAGER_ADD_PAGE;
          }
        resetToken(request);
        saveToken(request);
      } else {
        forwardPage = INVALID_TOKEN_ERROR_PAGE;
      }
      } else {
        return mapping.findForward(SESSION_INACTIVE_PAGE);  
      }
    } catch (Exception e) {
      log.error("Problem in the ActionForward addManager");
      log.error(e);
      e.printStackTrace();
      ActionMessages errors = new ActionMessages();
      saveErrors(request,errors);
      throw e;
    }
    log.info("END of the ActionForward addManager");
    return mapping.findForward(forwardPage);
  }

  // ========================================================================

  public ActionForward showManageUsersPage(ActionMapping mapping, ActionForm form,
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward showManageUsersPage");

    try {

    } catch (Exception e) {
      log.error("Problem in the ActionForward showManageUsersPage");
      log.error(e);
      ActionMessages errors = new ActionMessages();
      saveErrors(request,errors);
      throw e;
    }
    log.info("END of the ActionForward showManageUsersPage");
    return mapping.findForward(MANAGE_USERS_PAGE);
  }

  // ========================================================================

  public ActionForward showEditProfilePage(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward showEditProfilePage");
    UserDetails userDetails = null;
    UserForm userForm = null;
    HttpSession session = null;
    Long requestedUserId;
    UserManager userManager = null;
    SimpleDateFormat formatter = null;
    
    try {
      session = request.getSession(false);
      
      if(session != null && session.getAttribute(USER_ID) != null && ((Long)session.getAttribute(USER_ID)).longValue() > 0 ) {
        userManager = (UserManager) getBean(USER_MANAGER);
        userForm = (UserForm) form;
        BeanUtils.copyProperties(userForm, new UserForm());
        formatter = new SimpleDateFormat(DATE_FORMAT);
        requestedUserId = (Long) session.getAttribute(USER_ID);
        userDetails = userManager.getUserDetails(requestedUserId);

        if (userDetails != null) {
          // Personal Details
          userForm.setGender(userDetails.getGender());
          userForm.setName(userDetails.getName());
          userForm.setLastName(userDetails.getLastName());
          userForm.setDateOfBirth(formatter.format(userDetails.getDateOfBirth()));
          userForm.setBloodGroup(userDetails.getBloodGroup());
          userForm.setPhotoPath(userDetails.getPhotoPath());
          userForm.setMaritalStatus(userDetails.getMaritalStatus());
          
          if(userDetails.getDateOfAnniversary() != null && !userDetails.getDateOfAnniversary().equals(""))
            userForm.setDateOfAnniversary(formatter.format(userDetails.getDateOfAnniversary()));

          // Other Details
          userForm.setNearestPoliceStation(userDetails.getNearestPoliceStation());
          userForm.setImeiNo(userDetails.getImeiNo());
          userForm.setOfficePhone(userDetails.getOfficePhone());

          // Address Details
          userForm.setAddress(userDetails.getAddress());
          userForm.setCity(userDetails.getCity());
          userForm.setDistrict(userDetails.getDistrict());
          userForm.setState(userDetails.getState());
          userForm.setCountry(userDetails.getCountry());
          userForm.setPinCode(userDetails.getPinCode());

          // Primary Contact Details
          userForm.setPrimaryContactNo(userDetails.getPrimaryContactNo());
          userForm.setPrimaryEmail(userDetails.getPrimaryEmail());

          // Secondary Contact Details
          userForm.setSecondaryContactNo(userDetails.getSecondaryContactNo());
          userForm.setSecondaryEmail(userDetails.getSecondaryEmail());
        } else {
          log.error("Unable to get the user details");
        }
      } else {
        return mapping.findForward(SESSION_INACTIVE_PAGE);  
      }
    } catch (Exception e) {
      log.error("Problem in the ActionForward showEditProfilePage");
      log.error(e);
      ActionMessages errors = new ActionMessages();
      saveErrors(request,errors);
      throw e;
    }
    log.info("END of the ActionForward showEditProfilePage");
    return mapping.findForward(EDIT_PROFILE_PAGE);
  }

  // ========================================================================

  public ActionForward updateEditProfile(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward updateEditProfile");
    UserForm userForm = null;
    UserDetails userDetails = null;
    UserManager userManager = null;
    Long requestedUserId;
    Long requestedUserRoleId;
    HttpSession session = null;
    SimpleDateFormat formatter = null;
    FormFile formFile = null;
    ActionMessages actionMessages = new ActionMessages();

    try {
      session = request.getSession(false);
      
      if (session != null && session.getAttribute(USER_ID) != null && ((Long) session.getAttribute(USER_ID)).longValue() > 0) {
        userManager = (UserManager) getBean(USER_MANAGER);
        userForm = (UserForm) form;
        userDetails = new UserDetails();

        formatter = new SimpleDateFormat(DATE_FORMAT);
        requestedUserId = (Long) session.getAttribute(USER_ID);
        requestedUserRoleId = (Long) session.getAttribute(ROLE_ID);
        userDetails = userManager.getUserDetails(requestedUserId);
        userDetails.setGender(userForm.getGender());
        userDetails.setName(userForm.getName());
        userDetails.setLastName(userForm.getLastName());
        userDetails.setDateOfBirth(formatter.parse(userForm.getDateOfBirth()));
        userDetails.setBloodGroup(userForm.getBloodGroup());
        
        formFile = userForm.getFile();
        if(StringUtil.isNotNull(formFile.getFileName())) {
          String fileName = session.getAttribute(USER_NAME).toString().concat("_").concat(System.currentTimeMillis() + "").concat(formFile.getFileName().substring(formFile.getFileName().lastIndexOf('.')));
          saveUploadedImage(formFile, fileName);
          userDetails.setPhotoPath(fileName);
        } else {
          userDetails.setPhotoPath(userForm.getPhotoPath());
        }
        
        userDetails.setMaritalStatus(userForm.getMaritalStatus());
        if (userForm.getDateOfAnniversary() != null && !userForm.getDateOfAnniversary().equals(""))
          userDetails.setDateOfAnniversary(formatter.parse(userForm.getDateOfAnniversary()));

        if (requestedUserRoleId == SYSTEM_ADMIN || requestedUserRoleId == TEAM_LEADER) {
          // Other Details
          userDetails.setNearestPoliceStation(userForm.getNearestPoliceStation());
          userDetails.setImeiNo(userForm.getImeiNo());
          userDetails.setOfficePhone(userForm.getOfficePhone());
        }
        // Address Details
        userDetails.setAddress(userForm.getAddress());
        userDetails.setCity(userForm.getCity());
        userDetails.setDistrict(userForm.getDistrict());
        userDetails.setState(userForm.getState());
        userDetails.setCountry(userForm.getCountry());
        userDetails.setPinCode(userForm.getPinCode());

        // Primary Contact Details
        userDetails.setPrimaryContactNo(userForm.getPrimaryContactNo());
        userDetails.setPrimaryEmail(userForm.getPrimaryEmail());

        // Secondary Contact Details
        userDetails.setSecondaryContactNo(userForm.getSecondaryContactNo());
        userDetails.setSecondaryEmail(userForm.getSecondaryEmail());

        String result = userManager.updateProfile(userDetails);
        
        if (result != null && result.equalsIgnoreCase(SUCCESS)) {
          request.setAttribute(UPDATE_SUCCESS, "Profile Updated successfully");
          actionMessages.add("profile.updated.successfully", new ActionMessage("profile.updated.successfully")); 
          saveMessages(request, actionMessages);
        } else if (result.equalsIgnoreCase(ERROR_MESSAGE)) {
          request.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE);
          actionMessages.add("profile.update.failure", new ActionMessage("profile.update.failure")); 
          saveMessages(request, actionMessages);
        }
      } else {
        return mapping.findForward(SESSION_INACTIVE_PAGE);
      }
    } catch (Exception e) {
      log.error("Problem in the ActionForward updateEditProfile");
      log.error(e);
      ActionMessages errors = new ActionMessages();
      saveErrors(request, errors);
      throw e;
    }
    log.info("END of the ActionForward updateEditProfile");
    return mapping.findForward(INFO_PAGE);
  }

  // ========================================================================

  public ActionForward showChangePasswordPage(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward showChangePasswordPage");
    Long requestedUserId;
    HttpSession session = null;
    UserManager userManager = null;
    UserDetails userDetails = null;
    String actualPassword;
    PasswordEncy passwordEncy = PasswordEncy.getInstance();
    
    try {
      session = request.getSession(false);
      if (session != null && session.getAttribute(USER_ID) != null && ((Long) session.getAttribute(USER_ID)).longValue() > 0) {
        userManager = (UserManager) getBean(USER_MANAGER);
        session = request.getSession(false);
        requestedUserId = (Long) session.getAttribute(USER_ID);
        userDetails = userManager.getUserDetails(requestedUserId);
        actualPassword = passwordEncy.decrypt(userDetails.getPassword());
        session.setAttribute("actualPassword", actualPassword);
      } else {
        return mapping.findForward(SESSION_INACTIVE_PAGE);
      }

    } catch (Exception e) {
      log.error("Problem in the ActionForward showChangePasswordPage");
      log.error(e);
      ActionMessages errors = new ActionMessages();
      saveErrors(request, errors);
      throw e;
    }
    log.info("END of the ActionForward showChangePasswordPage");
    return mapping.findForward(CHANGE_PASSWORD_PAGE);
  }

  // ===================================================================================
  
  public ActionForward updatePassword(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward updatePassword");
    UserForm userForm = null;
    UserDetails userDetails = null;
    UserManager userManager = null;
    Long requestedUserId;
    HttpSession session = null;
    PasswordEncy passwordEncy = PasswordEncy.getInstance();
    ActionMessages actionMessages = new ActionMessages();
    
    try {
      session = request.getSession(false);
      
      if (session != null && session.getAttribute(USER_ID) != null && ((Long) session.getAttribute(USER_ID)).longValue() > 0) {
        userManager = (UserManager) getBean(USER_MANAGER);
        userForm = (UserForm) form;
        userDetails = new UserDetails();
        session = request.getSession(false);
        requestedUserId = (Long) session.getAttribute(USER_ID);
        userDetails = userManager.getUserDetails(requestedUserId);
        userDetails.setUserId(requestedUserId);
        userDetails.setPassword(passwordEncy.encrypt(userForm.getNewPassword()));
        //check last five password Strat
        
        List<UserPasswordHistoryDetails> resultSet= userManager.getFivePassword(userDetails);
    	log.info("Sizer of the List is: "+resultSet.size());
    	ArrayList<String> passwordList = new ArrayList<String>();
   	 if (resultSet != null) {
			for (int i = 0; i < resultSet.size(); i++) {
				if (i <= 4) {
					passwordList.add(resultSet.get(i).getPassword());
				}
			}
		}
    	
    	for(String data:passwordList){
    		log.info("Password is: "+passwordEncy.decrypt(data));
    		
    	}
    	boolean passwordCheck = passwordList.contains(passwordEncy.encrypt(userForm.getNewPassword()));
    	log.info("Contains password "+passwordList.contains(passwordEncy.encrypt(userForm.getNewPassword())));
        // check five password end
    	if(!passwordCheck){
         String result = userManager.updatePassword(userDetails);
    	        
        if (result != null && result.equalsIgnoreCase(SUCCESS)) {
        	UserPasswordHistoryDetails userPass = new UserPasswordHistoryDetails();
        	userPass.setUserDetails(userDetails);
        	userPass.setPassword(passwordEncy.encrypt(userForm.getNewPassword()));
        	 userManager.storePassword(userPass);
          request.setAttribute(UPDATE_SUCCESS, "Password  Updated successfully");
          actionMessages.add("password.updated.successfully", new ActionMessage("password.updated.successfully")); 
          saveMessages(request, actionMessages);
        } else if (result.equalsIgnoreCase(ERROR_MESSAGE)) {
          request.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE);
          actionMessages.add("password.update.failure", new ActionMessage("password.update.failure")); 
          saveMessages(request, actionMessages);
        }
      }
    	else{
    		  actionMessages.add("password.repeat.failure", new ActionMessage("password.repeat.failure")); 
              saveMessages(request, actionMessages);
              return mapping.findForward(CHANGE_PASSWORD_PAGE);
    	}
      
    	
    	 
    	 
      } else {
        return mapping.findForward(SESSION_INACTIVE_PAGE);
      }
    } catch (Exception e) {
      log.error("Problem in the ActionForward updatePassword Method");
      log.error(e);
      ActionMessages errors = new ActionMessages();
      saveErrors(request, errors);
      throw e;
    }
    return mapping.findForward(INFO_PAGE);
  }

  // ========================================================================

  public void getUsersDetails(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward getUsersDetails");
    UserManager userManager = null;
    HttpSession session = null;
    Long loggedInUserId;
    long loggedInUserRoleId;
    List<UserDetails> userDetailsList = null;
    JSONArray jsonArray = new JSONArray();
    
    try {
      userManager = (UserManager) getBean(USER_MANAGER);
      session = request.getSession(false);
      userDetailsList = new ArrayList<>();
      loggedInUserId = (Long) session.getAttribute(USER_ID);
      loggedInUserRoleId = (Long) session.getAttribute(ROLE_ID);
      userDetailsList = userManager.getUsers(loggedInUserId, loggedInUserRoleId);

      if (userDetailsList != null) {
        for (int i = 0; i < userDetailsList.size(); i++) {
          JSONObject object = new JSONObject();
          object.put("userId", userDetailsList.get(i).getUserId());
          object.put("lastName", userDetailsList.get(i).getLastName());
          object.put("name", userDetailsList.get(i).getName());
          object.put("gender", userDetailsList.get(i).getGender());
          object.put("role", userDetailsList.get(i).getRoleDetails().getAliasName());
          object.put("contactNumber", "+91"+userDetailsList.get(i).getOfficePhone());
          object.put("reportingTo", userDetailsList.get(i).getReportingTo());
          object.put("status", userDetailsList.get(i).getStatus().equals(ACTIVE) ? ACTIVE_STRING : IN_ACTIVE_STRING);
          object.put("showDeleteBtn", userDetailsList.get(i).getStatus().equals(ACTIVE) ? false : true);
          object.put("showActivateBtn", userDetailsList.get(i).getStatus().equals(ACTIVE) ? true : false);
          jsonArray.put(object);
        }
      }
      response.setContentType("application/json");
      response.getWriter().print(jsonArray);
    } catch (Exception e) {
      log.error("Problem in the ActionForward getUsersDetails");
      log.error(e);
      ActionMessages errors = new ActionMessages();
      saveErrors(request,errors);
      throw e;
    }
    log.info("END of the ActionForward getUsersDetails");
  }

  // ========================================================================

  public ActionForward deleteUser(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward deleteUser");
    UserManager userManager = null;
    Long requestedUserId = 0L;
    String result = null;
    String forwardPage = null;
    HttpSession session = null;
    
    try {
      session = request.getSession(false);
      if(session != null && session.getAttribute(USER_ID) != null && ((Long)session.getAttribute(USER_ID)).longValue() > 0 ) {
        if (request.getParameter(USER_ID) != null) {
          requestedUserId = Long.parseLong(request.getParameter(USER_ID));
          if (requestedUserId != 0) {
            userManager = (UserManager) getBean(USER_MANAGER);
            if (userManager != null) {
              result = userManager.deleteUser(requestedUserId);
            }
          }
        }

        if (SUCCESS.equals(result)) {
          request.setAttribute(SUCCESS_MESSAGE, DELETE_USER_SUCCESS_MESSAGE);
        } else if("RunnerAssignedToBeatPlan".equalsIgnoreCase(result)) {
          request.setAttribute("deleteUserErrorMessage", DELETE_USER_FAILURE_MESSAGE);
        } else {
          request.setAttribute(ERROR_MESSAGE, result);
        }
        forwardPage = MANAGE_USERS_PAGE;
      } else {
        return mapping.findForward(SESSION_INACTIVE_PAGE);
      }
    } catch (Exception e) {
      log.error("Problem in the ActionForward deleteUser");
      log.error(e);
      ActionMessages errors = new ActionMessages();
      saveErrors(request,errors);
      throw e;
    }
    log.info("END of the ActionForward deleteUser");
    return mapping.findForward(forwardPage);
  }

  // ===========================================================================
  
  public ActionForward activateUser(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward activateUser");
    UserManager userManager = null;
    Long requestedUserId = 0L;
    String result = null;
    String forwardPage = null;
    HttpSession session = null;
    
    try {
      session = request.getSession(false);
      if(session != null && session.getAttribute(USER_ID) != null && ((Long)session.getAttribute(USER_ID)).longValue() > 0 ) {
        if (request.getParameter(USER_ID) != null) {
          requestedUserId = Long.parseLong(request.getParameter(USER_ID));
          if (requestedUserId != 0) {
            userManager = (UserManager) getBean(USER_MANAGER);
            
            if (userManager != null) {
              result = userManager.activateUser(requestedUserId);
            }
          }
        }

        if (SUCCESS.equals(result)) {
          request.setAttribute(SUCCESS_MESSAGE, ACTIVATE_USER_SUCCESS_MESSAGE);
        } else {
          request.setAttribute(ERROR_MESSAGE, result);
        }
        forwardPage = MANAGE_USERS_PAGE;
      } else {
        return mapping.findForward(SESSION_INACTIVE_PAGE);
      }
    } catch (Exception e) {
      log.error("Problem in the ActionForward activateUser");
      log.error(e);
      ActionMessages errors = new ActionMessages();
      saveErrors(request,errors);
      throw e;
    }
    log.info("END of the ActionForward activateUser");
    return mapping.findForward(forwardPage);
  }

  // ===========================================================================

  public String checkAndGetUniqueUserCode(UserManager userManagerBean, int type, String code) {
    log.info("START of the String checkAndGetUniqueUserCode");
    String userCode = code;
    int userType = type;
    UserManager userManager = userManagerBean;
    boolean isUserCodeExists = false;
    String newUserCode = null;
    
    try {
      isUserCodeExists = userManager.isUserCodeExists(userCode);
    } catch (Exception e) {
      log.error("Problem in the String checkAndGetUniqueUserCode");
      log.error(e);
    }
    
    if (isUserCodeExists) {
      log.info("Generated user code " + userCode + " exist and getting other one");
      switch (userType) {
        case RUNNER:
        newUserCode = UniqueCodeGenerator.getInstance().getRunnerCode();
        break;

        case TEAM_LEADER:
        newUserCode = UniqueCodeGenerator.getInstance().getTeamLeaderCode();
        break;

        case CIRCLE_MANAGER:
        newUserCode = UniqueCodeGenerator.getInstance().getCircleManagerCode();
        break;

        case REGION_MANAGER:
        newUserCode = UniqueCodeGenerator.getInstance().getRegionManagerCode();
        break;

        case ZONE_MANAGER:
        newUserCode = UniqueCodeGenerator.getInstance().getZoneManagerCode();
        break;

        case HUB_MANAGER:
        newUserCode = UniqueCodeGenerator.getInstance().getHubManagerCode();
        break;

        default:
         break;
      }
      return checkAndGetUniqueUserCode(userManager, userType, newUserCode);
    } else {
      log.info("Generated user code " + userCode + " is unique");
       return userCode;
    }
  }

  // ===========================================================================

  public ActionForward getHubManagersList(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward getHubManagersList");
    UserManager userManager = null;
    List<UserDetails> usersDetails = null;
    StringBuffer stringBufferResult = new StringBuffer();
    
    try {
      userManager = (UserManager) getBean(USER_MANAGER);
      usersDetails = new ArrayList<>();
      if (userManager != null) {
        Long hubId = Long.parseLong(request.getParameter("selectedId"));
        
        if (hubId != null && hubId != 0L) {
          usersDetails = userManager.getHubManagers(Long.parseLong(request.getParameter("selectedId")));
          if (usersDetails != null) {
            for (int i = 0; i < usersDetails.size(); i++) {
              stringBufferResult.append("<option value=" + usersDetails.get(i).getUserId() + ">");
              stringBufferResult.append(usersDetails.get(i).getName().concat(" ").concat(usersDetails.get(i).getLastName()));
              stringBufferResult.append("</option>");
            }
          }
        }
      }
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      out.println(stringBufferResult);
      out.flush();
      out.close();
    } catch (Exception e) {
      log.error("Problem in the ActionForward getHubManagersList");
      log.error(e);
      ActionMessages errors = new ActionMessages();
      saveErrors(request,errors);
      throw e;
    }
    log.info("END of the ActionForward getHubManagersList");
    return mapping.findForward(TEAM_LEADER_ADD_PAGE);
  }

  // ===================================================================================

  public void getZonesList(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward getZonesList");
    UserManager userManager = null;
    List<ZoneDetails> zoneDetailsList = null;
    HttpSession session = null;
    StringBuffer stringBufferResult = new StringBuffer();
    
    try {
      userManager = (UserManager) getBean(USER_MANAGER);
      session = request.getSession(false);
      zoneDetailsList = new ArrayList<>();
      
      if (userManager != null) {
        
        if (session != null) {
          zoneDetailsList = userManager.getZones((Long) session.getAttribute(USER_ID), (Long) session.getAttribute(ROLE_ID), (Long) session.getAttribute(ASSIGNED_REGION_ID));
          
          if (zoneDetailsList != null) {
            for (int i = 0; i < zoneDetailsList.size(); i++) {
              stringBufferResult.append("<option value=" + zoneDetailsList.get(i).getZoneId() + ">");
              stringBufferResult.append(zoneDetailsList.get(i).getZoneName());
              stringBufferResult.append("</option>");
            }
          }
        }
      }
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      out.println(stringBufferResult);
      out.flush();
      out.close();
    } catch (Exception e) {
      log.error("Problem in the ActionForward getZonesList");
      log.error(e);
      ActionMessages errors = new ActionMessages();
      saveErrors(request,errors);
      throw e;
    }
    log.info("END of the ActionForward getZonesList");
    return;
  }

  // ===================================================================================

  public void getHubsList(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward getHubsList");
    UserManager userManager = null;
    List<HubDetails> hubsDetailsList = null;
    HttpSession session = null;
    StringBuffer stringBufferResult = new StringBuffer();
    
    try {
      userManager = (UserManager) getBean(USER_MANAGER);
      session = request.getSession(false);
      hubsDetailsList = new ArrayList<>();
      if (userManager != null) {
        if (session != null) {
          hubsDetailsList = userManager.getHubs((Long) session.getAttribute(USER_ID), (Long) session.getAttribute(ROLE_ID), (Long) session.getAttribute(ASSIGNED_ZONE_ID));
          if (hubsDetailsList != null) {
            for (int i = 0; i < hubsDetailsList.size(); i++) {
              stringBufferResult.append("<option value=" + hubsDetailsList.get(i).getHubId() + ">");
              stringBufferResult.append(hubsDetailsList.get(i).getHubName());
              stringBufferResult.append("</option>");
            }
          }
        }
      }
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      out.println(stringBufferResult);
      out.flush();
      out.close();
    } catch (Exception e) {
      log.error("Problem in the ActionForward getHubsList");
      log.error(e);
      ActionMessages errors = new ActionMessages();
      saveErrors(request,errors);
      throw e;
    }
    log.info("END of the ActionForward getHubsList");
  }

  // ===================================================================================

  public void getRegionsList(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward getRegionsList");
    UserManager userManager = null;
    List<RegionDetails> regionsDetailsList = null;
    HttpSession session = null;
    StringBuffer stringBufferResult = new StringBuffer();
    
    try {
      userManager = (UserManager) getBean(USER_MANAGER);
      session = request.getSession(false);
      regionsDetailsList = new ArrayList<>();
      if (userManager != null) {
        if (session != null) {
          regionsDetailsList = userManager.getRegions((Long) session.getAttribute(USER_ID), (Long) session.getAttribute(ROLE_ID), (Long) session.getAttribute(ASSIGNED_CIRCLE_ID));
          
          if (regionsDetailsList != null) {
            for (int i = 0; i < regionsDetailsList.size(); i++) {
              stringBufferResult.append("<option value=" + regionsDetailsList.get(i).getRegionId() + ">");
              stringBufferResult.append(regionsDetailsList.get(i).getRegionName());
              stringBufferResult.append("</option>");
            }
          }
        }
      }
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      out.println(stringBufferResult);
      out.flush();
      out.close();
    } catch (Exception e) {
      log.error("Problem in the ActionForward getRegionsList");
      log.error(e);
      ActionMessages errors = new ActionMessages();
      saveErrors(request,errors);
      throw e;
    }
    log.info("END of the ActionForward getRegionsList");
  }

  // ===================================================================================

  public void getCirclesList(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward getCirclesList");
    UserManager userManager = null;
    List<CircleDetails> circlesDetailsList = null;
    StringBuffer stringBufferResult = new StringBuffer();
    
    try {
      userManager = (UserManager) getBean(USER_MANAGER);
      circlesDetailsList = new ArrayList<>();
      if (userManager != null) {
        circlesDetailsList = userManager.getCircles();
        if (circlesDetailsList != null) {
          for (int i = 0; i < circlesDetailsList.size(); i++) {
            stringBufferResult.append("<option value=" + circlesDetailsList.get(i).getCircleId() + ">");
            stringBufferResult.append(circlesDetailsList.get(i).getCircleName());
            stringBufferResult.append("</option>");
          }
        }
      }
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      out.println(stringBufferResult);
      out.flush();
      out.close();
    } catch (Exception e) {
      log.error("Problem in the ActionForward getCirclesList");
      log.error(e);
      ActionMessages errors = new ActionMessages();
      saveErrors(request,errors);
      throw e;
    }
    log.info("END of the ActionForward getCirclesList");
  }

  // ===================================================================================
  
  public void getManagersList(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward getManagersList");
    UserManager userManager = null;
    List<UserDetails> usersDetails = null;
    StringBuffer stringBufferResult = new StringBuffer();
    
    try {
      userManager = (UserManager) getBean(USER_MANAGER);
      usersDetails = new ArrayList<>();
      if (userManager != null) {
        switch (Integer.parseInt(request.getParameter("selectedRoleId"))) {
        case REGION_MANAGER:
          usersDetails = userManager.getCircleManagers(Long.parseLong(request.getParameter("selectedId")));
          break;
        case ZONE_MANAGER:
          usersDetails = userManager.getRegionManagers(Long.parseLong(request.getParameter("selectedId")));
          break;
        case HUB_MANAGER:
          usersDetails = userManager.getZoneManagers(Long.parseLong(request.getParameter("selectedId")));
          break;

        default:
          break;
        }
        if (usersDetails != null) {
          for (int i = 0; i < usersDetails.size(); i++) {
            stringBufferResult.append("<option value=" + usersDetails.get(i).getUserId() + ">");
            stringBufferResult.append(usersDetails.get(i).getName().concat(" ").concat(usersDetails.get(i).getLastName()));
            stringBufferResult.append("</option>");
          }
        }
      }
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      out.println(stringBufferResult);
      out.flush();
      out.close();
    } catch (Exception e) {
      log.error("Problem in the ActionForward getManagersList");
      log.error(e);
      ActionMessages errors = new ActionMessages();
      saveErrors(request,errors);
      throw e;
    }
    log.info("END of the ActionForward getManagersList");
  }

  // ===================================================================================
  
  public void getReportingToTeamLeadersList(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward getReportingToList");
    UserManager userManager = null;
    List<UserDetails> usersDetails = null;
    StringBuffer stringBufferResult = new StringBuffer();
    
    try {
      userManager = (UserManager) getBean(USER_MANAGER);
      usersDetails = new ArrayList<>();
      if (userManager != null) {
        if(StringUtil.isNotNull(request.getParameter("selectedId"))) {
          usersDetails = userManager.getTeamLeaders(Long.parseLong(request.getParameter("selectedId")));
          if (usersDetails != null) {
            for (int i = 0; i < usersDetails.size(); i++) {
              stringBufferResult.append("<option value=" + usersDetails.get(i).getUserId() + ">");
              stringBufferResult.append(usersDetails.get(i).getName().concat(" ").concat(usersDetails.get(i).getLastName()));
              stringBufferResult.append("</option>");
            }
          }
        }
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println(stringBufferResult);
        out.flush();
        out.close();  
      }
    } catch (Exception e) {
      log.error("Problem in the ActionForward getReportingToList");
      log.error(e);
      ActionMessages errors = new ActionMessages();
      saveErrors(request,errors);
      throw e;
    }
    log.info("END of the ActionForward getReportingToList");
  }
  
  //===========================================================================
  
  public void isOfficePhoneNoExists(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward isOfficePhoneNoExists");
    UserManager userManager = null;
    boolean isExists = false;
    Long userId = null;
    
    try {
      userManager = (UserManager) getBean(USER_MANAGER);
      if (userManager != null) {
        if(request.getParameter(USER_ID) != null) {
          userId = Long.parseLong(request.getParameter(USER_ID));
        }
        if(request.getParameter(PHONE_NO) != null && request.getParameter(ROLE) != null) {
          isExists = userManager.isPhoneNumberExists(userId, Integer.parseInt(request.getParameter(ROLE)), request.getParameter(PHONE_NO));
        }
      }
      response.setContentType("application/json");
      JSONObject jsonObject = new JSONObject();
      jsonObject.put("phoneNoExists", isExists);
      PrintWriter out = response.getWriter();
      out.println(jsonObject);
      out.flush();
      out.close();
    } catch (Exception e) {
      log.error("Problem in the ActionForward isOfficePhoneNoExists");
      log.error(e);
      ActionMessages errors = new ActionMessages();
      saveErrors(request,errors);
      throw e;
    }
    log.info("END of the ActionForward isOfficePhoneNoExists");
  }

  //===================================================================================
  
  public void isUserExists(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward isUserExists");
    UserManager userManager = null;
    boolean isExists = false;

    try {
      userManager = (UserManager) getBean(USER_MANAGER);
      if (userManager != null) {
        if(request.getParameter(USER_NAME) != null) {
          isExists = userManager.isUserExists(request.getParameter(USER_NAME));
        }
      }
      response.setContentType("application/json");
      JSONObject jsonObject = new JSONObject();
      jsonObject.put("userNameExists", isExists);
      PrintWriter out = response.getWriter();
      out.println(jsonObject);
      out.flush();
      out.close();
    } catch (Exception e) {
      log.error("Problem in the ActionForward isUserExists");
      log.error(e);
      ActionMessages errors = new ActionMessages();
      saveErrors(request,errors);
      throw e;
    }
    log.info("END of the ActionForward isUserExists");
  }
  
  // ===================================================================================
  
  public void saveUploadedImage(FormFile formFile, String fileName) {
    log.info("START of the saveUploadedImage");
  	
    try {
      File newFile = new File(UPLOAD_PHOTOS_PATH, fileName);
      if (!newFile.exists()) {
        FileOutputStream fos = new FileOutputStream(newFile);
        fos.write(formFile.getFileData());
        fos.flush();
        fos.close();
      }
    } catch (Exception e) {
      log.info("END of the saveUploadedImage");
    }
  }
  
  // =====================================================================================
  
  public void getImage(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward getImage");

    try {
      response.setContentType("image/jpeg");
      ServletOutputStream out = response.getOutputStream();

      if(request.getParameter("imagePath") != null) {
        FileInputStream fin = new FileInputStream(UPLOAD_PHOTOS_PATH.concat("/").concat(request.getParameter("imagePath")));
        BufferedInputStream bin = new BufferedInputStream(fin);  
        BufferedOutputStream bout = new BufferedOutputStream(out); 
          
        int ch = 0;
        while((ch=bin.read())!=-1){
          bout.write(ch);
        }

        bin.close();
        fin.close();
        bout.close();
        out.close();
      }
    } catch (Exception e) {
      log.error("Problem in the ActionForward getImage");
      log.error(e);
      ActionMessages errors = new ActionMessages();
      saveErrors(request,errors);
      throw e;
    }
    log.info("END of the ActionForward getImage");
  }
  
  //==========================================================================
  
  public ActionForward showUpdateManagerPage(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward showUpdateManagerPage");
    UserManager userManager = null;
    UserForm userForm = null;
    HttpSession session = null;
    SimpleDateFormat formatter = null;
    Map<String, Object> userDetailsMap = null;
    UserDetails userDetails = null;
    List<HubDetails> hubDetailsList = null;
    List<ZoneDetails> zoneDetailsList = null;
    List<RegionDetails> regionDetailsList = null;
    List<CircleDetails> circleDetailsList = null;
    List<UserDetails> userReportingToList = null;
    Long loggedInUserId = 0L;
    Long loggedInRoleId = 0L;
    Long requestedUserId = 0L;
    
    try {
      session = request.getSession(false);
      
      if(session != null && session.getAttribute(USER_ID) != null && ((Long)session.getAttribute(USER_ID)).longValue() > 0 ) {
        userForm = (UserForm) form;
        BeanUtils.copyProperties(userForm, new UserForm()); // Resetting the form
        userManager = (UserManager) getBean(USER_MANAGER);
        hubDetailsList = new ArrayList<HubDetails>();
        zoneDetailsList = new ArrayList<ZoneDetails>();
        regionDetailsList = new ArrayList<RegionDetails>();
        circleDetailsList = new ArrayList<CircleDetails>();

        userReportingToList = new ArrayList<UserDetails>();
        formatter = new SimpleDateFormat(DATE_FORMAT);
        loggedInUserId = (Long) session.getAttribute(USER_ID);
        loggedInRoleId = (Long) session.getAttribute(ROLE_ID);
        userForm.setGender("M");
        
        if (request.getParameter(USER_ID) != null) {
          requestedUserId = Long.parseLong(request.getParameter(USER_ID));
        }

        if (requestedUserId != null) {
          userDetailsMap = userManager.getUser(requestedUserId);
          
          if (userDetailsMap != null) {
            userDetails = (UserDetails) userDetailsMap.get(USER_DETAILS);
            
            if (userDetails != null) {
              userForm.setUserId(userDetails.getUserId());
              userForm.setUserName(userDetails.getUserName());
              userForm.setPassword(userDetails.getPassword());
              userForm.setUserCode(userDetails.getUserCode());
              userForm.setName(userDetails.getName());
              userForm.setLastName(userDetails.getLastName());
              userForm.setGender(userDetails.getGender());
              userForm.setDateOfBirth(formatter.format(userDetails.getDateOfBirth()));
              userForm.setBloodGroup(userDetails.getBloodGroup());
              userForm.setPhotoPath(userDetails.getPhotoPath());
              userForm.setMaritalStatus(userDetails.getMaritalStatus());
              
              if (userDetails.getDateOfAnniversary() != null && !userDetails.getDateOfAnniversary().equals("")) {
                userForm.setDateOfAnniversary(formatter.format(userDetails.getDateOfAnniversary()));
              }
              // address details
              userForm.setAddress(userDetails.getAddress());
              userForm.setCity(userDetails.getCity());
              userForm.setDistrict(userDetails.getDistrict());
              userForm.setState(userDetails.getState());
              userForm.setCountry(userDetails.getCountry());
              userForm.setPinCode(userDetails.getPinCode());
              
              // primary contact details
              userForm.setPrimaryContactNo(userDetails.getPrimaryContactNo());
              userForm.setPrimaryEmail(userDetails.getPrimaryEmail());
              
              // secondary contact details
              userForm.setSecondaryContactNo(userDetails.getSecondaryContactNo());
              userForm.setSecondaryEmail(userDetails.getSecondaryEmail());
              userForm.setOfficePhone(userDetails.getOfficePhone());
              userForm.setImeiNo(userDetails.getImeiNo());

              RoleDetails roleDetails = userDetails.getRoleDetails();
              Long roleId = roleDetails.getRoleId();

              userForm.setRoleName(roleId.toString());
              userForm.setRoleDetails(roleDetails);
              userForm.setStatus(userDetails.getStatus());
            }
            UserToHubDetails userToHubDetails = (UserToHubDetails) userDetailsMap.get(USER_TO_HUB_DETAILS);

            if (userToHubDetails != null) {
              hubDetailsList = userManager.getHubs(loggedInUserId, loggedInRoleId, (Long) session.getAttribute(ASSIGNED_ZONE_ID));
              userForm.setHubDetailsList(hubDetailsList);
              HubDetails hubDetails = userToHubDetails.getHubDetails();
              
              if (hubDetails != null) {
                userForm.setHubId(hubDetails.getHubId());
                userReportingToList = new ArrayList<>();
                userReportingToList = userManager.getZoneManagers(userForm.getHubId());
                
                if (userReportingToList != null && userReportingToList.size() > 0) {
                  userForm.setUserReportingToList(userReportingToList);
                }
              }
            }
            UserToZoneDetails userToZoneDetails = (UserToZoneDetails) userDetailsMap.get(USER_TO_ZONE_DETAILS);
            
            if (userToZoneDetails != null) {
              zoneDetailsList = userManager.getZones(loggedInUserId, loggedInRoleId, (Long) session.getAttribute(ASSIGNED_REGION_ID));
              userForm.setZoneDetailsList(zoneDetailsList);
              ZoneDetails zoneDetails = userToZoneDetails.getZoneDetails();
              
              if (zoneDetails != null) {
                userForm.setHubId(zoneDetails.getZoneId());

                userReportingToList = new ArrayList<>();
                userReportingToList = userManager.getRegionManagers(userForm.getHubId());

                if (userReportingToList != null && userReportingToList.size() > 0) {
                  userForm.setUserReportingToList(userReportingToList);
                }
              }
            }
            UserToRegionDetails userToRegionDetails = (UserToRegionDetails) userDetailsMap.get(USER_TO_REGION_DETAILS);

            if (userToRegionDetails != null) {
              regionDetailsList = userManager.getRegions(loggedInUserId, loggedInRoleId,  (Long) session.getAttribute(ASSIGNED_CIRCLE_ID));
              userForm.setRegionDetailsList(regionDetailsList);
              RegionDetails regionDetails = userToRegionDetails.getRegionDetails();
              
              if (regionDetails != null) {
                userForm.setHubId(regionDetails.getRegionId());
                userReportingToList = new ArrayList<>();
                userReportingToList = userManager.getCircleManagers(userForm.getHubId());
                if (userReportingToList != null && userReportingToList.size() > 0) {
                  userForm.setUserReportingToList(userReportingToList);
                }
              }
            }
            UserToCircleDetails userToCircleDetails = (UserToCircleDetails) userDetailsMap.get(USER_TO_CIRCLE_DETAILS);
              
            if (userToCircleDetails != null) {
              circleDetailsList = userManager.getCircles();
              userForm.setCircleDetailsList(circleDetailsList);
              CircleDetails circleDetails = userToCircleDetails.getCircleDetails();
              
              if (circleDetails != null) {
                userForm.setHubId(circleDetails.getCircleId());
              }
            }
            UserReportingToDetails userReportingToDetails = (UserReportingToDetails) userDetailsMap.get(USER_REPORTING_TO_DETAILS);
            
            if(userForm.getRoleDetails().getRoleId() == CIRCLE_MANAGER) {
              userForm.setReportingToUserID(1L);
            } else { 
              userForm.setReportingToUserID(userReportingToDetails.getUserDetails().getUserId());
            }
          }
        }
        saveToken(request);
       } else {
         return mapping.findForward(SESSION_INACTIVE_PAGE);  
       }
    } catch (Exception e) {
      log.error("Problem in the ActionForward showAddManagerPage");
      log.error(e);
      ActionMessages errors = new ActionMessages();
      saveErrors(request,errors);
      throw e;
    }
    log.info("END of the ActionForward showUpdateManagerPage");
    return mapping.findForward(MANAGER_UPDATE_PAGE);
  }

  //==========================================================================
  
  public ActionForward updateManager(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward updateManager");
    UserForm userForm = null;
    UserDetails userDetails = null;
    UserReportingToDetails userReportingToDetails = null;
    UserToHubDetails userToHubDetails = null;
    UserToZoneDetails userToZoneDetails = null;
    UserToRegionDetails userToRegionDetails = null;
    UserToCircleDetails userToCircleDetails = null;
    HubDetails hubDetails = null;
    SimpleDateFormat formatter = null;
    RoleDetails roleDetails = null;
    UserManager userManager = null;
    Map<String, Object> userDetailsMap = null;
    Map<String, String> resultMap = null;
    String forwardPage = MANAGE_USERS_PAGE;
    HttpSession session = null;
    FormFile formFile = null;
    
    try {
      session = request.getSession(false);
      if(session != null && session.getAttribute(USER_ID) != null && ((Long)session.getAttribute(USER_ID)).longValue() > 0 ) {
        
        if (isTokenValid(request)) {
          formatter = new SimpleDateFormat(DATE_FORMAT);
          userForm = (UserForm) form;
          userManager = (UserManager) getBean(USER_MANAGER);
          userDetails = new UserDetails();
          userDetails.setUserName(userForm.getUserName());
          userDetails.setUserId(userForm.getUserId());
          userDetails.setPassword(userForm.getPassword());
          userDetails.setUserCode(userForm.getUserCode());
          roleDetails = userForm.getRoleDetails();
          
          if (roleDetails != null) {
            roleDetails.setRoleName(roleDetails.getRoleName());
            roleDetails.setAliasName(roleDetails.getAliasName());
            roleDetails.setStatus(ACTIVE);
            roleDetails.setRoleId(roleDetails.getRoleId());
            userDetails.setRoleDetails(roleDetails);
          }
          // personal details
          userDetails.setName(userForm.getName());
          userDetails.setLastName(userForm.getLastName());
          userDetails.setGender(userForm.getGender());
          userDetails.setDateOfBirth(formatter.parse(userForm.getDateOfBirth()));
          userDetails.setBloodGroup(userForm.getBloodGroup());
        
          formFile = userForm.getFile();
          
          if(formFile != null && StringUtil.isNotNull(formFile.getFileName())) {
            String fileName = userForm.getUserName().concat("_").concat(System.currentTimeMillis() + "").concat(formFile.getFileName().substring(formFile.getFileName().lastIndexOf('.')));
            saveUploadedImage(formFile, fileName);
            userDetails.setPhotoPath(fileName);
          } else {
            userDetails.setPhotoPath(userForm.getPhotoPath());
          }
        
          userDetails.setMaritalStatus(userForm.getMaritalStatus());
          
          if (userForm.getDateOfAnniversary() != null && !userForm.getDateOfAnniversary().equals("")) {
            userDetails.setDateOfAnniversary(formatter.parse(userForm.getDateOfAnniversary()));
          }
          
          // address details
          userDetails.setAddress(userForm.getAddress());
          userDetails.setCity(userForm.getCity());
          userDetails.setDistrict(userForm.getDistrict());
          userDetails.setState(userForm.getState());
          userDetails.setCountry(userForm.getCountry());
          userDetails.setPinCode(userForm.getPinCode());
        
          // primary contact details
          userDetails.setPrimaryContactNo(userForm.getPrimaryContactNo());
          userDetails.setPrimaryEmail(userForm.getPrimaryEmail());
         
          // secondary contact details
          userDetails.setSecondaryContactNo(userForm.getSecondaryContactNo());
          userDetails.setSecondaryEmail(userForm.getSecondaryEmail());
        
          userDetails.setOfficePhone(userDetails.getPrimaryContactNo());
          userDetails.setStatus(userForm.getStatus());
          userDetails.setDateTime(new Date());
          int roleId = Integer.parseInt(userForm.getRoleName());
          
          if (roleId == HUB_MANAGER) {
            userToHubDetails = new UserToHubDetails();
            userToHubDetails.setStatus(ACTIVE);
            userToHubDetails.setUserDetails(userDetails);
          
            // hub details
            hubDetails = new HubDetails();
            hubDetails.setHubId(userForm.getHubId());
            hubDetails.setStatus(ACTIVE);
            userToHubDetails.setHubDetails(hubDetails);
          } else if (roleId == ZONE_MANAGER) {
            userToZoneDetails = new UserToZoneDetails();
            userToZoneDetails.setStatus(ACTIVE);
            userToZoneDetails.setUserDetails(userDetails);
          
            // zone details
            ZoneDetails zoneDetails = new ZoneDetails();
            zoneDetails.setZoneId(userForm.getHubId());
            zoneDetails.setStatus(ACTIVE);
            userToZoneDetails.setZoneDetails(zoneDetails);
          } else if (roleId == REGION_MANAGER) {
            userToRegionDetails = new UserToRegionDetails();
            userToRegionDetails.setStatus(ACTIVE);
            userToRegionDetails.setUserDetails(userDetails);
        
            // region details
            RegionDetails regionDetails = new RegionDetails();
            regionDetails.setRegionId(userForm.getHubId());
            regionDetails.setStatus(ACTIVE);
            userToRegionDetails.setRegionDetails(regionDetails);
          } else if (roleId == CIRCLE_MANAGER) {
            userToCircleDetails = new UserToCircleDetails();
            userToCircleDetails.setStatus(ACTIVE);
            userToCircleDetails.setUserDetails(userDetails);
      
            // circle details
            CircleDetails circleDetails = new CircleDetails();
            circleDetails.setCircleId(userForm.getHubId());
            circleDetails.setStatus(ACTIVE);
            userToCircleDetails.setCircleDetails(circleDetails);
          }
          userReportingToDetails = new UserReportingToDetails();
   
          if (roleId == CIRCLE_MANAGER) {
            userReportingToDetails.setUserDetails(userDetails);
            userReportingToDetails.setStatus(ACTIVE);
            UserDetails reportingToUserDetails = new UserDetails();
            reportingToUserDetails.setUserId(1L);
            userReportingToDetails.setUserDetails(reportingToUserDetails);
          } else {
            userReportingToDetails.setUserDetails(userDetails);
            userReportingToDetails.setStatus(ACTIVE);
            UserDetails reportingToUserDetails = new UserDetails();
            reportingToUserDetails.setUserId(userForm.getReportingToUserID());
            userReportingToDetails.setUserDetails(reportingToUserDetails);
          }
          userDetailsMap = new HashMap<String, Object>();
          userDetailsMap.put(USER_DETAILS, userDetails);
          userDetailsMap.put(USER_REPORTING_TO_DETAILS, userReportingToDetails);
          userDetailsMap.put(USER_TO_HUB_DETAILS, userToHubDetails);
          userDetailsMap.put(USER_TO_ZONE_DETAILS, userToZoneDetails);
          userDetailsMap.put(USER_TO_REGION_DETAILS, userToRegionDetails);
          userDetailsMap.put(USER_TO_CIRCLE_DETAILS, userToCircleDetails);
        
          resultMap = userManager.updateUser(userDetailsMap);
          
          if (resultMap.get(RESULT) != null && resultMap.get(RESULT).equals(SUCCESS)) {
            log.info("Edit user success");
            request.setAttribute(SUCCESS_MESSAGE, EDIT_USER_SUCCESS_MESSAGE);
          }
          resetToken(request);
          saveToken(request);
        } else {
          forwardPage = INVALID_TOKEN_ERROR_PAGE;
        }
      } else {
        return mapping.findForward(SESSION_INACTIVE_PAGE);
      }
    } catch (Exception e) {
      log.error("Problem in the ActionForward updateManager");
      log.error(e);
      e.printStackTrace();
      ActionMessages errors = new ActionMessages();
      saveErrors(request,errors);
      throw e;
    }
    log.info("END of the ActionForward updateManager");
    return mapping.findForward(forwardPage);
  }

  // ========================================================================
  
  public ActionForward showUpdateRunnerPage(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward showUpdateRunnerPage");
    UserManager userManager = null;
    UserForm userForm = null;
    HttpSession session = null;
    SimpleDateFormat formatter = null;
    Map<String, Object> userDetailsMap = null;
    UserDetails userDetails = null;
    List<HubDetails> hubDetailsList = null;
    List<UserDetails> userReportingToList = null;
    Long loggedInUserId = 0L;
    Long requestedUserId = 0L;
    String pageToForward = RUNNER_UPDATE_PAGE;
    
    try {
      session = request.getSession(false);
      
      if(session != null && session.getAttribute(USER_ID) != null && ((Long)session.getAttribute(USER_ID)).longValue() > 0 ) {
        userManager = (UserManager) getBean(USER_MANAGER);
        hubDetailsList = new ArrayList<HubDetails>();
        userReportingToList = new ArrayList<UserDetails>();
        formatter = new SimpleDateFormat(DATE_FORMAT);
        userForm = (UserForm) form;
        BeanUtils.copyProperties(userForm, new UserForm()); // Resetting the form
        loggedInUserId = (Long) session.getAttribute(USER_ID);
        userForm.setGender("M");
        
        // Getting hub details that are assigned to logged in user, and
        // displaying these details into Hub drop down while adding Runner
        hubDetailsList = userManager.getHubs(loggedInUserId, (Long) session.getAttribute(ROLE_ID), (Long) session.getAttribute(ASSIGNED_ZONE_ID));
        
        // by setting hub details list to the user form, will populate these details into Hub drop down
        if (hubDetailsList != null && hubDetailsList.size() > 0) {
          userForm.setHubDetailsList(hubDetailsList);
          
          if (request.getParameter(USER_ID) != null) {
            requestedUserId = Long.parseLong(request.getParameter(USER_ID));
          }
          
          if (requestedUserId != null && requestedUserId != 0) {
            userDetailsMap = userManager.getUser(requestedUserId);
              
            if (userDetailsMap != null) {
                userDetails = (UserDetails) userDetailsMap.get(USER_DETAILS);
                
                if (userDetails != null) {
                  userForm.setUserId(userDetails.getUserId());
                  userForm.setUserName(userDetails.getUserName());
                  userForm.setPassword(PasswordEncy.getInstance().decrypt(userDetails.getPassword()));
                  userForm.setUserCode(userDetails.getUserCode());
                  userForm.setName(userDetails.getName());
                  userForm.setLastName(userDetails.getLastName());
                  userForm.setGender(userDetails.getGender());
                  userForm.setDateOfBirth(formatter.format(userDetails.getDateOfBirth()));
                  userForm.setBloodGroup(userDetails.getBloodGroup());
                  userForm.setPhotoPath(userDetails.getPhotoPath());
                  userForm.setMaritalStatus(userDetails.getMaritalStatus());
                  userForm.setStatus(userDetails.getStatus());
                  
                  if (userDetails.getDateOfAnniversary() != null && !userDetails.getDateOfAnniversary().equals("")) {
                    userForm.setDateOfAnniversary(formatter.format(userDetails.getDateOfAnniversary()));
                  }
                  
                  // address details
                  userForm.setAddress(userDetails.getAddress());
                  userForm.setCity(userDetails.getCity());
                  userForm.setDistrict(userDetails.getDistrict());
                  userForm.setState(userDetails.getState());
                  userForm.setCountry(userDetails.getCountry());
                  userForm.setPinCode(userDetails.getPinCode());
                  userForm.setNearestPoliceStation(userDetails.getNearestPoliceStation());
                  
                  // primary contact details
                  userForm.setPrimaryContactNo(userDetails.getPrimaryContactNo());
                  userForm.setPrimaryEmail(userDetails.getPrimaryEmail());
                  
                  // secondary contact details
                  userForm.setSecondaryContactNo(userDetails.getSecondaryContactNo());
                  userForm.setSecondaryEmail(userDetails.getSecondaryEmail());
                  userForm.setOfficePhone(userDetails.getOfficePhone());
                  userForm.setImeiNo(userDetails.getImeiNo());
                }
                UserToHubDetails userToHubDetails = (UserToHubDetails) userDetailsMap.get(USER_TO_HUB_DETAILS);
                 
                if (userToHubDetails != null) {
                  HubDetails hubDetails = userToHubDetails.getHubDetails();
              
                  if (hubDetails != null) {
                    userForm.setHubId(hubDetails.getHubId());

                    // populating reporting to users drop down with above hub id .
                    userReportingToList = userManager.getTeamLeaders(userForm.getHubId());
                    
                    if (userReportingToList != null) {
                      userForm.setUserReportingToList(userReportingToList);
                    } else {
                      log.info("Edit runner reporting to user details is null");
                      pageToForward = INFO_OR_ERROR_PAGE;
                      ActionMessages actionMessages = new ActionMessages();
                      actionMessages.add("info.reporting.to.user.not.available", new ActionMessage("info.reporting.to.user.not.available"));
                      saveMessages(request, actionMessages);
                    }
                  }
                }
                UserReportingToDetails userReportingToDetails = (UserReportingToDetails) userDetailsMap.get(USER_REPORTING_TO_DETAILS);
                userForm.setReportingToUserID(userReportingToDetails.getUserDetails().getUserId());
              }
          } else {
            log.info("Edit user: requested user id is null or zero");
          }
          saveToken(request);
        } else {
          // Need to show an information to the user that there are no hubs
          pageToForward = INFO_OR_ERROR_PAGE;
          ActionMessages actionMessages = new ActionMessages();
          actionMessages.add("info.hubs.not.available", new ActionMessage("info.hubs.not.available"));
          saveMessages(request, actionMessages);
        }
      } else {
        pageToForward = SESSION_INACTIVE_PAGE;
      }
    } catch (Exception e) {
      log.error("Problem in the ActionForward showUpdateRunnerPage");
      log.error(e);
      ActionMessages errors = new ActionMessages();
      saveErrors(request,errors);
      throw e;
    }
    log.info("END of the ActionForward showUpdateRunnerPage");
    return mapping.findForward(pageToForward);
  }

  // ========================================================================
  
  public ActionForward updateRunner(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward updateRunner");
    UserForm userForm = null;
    UserDetails userDetails = null;
    UserReportingToDetails userReportingToDetails = null;
    UserToHubDetails userToHubDetails = null;
    HubDetails hubDetails = null;
    SimpleDateFormat formatter = null;
    RoleDetails roleDetails = null;
    UserManager userManager = null;
    Map<String, Object> userDetailsMap = null;
    HttpSession session = null;
    Map<String, String> resultMap = null;
    String forwardPage = MANAGE_USERS_PAGE;
    FormFile formFile = null;

    try {
      session = request.getSession(false);
      
      if(session != null && session.getAttribute(USER_ID) != null && ((Long)session.getAttribute(USER_ID)).longValue() > 0 ) {
        if (isTokenValid(request)) {
          formatter = new SimpleDateFormat(DATE_FORMAT);
          userForm = (UserForm) form;
          userManager = (UserManager) getBean(USER_MANAGER);
        
          // initializing user details obj and setting values
          userDetails = new UserDetails();

          // authentication details
          userDetails.setUserName(userForm.getUserName());
          userDetails.setUserId(userForm.getUserId());
          userDetails.setPassword(PasswordEncy.getInstance().encrypt(userForm.getPassword()));
          userDetails.setUserCode(userForm.getUserCode());
          
          // personal details
          userDetails.setName(userForm.getName());
          userDetails.setLastName(userForm.getLastName());
          userDetails.setGender(userForm.getGender());
          userDetails.setDateOfBirth(formatter.parse(userForm.getDateOfBirth()));
          userDetails.setBloodGroup(userForm.getBloodGroup());
          
          formFile = userForm.getFile();
          
          if(StringUtil.isNotNull(formFile.getFileName())) {
            String fileName = userForm.getUserName().concat("_").concat(System.currentTimeMillis() + "").concat(formFile.getFileName().substring(formFile.getFileName().lastIndexOf('.')));
            saveUploadedImage(formFile, fileName);
            userDetails.setPhotoPath(fileName);
          } else {
            userDetails.setPhotoPath(userForm.getPhotoPath());
          }
          userDetails.setMaritalStatus(userForm.getMaritalStatus());
          
          if (userForm.getDateOfAnniversary() != null && !userForm.getDateOfAnniversary().equals("")) {
            userDetails.setDateOfAnniversary(formatter.parse(userForm.getDateOfAnniversary()));
          }
          // address details
          userDetails.setAddress(userForm.getAddress());
          userDetails.setCity(userForm.getCity());
          userDetails.setDistrict(userForm.getDistrict());
          userDetails.setState(userForm.getState());
          userDetails.setCountry(userForm.getCountry());
          userDetails.setPinCode(userForm.getPinCode());
          
          // primary contact details
          userDetails.setPrimaryContactNo(userForm.getPrimaryContactNo());
          userDetails.setPrimaryEmail(userForm.getPrimaryEmail());
          
          // secondary contact details
          userDetails.setSecondaryContactNo(userForm.getSecondaryContactNo());
          userDetails.setSecondaryEmail(userForm.getSecondaryEmail());
          
          // other details
          userDetails.setNearestPoliceStation(userForm.getNearestPoliceStation());
          userDetails.setOfficePhone(userForm.getOfficePhone());
          userDetails.setImeiNo(userForm.getImeiNo());
          userDetails.setStatus(userForm.getStatus());
          userDetails.setDateTime(new Date());

          // role details
          roleDetails = new RoleDetails();
          roleDetails.setStatus(ACTIVE);
          roleDetails.setRoleId(Long.valueOf(RUNNER));
          userDetails.setRoleDetails(roleDetails);

          userReportingToDetails = new UserReportingToDetails();
          userReportingToDetails.setUserDetails(userDetails);
          userReportingToDetails.setStatus(ACTIVE);
          UserDetails reportingToUserDetails = new UserDetails();
          reportingToUserDetails.setUserId(userForm.getReportingToUserID());
          userReportingToDetails.setUserDetails(reportingToUserDetails);

          userToHubDetails = new UserToHubDetails();
          userToHubDetails.setStatus(ACTIVE);
          userToHubDetails.setUserDetails(userDetails);
          
          // hub details
          hubDetails = new HubDetails();
          hubDetails.setHubId(userForm.getHubId());
          hubDetails.setStatus(ACTIVE);
          userToHubDetails.setHubDetails(hubDetails);

          userDetailsMap = new HashMap<String, Object>();
          userDetailsMap.put(USER_DETAILS, userDetails);
          userDetailsMap.put(USER_REPORTING_TO_DETAILS, userReportingToDetails);
          userDetailsMap.put(USER_TO_HUB_DETAILS, userToHubDetails);
          resultMap = userManager.updateUser(userDetailsMap);
          
          if (resultMap.get(RESULT) != null && resultMap.get(RESULT).equals(SUCCESS)) {
            log.info("Edit runner success");
            request.setAttribute(SUCCESS_MESSAGE, EDIT_USER_SUCCESS_MESSAGE);
          } 
          resetToken(request);
          saveToken(request);
        } else {
          forwardPage = INVALID_TOKEN_ERROR_PAGE;
        }
      } else {
        return mapping.findForward(SESSION_INACTIVE_PAGE);  
      }
    } catch (Exception e) {
      log.error("Problem in the ActionForward updateRunner");
      log.error(e);
      ActionMessages errors = new ActionMessages();
      saveErrors(request,errors);
      throw e;
    }
    log.info("END of the ActionForward updateRunner");
    return mapping.findForward(forwardPage);
  }

  // ========================================================================
  
  public ActionForward showUpdateTeamLeaderPage(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward showUpdateTeamLeaderPage");
    UserManager userManager = null;
    UserForm userForm = null;
    HttpSession session = null;
    SimpleDateFormat formatter = null;
    Map<String, Object> userDetailsMap = null;
    UserDetails userDetails = null;
    List<HubDetails> hubDetailsList = null;
    List<UserDetails> userReportingToList = null;
    Long loggedInUserId = 0L;
    Long requestedUserId = 0L;
    
    try {
      session = request.getSession(false);
      if(session != null && session.getAttribute(USER_ID) != null && ((Long) session.getAttribute(USER_ID)).longValue() > 0 ) {
        userManager = (UserManager) getBean(USER_MANAGER);
        hubDetailsList = new ArrayList<HubDetails>();
        formatter = new SimpleDateFormat(DATE_FORMAT);
        userForm = (UserForm) form;
        BeanUtils.copyProperties(userForm, new UserForm()); // Resetting the form
        userForm.setGender("M");
        loggedInUserId = (Long) session.getAttribute(USER_ID);
      
        // Getting hub details that are assigned to logged in user, and
        // displaying these details into Hub drop down while adding Team leader
        hubDetailsList = userManager.getHubs(loggedInUserId, (Long) session.getAttribute(ROLE_ID), (Long) session.getAttribute(ASSIGNED_ZONE_ID));

        // by setting hub details list to the user form, will populate these details into Hub drop down
        if (hubDetailsList != null) {
          userForm.setHubDetailsList(hubDetailsList);
        }

        if (request.getParameter(USER_ID) != null) {
          requestedUserId = Long.parseLong(request.getParameter(USER_ID));
        }
        
        if (requestedUserId != null && requestedUserId != 0) {
          userDetailsMap = userManager.getUser(requestedUserId);
              
          if (userDetailsMap != null) {
            userDetails = (UserDetails) userDetailsMap.get(USER_DETAILS);
                
            if (userDetails != null) {
              userForm.setUserId(userDetails.getUserId());
              userForm.setUserName(userDetails.getUserName());
              userForm.setPassword(userDetails.getPassword());
              userForm.setUserCode(userDetails.getUserCode());
              userForm.setName(userDetails.getName());
              userForm.setLastName(userDetails.getLastName());
              userForm.setGender(userDetails.getGender());
              userForm.setDateOfBirth(formatter.format(userDetails.getDateOfBirth()));
              userForm.setBloodGroup(userDetails.getBloodGroup());
              userForm.setPhotoPath(userDetails.getPhotoPath());
              userForm.setMaritalStatus(userDetails.getMaritalStatus());
              
              if (userDetails.getDateOfAnniversary() != null && !userDetails.getDateOfAnniversary().equals("")) {
                userForm.setDateOfAnniversary(formatter.format(userDetails.getDateOfAnniversary()));
              }
              
              // address details
              userForm.setAddress(userDetails.getAddress());
              userForm.setCity(userDetails.getCity());
              userForm.setDistrict(userDetails.getDistrict());
              userForm.setState(userDetails.getState());
              userForm.setCountry(userDetails.getCountry());
              userForm.setPinCode(userDetails.getPinCode());
              userForm.setNearestPoliceStation(userDetails.getNearestPoliceStation());
              
              // primary contact details
              userForm.setPrimaryContactNo(userDetails.getPrimaryContactNo());
              userForm.setPrimaryEmail(userDetails.getPrimaryEmail());
              
              // secondary contact details
              userForm.setSecondaryContactNo(userDetails.getSecondaryContactNo());
              userForm.setSecondaryEmail(userDetails.getSecondaryEmail());
              userForm.setOfficePhone(userDetails.getOfficePhone());
              userForm.setImeiNo(userDetails.getImeiNo());
              userForm.setStatus(userDetails.getStatus());
            }

            UserToHubDetails userToHubDetails = (UserToHubDetails) userDetailsMap.get(USER_TO_HUB_DETAILS);
            if (userToHubDetails != null) {
              HubDetails hubDetails = userToHubDetails.getHubDetails();
                
              if (hubDetails != null) {
                userForm.setHubId(hubDetails.getHubId());
                // populating reporting to users drop down with above hub id .
                userReportingToList = new ArrayList<UserDetails>();
                userReportingToList = userManager.getHubManagers(userForm.getHubId());
                
                if (userReportingToList != null && userReportingToList.size() > 0) {
                  userForm.setUserReportingToList(userReportingToList);
                }
              }
            }
            UserReportingToDetails userReportingToDetails = (UserReportingToDetails) userDetailsMap.get(USER_REPORTING_TO_DETAILS);
            userForm.setReportingToUserID(userReportingToDetails.getUserDetails().getUserId());
          } else {
            log.error("Unable to get the team leader details");
          }
        } else {
          log.info("showUpdateTeamLeaderPage: Edit user: requested user id is null or zero");
        }
        saveToken(request);
      } else {
        return mapping.findForward(SESSION_INACTIVE_PAGE);  
      }
    } catch (Exception e) {
      log.error("Problem in the ActionForward showUpdateTeamLeaderPage");
      log.error(e);
      ActionMessages errors = new ActionMessages();
      saveErrors(request,errors);
      throw e;
    }
    log.info("END of the ActionForward showUpdateTeamLeaderPage");
    return mapping.findForward(TEAM_LEADER_UPDATE_PAGE);
  }

  // =========================================================================

  public ActionForward updateTeamLeader(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward updateTeamLeader");
    UserForm userForm = null;
    UserDetails userDetails = null;
    UserReportingToDetails userReportingToDetails = null;
    UserToHubDetails userToHubDetails = null;
    HubDetails hubDetails = null;
    SimpleDateFormat formatter = null;
    RoleDetails roleDetails = null;
    UserManager userManager = null;
    Map<String, Object> userDetailsMap = null;
    HttpSession session = null;
    Map<String, String> resultMap = null;
    String forwardPage = MANAGE_USERS_PAGE;
    FormFile formFile = null;
    
    try {
      session = request.getSession(false);
      if(session != null && session.getAttribute(USER_ID) != null && ((Long)session.getAttribute(USER_ID)).longValue() > 0 ) {
        if (isTokenValid(request)) {
          formatter = new SimpleDateFormat(DATE_FORMAT);
          userForm = (UserForm) form;
          userManager = (UserManager) getBean(USER_MANAGER);

          // initializing user details obj and setting values
          userDetails = new UserDetails();

          // authentication details
          userDetails.setUserName(userForm.getUserName());
          userDetails.setUserId(userForm.getUserId());
          userDetails.setPassword(userForm.getPassword());
          userDetails.setUserCode(userForm.getUserCode());

          // personal details
          userDetails.setName(userForm.getName());
          userDetails.setLastName(userForm.getLastName());
          userDetails.setGender(userForm.getGender());
          userDetails.setDateOfBirth(formatter.parse(userForm.getDateOfBirth()));
          userDetails.setBloodGroup(userForm.getBloodGroup());
          
          formFile = userForm.getFile();
          if(StringUtil.isNotNull(formFile.getFileName())) {
            String fileName = userForm.getUserName().concat("_").concat(System.currentTimeMillis() + "").concat(formFile.getFileName().substring(formFile.getFileName().lastIndexOf('.')));
            saveUploadedImage(formFile, fileName);
            userDetails.setPhotoPath(fileName);
          } else {
            userDetails.setPhotoPath(userForm.getPhotoPath());
          }
          
          userDetails.setMaritalStatus(userForm.getMaritalStatus());
          if (userForm.getDateOfAnniversary() != null && !userForm.getDateOfAnniversary().equals("")) {
            userDetails.setDateOfAnniversary(formatter.parse(userForm.getDateOfAnniversary()));
          }
          // address details
          userDetails.setAddress(userForm.getAddress());
          userDetails.setCity(userForm.getCity());
          userDetails.setDistrict(userForm.getDistrict());
          userDetails.setState(userForm.getState());
          userDetails.setCountry(userForm.getCountry());
          userDetails.setPinCode(userForm.getPinCode());
          
          // primary contact details
          userDetails.setPrimaryContactNo(userForm.getPrimaryContactNo());
          userDetails.setPrimaryEmail(userForm.getPrimaryEmail());
          
          // secondary contact details
          userDetails.setSecondaryContactNo(userForm.getSecondaryContactNo());
          userDetails.setSecondaryEmail(userForm.getSecondaryEmail());
          
          // other details
          userDetails.setNearestPoliceStation(userForm.getNearestPoliceStation());
          userDetails.setOfficePhone(userForm.getOfficePhone());
          userDetails.setImeiNo(userForm.getImeiNo());
          userDetails.setStatus(userForm.getStatus());
          userDetails.setDateTime(new Date());
          
          // role details
          roleDetails = new RoleDetails();
          roleDetails.setRoleName(userForm.getRoleName());
          roleDetails.setAliasName(userForm.getRoleName());
          roleDetails.setStatus(ACTIVE);
          roleDetails.setRoleId(Long.valueOf(TEAM_LEADER));
          userDetails.setRoleDetails(roleDetails);
          
          // UserReportingToDetails
          userReportingToDetails = new UserReportingToDetails();
          userReportingToDetails.setUserDetails(userDetails);
          userReportingToDetails.setStatus(ACTIVE);
          UserDetails reportingToUserDetails = new UserDetails();
          reportingToUserDetails.setUserId(userForm.getReportingToUserID());
          userReportingToDetails.setUserDetails(reportingToUserDetails);

          // user to hub details
          userToHubDetails = new UserToHubDetails();
          userToHubDetails.setStatus(ACTIVE);
          userToHubDetails.setUserDetails(userDetails);
          
          // hub details
          hubDetails = new HubDetails();
          hubDetails.setHubId(userForm.getHubId());
          hubDetails.setStatus(ACTIVE);
          userToHubDetails.setHubDetails(hubDetails);
          userDetailsMap = new HashMap<String, Object>();
          userDetailsMap.put(USER_DETAILS, userDetails);
          userDetailsMap.put(USER_REPORTING_TO_DETAILS, userReportingToDetails);
          userDetailsMap.put(USER_TO_HUB_DETAILS, userToHubDetails);
          
          resultMap = userManager.updateUser(userDetailsMap);
            
          if (resultMap.get(RESULT) != null && resultMap.get(RESULT).equals(SUCCESS)) {
            session.removeAttribute(USER_VIEW_TYPE);
            request.setAttribute(SUCCESS_MESSAGE, EDIT_USER_SUCCESS_MESSAGE);
          } else {
            request.setAttribute(ERROR_MESSAGE, "Unable to update the team leader"); 
          }
          resetToken(request);
          saveToken(request);
        } else {
          forwardPage = INVALID_TOKEN_ERROR_PAGE;
        }
      } else {
        return mapping.findForward(SESSION_INACTIVE_PAGE);
      }
    } catch (Exception e) {
      log.error("Problem in the ActionForward updateTeamLeader");
      log.error(e);
      ActionMessages errors = new ActionMessages();
      saveErrors(request,errors);
      throw e;  
    }
    log.info("END of the ActionForward updateTeamLeader");
    return mapping.findForward(forwardPage);
  }

  // ========================================================================
}