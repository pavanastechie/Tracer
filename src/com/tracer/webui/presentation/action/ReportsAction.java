/**
 * @author Jp
 *
 */
package com.tracer.webui.presentation.action;

import static com.tracer.common.Constants.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.json.JSONArray;
import org.json.JSONObject;

import com.tracer.dao.model.HubDetails;
import com.tracer.dao.model.PerformanceReportDetails;
import com.tracer.dao.model.RegionDetails;
import com.tracer.dao.model.ZoneDetails;
import com.tracer.service.ReportsManager;
import com.tracer.service.UserManager;
import com.tracer.util.StringUtil;
import com.tracer.webui.presentation.form.ReportsForm;

public class ReportsAction extends BaseDispatchAction {

  protected transient final Log log = LogFactory.getLog(getClass());
  
  //========================================================================

  public ActionForward showReportsDashboard(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward showReportsDashboard");
    HttpSession session = null;
    int roleId = 0;
    String pageToForward = null;
    try {
      session = request.getSession(false);
      roleId = ((Long) session.getAttribute(ROLE_ID)).intValue();
      switch (roleId) {
        case 1:
          pageToForward = REPORTS_DASHBOARD_PAGE;
          break;
        case 2:
          pageToForward = CBM_REPORTS_DASHBOARD_PAGE;
          break;
        case 3:
          pageToForward = RBM_REPORTS_DASHBOARD_PAGE;
          break;
        case 4:
          pageToForward = ZBM_REPORTS_DASHBOARD_PAGE;
          break;
        case 5:
          pageToForward = ASM_REPORTS_DASHBOARD_PAGE;
          break;
        case 6:
          pageToForward = TSM_REPORTS_DASHBOARD_PAGE;
          break;
      }
    } catch (Exception e) {
      log.error("Problem in the ActionForward showReportsDashboard"); 
      log.error(e);
      ActionMessages errors = new ActionMessages();
		 saveErrors(request,errors);
		    throw e;
    }
    log.info("END of the ActionForward showReportsDashboard");
    return mapping.findForward(pageToForward);
  }

  
  //========================================================================
  
  public ActionForward getRegionsList(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward showTopRunnersReportPage");
    ReportsForm reportsForm = null;
    UserManager userManager = null;
    HttpSession session = null;
    List<RegionDetails> regionDetailsList = null;
    Long circleId = 0L;
    String reportName = null;
    String pageToForward = null;
    try {
    	userManager = (UserManager) getBean(USER_MANAGER);
        session = request.getSession(false);
        regionDetailsList = new ArrayList<>();
        reportsForm = (ReportsForm) form;
        reportName = reportsForm.getReportName();
        if(reportName == TOP_RUNNERS_REPORT)
          pageToForward = TOP_RUNNERS_REPORT_PAGE;
        else if(reportName == TOP_DISTRIBUTORS_REPORT)
          pageToForward = TOP_DISTRIBUTORS_REPORT_PAGE;
        else if(reportName == TOP_HUBS_REPORT)
          pageToForward = TOP_HUBS_REPORT_PAGE;
        else if(reportName == TOP_ZONES_REPORT)
          pageToForward = TOP_ZONES_REPORT_PAGE;
        else if(reportName == TOP_REGIONS_REPORT)
          pageToForward = TOP_REGIONS_REPORT_PAGE;
        else if(reportName == ATTENDANCE_REPORT)
            pageToForward = ATTENDANCE_REPORT_PAGE;
        else if(reportName == VISITS_VS_BEATPLAN_REPORT)
            pageToForward = VISITS_VS_BEATPLAN_REPORT_PAGE;
        else if(reportName == NONPERFORMED_RUNNERS_REPORT)
            pageToForward = NON_PERFORMED_RUNNERS_REPORT_PAGE;
        circleId = reportsForm.getCircleId();
        if(circleId != null)
          regionDetailsList = userManager.getRegions((Long) session.getAttribute(USER_ID), (Long) session.getAttribute(ROLE_ID), circleId);
       if(regionDetailsList != null) {
         reportsForm.setRegionDetailsList(regionDetailsList);
   }
    } catch (Exception e) {
      log.error("Problem in the ActionForward showTopRunnersReportPage"); 
      log.error(e);
      ActionMessages errors = new ActionMessages();
		 saveErrors(request,errors);
		    throw e;
    }
    log.info("END of the ActionForward showTopRunnersReportPage");
    return mapping.findForward(pageToForward);
    }

