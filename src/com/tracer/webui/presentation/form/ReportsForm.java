/**
 * @author Jp
 *
 */
package com.tracer.webui.presentation.form;

import java.util.List;

import com.tracer.dao.model.CircleDetails;
import com.tracer.dao.model.HubDetails;
import com.tracer.dao.model.RegionDetails;
import com.tracer.dao.model.ZoneDetails;
 	
public class ReportsForm extends BaseForm {
	private static final long serialVersionUID = -2099944339677576811L;
	private Long circleId;
	private Long regionId;
	private Long zoneId;
	private Long hubId;
	private String fromDate;
	private String toDate;
	private String reportName;
	private String authCode;
	private long roleId;
	private List<CircleDetails> circleDetailsList;
	private List<RegionDetails> regionDetailsList;
	private List<ZoneDetails> zoneDetailsList;
	private List<HubDetails> hubDetailsList;
	
	public String getAuthCode() {
		return authCode;
	}
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	public long getRoleId() {
		return roleId;
	}
	public void setRoleId(long roleId) {
		this.roleId = roleId;
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
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
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
	public Long getCircleId() {
		return circleId;
	}
	public void setCircleId(Long circleId) {
		this.circleId = circleId;
	}
	public List<CircleDetails> getCircleDetailsList() {
		return circleDetailsList;
	}
	public void setCircleDetailsList(List<CircleDetails> circleDetailsList) {
		this.circleDetailsList = circleDetailsList;
	}
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
}
