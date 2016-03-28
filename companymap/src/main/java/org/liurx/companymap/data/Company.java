package org.liurx.companymap.data;

import java.util.List;

public class Company {
	private String id;
	private String shortName;
	private String name;
	private List<String> addressList;
	private List<Coordinate> coordinateList;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getAddressList() {
		return addressList;
	}
	public void setAddressList(List<String> addressList) {
		this.addressList = addressList;
	}
	public List<Coordinate> getCoordinateList() {
		return coordinateList;
	}
	public void setCoordinateList(List<Coordinate> coordinateList) {
		this.coordinateList = coordinateList;
	}
	@Override
	public String toString() {
		return "Company [id=" + id + ", shortName=" + shortName + ", name=" + name + ", addressList=" + addressList
				+ ", coordinateList=" + coordinateList + "]";
	}
}
