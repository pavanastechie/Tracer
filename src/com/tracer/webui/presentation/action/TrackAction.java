/**
 * @author Jp
 *
 */
package com.tracer.webui.presentation.action;

import static com.tracer.common.Constants.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
import org.apache.struts.action.ActionMessages;
import org.json.JSONArray;
import org.json.JSONObject;

import com.tracer.dao.model.AttendanceDetails;
import com.tracer.dao.model.BeatPlanDetails;
import com.tracer.dao.model.BeatPlanDistributorAssignments;
import com.tracer.dao.model.BeatPlanUserAssignments;
import com.tracer.dao.model.CafCollectionDetails;
import com.tracer.dao.model.CafScanDetails;
import com.tracer.dao.model.DistributorDetails;
import com.tracer.dao.model.RunnerTrackDetails;
import com.tracer.dao.model.RunnerVisittedLocationDetails;
import com.tracer.dao.model.UserDetails;
import com.tracer.service.TrackManager;
import com.tracer.util.StringUtil;
import com.tracer.webui.presentation.form.TrackForm;

public class TrackAction extends BaseDispatchAction {

  protected transient final Log log = LogFactory.getLog(getClass());
  
  //========================================================================

  public ActionForward showTrackRunnersPage(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward showTrackRunnersPage");
    HttpSession session = null;
    Long runnersHeadUserId = null;
    String runnerId = null;
    TrackForm trackForm;
    
    try {
      session = request.getSession(false);
      
      if(session != null && session.getAttribute(USER_ID) != null && ((Long)session.getAttribute(USER_ID)).longValue() > 0 ) {
        trackForm = (TrackForm)form;
        BeanUtils.copyProperties(trackForm, new TrackForm());
        runnerId = request.getParameter("id");
        
        if(runnerId != null && runnerId != "") {
          runnersHeadUserId = Long.parseLong(runnerId);
          trackForm.setRunnersHeadUserId(runnersHeadUserId);
        }
        //session.setAttribute(mapping.getAttribute(), trackForm);
      } else {
        return mapping.findForward(SESSION_INACTIVE_PAGE);
      }
    } catch (Exception e) {
      log.error("Problem in the ActionForward showTrackRunnersPage"); 
      log.error(e);
      ActionMessages errors = new ActionMessages();
      saveErrors(request,errors);
      throw e;
    }
    log.info("END of the ActionForward showTrackRunnersPage");
    return mapping.findForward(TRACK_RUNNERS_PAGE);
  }

  //========================================================================
  
  public  ActionForward getRunnersDetails(ActionMapping mapping, ActionForm form, 
      HttpServletRequest request, HttpServletResponse response) throws Exception {
      log.info("START of the ActionForward getRunnersDetails");
      TrackManager trackManager = null;
      HttpSession session;
      TrackForm trackForm;
      JSONObject jsonObject = null;
      JSONArray jsonArray = new JSONArray();
      Long userId;
      String runnersHeadUserId = null;
      List<UserDetails> runners = null;
      AttendanceDetails attendanceDetails = null;
      
      try {
        session = request.getSession(false);
        if(session != null && session.getAttribute(USER_ID) != null && ((Long)session.getAttribute(USER_ID)).longValue() > 0 ) {
          trackManager = (TrackManager) getBean(TRACK_MANAGER);
          trackForm = (TrackForm)form;
          BeanUtils.copyProperties(trackForm, new TrackForm());
          runnersHeadUserId = request.getParameter("id");
          
          if(runnersHeadUserId != "" && runnersHeadUserId != null) {
            userId = Long.parseLong(runnersHeadUserId);
          } else {
            userId = (Long)session.getAttribute(USER_ID);
          }
          runners = trackManager.getRunners(userId);
          
          if(runners != null && runners.size() > 0) {
            for(int i = 0; i < runners.size(); i++) {
              jsonObject = new JSONObject();
              UserDetails user = runners.get(i);
              jsonObject.put("temLeaderCode", user.getUserCode());
              jsonObject.put("contactNo", user.getOfficePhone());
              if(user.getReportingTo() == null) {
                jsonObject.put("reportingTo", "");
              } else {
                jsonObject.put("reportingTo", user.getReportingTo());
              }
              jsonObject.put("runnerName", user.getName());
              jsonObject.put("runnerId", user.getUserId());
              attendanceDetails = trackManager.getAttendanceDetails(user.getUserId());

              if(attendanceDetails != null) {
                jsonObject.put("attendanceClass", "runner-present");
              } else {
                jsonObject.put("attendanceClass", "runner-absent");
              }
              jsonArray.put(jsonObject);
            }
          }
          response.setContentType("application/Json");
          response.getWriter().print(jsonArray);
        } else {
          return mapping.findForward(SESSION_INACTIVE_PAGE);
        }
      } catch (NullPointerException e) {
        log.error("Null pointer exception in the ActionForward getRunnersDetails"); 
        response.setContentType("application/Json");
        response.getWriter().print(jsonArray);
        log.error(e);
      }
      catch(Exception e) {
        log.error("Problem in the ActionForward getRunnersDetails");
        log.error(e);
        ActionMessages errors = new ActionMessages();
        saveErrors(request,errors);
        throw e;
      }
      log.info("END of the ActionForward getRunnersDetails");
      return null;
    }

