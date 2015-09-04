/**
 ** @author Bhargava
 *
 */
package com.tracer.webui.presentation.action;

import static com.tracer.common.Constants.*;

import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;

import com.tracer.dao.model.BeatPlanCircleDetails;
import com.tracer.dao.model.BeatPlanDistributorDetails;
import com.tracer.dao.model.BeatPlanHubDetails;
import com.tracer.dao.model.BeatPlanRegionDetails;
import com.tracer.dao.model.BeatPlanUserDetails;
import com.tracer.dao.model.BeatPlanZoneDetails;
import com.tracer.dao.model.CircleDetails;
import com.tracer.dao.model.DistributorDetails;
import com.tracer.dao.model.HubDetails;
import com.tracer.dao.model.RegionDetails;
import com.tracer.dao.model.UserDetails;
import com.tracer.dao.model.ZoneDetails;
import com.tracer.service.BeatPlanBulkUploadManager;
import com.tracer.util.BeatPlanExcelManager;
import com.tracer.util.StringUtil;
import com.tracer.webui.presentation.form.BeatPlanBulkUploadForm;

public class BeatPlanBulkUploadAction extends BaseDispatchAction {
  
  //protected transient final Log log = LogFactory.getLog(getClass());
  private static final Logger log = Logger.getLogger(Logger.class.getName());
  
  // ===================================================================================================

  public ActionForward showBeatPlanBulkUploadPage(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse) throws Exception {
    log.info("START of the ActionForward showBeatPlanBulkUploadPage");
    HttpSession session = null;
    String pageToForward = null;
    
    try {
      session = request.getSession(false);
      if (session != null && session.getAttribute(USER_ID) != null && ((Long) session.getAttribute(USER_ID)).longValue() > 0) {
        saveToken(request);
        pageToForward = BEAT_PLAN_BULK_UPLOAD_PAGE;
      } else {
        pageToForward = SESSION_INACTIVE_PAGE;
      }
    } catch (Exception e) {
      log.error("Problem in the ActionForward showAddRunnerPage");
      log.error(e);
      ActionMessages errors = new ActionMessages();
      saveErrors(request, errors);
      throw e;
    }
    log.info("END of the ActionForward showBeatPlanBulkUploadPage");
    return actionMapping.findForward(pageToForward);
  }

  // ===================================================================================================

