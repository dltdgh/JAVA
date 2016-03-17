package com.dlt.pojo;

import java.io.Serializable;
import java.util.List;

public class BusTransfer implements Serializable{
	
	/**
	 * 设置为可序列化对象
	 */
	private static final long serialVersionUID = 6403857977231547142L;
	private Integer dist = null;
	private Integer time = null;
	private Integer foot_dist = null;
	private Integer last_foot_dist = null;
	private List<BusSegment> busSegments = null;
	
	public BusTransfer(){}
	
	public Integer getDist() {
		return dist;
	}
	public void setDist(Integer dist) {
		this.dist = dist;
	}
	public Integer getTime() {
		return time;
	}
	public void setTime(Integer time) {
		this.time = time;
	}
	public Integer getLast_foot_dist() {
		return last_foot_dist;
	}
	public void setLast_foot_dist(Integer last_foot_dist) {
		this.last_foot_dist = last_foot_dist;
	}
	
	
	public Integer getFoot_dist() {
		return foot_dist;
	}

	public void setFoot_dist(Integer foot_dist) {
		this.foot_dist = foot_dist;
	}

	public List<BusSegment> getBusSegments() {
		return busSegments;
	}

	public void setBusSegments(List<BusSegment> busSegments) {
		this.busSegments = busSegments;
	}

	@Override
	public String toString() {
		return "BusTransfer [dist=" + dist + ", time=" + time + ", foot_dist="
				+ foot_dist + ", last_foot_dist=" + last_foot_dist
				+ ", busSegments=" + busSegments.toString() + "]";
	}

	
}
