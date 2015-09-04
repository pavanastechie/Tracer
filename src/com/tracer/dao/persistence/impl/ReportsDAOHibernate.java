/**
 * @author Jp
 *
 */
package com.tracer.dao.persistence.impl;

import static com.tracer.common.Constants.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;

import com.tracer.dao.model.HubDetails;
import com.tracer.dao.model.PerformanceReportDetails;
import com.tracer.dao.model.UserDetails;
import com.tracer.dao.model.UserToHubDetails;
import com.tracer.dao.model.ZoneDetails;
import com.tracer.dao.persistence.ReportsDAO;
import com.tracer.util.StringUtil;

public class ReportsDAOHibernate extends BaseDAOHibernate implements ReportsDAO {
  protected transient final Log log = LogFactory.getLog(ReportsDAOHibernate.class);

  // ========================================================================
  
  @SuppressWarnings("unchecked")
  @Override
  public List<PerformanceReportDetails> getHubPerformanceReport(String fromDate, String toDate, long hubId) throws Exception {
    log.info("START of the method getHubPerformanceReport");
    List<UserDetails> runners = null;
    String runnerIds = null;
    HubDetails hubDetails = null;
    List<Object[]> resultList = null;
    List<PerformanceReportDetails> hubPerformanceReport = null;
    DateFormat dateFormat = null;
    DateFormat timeFormat = null;
    StringBuffer nativeQuery;
    Query hibQuery = null;
    
    try {
      if(StringUtil.isNotNull(fromDate) && StringUtil.isNotNull(toDate) && hubId > 0) {
        runners = getRunnersOfHub(hubId);
        if(runners != null && runners.size() > 0) {
          runnerIds = getRunnerIds(runners);
          
          if(StringUtil.isNotNull(runnerIds)) {
            hubDetails = (HubDetails) getHibernateTemplate().get(HubDetails.class, hubId);
            /*select bpua.bp_user_assignment_id, bpua.user_id, bpua.schedule_time, time(ccd.date_time) as time,
            TIMEDIFF(bpua.schedule_time, time(ccd.date_time)) as diff, dd.distributor_name, ccd.total_caf_count,rvd.visit_no, 
            ccd.date_time, ccd.caf_collection_id, dd.ofsc_code
            from caf_collection_details as ccd, runner_visit_details as rvd, beat_plan_user_assignments as bpua, 
            beat_plan_distributor_assignments bpda, distributor_details as dd
            where ccd.runner_visit_id=rvd.runner_visit_id and rvd.bp_user_assignment_id=bpua.bp_user_assignment_id
            and bpda.bp_dist_assignments_id=bpua.bp_dist_assignment_id and bpda.distributor_id=dd.distributor_id
            and bpua.user_id in (9,10,11,12,13,14,15)
            and DATE(ccd.date_time) between '2014-10-01' and '2014-11-21'
            order by ccd.date_time desc, bpua.user_id;*/
            nativeQuery = new StringBuffer(" select bpua.bp_user_assignment_id, bpua.user_id, bpua.schedule_time, time(ccd.date_time) as time, ");
            nativeQuery.append(" TIMEDIFF(bpua.schedule_time, time(ccd.date_time)) as diff, dd.distributor_name, ccd.total_caf_count, rvd.visit_no, ");
            nativeQuery.append(" ccd.date_time, ccd.caf_collection_id, dd.ofsc_code");
            nativeQuery.append(" from caf_collection_details as ccd, runner_visit_details as rvd, beat_plan_user_assignments as bpua,  ");
            nativeQuery.append(" beat_plan_distributor_assignments bpda, distributor_details as dd ");
            nativeQuery.append(" where ccd.runner_visit_id=rvd.runner_visit_id and rvd.bp_user_assignment_id=bpua.bp_user_assignment_id ");
            nativeQuery.append(" and bpda.bp_dist_assignments_id=bpua.bp_dist_assignment_id and bpda.distributor_id=dd.distributor_id ");
            nativeQuery.append(" and bpua.user_id in ("+runnerIds+")");
            nativeQuery.append(" and cast(ccd.date_time as date) between '"+fromDate+"' and '"+toDate+"'");
            nativeQuery.append(" order by ccd.date_time desc, bpua.user_id");
            hibQuery = getSession().createSQLQuery(nativeQuery.toString());
            resultList = hibQuery.list();
            
            if(resultList != null && resultList.size() > 0) {
              Date scheduleTime = null;
              Date visittedTime = null;
              Date cafCollectedDateTime = null;
              Long runnerUserId = null;
              hubPerformanceReport = new ArrayList<PerformanceReportDetails>(resultList.size());
              dateFormat = new SimpleDateFormat(DATE_FORMAT1);
              timeFormat = new SimpleDateFormat(TIME_FORMAT);
                
              for(int i = 0; i < resultList.size(); i++) {
                Object[] result = (Object[]) resultList.get(i);
                runnerUserId = Long.parseLong(result[1].toString());
                scheduleTime = (Date) result[2];
                visittedTime = (Date) result[3];
                cafCollectedDateTime = (Date) result[8];
                PerformanceReportDetails performanceReportDetails = new PerformanceReportDetails();
                performanceReportDetails.setHubName(hubDetails.getHubName());
                performanceReportDetails.setDate(dateFormat.format(cafCollectedDateTime));
                performanceReportDetails.setScheduleTime(timeFormat.format(scheduleTime));
                performanceReportDetails.setVisitedTime(timeFormat.format(visittedTime));
                //performanceReportDetails.setDelayTime(getDelayTime(scheduleTime, visittedTime));
                //performanceReportDetails.setEarlyVisit(scheduleTime.after(visittedTime));
                performanceReportDetails.setCafsCollected(Integer.parseInt(result[6].toString()));
                performanceReportDetails.setDistributorName(result[5].toString());
                performanceReportDetails.setOfscCode(result[10].toString());
                performanceReportDetails.setRunnerName(getRunnerName(runners, runnerUserId));
                hubPerformanceReport.add(performanceReportDetails);
              }
            }
          }
        }
      }
    } catch (Exception e) {
        log.error("PROBLEM in the method getHubPerformanceReport");
        log.error(e);
        e.printStackTrace();
    }
    log.info("END of the method getHubPerformanceReport");
    return hubPerformanceReport;
  }

