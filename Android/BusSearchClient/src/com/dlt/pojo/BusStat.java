package com.dlt.pojo;

import java.io.Serializable;
import java.util.Map;

public class BusStat implements Serializable{

	/**
	 * 设置为可序列化对象
	 */
	private static final long serialVersionUID = 5389060819455903805L;
	private String name = null;
	private String xy = null;
	private String line_names = null;
	
	
	public BusStat() {}
	
	public BusStat(Map<String, Object> map){
		this.name = map.get("name").toString();
		this.xy = map.get("xy").toString();
		this.line_names = map.get("line_names").toString();
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

	@Override
	public String toString() {
		return "BusStat [name=" + name + ", xy=" + xy + ", line_names="
				+ line_names + "]";
	}
	
}
