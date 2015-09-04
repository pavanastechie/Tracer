/**
 * @author Jp
 *
 */
package com.tracer.webui.presentation.action;

import static com.tracer.common.Constants.ACTIVE;
import static com.tracer.common.Constants.ASM_DASHBOARD_PAGE;
import static com.tracer.common.Constants.CBM_DASHBOARD_PAGE;
import static com.tracer.common.Constants.CIRCLE_MANAGER;
import static com.tracer.common.Constants.DEO;
import static com.tracer.common.Constants.DEO_DASHBOARD_PAGE;
import static com.tracer.common.Constants.HOME_PAGE;
import static com.tracer.common.Constants.HUB_MANAGER;
import static com.tracer.common.Constants.IN_ACTIVE;
import static com.tracer.common.Constants.RBM_DASHBOARD_PAGE;
import static com.tracer.common.Constants.REGION_MANAGER;
import static com.tracer.common.Constants.ROLE_ID;
import static com.tracer.common.Constants.RUNNER;
import static com.tracer.common.Constants.SA_DASHBOARD_PAGE;
import static com.tracer.common.Constants.SYSTEM_ADMIN;
import static com.tracer.common.Constants.TEAM_LEADER;
import static com.tracer.common.Constants.TRACK_MANAGER;
import static com.tracer.common.Constants.TSE_DASHBOARD_PAGE;
import static com.tracer.common.Constants.TSM_DASHBOARD_PAGE;
import static com.tracer.common.Constants.USER_ID;
import static com.tracer.common.Constants.USER_MANAGER;
import static com.tracer.common.Constants.ZBM_DASHBOARD_PAGE;
import static com.tracer.common.Constants.ZONE_MANAGER;

import java.util.List;

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

import com.tracer.dao.model.DashboardDetails;
import com.tracer.dao.model.RunnerCAFTrackDetails;
import com.tracer.dao.model.UserDetails;
import com.tracer.service.TrackManager;
import com.tracer.service.UserManager;
import com.tracer.util.StringUtil;
import com.tracer.webui.presentation.form.DashboardForm;

public class DashboardAction extends BaseDispatchAction {

  protected transient final Log log = LogFactory.getLog(getClass());
  
  //========================================================================

  public ActionForward showDashboard(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response)throws Exception {
    log.info("START of the ActionForward showDashboard");
    HttpSession session = null;
    int roleId = 0;
    long userId;
    DashboardForm dashboardForm = null;
    DashboardDetails dashboardDetails = null;
    UserManager userManager = null;
    String pageToForward = null;
    TrackManager trackManager = null;
    List<UserDetails> runners = null;
    List<RunnerCAFTrackDetails> runnersCAFTrack = null;
  
    try {
      session = request.getSession(false);
      roleId = ((Long) session.getAttribute(ROLE_ID)).intValue();
      userId = ((Long) session.getAttribute(USER_ID));
      dashboardForm = (DashboardForm) form;
      
      if(roleId == TEAM_LEADER) {
        trackManager = (TrackManager) getBean(TRACK_MANAGER);
        runners = trackManager.getRunners(userId);
        runnersCAFTrack = trackManager.getRunnersCAFTrack(runners);
        dashboardForm.setRunnersCAFTrack(runnersCAFTrack);
        pageToForward = TSM_DASHBOARD_PAGE;  
      } else {
        userManager = (UserManager) getBean(USER_MANAGER);
        BeanUtils.copyProperties(dashboardForm, new DashboardForm());
        dashboardDetails = userManager.getDashboardDetails(userId);
      
        //Setting Default Reports For User in the Dash board
       /* if(Integer.parseInt(dashboardDetails.getAttendanceReport()) == 1) {
          dashboardForm.setAttendanceReport("on");
        } else {
          dashboardForm.setAttendanceReport("off");
        }
        if(Integer.parseInt(dashboardDetails.getNonPerformedRunnersReport()) == 1) {
          dashboardForm.setNonPerformedRunnersReport("on");
        } else {
          dashboardForm.setNonPerformedRunnersReport("off");
        }
        if(Integer.parseInt(dashboardDetails.getTopDistributorsReport()) == 1) {
          dashboardForm.setTopDistributorsReport("on");
        } else {
          dashboardForm.setTopDistributorsReport("off");   
        }
        if(Integer.parseInt(dashboardDetails.getTopHubsReport()) == 1) {
          dashboardForm.setTopHubsReport("on");
        } else {
          dashboardForm.setTopHubsReport("off");
        }
        if(Integer.parseInt(dashboardDetails.getTopRegionsReport()) == 1) {
          dashboardForm.setTopRegionsReport("on");
        } else {
          dashboardForm.setTopRegionsReport("off");
        }
        if(Integer.parseInt(dashboardDetails.getTopRunnersReport()) == 1) {
          dashboardForm.setTopRunnersReport("on");
        } else {
          dashboardForm.setTopRunnersReport("off");
        }
        if(Integer.parseInt(dashboardDetails.getTopZonesReport()) == 1) {
          dashboardForm.setTopZonesReport("on");
        } else {
          dashboardForm.setTopZonesReport("off");
        }
        if(Integer.parseInt(dashboardDetails.getVisitsVsBeatPlanReport()) == 1) {
          dashboardForm.setVisitsVsBeatPlanReport("on");
        } else {
          dashboardForm.setVisitsVsBeatPlanReport("off");
        }*/
        if(dashboardDetails != null && dashboardDetails.getDashboardDetailsId() != null && dashboardDetails.getDashboardDetailsId().longValue() > 0) {
          dashboardForm.setDashboardDetailsId(dashboardDetails.getDashboardDetailsId());
        }
      
        switch (roleId) {
          case SYSTEM_ADMIN:
          pageToForward = SA_DASHBOARD_PAGE;
          break;
          case CIRCLE_MANAGER:
          pageToForward = CBM_DASHBOARD_PAGE;
          break;
          case REGION_MANAGER:
          pageToForward = RBM_DASHBOARD_PAGE;
          break;
          case ZONE_MANAGER:
          pageToForward = ZBM_DASHBOARD_PAGE;
          break;
          case HUB_MANAGER:
          pageToForward = ASM_DASHBOARD_PAGE;
          break;
          case TEAM_LEADER:
          pageToForward = TSM_DASHBOARD_PAGE;
          break;
          case RUNNER:
          pageToForward = TSE_DASHBOARD_PAGE;
          break;
          case DEO:
          pageToForward = DEO_DASHBOARD_PAGE;
          break;
          default:
          pageToForward = HOME_PAGE;
          break;
        }  
      }
    } catch (Exception e) {
      log.error("Problem in the ActionForward showDashboard");
      log.error(e);
      ActionMessages errors = new ActionMessages();
      saveErrors(request,errors);
      throw e;
    }
    log.info("END of the ActionForward showDashboard");
    return mapping.findForward(pageToForward);
  }

