/**
 * @author Jp
 *
 */
package com.tracer.dao.persistence.impl;

import static com.tracer.common.Constants.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;

import com.tracer.dao.model.AttendanceDetails;
import com.tracer.dao.model.BeatPlanDetails;
import com.tracer.dao.model.BeatPlanUserAssignments;
import com.tracer.dao.model.CafCollectionDetails;
import com.tracer.dao.model.CafScanDetails;
import com.tracer.dao.model.DistributorDetails;
import com.tracer.dao.model.DistributorToHubDetails;
import com.tracer.dao.model.HubDetails;
import com.tracer.dao.model.RunnerCAFTrackDetails;
import com.tracer.dao.model.RunnerTrackDetails;
import com.tracer.dao.model.RunnerVisittedLocationDetails;
import com.tracer.dao.model.UserDetails;
import com.tracer.dao.model.UserReportingToDetails;
import com.tracer.dao.model.UserToHubDetails;
import com.tracer.dao.persistence.TrackDAO;

public class TrackDAOHibernate extends BaseDAOHibernate implements TrackDAO {
  protected transient final Log log = LogFactory.getLog(TrackDAOHibernate.class);

  //========================================================================
  
  @SuppressWarnings("unchecked")
  @Override
  public List<UserDetails> getRunners(Long tlUserId) throws Exception {
    log.info("START of the method getRunners");
    StringBuffer hqlQuery;
    List<UserReportingToDetails> resultList = null;
    List<UserDetails> runners = null;
    
    try {
      hqlQuery = new StringBuffer("SELECT userReportingToDetails FROM UserReportingToDetails userReportingToDetails WHERE ");
      hqlQuery.append(" userReportingToDetails.status='"+ACTIVE+"' AND ");
      hqlQuery.append(" userReportingToDetails.userDetails.userId="+tlUserId);
      hqlQuery.append(" AND userReportingToDetails.userDetails.roleDetails.roleId="+TEAM_LEADER);
      resultList = getHibernateTemplate().find(hqlQuery.toString());    
      
      if(resultList != null && resultList.size() > 0) {
        runners = new ArrayList<>();
        
        for(UserReportingToDetails details : resultList) {
          if(details != null) {
            UserDetails runnerDetails = (UserDetails) getHibernateTemplate().get(UserDetails.class, details.getUserId());
            runnerDetails.setReportingTo(details.getUserDetails().getName().concat(" ").concat(details.getUserDetails().getLastName()));
            runners.add(runnerDetails);
          }
        }
      }
    } catch (Exception e) {
        log.error("PROBLEM in the method getRunners");
        log.error(e);
        e.printStackTrace();
    }
    log.info("END of the method getRunners");
    return runners;
  }

  //========================================================================
  
  @SuppressWarnings("unchecked")
  @Override
  public List<UserDetails> getTeamLeaders(Long managerUserId) throws Exception {
    log.info("START of the method getRunners");
    StringBuffer hqlQuery;
    List<UserReportingToDetails> resultList = null;
    List<UserDetails> teamLeaders = null;
    
    try {
      hqlQuery = new StringBuffer("SELECT userReportingToDetails FROM UserReportingToDetails userReportingToDetails WHERE ");
      hqlQuery.append(" userReportingToDetails.status='"+ACTIVE+"' AND ");
      hqlQuery.append(" userReportingToDetails.userDetails.userId="+managerUserId);
      hqlQuery.append(" AND userReportingToDetails.userDetails.roleDetails.roleId="+HUB_MANAGER);
      resultList = getHibernateTemplate().find(hqlQuery.toString());    
      
      if(resultList != null && resultList.size() > 0) {
        teamLeaders = new ArrayList<>();
        
        for(UserReportingToDetails details : resultList) {
          if(details != null) {
            UserDetails teamLeaderDetails = (UserDetails) getHibernateTemplate().get(UserDetails.class, details.getUserId());
            teamLeaderDetails.setReportingTo(details.getUserDetails().getName().concat(" ").concat(details.getUserDetails().getLastName()));
            teamLeaders.add(teamLeaderDetails);
          }
        }
      }
    } catch (Exception e) {
        log.error("PROBLEM in the method getRunners");
        log.error(e);
        e.printStackTrace();
    }
    log.info("END of the method getRunners");
    return teamLeaders;
  }