  //========================================================================

  public ActionForward showTrackTeamLeadersPage(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward showTrackTeamLeadersPage");

    try {

    } catch (Exception e) {
      log.error("Problem in the ActionForward showTrackTeamLeadersPage"); 
      log.error(e);
      ActionMessages errors = new ActionMessages();
         saveErrors(request,errors);
            throw e;
    }
    log.info("END of the ActionForward showTrackTeamLeadersPage");
    return mapping.findForward(TRACK_TEAM_LEADERS_PAGE);
  }
/**getTeamLeaderDetails()::
 * This method gives information about all the team leaders under the current
 * logged in USER
 * 
 * @param mapping
 * @param form
 * @param request:: userId
 * @param response:: team leader details in json format
 * @return
 * @throws Exception
 */
  //========================================================================
  public  ActionForward getTeamLeaderDetails(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward getTeamLeaderDetails");
    TrackManager trackManager = null;
    HttpSession session;
    JSONObject jsonObject = null;
    JSONArray jsonArray = new JSONArray();
      
    try {
      session = request.getSession(false);
      
      if(session != null && session.getAttribute(USER_ID) != null && ((Long)session.getAttribute(USER_ID)).longValue() > 0 ) {
        trackManager = (TrackManager)getBean(TRACK_MANAGER);
        Long userId = (Long)session.getAttribute(USER_ID);
        List<UserDetails> teamleaders = trackManager.getTeamLeaders(userId);

        if(teamleaders != null) {
          for(int i=0; i < teamleaders.size(); i++) {
            jsonObject = new JSONObject();
            UserDetails user = teamleaders.get(i);
            jsonObject.put("temLeaderCode", user.getUserCode());
            jsonObject.put("contactNo", user.getOfficePhone());
            
            if(user.getReportingTo() == null) {
              jsonObject.put("reportingTo", "");
            } else {
              jsonObject.put("reportingTo", user.getReportingTo());
            }
            jsonObject.put("runnerName", user.getName());
            jsonObject.put("runnerId", user.getUserId());
            jsonArray.put(jsonObject);
          }
        }
        response.setContentType("application/Json");
        response.getWriter().print(jsonArray);
      } else {
        return mapping.findForward(SESSION_INACTIVE_PAGE);    
      }
    } catch (NullPointerException e) {
      log.error("Null pointer exception in the ActionForward getTeamLeaderDetails"); 
      response.setContentType("application/Json");
      response.getWriter().print(jsonArray);
      log.error(e);
    } catch(Exception e) {
      log.error("Problem in the ActionForward getTeamLeaderDetails");
      log.error(e);
      ActionMessages errors = new ActionMessages();
      saveErrors(request,errors);
      throw e;
    }
    log.info("END of the ActionForward getTeamLeaderDetails");
    return null;
  }
  
  //========================================================================

