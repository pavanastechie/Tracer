/**
 ** @author Jp
 *
 */
package com.tracer.webui.presentation.action;

import static com.tracer.common.Constants.ACTIVE;
import static com.tracer.common.Constants.ADD_DISTRIBUTOR_BEAT_PLAN_PAGE;
import static com.tracer.common.Constants.ADD_DISTRIBUTOR_BEAT_PLAN_SUCCESS_MESSAGE;
import static com.tracer.common.Constants.ADD_RUNNER_BEAT_PLAN_PAGE;
import static com.tracer.common.Constants.ADD_RUNNER_BEAT_PLAN_SUCCESS_MESSAGE;
import static com.tracer.common.Constants.BEAT_PLANS_MANAGER;
import static com.tracer.common.Constants.BEAT_PLAN_BULK_UPLOAD_PAGE;
import static com.tracer.common.Constants.BEAT_PLAN_DETAILS;
import static com.tracer.common.Constants.BEAT_PLAN_TO_DISTRIBUTOR_DETAILS;
import static com.tracer.common.Constants.BEAT_PLAN_TO_USER_DETAILS;
import static com.tracer.common.Constants.CIRCLE_ID;
import static com.tracer.common.Constants.CIRCLE_NAME;
import static com.tracer.common.Constants.DELETE_DISTRIBUTOR_BEAT_PLAN_SUCCESS_MESSAGE;
import static com.tracer.common.Constants.DELETE_RUNNER_BEAT_PLAN_SUCCESS_MESSAGE;
import static com.tracer.common.Constants.DISTRIBUTORS;
import static com.tracer.common.Constants.DISTRIBUTOR_DETAILS;
import static com.tracer.common.Constants.DISTRIBUTOR_MANAGER;
import static com.tracer.common.Constants.EDIT_DISTRIBUTOR_BEAT_PLAN_SUCCESS_MESSAGE;
import static com.tracer.common.Constants.EDIT_RUNNER_BEAT_PLAN_SUCCESS_MESSAGE;
import static com.tracer.common.Constants.ERROR_MESSAGE;
import static com.tracer.common.Constants.HUB_ID;
import static com.tracer.common.Constants.HUB_NAME;
import static com.tracer.common.Constants.IN_ACTIVE;
import static com.tracer.common.Constants.MANAGE_BEAT_PLANS_PAGE;
import static com.tracer.common.Constants.MANAGE_RUNNER_BEAT_PLANS_PAGE;
import static com.tracer.common.Constants.REGION_ID;
import static com.tracer.common.Constants.REGION_NAME;
import static com.tracer.common.Constants.RESULT;
import static com.tracer.common.Constants.ROLE_ID;
import static com.tracer.common.Constants.RUNNERS;
import static com.tracer.common.Constants.SESSION_INACTIVE_PAGE;
import static com.tracer.common.Constants.SUCCESS;
import static com.tracer.common.Constants.SUCCESS_MESSAGE;
import static com.tracer.common.Constants.TIME_FORMAT;
import static com.tracer.common.Constants.USER_ID;
import static com.tracer.common.Constants.USER_MANAGER;
import static com.tracer.common.Constants.ZONE_ID;
import static com.tracer.common.Constants.ZONE_NAME;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.json.JSONObject;

import com.tracer.dao.model.BeatPlanDetails;
import com.tracer.dao.model.BeatPlanDistributorAssignments;
import com.tracer.dao.model.BeatPlanUserAssignments;
import com.tracer.dao.model.CircleDetails;
import com.tracer.dao.model.DistributorBeatPlansDetails;
import com.tracer.dao.model.DistributorDetails;
import com.tracer.dao.model.HubDetails;
import com.tracer.dao.model.RegionDetails;
import com.tracer.dao.model.RunnerBeatPlansDetails;
import com.tracer.dao.model.UserDetails;
import com.tracer.dao.model.ZoneDetails;
import com.tracer.service.BeatPlansManager;
import com.tracer.service.DistributorManager;
import com.tracer.service.UserManager;
import com.tracer.util.UniqueCodeGenerator;
import com.tracer.webui.presentation.form.BeatPlansForm;

public class BeatPlansAction extends BaseDispatchAction {

  protected transient final Log log = LogFactory.getLog(getClass());

  // ========================================================================
  
  public ActionForward showAddDistributorBeatPlanPage(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward showAddDistributorBeatPlanPage");
    BeatPlansForm beatPlansform = null;
    BeatPlansManager beatPlansManager = null;
    List<CircleDetails> circleDetailsList = null;
    HttpSession session = null;
    
    try {
      session = request.getSession(false);
      
      if (session != null && session.getAttribute(USER_ID) != null && ((Long) session.getAttribute(USER_ID)).longValue() > 0) {
        beatPlansManager = (BeatPlansManager) getBean(BEAT_PLANS_MANAGER);
        circleDetailsList = new ArrayList<>();

        beatPlansform = (BeatPlansForm) form;
        BeanUtils.copyProperties(beatPlansform, new BeatPlansForm());
        circleDetailsList = beatPlansManager.getCircles();

        beatPlansform.setCircleDetailsList(circleDetailsList);
        request.getSession(false).setAttribute("BeatPlansJSONArray", null);
        request.getSession(false).setAttribute("viewType", "view");
        saveToken(request);
      } else {
        return mapping.findForward(SESSION_INACTIVE_PAGE);
      }
    } catch (Exception e) {
      log.error("Problem in the ActionForward showAddDistributorBeatPlanPage");
      log.error(e);
      ActionMessages errors = new ActionMessages();
      saveErrors(request, errors);
      throw e;
    }
    log.info("END of the ActionForward showAddDistributorBeatPlanPage");
    return mapping.findForward(ADD_DISTRIBUTOR_BEAT_PLAN_PAGE);
  }

  // =========================================================================
  
  public ActionForward showAddRunnerBeatPlanPage(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward showAddRunnerBeatPlanPage");
    BeatPlansForm beatPlansform = null;
    BeatPlansManager beatPlansManager = null;
    List<BeatPlanDetails> beatDetailsList = null;
    HttpSession session = null;
    Long requestedUserId;
    Long requestedRoleId;
    
    try {
      session = request.getSession(false);
      
      if (session != null && session.getAttribute(USER_ID) != null && ((Long) session.getAttribute(USER_ID)).longValue() > 0) {
        beatPlansform = (BeatPlansForm) form;
        BeanUtils.copyProperties(beatPlansform, new BeatPlansForm());
        requestedUserId = (Long) session.getAttribute(USER_ID);
        requestedRoleId = (Long) session.getAttribute(ROLE_ID);
        beatPlansManager = (BeatPlansManager) getBean(BEAT_PLANS_MANAGER);
        beatDetailsList = new ArrayList<>();
        beatDetailsList = beatPlansManager.getBeatPlans(requestedUserId, requestedRoleId);
        beatPlansform.setBeatPlanDetailsList(beatDetailsList);
        request.getSession(false).setAttribute("BeatPlansJSONArray", null);
        request.getSession(false).setAttribute("viewType", "view");
        saveToken(request);
      } else {
        return mapping.findForward(SESSION_INACTIVE_PAGE);
      }
    } catch (Exception e) {
      log.error("Problem in the ActionForward showAddRunnerBeatPlanPage");
      log.error(e);
      ActionMessages errors = new ActionMessages();
      saveErrors(request, errors);
      throw e;
    }
    log.info("END of the ActionForward showAddRunnerBeatPlanPage");
    return mapping.findForward(ADD_RUNNER_BEAT_PLAN_PAGE);
  }