  //========================================================================

  
  public ActionForward getZonesList(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward showTopRunnersReportPage");
    ReportsForm reportsForm = null;
    UserManager userManager = null;
    HttpSession session = null;
    List<ZoneDetails> zoneDetailsList = null;
    Long regionId = 0L;
    String reportName = null;
    String pageToForward = null;
    
    try {
    	userManager = (UserManager) getBean(USER_MANAGER);
        session = request.getSession(false);
        zoneDetailsList = new ArrayList<>();
        reportsForm = (ReportsForm) form;
        reportName = reportsForm.getReportName();
        if(reportName == TOP_RUNNERS_REPORT)
          pageToForward = TOP_RUNNERS_REPORT_PAGE;
        else if(reportName == TOP_DISTRIBUTORS_REPORT)
          pageToForward = TOP_DISTRIBUTORS_REPORT_PAGE;
        else if(reportName == TOP_HUBS_REPORT)
          pageToForward = TOP_HUBS_REPORT_PAGE;
        else if(reportName == TOP_ZONES_REPORT)
          pageToForward = TOP_ZONES_REPORT_PAGE;
        else if(reportName == TOP_REGIONS_REPORT)
          pageToForward = TOP_REGIONS_REPORT_PAGE;
        else if(reportName == ATTENDANCE_REPORT)
            pageToForward = ATTENDANCE_REPORT_PAGE;
        else if(reportName == VISITS_VS_BEATPLAN_REPORT)
            pageToForward = VISITS_VS_BEATPLAN_REPORT_PAGE;
        else if(reportName == NONPERFORMED_RUNNERS_REPORT)
            pageToForward = NON_PERFORMED_RUNNERS_REPORT_PAGE;
        regionId = reportsForm.getRegionId();
        if(regionId != null)
          zoneDetailsList = userManager.getZones((Long) session.getAttribute(USER_ID), (Long) session.getAttribute(ROLE_ID), regionId);
       if(zoneDetailsList != null) {
         reportsForm.setZoneDetailsList(zoneDetailsList);
	   }
    } catch (Exception e) {
      log.error("Problem in the ActionForward showTopRunnersReportPage"); 
      log.error(e);
      ActionMessages errors = new ActionMessages();
		 saveErrors(request,errors);
		    throw e;
    }
    log.info("END of the ActionForward showTopRunnersReportPage");
    return mapping.findForward(pageToForward);
    }

  //========================================================================
  
  public ActionForward getHubsList(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward showTopRunnersReportPage");
    ReportsForm reportsForm = null;
    UserManager userManager = null;
    HttpSession session = null;
    List<HubDetails> hubDetailsList = null;
    Long zoneId = 0L;
    String reportName = null;
    String pageToForward = null;
    
    try {
    	userManager = (UserManager) getBean(USER_MANAGER);
        session = request.getSession(false);
        hubDetailsList = new ArrayList<>();
        reportsForm = (ReportsForm) form;
        reportName = reportsForm.getReportName();
        if(reportName == TOP_RUNNERS_REPORT)
          pageToForward = TOP_RUNNERS_REPORT_PAGE;
        else if(reportName == TOP_DISTRIBUTORS_REPORT)
          pageToForward = TOP_DISTRIBUTORS_REPORT_PAGE;
        else if(reportName == TOP_HUBS_REPORT)
          pageToForward = TOP_HUBS_REPORT_PAGE;
        else if(reportName == TOP_ZONES_REPORT)
          pageToForward = TOP_ZONES_REPORT_PAGE;
        else if(reportName == TOP_REGIONS_REPORT)
          pageToForward = TOP_REGIONS_REPORT_PAGE;
        else if(reportName == ATTENDANCE_REPORT)
            pageToForward = ATTENDANCE_REPORT_PAGE;
        else if(reportName == VISITS_VS_BEATPLAN_REPORT)
            pageToForward = VISITS_VS_BEATPLAN_REPORT_PAGE;
        else if(reportName == NONPERFORMED_RUNNERS_REPORT)
            pageToForward = NON_PERFORMED_RUNNERS_REPORT_PAGE;
        zoneId = reportsForm.getZoneId();
        
        if(zoneId != null)
          hubDetailsList = userManager.getHubs((Long) session.getAttribute(USER_ID), (Long) session.getAttribute(ROLE_ID), zoneId);
       if(hubDetailsList != null) {
         reportsForm.setHubDetailsList(hubDetailsList);
       }
    } catch (Exception e) {
      log.error("Problem in the ActionForward showTopRunnersReportPage"); 
      log.error(e);
      ActionMessages errors = new ActionMessages();
		 saveErrors(request,errors);
		    throw e;
    }
    log.info("END of the ActionForward showTopRunnersReportPage");
    return mapping.findForward(pageToForward);
    }
  
//========================================================================
  