  @SuppressWarnings("unchecked")
  public ActionForward showRunnerCAFCollectionDetailsPage(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward showRunnerCAFCollectionDetailsPage");
    TrackManager trackManager = null;
    HttpSession session = null;
    Long runnerUserId = null;
    String runnerId = null;
    Map<String, Object> cafCollectionsDetails = null;
    Iterator<String> cafIterator = null;
    TrackForm trackForm = null;
    List<RunnerTrackDetails> runnerTrackDetails, runnerTrackDetailsModifiedList = null;
    List<UserDetails> runnersList = null;
    Long userId = null;
    int distributorNo = 0;
    String dateOfTrack = null;
    String trackDateInDBFormat = null;
    List<RunnerVisittedLocationDetails> runnervisittedLocationDetails = null;
    

    try {
      session = request.getSession(false);
      
      if(session != null && session.getAttribute(USER_ID) != null && ((Long)session.getAttribute(USER_ID)).longValue() > 0 ) {
        trackManager = (TrackManager) getBean(TRACK_MANAGER);
        runnerId = request.getParameter("id");
        runnerUserId = Long.parseLong(runnerId);
        trackForm = (TrackForm) form;
        dateOfTrack = trackForm.getRunnerDate();
        BeanUtils.copyProperties(trackForm, new TrackForm());
        Date date = new Date();
        runnerTrackDetails = new ArrayList<RunnerTrackDetails>();
        runnerTrackDetailsModifiedList = new ArrayList<RunnerTrackDetails>();
        
        if(StringUtil.isNull(dateOfTrack)) {
          dateOfTrack = new SimpleDateFormat(DATE_FORMAT).format(date);
        }
        trackForm.setRunnerDate(dateOfTrack);
        trackForm.setRunnerTime(new SimpleDateFormat("hh:mm").format(date));
        trackDateInDBFormat = convertUIDateToDBDate(dateOfTrack);
        cafCollectionsDetails = trackManager.getRunnerCAFCollectionsDetails(runnerUserId, trackDateInDBFormat);
        
        if(cafCollectionsDetails != null) {
          cafIterator = cafCollectionsDetails.keySet().iterator();
          
          while(cafIterator.hasNext()) {
            trackForm.setRunnerId(runnerId);
            String key = (String) cafIterator.next();
            
            if( cafCollectionsDetails.get(key) != null) {
              String value = cafCollectionsDetails.get(key).toString();
              
              if(key == HUB_NAME) {
                trackForm.setHubName(value);
              } else if(key == RUNNER_NAME) {
                trackForm.setRunnerName(value);
              } else if(key == RUNNER_CODE) {
                trackForm.setRunnerCode(value);
              } else if(key == BEAT_PLAN_NAME) {
                  trackForm.setBeatPlanName(value);
              } else if(key == RUNNER_VISITED_LOCATION_LIST) {
                runnervisittedLocationDetails = (List<RunnerVisittedLocationDetails>) cafCollectionsDetails.get(key);
                //trackForm.setRunnervisittedLocationDetails(runnervisittedLocationDetails);
              } else if(key == RUNNER_TRACK_DETAILS_LIST) {
                runnerTrackDetails = (List<RunnerTrackDetails>) cafCollectionsDetails.get(key);
                
                for (int i = 0; i < runnerTrackDetails.size(); i++) {
                  RunnerTrackDetails runnerDetails = runnerTrackDetails.get(i);
                  Date scheduleTime = runnerDetails.getScheduleTime();
                  
                  if(scheduleTime != null) {
                    String time = new SimpleDateFormat("HH:mm").format(runnerDetails.getScheduleTime());
                    String formattedDate = /*modifiedDate+ " "+ */time;
                    runnerDetails.setScheduleDateTime(formattedDate);
                  }
                  Date visittedTime = runnerDetails.getVisittedTime();
                  
                  if(visittedTime != null) {
                    String time = new SimpleDateFormat("HH:mm").format(runnerDetails.getVisittedTime());
                    String  visitedDate = time;
                    runnerDetails.setVisittedDateTime(visitedDate);
                  }
                  Long delayTime = runnerDetails.getDelayTime();

                  if(delayTime != null) {
                    String delayHours = getTwoDigitNumber(Math.abs(delayTime/60))+":"+getTwoDigitNumber(Math.abs(delayTime%60));
                    runnerDetails.setDelayHours(delayHours.concat(" mins"));
                  }
                  runnerDetails.setDistributorLattitude(runnerDetails.getDistributorLattitude());
                  runnerDetails.setDistributorLongitude(runnerDetails.getDistributorLongitude());
                  String distName = runnerDetails.getDistributorName();
                  
                  if(distName.contains("[")) {
                    distName = distName.replace("[", " "); 
                  }
                  
                  if(distName.contains("]")) {
                    distName = distName.replace("]", "");
                  }
                  runnerDetails.setDistributorName(distName);
                  distributorNo = distributorNo + 1;
                  runnerDetails.setDistributorNo(distributorNo);
                  runnerTrackDetailsModifiedList.add(runnerDetails);
                }
                //trackForm.setRunnerTrackDetails(runnerTrackDetailsModifiedList);
              }
            }
          }
        }
        userId = (Long)session.getAttribute(USER_ID);
        
        if(userId != null) {
          runnersList = trackManager.getRunners(userId);
          trackForm.setRunnersList(runnersList);
          trackForm.setRunnerUserId(runnerUserId);
        }
        trackForm.setRunnervisittedLocationDetails(runnervisittedLocationDetails);
        trackForm.setRunnerTrackDetails(runnerTrackDetailsModifiedList);
        //session.setAttribute(mapping.getAttribute(), trackForm);
      } else {
        return mapping.findForward(SESSION_INACTIVE_PAGE);
      }
    } catch (Exception e) {
      log.error("Problem in the ActionForward showRunnerCAFCollectionDetailsPage");
      log.error(e);
      e.printStackTrace();
      ActionMessages errors = new ActionMessages();
      saveErrors(request,errors);
      throw e;
    }
    log.info("END of the ActionForward showRunnerCAFCollectionDetailsPage");
    return mapping.findForward(RUNNER_CAF_COLLECTION_DETAILS_PAGE);
  }
  