  //========================================================================
  
  @SuppressWarnings("unchecked")
  @Override
  public Map<String, Object> getRunnerCAFCollectionsDetails(Long runnerUserId, String dateOfTrack) throws Exception {
    log.info("START of the method getRunnerCAFCollectionsDetails");
    StringBuffer nativeQuery;
    Query hibQuery = null;
    @SuppressWarnings("rawtypes")
    List resultList = null;
    StringBuffer hqlQuery = null;
    List<BeatPlanUserAssignments> beatPlanUserAssignments = null;
    List<RunnerTrackDetails> runnerTrackDetailsList = null;
    Map<String, Object> runnerCAFCollectionsDetails = new HashMap<String, Object>();
    String beatPlanName = null;
    String hubName = null;
    List<UserToHubDetails> userToHubDetailsList = null;
    HubDetails hubDetails = null;
    String runnerName = null;
    String runnerCode = null;
    List<RunnerVisittedLocationDetails> runnerVisittedLocationsList = null;
    
    try {
      if(runnerUserId != null && runnerUserId.longValue() > 0) {
        hqlQuery = new StringBuffer("SELECT uthd from UserToHubDetails uthd WHERE uthd.userDetails.userId="+runnerUserId);
        hqlQuery.append(" and uthd.status='"+ACTIVE+"'");
        userToHubDetailsList = getHibernateTemplate().find(hqlQuery.toString());
        
        if(userToHubDetailsList != null && userToHubDetailsList.size() > 0) {
          hubDetails = userToHubDetailsList.get(0).getHubDetails();
          hubName = hubDetails.getHubName();
          runnerName = userToHubDetailsList.get(0).getUserDetails().getName().concat(" ").concat(userToHubDetailsList.get(0).getUserDetails().getLastName());
          runnerCode = userToHubDetailsList.get(0).getUserDetails().getUserCode();
        }
        runnerCAFCollectionsDetails.put(HUB_NAME, hubName);
        runnerCAFCollectionsDetails.put(RUNNER_NAME, runnerName);
        runnerCAFCollectionsDetails.put(RUNNER_CODE, runnerCode);
          
        hqlQuery = new StringBuffer("SELECT bpua from BeatPlanUserAssignments as bpua where ");
        hqlQuery.append(" bpua.userDetails.userId="+runnerUserId);
        hqlQuery.append(" and bpua.status='"+ACTIVE+"' ");
        hqlQuery.append(" order by bpua.scheduleTime");
        beatPlanUserAssignments = getHibernateTemplate().find(hqlQuery.toString());
        
        if(beatPlanUserAssignments != null && beatPlanUserAssignments.size() > 0) {
          runnerTrackDetailsList = new ArrayList<RunnerTrackDetails>();
          
          for(BeatPlanUserAssignments bpUserAssignments : beatPlanUserAssignments) {
            beatPlanName = bpUserAssignments.getBeatPlanDistributorAssignments().getBeatPlanDetails().getBeatPlanName();  
            RunnerTrackDetails runnerTrackDetails = new RunnerTrackDetails();
            DistributorDetails distributorDetails = bpUserAssignments.getBeatPlanDistributorAssignments().getDistributorDetails();
            runnerTrackDetails.setBeatPlanUserAssignmentId(bpUserAssignments.getBpUserAssignmentId());
            runnerTrackDetails.setDistributorName(distributorDetails.getDistributorName());
            runnerTrackDetails.setScheduleTime(bpUserAssignments.getScheduleTime());
            runnerTrackDetails.setDistributorLattitude(distributorDetails.getLattitude());
            runnerTrackDetails.setDistributorLongitude(distributorDetails.getLongitude());
            runnerTrackDetails.setDistributorOfscCode(distributorDetails.getOfscCode());
            runnerTrackDetailsList.add(runnerTrackDetails);
          }
          /*select bpua.bp_user_assignment_id, bpua.user_id, bpua.schedule_time, time(ccd.date_time) as time,
          TIMEDIFF(bpua.schedule_time, time(ccd.date_time)) as diff, dd.distributor_name, ccd.total_caf_count,rvd.visit_no, 
          ccd.date_time, ccd.caf_collection_id
          from caf_collection_details as ccd, runner_visit_details as rvd, beat_plan_user_assignments as bpua, 
          beat_plan_distributor_assignments bpda, distributor_details as dd
          where ccd.runner_visit_id=rvd.runner_visit_id and rvd.bp_user_assignment_id=bpua.bp_user_assignment_id
          and bpda.bp_dist_assignments_id=bpua.bp_dist_assignment_id and bpda.distributor_id=dd.distributor_id
          and bpua.user_id=33
          and DATE(ccd.date_time)=DATE(now()) order by ccd.date_time;*/
          nativeQuery = new StringBuffer(" select bpua.bp_user_assignment_id, bpua.user_id, bpua.schedule_time, time(ccd.date_time) as time, ");
          nativeQuery.append(" TIMEDIFF(bpua.schedule_time, time(ccd.date_time)) as diff, dd.distributor_name, ccd.total_caf_count, rvd.visit_no, ");
          nativeQuery.append(" ccd.date_time, ccd.caf_collection_id, ccd.remarks");
          nativeQuery.append(" from caf_collection_details as ccd, runner_visit_details as rvd, beat_plan_user_assignments as bpua,  ");
          nativeQuery.append(" beat_plan_distributor_assignments bpda, distributor_details as dd ");
          nativeQuery.append(" where ccd.runner_visit_id=rvd.runner_visit_id and rvd.bp_user_assignment_id=bpua.bp_user_assignment_id ");
          nativeQuery.append(" and bpda.bp_dist_assignments_id=bpua.bp_dist_assignment_id and bpda.distributor_id=dd.distributor_id ");
          nativeQuery.append(" and bpua.user_id="+runnerUserId);
          //nativeQuery.append(" and DATE(ccd.date_time) = DATE(NOW()) ");
          nativeQuery.append(" and cast(ccd.date_time as date) = '"+dateOfTrack+"'"); 
          nativeQuery.append(" order by ccd.date_time");
          hibQuery = getSession().createSQLQuery(nativeQuery.toString());
          resultList = hibQuery.list();
          Date scheduleTime = null;
          Date visittedTime = null;
          Date cafCollectedDateTime = null;
          Long bpUserAssignmentId = null;
          
        
          if(resultList != null && resultList.size() > 0) {
            for(int i = 0; i < resultList.size(); i++) {
              Object[] result = (Object[]) resultList.get(i);
              bpUserAssignmentId = Long.parseLong(result[0].toString());
              scheduleTime = (Date) result[2];
              visittedTime = (Date) result[3];
              cafCollectedDateTime = (Date) result[8];

              for(RunnerTrackDetails runnerTrackDetails : runnerTrackDetailsList) {
                if(runnerTrackDetails.getBeatPlanUserAssignmentId().longValue() == bpUserAssignmentId.longValue()) {
                  runnerTrackDetails.setDelayTime(getDelayTime(scheduleTime, visittedTime));
                  runnerTrackDetails.setEarlyVisit(scheduleTime.after(visittedTime)); 
                  runnerTrackDetails.setVisittedTime(cafCollectedDateTime);
                  runnerTrackDetails.setTotalCafCount(Integer.parseInt(result[6].toString()));
                  runnerTrackDetails.setCafCollectionId(Long.parseLong(result[9].toString()));
                  if(result[10] != null) {
                    runnerTrackDetails.setRemarks(result[10].toString());
                  }
                }
              }
            }
          }
          runnerCAFCollectionsDetails.put(RUNNER_TRACK_DETAILS_LIST, runnerTrackDetailsList);
        }
        hqlQuery = new StringBuffer("SELECT rvld from RunnerVisittedLocationDetails as rvld where rvld.userDetails.userId="+runnerUserId);
        hqlQuery.append(" and rvld.lattitude !=0.0");
        //hqlQuery.append(" and DATE(rvld.dateTime) = DATE(NOW())");
        hqlQuery.append(" and cast(rvld.dateTime as date) = '"+dateOfTrack+"'"); 
        hqlQuery.append(" order by rvld.dateTime");
        runnerVisittedLocationsList = getHibernateTemplate().find(hqlQuery.toString());
        runnerCAFCollectionsDetails.put(RUNNER_VISITED_LOCATION_LIST, runnerVisittedLocationsList);
        runnerCAFCollectionsDetails.put(BEAT_PLAN_NAME, beatPlanName);
      }    
    } catch (Exception e) {
        log.error("PROBLEM in the method getRunnerCAFCollectionsDetails");
        log.error(e);
        e.printStackTrace();
    }
    log.info("END of the method getRunnerCAFCollectionsDetails");
    return runnerCAFCollectionsDetails;
  }

