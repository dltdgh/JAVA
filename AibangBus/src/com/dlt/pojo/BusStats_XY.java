package com.dlt.pojo;

import java.util.Map;

public class BusStats_XY {
	private String name = null;
	private String xy = null;
	private Integer dist = null;
	private String line_names = null;
	
	public BusStats_XY(){}
	
	public BusStats_XY(Map<String, String> map) {
		this.name = map.get("name");
		this.xy = map.get("xy");
		this.dist = Integer.parseInt(map.get("dist"));
		this.line_names = map.get("line_names");
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getXy() {
		return xy;
	}
	public void setXy(String xy) {
		this.xy = xy;
	}
	public Integer getDist() {
		return dist;
	}
	public void setDist(Integer dist) {
		this.dist = dist;
	}
	public String getLine_names() {
		return line_names;
	}
	public void setLine_names(String line_names) {
		this.line_names = line_names;
	}
}