  //========================================================================
  
  private String convertUIDateToDBDate(String uiDate) {
    String dbDate = null;
    Date uDate = null;
    DateFormat udf = new SimpleDateFormat(DATE_FORMAT);
    DateFormat dbdf = new SimpleDateFormat(DATE_FORMAT_DB);
    
    try {
      if(StringUtil.isNotNull(uiDate)) {
        uDate = udf.parse(uiDate);
      } else {
        uDate = new Date();
      }
      dbDate = dbdf.format(uDate);
    } catch (ParseException e) {
        e.printStackTrace();
    }
    return dbDate;
  }
  
  //========================================================================
  /**
   * getTwoDigitNumber()
   * It is used to get the digit as two digit number.
   * When number is less than 10, 0 is added.
   * @param  Long
   * @return String
   */
  public String getTwoDigitNumber(Long number) {
    if(number < 10) {
      return "0"+number;
    }
    return number.toString();
  }

  //========================================================================
/**getRunnerBeatPlanDetails()::
 * This method gives information about the beat plan details for the 
 * runner Id. 
 * @param mapping
 * @param form: 
 * @param request:: runner Id
 * @param response:: Beat Plan details in JSON format
 * @return
 * @throws Exception
 */
  @SuppressWarnings("unchecked")
  public  ActionForward getRunnerBeatPlanDetails(ActionMapping mapping, ActionForm form, 
      HttpServletRequest request, HttpServletResponse response) throws Exception {
      log.info("START of the ActionForward getRunnerBeatPlanDetails");
      TrackManager trackManager = null;
      HttpSession session;
      JSONObject jsonObject = null;
      JSONArray jsonArray = null;
      String runnerId = null;
      Long runnerUserId;
      Map<String, Object> planDetails = null;
      Iterator<String> beatPlanIterator = null;
      List<BeatPlanUserAssignments> beatPlanUserAssignments = null;
      BeatPlanDetails beatPlanDetails = null;
      
      try {
          session = request.getSession(false);
          if(session != null && session.getAttribute(USER_ID) != null && ((Long)session.getAttribute(USER_ID)).longValue() > 0 ) {
          jsonObject = new JSONObject();
          jsonArray = new JSONArray();
          beatPlanUserAssignments = new ArrayList<BeatPlanUserAssignments>();
          beatPlanDetails = new BeatPlanDetails();
          trackManager = (TrackManager)getBean(TRACK_MANAGER);

          runnerId = request.getParameter("runnerId");
          runnerUserId = Long.parseLong(runnerId);
          
          if(runnerId != null && runnerId != "") {
            planDetails = trackManager.getRunnerBeatPlan(runnerUserId);
            if(planDetails != null) {
              beatPlanIterator = planDetails.keySet().iterator();
              while(beatPlanIterator.hasNext()){
                String key = (String)beatPlanIterator.next();

                if(key != null) {
                    if(key == BEAT_PLAN_TO_USER_DETAILS) {
                      beatPlanUserAssignments = (List<BeatPlanUserAssignments>)planDetails.get(key);
                      for(int i=0; i < beatPlanUserAssignments.size();i++) {
                        BeatPlanUserAssignments userAssignments = beatPlanUserAssignments.get(i);
                        jsonObject.put("runnerId", runnerUserId);
                        //Set Circle Name
                        String circleName = userAssignments.getCircleName();
                        if(circleName != "" && circleName != null) {
                          jsonObject.put("circleName", circleName);
                        }
                        else {
                          jsonObject.put("circleName", "");
                        }

                        //Set Zone Name
                        String zoneName = userAssignments.getZoneName();
                        if(zoneName != "" && zoneName != null) {
                          jsonObject.put("zoneName", zoneName);
                        }
                        else {
                          jsonObject.put("zoneName", "");
                        }

                        //Set Region Name
                        String regionName = userAssignments.getRegionName();
                        if(regionName != "" && regionName != null) {
                          jsonObject.put("regionName", regionName);
                        }
                        else {
                          jsonObject.put("regionName", "");
                        }
                        
                        //Set Hub Name
                        String hubName = userAssignments.getHubName();
                        if(hubName != "" && hubName != null) {
                          jsonObject.put("hubName", hubName);
                        }
                        else {
                          jsonObject.put("hubName", "");
                        }

                        //set Schedule Time
                        Date scheduleTime =userAssignments.getScheduleTime();
                        if(scheduleTime != null) {
                          String time = new SimpleDateFormat("HH:mm").format(scheduleTime);
                          if(time != "" && time != null) {
                            jsonObject.put("scheduleTime", time);
                          }
                          else {
                            jsonObject.put("scheduleTime", "");
                          }
                        }
                        else {
                          jsonObject.put("scheduleTime", "");
                        }
                        BeatPlanDistributorAssignments  beatPlanDistributorAssignments = userAssignments.getBeatPlanDistributorAssignments();
                        //Set visit Frequency
                        String visitFrequency = beatPlanDistributorAssignments.getVisitFrequency();
                        if(visitFrequency != null) {
                          jsonObject.put("visitFrequency", visitFrequency);
                        }
                        else {
                          jsonObject.put("visitFrequency", "");
                        }
                        jsonObject.put("visitNo", userAssignments.getVisitNo());
                        DistributorDetails distributorDetails = beatPlanDistributorAssignments.getDistributorDetails();
                        //Set Distributor Name
                        String distributorName = distributorDetails.getDistributorName();
                        if(distributorName != null) {
                          jsonObject.put("distributorName", distributorName);
                        }
                        else {
                          jsonObject.put("distributorName", "");
                        }
                        jsonArray.put(jsonObject);
                        jsonObject = new JSONObject();
                      }
                    }
                    else if(key == BEAT_PLAN_DETAILS) {
                      beatPlanDetails = (BeatPlanDetails)planDetails.get(key);
                      if(beatPlanDetails != null) {
                        String beatPlanName = beatPlanDetails.getBeatPlanName();
                        if(beatPlanName != null) {
                          jsonObject.put("beatPlanName", beatPlanName);
                        }
                        else {
                          jsonObject.put("beatPlanName", "");
                        }
                      }
                      else {
                        jsonObject.put("beatPlanName", "");
                      }
                      jsonArray.put(jsonObject);
                      jsonObject = new JSONObject();
                    }
                }
              }
              response.setContentType("application/Json");
              response.getWriter().print(jsonArray);
            }

          }
        }
        else {
          return mapping.findForward(SESSION_INACTIVE_PAGE);
        }
      } catch (NullPointerException e) {
        log.error("Null pointer exception in the ActionForward getRunnerBeatPlanDetails"); 
        response.setContentType("application/Json");
        response.getWriter().print(jsonArray);
        log.error(e);
        ActionMessages errors = new ActionMessages();
         saveErrors(request,errors);
            throw e;
      }
      catch(Exception e) {
        log.error("Problem in the ActionForward getRunnerBeatPlanDetails");
        log.error(e);
        ActionMessages errors = new ActionMessages();
         saveErrors(request,errors);
            throw e;
      }
      log.info("END of the ActionForward getRunnerBeatPlanDetails");
      return null;
    }
  
