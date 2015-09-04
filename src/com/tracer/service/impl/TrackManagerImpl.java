/**
 * @author Jp
 *
 */
package com.tracer.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tracer.dao.model.AttendanceDetails;
import com.tracer.dao.model.CafCollectionDetails;
import com.tracer.dao.model.RunnerCAFTrackDetails;
import com.tracer.dao.model.UserDetails;
import com.tracer.dao.persistence.TrackDAO;
import com.tracer.service.TrackManager;

public class TrackManagerImpl implements TrackManager {
  protected transient final Log log = LogFactory.getLog(TrackManagerImpl.class);
  protected TrackDAO trackDAO = null;
  
  //========================================================================
  
  public void setTrackDAO(TrackDAO trackDAO) {
    
    try {
      this.trackDAO = trackDAO;
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  //========================================================================
  
  @Override
  public List<UserDetails> getRunners(Long tlUserId) throws Exception {
    return trackDAO.getRunners(tlUserId);
  }

  //========================================================================
  
  @Override
  public List<UserDetails> getTeamLeaders(Long managerUserId) throws Exception {
    return trackDAO.getTeamLeaders(managerUserId);
  }

  //========================================================================
  
  @Override
  public Map<String, Object> getRunnerCAFCollectionsDetails(Long runnerUserId, String dateOfTrack) throws Exception {
    return trackDAO.getRunnerCAFCollectionsDetails(runnerUserId, dateOfTrack);
  }

  //========================================================================

  @Override
  public Map<String, Object> getRunnerBeatPlan(Long runnerId) throws Exception {
	return trackDAO.getRunnerBeatPlan(runnerId);
  }

  //========================================================================
  
  @Override
  public CafCollectionDetails getCAFDetails(Long cafCollectionId) throws Exception {
    return trackDAO.getCAFDetails(cafCollectionId);
  }

  //========================================================================
  
  @Override
  public List<RunnerCAFTrackDetails> getRunnersCAFTrack(List<UserDetails> runners) throws Exception {
	return trackDAO.getRunnersCAFTrack(runners);
  }

  //========================================================================
  
  @Override
  public AttendanceDetails getAttendanceDetails(Long userId) throws Exception {
    return trackDAO.getAttendanceDetails(userId);
  }

  //========================================================================

}
