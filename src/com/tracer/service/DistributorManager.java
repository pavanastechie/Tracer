/**
 * @author Jp
 *
 */
package com.tracer.service;

import java.util.List;
import java.util.Map;

import com.tracer.dao.model.DistributorDetails;

public interface DistributorManager {
  
  public boolean isDistributorCodeExists(String distributorCode) throws Exception;
  
  public Map<String, String> addDistributor(Map<String, Object> distributorDetailsMap) throws Exception;
  
  public Map<String, String> updateDistributor(Map<String, Object> distributorDetailsMap) throws Exception;
  
  public Map<String, Object> getDistributorDetails(Long distributorId) throws Exception;
  
  public List<DistributorDetails> getDistributors() throws Exception;
  
  public String deleteDistributor(Long distributorId) throws Exception;
  
  public String activateDistributor(Long distributorId) throws Exception;

}
