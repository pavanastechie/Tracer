/**
 * @author Jp
 *
 */
package com.tracer.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tracer.dao.model.BeatPlanDetails;
import com.tracer.dao.model.CircleDetails;
import com.tracer.dao.model.DistributorBeatPlansDetails;
import com.tracer.dao.model.DistributorDetails;
import com.tracer.dao.model.HubDetails;
import com.tracer.dao.model.RegionDetails;
import com.tracer.dao.model.RunnerBeatPlansDetails;
import com.tracer.dao.model.ZoneDetails;
import com.tracer.dao.persistence.BeatPlansDAO;
import com.tracer.service.BeatPlansManager;

public class BeatPlansManagerImpl implements BeatPlansManager {
  protected transient final Log log = LogFactory.getLog(BeatPlansManagerImpl.class);
  protected BeatPlansDAO beatPlansDAO = null;
  
  //========================================================================
  
  public void setBeatPlansDAO(BeatPlansDAO beatPlansDAO) {
    
    try {
      this.beatPlansDAO = beatPlansDAO;
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  //========================================================================
  
  @Override
  public List<HubDetails> getHubsOfUser(Long userId) throws Exception {
    return beatPlansDAO.getHubsOfUser(userId);
  }
  
  //========================================================================
  
  @Override
  public Map<String, String> addDistributorBeatPlan(Map<String, Object> distributorBeatPlanDetailsMap) throws Exception {
    return beatPlansDAO.addDistributorBeatPlan(distributorBeatPlanDetailsMap);
  }
  
  //========================================================================
  
  @Override
  public List<CircleDetails> getCircles() throws Exception {
    return beatPlansDAO.getCircles();
  }
  
  //========================================================================
  
  @Override
  public List<RegionDetails> getRegions(Long circleId) throws Exception {
    return beatPlansDAO.getRegions(circleId);
  }
  
  //========================================================================
  
  @Override
  public List<ZoneDetails> getZones(Long regionId) throws Exception {
    return beatPlansDAO.getZones(regionId);
  }
  
  //========================================================================
  
  @Override
  public List<HubDetails> getHubs(Long zoneId) throws Exception {
    return beatPlansDAO.getHubs(zoneId);
  }
  
  //========================================================================
  
  @Override
  public List<DistributorDetails> getDistributors(Long userId) throws Exception {
    return beatPlansDAO.getDistributors(userId);
  }
  
  //========================================================================
  
  @Override
  public List<DistributorDetails> getDistributorsOfHub(Long hubId) throws Exception {
    return beatPlansDAO.getDistributorsOfHub(hubId);
  }

  //========================================================================
  
  @Override
  public List<DistributorBeatPlansDetails> getDistributorBeatPlans(Long userId, Long roleId) throws Exception {
    return beatPlansDAO.getDistributorBeatPlans(userId, roleId);
  }

  //========================================================================
  
  @Override
  public boolean isBeatPlanCodeExists(String beatPlanCode) throws Exception {
    return beatPlansDAO.isBeatPlanCodeExists(beatPlanCode);
  }
  
  //========================================================================
  
  @Override
  public boolean isBeatPlanExists(String beatPlanName) throws Exception {
    return beatPlansDAO.isBeatPlanExists(beatPlanName);
  }
  
  //========================================================================
  
  @Override
  public Map<String, String> updateDistributorBeatPlan(Map<String, Object> distributorBeatPlanDetailsMap) throws Exception {
    return beatPlansDAO.updateDistributorBeatPlan(distributorBeatPlanDetailsMap);
  }

  @Override
  public String deleteDistributorBeatPlan(Long beatPlanId) throws Exception {
    return beatPlansDAO.deleteDistributorBeatPlan(beatPlanId);
  }

  //========================================================================
  
  @Override
  public Map<String, Object> getDistributorBeatPlan(Long beatPlanId) throws Exception {
    return beatPlansDAO.getDistributorBeatPlan(beatPlanId);
  }

  //========================================================================
  
  @Override
  public List<BeatPlanDetails> getBeatPlans(Long userId, Long roleId) throws Exception {
    return beatPlansDAO.getBeatPlans(userId, roleId);
  }
  
  //========================================================================
  
  @Override
  public Map<String, Object> getDistributorsAndRunners(Long beatPlanId) throws Exception {
    return beatPlansDAO.getDistributorsAndRunners(beatPlanId);
  }
  
  //========================================================================
  
  @Override
  public Map<String, String> addRunnerBeatPlan(Map<String, Object> runnerBeatPlanDetailsMap) throws Exception {
    return beatPlansDAO.addRunnerBeatPlan(runnerBeatPlanDetailsMap);
  }
  
  //========================================================================
  
  @Override
  public Map<String, String> updateRunnerBeatPlan(Map<String, Object> runnerBeatPlanDetailsMap) throws Exception {
    return beatPlansDAO.updateRunnerBeatPlan(runnerBeatPlanDetailsMap);
  }
  
  //========================================================================
  
  @Override
  public Map<String, Object> getRunnerBeatPlan(Long beatPlanId) throws Exception {
    return beatPlansDAO.getRunnerBeatPlan(beatPlanId);
  }

  //========================================================================
  
  @Override
  public int getDistributorVisitFrequency(Long beatPlanId, Long distributorId) throws Exception {
    return beatPlansDAO.getDistributorVisitFrequency(beatPlanId, distributorId);
  }
  
  //========================================================================
  
  @Override
  public String deleteRunnerBeatPlan(Long beatPlanId) throws Exception {
    return beatPlansDAO.deleteRunnerBeatPlan(beatPlanId);
  }
  
  //========================================================================
  
  @Override
  public List<RunnerBeatPlansDetails> getRunnerBeatPlans(Long userId, Long roleId) throws Exception {
    return beatPlansDAO.getRunnerBeatPlans(userId, roleId);
  }

  //========================================================================
  
  @Override
  public int[] getVisitNumbers(Long distributorId) throws Exception {
    return beatPlansDAO.getVisitNumbers(distributorId);
  }

  //========================================================================
  
  @Override
  public boolean isRunnerBeatPlanAssigned(Long bpDistAssignmentsId) throws Exception {
    return beatPlansDAO.isRunnerBeatPlanAssigned(bpDistAssignmentsId);
  }

  //========================================================================

}