  //========================================================================

  public ActionForward saveDashBoardDetails(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward saveDashBoardDetails");
    HttpSession session = null;
    DashboardForm dashboardForm = null;
    DashboardDetails dashboardDetails = null;
    UserManager userManager = null;
    UserDetails userDetails = null;
    String selected = null;
    String[] selectedList = null;
    StringBuffer selectedCheckboxes = null;
    long userId;
    
    try {
      session = request.getSession(false);
      userId = ((Long) session.getAttribute(USER_ID));
      dashboardForm = (DashboardForm) form;
      dashboardDetails = new DashboardDetails();
      selectedCheckboxes = new StringBuffer(request.getParameter("selectedCheckboxes"));
      selected = StringUtil.removeTrailingValue(selectedCheckboxes, ",");
      log.info("Selected Checkboxes"+selected);
      selectedList = selected.split(",");
      
      dashboardDetails.setAttendanceReport(IN_ACTIVE);
      dashboardDetails.setNonPerformedRunnersReport(IN_ACTIVE);
      dashboardDetails.setTopDistributorsReport(IN_ACTIVE);
      dashboardDetails.setTopHubsReport(IN_ACTIVE);
      dashboardDetails.setTopRegionsReport(IN_ACTIVE);
      dashboardDetails.setTopRunnersReport(IN_ACTIVE);
      dashboardDetails.setTopZonesReport(IN_ACTIVE);
      dashboardDetails.setVisitsVsBeatPlanReport(IN_ACTIVE);
      
      for(String item : selectedList) {
        if(item.equalsIgnoreCase("attendanceReport")) {
          dashboardDetails.setAttendanceReport(ACTIVE);
        } else if(item.equalsIgnoreCase("visitsVsBeatPlanReport")) {
          dashboardDetails.setVisitsVsBeatPlanReport(ACTIVE);
        } else if(item.equalsIgnoreCase("nonPerformedRunnersReport")) {
          dashboardDetails.setNonPerformedRunnersReport(ACTIVE);
        } else if(item.equalsIgnoreCase("topRunnersReport")) {
          dashboardDetails.setTopRunnersReport(ACTIVE);
        } else if(item.equalsIgnoreCase("topDistributorsReport")) {
          dashboardDetails.setTopDistributorsReport(ACTIVE);
        } else if(item.equalsIgnoreCase("topRegionsReport")) {
          dashboardDetails.setTopRegionsReport(ACTIVE);
        } else if(item.equalsIgnoreCase("topZonesReport")) {
          dashboardDetails.setTopZonesReport(ACTIVE);
        } else if(item.equalsIgnoreCase("topHubsReport")) {
          dashboardDetails.setTopHubsReport(ACTIVE);
        }
      }
      dashboardDetails.setStatus(ACTIVE);
      dashboardDetails.setDashboardDetailsId(dashboardForm.getDashboardDetailsId());
      
      userDetails = new UserDetails();
      userDetails.setUserId(userId);
      dashboardDetails.setUserDetails(userDetails);
      
      userManager = (UserManager) getBean(USER_MANAGER);
      userManager.updateDashboardDetails(dashboardDetails);
    } catch (Exception e) {
      log.error("Problem in the ActionForward saveDashBoardDetails"); 
      log.error(e);
      ActionMessages errors = new ActionMessages();
		 saveErrors(request,errors);
		    throw e;
    }
    log.info("END of the ActionForward saveDashBoardDetails");
    return showDashboard(mapping, dashboardForm, request, response);
  }
  
  //========================================================================
}