  @SuppressWarnings("unused")
public ActionForward uploadBeatPlan(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
    log.info("START of the ActionForward uploadBeatPlan");
    HttpSession session = null;
    String pageToForward = UPLOAD_BEAT_PLAN;
    BeatPlanCircleDetails beatPlanCircleDetails = null;
    CircleDetails circleDetails = null;
    RegionDetails regionDetails = null;
    ZoneDetails zoneDetails = null;
    HubDetails hubDetails = null;
    UserDetails userDetails = null;
    DistributorDetails distributorDetails = null;
    String circleName = null;
    String regionName = null;
    String zoneName = null;
    String hubName = null;
    String distributorName = null;
    String userName = null;
    BeatPlanBulkUploadManager beatPlanBulkUploadManager = null;
    Long circleId;
    Long regionId;
    Long zoneId;
    Long hubId;
    Long distributorId;
    Long userId;

    try {
      session = httpServletRequest.getSession(false);
      if (session != null && session.getAttribute(USER_ID) != null && ((Long) session.getAttribute(USER_ID)).longValue() > 0) {
        if (isTokenValid(httpServletRequest)) {
          saveToken(httpServletRequest);
          BeatPlanBulkUploadForm beatPlanBulkUploadForm = (BeatPlanBulkUploadForm) actionForm;
          FormFile file = beatPlanBulkUploadForm.getFile();
          
          if (file != null) {
            InputStream inputFile = file.getInputStream();
            beatPlanBulkUploadManager = (BeatPlanBulkUploadManager) getBean(BEAT_PLAN_BULK_UPLOAD_MANAGER);
            beatPlanCircleDetails = BeatPlanExcelManager.getInstance().readBeatPlanExcelFile(inputFile);
            circleName = beatPlanCircleDetails.getCircleName();
            if (beatPlanCircleDetails != null) {
              List<BeatPlanRegionDetails> beatPlanRegions = beatPlanCircleDetails.getBeatPlanRegions();
              if (!beatPlanRegions.isEmpty()) {
                for (BeatPlanRegionDetails beatPlanRegionDetails : beatPlanRegions) {
                  regionName = beatPlanRegionDetails.getRegionName();
                  if (StringUtil.isNotNull(regionName)) {
                    regionDetails = beatPlanBulkUploadManager.isRegionExists(regionName);
                    circleDetails = regionDetails.getCircleDetails();
                    circleName = circleDetails.getCircleName();
                  } else {
                    List<BeatPlanZoneDetails> beatPlanZones = beatPlanRegionDetails.getBeatPlanZones();
                    if (!beatPlanZones.isEmpty()) {
                      for (BeatPlanZoneDetails beatPlanZoneDetails : beatPlanZones) {
                        zoneName = beatPlanZoneDetails.getZoneName();
                        if (StringUtil.isNotNull(zoneName)) {
                          zoneDetails = beatPlanBulkUploadManager.isZoneExists(zoneName);
                          regionName = zoneDetails.getRegionDetails().getRegionName();
                          circleDetails = zoneDetails.getRegionDetails().getCircleDetails();
                          circleName = circleDetails.getCircleName();
                        }
                      }
                    }
                    }
                    circleId = circleDetails.getCircleId();
                    beatPlanCircleDetails.setCircleId(circleId);
                    regionDetails = beatPlanBulkUploadManager.isRegionExists(regionName);
                    if (regionDetails.getRegionId() == null) {
                      regionDetails = beatPlanBulkUploadManager.createRegion(regionDetails);
                    }
                    regionId = regionDetails.getRegionId();
                    beatPlanRegionDetails.setRegionId(regionId);
                    log.info("regionId ---> " + regionId);
                    
                    List<BeatPlanZoneDetails> beatPlanZones = beatPlanRegionDetails.getBeatPlanZones();
                    if(!beatPlanZones.isEmpty()) {
                      for(BeatPlanZoneDetails beatPlanZoneDetails : beatPlanZones) {
                        zoneName = beatPlanZoneDetails.getZoneName();
                        zoneDetails = beatPlanBulkUploadManager.isZoneExists(zoneName);
                        if (zoneDetails.getZoneId() == null) {
                          zoneDetails = beatPlanBulkUploadManager.createZone(zoneDetails);
                        }
                        zoneId = zoneDetails.getZoneId();
                        beatPlanZoneDetails.setZoneId(zoneId);
                        log.info("zoneId ----> " + zoneId);
                        
                        List<BeatPlanHubDetails> beatPlanHubs = beatPlanZoneDetails.getBeatPlanHubs();
                        if(!beatPlanHubs.isEmpty()) {
                          for(BeatPlanHubDetails beatPlanHubDetails : beatPlanHubs) {
                            hubName = beatPlanHubDetails.getHubName();
                            hubDetails = beatPlanBulkUploadManager.isHubExists(hubName);
                            if (hubDetails.getHubId() == null ) {
                              hubDetails = beatPlanBulkUploadManager.createHub(hubDetails);
                            }
                            hubId = hubDetails.getHubId();
                            log.info("hubId ---> " + hubId);
                            beatPlanHubDetails.setHubId(hubId);
                            
                            List<BeatPlanDistributorDetails> beatPlanDistributors = beatPlanHubDetails.getBeatPlanDistributors();
                            if(!beatPlanDistributors.isEmpty()) {
                              for(BeatPlanDistributorDetails beatPlanDistributorDetails : beatPlanDistributors) {
                                distributorName = beatPlanDistributorDetails.getDistributorName();
                                distributorDetails = beatPlanBulkUploadManager.isDistributorExists(distributorName);
                                if (distributorDetails.getDistributorId() == null) {
                                  distributorDetails = beatPlanBulkUploadManager.createDistributor(distributorDetails);
                                }
                                distributorId = distributorDetails.getDistributorId();
                                log.info("distributorId ---> " + distributorId);
                                beatPlanDistributorDetails.setDistributorId(distributorId);
                                
                                List<BeatPlanUserDetails> beatPlanUsers = beatPlanHubDetails.getBeatPlanUsers();
                                if(!beatPlanUsers.isEmpty()) {
                                  for(BeatPlanUserDetails beatPlanUserDetails : beatPlanUsers) {
                                    userName = beatPlanUserDetails.getUserName();
                                    userDetails = beatPlanBulkUploadManager.isUserExists(userName);
                                    /*if (beatPlanUserDetails.getRole().equalsIgnoreCase(ROLE_ASM)) {
                                      userDetails = beatPlanBulkUploadManager.isUserExists(userName);
                                    } else {
                                      userDetails = beatPlanBulkUploadManager.isUserExists(userName);
                                      }*/
                                      if (userDetails.getUserId() == null) {
                                        userDetails = beatPlanBulkUploadManager.createUser(userDetails);
                                      }
                                      userId = userDetails.getUserId();
                                      log.info("userId ---> " + userId);
                                      beatPlanUserDetails.setUserId(userId);
                                  }
                                }
                              }
                            }
                          }
                        }
                      }
                    }
                }
              }
            }
          }
          pageToForward = UPLOAD_BEAT_PLAN;
        }
      } else {
        pageToForward = SESSION_INACTIVE_PAGE;
        }
    } catch (Exception e) {
      log.error(e);
    }
    log.info("END of the ActionForward uploadBeatPlan");
    return actionMapping.findForward(pageToForward);
  }

  // ===================================================================================================

  public ActionForward showManageDistributorBeatPlansPage(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
    log.info("START of the ActionForward showManageDistributorBeatPlansPage");
    /*
     * HttpSession session = null; String pageToForward =
     * MANAGE_DISTRIBUTOR_BEAT_PLANS_PAGE; session =
     * httpServletRequest.getSession(false); if (session != null &&
     * session.getAttribute(USER_ID) != null && ((Long)
     * session.getAttribute(USER_ID)).longValue() > 0) { if
     * (isTokenValid(httpServletRequest)) { saveToken(httpServletRequest);
     * pageToForward = MANAGE_DISTRIBUTOR_BEAT_PLANS_PAGE; } } else {
     * pageToForward = SESSION_INACTIVE_PAGE; }
     */
    log.info("END of the ActionForward showManageDistributorBeatPlansPage");
    return actionMapping.findForward(MANAGE_DISTRIBUTOR_BEAT_PLANS_PAGE);
  }
}
