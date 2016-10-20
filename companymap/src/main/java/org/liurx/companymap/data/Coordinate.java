package org.liurx.companymap.data;

/**
 * 坐标
 * @class Coordinate
 * @date 2016年10月19日 下午7:36:57
 * @author liurx
 * @since 4.0.0
 */
public class Coordinate {
	String longitude;
	String latitude;
	
	public Coordinate(String longitude, String latitude) {
		super();
		this.longitude = longitude;
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	@Override
	public String toString() {
		return "Coordinate [longitude=" + longitude + ", latitude=" + latitude + "]";
	}
}
