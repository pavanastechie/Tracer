/**
 * @author Jp
 *
 */
package com.tracer.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tracer.dao.model.DistributorDetails;
import com.tracer.dao.persistence.DistributorDAO;
import com.tracer.service.DistributorManager;

public class DistributorManagerImpl implements DistributorManager {
  protected transient final Log log = LogFactory.getLog(DistributorManagerImpl.class);
  protected DistributorDAO distributorDAO = null;
  
  //========================================================================
  
  public void setDistributorDAO(DistributorDAO distributorDAO) {
    
    try {
      this.distributorDAO = distributorDAO;
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  //========================================================================
  
  @Override
  public Map<String, String> addDistributor(Map<String, Object> distributorDetailsMap) throws Exception {
    return distributorDAO.addDistributor(distributorDetailsMap);
  }

  //========================================================================
  
  @Override
  public Map<String, String> updateDistributor(Map<String, Object> distributorDetailsMap) throws Exception {
    return distributorDAO.updateDistributor(distributorDetailsMap);
  }

  //========================================================================
  
  @Override
  public Map<String, Object> getDistributorDetails(Long distributorId) throws Exception {
    return distributorDAO.getDistributorDetails(distributorId);
  }

  //========================================================================
  
  @Override
  public List<DistributorDetails> getDistributors() throws Exception {
    return distributorDAO.getDistributors();
  }
  
  //========================================================================
  
  @Override
  public String deleteDistributor(Long distributorId) throws Exception {
    return distributorDAO.deleteDistributor(distributorId);
  }

  //========================================================================
  
  @Override
  public String activateDistributor(Long distributorId) throws Exception {
    return distributorDAO.activateDistributor(distributorId);
  }

  //========================================================================
  
  @Override
  public boolean isDistributorCodeExists(String distributorCode) throws Exception {
    return distributorDAO.isDistributorCodeExists(distributorCode);
  }

  //========================================================================
}