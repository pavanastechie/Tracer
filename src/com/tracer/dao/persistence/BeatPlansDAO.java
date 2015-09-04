/**
 * @author Jp
 *
 */
package com.tracer.dao.persistence;

import java.util.List;
import java.util.Map;

import com.tracer.dao.model.BeatPlanDetails;
import com.tracer.dao.model.CircleDetails;
import com.tracer.dao.model.DistributorBeatPlansDetails;
import com.tracer.dao.model.DistributorDetails;
import com.tracer.dao.model.HubDetails;
import com.tracer.dao.model.RegionDetails;
import com.tracer.dao.model.RunnerBeatPlansDetails;
import com.tracer.dao.model.ZoneDetails;

public interface BeatPlansDAO {

  public List<CircleDetails> getCircles() throws Exception;
  
  public List<RegionDetails> getRegions(Long circleId) throws Exception;
  
  public List<ZoneDetails> getZones(Long regionId) throws Exception;
  
  public List<HubDetails> getHubs(Long zoneId) throws Exception;
  
  public List<HubDetails> getHubsOfUser(Long userId) throws Exception;
  
  public List<DistributorDetails> getDistributors(Long userId) throws Exception;
  
  public List<DistributorDetails> getDistributorsOfHub(Long hubId) throws Exception;
  
  public Map<String, String> addDistributorBeatPlan(Map<String, Object> distributorBeatPlanDetailsMap) throws Exception;
  
  public List<DistributorBeatPlansDetails> getDistributorBeatPlans(Long userId, Long roleId) throws Exception;
  
  public boolean isBeatPlanCodeExists(String beatPlanCode) throws Exception;
  
  public boolean isBeatPlanExists(String beatPlanName) throws Exception;
  
  public Map<String, String> updateDistributorBeatPlan(Map<String, Object> distributorBeatPlanDetailsMap) throws Exception;
  
  public String deleteDistributorBeatPlan(Long beatPlanId) throws Exception;
  
  public Map<String, Object> getDistributorBeatPlan(Long beatPlanId) throws Exception;
  
  //This method is used to show the list of beat plans in add runner beat plan, beat plans drop down
  public List<BeatPlanDetails> getBeatPlans(Long userId, Long roleId) throws Exception; 
  
  public Map<String, Object> getDistributorsAndRunners(Long beatPlanId) throws Exception;
  
  public Map<String, String> addRunnerBeatPlan(Map<String, Object> runnerBeatPlanDetailsMap) throws Exception;
  
  public Map<String, String> updateRunnerBeatPlan(Map<String, Object> runnerBeatPlanDetailsMap) throws Exception;
  
  public Map<String, Object> getRunnerBeatPlan(Long beatPlanId) throws Exception;
  
  public int getDistributorVisitFrequency(Long beatPlanId, Long distributorId) throws Exception;
  
  public String deleteRunnerBeatPlan(Long beatPlanId) throws Exception; 
  
  List<RunnerBeatPlansDetails> getRunnerBeatPlans(Long userId, Long roleId) throws Exception;
  
  public int[] getVisitNumbers(Long distributorId) throws Exception;
  
  public boolean isRunnerBeatPlanAssigned(Long bpDistAssignmentsId) throws Exception;
  
}
