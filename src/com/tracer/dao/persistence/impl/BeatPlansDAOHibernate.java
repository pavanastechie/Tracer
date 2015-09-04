/**
 * @author Jp
 *
 */
package com.tracer.dao.persistence.impl;

import static com.tracer.common.Constants.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;

import com.tracer.dao.model.BeatPlanDetails;
import com.tracer.dao.model.BeatPlanDistributorAssignments;
import com.tracer.dao.model.BeatPlanUserAssignments;
import com.tracer.dao.model.CircleDetails;
import com.tracer.dao.model.DistributorBeatPlansDetails;
import com.tracer.dao.model.DistributorDetails;
import com.tracer.dao.model.DistributorToHubDetails;
import com.tracer.dao.model.HubDetails;
import com.tracer.dao.model.RegionDetails;
import com.tracer.dao.model.RunnerBeatPlansDetails;
import com.tracer.dao.model.UserDetails;
import com.tracer.dao.model.UserToHubDetails;
import com.tracer.dao.model.ZoneDetails;
import com.tracer.dao.persistence.BeatPlansDAO;
import com.tracer.util.StringUtil;

public class BeatPlansDAOHibernate extends BaseDAOHibernate implements BeatPlansDAO {
  protected transient final Log log = LogFactory.getLog(BeatPlansDAOHibernate.class);

  // ========================================================================