  // =========================================================================
  
  public ActionForward showBeatPlanBulkUploadPage(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward showBeatPlanBulkUploadPage");

    try {

    } catch (Exception e) {
      log.error("Problem in the ActionForward showBeatPlanBulkUploadPage");
      log.error(e);
      ActionMessages errors = new ActionMessages();
      saveErrors(request, errors);
      throw e;
    }
    log.info("END of the ActionForward showBeatPlanBulkUploadPage");
    return mapping.findForward(BEAT_PLAN_BULK_UPLOAD_PAGE);
  }

  // =========================================================================
  
  public ActionForward showManageDistributorBeatPlansPage(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward showManageDistributorBeatPlansPage");

    try {

    } catch (Exception e) {
      log.error("Problem in the ActionForward showManageDistributorBeatPlansPage");
      log.error(e);
      ActionMessages errors = new ActionMessages();
      saveErrors(request, errors);
      throw e;
    }
    log.info("END of the ActionForward showManageDistributorBeatPlansPage");
    return mapping.findForward(MANAGE_BEAT_PLANS_PAGE);
  }

  // =========================================================================
  
  public ActionForward showManageRunnerBeatPlansPage(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward showManageRunnerBeatPlansPage");

    try {

    } catch (Exception e) {
      log.error("Problem in the ActionForward showManageRunnerBeatPlansPage");
      log.error(e);
      ActionMessages errors = new ActionMessages();
      saveErrors(request, errors);
      throw e;
    }
    log.info("END of the ActionForward showManageRunnerBeatPlansPage");
    return mapping.findForward(MANAGE_RUNNER_BEAT_PLANS_PAGE);
  }

  // =========================================================================
  