  public ActionForward showTopRunnersReportPage(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward showTopRunnersReportPage");
    ReportsForm reportsForm = null;
    HttpSession session = null;
    UserManager userManager = null;
    
    try {
    	userManager = (UserManager) getBean(USER_MANAGER);
        session = request.getSession(false);
        reportsForm = (ReportsForm) form;
        BeanUtils.copyProperties(reportsForm, new ReportsForm());
        reportsForm.setReportName(TOP_RUNNERS_REPORT);
        setComponentsList(reportsForm, userManager, session);
    } catch (Exception e) {
      log.error("Problem in the ActionForward showTopRunnersReportPage"); 
      log.error(e);
      ActionMessages errors = new ActionMessages();
		 saveErrors(request,errors);
		    throw e;
    }
    log.info("END of the ActionForward showTopRunnersReportPage");
    return mapping.findForward(TOP_RUNNERS_REPORT_PAGE);
  }


  //========================================================================
  
  public ActionForward showAttendanceReportPage(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward showAttendanceReportPage");
    ReportsForm reportsForm = null;
    UserManager userManager = null;
    HttpSession session = null;

    try {
    	userManager = (UserManager) getBean(USER_MANAGER);
        session = request.getSession(false);
        reportsForm = (ReportsForm) form;
        BeanUtils.copyProperties(reportsForm, new ReportsForm());
        reportsForm.setReportName(ATTENDANCE_REPORT);
        setComponentsList(reportsForm, userManager, session);
    } catch (Exception e) {
      log.error("Problem in the ActionForward showAttendanceReportPage"); 
      log.error(e);
      ActionMessages errors = new ActionMessages();
		 saveErrors(request,errors);
		    throw e;
    }
    log.info("END of the ActionForward showAttendanceReportPage");
    return mapping.findForward(ATTENDANCE_REPORT_PAGE);
  }

  //========================================================================
  
  public ActionForward showVisitsVsBeatplanReportPage(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward showVisitsVsBeatplanReportPage");
    ReportsForm reportsForm = null;
    UserManager userManager = null;
    HttpSession session = null;

    try {
    	userManager = (UserManager) getBean(USER_MANAGER);
        session = request.getSession(false);
        reportsForm = (ReportsForm) form;
        BeanUtils.copyProperties(reportsForm, new ReportsForm());
        reportsForm.setReportName(VISITS_VS_BEATPLAN_REPORT);
        setComponentsList(reportsForm, userManager, session);
    } catch (Exception e) {
      log.error("Problem in the ActionForward showVisitsVsBeatplanReportPage"); 
      log.error(e);
      ActionMessages errors = new ActionMessages();
		 saveErrors(request,errors);
		    throw e;
    }
    log.info("END of the ActionForward showVisitsVsBeatplanReportPage");
    return mapping.findForward(VISITS_VS_BEATPLAN_REPORT_PAGE);
  }

  //========================================================================
  
  public ActionForward showNonPerformedRunnersReportPage(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward showNonPerformanceReportPage");
    ReportsForm reportsForm = null;
    UserManager userManager = null;
    HttpSession session = null;
    
    try {
    	userManager = (UserManager) getBean(USER_MANAGER);
        session = request.getSession(false);
        reportsForm = (ReportsForm) form;
        BeanUtils.copyProperties(reportsForm, new ReportsForm());
        reportsForm.setReportName(NONPERFORMED_RUNNERS_REPORT);
        setComponentsList(reportsForm, userManager, session);
    } catch (Exception e) {
      log.error("Problem in the ActionForward showNonPerformanceReportPage"); 
      log.error(e);
      ActionMessages errors = new ActionMessages();
	  saveErrors(request,errors);
     throw e;
    }
    log.info("END of the ActionForward showNonPerformanceReportPage");
    return mapping.findForward(NON_PERFORMED_RUNNERS_REPORT_PAGE);
  }

  //========================================================================
  
