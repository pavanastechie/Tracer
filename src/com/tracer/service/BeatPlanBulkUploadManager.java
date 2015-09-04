/**
 * @author Jp
 *
 */
package com.tracer.service;

import com.tracer.dao.model.BeatPlanCircleDetails;
import com.tracer.dao.model.BeatPlanHubDetails;
import com.tracer.dao.model.CircleDetails;
import com.tracer.dao.model.DistributorDetails;
import com.tracer.dao.model.HubDetails;
import com.tracer.dao.model.RegionDetails;
import com.tracer.dao.model.UserDetails;
import com.tracer.dao.model.ZoneDetails;

public interface BeatPlanBulkUploadManager {
  public CircleDetails isCircleExists(String circleName);

  public CircleDetails createCircle(CircleDetails circleDetails);

  public RegionDetails isRegionExists(String regionName);

  public RegionDetails createRegion(RegionDetails regionDetails);

  public ZoneDetails isZoneExists(String zoneName);

  public ZoneDetails createZone(ZoneDetails zoneDetails);

  public HubDetails isHubExists(String hubName);

  public HubDetails createHub(HubDetails hubDetails);

  public DistributorDetails isDistributorExists(String distributorName);

  public DistributorDetails createDistributor(DistributorDetails distributorDetails);

  public UserDetails isUserExists(String userName);

  public UserDetails createUser(UserDetails userDetails);

  public String createBeatPlan(BeatPlanCircleDetails beatPlanCircleDetails);

}