  //========================================================================

  public  ActionForward getCAFDetails(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward getCAFDetails");
    TrackManager trackManager = null;
    HttpSession session;
    JSONObject jsonObject = null;
    String cafId = null;
    Long cafCollectionId;
    CafCollectionDetails cafCollectionDetails = null;
    String distributorPhoto = null;
    String distributorSignature = null;
    String runnerPhoto = null;
    TrackForm trackForm = null;
    List<CafScanDetails> scannedCafs = null;
    String need = null;
    boolean doDBCall = false;
    
    try {
      session = request.getSession(false);
      
      if(session != null && session.getAttribute(USER_ID) != null && ((Long)session.getAttribute(USER_ID)).longValue() > 0 ) {
        jsonObject = new JSONObject();
        trackManager = (TrackManager) getBean(TRACK_MANAGER);
        trackForm = (TrackForm) form;
        cafId = request.getParameter("cafId");
        need = request.getParameter("need");
        cafCollectionDetails = trackForm.getCafCollectionDetails();
        
        if(StringUtil.isNotNull(cafId) && StringUtil.isNotNull(need)) {
          cafCollectionId = Long.parseLong(cafId);
          if(cafCollectionDetails == null) {
            doDBCall = true;
          } else if(cafCollectionDetails.getCafCollectionId().longValue() != cafCollectionId) {
            doDBCall = true;  
          } else {
            doDBCall = false;  
          }
          if(doDBCall) {
            cafCollectionDetails  = trackManager.getCAFDetails(cafCollectionId);
            trackForm.setCafCollectionDetails(cafCollectionDetails);
          }
          if(cafCollectionDetails != null) {
            if("distPhoto".equalsIgnoreCase(need)) {
              distributorPhoto = cafCollectionDetails.getDistributorPhotoPath();
              if(StringUtil.isNotNull(distributorPhoto)) 
                jsonObject.put("distributorPhoto", distributorPhoto);
              else
                jsonObject.put("distributorPhoto", "");
            } else if("digSign".equalsIgnoreCase(need)) {
              distributorSignature = cafCollectionDetails.getDstributorSignPath();
              if(StringUtil.isNotNull(distributorSignature))
                jsonObject.put("distributorSignature", distributorSignature);
              else
                jsonObject.put("distributorSignature", "");
            } else if("runnerPhoto".equalsIgnoreCase(need)) {
              runnerPhoto = cafCollectionDetails.getRunnerPhotoPath();
              if(StringUtil.isNotNull(runnerPhoto))
                jsonObject.put("runnerPhoto", runnerPhoto);
              else
                jsonObject.put("runnerPhoto", "");
            } else if("scannedCAFs".equalsIgnoreCase(need)) {
              scannedCafs = cafCollectionDetails.getScannedCafs();
              if(scannedCafs != null && scannedCafs.size() > 0) {
                JSONArray scannedCAFJSONArray = new JSONArray();
                for(CafScanDetails cafScanDetails : scannedCafs) {
                  JSONObject scannedCAFJSON = new JSONObject();
                  scannedCAFJSON.put("barCode", cafScanDetails.getCafBarCode());
                  scannedCAFJSON.put("cafScanImagePath", cafScanDetails.getCafScanImagePath());
                  scannedCAFJSONArray.put(scannedCAFJSON);
                }
                jsonObject.put("scannedCAFs", scannedCAFJSONArray);
              } else { 
                jsonObject.put("scannedCAFs", "");
              }
            }
          }
        }
        response.setContentType("application/Json");
        response.getWriter().print(jsonObject);
      } else {
        return mapping.findForward(SESSION_INACTIVE_PAGE);
      }
    } catch (NullPointerException e) {
      log.error("Null pointer exception in the ActionForward getCAFDetails"); 
      response.setContentType("application/Json");
      response.getWriter().print(jsonObject);
      log.error(e);
      ActionMessages errors = new ActionMessages();
      saveErrors(request,errors);
      throw e;
    } catch(Exception e) {
      log.error("Problem in the ActionForward getCAFDetails");
      log.error(e);
      ActionMessages errors = new ActionMessages();
      saveErrors(request,errors);
      throw e;
    }
    log.info("END of the ActionForward getCAFDetails");
    return null;
  }
    
