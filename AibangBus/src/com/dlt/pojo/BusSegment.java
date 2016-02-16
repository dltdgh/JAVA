package com.dlt.pojo;

import java.util.Map;

public class BusSegment {
	private String start_stat = null;
	private String end_stat = null;
	private String line_name = null;
	private String stats = null;
	private Integer line_dist = null;
	private Integer foot_dist = null;
	private String stat_xys = null;
	private String line_xys = null;
	
	public BusSegment(){}
	
	public BusSegment(Map<String, String> map) {
		this.start_stat = map.get("start_stat");
		this.end_stat = map.get("end_stat");
		this.line_name = map.get("line_name");
		this.stats = map.get("stats");
		this.line_dist = Integer.parseInt(map.get("line_dist"));
		this.foot_dist = Integer.parseInt(map.get("foot_dist"));
		this.stat_xys = map.get("stat_xys");
		this.line_xys = map.get("line_xys");
	}
	
	public String getStart_stat() {
		return start_stat;
	}
	public void setStart_stat(String start_stat) {
		this.start_stat = start_stat;
	}
	public String getEnd_stat() {
		return end_stat;
	}
	public void setEnd_stat(String end_stat) {
		this.end_stat = end_stat;
	}
	public String getLine_name() {
		return line_name;
	}
	public void setLine_name(String line_name) {
		this.line_name = line_name;
	}
	public String getStats() {
		return stats;
	}
	public void setStats(String stats) {
		this.stats = stats;
	}
	public Integer getLine_dist() {
		return line_dist;
	}
	public void setLine_dist(Integer line_dist) {
		this.line_dist = line_dist;
	}
	public Integer getFoot_dist() {
		return foot_dist;
	}
	public void setFoot_dist(Integer foot_dist) {
		this.foot_dist = foot_dist;
	}
	public String getStat_xys() {
		return stat_xys;
	}
	public void setStat_xys(String stat_xys) {
		this.stat_xys = stat_xys;
	}
	public String getLine_xys() {
		return line_xys;
	}
	public void setLine_xys(String line_xys) {
		this.line_xys = line_xys;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.start_stat+" "+this.end_stat+" "+this.line_name+" "+this.stats+" "+this.stat_xys+" "+this.line_xys;
	}
}