  @SuppressWarnings("unchecked")
  @Override
  public List<HubDetails> getHubsOfUser(Long userId) throws Exception {
    log.info("START of the method getHubs");
    List<HubDetails> hubs = null;
    StringBuffer hqlQuery;
    List<UserToHubDetails> resultList = null;

    try {
      if (userId != null && userId > 0) {
        hqlQuery = new StringBuffer("SELECT userToHubDetails FROM UserToHubDetails userToHubDetails WHERE ");
        hqlQuery.append(" userToHubDetails.status='" + ACTIVE + "' AND ");
        hqlQuery.append(" userToHubDetails.userDetails.userId=" + userId);
        resultList = getHibernateTemplate().find(hqlQuery.toString());

        if (resultList != null && resultList.size() > 0) {
          hubs = new ArrayList<>();
          for (UserToHubDetails details : resultList) {
            if (details != null) {
              hubs.add(details.getHubDetails());
            }
          }
        }
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method getHubs");
      log.error(e);
      e.printStackTrace();
    }

    log.info("END of the method getHubs");
    return hubs;
  }

  // ========================================================================

  @SuppressWarnings("unchecked")
  @Override
  public Map<String, String> addDistributorBeatPlan(Map<String, Object> distributorBeatPlanDetailsMap) throws Exception {
    log.info("START of the method addDistributorBeatPlan");
    String result = FAILURE;
    String errorMessage = "";
    Map<String, String> resultMap = null;
    BeatPlanDetails beatPlanDetails = null;
    List<BeatPlanDistributorAssignments> beatPlanToDistributorsAssignments = null;

    try {
      resultMap = new HashMap<String, String>();
      if (distributorBeatPlanDetailsMap != null && !(distributorBeatPlanDetailsMap.isEmpty())) {
        if (distributorBeatPlanDetailsMap.get(BEAT_PLAN_DETAILS) != null) {
          beatPlanDetails = (BeatPlanDetails) distributorBeatPlanDetailsMap.get(BEAT_PLAN_DETAILS);
          beatPlanToDistributorsAssignments = ((List<BeatPlanDistributorAssignments>) distributorBeatPlanDetailsMap
              .get(BEAT_PLAN_TO_DISTRIBUTOR_DETAILS));

          if (beatPlanToDistributorsAssignments.size() > 0) {
            getHibernateTemplate().save(beatPlanDetails);

            for (BeatPlanDistributorAssignments beatPlanDistributorAssignments : beatPlanToDistributorsAssignments) {
              beatPlanDistributorAssignments.setBeatPlanDetails(beatPlanDetails);
              getHibernateTemplate().save(beatPlanDistributorAssignments);
            }
          } else {
            errorMessage = "Beat Plan must have atleast once Distributor";
          }
        }
      } else {
        result = INVALID_INPUT;
      }

      if (StringUtil.isNotNull(errorMessage)) {
        // Need to roll back the adding the beat plan.

      } else {
        result = SUCCESS;
      }
      resultMap.put(RESULT, result);
      resultMap.put(ERROR_MESSAGE, errorMessage);
    } catch (Exception e) {
      log.error("PROBLEM in the method addDistributorBeatPlan");
      log.error(e);
      e.printStackTrace();
    }

    log.info("END of the method addDistributorBeatPlan");
    return resultMap;
  }

  // ========================================================================

  @SuppressWarnings("unchecked")
  @Override
  public List<CircleDetails> getCircles() throws Exception {
    log.info("START of the method getCircles");
    List<CircleDetails> circles = null;
    StringBuffer hqlQuery;

    try {
      hqlQuery = new StringBuffer("SELECT circleDetails FROM CircleDetails circleDetails WHERE circleDetails.status='" + ACTIVE + "' ");
      circles = getHibernateTemplate().find(hqlQuery.toString());
    } catch (Exception e) {
      log.error("PROBLEM in the method getCircles");
      log.error(e);
      e.printStackTrace();
    }

    log.info("END of the method getCircles");
    return circles;
  }

  // ========================================================================

  @SuppressWarnings("unchecked")
  @Override
  public List<RegionDetails> getRegions(Long circleId) throws Exception {
    log.info("START of the method getRegions");
    List<RegionDetails> regions = null;
    StringBuffer hqlQuery;

    try {
      if (circleId != null && circleId > 0) {
        hqlQuery = new StringBuffer("SELECT regionDetails FROM RegionDetails regionDetails WHERE regionDetails.status='" + ACTIVE + "' ");
        hqlQuery.append(" and regionDetails.circleDetails.circleId=" + circleId);
        regions = getHibernateTemplate().find(hqlQuery.toString());
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method getRegions");
      log.error(e);
      e.printStackTrace();
    }

    log.info("END of the method getRegions");
    return regions;
  }

  // ========================================================================

  @SuppressWarnings("unchecked")
  @Override
  public List<ZoneDetails> getZones(Long regionId) throws Exception {
    log.info("START of the method getZones");
    List<ZoneDetails> zones = null;
    StringBuffer hqlQuery;

    try {
      if (regionId != null && regionId > 0) {
        hqlQuery = new StringBuffer("SELECT zoneDetails FROM ZoneDetails zoneDetails WHERE zoneDetails.status='" + ACTIVE + "' ");
        hqlQuery.append(" and zoneDetails.regionDetails.regionId=" + regionId);
        zones = getHibernateTemplate().find(hqlQuery.toString());
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method getZones");
      log.error(e);
      e.printStackTrace();
    }

    log.info("END of the method getZones");
    return zones;
  }

  // ========================================================================

  @SuppressWarnings("unchecked")
  @Override
  public List<HubDetails> getHubs(Long zoneId) throws Exception {
    log.info("START of the method getHubs");
    List<HubDetails> hubs = null;
    StringBuffer hqlQuery;

    try {
      if (zoneId != null && zoneId > 0) {
        hqlQuery = new StringBuffer("SELECT hubDetails FROM HubDetails hubDetails WHERE hubDetails.status='" + ACTIVE + "' ");
        hqlQuery.append(" and hubDetails.zoneDetails.zoneId=" + zoneId);
        hubs = getHibernateTemplate().find(hqlQuery.toString());
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method getHubs");
      log.error(e);
      e.printStackTrace();
    }

    log.info("END of the method getHubs");
    return hubs;
  }

  // ========================================================================

  @Override
  public List<DistributorDetails> getDistributors(Long userId) throws Exception {
    log.info("START of the method getDistributors");
    List<DistributorDetails> distributors = null;
    StringBuffer nativeQuery;
    Query hibQuery = null;
    @SuppressWarnings("rawtypes")
    List resultList = null;

    try {
      if (userId != null && userId > 0) {
        /*
         * select dd.distributor_id, dd.distributor_name from
         * distributor_details as dd where dd.status=1 and dd.distributor_id in
         * (select dth.distributor_id from distributor_to_hub_details as dth
         * where dth.status=1 and dth.hub_id in (select uth.hub_id from
         * user_to_hub_details as uth where uth.status=1 and uth.user_id = 4));
         */
        nativeQuery = new StringBuffer(" select dd.distributor_id, dd.distributor_name from distributor_details as dd where dd.status='"
            + ACTIVE + "' ");
        nativeQuery.append(" and dd.distributor_id in (select dth.distributor_id from distributor_to_hub_details as dth where dth.status='"
            + ACTIVE + "' ");
        nativeQuery.append(" and dth.hub_id in (select uth.hub_id from user_to_hub_details as uth where uth.status='" + ACTIVE + "' ");
        nativeQuery.append("and uth.user_id =" + userId);
        hibQuery = getSession().createSQLQuery(nativeQuery.toString());
        resultList = hibQuery.list();

        if (resultList != null && resultList.size() > 0) {
          distributors = new ArrayList<DistributorDetails>();

          for (int i = 0; i < resultList.size(); i++) {
            DistributorDetails distributorDetails = new DistributorDetails();
            Object[] result = (Object[]) resultList.get(i);
            distributorDetails.setDistributorId(Long.parseLong(result[0].toString()));
            distributorDetails.setDistributorName(result[1].toString());
            distributors.add(distributorDetails);
          }
        }
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method getDistributors");
      log.error(e);
      e.printStackTrace();
    }

    log.info("END of the method getDistributors");
    return null;
  }

  // ========================================================================

  @SuppressWarnings("unchecked")
  @Override
  public List<DistributorDetails> getDistributorsOfHub(Long hubId) throws Exception {
    log.info("START of the method getDistributorsOfHub");
    List<DistributorDetails> distributors = null;
    StringBuffer hqlQuery;
    List<DistributorToHubDetails> distributorToHubAssignments = null;

    try {
      if (hubId != null && hubId.longValue() > 0) {
        hqlQuery = new StringBuffer("SELECT distributorToHubDetails FROM DistributorToHubDetails distributorToHubDetails ");
        hqlQuery.append(" WHERE distributorToHubDetails.status='" + ACTIVE + "'");
        hqlQuery.append(" AND distributorToHubDetails.hubDetails.hubId=" + hubId);
        hqlQuery.append(" AND distributorToHubDetails.distributorDetails.status='" + ACTIVE + "'");
        distributorToHubAssignments = getHibernateTemplate().find(hqlQuery.toString());

        if (distributorToHubAssignments != null && distributorToHubAssignments.size() > 0) {
          distributors = new ArrayList<DistributorDetails>();
          for (DistributorToHubDetails distributorToHubDetails : distributorToHubAssignments) {
            if (!isDistributorAssignedToBeatPlan(distributorToHubDetails.getDistributorDetails().getDistributorId())) {
              distributors.add(distributorToHubDetails.getDistributorDetails());
            }
          }
        }
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method getDistributorsOfHub");
      log.error(e);
      e.printStackTrace();
    }
    log.info("END of the method getDistributorsOfHub");
    return distributors;
  }

  // ========================================================================

  @SuppressWarnings("unchecked")
  private boolean isDistributorAssignedToBeatPlan(Long distributorId) {
    boolean isDistributorAssignedToBeatPlan = false;
    StringBuffer hqlQuery;
    List<BeatPlanDistributorAssignments> bpdaList = null;

    try {
      if (distributorId != null && distributorId.longValue() > 0) {
        hqlQuery = new StringBuffer("SELECT bpda FROM BeatPlanDistributorAssignments bpda ");
        hqlQuery.append(" WHERE bpda.status='" + ACTIVE + "'");
        hqlQuery.append(" AND bpda.distributorDetails.distributorId=" + distributorId);
        bpdaList = getHibernateTemplate().find(hqlQuery.toString());

        if (bpdaList != null && bpdaList.size() > 0) {
          isDistributorAssignedToBeatPlan = true;
        }
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method isDistributorAssignedToBeatPlan");
      log.error(e);
      e.printStackTrace();
    }
    log.info("END of the method isDistributorAssignedToBeatPlan");
    return isDistributorAssignedToBeatPlan;
  }

  // ========================================================================

  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public List<DistributorBeatPlansDetails> getDistributorBeatPlans(Long userId, Long roleId) throws Exception {
    log.info("START of the method getDistributorBeatPlans");
    StringBuffer hqlQuery;
    List<UserToHubDetails> hubsOfUser = null;
    List<DistributorToHubDetails> distributorToHubAssignments = null;
    StringBuffer hubIdsSB = new StringBuffer("");
    StringBuffer distributorIdsSB = new StringBuffer("");
    List<HubDetails> hubs = null;
    List<BeatPlanDistributorAssignments> bpdaList = null;
    List<DistributorBeatPlansDetails> distributorBeatPlans = null;
    Query hibQuery = null;
    List resultList = null;

    try {
      if (userId != null && userId.longValue() > 0 && roleId != null && roleId.longValue() > 0) {

        if (roleId.longValue() == SYSTEM_ADMIN) {
          hqlQuery = new StringBuffer("SELECT hubDetails FROM HubDetails hubDetails WHERE ");
          hqlQuery.append(" hubDetails.status='" + ACTIVE + "'");
          hubs = getHibernateTemplate().find(hqlQuery.toString());

          if (hubs != null && hubs.size() > 0) {
            for (HubDetails hubDetails : hubs) {
              hubIdsSB.append(hubDetails.getHubId());
              hubIdsSB.append(",");
            }
          }
        } else {
          hqlQuery = new StringBuffer("SELECT userToHubDetails FROM UserToHubDetails userToHubDetails WHERE ");
          hqlQuery.append(" userToHubDetails.status='" + ACTIVE + "' AND ");
          hqlQuery.append(" userToHubDetails.userDetails.userId=" + userId);
          hubsOfUser = getHibernateTemplate().find(hqlQuery.toString());

          if (hubsOfUser != null && hubsOfUser.size() > 0) {
            for (UserToHubDetails userToHubDetails : hubsOfUser) {
              hubIdsSB.append(userToHubDetails.getHubDetails().getHubId());
              hubIdsSB.append(",");
            }
          }
        }

        if (hubIdsSB.length() > 0) {
          hqlQuery = new StringBuffer(" SELECT dtha FROM DistributorToHubDetails as dtha WHERE dtha.status='" + ACTIVE + "' ");
          hqlQuery.append(" and dtha.hubDetails.hubId in (" + StringUtil.removeTrailingValue(hubIdsSB, ",") + ")");
          distributorToHubAssignments = getHibernateTemplate().find(hqlQuery.toString());

          if (distributorToHubAssignments != null && distributorToHubAssignments.size() > 0) {
            for (DistributorToHubDetails dtha : distributorToHubAssignments) {
              distributorIdsSB.append(dtha.getDistributorDetails().getDistributorId());
              distributorIdsSB.append(",");
            }
          }

          if (distributorIdsSB.length() > 0) {
            hqlQuery = new StringBuffer("SELECT bpda FROM BeatPlanDistributorAssignments as bpda WHERE bpda.status='" + ACTIVE + "' ");
            hqlQuery.append(" and bpda.distributorDetails.distributorId in (" + StringUtil.removeTrailingValue(distributorIdsSB, ",") + ")");
            hqlQuery.append(" group by bpda.beatPlanDetails.beatPlanId ");
            bpdaList = getHibernateTemplate().find(hqlQuery.toString());

            if (bpdaList != null && bpdaList.size() > 0) {
              distributorBeatPlans = new ArrayList<DistributorBeatPlansDetails>();

              for (BeatPlanDistributorAssignments bpda : bpdaList) {
                BeatPlanDetails beatPlanDetails = bpda.getBeatPlanDetails();
                DistributorBeatPlansDetails distributorBeatPlansDetails = new DistributorBeatPlansDetails();
                distributorBeatPlansDetails.setBeatPlanId(beatPlanDetails.getBeatPlanId());
                distributorBeatPlansDetails.setBeatPlanCode(beatPlanDetails.getBeatPlanCode());
                distributorBeatPlansDetails.setBeatPlanName(beatPlanDetails.getBeatPlanName());
                HubDetails hubDetails = getHubOfDistributor(bpda.getDistributorDetails().getDistributorId());

                if (hubDetails != null) {
                  distributorBeatPlansDetails.setHubName(hubDetails.getHubName());
                  distributorBeatPlansDetails.setZoneName(hubDetails.getZoneDetails().getZoneName());
                  distributorBeatPlansDetails.setRegionName(hubDetails.getZoneDetails().getRegionDetails().getRegionName());
                  distributorBeatPlansDetails.setCircleName(hubDetails.getZoneDetails().getRegionDetails().getCircleDetails().getCircleName());
                }
                String nativeQuery = " select count(*) from beat_plan_user_assignments as bpua where bpua.status='" + ACTIVE
                    + "' and bpua.bp_dist_assignment_id=" + bpda.getBpDistAssignmentsId();
                hibQuery = getSession().createSQLQuery(nativeQuery);
                resultList = hibQuery.list();

                if (resultList != null && resultList.size() > 0) {
                  BigInteger resultBigInt = (BigInteger) resultList.get(0);
                  if (resultBigInt.intValue() > 0) {
                    distributorBeatPlansDetails.setAllowDelete(false);
                  } else {
                    distributorBeatPlansDetails.setAllowDelete(true);
                  }
                }
                distributorBeatPlans.add(distributorBeatPlansDetails);
              }
            }
          }
        }
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method getDistributorBeatPlans");
      log.error(e);
      e.printStackTrace();
    }
    log.info("START of the method getDistributorBeatPlans");
    return distributorBeatPlans;
  }

  // ========================================================================

  @SuppressWarnings("unchecked")
  private HubDetails getHubOfDistributor(Long distributorId) throws Exception {
    HubDetails hubDetails = null;

    if (distributorId != null && distributorId.longValue() > 0) {
      StringBuffer hqlQuery = new StringBuffer("SELECT distributorToHubDetails FROM DistributorToHubDetails distributorToHubDetails ");
      hqlQuery.append(" WHERE distributorToHubDetails.status='" + ACTIVE + "'");
      hqlQuery.append(" AND distributorToHubDetails.distributorDetails.distributorId=" + distributorId);
      List<DistributorToHubDetails> distributorToHubAssignments = getHibernateTemplate().find(hqlQuery.toString());

      if (distributorToHubAssignments != null && distributorToHubAssignments.size() > 0) {
        hubDetails = distributorToHubAssignments.get(0).getHubDetails();
      }
    }
    return hubDetails;
  }

  // ========================================================================

  @SuppressWarnings("unchecked")
  @Override
  public boolean isBeatPlanCodeExists(String beatPlanCode) throws Exception {
    log.info("START of the method isBeatPlanCodeExists");
    StringBuffer hqlQuery;
    List<UserDetails> resultList = null;
    boolean isBeatPlanCodeExist = true;

    try {
      if (StringUtil.isNotNull(beatPlanCode)) {
        hqlQuery = new StringBuffer("SELECT bd FROM BeatPlanDetails ud WHERE bd.beatPlanCode='" + beatPlanCode + "'");
        resultList = getHibernateTemplate().find(hqlQuery.toString());

        if (resultList != null && resultList.size() > 0) {
          isBeatPlanCodeExist = true;
        } else {
          isBeatPlanCodeExist = false;
        }
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method isBeatPlanCodeExists");
      log.error(e);
      e.printStackTrace();
    }
    log.info("START of the method isBeatPlanCodeExists");
    return isBeatPlanCodeExist;
  }

  // ========================================================================

  @SuppressWarnings("unchecked")
  @Override
  public boolean isBeatPlanExists(String beatPlanName) throws Exception {
    log.info("START of the method isBeatPlanExists");
    StringBuffer hqlQuery;
    List<UserDetails> resultList = null;
    boolean isBeatPlanExists = true;

    try {
      if (StringUtil.isNotNull(beatPlanName)) {
        hqlQuery = new StringBuffer("SELECT bd FROM BeatPlanDetails ud WHERE bd.beatPlanName='" + beatPlanName + "'");
        resultList = getHibernateTemplate().find(hqlQuery.toString());

        if (resultList != null && resultList.size() > 0) {
          isBeatPlanExists = true;
        } else {
          isBeatPlanExists = false;
        }
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method isBeatPlanExists");
      log.error(e);
      e.printStackTrace();
    }
    log.info("START of the method isBeatPlanExists");
    return isBeatPlanExists;
  }

  // ========================================================================

  @SuppressWarnings("unchecked")
  @Override
  public Map<String, String> updateDistributorBeatPlan(Map<String, Object> distributorBeatPlanDetailsMap) throws Exception {
    log.info("START of the method updateDistributorBeatPlan");
    String result = FAILURE;
    String errorMessage = "";
    Map<String, String> resultMap = null;
    BeatPlanDetails beatPlanDetails = null;
    List<BeatPlanDistributorAssignments> beatPlanToDistributorsAssignments = null;

    try {
      resultMap = new HashMap<String, String>();
      if (distributorBeatPlanDetailsMap != null && !(distributorBeatPlanDetailsMap.isEmpty())) {
        if (distributorBeatPlanDetailsMap.get(BEAT_PLAN_DETAILS) != null) {
          beatPlanDetails = (BeatPlanDetails) distributorBeatPlanDetailsMap.get(BEAT_PLAN_DETAILS);
          beatPlanToDistributorsAssignments = ((List<BeatPlanDistributorAssignments>) distributorBeatPlanDetailsMap
              .get(BEAT_PLAN_TO_DISTRIBUTOR_DETAILS));

          if (beatPlanToDistributorsAssignments.size() > 0) {
            // getHibernateTemplate().merge(beatPlanDetails);
            for (BeatPlanDistributorAssignments beatPlanDistributorAssignments : beatPlanToDistributorsAssignments) {
              beatPlanDistributorAssignments.setBeatPlanDetails(beatPlanDetails);
              if (beatPlanDistributorAssignments.getBpDistAssignmentsId() != null
                  && beatPlanDistributorAssignments.getBpDistAssignmentsId() == 0) {
                beatPlanDistributorAssignments.setBpDistAssignmentsId(null);
              }
              getHibernateTemplate().merge(beatPlanDistributorAssignments);
            }
          } else {
            errorMessage = "Beat Plan must have atleast once Distributor";
          }
        }
      } else {
        result = INVALID_INPUT;
      }

      if (StringUtil.isNotNull(errorMessage)) {
        // Need to roll back the adding the beat plan.

      } else {
        result = SUCCESS;
      }
      resultMap.put(RESULT, result);
      resultMap.put(ERROR_MESSAGE, errorMessage);
    } catch (Exception e) {
      log.error("PROBLEM in the method updateDistributorBeatPlan");
      log.error(e);
      e.printStackTrace();
    }

    log.info("END of the method updateDistributorBeatPlan");
    return resultMap;
  }

  // ========================================================================

  @SuppressWarnings("unchecked")
  @Override
  public String deleteDistributorBeatPlan(Long beatPlanId) throws Exception {
    log.info("START of the method deleteDistributorBeatPlan");
    String result = FAILURE;
    StringBuffer hqlQuery = null;
    List<BeatPlanDistributorAssignments> bpdaList = null;
    BeatPlanDetails beatPlanDetails = null;

    try {
      if (beatPlanId != null && beatPlanId.longValue() > 0) {
        beatPlanDetails = (BeatPlanDetails) getHibernateTemplate().get(BeatPlanDetails.class, beatPlanId);

        if (beatPlanDetails != null) {
          hqlQuery = new StringBuffer("SELECT bpda FROM BeatPlanDistributorAssignments as bpda WHERE bpda.status='" + ACTIVE + "' ");
          hqlQuery.append(" and bpda.beatPlanDetails.beatPlanId=" + beatPlanId);
          bpdaList = getHibernateTemplate().find(hqlQuery.toString());

          if (bpdaList != null && bpdaList.size() > 0) {
            for (BeatPlanDistributorAssignments beatPlanDistributorAssignments : bpdaList) {
              if (beatPlanDistributorAssignments != null) {
                beatPlanDistributorAssignments.setStatus(IN_ACTIVE);
                getHibernateTemplate().merge(beatPlanDistributorAssignments);
              }
            }
          }
          beatPlanDetails.setStatus(IN_ACTIVE);
          getHibernateTemplate().merge(beatPlanDetails);
          result = SUCCESS;
        }
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method deleteDistributorBeatPlan");
      log.error(e);
      e.printStackTrace();
    }
    log.info("END of the method deleteDistributorBeatPlan");
    return result;
  }

  // ========================================================================
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public Map<String, Object> getDistributorBeatPlan(Long beatPlanId) throws Exception {
    log.info("START of the method getDistributorBeatPlan");
    StringBuffer hqlQuery = null;
    BeatPlanDetails beatPlanDetails = null;
    List<BeatPlanDistributorAssignments> bpdaList = null;
    Map<String, Object> distributorBeatPlanMap = null;
    Query hibQuery = null;
    List resultList = null;
    StringBuffer nativeQuery = null;
    Long hubId = null;
    HubDetails hubDetails = null;
    ZoneDetails zoneDetails = null;
    RegionDetails regionDetails = null;
    CircleDetails circleDetails = null;
    List<CircleDetails> circles = null;
    List<RegionDetails> regions = null;
    List<ZoneDetails> zones = null;
    List<HubDetails> hubs = null;

    try {
      if (beatPlanId != null && beatPlanId.longValue() > 0) {
        distributorBeatPlanMap = new HashMap<>();
        beatPlanDetails = (BeatPlanDetails) getHibernateTemplate().get(BeatPlanDetails.class, beatPlanId);

        if (beatPlanDetails != null) {
          hqlQuery = new StringBuffer("SELECT bpda FROM BeatPlanDistributorAssignments as bpda WHERE bpda.status='" + ACTIVE + "' ");
          hqlQuery.append(" and bpda.beatPlanDetails.beatPlanId=" + beatPlanId);
          bpdaList = getHibernateTemplate().find(hqlQuery.toString());
          distributorBeatPlanMap.put(BEAT_PLAN_DETAILS, beatPlanDetails);
          distributorBeatPlanMap.put(BEAT_PLAN_TO_DISTRIBUTOR_DETAILS, bpdaList);
          /*
           * // Getting the hub id of the beat plan select dthd.hub_id from
           * distributor_to_hub_details as dthd where dthd.status=1 and
           * dthd.distributor_id in (select bpda.distributor_id from
           * beat_plan_distributor_assignments as bpda where bpda.status=1 and
           * bpda.beat_plan_id=1 group by bpda.beat_plan_id);
           */
          nativeQuery = new StringBuffer("select dthd.hub_id from distributor_to_hub_details as dthd ");
          nativeQuery.append(" where dthd.status='" + ACTIVE + "' and dthd.distributor_id in (select bpda.distributor_id from ");
          nativeQuery.append(" beat_plan_distributor_assignments as bpda where bpda.status='" + ACTIVE + "' and ");
          nativeQuery.append(" bpda.beat_plan_id='" + beatPlanId + "' group by bpda.beat_plan_id) group by dthd.hub_id");
          hibQuery = getSession().createSQLQuery(nativeQuery.toString());
          resultList = hibQuery.list();

          if (resultList != null && resultList.size() > 0) {
            BigInteger hubBigInt = (BigInteger) resultList.get(0);
            hubId = hubBigInt.longValue();

            if (hubId != null && hubId.longValue() > 0) {
              hubDetails = (HubDetails) getHibernateTemplate().get(HubDetails.class, hubId);
              zoneDetails = hubDetails.getZoneDetails();
              regionDetails = zoneDetails.getRegionDetails();
              circleDetails = regionDetails.getCircleDetails();
              circles = new ArrayList<CircleDetails>();
              circles.add(circleDetails);
              regions = getRegions(circleDetails.getCircleId());
              zones = getZones(regionDetails.getRegionId());
              hubs = getHubs(zoneDetails.getZoneId());
              distributorBeatPlanMap.put(CIRCLES, circles);
              distributorBeatPlanMap.put(REGIONS, regions);
              distributorBeatPlanMap.put(ZONES, zones);
              distributorBeatPlanMap.put(HUBS, hubs);
              distributorBeatPlanMap.put(CIRCLE_ID, circleDetails.getCircleId());
              distributorBeatPlanMap.put(REGION_ID, regionDetails.getRegionId());
              distributorBeatPlanMap.put(ZONE_ID, zoneDetails.getZoneId());
              distributorBeatPlanMap.put(HUB_ID, hubId);
              distributorBeatPlanMap.put(CIRCLE_NAME, circleDetails.getCircleName());
              distributorBeatPlanMap.put(REGION_NAME, regionDetails.getRegionName());
              distributorBeatPlanMap.put(ZONE_NAME, zoneDetails.getZoneName());
              distributorBeatPlanMap.put(HUB_NAME, hubDetails.getHubName());
            }
          }
        }
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method getDistributorBeatPlan");
      log.error(e);
      e.printStackTrace();
    }
    log.info("END of the method getDistributorBeatPlan");
    return distributorBeatPlanMap;
  }

  // ========================================================================

  /*@SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public List<BeatPlanDetails> getBeatPlans(Long userId, Long roleId) throws Exception {
    log.info("START of the method getBeatPlans");
    StringBuffer hqlQuery = null;
    List<BeatPlanDetails> beatPlans = null;
    StringBuffer nativeQuery = null;
    Query hibQuery = null;
    List resultList = null;

    try {
      if (userId != null && userId.longValue() > 0 && roleId != null && roleId.longValue() > 0) {
        if (roleId.longValue() == SYSTEM_ADMIN) {
          hqlQuery = new StringBuffer(" SELECT bpd FROM BeatPlanDetails as bpd WHERE bpd.status='" + ACTIVE + "'");
          beatPlans = getHibernateTemplate().find(hqlQuery.toString());
        } else if (roleId.longValue() == HUB_MANAGER) {
          
           * select bpd.beat_plan_id, bpd.beat_plan_name from beat_plan_details
           * as bpd where bpd.status=1 and bpd.beat_plan_id in (select
           * bpda.bp_dist_assignments_id from beat_plan_distributor_assignments
           * as bpda where bpda.status=1 and bpda.distributor_id in (select
           * dthd.distributor_id from distributor_to_hub_details as dthd where
           * dthd.status=1 and dthd.hub_id in (select uthd.hub_id from
           * user_to_hub_details as uthd where uthd.status=1 and
           * uthd.user_id=11) group by dthd.distributor_id) group by
           * bpda.beat_plan_id);
           
          nativeQuery = new StringBuffer("select bpd.beat_plan_id, bpd.beat_plan_name from beat_plan_details as bpd where ");
          nativeQuery.append(" bpd.status='" + ACTIVE + "' and bpd.beat_plan_id in (select bpda.bp_dist_assignments_id from ");
          nativeQuery.append(" beat_plan_distributor_assignments as bpda where bpda.status='" + ACTIVE + "' and ");
          nativeQuery.append(" bpda.distributor_id in (select dthd.distributor_id from distributor_to_hub_details as dthd ");
          nativeQuery.append(" where dthd.status='" + ACTIVE + "' and dthd.hub_id in (select uthd.hub_id from user_to_hub_details as uthd ");
          nativeQuery.append(" where uthd.status='" + ACTIVE + "' and uthd.user_id=" + userId);
          nativeQuery.append(" ) group by dthd.distributor_id) group by bpda.beat_plan_id)");
          hibQuery = getSession().createSQLQuery(nativeQuery.toString());
          resultList = hibQuery.list();
          
          if (resultList != null && resultList.size() > 0) {
            beatPlans = new ArrayList<BeatPlanDetails>();
            for (int i = 0; i < resultList.size(); i++) {
              BeatPlanDetails beatPlanDetails = new BeatPlanDetails();
              Object[] results = (Object[]) resultList.get(i);
              beatPlanDetails.setBeatPlanId(Long.parseLong(results[0].toString()));
              beatPlanDetails.setBeatPlanName(results[1].toString());
              beatPlans.add(beatPlanDetails);
            }
          }
        }
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method getBeatPlans");
      log.error(e);
      e.printStackTrace();
    }
    log.info("END of the method getBeatPlans");
    log.info("END of the method getDistributorBeatPlans");
    return beatPlans;
  }*/
  
  //==========================================================================
  
  @SuppressWarnings("unchecked")
  public List<BeatPlanDetails> getBeatPlans(Long userId, Long roleId) throws Exception {
    log.info("START of the method getBeatPlans");
    StringBuffer hqlQuery;
    List<UserToHubDetails> hubsOfUser = null;
    List<DistributorToHubDetails> distributorToHubAssignments = null;
    StringBuffer hubIdsSB = new StringBuffer("");
    StringBuffer distributorIdsSB = new StringBuffer("");
    List<HubDetails> hubs = null;
    List<BeatPlanDistributorAssignments> bpdaList = null;
    List<BeatPlanDetails> beatPlans = null;

    try {
      if (userId != null && userId.longValue() > 0 && roleId != null && roleId.longValue() > 0) {

        if (roleId.longValue() == SYSTEM_ADMIN) {
          hqlQuery = new StringBuffer("SELECT hubDetails FROM HubDetails hubDetails WHERE ");
          hqlQuery.append(" hubDetails.status='" + ACTIVE + "'");
          hubs = getHibernateTemplate().find(hqlQuery.toString());

          if (hubs != null && hubs.size() > 0) {
            for (HubDetails hubDetails : hubs) {
              hubIdsSB.append(hubDetails.getHubId());
              hubIdsSB.append(",");
            }
          }
        } else {
          hqlQuery = new StringBuffer("SELECT userToHubDetails FROM UserToHubDetails userToHubDetails WHERE ");
          hqlQuery.append(" userToHubDetails.status='" + ACTIVE + "' AND ");
          hqlQuery.append(" userToHubDetails.userDetails.userId=" + userId);
          hubsOfUser = getHibernateTemplate().find(hqlQuery.toString());

          if (hubsOfUser != null && hubsOfUser.size() > 0) {
            for (UserToHubDetails userToHubDetails : hubsOfUser) {
              hubIdsSB.append(userToHubDetails.getHubDetails().getHubId());
              hubIdsSB.append(",");
            }
          }
        }

        if (hubIdsSB.length() > 0) {
          hqlQuery = new StringBuffer(" SELECT dtha FROM DistributorToHubDetails as dtha WHERE dtha.status='" + ACTIVE + "' ");
          hqlQuery.append(" and dtha.hubDetails.hubId in (" + StringUtil.removeTrailingValue(hubIdsSB, ",") + ")");
          distributorToHubAssignments = getHibernateTemplate().find(hqlQuery.toString());

          if (distributorToHubAssignments != null && distributorToHubAssignments.size() > 0) {
            for (DistributorToHubDetails dtha : distributorToHubAssignments) {
              distributorIdsSB.append(dtha.getDistributorDetails().getDistributorId());
              distributorIdsSB.append(",");
            }
          }

          if (distributorIdsSB.length() > 0) {
            hqlQuery = new StringBuffer("SELECT bpda FROM BeatPlanDistributorAssignments as bpda WHERE bpda.status='" + ACTIVE + "' ");
            hqlQuery.append(" and bpda.distributorDetails.distributorId in (" + StringUtil.removeTrailingValue(distributorIdsSB, ",") + ")");
            hqlQuery.append(" group by bpda.beatPlanDetails.beatPlanId ");
            bpdaList = getHibernateTemplate().find(hqlQuery.toString());

            if (bpdaList != null && bpdaList.size() > 0) {
              beatPlans = new ArrayList<>();

              for (BeatPlanDistributorAssignments bpda : bpdaList) {
                BeatPlanDetails beatPlanDetails = bpda.getBeatPlanDetails();
                beatPlans.add(beatPlanDetails);
              }
            }
          }
        }
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method getBeatPlans");
      log.error(e);
      e.printStackTrace();
    }
    log.info("END of the method getBeatPlans");
    return beatPlans;
  }

  // ========================================================================

  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public Map<String, Object> getDistributorsAndRunners(Long beatPlanId) throws Exception {
    log.info("START of the method getDistributorsAndRunners");
    StringBuffer nativeQuery = null;
    Query hibQuery = null;
    List resultList = null;
    Long hubId = null;
    List<DistributorDetails> distributors = null;
    HubDetails hubDetails = null;
    ZoneDetails zoneDetails = null;
    RegionDetails regionDetails = null;
    StringBuffer hqlQuery = null;
    List<UserDetails> runners = null;
    List<UserToHubDetails> uthList = null;
    Map<String, Object> distributorsAndRunnersDetailsMap = null;
    List<BeatPlanDistributorAssignments> bpdaList = null;

    try {
      if (beatPlanId != null && beatPlanId.longValue() > 0) {
        /*
         * // Getting the hub id of the beat plan select dthd.hub_id from
         * distributor_to_hub_details as dthd where dthd.status=1 and
         * dthd.distributor_id in (select bpda.distributor_id from
         * beat_plan_distributor_assignments as bpda where bpda.status=1 and
         * bpda.beat_plan_id=1 group by bpda.beat_plan_id);
         */

        nativeQuery = new StringBuffer("select dthd.hub_id from distributor_to_hub_details as dthd ");
        nativeQuery.append(" where dthd.status='" + ACTIVE + "' and dthd.distributor_id in (select bpda.distributor_id from ");
        nativeQuery.append(" beat_plan_distributor_assignments as bpda where bpda.status='" + ACTIVE + "' and ");
        nativeQuery.append(" bpda.beat_plan_id='" + beatPlanId + "' group by bpda.beat_plan_id) group by dthd.hub_id");
        hibQuery = getSession().createSQLQuery(nativeQuery.toString());
        resultList = hibQuery.list();

        if (resultList != null && resultList.size() > 0) {
          BigInteger hubBigInt = (BigInteger) resultList.get(0);
          hubId = hubBigInt.longValue();

          if (hubId != null && hubId.longValue() > 0) {
            // distributorsOfHub = getDistributorsOfHub(hubId);
            hqlQuery = new StringBuffer("SELECT bpda from BeatPlanDistributorAssignments as bpda where bpda.status='" + ACTIVE + "' ");
            hqlQuery.append(" AND bpda.beatPlanDetails.beatPlanId=" + beatPlanId);
            hqlQuery.append(" AND bpda.distributorDetails.status='" + ACTIVE + "'");
            bpdaList = getHibernateTemplate().find(hqlQuery.toString());

            if (bpdaList != null && bpdaList.size() > 0) {
              distributors = new ArrayList<DistributorDetails>();
              Set<Long> distributorIdsSet = new HashSet<Long>();

              for (BeatPlanDistributorAssignments bpda : bpdaList) {
                if (distributorIdsSet.add(bpda.getDistributorDetails().getDistributorId())) {
                  distributors.add(bpda.getDistributorDetails());
                }
              }
            }
            hubDetails = (HubDetails) getHibernateTemplate().get(HubDetails.class, hubId);
            zoneDetails = hubDetails.getZoneDetails();
            regionDetails = zoneDetails.getRegionDetails();

            hqlQuery = new StringBuffer("SELECT userToHubDetails FROM UserToHubDetails userToHubDetails WHERE ");
            hqlQuery.append(" userToHubDetails.status='" + ACTIVE + "' AND ");
            hqlQuery.append(" userToHubDetails.hubDetails.hubId=" + hubId);
            hqlQuery.append(" AND userToHubDetails.userDetails.roleDetails.roleId='" + RUNNER + "'");
            hqlQuery.append(" AND userToHubDetails.userDetails.status='" + ACTIVE + "'");
            uthList = getHibernateTemplate().find(hqlQuery.toString());

            if (uthList != null && uthList.size() > 0) {
              runners = new ArrayList<UserDetails>();
              for (UserToHubDetails userToHubDetails : uthList) {
                runners.add(userToHubDetails.getUserDetails());
              }
            }
            distributorsAndRunnersDetailsMap = new HashMap<String, Object>();
            distributorsAndRunnersDetailsMap.put(CIRCLE_NAME, regionDetails.getCircleDetails().getCircleName());
            distributorsAndRunnersDetailsMap.put(REGION_NAME, regionDetails.getRegionName());
            distributorsAndRunnersDetailsMap.put(ZONE_NAME, zoneDetails.getZoneName());
            distributorsAndRunnersDetailsMap.put(HUB_NAME, hubDetails.getHubName());
            distributorsAndRunnersDetailsMap.put(DISTRIBUTORS, distributors);
            distributorsAndRunnersDetailsMap.put(RUNNERS, runners);
          }
        }
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method getDistributorsAndRunners");
      log.error(e);
      e.printStackTrace();
    }

    log.info("END of the method getDistributorsAndRunners");
    return distributorsAndRunnersDetailsMap;
  }

  // ========================================================================

  @SuppressWarnings("unchecked")
  @Override
  public Map<String, String> updateRunnerBeatPlan(Map<String, Object> runnerBeatPlanDetailsMap) throws Exception {
    log.info("START of the method updateRunnerBeatPlan");
    String result = FAILURE;
    String errorMessage = "";
    Map<String, String> resultMap = null;
    List<BeatPlanUserAssignments> beatPlanUserAssignmentsList = null;
    BeatPlanDetails beatPlanDetails = null;

    try {
      resultMap = new HashMap<String, String>();

      if (runnerBeatPlanDetailsMap != null && !(runnerBeatPlanDetailsMap.isEmpty())) {
        beatPlanDetails = (BeatPlanDetails) runnerBeatPlanDetailsMap.get(BEAT_PLAN_DETAILS);
        beatPlanUserAssignmentsList = ((List<BeatPlanUserAssignments>) runnerBeatPlanDetailsMap.get(BEAT_PLAN_TO_USER_DETAILS));

        if (beatPlanDetails != null && beatPlanUserAssignmentsList != null && beatPlanUserAssignmentsList.size() > 0) {
          for (BeatPlanUserAssignments bpua : beatPlanUserAssignmentsList) {
            BeatPlanDistributorAssignments bpda = getBeatPlanDistributorAssignment(beatPlanDetails.getBeatPlanId(), bpua.getDistributorId());
            if (bpua.getBpUserAssignmentId() != null && bpua.getBpUserAssignmentId() == 0) {
              bpua.setBpUserAssignmentId(null);
            }
            bpua.setBeatPlanDistributorAssignments(bpda);
            getHibernateTemplate().merge(bpua);
          }
          result = SUCCESS;
        } else {
          errorMessage = INVALID_INPUT;
        }
      } else {
        errorMessage = INVALID_INPUT;
      }
      resultMap.put(RESULT, result);
      resultMap.put(ERROR_MESSAGE, errorMessage);
    } catch (Exception e) {
      log.error("PROBLEM in the method updateRunnerBeatPlan");
      log.error(e);
      e.printStackTrace();
    }

    log.info("END of the method updateRunnerBeatPlan");
    return resultMap;
  }

  // ========================================================================

  @SuppressWarnings("unchecked")
  @Override
  public Map<String, Object> getRunnerBeatPlan(Long beatPlanId) throws Exception {
    log.info("START of the method getRunnerBeatPlan");
    Map<String, Object> resultMap = null;
    StringBuffer hqlQuery = null;
    List<BeatPlanUserAssignments> bpuaList = null;
    BeatPlanDetails beatPlanDetails = null;

    try {
      resultMap = new HashMap<String, Object>();

      if (beatPlanId != null && beatPlanId.longValue() > 0) {
        /*
         * select * from beat_plan_user_assignments as bpua where bpua.status=1
         * and bpua.bp_dist_assignment_id in ( select
         * bpda.bp_dist_assignments_id from beat_plan_distributor_assignments as
         * bpda where bpda.beat_plan_id=1 and bpda.status=1) order by
         * bpua.schedule_time
         */
        hqlQuery = new StringBuffer(" SELECT bpua from BeatPlanUserAssignments as bpua WHERE bpua.status='" + ACTIVE + "' ");
        hqlQuery.append(" AND bpua.beatPlanDistributorAssignments.bpDistAssignmentsId in (");
        hqlQuery.append(" SELECT bpda.bpDistAssignmentsId FROM BeatPlanDistributorAssignments as bpda ");
        hqlQuery.append(" WHERE bpda.beatPlanDetails.beatPlanId=" + beatPlanId + " AND bpda.status='" + ACTIVE + "')");
        hqlQuery.append(" order by bpua.scheduleTime");
        bpuaList = getHibernateTemplate().find(hqlQuery.toString());

        if (bpuaList != null && bpuaList.size() > 0) {
          beatPlanDetails = bpuaList.get(0).getBeatPlanDistributorAssignments().getBeatPlanDetails();
          resultMap.put(BEAT_PLAN_DETAILS, beatPlanDetails);

          for (BeatPlanUserAssignments bpua : bpuaList) {
            DistributorDetails distributorDetails = bpua.getBeatPlanDistributorAssignments().getDistributorDetails();
            bpua.setDistributorId(distributorDetails.getDistributorId());
            bpua.setDistributorName(distributorDetails.getDistributorName());
            bpua.setBeatPlanName(beatPlanDetails.getBeatPlanName());
            //bpua.setVisitNo(getVisitNo(bpuaList, distributorDetails.getDistributorId()));
          }
          resultMap.put(BEAT_PLAN_TO_USER_DETAILS, bpuaList);
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

  // ==========================================================================

  /*private int getVisitNo(List<BeatPlanUserAssignments> bpuaList, Long distributorId) {
    int visitNo = 0;

    try {
      if (bpuaList != null && bpuaList.size() > 0 && distributorId != null && distributorId.longValue() > 0) {
        for (BeatPlanUserAssignments bpua : bpuaList) {
          if (bpua.getDistributorId() == distributorId) {
            visitNo = visitNo + bpua.getVisitNo();
          }
        }
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method getVisitNo");
      log.error(e);
      e.printStackTrace();
    }
    return visitNo + 1;
  }

  // ==========================================================================
*/
  @SuppressWarnings("unchecked")
  @Override
  public Map<String, String> addRunnerBeatPlan(Map<String, Object> runnerBeatPlanDetailsMap) throws Exception {
    log.info("START of the method addRunnerBeatPlan");
    String result = FAILURE;
    String errorMessage = "";
    Map<String, String> resultMap = null;
    List<BeatPlanUserAssignments> beatPlanUserAssignmentsList = null;
    BeatPlanDetails beatPlanDetails = null;

    try {
      resultMap = new HashMap<String, String>();

      if (runnerBeatPlanDetailsMap != null && !(runnerBeatPlanDetailsMap.isEmpty())) {
        beatPlanDetails = (BeatPlanDetails) runnerBeatPlanDetailsMap.get(BEAT_PLAN_DETAILS);
        beatPlanUserAssignmentsList = ((List<BeatPlanUserAssignments>) runnerBeatPlanDetailsMap.get(BEAT_PLAN_TO_USER_DETAILS));

        if (beatPlanDetails != null && beatPlanUserAssignmentsList != null && beatPlanUserAssignmentsList.size() > 0) {
          for (BeatPlanUserAssignments bpua : beatPlanUserAssignmentsList) {
            BeatPlanDistributorAssignments bpda = getBeatPlanDistributorAssignment(beatPlanDetails.getBeatPlanId(), bpua.getDistributorId());
            bpua.setBeatPlanDistributorAssignments(bpda);
            getHibernateTemplate().save(bpua);
          }
          result = SUCCESS;
        } else {
          errorMessage = INVALID_INPUT;
        }
      } else {
        errorMessage = INVALID_INPUT;
      }
      resultMap.put(RESULT, result);
      resultMap.put(ERROR_MESSAGE, errorMessage);
    } catch (Exception e) {
      log.error("PROBLEM in the method addRunnerBeatPlan");
      log.error(e);
      e.printStackTrace();
    }

    log.info("END of the method addRunnerBeatPlan");
    return resultMap;
  }

  // ========================================================================

  @SuppressWarnings("unchecked")
  private BeatPlanDistributorAssignments getBeatPlanDistributorAssignment(Long beatPlanId, Long distributorId) throws Exception {
    BeatPlanDistributorAssignments beatPlanDistributorAssignments = null;
    StringBuffer hqlQuery = null;
    List<BeatPlanDistributorAssignments> bpdaList = null;

    try {
      if (beatPlanId != null && distributorId != null && beatPlanId.longValue() > 0 && distributorId.longValue() > 0) {
        hqlQuery = new StringBuffer(" SELECT bpda FROM BeatPlanDistributorAssignments as bpda where bpda.status='" + ACTIVE + "' ");
        hqlQuery.append(" AND bpda.distributorDetails.distributorId=" + distributorId);
        hqlQuery.append(" AND bpda.beatPlanDetails.beatPlanId=" + beatPlanId);
        bpdaList = getHibernateTemplate().find(hqlQuery.toString());

        if (bpdaList != null && bpdaList.size() > 0) {
          beatPlanDistributorAssignments = bpdaList.get(0);
        }
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method addRunnerBeatPlan");
      log.error(e);
      e.printStackTrace();
    }
    return beatPlanDistributorAssignments;
  }

  // ========================================================================

  @Override
  public int getDistributorVisitFrequency(Long beatPlanId, Long distributorId) throws Exception {
    log.info("START of the method getDistributorVisitFrequency");
    BeatPlanDistributorAssignments bpda = null;
    int visitFreq = 0;

    try {
      bpda = getBeatPlanDistributorAssignment(beatPlanId, distributorId);

      if (bpda != null) {
        visitFreq = Integer.parseInt(bpda.getVisitFrequency());
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method getDistributorVisitFrequency");
      log.error(e);
      e.printStackTrace();
    }
    log.info("END of the method getDistributorVisitFrequency");
    return visitFreq;
  }

  // ========================================================================

  @SuppressWarnings("unchecked")
  @Override
  public String deleteRunnerBeatPlan(Long beatPlanId) throws Exception {
    log.info("START of the method deleteRunnerBeatPlan");
    StringBuffer hqlQuery = null;
    List<BeatPlanUserAssignments> bpuaList = null;
    String result = FAILURE;

    try {
      /*
       * select * from beat_plan_user_assignments as bpua where bpua.status=1
       * and bpua.bp_dist_assignment_id in ( select bpda.bp_dist_assignments_id
       * from beat_plan_distributor_assignments as bpda where bpda.status=1 and
       * bpda.beat_plan_id=1)
       */
      if (beatPlanId != null && beatPlanId.longValue() > 0) {
        hqlQuery = new StringBuffer(" SELECT bpua FROM BeatPlanUserAssignments as bpua WHERE bpua.status='" + ACTIVE + "' ");
        hqlQuery.append(" AND bpua.beatPlanDistributorAssignments.bpDistAssignmentsId in (");
        hqlQuery.append(" SELECT bpda.bpDistAssignmentsId FROM BeatPlanDistributorAssignments as bpda WHERE bpda.status='" + ACTIVE + "' ");
        hqlQuery.append(" AND bpda.beatPlanDetails.beatPlanId=" + beatPlanId + ")");
        bpuaList = getHibernateTemplate().find(hqlQuery.toString());

        if (bpuaList != null && bpuaList.size() > 0) {
          for (BeatPlanUserAssignments bpua : bpuaList) {
            bpua.setStatus(IN_ACTIVE);
            getHibernateTemplate().merge(bpua);
          }
          result = SUCCESS;
        }
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method deleteRunnerBeatPlan");
      log.error(e);
      e.printStackTrace();
    }
    log.info("END of the method deleteRunnerBeatPlan");
    return result;
  }

  // ========================================================================

  @SuppressWarnings("unchecked")
  @Override
  public List<RunnerBeatPlansDetails> getRunnerBeatPlans(Long userId, Long roleId) throws Exception {
    log.info("START of the method getRunnerBeatPlans");
    StringBuffer hqlQuery;
    List<UserToHubDetails> hubsOfUser = null;
    List<UserToHubDetails> runnersOfHub = null;
    StringBuffer hubIdsSB = new StringBuffer("");
    StringBuffer runnerIdsSB = new StringBuffer("");
    List<HubDetails> hubs = null;
    List<Object[]> beatPlans = null;
    List<RunnerBeatPlansDetails> runnerBeatPlans = null;

    try {
      if (userId != null && userId.longValue() > 0 && roleId != null && roleId.longValue() > 0) {
        if (roleId.longValue() == SYSTEM_ADMIN) {
          hqlQuery = new StringBuffer("SELECT hubDetails FROM HubDetails hubDetails WHERE ");
          hqlQuery.append(" hubDetails.status='" + ACTIVE + "'");
          hubs = getHibernateTemplate().find(hqlQuery.toString());

          if (hubs != null && hubs.size() > 0) {
            for (HubDetails hubDetails : hubs) {
              hubIdsSB.append(hubDetails.getHubId());
              hubIdsSB.append(",");
            }
          }
        } else {
          hqlQuery = new StringBuffer("SELECT userToHubDetails FROM UserToHubDetails userToHubDetails WHERE ");
          hqlQuery.append(" userToHubDetails.status='" + ACTIVE + "' AND ");
          hqlQuery.append(" userToHubDetails.userDetails.userId=" + userId);
          hubsOfUser = getHibernateTemplate().find(hqlQuery.toString());

          if (hubsOfUser != null && hubsOfUser.size() > 0) {
            for (UserToHubDetails userToHubDetails : hubsOfUser) {
              hubIdsSB.append(userToHubDetails.getHubDetails().getHubId());
              hubIdsSB.append(",");
            }
          }
        }

        if (hubIdsSB.length() > 1) {
          hqlQuery = new StringBuffer("SELECT userToHubDetails FROM UserToHubDetails userToHubDetails WHERE ");
          hqlQuery.append(" userToHubDetails.status='" + ACTIVE + "' AND ");
          hqlQuery.append(" userToHubDetails.userDetails.roleDetails.roleId=" + RUNNER + " AND ");
          hqlQuery.append(" userToHubDetails.hubDetails.hubId in (" + StringUtil.removeTrailingValue(hubIdsSB, ",") + ")");
          runnersOfHub = getHibernateTemplate().find(hqlQuery.toString());

          if (runnersOfHub != null && runnersOfHub.size() > 0) {
            for (UserToHubDetails userToHubDetails : runnersOfHub) {
              runnerIdsSB.append(userToHubDetails.getUserDetails().getUserId());
              runnerIdsSB.append(",");
            }
          }
        }
        if (runnerIdsSB.length() > 1) {
          /*
           * select * from beat_plan_details as bpd,
           * beat_plan_distributor_assignments as bpda where bpd.status=1 and
           * bpda.beat_plan_id = bpd.beat_plan_id and
           * bpda.bp_dist_assignments_id in (select bpua.bp_dist_assignment_id
           * from beat_plan_user_assignments as bpua where bpua.status=1 and
           * bpua.user_id in (33,34,35,48,52)) group by bpd.beat_plan_id;
           */
          hqlQuery = new StringBuffer(
              "SELECT bpd.beatPlanId, bpd.beatPlanName, bpd.beatPlanCode, bpda.distributorDetails.distributorId FROM BeatPlanDetails as bpd, BeatPlanDistributorAssignments as  bpda");
          hqlQuery.append(" where bpd.status='" + ACTIVE + "' and bpda.beatPlanDetails.beatPlanId = bpd.beatPlanId");
          hqlQuery.append(" and bpda.bpDistAssignmentsId in (SELECT bpua.beatPlanDistributorAssignments.bpDistAssignmentsId FROM");
          hqlQuery.append(" BeatPlanUserAssignments as bpua where bpua.status='" + ACTIVE + "'");
          hqlQuery.append(" and bpua.userDetails.userId in (" + StringUtil.removeTrailingValue(runnerIdsSB, ",") + "))");
          hqlQuery.append(" group by bpd.beatPlanId");
          beatPlans = getHibernateTemplate().find(hqlQuery.toString());

          if (beatPlans != null && beatPlans.size() > 0) {
            runnerBeatPlans = new ArrayList<RunnerBeatPlansDetails>();
            for (int i = 0; i < beatPlans.size(); i++) {
              Object[] results = (Object[]) beatPlans.get(i);
              RunnerBeatPlansDetails runnerBeatPlansDetails = new RunnerBeatPlansDetails();
              runnerBeatPlansDetails.setBeatPlanId(Long.valueOf(results[0].toString()));
              runnerBeatPlansDetails.setBeatPlanName(results[1].toString());
              runnerBeatPlansDetails.setBeatPlanCode(results[2].toString());
              HubDetails hubDetails = getHubOfDistributor(Long.valueOf(results[3].toString()));

              if (hubDetails != null) {
                runnerBeatPlansDetails.setHubName(hubDetails.getHubName());
                runnerBeatPlansDetails.setZoneName(hubDetails.getZoneDetails().getZoneName());
                runnerBeatPlansDetails.setRegionName(hubDetails.getZoneDetails().getRegionDetails().getRegionName());
                runnerBeatPlansDetails.setCircleName(hubDetails.getZoneDetails().getRegionDetails().getCircleDetails().getCircleName());
              }
              runnerBeatPlans.add(runnerBeatPlansDetails);
            }
          }
        }
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method getRunnerBeatPlans");
      log.error(e);
      e.printStackTrace();
    }
    log.info("END of the method getRunnerBeatPlans");
    return runnerBeatPlans;
  }

  // ========================================================================

  @SuppressWarnings("unchecked")
  @Override
  public int[] getVisitNumbers(Long distributorId) throws Exception {
    log.info("START of the method getVisitNumbers");
    int[] visitNumbers = null;
    StringBuffer hqlQuery;
    List<BeatPlanDistributorAssignments> bpdaList = null;
    BeatPlanDistributorAssignments distributorAssignments = null;
    List<BeatPlanUserAssignments> bpuaList = null;
    int visitFreq = 0;

    try {
      if (distributorId != null && distributorId.longValue() > 0) {
        hqlQuery = new StringBuffer("SELECT bpda FROM BeatPlanDistributorAssignments bpda where bpda.status='" + ACTIVE + "'");
        hqlQuery.append(" AND bpda.distributorDetails.distributorId=" + distributorId);
        hqlQuery.append(" AND bpda.distributorDetails.status='" + ACTIVE + "'");
        bpdaList = getHibernateTemplate().find(hqlQuery.toString());

        if (bpdaList != null && bpdaList.size() > 0) {
          distributorAssignments = bpdaList.get(0);

          if (distributorAssignments != null) {
            visitFreq = Integer.parseInt(distributorAssignments.getVisitFrequency());
            hqlQuery = new StringBuffer("SELECT bpua FROM BeatPlanUserAssignments bpua where bpua.status='" + ACTIVE + "'");
            hqlQuery.append(" AND bpua.beatPlanDistributorAssignments.bpDistAssignmentsId=" + distributorAssignments.getBpDistAssignmentsId());
            bpuaList = getHibernateTemplate().find(hqlQuery.toString());

            if (bpuaList != null && bpuaList.size() > 0) {
              if (bpuaList.size() < visitFreq) {
                int visitNo = 1;
                visitNumbers = new int[visitFreq];
                for(int i = 0; i < visitFreq; i ++) {
                  visitNumbers[i] = visitNo++;  
                }
                
                for(BeatPlanUserAssignments bpua : bpuaList) {
                  for(int i = 0; i < visitFreq; i ++) {
                    if(Integer.valueOf(bpua.getVisitNo()) == visitNumbers[i]) {
                      visitNumbers[i] = 0;
                      break;
                    }
                  }
                }
              }
            } else if(bpuaList.size() == visitFreq) { 
              
            } else {
              int visitNo = 1;
              visitNumbers = new int[visitFreq];
              for (int i = 0; i < visitNumbers.length; i++) {
                visitNumbers[i] = visitNo++;
              }
            }
          }
        }
      }
    } catch (Exception e) {
      log.error("PROBLEM in the method getVisitNumbers");
      log.error(e);
      e.printStackTrace();
    }
    log.info("END of the method getVisitNumbers");
    return visitNumbers;
  }

  // ========================================================================
  
  @SuppressWarnings("unchecked")
  @Override
  public boolean isRunnerBeatPlanAssigned(Long bpDistAssignmentsId) throws Exception {
    log.info("START of the method isRunnerBeatPlanAssigned");
    boolean isRunnerBeatPlanAssigned = false;
    StringBuffer hqlQuery;
    List<BeatPlanUserAssignments> bpuaList = null;
    
    try {
      if (bpDistAssignmentsId != null && bpDistAssignmentsId.longValue() > 0) {
        hqlQuery = new StringBuffer("SELECT bpua FROM BeatPlanUserAssignments bpua where bpua.status='" + ACTIVE + "'");
        hqlQuery.append(" AND bpua.beatPlanDistributorAssignments.bpDistAssignmentsId=" + bpDistAssignmentsId);
        bpuaList = getHibernateTemplate().find(hqlQuery.toString());

        if (bpuaList != null && bpuaList.size() > 0) {
          isRunnerBeatPlanAssigned = true;  
        }
      }
	} catch (Exception e) {
      log.error("PROBLEM in the method isRunnerBeatPlanAssigned");
      log.error(e);
      e.printStackTrace();
    }
    log.info("END of the method isRunnerBeatPlanAssigned");
    return isRunnerBeatPlanAssigned;
  }

  // ========================================================================

}