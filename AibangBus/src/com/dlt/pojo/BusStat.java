package com.dlt.pojo;

import java.util.Map;

public class BusStat {
	private String name = null;
	private String xy = null;
	private String line_names = null;
	
	
	public BusStat() {}
	
	public BusStat(Map<String, String> map){
		this.name = map.get("name");
		this.xy = map.get("xy");
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
	public String getLine_names() {
		return line_names;
	}
	public void setLine_names(String line_names) {
		this.line_names = line_names;
	}
	
}