  public ActionForward getRegionsList(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
    HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward getRegionsList");
    BeatPlansManager beatPlansManager = null;
    HttpSession session = null;
    List<RegionDetails> regionDetailsList = null;
    StringBuffer stringBufferResult = new StringBuffer();
    Long circleId = 0L;

    try {
      session = request.getSession(false);
      
      if (session != null && session.getAttribute(USER_ID) != null && ((Long) session.getAttribute(USER_ID)).longValue() > 0) {
        beatPlansManager = (BeatPlansManager) getBean(BEAT_PLANS_MANAGER);
        session = request.getSession(false);
        regionDetailsList = new ArrayList<>();

        if (beatPlansManager != null) {
          circleId = Long.parseLong(request.getParameter("selectedId"));
          if (circleId != null && circleId != 0L) {
            regionDetailsList = beatPlansManager.getRegions(Long.parseLong(request.getParameter("selectedId")));
            if (regionDetailsList != null) {
              for (int i = 0; i < regionDetailsList.size(); i++) {
                stringBufferResult.append("<option value=" + regionDetailsList.get(i).getRegionId() + ">");
                stringBufferResult.append(regionDetailsList.get(i).getRegionName());
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
      } else {
        return mapping.findForward(SESSION_INACTIVE_PAGE);
      }
    } catch (Exception e) {
      log.error("Problem in the ActionForward getRegionsList");
      log.error(e);
      ActionMessages errors = new ActionMessages();
      saveErrors(request, errors);
      throw e;
    }
    log.info("END of the ActionForward showTopRunnersReportPage");
    return mapping.findForward(ADD_DISTRIBUTOR_BEAT_PLAN_PAGE);
  }

  // =========================================================================
  
  public ActionForward getZonesList(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward getZonesList");
    BeatPlansManager beatPlansManager = null;
    HttpSession session = null;
    List<ZoneDetails> zoneDetailsList = null;
    Long regionId = 0L;
    StringBuffer stringBufferResult = new StringBuffer();
    
    try {
      session = request.getSession(false);
      
      if (session != null && session.getAttribute(USER_ID) != null && ((Long) session.getAttribute(USER_ID)).longValue() > 0) {
        beatPlansManager = (BeatPlansManager) getBean(BEAT_PLANS_MANAGER);
        zoneDetailsList = new ArrayList<>();

        if (beatPlansManager != null) {
          regionId = Long.parseLong(request.getParameter("selectedId"));
          if (regionId != null && regionId != 0L) {
            zoneDetailsList = beatPlansManager.getZones(Long.parseLong(request.getParameter("selectedId")));
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
        log.info(stringBufferResult);
        out.println(stringBufferResult);
        out.flush();
        out.close();
      } else {
        return mapping.findForward(SESSION_INACTIVE_PAGE);
      }
    } catch (Exception e) {
      log.error("Problem in the ActionForward getZonesList");
      log.error(e);
      ActionMessages errors = new ActionMessages();
      saveErrors(request, errors);
      throw e;
    }
    log.info("END of the ActionForward getZonesList");
    return mapping.findForward(ADD_DISTRIBUTOR_BEAT_PLAN_PAGE);
  }

  // =========================================================================
  
  public ActionForward getHubsList(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward getHubsList");
    BeatPlansManager beatPlansManager = null;
    HttpSession session = null;
    List<HubDetails> hubDetailsList = null;
    Long zoneId = 0L;
    StringBuffer hubsOptionsString = new StringBuffer();
    
    try {
      session = request.getSession(false);
      
      if (session != null && session.getAttribute(USER_ID) != null && ((Long) session.getAttribute(USER_ID)).longValue() > 0) {
        beatPlansManager = (BeatPlansManager) getBean(BEAT_PLANS_MANAGER);
        hubDetailsList = new ArrayList<>();

        if (beatPlansManager != null) {
          zoneId = Long.parseLong(request.getParameter("selectedId"));
          if (zoneId != null && zoneId != 0L) {
            hubDetailsList = beatPlansManager.getHubs(Long.parseLong(request.getParameter("selectedId")));
            if (hubDetailsList != null) {
              for (int i = 0; i < hubDetailsList.size(); i++) {
                hubsOptionsString.append("<option value=" + hubDetailsList.get(i).getHubId() + ">");
                hubsOptionsString.append(hubDetailsList.get(i).getHubName());
                hubsOptionsString.append("</option>");
              }
            }
          }
        }
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println(hubsOptionsString);
        out.flush();
        out.close();
      } else {
        return mapping.findForward(SESSION_INACTIVE_PAGE);
      }
    } catch (Exception e) {
      log.error("Problem in the ActionForward getHubsList");
      log.error(e);
      ActionMessages errors = new ActionMessages();
      saveErrors(request, errors);
      throw e;
    }
    log.info("END of the ActionForward showTopRunnersReportPage");
    return mapping.findForward(ADD_DISTRIBUTOR_BEAT_PLAN_PAGE);
  }

  // =========================================================================
  
  public ActionForward getDistributorsList(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward showTopRunnersReportPage");
    BeatPlansManager beatPlansManager = null;
    HttpSession session = null;
    List<DistributorDetails> distributorDetailsList = null;
    Long hubId = 0L;
    StringBuffer stringBufferResult = new StringBuffer();
    
    try {
      session = request.getSession(false);
      
      if (session != null && session.getAttribute(USER_ID) != null && ((Long) session.getAttribute(USER_ID)).longValue() > 0) {
        beatPlansManager = (BeatPlansManager) getBean(BEAT_PLANS_MANAGER);
        distributorDetailsList = new ArrayList<>();

        if (beatPlansManager != null) {
          hubId = Long.parseLong(request.getParameter("selectedId"));
          if (hubId != null && hubId != 0L) {
            distributorDetailsList = beatPlansManager.getDistributorsOfHub(Long.parseLong(request.getParameter("selectedId")));
            if (distributorDetailsList != null) {
              for (int i = 0; i < distributorDetailsList.size(); i++) {
                stringBufferResult.append("<option value=" + distributorDetailsList.get(i).getDistributorId() + ">");
                stringBufferResult.append(distributorDetailsList.get(i).getDistributorName());
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
      } else {
        return mapping.findForward(SESSION_INACTIVE_PAGE);
      }
    } catch (Exception e) {
      log.error("Problem in the ActionForward getDistributorsList");
      log.error(e);
      ActionMessages errors = new ActionMessages();
      saveErrors(request, errors);
      throw e;
    }
    log.info("END of the ActionForward showTopRunnersReportPage");
    return mapping.findForward(ADD_DISTRIBUTOR_BEAT_PLAN_PAGE);
  }

  // =========================================================================
  
  public void getBeatPlanDetailsList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    log.info("START of the ActionForward showAddDistributorBeatPlanPage");
    BeatPlansManager beatPlansManager = null;
    HttpSession session = null;
    Long requestedUserId;
    Long requestedRoleId;
    List<DistributorBeatPlansDetails> distributorBeatPlansDetails = null;
    
    try {
      beatPlansManager = (BeatPlansManager) getBean(BEAT_PLANS_MANAGER);
      distributorBeatPlansDetails = new ArrayList<>();
      session = request.getSession(false);
      requestedUserId = (Long) session.getAttribute(USER_ID);
      requestedRoleId = (Long) session.getAttribute(ROLE_ID);
      distributorBeatPlansDetails = beatPlansManager.getDistributorBeatPlans(requestedUserId, requestedRoleId);
      org.json.JSONArray jsonArray = new org.json.JSONArray();
      
      if (distributorBeatPlansDetails != null) {
        for (int i = 0; i < distributorBeatPlansDetails.size(); i++) {
          JSONObject object = new JSONObject();
          object.put("beatPlanId", distributorBeatPlansDetails.get(i).getBeatPlanId());
          object.put("beatPlanCode", distributorBeatPlansDetails.get(i).getBeatPlanCode());
          object.put("beatPlanName", distributorBeatPlansDetails.get(i).getBeatPlanName());
          object.put("circleName", distributorBeatPlansDetails.get(i).getCircleName());
          object.put("regionName", distributorBeatPlansDetails.get(i).getRegionName());
          object.put("zoneName", distributorBeatPlansDetails.get(i).getZoneName());
          object.put("hubName", distributorBeatPlansDetails.get(i).getHubName());
          object.put("isBeatPlanAssigned", String.valueOf(!distributorBeatPlansDetails.get(i).isAllowDelete()));
          jsonArray.put(object);
        }
      }
      response.setContentType("application/json");
      response.getWriter().print(jsonArray);

    } catch (Exception e) {
      log.error("Problem in the ActionForward showAddDistributorBeatPlanPage");
      log.error(e);
      ActionMessages errors = new ActionMessages();
      saveErrors(request, errors);
      throw e;
    }
    log.info("END of the ActionForward showAddDistributorBeatPlanPage");
  }

  // =========================================================================
  
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public ActionForward saveDistributorBeatPlan(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    List<BeatPlanDistributorAssignments> beatPlanDistributorAssignmentsList = null;
    BeatPlanDistributorAssignments beatPlanDistributorAssignments = null;
    HttpSession session = null;
    BeatPlansForm beatPlansForm = null;
    BeatPlansManager beatPlansManager = null;
    UserManager userManager = null;
    DistributorManager distributorManager = null;
    UserDetails userDetails = null;
    Long loggedInUserId = 0L;
    Map<String, Object> distributorBeatPlanDetailsMap = null;
    Set beatPlanDistributorAssignmentses = null;
    BeatPlanDetails beatPlanDetails = null;
    Map distributorDetailsMap = null;
    DistributorDetails distributorDetails = null;
    Map resultMap = null;
    
    try {
      session = request.getSession(false);
      
      if (session != null && session.getAttribute(USER_ID) != null && ((Long) session.getAttribute(USER_ID)).longValue() > 0) {
        if (isTokenValid(request)) {
          beatPlansForm = (BeatPlansForm) form;
          log.info("Method Called");
          String jsonResponse = beatPlansForm.getBeatPlanData();
          beatPlansManager = (BeatPlansManager) getBean(BEAT_PLANS_MANAGER);
          userManager = (UserManager) getBean(USER_MANAGER);
          distributorManager = (DistributorManager) getBean(DISTRIBUTOR_MANAGER);
          loggedInUserId = (Long) session.getAttribute(USER_ID);
          userDetails = userManager.getUserDetails(loggedInUserId);
          beatPlanDistributorAssignmentsList = new ArrayList<BeatPlanDistributorAssignments>();
          JSONArray jsonArray = JSONArray.fromObject(jsonResponse);

          Collection<Map<String, String>> collection = JSONArray.toCollection(jsonArray, HashMap.class);
          for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
            Map<String, String> mapObject = (Map<String, String>) iterator.next();
            distributorBeatPlanDetailsMap = new HashMap();
            beatPlanDistributorAssignmentses = new HashSet();
            beatPlanDistributorAssignments = new BeatPlanDistributorAssignments();
            beatPlanDetails = new BeatPlanDetails();
            distributorDetailsMap = distributorManager.getDistributorDetails(Long.parseLong(String.valueOf(mapObject.get("distributorId"))));
            distributorDetails = (DistributorDetails) distributorDetailsMap.get(DISTRIBUTOR_DETAILS);
            beatPlanDistributorAssignments.setDistributorDetails(distributorDetails);
            beatPlanDistributorAssignments.setDistributorDetails(distributorDetails);
            beatPlanDistributorAssignments.setDateTime(new Date());
            beatPlanDistributorAssignments.setStatus(ACTIVE);
            beatPlanDistributorAssignments.setVisitFrequency(mapObject.get("visitFrequency"));
            beatPlanDistributorAssignmentses.add(distributorDetails);
            beatPlanDistributorAssignmentses.add(beatPlanDistributorAssignments);
            beatPlanDetails.setBeatPlanName(mapObject.get("beatPlanName"));
            beatPlanDetails.setBeatPlanCode(UniqueCodeGenerator.getInstance().getBeatPlanCode());
            beatPlanDetails.setUserDetails(userDetails);
            beatPlanDetails.setBeatPlanType("1");
            beatPlanDetails.setBeatPlanDistributorAssignmentses(beatPlanDistributorAssignmentses);
            beatPlanDetails.setStatus(ACTIVE);
            beatPlanDetails.setDateTime(new Date());
            beatPlanDistributorAssignmentsList.add(beatPlanDistributorAssignments);
            distributorBeatPlanDetailsMap.put(BEAT_PLAN_TO_DISTRIBUTOR_DETAILS, beatPlanDistributorAssignmentsList);
            distributorBeatPlanDetailsMap.put(BEAT_PLAN_DETAILS, beatPlanDetails);
          }
          resultMap = beatPlansManager.addDistributorBeatPlan(distributorBeatPlanDetailsMap);
          
          if (resultMap.get(RESULT) != null && resultMap.get(RESULT).equals(SUCCESS)) {
            log.info(ADD_DISTRIBUTOR_BEAT_PLAN_SUCCESS_MESSAGE);
            request.setAttribute(SUCCESS_MESSAGE, ADD_DISTRIBUTOR_BEAT_PLAN_SUCCESS_MESSAGE);
          } else if (resultMap.get(ERROR_MESSAGE) != null) {
            request.setAttribute(ERROR_MESSAGE, resultMap.get(ERROR_MESSAGE));
          }
          resetToken(request);
          saveToken(request);
        } else {

        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      ActionMessages errors = new ActionMessages();
      saveErrors(request, errors);
      throw e;
    }
    return mapping.findForward(MANAGE_BEAT_PLANS_PAGE);
  }

  // =========================================================================
 
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public ActionForward updateDistributorBeatPlan(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    Map updatedDistributorBeatPlanDetailsMap = null;
    BeatPlansForm beatPlansForm = null;
    Long beatPlanId = null;
    BeatPlansManager beatPlansManager = null;
    BeatPlanDetails beatPlanDetails = null;
    List beatPlanDistributorAssignmentsList = null;
    Map distributorDetailsMap = null;
    DistributorManager distributorManager = null;
    DistributorDetails distributorDetails = null;
    BeatPlanDistributorAssignments beatPlanDistributorAssignments = null;
    Map resultMap = null;
    HttpSession session = null;
    
    try {
      session = request.getSession(false);
      beatPlansForm = (BeatPlansForm) form;
      beatPlansManager = (BeatPlansManager) getBean(BEAT_PLANS_MANAGER);
      distributorManager = (DistributorManager) getBean(DISTRIBUTOR_MANAGER);
      String jsonResponse = beatPlansForm.getBeatPlanData();
      JSONArray jsonArray = JSONArray.fromObject(jsonResponse);
      Collection<Map<String, String>> collection = JSONArray.toCollection(jsonArray, HashMap.class);
      beatPlanDistributorAssignmentsList = new ArrayList();
      
      if (beatPlanId == null) {
        beatPlanId = Long.parseLong(session.getAttribute("selectedbeatplanid").toString());
      }
      updatedDistributorBeatPlanDetailsMap = beatPlansManager.getDistributorBeatPlan(beatPlanId);
      
      if (updatedDistributorBeatPlanDetailsMap != null) {
        beatPlanDetails = (BeatPlanDetails) updatedDistributorBeatPlanDetailsMap.get(BEAT_PLAN_DETAILS);
      }
      
      for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
        Map<String, String> mapObject = (Map<String, String>) iterator.next();
        
        if (updatedDistributorBeatPlanDetailsMap != null) {
          beatPlanDistributorAssignments = new BeatPlanDistributorAssignments();
          distributorDetailsMap = distributorManager.getDistributorDetails(Long.parseLong(String.valueOf(mapObject.get("distributorId"))));
          distributorDetails = (DistributorDetails) distributorDetailsMap.get(DISTRIBUTOR_DETAILS);
          beatPlanDistributorAssignments.setDistributorDetails(distributorDetails);
          beatPlanDistributorAssignments.setVisitFrequency(mapObject.get("visitFrequency"));
          beatPlanDistributorAssignments.setDateTime(new Date());
          
          if (mapObject.get("status").toString().equalsIgnoreCase("active")) {
            beatPlanDistributorAssignments.setStatus(ACTIVE);
          } else {
            beatPlanDistributorAssignments.setStatus(IN_ACTIVE);
          }
          beatPlanDistributorAssignments.setBpDistAssignmentsId(Long.parseLong(String.valueOf(mapObject.get("beatPlanDistributorAssId"))));
          beatPlanDistributorAssignmentsList.add(beatPlanDistributorAssignments);
        }
      }
      updatedDistributorBeatPlanDetailsMap.put(BEAT_PLAN_TO_DISTRIBUTOR_DETAILS, beatPlanDistributorAssignmentsList);
      updatedDistributorBeatPlanDetailsMap.put(BEAT_PLAN_DETAILS, beatPlanDetails);
      resultMap = beatPlansManager.updateDistributorBeatPlan(updatedDistributorBeatPlanDetailsMap);

      if (resultMap.get(RESULT) != null && resultMap.get(RESULT).equals(SUCCESS)) {
        request.setAttribute(SUCCESS_MESSAGE, EDIT_DISTRIBUTOR_BEAT_PLAN_SUCCESS_MESSAGE);
      } else if (resultMap.get(ERROR_MESSAGE) != null) {
        request.setAttribute(ERROR_MESSAGE, resultMap.get(ERROR_MESSAGE));
      }
      session.removeAttribute("selectedbeatplanid");
      session.removeAttribute("beatplandetailsjson");
    } catch (Exception e) {
      e.printStackTrace();
      ActionMessages errors = new ActionMessages();
      saveErrors(request, errors);
      throw e;
    }
    log.info("END of the ActionForward updateDistributorBeatPlan");
    return mapping.findForward(MANAGE_BEAT_PLANS_PAGE);
  }

  // =========================================================================
  
  @SuppressWarnings("unchecked")
  public ActionForward editDistributorBeatPlan(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward editDistributorBeatPlan");
    BeatPlansForm beatPlansform = null;
    BeatPlansManager beatPlansManager = null;
    HttpSession session = null;
    Map<String, Object> distributorBeatPlansDetailsMap = null;
    List<BeatPlanDistributorAssignments> beatPlanDistributorAssignmentsList = null;
    BeatPlanDistributorAssignments beatPlanDistributorAssignments = null;
    BeatPlanDetails beatPlanDetails = null;
    String viewType = null;

    try {
      session = request.getSession(false);
      
      if (session != null && session.getAttribute(USER_ID) != null && ((Long) session.getAttribute(USER_ID)).longValue() > 0) {
        beatPlansManager = (BeatPlansManager) getBean(BEAT_PLANS_MANAGER);
        viewType = request.getParameter("viewType");
        beatPlansform = (BeatPlansForm) form;
        distributorBeatPlansDetailsMap = beatPlansManager.getDistributorBeatPlan(Long.parseLong(request.getParameter("beat_plan_id")));
        
        if (distributorBeatPlansDetailsMap != null) {
          session.setAttribute("selectedbeatplanid", Long.parseLong(request.getParameter("beat_plan_id")));
          org.json.JSONArray jsonArray = new org.json.JSONArray();
          beatPlanDetails = (BeatPlanDetails) distributorBeatPlansDetailsMap.get(BEAT_PLAN_DETAILS);
          beatPlanDistributorAssignmentsList = (List<BeatPlanDistributorAssignments>) distributorBeatPlansDetailsMap.get(BEAT_PLAN_TO_DISTRIBUTOR_DETAILS);
          
          if (beatPlanDistributorAssignmentsList.size() > 0) {
            for (int i = 0; i < beatPlanDistributorAssignmentsList.size(); i++) {
              beatPlanDistributorAssignments = beatPlanDistributorAssignmentsList.get(i);
              beatPlansform.setBeatPlanName(beatPlanDetails.getBeatPlanName());

              beatPlansform.setCircleDetailsList(beatPlansManager.getCircles());
              beatPlansform.setCircleId((Long) distributorBeatPlansDetailsMap.get(CIRCLE_ID));

              beatPlansform.setRegionDetailsList(beatPlansManager.getRegions((Long) distributorBeatPlansDetailsMap.get(CIRCLE_ID)));
              beatPlansform.setRegionId((Long) (distributorBeatPlansDetailsMap.get(REGION_ID)));

              beatPlansform.setZoneDetailsList(beatPlansManager.getZones((Long) (distributorBeatPlansDetailsMap.get(REGION_ID))));
              beatPlansform.setZoneId((Long) (distributorBeatPlansDetailsMap.get(ZONE_ID)));

              beatPlansform.setHubDetailsList(beatPlansManager.getHubs((Long) (distributorBeatPlansDetailsMap.get(ZONE_ID))));
              beatPlansform.setHubId((Long) (distributorBeatPlansDetailsMap.get(HUB_ID)));

              beatPlansform.setDistributorDetailsList(beatPlansManager.getDistributorsOfHub((Long) (distributorBeatPlansDetailsMap.get(HUB_ID))));

              JSONObject object = new JSONObject();
              object.put("beatPlanName", beatPlanDetails.getBeatPlanName());
              object.put("beatPlanId", beatPlanDetails.getBeatPlanId());
              object.put("visitFrequency", beatPlanDistributorAssignments.getVisitFrequency());
              object.put("distributorName", beatPlanDistributorAssignments.getDistributorDetails().getDistributorName());
              object.put("distributorId", beatPlanDistributorAssignments.getDistributorDetails().getDistributorId());
              object.put("beatPlanDistributorAssId", beatPlanDistributorAssignments.getBpDistAssignmentsId());
              object.put("isBeatPlanAssigned", String.valueOf(beatPlansManager.isRunnerBeatPlanAssigned(beatPlanDistributorAssignments.getBpDistAssignmentsId())));
              object.put("circleName", distributorBeatPlansDetailsMap.get(CIRCLE_NAME));
              object.put("regionsName", distributorBeatPlansDetailsMap.get(REGION_NAME));
              object.put("zoneName", distributorBeatPlansDetailsMap.get(ZONE_NAME));
              object.put("hubName", distributorBeatPlansDetailsMap.get(HUB_NAME));
              object.put("circleId", distributorBeatPlansDetailsMap.get(CIRCLE_ID));
              object.put("hubId", distributorBeatPlansDetailsMap.get(HUB_ID));
              object.put("regionId", distributorBeatPlansDetailsMap.get(REGION_ID));
              object.put("zoneId", distributorBeatPlansDetailsMap.get(ZONE_ID));
              object.put("type", "edit");
              object.put("status", "active");
              jsonArray.put(object);
            }
          }
          session.setAttribute("beatplandetailsjson", jsonArray);
          session.setAttribute("viewType", viewType);
          response.setContentType("application/json");
        }
      } else {
        return mapping.findForward(SESSION_INACTIVE_PAGE);
      }
    } catch (Exception e) {
      e.printStackTrace();
      ActionMessages errors = new ActionMessages();
      saveErrors(request, errors);
      throw e;
    }
    log.info("END of the ActionForward editDistributorBeatPlan");
    return mapping.findForward(ADD_DISTRIBUTOR_BEAT_PLAN_PAGE);
  }

  // =========================================================================
  
  public ActionForward deleteDistributorBeatPlan(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward deleteBeatPlan");
    BeatPlansManager beatManager = null;
    Long requestedBeatPlanId = 0L;
    String result = null;
    String forwardPage = null;
    HttpSession session = null;
    
    try {
      session = request.getSession(false);
      
      if (session != null && session.getAttribute(USER_ID) != null && ((Long) session.getAttribute(USER_ID)).longValue() > 0) {
        beatManager = (BeatPlansManager) getBean(BEAT_PLANS_MANAGER);
        
        if (request.getParameter("beat_plan_id") != null) {
          requestedBeatPlanId = Long.parseLong(request.getParameter("beat_plan_id"));
          if (requestedBeatPlanId != 0) {
            if (beatManager != null) {
              result = beatManager.deleteDistributorBeatPlan(requestedBeatPlanId);
              if (result != null && result.equals(SUCCESS)) {
                log.info(DELETE_DISTRIBUTOR_BEAT_PLAN_SUCCESS_MESSAGE);
                request.setAttribute(SUCCESS_MESSAGE, DELETE_DISTRIBUTOR_BEAT_PLAN_SUCCESS_MESSAGE);
              } else {
                request.setAttribute(ERROR_MESSAGE, "Problem in Deleting Beat Plan, Please try Again!");
              }
            }
          }
        }

        if (SUCCESS.equals(result)) {
          forwardPage = MANAGE_BEAT_PLANS_PAGE;
        }
      } else {
        return mapping.findForward(SESSION_INACTIVE_PAGE);
      }
    } catch (Exception e) {
      log.error("Problem in the ActionForward deleteBeatPlan");
      log.error(e);
      ActionMessages errors = new ActionMessages();
      saveErrors(request, errors);
      throw e;
    }
    log.info("END of the ActionForward deleteBeatPlan");
    return mapping.findForward(forwardPage);
  }

  // =========================================================================
  
  public void getRunnerBeatPlanDetailsList(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward getRunnerBeatPlanDetailsList");
    BeatPlansManager beatPlansManager = null;
    HttpSession session = null;
    Long requestedUserId;
    Long requestedRoleId;
    RunnerBeatPlansDetails runnerBeatPlansDetails = null;
    List<RunnerBeatPlansDetails> runnerBeatPlansDetailsList = null;
    
    try {
      beatPlansManager = (BeatPlansManager) getBean(BEAT_PLANS_MANAGER);
      runnerBeatPlansDetailsList = new ArrayList<>();
      session = request.getSession(false);
      requestedUserId = (Long) session.getAttribute(USER_ID);
      requestedRoleId = (Long) session.getAttribute(ROLE_ID);
      runnerBeatPlansDetailsList = beatPlansManager.getRunnerBeatPlans(requestedUserId, requestedRoleId);
      org.json.JSONArray jsonArray = new org.json.JSONArray();
      
      if (runnerBeatPlansDetailsList != null) {
        for (int i = 0; i < runnerBeatPlansDetailsList.size(); i++) {
          JSONObject object = new JSONObject();
          runnerBeatPlansDetails = (RunnerBeatPlansDetails) runnerBeatPlansDetailsList.get(i);
          object.put("beatPlanId", runnerBeatPlansDetails.getBeatPlanId());
          object.put("beatPlanCode", runnerBeatPlansDetails.getBeatPlanCode());
          object.put("beatPlanName", runnerBeatPlansDetails.getBeatPlanName());
          object.put("circleName", runnerBeatPlansDetails.getCircleName());
          object.put("hubName", runnerBeatPlansDetails.getHubName());
          object.put("regionName", runnerBeatPlansDetails.getRegionName());
          object.put("zoneName", runnerBeatPlansDetails.getZoneName());
          jsonArray.put(object);
        }
      }
      response.setContentType("application/json");
      response.getWriter().print(jsonArray);

    } catch (Exception e) {
      log.error("Problem in the ActionForward getRunnerBeatPlanDetailsList");
      log.error(e);
      ActionMessages errors = new ActionMessages();
      saveErrors(request, errors);
      throw e;
    }
    log.info("END of the ActionForward getRunnerBeatPlanDetailsList");
  }

  // =========================================================================
  
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public ActionForward saveRunnerBeatPlan(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward saveRunnerBeatPlan");
    HttpSession session = null;
    BeatPlansForm beatPlansForm = null;
    BeatPlansManager beatPlansManager = null;
    List beatPlanUserAssignmentsList = null;
    BeatPlanUserAssignments beatPlanUserAssignments = null;
    BeatPlanDetails beatPlanDetails = null;
    Map runnerBeatPlanDetailsMap = null;
    UserDetails userDetails = null;
    Date schedule = null;
    String beatPlanName = null;
    Long beatPlanId = 0L;
    Long runnersId = 0L;
    Map resultMap = null;

    try {
      session = request.getSession(false);
      
      if (session != null && session.getAttribute(USER_ID) != null && ((Long) session.getAttribute(USER_ID)).longValue() > 0) {
        if (isTokenValid(request)) {
          beatPlansForm = (BeatPlansForm) form;
          beatPlansManager = (BeatPlansManager) getBean(BEAT_PLANS_MANAGER);

          beatPlanUserAssignmentsList = new ArrayList<>();
          //String mode = null;
          String jsonResponse = beatPlansForm.getRunnerBeatPlanData().toString();
          JSONArray jsonArray = JSONArray.fromObject(jsonResponse);
          beatPlanDetails = new BeatPlanDetails();
          Collection<Map<String, String>> collection = JSONArray.toCollection(jsonArray, HashMap.class);

          for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
            Map<String, String> mapObject = (Map<String, String>) iterator.next();
            runnersId = Long.parseLong(String.valueOf(mapObject.get("runnersId")));
            beatPlanName = mapObject.get("beatPlanName");
            beatPlanId = Long.parseLong(String.valueOf(mapObject.get("beatPlanId")));
            runnerBeatPlanDetailsMap = new HashMap();
            beatPlanDetails = new BeatPlanDetails();
            userDetails = new UserDetails();
            userDetails.setUserId(runnersId);
            beatPlanDetails.setBeatPlanName(beatPlanName);
            beatPlanDetails.setUserDetails(userDetails);
            beatPlanDetails.setBeatPlanType("1");
            beatPlanDetails.setBeatPlanId(beatPlanId);
            beatPlanDetails.setStatus(ACTIVE);
            beatPlanDetails.setDateTime(new Date());

            SimpleDateFormat f = new SimpleDateFormat(TIME_FORMAT);
            schedule = f.parse(mapObject.get("schedule"));

            beatPlanUserAssignments = new BeatPlanUserAssignments();
            beatPlanUserAssignments.setDistributorId(Long.parseLong(String.valueOf(mapObject.get("distributorId"))));
            beatPlanUserAssignments.setScheduleTime(schedule);
            beatPlanUserAssignments.setStatus(ACTIVE);
            beatPlanUserAssignments.setUserDetails(userDetails);
            beatPlanUserAssignments.setVisitNo(String.valueOf(mapObject.get("visitNo")));
            beatPlanUserAssignmentsList.add(beatPlanUserAssignments);

            runnerBeatPlanDetailsMap.put(BEAT_PLAN_DETAILS, beatPlanDetails);
            runnerBeatPlanDetailsMap.put(BEAT_PLAN_TO_USER_DETAILS, beatPlanUserAssignmentsList);
          }
          
          if (runnerBeatPlanDetailsMap != null && beatPlanUserAssignmentsList != null) {
            resultMap = beatPlansManager.addRunnerBeatPlan(runnerBeatPlanDetailsMap);

            if (resultMap.get(RESULT) != null && resultMap.get(RESULT).equals(SUCCESS)) {
              log.info(ADD_RUNNER_BEAT_PLAN_SUCCESS_MESSAGE);
              request.setAttribute(SUCCESS_MESSAGE, ADD_RUNNER_BEAT_PLAN_SUCCESS_MESSAGE);
            } else if (resultMap.get(ERROR_MESSAGE) != null) {
              request.setAttribute(ERROR_MESSAGE, resultMap.get(ERROR_MESSAGE));
            }
          }
          resetToken(request);
          saveToken(request);
        }
      }
    } catch (Exception e) {
      log.error("Problem in the ActionForward saveRunnerBeatPlan");
      log.error(e);
      ActionMessages errors = new ActionMessages();
      saveErrors(request, errors);
      throw e;
    }
    log.info("END of the ActionForward saveRunnerBeatPlan");
    return mapping.findForward(MANAGE_RUNNER_BEAT_PLANS_PAGE);
  }

  // ======================UPDATE RUNNER BEAT PLAN==============
  
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public ActionForward updateRunnerBeatPlan(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward updateRunnerBeatPlan");
    HttpSession session = null;
    BeatPlansForm beatPlansForm = null;
    BeatPlanDetails beatPlanDetails = null;
    BeatPlansManager beatPlansManager = null;
    UserDetails userDetails = null;
    BeatPlanUserAssignments beatPlanUserAssignments = null;
    Map<String, Object> runnerBeatPlanDetailsMap = null;
    List<BeatPlanUserAssignments> updatedBeatPlanUserAssignmentsList = null;
    Map resultMap = null;
    Long beatPlanId = null;

    try {
      session = request.getSession(false);

      if (session != null && session.getAttribute(USER_ID) != null && ((Long) session.getAttribute(USER_ID)).longValue() > 0) {
        beatPlansForm = (BeatPlansForm) form;
        updatedBeatPlanUserAssignmentsList = new ArrayList<BeatPlanUserAssignments>();
        beatPlansManager = (BeatPlansManager) getBean(BEAT_PLANS_MANAGER);

        if (session.getAttribute("selectedbeatplanid") != null) {
          beatPlanId = Long.parseLong(session.getAttribute("selectedbeatplanid").toString());
        }
        if (beatPlanId != null && beatPlanId.longValue() > 0) {
          runnerBeatPlanDetailsMap = beatPlansManager.getRunnerBeatPlan(beatPlanId);

          if (runnerBeatPlanDetailsMap != null && !(runnerBeatPlanDetailsMap.isEmpty())) {
            beatPlanDetails = (BeatPlanDetails) runnerBeatPlanDetailsMap.get(BEAT_PLAN_DETAILS);
            String jsonResponse = beatPlansForm.getRunnerBeatPlanData().toString();
            JSONArray jsonArray = JSONArray.fromObject(jsonResponse);

            SimpleDateFormat f = new SimpleDateFormat("hh:mm");
            Collection<Map<String, String>> collection = JSONArray.toCollection(jsonArray, HashMap.class);
            
            for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
              Map<String, String> mapObject = (Map<String, String>) iterator.next();
              userDetails = new UserDetails();
              userDetails.setUserId(Long.parseLong(String.valueOf(mapObject.get("runnersId"))));
              beatPlanUserAssignments = new BeatPlanUserAssignments();
              beatPlanUserAssignments.setBpUserAssignmentId(Long.parseLong(String.valueOf(mapObject.get("bpuid"))));
              beatPlanUserAssignments.setUserDetails(userDetails);
              beatPlanUserAssignments.setVisitNo(String.valueOf(mapObject.get("visitNo")));
              beatPlanUserAssignments.setDistributorId(Long.parseLong(String.valueOf(mapObject.get("distributorId"))));
              if (mapObject.get("status").toString().equalsIgnoreCase("active")) {
                beatPlanUserAssignments.setStatus(ACTIVE);
              } else {
                beatPlanUserAssignments.setStatus(IN_ACTIVE);
              }
              beatPlanUserAssignments.setScheduleTime(f.parse(mapObject.get("schedule")));
              updatedBeatPlanUserAssignmentsList.add(beatPlanUserAssignments);
            }
            runnerBeatPlanDetailsMap.put(BEAT_PLAN_DETAILS, beatPlanDetails);
            runnerBeatPlanDetailsMap.put(BEAT_PLAN_TO_USER_DETAILS, updatedBeatPlanUserAssignmentsList);

            if (runnerBeatPlanDetailsMap != null) {
              resultMap = beatPlansManager.updateRunnerBeatPlan(runnerBeatPlanDetailsMap);
              if (resultMap.get(RESULT) != null && resultMap.get(RESULT).equals(SUCCESS)) {
                log.info("Updating Runner Beat Plan success");
                request.setAttribute(SUCCESS_MESSAGE, EDIT_RUNNER_BEAT_PLAN_SUCCESS_MESSAGE);
              } else if (resultMap.get(ERROR_MESSAGE) != null) {
                request.setAttribute(ERROR_MESSAGE, resultMap.get(ERROR_MESSAGE));
              }
              session.removeAttribute("selectedbeatplanid");
              session.removeAttribute("runnerBeatplandetailsjson");
            }
          }
        }
      } else {
        return mapping.findForward(SESSION_INACTIVE_PAGE);
      }
    } catch (Exception e) {
      log.error("Problem in the ActionForward updateRunnerBeatPlan");
      e.printStackTrace();
      ActionMessages errors = new ActionMessages();
      saveErrors(request, errors);
      throw e;
    }
    log.info("END of the ActionForward updateRunnerBeatPlan");
    return mapping.findForward(MANAGE_RUNNER_BEAT_PLANS_PAGE);
  }

  // =========================================================================
  
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public ActionForward editRunnerBeatPlan(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward editRunnerBeatPlan");
    BeatPlansForm beatPlansform = null;
    BeatPlansManager beatPlansManager = null;
    HttpSession session = null;
    String viewType = null;
    Long beatPlanId = 0L;
    BeatPlanDetails beatPlanDetails = null;
    Map<String, Object> runnerBeatPlanDetailsMap;
    List<BeatPlanUserAssignments> bpuaList = null;
    BeatPlanUserAssignments beatPlanUserAssignments = null;
    Map<String, Object> distributorandRunnersMap = null;
    Long requestedUserId;
    Long requestedRoleId;
    List<UserDetails> runners = null;
    List<DistributorDetails> distributorsOfHub = null;
    
    try {
      session = request.getSession(false);
      if (session != null && session.getAttribute(USER_ID) != null && ((Long) session.getAttribute(USER_ID)).longValue() > 0) {
        beatPlansform = (BeatPlansForm) form;
        beatPlansManager = (BeatPlansManager) getBean(BEAT_PLANS_MANAGER);
        requestedUserId = (Long) session.getAttribute(USER_ID);
        requestedRoleId = (Long) session.getAttribute(ROLE_ID);
        viewType = request.getParameter("viewType");
        beatPlanId = Long.parseLong(request.getParameter("beat_plan_id"));
        session.setAttribute("selectedbeatplanid", beatPlanId);
        beatPlansform.setBeatPlanDetailsList(beatPlansManager.getBeatPlans(requestedUserId, requestedRoleId));
        beatPlansform.setBeatPlanId(beatPlanId);
        distributorandRunnersMap = beatPlansManager.getDistributorsAndRunners(beatPlanId);
        distributorsOfHub = (List) distributorandRunnersMap.get(DISTRIBUTORS);
        beatPlansform.setDistributorDetailsList(distributorsOfHub);

        runners = (List) distributorandRunnersMap.get(RUNNERS);
        beatPlansform.setRunnerDetailsList(runners);

        runnerBeatPlanDetailsMap = beatPlansManager.getRunnerBeatPlan(beatPlanId);
        org.json.JSONArray jsonArray = new org.json.JSONArray();
        SimpleDateFormat f = new SimpleDateFormat("HH:mm");

        if (runnerBeatPlanDetailsMap != null) {
          beatPlanDetails = (BeatPlanDetails) runnerBeatPlanDetailsMap.get(BEAT_PLAN_DETAILS);
          bpuaList = (List) runnerBeatPlanDetailsMap.get(BEAT_PLAN_TO_USER_DETAILS);

          if (bpuaList != null && bpuaList.size() > 0) {
            for (int i = 0; i < bpuaList.size(); i++) {
              JSONObject jsonObject = new JSONObject();
              beatPlanUserAssignments = bpuaList.get(i);
              beatPlansform.setScheduleStartTime(beatPlanUserAssignments.getScheduleTime().toString());
              jsonObject.put("beatPlanName", beatPlanDetails.getBeatPlanName());
              jsonObject.put("beatPlanId", beatPlanDetails.getBeatPlanId());
              jsonObject.put("runnersName",  beatPlanUserAssignments.getUserDetails().getName()+" "+ beatPlanUserAssignments.getUserDetails().getLastName());
              jsonObject.put("runnersId", beatPlanUserAssignments.getUserDetails().getUserId());
              jsonObject.put("visitNo", beatPlanUserAssignments.getVisitNo());
              jsonObject.put("schedule", f.format(beatPlanUserAssignments.getScheduleTime()));
              jsonObject.put("type", "edit");
              jsonObject.put("status", "active");
              jsonObject.put("distributorsName", beatPlanUserAssignments.getDistributorName());
              jsonObject.put("distributorId", beatPlanUserAssignments.getDistributorId());
              jsonObject.put("bpuid", beatPlanUserAssignments.getBpUserAssignmentId());
              jsonArray.put(jsonObject);
            }
          }
        }
        session = request.getSession(false);
        session.setAttribute("runnerBeatplandetailsjson", jsonArray);
        session.setAttribute("viewType", viewType);
        response.setContentType("application/json");
        // response.getWriter().print(jsonArray);
      } else {
        return mapping.findForward(SESSION_INACTIVE_PAGE);
      }
    } catch (Exception e) {
      log.error("Problem in the ActionForward editRunnerBeatPlan");
      log.error(e);
      ActionMessages errors = new ActionMessages();
      saveErrors(request, errors);
      throw e;
    }
    log.info("END of the ActionForward editRunnerBeatPlan");
    return mapping.findForward(ADD_RUNNER_BEAT_PLAN_PAGE);
  }

  // =========================================================================
  
  public ActionForward deleteRunnerBeatPlan(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward deleteRunnerBeatPlan");
    BeatPlansManager beatManager = null;
    Long requestedBeatPlanId = 0L;
    String result = null;
    String forwardPage = null;
    HttpSession session = null;
    
    try {
      session = request.getSession(false);
      
      if (session != null && session.getAttribute(USER_ID) != null && ((Long) session.getAttribute(USER_ID)).longValue() > 0) {
        beatManager = (BeatPlansManager) getBean(BEAT_PLANS_MANAGER);
        if (request.getParameter("beat_plan_id") != null) {
          requestedBeatPlanId = Long.parseLong(request.getParameter("beat_plan_id"));
          if (requestedBeatPlanId != 0) {
            if (beatManager != null) {
              result = beatManager.deleteRunnerBeatPlan(requestedBeatPlanId);
              if (result != null && result.equals(SUCCESS)) {
                log.info(DELETE_RUNNER_BEAT_PLAN_SUCCESS_MESSAGE);
                request.setAttribute(SUCCESS_MESSAGE, DELETE_RUNNER_BEAT_PLAN_SUCCESS_MESSAGE);
                forwardPage = MANAGE_RUNNER_BEAT_PLANS_PAGE;
              } else {
                request.setAttribute(ERROR_MESSAGE, "Problem in Deleting Beat Plan, Please try Again!");
              }
            }
          }
        }
      } else {
        return mapping.findForward(SESSION_INACTIVE_PAGE);
      }
    } catch (Exception e) {
      log.error("Problem in the ActionForward deleteRunnerBeatPlan");
      log.error(e);
      ActionMessages errors = new ActionMessages();
      saveErrors(request, errors);
      throw e;
    }
    log.info("END of the ActionForward deleteRunnerBeatPlan");
    return mapping.findForward(forwardPage);
  }

  // =========================================================================
  
  @SuppressWarnings("unchecked")
  public ActionForward getDistributorsAndRunnersList(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward getDistributorsAndRunnersList");
    BeatPlansForm beatPlansForm = null;
    BeatPlansManager beatPlansManager = null;
    HttpSession session = null;
    Long beatPlanId = 0L;
    StringBuffer distributorBufferResult = new StringBuffer();
    StringBuffer runnerBufferResult = new StringBuffer();
    Map<String, Object> distributorandRunnersMap = null;
    List<DistributorDetails> distributorsOfHub = null;
    List<UserDetails> runners = null;
    String selectType;
    
    try {
      session = request.getSession(false);
      
      if (session != null && session.getAttribute(USER_ID) != null && ((Long) session.getAttribute(USER_ID)).longValue() > 0) {
        beatPlansManager = (BeatPlansManager) getBean(BEAT_PLANS_MANAGER);
        beatPlansForm = (BeatPlansForm) form;
        beatPlanId = Long.parseLong(request.getParameter("selectedId"));
        selectType = (String) request.getParameter("type");
        distributorandRunnersMap = beatPlansManager.getDistributorsAndRunners(beatPlanId);
        distributorsOfHub = (List<DistributorDetails>) distributorandRunnersMap.get(DISTRIBUTORS);
        
        if (distributorsOfHub != null) {
          for (int i = 0; i < distributorsOfHub.size(); i++) {
            distributorBufferResult.append("<option value=" + distributorsOfHub.get(i).getDistributorId() + ">");
            distributorBufferResult.append(distributorsOfHub.get(i).getDistributorName());
            distributorBufferResult.append("</option>");
          }
        }
        runners = (List<UserDetails>) distributorandRunnersMap.get(RUNNERS);
        
        if (runners != null) {
          for (int i = 0; i < runners.size(); i++) {
            runnerBufferResult.append("<option value=" + runners.get(i).getUserId() + ">");
            runnerBufferResult.append(runners.get(i).getName().concat(" ").concat(runners.get(i).getLastName()));
            runnerBufferResult.append("</option>");
          }
        }
        beatPlansForm.setHubName(distributorandRunnersMap.get(HUB_NAME).toString());
        beatPlansForm.setZoneName(distributorandRunnersMap.get(ZONE_NAME).toString());
        beatPlansForm.setRegionName(distributorandRunnersMap.get(REGION_NAME).toString());
        beatPlansForm.setCircleName(distributorandRunnersMap.get(CIRCLE_NAME).toString());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("HUB_NAME", distributorandRunnersMap.get(HUB_NAME));
        jsonObject.put("ZONE_NAME", distributorandRunnersMap.get(ZONE_NAME));
        jsonObject.put("REGION_NAME", distributorandRunnersMap.get(REGION_NAME));
        jsonObject.put("CIRCLE_NAME", distributorandRunnersMap.get(CIRCLE_NAME));

        PrintWriter out = response.getWriter();

        if (selectType.equalsIgnoreCase("distributors")) {
          response.setContentType("text/html");
          out.println(distributorBufferResult);
        } else if (selectType.equalsIgnoreCase("runners")) {
          response.setContentType("text/html");
          out.println(runnerBufferResult);
        } else if (selectType.equalsIgnoreCase("beatPlanDetails")) {
          response.setContentType("application/json");
          out.println(jsonObject);
        }
        out.flush();
        out.close();
      } else {
        return mapping.findForward(SESSION_INACTIVE_PAGE);
      }
    } catch (Exception e) {
      log.error("Problem in the ActionForward getDistributorsAndRunnersList");
      log.error(e);
      ActionMessages errors = new ActionMessages();
      saveErrors(request, errors);
      throw e;
    }
    log.info("END of the ActionForward getDistributorsAndRunnersList");
    return mapping.findForward(ADD_RUNNER_BEAT_PLAN_PAGE);
  }

  // =========================================================================
  
  public void getVisitNumbersList(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward getVisitNumbersList");
    BeatPlansManager beatManager = null;
    HttpSession session = null;
    int[] visitNumbersList = null;

    try {
      session = request.getSession(false);
      
      if (session != null) {
        beatManager = (BeatPlansManager) getBean(BEAT_PLANS_MANAGER);
        visitNumbersList = beatManager.getVisitNumbers(Long.valueOf(request.getParameter("distributorId")));
        org.json.JSONArray jsonArray = new org.json.JSONArray();
        
        if (visitNumbersList != null && visitNumbersList.length > 0) {
          for (int i = 0; i < visitNumbersList.length; i++) {
            if (visitNumbersList[i] != 0) {
              jsonArray.put(visitNumbersList[i]);
            }
          }
        }
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.println(jsonArray);
        log.info("VISITNOS:" + jsonArray);
        out.flush();
        out.close();
      }
    } catch (Exception e) {
      log.error("Problem in the ActionForward getVisitNumbersList");
      log.error(e);
      ActionMessages errors = new ActionMessages();
      saveErrors(request, errors);
      throw e;
    }
    log.info("END of the ActionForward getVisitNumbersList");
  }
  
  //==========================================================================
}