  //==========================================================================
    
  public ActionForward getRunnerAttendanceDetails(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward getRunnerAttendanceDetails");
    TrackManager trackManager = null;
    HttpSession session;
    JSONObject jsonObject = new JSONObject();
    String runnerUserId = null;
    AttendanceDetails attendanceDetails = null;
    boolean isRunnerPresent = false;
    String runnerPhoto = "";
    String time = "";
    String lattitude = "";
    String longitude = "";
    String location = "";
    DateFormat timeFormat = null;
    
    try {
      session = request.getSession(false);
      
      if(session != null && session.getAttribute(USER_ID) != null && ((Long)session.getAttribute(USER_ID)).longValue() > 0 ) {
        jsonObject = new JSONObject();
        trackManager = (TrackManager) getBean(TRACK_MANAGER);
        runnerUserId = request.getParameter("runnerUserId");
       
        if(StringUtil.isNotNull(runnerUserId)) {
          attendanceDetails = trackManager.getAttendanceDetails(Long.parseLong(runnerUserId));
         
          if(attendanceDetails != null) {
            isRunnerPresent = true;
            runnerPhoto = attendanceDetails.getUserPhotoPath();
            timeFormat = new SimpleDateFormat("hh:mm:ss a");
            time = timeFormat.format(attendanceDetails.getDateTime());
            lattitude = attendanceDetails.getLattitude();
            longitude = attendanceDetails.getLongitude();
            
            if(StringUtil.isNotNull(attendanceDetails.getLocation())) {
              location = attendanceDetails.getLocation();
            }
          }
        }
        jsonObject.put("isRunnerPresent", isRunnerPresent);
        jsonObject.put("runnerPhoto", runnerPhoto);
        jsonObject.put("time", time);
        jsonObject.put("lattitude", lattitude);
        jsonObject.put("longitude", longitude);
        jsonObject.put("location", location);
        response.setContentType("application/Json");
        response.getWriter().print(jsonObject);
      } else {
        return mapping.findForward(SESSION_INACTIVE_PAGE);
      }
    } catch (Exception e) {
      log.error("Problem in the ActionForward getRunnerAttendanceDetails");
      log.error(e);
    }
    log.info("END of the ActionForward getRunnerAttendanceDetails");
    return null;
  }
    
