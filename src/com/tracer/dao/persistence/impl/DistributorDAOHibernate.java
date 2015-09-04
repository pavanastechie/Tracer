/**
 * @author Jp
 *
 */
package com.tracer.dao.persistence.impl;

import static com.tracer.common.Constants.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;

import com.tracer.dao.model.BeatPlanDistributorAssignments;
import com.tracer.dao.model.DistributorDetails;
import com.tracer.dao.model.DistributorToHubDetails;
import com.tracer.dao.model.HubDetails;
import com.tracer.dao.persistence.DistributorDAO;
import com.tracer.util.StringUtil;

public class DistributorDAOHibernate extends BaseDAOHibernate implements DistributorDAO {
  protected transient final Log log = LogFactory.getLog(DistributorDAOHibernate.class);

  //========================================================================
  
  @SuppressWarnings("unchecked")
  @Override
  public boolean isDistributorCodeExists(String distributorCode) throws Exception {
    log.info("START of the method isDistributorCodeExists");
    StringBuffer hqlQuery;
    List<DistributorDetails> resultList = null;
    boolean isDistributorCodeExists = true;
    
    try {
      if(StringUtil.isNotNull(distributorCode)) {
        hqlQuery = new StringBuffer("SELECT dd FROM DistributorDetails dd WHERE dd.distributorCode='"+distributorCode+"'");
        resultList = getHibernateTemplate().find(hqlQuery.toString());
        
        if(resultList != null && resultList.size() > 0) {
            isDistributorCodeExists = true;
        } else {
            isDistributorCodeExists = false;
        }
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method isDistributorCodeExists");
      log.error(e);
      e.printStackTrace();
    }
    log.info("START of the method isDistributorCodeExists");
    return isDistributorCodeExists;
  }
  
  //==========================================================================
  
  @Override
  public Map<String, String> addDistributor(Map<String, Object> distributorDetailsMap) throws Exception {
    log.info("START of the method addDistributor");
    String result = FAILURE;
    Map<String, String> resultMap = null;
    String errorMessage = "";
    DistributorDetails distributorDetails = null;
    HubDetails hubDetails = null;
    DistributorToHubDetails distributorToHubDetails = null;
    
    try {
      resultMap = new HashMap<String, String>();
      if(distributorDetailsMap != null && !(distributorDetailsMap.isEmpty())) {
        if(distributorDetailsMap.get(DISTRIBUTOR_DETAILS) != null && distributorDetailsMap.get(DISTRIBUTOR_TO_HUB_DETAILS) != null) {
          distributorDetails = (DistributorDetails) distributorDetailsMap.get(DISTRIBUTOR_DETAILS);
          getHibernateTemplate().save(distributorDetails);
    
          // Saving the Distributor to Hub details
          if(distributorDetailsMap.get(DISTRIBUTOR_TO_HUB_DETAILS) != null) {
            hubDetails = (HubDetails) distributorDetailsMap.get(DISTRIBUTOR_TO_HUB_DETAILS);
            distributorToHubDetails = new DistributorToHubDetails();
            distributorToHubDetails.setDistributorDetails(distributorDetails);
            distributorToHubDetails.setHubDetails(hubDetails);
            distributorToHubDetails.setStatus(ACTIVE);
            getHibernateTemplate().save(distributorToHubDetails);
            result = SUCCESS;
          } else {
            errorMessage = "Distributor must be assigned to Hub";
          }
        } else {
          errorMessage = INVALID_INPUT;
        }
      } else {
        errorMessage = INVALID_INPUT;
      }
      resultMap.put(RESULT, result);
      resultMap.put(ERROR_MESSAGE, errorMessage);
    } catch (Exception e) {
      log.error("PROBLEM in the method addDistributor");
      log.error(e);
      e.printStackTrace();
    }
    log.info("END of the method addDistributor");
    return resultMap;
  }

  //========================================================================
  
  @SuppressWarnings("unchecked")
  @Override
  public Map<String, String> updateDistributor(Map<String, Object> distributorDetailsMap) throws Exception {
    log.info("START of the method updateDistributor");
    String result = FAILURE;
    Map<String, String> resultMap = null;
    String errorMessage = "";
    DistributorDetails distributorDetails = null;
    HubDetails hubDetails = null;
    DistributorToHubDetails distributorToHubDetails = null;
    StringBuffer hqlQuery = null;
    List<DistributorToHubDetails> distributorToHubsList = null;
    boolean foundCombination = false;
    
    try {
      resultMap = new HashMap<String, String>();
      if(distributorDetailsMap != null && !(distributorDetailsMap.isEmpty())) {
        if(distributorDetailsMap.get(DISTRIBUTOR_DETAILS) != null && distributorDetailsMap.get(DISTRIBUTOR_TO_HUB_DETAILS) != null) {
          distributorDetails = (DistributorDetails) distributorDetailsMap.get(DISTRIBUTOR_DETAILS);
          getHibernateTemplate().merge(distributorDetails);
    
          // Updating the Distributor to Hub details
          if(distributorDetailsMap.get(DISTRIBUTOR_TO_HUB_DETAILS) != null) {
            hubDetails = (HubDetails) distributorDetailsMap.get(DISTRIBUTOR_TO_HUB_DETAILS);
            hqlQuery = new StringBuffer(" SELECT dtoh from DistributorToHubDetails dtoh WHERE dtoh.distributorDetails="+distributorDetails.getDistributorId());
            distributorToHubsList = getHibernateTemplate().find(hqlQuery.toString());
            
            if(distributorToHubsList != null && distributorToHubsList.size() > 0) {
              for(DistributorToHubDetails dtoh : distributorToHubsList) {
                if(dtoh.getHubDetails().getHubId().longValue() == hubDetails.getHubId().longValue()) {
                  foundCombination = true;
                  dtoh.setStatus(ACTIVE);
                  getHibernateTemplate().merge(dtoh);
                } else {
                  dtoh.setStatus(IN_ACTIVE);
                  getHibernateTemplate().merge(dtoh);
                }
              }
            } 
            
            if(foundCombination == false) {
              distributorToHubDetails = new DistributorToHubDetails();
              distributorToHubDetails.setDistributorDetails(distributorDetails);
              distributorToHubDetails.setHubDetails(hubDetails);
              distributorToHubDetails.setStatus(ACTIVE);
              getHibernateTemplate().save(distributorToHubDetails);
            }
           
            result = SUCCESS;
          } else {
            errorMessage = "Distributor must be assigned to Hub";
          }
        } else {
          errorMessage = INVALID_INPUT;
        }
      } else {
        errorMessage = INVALID_INPUT;
      }
      resultMap.put(RESULT, result);
      resultMap.put(ERROR_MESSAGE, errorMessage);
    } catch (Exception e) {
      log.error("PROBLEM in the method updateDistributor");
      log.error(e);
      e.printStackTrace();
    }
    log.info("END of the method updateDistributor");
    return resultMap;
  }

  //========================================================================
  
  @SuppressWarnings("unchecked")
  @Override
  public Map<String, Object> getDistributorDetails(Long distributorId) throws Exception {
    log.info("START of the method getDistributorDetails");
    DistributorDetails distributorDetails = null;
    DistributorToHubDetails distributorToHubDetails = null;
    StringBuffer hqlQuery;
    List<DistributorToHubDetails> distToHubsList = null;
    Map<String, Object> distributorDetailsMap = null;
    Boolean isAssingedToBeatPlan = false;
    List<BeatPlanDistributorAssignments> bpdaList = null;
    
    try {
      if(distributorId != null && distributorId > 0) {
        distributorDetails = (DistributorDetails) getHibernateTemplate().get(DistributorDetails.class, distributorId);
          
        if(distributorDetails != null && distributorDetails.getStatus().equals(ACTIVE)) {
          distributorDetailsMap = new HashMap<String, Object>();
          hqlQuery = new StringBuffer("SELECT distributorToHubDetails from DistributorToHubDetails distributorToHubDetails WHERE ");
          hqlQuery.append(" distributorToHubDetails.distributorDetails.distributorId="+distributorId);
          hqlQuery.append(" and distributorToHubDetails.status='"+ACTIVE+"'");
          distToHubsList = getHibernateTemplate().find(hqlQuery.toString());  
            
          if(distToHubsList != null && distToHubsList.size() > 0) {
            distributorToHubDetails = distToHubsList.get(0);
            distributorDetailsMap.put(DISTRIBUTOR_DETAILS, distributorDetails);
            distributorDetailsMap.put(DISTRIBUTOR_TO_HUB_DETAILS, distributorToHubDetails);
             
            hqlQuery = new StringBuffer(" SELECT bpda FROM BeatPlanDistributorAssignments as bpda WHERE bpda.status='"+ACTIVE+"' ");
            hqlQuery.append(" AND bpda.distributorDetails.distributorId="+distributorId);
            bpdaList = getHibernateTemplate().find(hqlQuery.toString());
              
            if(bpdaList != null && bpdaList.size() > 0) {
              isAssingedToBeatPlan = true;  
            }
            distributorDetailsMap.put(IS_ASSINGED_TO_BEAT_PLAN, isAssingedToBeatPlan);
          }
        }
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method getDistributorDetails");
      log.error(e);
      e.printStackTrace();
    }
    log.info("END of the method getDistributorDetails");
    return distributorDetailsMap;
  }

  //========================================================================
  
  @Override
  public List<DistributorDetails> getDistributors() throws Exception {
    log.info("START of the method getDistributors");
    StringBuffer nativeQuery;
    Query hibQuery = null;
    @SuppressWarnings("rawtypes")
    List resultList = null;
    List<DistributorDetails> distributors = null;
    
    try {
      /*select dd.distributor_id, dd.distributor_name, dd.ofsc_code,dd.primary_contact_no, dd.city, dd.state, hd.hub_name
      from distributor_details as dd, distributor_to_hub_details as dth, hub_details as hd  where dd.status=1
      and dd.distributor_id=dth.distributor_id and dth.status=1
      and hd.hub_id = dth.hub_id and hd.status=1;*/
      nativeQuery = new StringBuffer("select dd.distributor_id, dd.distributor_name, dd.ofsc_code,dd.primary_contact_no, dd.city, dd.state, hd.hub_name, dd.status");
      nativeQuery.append(" from distributor_details as dd, distributor_to_hub_details as dth, hub_details as hd ");
      nativeQuery.append(" where dd.distributor_id=dth.distributor_id and dth.status='"+ACTIVE+"'");
      nativeQuery.append(" and hd.hub_id = dth.hub_id and hd.status='"+ACTIVE+"'");
      hibQuery = getSession().createSQLQuery(nativeQuery.toString());
      resultList = hibQuery.list();
      if(resultList != null && resultList.size() > 0) {
        distributors = new ArrayList<DistributorDetails>();
        for(int i = 0; i < resultList.size(); i++) {
          DistributorDetails distributorDetails = new DistributorDetails();
          Object[] result = (Object[]) resultList.get(i);
          distributorDetails.setDistributorId(Long.parseLong(result[0].toString()));
          distributorDetails.setDistributorName(result[1].toString());
          distributorDetails.setOfscCode(result[2].toString());
          distributorDetails.setPrimaryContactNo(result[3].toString());
          distributorDetails.setCity(result[4].toString());
          distributorDetails.setState(result[5].toString());
          distributorDetails.setHubName(result[6].toString());
          distributorDetails.setStatus(result[7].toString());
          distributors.add(distributorDetails);
        }
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method getDistributors");
      log.error(e);
      e.printStackTrace();
    }
    return distributors;
  }

  //========================================================================
  
  @SuppressWarnings("unchecked")
  @Override
  public String deleteDistributor(Long distributorId) throws Exception {
    log.info("START of the method deleteDistributor");
    DistributorDetails distributorDetails = null;
    String result = FAILURE;
    boolean isDistributorAssignedToBeatPlan = false;
    StringBuffer hqlQuery;
    List<BeatPlanDistributorAssignments> bpdaList = null;
    
    try {
      if(distributorId != null && distributorId > 0) {
        hqlQuery = new StringBuffer("SELECT bpda FROM BeatPlanDistributorAssignments bpda ");
        hqlQuery.append(" WHERE bpda.status='" + ACTIVE + "'");
        hqlQuery.append(" AND bpda.distributorDetails.distributorId=" + distributorId);
        bpdaList = getHibernateTemplate().find(hqlQuery.toString());

        if (bpdaList != null && bpdaList.size() > 0) {
          isDistributorAssignedToBeatPlan = true;
        }
        
        if(isDistributorAssignedToBeatPlan) {
          result = "BeatPlanAssigned";	
        } else {
          distributorDetails = (DistributorDetails) getHibernateTemplate().get(DistributorDetails.class, distributorId); 
          distributorDetails.setStatus(IN_ACTIVE);
          getHibernateTemplate().merge(distributorDetails);
          result = SUCCESS;	
        }
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method deleteDistributor");
      log.error(e);
      e.printStackTrace();
    }
    log.info("END of the method deleteDistributor");
    return result;
  }

  //========================================================================
  
  @Override
  public String activateDistributor(Long distributorId) throws Exception {
    log.info("START of the method activateDistributor");
    DistributorDetails distributorDetails = null;
    String result = FAILURE;
    
    try {
      if(distributorId != null && distributorId > 0) {
    	  distributorDetails = (DistributorDetails) getHibernateTemplate().get(DistributorDetails.class, distributorId); 
          distributorDetails.setStatus(ACTIVE);
          getHibernateTemplate().merge(distributorDetails);
          result = SUCCESS;
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method activateDistributor");
      log.error(e);
      e.printStackTrace();
    }
    log.info("END of the method activateDistributor");
    return result;
  }

  //========================================================================

}
