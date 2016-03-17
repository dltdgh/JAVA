package com.dlt.pojo;

public class Coordinates {
	Double lat = null;
	Double lng = null;
	public Coordinates(Double lat, Double lng) {
		super();
		this.lat = lat;
		this.lng = lng;
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
	@Override
	public String toString() {
		return "Coordinates [lat=" + lat + ", lng=" + lng + "]";
	}
	
}