  //==========================================================================
  
  public ActionForward downloadReport(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward downloadReport");
    HttpSession session;
    ServletOutputStream servletOutputStream = null;
    StringBuffer stringBuffer = null;
    InputStream inputStream = null;
    TrackForm trackForm = null;
    StringBuffer contentDisposition = new StringBuffer("attachment;filename=");
    
    try {
      session = request.getSession(false);
      
      if(session != null && session.getAttribute(USER_ID) != null && ((Long)session.getAttribute(USER_ID)).longValue() > 0 ) {
        trackForm = (TrackForm) form;
        
        if(StringUtil.isNotNull(trackForm.getRunnerName())) {
          contentDisposition.append(trackForm.getRunnerName().replaceAll(" ", "_").toUpperCase());
          contentDisposition.append("_CAF_REPORT.csv");
          //tell browser program going to return an application file instead of html page
          response.setContentType("application/octet-stream");
          response.setHeader("Content-Disposition", contentDisposition.toString());
          servletOutputStream = response.getOutputStream();
          stringBuffer = generateCsvFileBuffer(trackForm);
          inputStream =  new ByteArrayInputStream(stringBuffer.toString().getBytes("UTF-8"));
     
          byte[] outputByte = new byte[4096];
          //copy binary content to output stream
          while(inputStream.read(outputByte, 0, 4096) != -1) {
            servletOutputStream.write(outputByte, 0, 4096);
          }
          inputStream.close();
          servletOutputStream.flush();
          servletOutputStream.close();
        }
      } else {
        return mapping.findForward(SESSION_INACTIVE_PAGE);
      }
    } catch (Exception e) {
      log.error("Problem in the ActionForward downloadReport");
      log.error(e);
    }
    log.info("END of the ActionForward downloadReport");
    return null;
  }
    
  //==========================================================================
  