  public ActionForward showTopDistributorsReportPage(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward showTopDistributorsReportPage");
    ReportsForm reportsForm = null;
    UserManager userManager = null;
    HttpSession session = null;

    try {
      userManager = (UserManager) getBean(USER_MANAGER);
      session = request.getSession(false);
      reportsForm = (ReportsForm) form;
      BeanUtils.copyProperties(reportsForm, new ReportsForm());
      reportsForm.setReportName(TOP_DISTRIBUTORS_REPORT);
      setComponentsList(reportsForm, userManager, session);
    } catch (Exception e) {
      log.error("Problem in the ActionForward showTopDistributorsReportPage"); 
      log.error(e);
      ActionMessages errors = new ActionMessages();
		 saveErrors(request,errors);
		    throw e;
    }
    log.info("END of the ActionForward showTopDistributorsReportPage");
    return mapping.findForward(TOP_DISTRIBUTORS_REPORT_PAGE);
  }

  //========================================================================
  
  public ActionForward showTopRegionsReportPage(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward showTopRegionsReportPage");
    ReportsForm reportsForm = null;
    UserManager userManager = null;
    HttpSession session = null;

    try {
    	userManager = (UserManager) getBean(USER_MANAGER);
        session = request.getSession(false);
        reportsForm = (ReportsForm) form;
        BeanUtils.copyProperties(reportsForm, new ReportsForm());
        reportsForm.setReportName(TOP_REGIONS_REPORT);
        setComponentsList(reportsForm, userManager, session);
    } catch (Exception e) {
      log.error("Problem in the ActionForward showTopRegionsReportPage"); 
      log.error(e);
      ActionMessages errors = new ActionMessages();
		 saveErrors(request,errors);
		    throw e;
    }
    log.info("END of the ActionForward showTopRegionsReportPage");
    return mapping.findForward(TOP_REGIONS_REPORT_PAGE);
  }

  //========================================================================
  
  public ActionForward showTopZonesReportPage(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward showTopZonesReportPage");
    ReportsForm reportsForm = null;
    UserManager userManager = null;
    HttpSession session = null;

    try {
      userManager = (UserManager) getBean(USER_MANAGER);
      session = request.getSession(false);
      reportsForm = (ReportsForm) form;
      BeanUtils.copyProperties(reportsForm, new ReportsForm());
      reportsForm.setReportName(TOP_ZONES_REPORT);
      setComponentsList(reportsForm, userManager, session);
    } catch (Exception e) {
      log.error("Problem in the ActionForward showTopZonesReportPage"); 
      log.error(e);
      ActionMessages errors = new ActionMessages();
      saveErrors(request,errors);
      throw e;
    }
    log.info("END of the ActionForward showTopZonesReportPage");
    return mapping.findForward(TOP_ZONES_REPORT_PAGE);
  }

  //========================================================================
  
  public ActionForward showTopHubsReportPage(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward showTopHubReportPage");
    ReportsForm reportsForm = null;
    UserManager userManager = null;
    HttpSession session = null;
    
    try {
    	userManager = (UserManager) getBean(USER_MANAGER);
        session = request.getSession(false);
        reportsForm = (ReportsForm) form;
        BeanUtils.copyProperties(reportsForm, new ReportsForm());
        reportsForm.setReportName(TOP_HUBS_REPORT);
        setComponentsList(reportsForm, userManager, session);
    } catch (Exception e) {
      log.error("Problem in the ActionForward showTopHubReportPage"); 
      log.error(e);
      ActionMessages errors = new ActionMessages();
	  saveErrors(request,errors);
	  throw e;
    }
    log.info("END of the ActionForward showTopHubReportPage");
    return mapping.findForward(TOP_HUBS_REPORT_PAGE);
  }
  
  //==========================================================================
  
