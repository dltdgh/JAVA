package com.dlt.pojo;

import java.util.List;

public class BusTransferPlan {
	public Integer id = null;
	public String desc = null;
	public Integer stat_num = null;
	public Integer time_cost = null;
	public Integer total_dis = null;
	public Integer walk_dis = null;
	
	public BusTransferPlan(Integer id, String desc, Integer stat_num,
			Integer time_cost, Integer total_dis, Integer walk_dis) {
		super();
		this.id = id;
		this.desc = desc;
		this.stat_num = stat_num;
		this.time_cost = time_cost;
		this.total_dis = total_dis;
		this.walk_dis = walk_dis;
	}
	
	public BusTransferPlan(Integer id, BusTransfer busTransfer){
		this.id = id+1;
		this.time_cost = busTransfer.getTime();
		this.total_dis = busTransfer.getDist();
		this.walk_dis = busTransfer.getFoot_dist();
		List<BusSegment> list = busTransfer.getBusSegments();
		
		this.stat_num = 0;
		this.desc = "";	
		
		for (BusSegment segment : list) {
			this.stat_num += (segment.getStats().split(";").length);
			this.desc += "在 "+segment.getStart_stat()+" 上车\n乘坐 "+segment.getLine_name()+"\n在 "+segment.getEnd_stat()+" 下车\n";
		}
		
		this.desc = this.desc.substring(0, this.desc.length()-1);
		System.out.println(this.desc);
	}
}
