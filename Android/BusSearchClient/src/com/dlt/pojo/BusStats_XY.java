package com.dlt.pojo;

import java.io.Serializable;
import java.util.Map;

public class BusStats_XY implements Serializable{
	
	/**
	 * 设置为可序列化对象
	 */
	
	private static final long serialVersionUID = 2065639214058717917L;
	private String name = null;
	private String xy = null;
	private Integer dist = null;
	private String line_names = null;
	
	public BusStats_XY(){}
	
	public BusStats_XY(Map<String, Object> map) {
		this.name = map.get("name").toString();
		this.xy = map.get("xy").toString();
		this.dist = Integer.parseInt(map.get("dist").toString());
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

	@Override
	public String toString() {
		return "BusStats_XY [name=" + name + ", xy=" + xy + ", dist=" + dist
				+ ", line_names=" + line_names + "]";
	}
	
	
}