  private void setComponentsList( ReportsForm reportsForm, UserManager userManager, HttpSession session) {
    Long roleId = 0L;
    Long userId = 0L;
    
    try {
      if(reportsForm != null && userManager != null && session != null) {
        roleId = ((Long) session.getAttribute(ROLE_ID));  
        userId = ((Long) session.getAttribute(USER_ID)); 
        
        switch (roleId.intValue()) {
          case SYSTEM_ADMIN:
            reportsForm.setCircleDetailsList(userManager.getCircles());
            break;
          case CIRCLE_MANAGER:
            reportsForm.setCircleDetailsList(userManager.getCircles(userId));
            break;
          case REGION_MANAGER:
            if(session.getAttribute(ASSIGNED_CIRCLE_ID) != null) {
              reportsForm.setRegionDetailsList(userManager.getRegions(userId, roleId, (Long) session.getAttribute(ASSIGNED_CIRCLE_ID)));
            }
            break;
          case ZONE_MANAGER:
            if(session.getAttribute(ASSIGNED_REGION_ID) != null) {
              reportsForm.setZoneDetailsList(userManager.getZones(userId, roleId, (Long) session.getAttribute(ASSIGNED_REGION_ID)));
            }
            break;
          case HUB_MANAGER:
            if(session.getAttribute(ASSIGNED_ZONE_ID) != null) {
              reportsForm.setHubDetailsList(userManager.getHubs(userId, roleId, (Long) session.getAttribute(ASSIGNED_ZONE_ID)));
            }
            break;
        }
      }
    } catch (Exception e) {
      log.error("Problem in the ActionForward showTopHubReportPage"); 
      log.error(e);
    }
  }
  
  //==========================================================================
  
  public ActionForward showHubPerformanceReportPage(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward showHubPerformanceReportPage");
    HttpSession session = null;
    ReportsForm reportsForm = null;
    String curDate = null;
    Date date = null;
    String pageToForward = HUB_PERFORMANCE_REPORT;
    UserManager userManager = null;
    
    try {
      reportsForm = (ReportsForm) form;
      session = request.getSession(false);
      userManager = (UserManager) getBean(USER_MANAGER);
      
      if (session != null && session.getAttribute(USER_ID) != null && ((Long) session.getAttribute(USER_ID)).longValue() > 0) {
        if(StringUtil.isNull(reportsForm.getFromDate())) {
          date = new Date();
          curDate = new SimpleDateFormat(DATE_FORMAT).format(date);
          reportsForm.setFromDate(curDate);
          reportsForm.setToDate(curDate);
        }
        reportsForm.setHubDetailsList(userManager.getHubs(((Long) session.getAttribute(USER_ID)), ((Long) session.getAttribute(ROLE_ID)), (Long) session.getAttribute(ASSIGNED_ZONE_ID)));
      } else {
        pageToForward = SESSION_INACTIVE_PAGE;
      }
    } catch (Exception e) {
      log.error("Problem in the ActionForward showHubPerformanceReportPage");
      log.error(e);
    }
    log.info("END of the ActionForward showHubPerformanceReportPage");
    return mapping.findForward(pageToForward);
  }

  // ==============================================================================
  
  public void getHubPerformanceReport(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward getHubPerformanceReport");
    ReportsForm reportsForm = null;
    String fromDate = null;
    String toDate = null;
    Long hubId = null;
    JSONArray hubPerformanceDetailsJSONArray = null;
    List<PerformanceReportDetails> hubPerformanceReportDetailsList = null;
    ReportsManager reportsManager = null;

    try {
      reportsForm = (ReportsForm) form;
      fromDate = reportsForm.getFromDate();
      toDate = reportsForm.getToDate();
      hubId = reportsForm.getHubId();
      hubPerformanceDetailsJSONArray = new JSONArray();

      if(StringUtil.isNotNull(fromDate) && StringUtil.isNotNull(toDate) && hubId != null && hubId.longValue() > 0) {
        reportsManager = (ReportsManager) getBean(REPORTS_MANAGER);
        hubPerformanceReportDetailsList = reportsManager.getHubPerformanceReport(StringUtil.convertUIDateToDBDate(fromDate), StringUtil.convertUIDateToDBDate(toDate), hubId);
      }
      if(hubPerformanceReportDetailsList != null && hubPerformanceReportDetailsList.size() > 0) {
        for(PerformanceReportDetails reportDetails : hubPerformanceReportDetailsList) {
          JSONObject hubPerformanceDetailsJSON = new JSONObject();
          hubPerformanceDetailsJSON.put("hubName", reportDetails.getHubName());
          hubPerformanceDetailsJSON.put("runnerName", reportDetails.getRunnerName());
          hubPerformanceDetailsJSON.put("distributorName", reportDetails.getDistributorName());
          hubPerformanceDetailsJSON.put("ofscCode", reportDetails.getOfscCode());
          hubPerformanceDetailsJSON.put("scheduleTime", reportDetails.getScheduleTime());
          hubPerformanceDetailsJSON.put("visitedTime", reportDetails.getVisitedTime());
          hubPerformanceDetailsJSON.put("cafsCollected", reportDetails.getCafsCollected());
          hubPerformanceDetailsJSON.put("date", reportDetails.getDate());
          hubPerformanceDetailsJSONArray.put(hubPerformanceDetailsJSON);
        }
      }
      response.setContentType("application/json");
      response.getWriter().print(hubPerformanceDetailsJSONArray);
    } catch (Exception e) {
      log.error("Problem in the ActionForward getHubPerformanceReport");
      log.error(e);
    }
    log.info("END of the ActionForward getHubPerformanceReport");
  }