  private static StringBuffer generateCsvFileBuffer(TrackForm trackForm) {
    List<RunnerTrackDetails> runnerTrackDetailsList = null;
    StringBuffer writer = new StringBuffer();
    String runnerName = null;
    String hubName = null;
    
    try {
      runnerTrackDetailsList = trackForm.getRunnerTrackDetails();
      runnerName = trackForm.getRunnerName();
      hubName = trackForm.getHubName();
      writer.append("Runner Name");
      writer.append(',');
      writer.append("Hub Name");
      writer.append(',');
      writer.append("Distributor Name");
      writer.append(',');
      writer.append("OFSC Code");
      writer.append(',');
      writer.append("Lattitude");
      writer.append(',');
      writer.append("Longitude");
      writer.append(',');
      writer.append("No Of CAFs collected");
      writer.append(',');
      writer.append("CAF Submission Time");
      writer.append('\n');
        
      if(runnerTrackDetailsList != null && runnerTrackDetailsList.size() > 0) {
        for(RunnerTrackDetails runnerTrackDetails : runnerTrackDetailsList) {
          int cafCount = 0;
          if(runnerTrackDetails.getTotalCafCount() != null && runnerTrackDetails.getTotalCafCount().intValue() > 0) {
            cafCount = runnerTrackDetails.getTotalCafCount().intValue();
          }
          String visittedDateTime = "";
          if(StringUtil.isNotNull(trackForm.getRunnerDate()) && StringUtil.isNotNull(runnerTrackDetails.getVisittedDateTime())) {
            visittedDateTime = trackForm.getRunnerDate().concat(" ").concat(runnerTrackDetails.getVisittedDateTime());
          }
          writer.append(runnerName);
          writer.append(',');
          writer.append(hubName);
          writer.append(',');
          writer.append(runnerTrackDetails.getDistributorName());
          writer.append(',');
          writer.append(runnerTrackDetails.getDistributorOfscCode());
          writer.append(',');
          writer.append(runnerTrackDetails.getDistributorLattitude());
          writer.append(',');
          writer.append(runnerTrackDetails.getDistributorLongitude());
          writer.append(',');
          writer.append(cafCount);
          writer.append(',');
          writer.append(visittedDateTime);
          writer.append('\n');	
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return writer;
  }
  
  //==========================================================================
  
  public  ActionForward getScannedCAFImagePath(ActionMapping mapping, ActionForm form, 
    HttpServletRequest request, HttpServletResponse response) throws Exception {
    log.info("START of the ActionForward getCAFImagePath");
    HttpSession session;
    JSONObject jsonObject = null;
    CafCollectionDetails cafCollectionDetails = null;
    TrackForm trackForm = null;
    List<CafScanDetails> scannedCafs = null;
    String index = null;
    
    try {
      session = request.getSession(false);
      
      if(session != null && session.getAttribute(USER_ID) != null && ((Long)session.getAttribute(USER_ID)).longValue() > 0 ) {
        jsonObject = new JSONObject();
        trackForm = (TrackForm) form;
        cafCollectionDetails = trackForm.getCafCollectionDetails();
        index = request.getParameter("index");
        
        if(cafCollectionDetails != null && StringUtil.isNotNull(index)) {
          scannedCafs = cafCollectionDetails.getScannedCafs();
          
          if(scannedCafs != null && scannedCafs.size() > 0) {
            jsonObject.put("scannedCAFImagePath", scannedCafs.get(Integer.parseInt(index)).getCafScanImagePath());
          } else { 
            jsonObject.put("scannedCAFImagePath", "");
          }
        } else {
          jsonObject.put("scannedCAFImagePath", "");
        }
        response.setContentType("application/Json");
        response.getWriter().print(jsonObject);
      } else {
        return mapping.findForward(SESSION_INACTIVE_PAGE);
      }
    } catch (NullPointerException e) {
      log.error("Null pointer exception in the ActionForward getCAFImagePath"); 
      response.setContentType("application/Json");
      response.getWriter().print(jsonObject);
      log.error(e);
      ActionMessages errors = new ActionMessages();
      saveErrors(request,errors);
      throw e;
    } catch(Exception e) {
      log.error("Problem in the ActionForward getCAFImagePath");
      log.error(e);
      ActionMessages errors = new ActionMessages();
      saveErrors(request,errors);
      throw e;
    }
    log.info("END of the ActionForward getCAFImagePath");
    return null;
  }
    
  //==========================================================================
}