  //========================================================================
  
  private long getDelayTime(Date scheduleTime, Date visittedTime) {
    long delayTime = 0;
    long diffInHours = 0;
    long diffInMins = 0;
      
      try {
        long diff = scheduleTime.getTime() - visittedTime.getTime();
        diffInMins = diff / (60 * 1000) % 60;
        diffInHours = diff / (60 * 60 * 1000) % 24;
        delayTime = (diffInHours * 60) + diffInMins;
    } catch (Exception e) {
        e.printStackTrace();
    }
    return delayTime;
  }

  //========================================================================
  
  @SuppressWarnings("unchecked")
  @Override
  public Map<String, Object> getRunnerBeatPlan(Long runnerId) throws Exception {
    log.info("START of the method getRunnerBeatPlan");
    Map<String, Object> resultMap = null;
    StringBuffer hqlQuery = null;
    List<BeatPlanUserAssignments> bpuaList = null;
    BeatPlanDetails beatPlanDetails = null;
    
    try {
      resultMap = new HashMap<String, Object>();
      
      if(runnerId != null && runnerId.longValue() > 0) {
        hqlQuery = new StringBuffer(" SELECT bpua from BeatPlanUserAssignments as bpua WHERE bpua.status='"+ACTIVE+"' ");
        hqlQuery.append(" AND bpua.userDetails.userId="+runnerId);
        hqlQuery.append(" order by bpua.scheduleTime");
        bpuaList = getHibernateTemplate().find(hqlQuery.toString());
        
        if(bpuaList != null && bpuaList.size() > 0) {
          beatPlanDetails = bpuaList.get(0).getBeatPlanDistributorAssignments().getBeatPlanDetails();
          
          for(BeatPlanUserAssignments bpua : bpuaList) {
            HubDetails hubDetails = getHubOfDistributor(bpua.getBeatPlanDistributorAssignments().getDistributorDetails().getDistributorId());

            if(hubDetails != null) {
              bpua.setHubName(hubDetails.getHubName());
              bpua.setZoneName(hubDetails.getZoneDetails().getZoneName());
              bpua.setRegionName(hubDetails.getZoneDetails().getRegionDetails().getRegionName());
              bpua.setCircleName(hubDetails.getZoneDetails().getRegionDetails().getCircleDetails().getCircleName());
            }
          }
          resultMap.put(BEAT_PLAN_TO_USER_DETAILS, bpuaList);
          resultMap.put(BEAT_PLAN_DETAILS, beatPlanDetails);
        }
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method getRunnerBeatPlan");
      log.error(e);
      e.printStackTrace();
    }
    log.info("END of the method getRunnerBeatPlan");
    return resultMap;
  }

  //========================================================================
  
  @SuppressWarnings("unchecked")
  private HubDetails getHubOfDistributor(Long distributorId) throws Exception {
    HubDetails hubDetails = null;
  
    if(distributorId != null && distributorId.longValue() > 0) {
      StringBuffer hqlQuery = new StringBuffer("SELECT distributorToHubDetails FROM DistributorToHubDetails distributorToHubDetails ");
      hqlQuery.append(" WHERE distributorToHubDetails.status='"+ACTIVE+"'");
      hqlQuery.append(" AND distributorToHubDetails.distributorDetails.distributorId="+distributorId);
      List<DistributorToHubDetails> distributorToHubAssignments = getHibernateTemplate().find(hqlQuery.toString());
      
      if(distributorToHubAssignments != null && distributorToHubAssignments.size() > 0) {
        hubDetails = distributorToHubAssignments.get(0).getHubDetails();
      }	
    }
    return hubDetails;
  }

  //========================================================================
  
  @SuppressWarnings("unchecked")
  @Override
  public CafCollectionDetails getCAFDetails(Long cafCollectionDetailsId) throws Exception {
    log.info("START of the method getCAFDetails");
    CafCollectionDetails cafCollectionDetails = null;
    List<CafScanDetails> scannedCafs = null;
    
    try {
      if(cafCollectionDetailsId != null && cafCollectionDetailsId.longValue() > 0) {
        cafCollectionDetails = (CafCollectionDetails) getHibernateTemplate().get(CafCollectionDetails.class, cafCollectionDetailsId);
        
        if(cafCollectionDetails != null) {
          StringBuffer hqlQuery = new StringBuffer("SELECT cafScanDetails FROM CafScanDetails cafScanDetails ");
          hqlQuery.append(" WHERE cafScanDetails.cafCollectionDetailsId="+cafCollectionDetailsId);
          scannedCafs = getHibernateTemplate().find(hqlQuery.toString());
          cafCollectionDetails.setScannedCafs(scannedCafs);
        }
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method getCAFDetails");
      log.error(e);
	}
    log.info("END of the method getCAFDetails");
    return cafCollectionDetails;
  }

  //========================================================================
  
  @Override
  public List<RunnerCAFTrackDetails> getRunnersCAFTrack(List<UserDetails> runners) throws Exception {
    log.info("START of the method getRunnersCAFTrack");
    List<RunnerCAFTrackDetails> runnersCAFTrack = null;
    int totalVisits = 0;
    
    try {
      if(runners != null && runners.size() > 0) {
        runnersCAFTrack = new ArrayList<RunnerCAFTrackDetails>();	
        
        for(UserDetails userDetails: runners) {
          RunnerCAFTrackDetails runnerCAFTrackDetails = new RunnerCAFTrackDetails();
          runnerCAFTrackDetails.setRunnerName(userDetails.getName().concat(" ").concat(userDetails.getLastName()));
          runnerCAFTrackDetails.setRunnerUserId(userDetails.getUserId());
          runnerCAFTrackDetails = setTargetVisitsDetails(runnerCAFTrackDetails);
          runnerCAFTrackDetails = setAchievedVisitsDetails(runnerCAFTrackDetails);
          totalVisits = runnerCAFTrackDetails.getTotalVisits();
          
          if(totalVisits > 0) {
            runnerCAFTrackDetails.setSlot1TargetPercentage(((runnerCAFTrackDetails.getSlot1Target() * 100) / totalVisits) + " %");
            runnerCAFTrackDetails.setSlot1AchievedPercentage(((runnerCAFTrackDetails.getSlot1Achieved() * 100) / totalVisits) + " %");

            runnerCAFTrackDetails.setSlot2TargetPercentage(((runnerCAFTrackDetails.getSlot2Target() * 100) / totalVisits) + " %");
            runnerCAFTrackDetails.setSlot2AchievedPercentage(((runnerCAFTrackDetails.getSlot2Achieved() * 100) / totalVisits) + " %");
          
            runnerCAFTrackDetails.setSlot3TargetPercentage(((runnerCAFTrackDetails.getSlot3Target() * 100) / totalVisits) + " %");
            runnerCAFTrackDetails.setSlot3AchievedPercentage(((runnerCAFTrackDetails.getSlot3Achieved() * 100) / totalVisits) + " %");
          
            runnerCAFTrackDetails.setSlot4TargetPercentage(((runnerCAFTrackDetails.getSlot4Target() * 100) / totalVisits) + " %");
            runnerCAFTrackDetails.setSlot4AchievedPercentage(((runnerCAFTrackDetails.getSlot4Achieved() * 100) / totalVisits) + " %");
          
            runnerCAFTrackDetails.setSlot5TargetPercentage(((runnerCAFTrackDetails.getSlot5Target() * 100) / totalVisits) + " %");
            runnerCAFTrackDetails.setSlot5AchievedPercentage(((runnerCAFTrackDetails.getSlot5Achieved() * 100) / totalVisits) + " %");  
          } else {
            runnerCAFTrackDetails.setSlot1TargetPercentage("0 %");
            runnerCAFTrackDetails.setSlot1AchievedPercentage("0 %");
            
            runnerCAFTrackDetails.setSlot2TargetPercentage("0 %");
            runnerCAFTrackDetails.setSlot2AchievedPercentage("0 %");
            
            runnerCAFTrackDetails.setSlot3TargetPercentage("0 %");
            runnerCAFTrackDetails.setSlot3AchievedPercentage("0 %");
            
            runnerCAFTrackDetails.setSlot4TargetPercentage("0 %");
            runnerCAFTrackDetails.setSlot4AchievedPercentage("0 %");
            
            runnerCAFTrackDetails.setSlot5TargetPercentage("0 %");
            runnerCAFTrackDetails.setSlot5AchievedPercentage("0 %");
          }
          runnersCAFTrack.add(runnerCAFTrackDetails);
        }
      }	
	} catch (Exception e) {
      log.error("PROBLEM in the method getRunnersCAFTrack");
      log.error(e);
      e.printStackTrace();
    }
    log.info("END of the method getRunnersCAFTrack");
    return runnersCAFTrack;
  }
  
  //========================================================================
  
  @SuppressWarnings("unchecked")
  private RunnerCAFTrackDetails setTargetVisitsDetails(RunnerCAFTrackDetails runnerCAFTrackDetails) throws Exception {
    log.info("START of the method setTargetVisitsDetails");
    StringBuffer hqlQuery = null;
    List<BeatPlanUserAssignments> bpuaList = null;
    DateFormat timeFormat = null;
    DateFormat dateFormat = null;
    DateFormat dateTimeFormat = null;
    String today = null;
    Date slot1Time = null;
    Date slot2Time = null;
    Date slot3Time = null;
    Date slot4Time = null;
    Date slot5Time = null;
    int slot1Target = 0;
    int slot2Target = 0;
    int slot3Target = 0;
    int slot4Target = 0;
    int slot5Target = 0;
    
    try {
      if(runnerCAFTrackDetails != null) {
        timeFormat = new SimpleDateFormat(TIME_FORMAT);
        dateFormat = new SimpleDateFormat(DATE_FORMAT1);
        dateTimeFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
        today = dateFormat.format(new Date());
        slot1Time = dateTimeFormat.parse(today.concat(" 10:00"));
        Calendar cal = Calendar.getInstance();
        cal.setTime(slot1Time);
        
        cal.add(Calendar.HOUR_OF_DAY, +2);
        slot2Time = cal.getTime();
        
        cal.add(Calendar.HOUR_OF_DAY, +2);
        slot3Time = cal.getTime();
        
        cal.add(Calendar.HOUR_OF_DAY, +2);
        slot4Time = cal.getTime();
        
        cal.add(Calendar.HOUR_OF_DAY, +2);
        slot5Time = cal.getTime();
        
        hqlQuery = new StringBuffer(" SELECT bpua FROM BeatPlanUserAssignments as bpua WHERE bpua.userDetails.userId="+runnerCAFTrackDetails.getRunnerUserId());
        hqlQuery.append(" and bpua.status='"+ACTIVE+"' order by bpua.scheduleTime");
        bpuaList = getHibernateTemplate().find(hqlQuery.toString());
        
        if(bpuaList != null && bpuaList.size() > 0) {
          runnerCAFTrackDetails.setTotalVisits(bpuaList.size());
          for(BeatPlanUserAssignments bpua : bpuaList) {
            Date scheduleDateTime = dateTimeFormat.parse(today.concat(" ").concat(timeFormat.format(bpua.getScheduleTime())));
            
            if(slot1Time.compareTo(scheduleDateTime) > 0) {
              slot1Target = slot1Target + 1;
            }
            
            if(slot2Time.compareTo(scheduleDateTime) > 0) {
              slot2Target = slot2Target + 1;
            }
            
            if(slot3Time.compareTo(scheduleDateTime) > 0) {
              slot3Target = slot3Target + 1;
            }
            
            if(slot4Time.compareTo(scheduleDateTime) > 0) {
              slot4Target = slot4Target + 1;
            }
            if(slot5Time.compareTo(scheduleDateTime) > 0) {
              slot5Target = slot5Target + 1;
            }
          }
          runnerCAFTrackDetails.setSlot1Target(slot1Target);
          runnerCAFTrackDetails.setSlot2Target(slot2Target);
          runnerCAFTrackDetails.setSlot3Target(slot3Target);
          runnerCAFTrackDetails.setSlot4Target(slot4Target);
          runnerCAFTrackDetails.setSlot5Target(slot5Target);
        }
	  }
    } catch (Exception e) {
      log.error("PROBLEM in the method setTargetVisitsDetails");
      log.error(e);
      e.printStackTrace();
    }
    log.info("END of the method setTargetVisitsDetails");
    return runnerCAFTrackDetails;
  }
  
  //========================================================================
  
  @SuppressWarnings("unchecked")
  private RunnerCAFTrackDetails setAchievedVisitsDetails(RunnerCAFTrackDetails runnerCAFTrackDetails) throws Exception {
    log.info("START of the method setAchievedVisitsDetails");
    StringBuffer hqlQuery = null;
    List<CafCollectionDetails> ccdList = null;
    DateFormat dateFormat = null;
    DateFormat dateTimeFormat = null;
    String today = null;
    Date slot1Time = null;
    Date slot2Time = null;
    Date slot3Time = null;
    Date slot4Time = null;
    Date slot5Time = null;
    int slot1Achieved = 0;
    int slot2Achieved = 0;
    int slot3Achieved = 0;
    int slot4Achieved = 0;
    int slot5Achieved = 0;
    int totalCAFCollected = 0;
    
    try {
      if(runnerCAFTrackDetails != null) {
        dateFormat = new SimpleDateFormat(DATE_FORMAT1);
        dateTimeFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
        today = dateFormat.format(new Date());
        
        slot1Time = dateTimeFormat.parse(today.concat(" 10:00"));
        Calendar cal = Calendar.getInstance();
        cal.setTime(slot1Time);
        
        cal.add(Calendar.HOUR_OF_DAY, +2);
        slot2Time = cal.getTime();
        
        cal.add(Calendar.HOUR_OF_DAY, +2);
        slot3Time = cal.getTime();
        
        cal.add(Calendar.HOUR_OF_DAY, +2);
        slot4Time = cal.getTime();
        
        cal.add(Calendar.HOUR_OF_DAY, +2);
        slot5Time = cal.getTime();
        /*select ccd.total_caf_count, ccd.date_time from caf_collection_details as ccd where ccd.runner_visit_id in (
        select rvd.runner_visit_id from runner_visit_details as rvd where rvd.bp_user_assignment_id in ( 
        select bpua.bp_user_assignment_id from beat_plan_user_assignments as bpua where bpua.user_id=15
        and bpua.status=1 order by bpua.schedule_time) and DATE(rvd.visit_time_stamp) = DATE(NOW()) )
        and DATE(ccd.date_time) = DATE(NOW());*/
        
        hqlQuery = new StringBuffer(" SELECT ccd FROM CafCollectionDetails as ccd WHERE ccd.runnerVisitDetails.runnerVisitId in (");
        hqlQuery.append(" select rvd.runnerVisitId from RunnerVisitDetails as rvd where rvd.beatPlanUserAssignments.bpUserAssignmentId in (");
        hqlQuery.append(" select bpua.bpUserAssignmentId from BeatPlanUserAssignments as bpua where bpua.userDetails.userId="+runnerCAFTrackDetails.getRunnerUserId());
        hqlQuery.append(" and bpua.status='"+ACTIVE+"' order by bpua.scheduleTime ) and DATE(rvd.visitTimeStamp) = DATE(NOW()))");
        hqlQuery.append(" and DATE(ccd.dateTime) = DATE(NOW())");
        ccdList = getHibernateTemplate().find(hqlQuery.toString());

        if(ccdList != null && ccdList.size() > 0) {
          runnerCAFTrackDetails.setTotalVisitsCompleted(ccdList.size());
          
          for(CafCollectionDetails ccd : ccdList) {
            Date cafCollectedTime = ccd.getDateTime();
            totalCAFCollected = totalCAFCollected + ccd.getTotalCafCount();

            if(slot1Time.compareTo(cafCollectedTime) > 0) {
              //log.info("slot1Time is after scheduleDateTime");
              slot1Achieved = slot1Achieved + 1;	
            }
            
            if(slot2Time.compareTo(cafCollectedTime) > 0) {
              slot2Achieved = slot2Achieved + 1;	
            }
            
            if(slot3Time.compareTo(cafCollectedTime) > 0) {
              slot3Achieved = slot3Achieved + 1;	
            }
            
            if(slot4Time.compareTo(cafCollectedTime) > 0) {
              slot4Achieved = slot4Achieved + 1;	
            }
            
            if(slot5Time.compareTo(cafCollectedTime) > 0) {
              slot5Achieved = slot5Achieved + 1;	
            }
          }
          runnerCAFTrackDetails.setSlot1Achieved(slot1Achieved);
          runnerCAFTrackDetails.setSlot2Achieved(slot2Achieved);
          runnerCAFTrackDetails.setSlot3Achieved(slot3Achieved);
          runnerCAFTrackDetails.setSlot4Achieved(slot4Achieved);
          runnerCAFTrackDetails.setSlot5Achieved(slot5Achieved);
          runnerCAFTrackDetails.setTotalCAFCollected(totalCAFCollected);
        }
	  }
    } catch (Exception e) {
      log.error("PROBLEM in the method setAchievedVisitsDetails");
      log.error(e);
      e.printStackTrace();
    }
    log.info("END of the method setAchievedVisitsDetails");
    return runnerCAFTrackDetails;
  }

  //========================================================================
  
  @SuppressWarnings("unchecked")
  @Override
  public AttendanceDetails getAttendanceDetails(Long runnerUserId) throws Exception {
    log.info("START of the method getAttendanceDetails");
    StringBuffer hqlQuery;
    List<AttendanceDetails> resultList = null;
    AttendanceDetails attendanceDetails = null;
    
    try {
      if(runnerUserId != null && runnerUserId.longValue() > 0) {
        hqlQuery = new StringBuffer(" SELECT attendanceDetails FROM AttendanceDetails as attendanceDetails WHERE");
        hqlQuery.append(" attendanceDetails.userDetails.userId="+runnerUserId);
        hqlQuery.append(" AND DATE(attendanceDetails.dateTime) = DATE(NOW()) ");
        resultList = getHibernateTemplate().find(hqlQuery.toString());
        
        if(resultList != null && resultList.size() > 0) {
          attendanceDetails = resultList.get(0);	
        }
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method getAttendanceDetails");
      log.error(e);
      e.printStackTrace();
    }
    log.info("END of the method getAttendanceDetails");
    return attendanceDetails;
  }
  
  //========================================================================
}