  // ==============================================================================
  
  public ActionForward showZonePerformanceReportPage(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward showZonePerformanceReportPage");
    HttpSession session = null;
    ReportsForm reportsForm = null;
    String curDate = null;
    Date date = null;
    String pageToForward = ZONE_PERFORMANCE_REPORT;
    ReportsManager reportsManager = null;
    
    try {
      reportsForm = (ReportsForm) form;
      session = request.getSession(false);
      reportsManager = (ReportsManager) getBean(REPORTS_MANAGER);
      
      if (session != null && session.getAttribute(USER_ID) != null && ((Long) session.getAttribute(USER_ID)).longValue() > 0) {
        if(StringUtil.isNull(reportsForm.getFromDate())) {
          date = new Date();
          curDate = new SimpleDateFormat(DATE_FORMAT).format(date);
          reportsForm.setFromDate(curDate);
          reportsForm.setToDate(curDate);
        }
        reportsForm.setZoneDetailsList(reportsManager.getZones((Long) session.getAttribute(USER_ID)));
      } else {
        pageToForward = SESSION_INACTIVE_PAGE;
      }
    } catch (Exception e) {
      log.error("Problem in the ActionForward showZonePerformanceReportPage");
      log.error(e);
    }
    log.info("END of the ActionForward showZonePerformanceReportPage");
    return mapping.findForward(pageToForward);
  }

  // ==============================================================================
  
  public void getZonePerformanceReport(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward getZonePerformanceReport");
    ReportsForm reportsForm = null;
    String fromDate = null;
    String toDate = null;
    Long zoneId = null;
    JSONArray zonePerformanceDetailsJSONArray = null;
    List<PerformanceReportDetails> zonePerformanceReportDetailsList = null;
    ReportsManager reportsManager = null;

    try {
      reportsForm = (ReportsForm) form;
      fromDate = reportsForm.getFromDate();
      toDate = reportsForm.getToDate();
      zoneId = reportsForm.getZoneId();
      zonePerformanceDetailsJSONArray = new JSONArray();

      if(StringUtil.isNotNull(fromDate) && StringUtil.isNotNull(toDate) && zoneId != null && zoneId.longValue() > 0) {
        reportsManager = (ReportsManager) getBean(REPORTS_MANAGER);
        zonePerformanceReportDetailsList = reportsManager.getZonePerformanceReport(StringUtil.convertUIDateToDBDate(fromDate), StringUtil.convertUIDateToDBDate(toDate), zoneId);
      }
      if(zonePerformanceReportDetailsList != null && zonePerformanceReportDetailsList.size() > 0) {
        for(PerformanceReportDetails reportDetails : zonePerformanceReportDetailsList) {
          JSONObject zonePerformanceDetailsJSON = new JSONObject();
          zonePerformanceDetailsJSON.put("zoneName", reportDetails.getZoneName());
          zonePerformanceDetailsJSON.put("hubName", reportDetails.getHubName());
          zonePerformanceDetailsJSON.put("runnerName", reportDetails.getRunnerName());
          zonePerformanceDetailsJSON.put("distributorName", reportDetails.getDistributorName());
          zonePerformanceDetailsJSON.put("ofscCode", reportDetails.getOfscCode());
          zonePerformanceDetailsJSON.put("scheduleTime", reportDetails.getScheduleTime());
          zonePerformanceDetailsJSON.put("visitedTime", reportDetails.getVisitedTime());
          zonePerformanceDetailsJSON.put("cafsCollected", reportDetails.getCafsCollected());
          zonePerformanceDetailsJSON.put("date", reportDetails.getDate());
          zonePerformanceDetailsJSONArray.put(zonePerformanceDetailsJSON);
        }
      }
      response.setContentType("application/json");
      response.getWriter().print(zonePerformanceDetailsJSONArray);
    } catch (Exception e) {
      log.error("Problem in the ActionForward getZonePerformanceReport");
      log.error(e);
    }
    log.info("END of the ActionForward getZonePerformanceReport");
  }

  // ==============================================================================
}
