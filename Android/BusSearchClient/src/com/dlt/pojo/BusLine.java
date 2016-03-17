package com.dlt.pojo;

import java.io.Serializable;
import java.util.Map;

public class BusLine implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3175960964328872716L;
	private String name = null;
	private String info = null;
	private String stats = null;
	private String stat_xys = null;
	private String xys = null;
	
	public BusLine() {}
	
	public BusLine(Map<String, Object> map){
		this.name = map.get("name").toString();
		this.info = map.get("info").toString();
		this.stats = map.get("stats").toString();
		this.stat_xys = map.get("stat_xys").toString();
		this.xys = map.get("xys").toString();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getStats() {
		return stats;
	}
	public void setStats(String stats) {
		this.stats = stats;
	}
	public String getStat_xys() {
		return stat_xys;
	}
	public void setStat_xys(String stat_xys) {
		this.stat_xys = stat_xys;
	}
	public String getXys() {
		return xys;
	}
	public void setXys(String xys) {
		this.xys = xys;
	}

	@Override
	public String toString() {
		return "BusLine [name=" + name + ", info=" + info + ", stats=" + stats
				+ ", stat_xys=" + stat_xys + ", xys=" + xys + "]";
	}
	
	
}
