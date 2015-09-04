package com.tracer.webui.presentation.model;

public class RegionHelper {

	private String regionCode;
	private String regionName;
	private String circleName;

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public RegionHelper(String regionName, String circleName) {
		this.regionName = regionName;
		this.circleName = circleName;

	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getCircleName() {
		return circleName;
	}

	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}

}
