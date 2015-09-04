/**
 * @author Jp
 *
 */
package com.tracer.webui.presentation.action;

import static com.tracer.common.Constants.*;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;
import org.json.JSONArray;
import org.json.JSONObject;

import com.tracer.dao.model.DistributorDetails;
import com.tracer.dao.model.DistributorToHubDetails;
import com.tracer.dao.model.HubDetails;
import com.tracer.dao.model.UserDetails;
import com.tracer.service.DistributorManager;
import com.tracer.service.UserManager;
import com.tracer.util.StringUtil;
import com.tracer.util.UniqueCodeGenerator;
import com.tracer.webui.presentation.form.DistributorForm;

public class DistributorAction extends BaseDispatchAction {

  protected transient final Log log = LogFactory.getLog(getClass());

  // ========================================================================

  public ActionForward showAddDistributorPage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
    HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward showAddDistributorPage");
    UserManager userManager = null;
    DistributorForm distributorForm = null;
    HttpSession session = null;
    List<HubDetails> hubDetailsList = null;
    Long loggedInUserId = 0L;
    String pageToForward = ADD_DISTRIBUTOR_PAGE;

    try {
      session = request.getSession(false);
      if (session != null && session.getAttribute(USER_ID) != null
          && ((Long) session.getAttribute(USER_ID)).longValue() > 0) {
        userManager = (UserManager) getBean(USER_MANAGER);
        distributorForm = (DistributorForm) form;
        BeanUtils.copyProperties(distributorForm, new DistributorForm()); // Resetting the form
        hubDetailsList = new ArrayList<>();
        loggedInUserId = (Long) session.getAttribute(USER_ID);

        if (loggedInUserId != 0) {
          hubDetailsList = userManager.getHubs(loggedInUserId, (Long) session.getAttribute(ROLE_ID), null);

          if (hubDetailsList != null) {
            distributorForm.setHubDetailsList(hubDetailsList);
            distributorForm.setDistributorCode((UniqueCodeGenerator.getInstance().getDistributorCode()));
          } else {
            // Need to redirect to a page where prompting the user to add Hub
          }
        }
        saveToken(request);
      } else {
        return mapping.findForward(SESSION_INACTIVE_PAGE);
      }
    } catch (Exception e) {
      log.error("Problem in the ActionForward showAddDistributorPage");
      log.error(e);
      ActionMessages errors = new ActionMessages();
      saveErrors(request, errors);
      throw e;
    }
    log.info("END of the ActionForward showAddDistributorPage");
    return mapping.findForward(pageToForward);
  }

  // ========================================================================

  public ActionForward addDistributor(ActionMapping mapping, ActionForm form,
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward addDistributor");
    DistributorForm distributorForm = null;
    DistributorDetails distributorDetails = null;
    Map<String, Object> distributorDetailsMap = null;
    DistributorManager distributorManager;
    HubDetails hubDetails = null;
    UserDetails userDetails = null;
    HttpSession session = null;
    Map<String, String> resultMap = null;
    String pageToForward = ADD_DISTRIBUTOR_PAGE;
    String viewType = NEW;
    Long userId = 0L;
    FormFile formFile;

    try {
      session = request.getSession(false);
      if (session != null && session.getAttribute(USER_ID) != null && ((Long) session.getAttribute(USER_ID)).longValue() > 0) {
        if (isTokenValid(request)) {
          distributorForm = (DistributorForm) form;
          distributorManager = (DistributorManager) getBean(DISTRIBUTOR_MANAGER);
          distributorDetails = new DistributorDetails();

          if (distributorForm.getDistributorId() != null) {
            distributorDetails.setDistributorId(distributorForm.getDistributorId());
            viewType = EDIT;
          }
          userDetails = new UserDetails();
          userId = (Long) session.getAttribute(USER_ID);
          userDetails.setUserId(userId);
          distributorDetails.setUserDetails(userDetails);
          distributorDetails.setDistributorName(distributorForm.getDistributorName());
          distributorDetails.setDistributorCode(distributorForm.getDistributorCode());
          distributorDetails.setDistributorBarCode(distributorForm.getDistributorBarCode());
          distributorDetails.setOfscCode(distributorForm.getOfscCode());
          distributorDetails.setLattitude(distributorForm.getLattitude());
          distributorDetails.setLongitude(distributorForm.getLongitude());
          distributorDetails.setLicenceInfo(distributorForm.getLicenceInfo());
          distributorDetails.setAddress(distributorForm.getAddress());
          distributorDetails.setLocation(distributorForm.getLocation());
          distributorDetails.setCity(distributorForm.getCity());
          distributorDetails.setState(distributorForm.getState());
          distributorDetails.setDistrict(distributorForm.getDistrict());
          distributorDetails.setCountry(distributorForm.getCountry());
          distributorDetails.setPinCode(distributorForm.getPinCode());
          formFile = distributorForm.getFile();
          
          if (StringUtil.isNotNull(formFile.getFileName())) {
            String fileName = ("Dist_").concat(System.currentTimeMillis() + "").concat(formFile.getFileName().substring(formFile.getFileName().lastIndexOf('.')));
            File newFile = new File(UPLOAD_PHOTOS_PATH, fileName);
            
            if (!newFile.exists()) {
              FileOutputStream fos = new FileOutputStream(newFile);
              fos.write(formFile.getFileData());
              fos.flush();
              fos.close();
            }
            distributorDetails.setPhotoPath(fileName);
          } else {
            distributorDetails.setPhotoPath(distributorForm.getPhotoPath());
          }
          // distributorDetails.setPhotoPath(distributorForm.getPhotoPath());
          distributorDetails.setPrimaryContactNo(distributorForm.getPrimaryContactNo());
          distributorDetails.setPrimaryEmail(distributorForm.getPrimaryEmail());
          distributorDetails.setSecondaryContactNo(distributorForm.getSecondaryContactNo());
          distributorDetails.setSecondaryEmail(distributorForm.getSecondaryEmail());
          distributorDetails.setStatus(ACTIVE);
          distributorDetails.setDateTime(new Date());

          // hub details
          hubDetails = new HubDetails();
          hubDetails.setHubId(Long.parseLong(distributorForm.getHubName()));
          hubDetails.setStatus(ACTIVE);

          distributorDetailsMap = new HashMap<String, Object>();
          distributorDetailsMap.put(DISTRIBUTOR_TO_HUB_DETAILS, hubDetails);
          distributorDetailsMap.put(DISTRIBUTOR_DETAILS, distributorDetails);

          if (viewType.equals(EDIT)) {
            resultMap = distributorManager.updateDistributor(distributorDetailsMap);

            if (resultMap.get(RESULT) != null && resultMap.get(RESULT).equals(SUCCESS)) {
              log.info("Edit distributor success");
              request.setAttribute(SUCCESS_MESSAGE, EDIT_DISTRIBUTOR_SUCCESS_MESSAGE);
              pageToForward = MANAGE_DISTRIBUTORS_PAGE;
            }
          } else {
            resultMap = distributorManager.addDistributor(distributorDetailsMap);

            if (resultMap.get(RESULT) != null && resultMap.get(RESULT).equals(SUCCESS)) {
              log.info("Add distributor success");
              request.setAttribute(SUCCESS_MESSAGE, ADD_DISTRIBUTOR_SUCCESS_MESSAGE);
              pageToForward = MANAGE_DISTRIBUTORS_PAGE;
            } else if (resultMap.get(ERROR_MESSAGE) != null) {
              request.setAttribute(ERROR_MESSAGE, resultMap.get(ERROR_MESSAGE));
            }
          }
          resetToken(request);
          saveToken(request);
        } else {
          pageToForward = INVALID_TOKEN_ERROR_PAGE;
        }
      } else {
        return mapping.findForward(SESSION_INACTIVE_PAGE);
      }
    } catch (Exception e) {
      log.error("Problem in the ActionForward addDistributor");
      log.error(e);
      ActionMessages errors = new ActionMessages();
      saveErrors(request, errors);
      throw e;
    }
    log.info("END of the ActionForward addDistributor");
    return mapping.findForward(pageToForward);

  }

  // ========================================================================

  public ActionForward showManageDistributorsPage(ActionMapping mapping,  ActionForm form, HttpServletRequest request,
    HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward showManageDistributorsPage");

    try {
      // resetToken(request);
      // saveToken(request);
    } catch (Exception e) {
      log.error("Problem in the ActionForward showManageDistributorsPage");
      log.error(e);
      ActionMessages errors = new ActionMessages();
      saveErrors(request, errors);
      throw e;
    }
    log.info("END of the ActionForward showManageDistributorsPage");
    return mapping.findForward(MANAGE_DISTRIBUTORS_PAGE);
  }

  // ========================================================================

  public void getDistributorsDetails(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward getDistributorsDetails");
    DistributorManager distributorManager = null;
    List<DistributorDetails> distributorList = null;
    JSONArray distributorsJSONArray = new JSONArray();

    try {
      distributorManager = (DistributorManager) getBean(DISTRIBUTOR_MANAGER);
      distributorList = distributorManager.getDistributors();

      if (distributorList != null) {
        for (int i = 0; i < distributorList.size(); i++) {
          JSONObject distributorDetailsJson = new JSONObject();
          distributorDetailsJson.put("distributorName", distributorList.get(i).getDistributorName());
          distributorDetailsJson.put("contactNo", "+91" + distributorList.get(i).getPrimaryContactNo());
          distributorDetailsJson.put("hubName", distributorList.get(i).getHubName());
          distributorDetailsJson.put("ofscCode", distributorList.get(i).getOfscCode());
          distributorDetailsJson.put("city", distributorList.get(i).getCity());
          distributorDetailsJson.put("state", distributorList.get(i).getState());
          distributorDetailsJson.put("distributorId", distributorList.get(i).getDistributorId());
          distributorDetailsJson.put("status", distributorList.get(i).getStatus().equals(ACTIVE) ? ACTIVE_STRING : IN_ACTIVE_STRING);
          distributorDetailsJson.put("showDeleteBtn", distributorList.get(i).getStatus().equals(ACTIVE) ? false : true);
          distributorDetailsJson.put("showActivateBtn", distributorList.get(i).getStatus().equals(ACTIVE) ? true : false);
          distributorsJSONArray.put(distributorDetailsJson);
        }
      }
      response.setContentType(JSON);
      response.getWriter().print(distributorsJSONArray);
    } catch (Exception e) {
      log.error("Problem in the ActionForward getDistributorsDetails");
      log.error(e);
      ActionMessages errors = new ActionMessages();
      saveErrors(request, errors);
      throw e;
    }
    log.info("END of the ActionForward getDistributorsDetails");
  }

  // ========================================================================

  public ActionForward deleteDistributor(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward deleteDistributor");
    DistributorManager distributorManager = null;
    Long requestedDistributorId = 0L;
    String result = null;
    String pageToForward = MANAGE_DISTRIBUTORS_PAGE;
    HttpSession session = null;

    try {
      session = request.getSession(false);
      if (session != null && session.getAttribute(USER_ID) != null && ((Long) session.getAttribute(USER_ID)).longValue() > 0) {
        // if(isTokenValid(request)) {
        if (request.getParameter(DISTRIBUTOR_ID) != null) {
          requestedDistributorId = Long.parseLong(request.getParameter(DISTRIBUTOR_ID));

          if (requestedDistributorId != 0) {
            distributorManager = (DistributorManager) getBean(DISTRIBUTOR_MANAGER);

            if (distributorManager != null) {
              result = distributorManager.deleteDistributor(requestedDistributorId);
              //response.getWriter().print(result);
              
              if (result != null && result.equals(SUCCESS)) {
                request.setAttribute(SUCCESS_MESSAGE, DELETE_DISTRIBUTOR_SUCCESS_MESSAGE);
              } else if("BeatPlanAssigned".equalsIgnoreCase(result)) {
                request.setAttribute("deleteDistributorErrorMessage", DELETE_DISTRIBUTOR_FAILURE_MESSAGE);
              } else {
                request.setAttribute(ERROR_MESSAGE, result);  
              }
            }
          }
        }
      } else {
        return mapping.findForward(SESSION_INACTIVE_PAGE);
      }
      /*
       * resetToken(request); saveToken(request); } else { pageToForward =
       * INVALID_TOKEN_ERROR_PAGE; }
       */
    } catch (Exception e) {
      log.error("Problem in the ActionForward deleteDistributor");
      log.error(e);
      ActionMessages errors = new ActionMessages();
      saveErrors(request, errors);
      throw e;
    }
    log.info("END of the ActionForward deleteDistributor");
    return mapping.findForward(pageToForward);
  }

  // ========================================================================
  
  public ActionForward activateDistributor(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward activateDistributor");
    DistributorManager distributorManager = null;
    Long requestedDistributorId = 0L;
    String result = null;
    String pageToForward = MANAGE_DISTRIBUTORS_PAGE;
    HttpSession session = null;

    try {
      session = request.getSession(false);
      if (session != null && session.getAttribute(USER_ID) != null && ((Long) session.getAttribute(USER_ID)).longValue() > 0) {
        // if(isTokenValid(request)) {
        if (request.getParameter(DISTRIBUTOR_ID) != null) {
          requestedDistributorId = Long.parseLong(request.getParameter(DISTRIBUTOR_ID));

          if (requestedDistributorId != 0) {
            distributorManager = (DistributorManager) getBean(DISTRIBUTOR_MANAGER);

            if (distributorManager != null) {
              result = distributorManager.activateDistributor(requestedDistributorId);
              //response.getWriter().print(result);
              
              if (result != null && result.equals(SUCCESS)) {
                request.setAttribute(SUCCESS_MESSAGE, ACTIVATE_DISTRIBUTOR_SUCCESS_MESSAGE);
              } else {
                request.setAttribute(ERROR_MESSAGE, result);  
              }
            }
          }
        }
      } else {
        return mapping.findForward(SESSION_INACTIVE_PAGE);
      }
    } catch (Exception e) {
      log.error("Problem in the ActionForward activateDistributor");
      log.error(e);
      ActionMessages errors = new ActionMessages();
      saveErrors(request, errors);
      throw e;
    }
    log.info("END of the ActionForward activateDistributor");
    return mapping.findForward(pageToForward);
  }

  // ========================================================================

  public ActionForward showEditDistributorPage(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward showEditDistributorPage");
    UserManager userManager = (UserManager) getBean(USER_MANAGER);
    DistributorManager distributorManager = null;
    DistributorForm distributorForm = null;
    HttpSession session = null;
    List<HubDetails> hubDetailsList = null;
    Map<String, Object> distributorDetailsMap = null;
    DistributorDetails distributorDetails = null;
    Long loggedInUserId = 0L;
    Long requestedDistributorId = 0L;
    HubDetails hubDetails = null;
    DistributorToHubDetails distributorToHubDetails = null;

    try {
      session = request.getSession(false);
      if (session != null && session.getAttribute(USER_ID) != null && ((Long) session.getAttribute(USER_ID)).longValue() > 0) {
        loggedInUserId = (Long) session.getAttribute(USER_ID);
        distributorManager = (DistributorManager) getBean(DISTRIBUTOR_MANAGER);
        distributorForm = (DistributorForm) form;
        BeanUtils.copyProperties(distributorForm, new DistributorForm());

        hubDetailsList = userManager.getHubs(loggedInUserId, (Long) session.getAttribute(ROLE_ID), null);
        distributorForm.setHubDetailsList(hubDetailsList);

        if (request.getParameter(DISTRIBUTOR_ID) != null) {
          requestedDistributorId = Long.parseLong(request.getParameter(DISTRIBUTOR_ID));

          if (requestedDistributorId != null && requestedDistributorId != 0) {
            distributorDetailsMap = distributorManager.getDistributorDetails(requestedDistributorId);

            if (distributorDetailsMap != null) {
              distributorDetails = (DistributorDetails) distributorDetailsMap.get(DISTRIBUTOR_DETAILS);
              distributorForm.setDistributorId(distributorDetails.getDistributorId());
              distributorForm.setDistributorName(distributorDetails.getDistributorName());
              distributorForm.setDistributorBarCode(distributorDetails.getDistributorBarCode());
              distributorForm.setDistributorCode(distributorDetails.getDistributorCode());
              distributorForm.setLattitude(distributorDetails.getLattitude());
              distributorForm.setLongitude(distributorDetails.getLongitude());
              distributorForm.setOfscCode(distributorDetails.getOfscCode());
              distributorForm.setAddress(distributorDetails.getAddress());
              distributorForm.setCity(distributorDetails.getCity());
              distributorForm.setState(distributorDetails.getState());
              distributorForm.setLocation(distributorDetails.getLocation());
              distributorForm.setDistrict(distributorDetails.getDistrict());
              distributorForm.setCountry(distributorDetails.getCountry());
              distributorForm.setPinCode(distributorDetails.getPinCode());
              distributorForm.setPrimaryContactNo(distributorDetails.getPrimaryContactNo());
              distributorForm.setPrimaryEmail(distributorDetails.getPrimaryEmail());
              distributorForm.setSecondaryContactNo(distributorForm.getSecondaryContactNo());
              distributorForm.setLicenceInfo(distributorDetails.getLicenceInfo());
              distributorForm.setAssignedToBeatPlan((Boolean) distributorDetailsMap.get(IS_ASSINGED_TO_BEAT_PLAN));
              distributorForm.setPhotoPath(distributorDetails.getPhotoPath());

              distributorToHubDetails = ((DistributorToHubDetails) distributorDetailsMap.get(DISTRIBUTOR_TO_HUB_DETAILS));

              if (distributorToHubDetails != null) {
                hubDetails = distributorToHubDetails.getHubDetails();

                if (hubDetails != null) {
                  distributorForm.setHubName(hubDetails.getHubId().toString());
                }
              }
            }
          }
        } else {
          log.info("Edit distributor: requested user id is null or zero");
        }
      } else {
        return mapping.findForward(SESSION_INACTIVE_PAGE);
      }
    } catch (Exception e) {
      log.error("Problem in the ActionForward showEditDistributorPage");
      log.error(e);
      ActionMessages errors = new ActionMessages();
      saveErrors(request, errors);
      throw e;
    }
    log.info("END of the ActionForward showEditDistributorPage");
    return mapping.findForward(ADD_DISTRIBUTOR_PAGE);
  }

  // ========================================================================
}