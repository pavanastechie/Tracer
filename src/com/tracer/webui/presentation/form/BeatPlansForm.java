/**
 * @author Jp
 *
 */
package com.tracer.webui.presentation.form;

import java.util.List;

import com.tracer.dao.model.BeatPlanDetails;
import com.tracer.dao.model.CircleDetails;
import com.tracer.dao.model.DistributorDetails;
import com.tracer.dao.model.HubDetails;
import com.tracer.dao.model.RegionDetails;
import com.tracer.dao.model.UserDetails;
import com.tracer.dao.model.ZoneDetails;

public class BeatPlansForm extends BaseForm {
	private static final long serialVersionUID = -2099944339677576811L;

	private Long circleId;
	private Long regionId;
	private Long zoneId;
	private Long hubId;
	private Long distributorId;
	private String visitFrequency;
	private String beatPlanName;
	private Long beatPlanId;
	private Long runnersId;
	private Long distributorsId;;
	private String runnersName;
	private String schedule;
	private String scheduleStartTime;
	private String scheduleEndTime;
	private String beatPlanData;
	private String runnerBeatPlanData;
	private String distributorsName;
	private String hubName;
	private String circleName;
	private String regionName;
	private String zoneName;
	private List<CircleDetails> circleDetailsList;
	private List<RegionDetails> regionDetailsList;
	private List<ZoneDetails> zoneDetailsList;
	private List<HubDetails> hubDetailsList;
	private List<BeatPlanDetails> beatPlanDetailsList;
	private List<UserDetails> runnerDetailsList;
	private List<DistributorDetails> distributorDetailsList;
	private Long visitNo;

	public String getHubName() {
		return hubName;
	}

	public void setHubName(String hubName) {
		this.hubName = hubName;
	}

	public String getCircleName() {
		return circleName;
	}

	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public Long getRunnersId() {
		return runnersId;
	}

	public void setRunnersId(Long runnersId) {
		this.runnersId = runnersId;
	}

	public Long getDistributorsId() {
		return distributorsId;
	}

	public void setDistributorsId(Long distributorsId) {
		this.distributorsId = distributorsId;
	}

	public Long getBeatPlanId() {
		return beatPlanId;
	}

	public void setBeatPlanId(Long beatPlanId) {
		this.beatPlanId = beatPlanId;
	}

	public Long getVisitNo() {
		return visitNo;
	}

	public void setVisitNo(Long visitNo) {
		this.visitNo = visitNo;
	}

	public String getScheduleStartTime() {
		return scheduleStartTime;
	}

	public void setScheduleStartTime(String scheduleStartTime) {
		this.scheduleStartTime = scheduleStartTime;
	}

	public String getScheduleEndTime() {
		return scheduleEndTime;
	}

	public void setScheduleEndTime(String scheduleEndTime) {
		this.scheduleEndTime = scheduleEndTime;
	}

	public List<DistributorDetails> getDistributorDetailsList() {
		return distributorDetailsList;
	}

	public void setDistributorDetailsList(List<DistributorDetails> distributorDetailsList) {
		this.distributorDetailsList = distributorDetailsList;
	}

	public List<UserDetails> getRunnerDetailsList() {
		return runnerDetailsList;
	}

	public void setRunnerDetailsList(List<UserDetails> runnerDetailsList) {
		this.runnerDetailsList = runnerDetailsList;
	}

	public Long getCircleId() {
		return circleId;
	}

	public void setCircleId(Long circleId) {
		this.circleId = circleId;
	}

	public Long getRegionId() {
		return regionId;
	}

	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}

	public Long getZoneId() {
		return zoneId;
	}

	public void setZoneId(Long zoneId) {
		this.zoneId = zoneId;
	}

	public Long getHubId() {
		return hubId;
	}

	public void setHubId(Long hubId) {
		this.hubId = hubId;
	}

	public List<CircleDetails> getCircleDetailsList() {
		return circleDetailsList;
	}

	public void setCircleDetailsList(List<CircleDetails> circleDetailsList) {
		this.circleDetailsList = circleDetailsList;
	}

	public List<RegionDetails> getRegionDetailsList() {
		return regionDetailsList;
	}

	public void setRegionDetailsList(List<RegionDetails> regionDetailsList) {
		this.regionDetailsList = regionDetailsList;
	}

	public List<ZoneDetails> getZoneDetailsList() {
		return zoneDetailsList;
	}

	public void setZoneDetailsList(List<ZoneDetails> zoneDetailsList) {
		this.zoneDetailsList = zoneDetailsList;
	}

	public List<HubDetails> getHubDetailsList() {
		return hubDetailsList;
	}

	public void setHubDetailsList(List<HubDetails> hubDetailsList) {
		this.hubDetailsList = hubDetailsList;
	}

	public String getBeatPlanName() {
		return beatPlanName;
	}

	public void setBeatPlanName(String beatPlanName) {
		this.beatPlanName = beatPlanName;
	}

	public String getVisitFrequency() {
		return visitFrequency;
	}

	public void setVisitFrequency(String visitFrequency) {
		this.visitFrequency = visitFrequency;
	}

	public Long getDistributorId() {
		return distributorId;
	}

	public void setDistributorId(Long distributorId) {
		this.distributorId = distributorId;
	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public String getBeatPlanData() {
		return beatPlanData;
	}

	public void setBeatPlanData(String beatPlanData) {
		this.beatPlanData = beatPlanData;
	}

	public String getRunnerBeatPlanData() {
		return runnerBeatPlanData;
	}

	public void setRunnerBeatPlanData(String runnerBeatPlanData) {
		this.runnerBeatPlanData = runnerBeatPlanData;
	}

	public String getRunnersName() {
		return runnersName;
	}

	public void setRunnersName(String runnersName) {
		this.runnersName = runnersName;
	}

	public String getDistributorsName() {
		return distributorsName;
	}

	public void setDistributorsName(String distributorsName) {
		this.distributorsName = distributorsName;
	}

	public List<BeatPlanDetails> getBeatPlanDetailsList() {
		return beatPlanDetailsList;
	}

	public void setBeatPlanDetailsList(List<BeatPlanDetails> beatPlanDetailsList) {
		this.beatPlanDetailsList = beatPlanDetailsList;
	}

}
