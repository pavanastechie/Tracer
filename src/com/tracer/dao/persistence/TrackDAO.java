/**
 * @author Jp
 *
 */
package com.tracer.dao.persistence;

import java.util.List;
import java.util.Map;

import com.tracer.dao.model.AttendanceDetails;
import com.tracer.dao.model.CafCollectionDetails;
import com.tracer.dao.model.RunnerCAFTrackDetails;
import com.tracer.dao.model.UserDetails;

public interface TrackDAO {
  
  public List<UserDetails> getRunners(Long tlUserId) throws Exception;
  
  public List<UserDetails> getTeamLeaders(Long managerUserId) throws Exception;
  
  public Map<String, Object> getRunnerCAFCollectionsDetails(Long runnerUserId, String dateOfTrack) throws Exception; 
  
  public Map<String, Object> getRunnerBeatPlan(Long runnerId) throws Exception;
  
  public CafCollectionDetails getCAFDetails(Long cafCollectionId) throws Exception;
  
  public List<RunnerCAFTrackDetails> getRunnersCAFTrack(List<UserDetails> runners) throws Exception;
  
  public AttendanceDetails getAttendanceDetails(Long userId) throws Exception;
  
}
