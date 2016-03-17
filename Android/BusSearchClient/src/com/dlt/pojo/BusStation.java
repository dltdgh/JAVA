package com.dlt.pojo;

import java.io.Serializable;

public class BusStation implements Serializable{
	/**
	 * 设置为可序列化对象
	 */
	private static final long serialVersionUID = 6742596604739587369L;
	String stationName = null;
	Integer num = null;
	Double lat = null;
	Double lng = null;
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Double getLat() {
		return lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}
	public Double getLng() {
		return lng;
	}
	public void setLng(Double lng) {
		this.lng = lng;
	}
	public BusStation(String stationName, Integer num, Double lat, Double lng) {
		super();
		this.stationName = stationName;
		this.num = num;
		this.lat = lat;
		this.lng = lng;
	}
	
}