  // ========================================================================
  
  private String getRunnerIds(List<UserDetails> runners) {
    StringBuffer runnerIdsSB = new StringBuffer("");
    for(UserDetails userDetails : runners) {
      runnerIdsSB.append(userDetails.getUserId());
      runnerIdsSB.append(",");
    }
    return StringUtil.removeTrailingValue(runnerIdsSB, ",");
  }
  
  // ========================================================================
  
  private String getRunnerName(List<UserDetails> runners, long runnerUserId) {
    StringBuffer runnerName = new StringBuffer("");
    for(UserDetails userDetails : runners) {
      if(runnerUserId == userDetails.getUserId().longValue()) {
        runnerName.append(userDetails.getName());
        runnerName.append(" ");
        runnerName.append(userDetails.getLastName());
        break;
      }
    }
    return runnerName.toString();
  }
  
  // ========================================================================
  
  @SuppressWarnings("unchecked")
  private List<UserDetails> getRunnersOfHub(long hubId) throws Exception {
    log.info("START of the method getRunnersOfHub"); 
    List<UserDetails> runners = null;
    StringBuffer hqlQuery = null;
    List<UserToHubDetails> userToHubDetailsList = null;
    
    try {
      if(hubId > 0) {
        hqlQuery = new StringBuffer("SELECT uthd from UserToHubDetails uthd WHERE uthd.userDetails.roleDetails.roleId="+RUNNER);
        hqlQuery.append(" and uthd.status='"+ACTIVE+"'");
        hqlQuery.append(" and uthd.hubDetails.hubId="+hubId);
        userToHubDetailsList = getHibernateTemplate().find(hqlQuery.toString());
        
        if(userToHubDetailsList != null && userToHubDetailsList.size() > 0) {
          runners = new ArrayList<UserDetails>();
          for(UserToHubDetails userToHubDetails : userToHubDetailsList) {
            runners.add(userToHubDetails.getUserDetails());
          }
        }
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method getRunnersOfHub");
      log.error(e);
      e.printStackTrace();
  }
    log.info("END of the method getRunnersOfHub");
    return runners;
  }

  // ========================================================================
  
  @SuppressWarnings("unchecked")
  @Override
  public List<PerformanceReportDetails> getZonePerformanceReport(String fromDate, String toDate, long zoneId) throws Exception {
    log.info("START of the method getZones");
    List<UserDetails> runners = null;
    String runnerIds = null;
    List<Object[]> resultList = null;
    List<PerformanceReportDetails> zonePerformanceReport = null;
    DateFormat dateFormat = null;
    DateFormat timeFormat = null;
    ZoneDetails zoneDetails = null;
    StringBuffer nativeQuery;
    Query hibQuery = null;
    
    try {
      if(StringUtil.isNotNull(fromDate) && StringUtil.isNotNull(toDate) && zoneId > 0) {
        runners = getRunnersOfZone(zoneId);
        if(runners != null && runners.size() > 0) {
          runnerIds = getRunnerIds(runners);
          
          if(StringUtil.isNotNull(runnerIds)) {
            zoneDetails = (ZoneDetails) getHibernateTemplate().get(ZoneDetails.class, zoneId);
            /*select bpua.bp_user_assignment_id, bpua.user_id, bpua.schedule_time, time(ccd.date_time) as time,
            TIMEDIFF(bpua.schedule_time, time(ccd.date_time)) as diff, dd.distributor_name, ccd.total_caf_count,rvd.visit_no, 
            ccd.date_time, ccd.caf_collection_id, dd.ofsc_code
            from caf_collection_details as ccd, runner_visit_details as rvd, beat_plan_user_assignments as bpua, 
            beat_plan_distributor_assignments bpda, distributor_details as dd
            where ccd.runner_visit_id=rvd.runner_visit_id and rvd.bp_user_assignment_id=bpua.bp_user_assignment_id
            and bpda.bp_dist_assignments_id=bpua.bp_dist_assignment_id and bpda.distributor_id=dd.distributor_id
            and bpua.user_id in (9,10,11,12,13,14,15)
            and DATE(ccd.date_time) between '2014-10-01' and '2014-11-21'
            order by ccd.date_time desc, bpua.user_id;*/
            
            /*select bpua.bp_user_assignment_id, bpua.user_id, bpua.schedule_time, time(ccd.date_time) as time,
            TIMEDIFF(bpua.schedule_time, time(ccd.date_time)) as diff, dd.distributor_name, ccd.total_caf_count,rvd.visit_no, 
            ccd.date_time, ccd.caf_collection_id, dd.ofsc_code, hd.hub_name
            from caf_collection_details as ccd, runner_visit_details as rvd, beat_plan_user_assignments as bpua, 
            beat_plan_distributor_assignments bpda, distributor_details as dd, user_to_hub_details as uthd, hub_details as hd
            where ccd.runner_visit_id=rvd.runner_visit_id and rvd.bp_user_assignment_id=bpua.bp_user_assignment_id
            and bpda.bp_dist_assignments_id=bpua.bp_dist_assignment_id and bpda.distributor_id=dd.distributor_id
            and uthd.user_id = bpua.user_id and hd.hub_id = uthd.hub_id
            and bpua.user_id in (9,10,11,12,13,14,15)
            and DATE(ccd.date_time) between '2014-10-01' and '2014-11-21'
            order by ccd.date_time desc, bpua.user_id;*/
            
            nativeQuery = new StringBuffer(" select bpua.bp_user_assignment_id, bpua.user_id, bpua.schedule_time, time(ccd.date_time) as time, ");
            nativeQuery.append(" TIMEDIFF(bpua.schedule_time, time(ccd.date_time)) as diff, dd.distributor_name, ccd.total_caf_count, rvd.visit_no, ");
            nativeQuery.append(" ccd.date_time, ccd.caf_collection_id, dd.ofsc_code, hd.hub_name");
            nativeQuery.append(" from caf_collection_details as ccd, runner_visit_details as rvd, beat_plan_user_assignments as bpua,  ");
            nativeQuery.append(" beat_plan_distributor_assignments bpda, distributor_details as dd, user_to_hub_details as uthd, hub_details as hd ");
            nativeQuery.append(" where ccd.runner_visit_id=rvd.runner_visit_id and rvd.bp_user_assignment_id=bpua.bp_user_assignment_id ");
            nativeQuery.append(" and bpda.bp_dist_assignments_id=bpua.bp_dist_assignment_id and bpda.distributor_id=dd.distributor_id ");
            nativeQuery.append(" and uthd.user_id = bpua.user_id and hd.hub_id = uthd.hub_id");
            nativeQuery.append(" and bpua.user_id in ("+runnerIds+")");
            nativeQuery.append(" and cast(ccd.date_time as date) between '"+fromDate+"' and '"+toDate+"'");
            nativeQuery.append(" order by ccd.date_time desc, bpua.user_id");
            hibQuery = getSession().createSQLQuery(nativeQuery.toString());
            resultList = hibQuery.list();
          
            if(resultList != null && resultList.size() > 0) {
              Date scheduleTime = null;
              Date visittedTime = null;
              Date cafCollectedDateTime = null;
              Long runnerUserId = null;
              zonePerformanceReport = new ArrayList<PerformanceReportDetails>(resultList.size());
              dateFormat = new SimpleDateFormat(DATE_FORMAT1);
              timeFormat = new SimpleDateFormat(TIME_FORMAT);
              
              for(int i = 0; i < resultList.size(); i++) {
                Object[] result = (Object[]) resultList.get(i);
                runnerUserId = Long.parseLong(result[1].toString());
                scheduleTime = (Date) result[2];
                visittedTime = (Date) result[3];
                cafCollectedDateTime = (Date) result[8];
                PerformanceReportDetails performanceReportDetails = new PerformanceReportDetails();
                performanceReportDetails.setZoneName(zoneDetails.getZoneName());
                performanceReportDetails.setDate(dateFormat.format(cafCollectedDateTime));
                performanceReportDetails.setScheduleTime(timeFormat.format(scheduleTime));
                performanceReportDetails.setVisitedTime(timeFormat.format(visittedTime));
                //performanceReportDetails.setDelayTime(getDelayTime(scheduleTime, visittedTime));
                //performanceReportDetails.setEarlyVisit(scheduleTime.after(visittedTime));
                performanceReportDetails.setCafsCollected(Integer.parseInt(result[6].toString()));
                performanceReportDetails.setDistributorName(result[5].toString());
                performanceReportDetails.setOfscCode(result[10].toString());
                performanceReportDetails.setRunnerName(getRunnerName(runners, runnerUserId));
                performanceReportDetails.setHubName(result[11].toString());
                zonePerformanceReport.add(performanceReportDetails);
              }
            }
          }
        }
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method getZones");
      log.error(e);
      e.printStackTrace();
    }
    log.info("START of the method getZones");
    return zonePerformanceReport;
  }
  
  // ========================================================================
  
  @SuppressWarnings("unchecked")
  @Override
  public List<ZoneDetails> getZones(long userDetailsId) throws Exception {
    log.info("START of the method getZones"); 
    StringBuffer hqlQuery;
    List<UserToHubDetails> userToHubsList = null;
    List<ZoneDetails> zones = null;

    try {
      if(userDetailsId > 0) {
        hqlQuery = new StringBuffer("SELECT userToHubDetails FROM UserToHubDetails userToHubDetails WHERE ");
        hqlQuery.append(" userToHubDetails.status='"+ACTIVE+"' AND ");
        hqlQuery.append(" userToHubDetails.userDetails.userId="+userDetailsId);
        userToHubsList = getHibernateTemplate().find(hqlQuery.toString());

        if(userToHubsList != null && userToHubsList.size() > 0) {
          zones = new ArrayList<ZoneDetails>();
          for(UserToHubDetails userToHubDetails : userToHubsList) {
            if(userToHubDetails != null) {
              zones.add(userToHubDetails.getHubDetails().getZoneDetails());
            }
          }
        }  
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method getZones");
      log.error(e);
      e.printStackTrace();
	}
    log.info("START of the method getZones"); 
    return zones;
  }
  
  // ========================================================================
  
  @SuppressWarnings("unchecked")
  private List<UserDetails> getRunnersOfZone(long zoneId) throws Exception {
    log.info("START of the method getRunnersOfZone"); 
    List<UserDetails> runners = null;
    StringBuffer hqlQuery = null;
    List<UserToHubDetails> userToHubDetailsList = null;
    List<HubDetails> hubsOfZone = null;
    StringBuffer hubIdsSB = new StringBuffer("");
    
    try {
      if(zoneId > 0) {
        hqlQuery = new StringBuffer("SELECT hubDetails from HubDetails hubDetails WHERE hubDetails.zoneDetails.zoneId = "+zoneId);
        hqlQuery.append(" and hubDetails.status='"+ACTIVE+"'");
        hubsOfZone = getHibernateTemplate().find(hqlQuery.toString());
        
        if(hubsOfZone != null && hubsOfZone.size() > 0) {
          for(HubDetails hubDetails : hubsOfZone) {
            hubIdsSB.append(hubDetails.getHubId());
            hubIdsSB.append(",");
          }
        }
        if(hubIdsSB.length() > 0) {
          hqlQuery = new StringBuffer("SELECT uthd from UserToHubDetails uthd WHERE uthd.userDetails.roleDetails.roleId="+RUNNER);
          hqlQuery.append(" and uthd.status='"+ACTIVE+"'");
          hqlQuery.append(" and uthd.hubDetails.hubId in ("+StringUtil.removeTrailingValue(hubIdsSB, ","));
          hqlQuery.append(")");
          userToHubDetailsList = getHibernateTemplate().find(hqlQuery.toString());
           
          if(userToHubDetailsList != null && userToHubDetailsList.size() > 0) {
            runners = new ArrayList<UserDetails>();
            for(UserToHubDetails userToHubDetails : userToHubDetailsList) {
              runners.add(userToHubDetails.getUserDetails());
            }
          }
        }
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method getRunnersOfZone");
      log.error(e);
      e.printStackTrace();
  }
    log.info("END of the method getRunnersOfZone");
    return runners;
  }

  // ========================================================================
  
  /*private long getDelayTime(Date scheduleTime, Date visittedTime) {
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
  }*/

  // ========================================================================
}
