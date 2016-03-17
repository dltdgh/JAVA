package com.dlt.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.dlt.pojo.BusLine;
import com.dlt.pojo.BusSegment;
import com.dlt.pojo.BusStat;
import com.dlt.pojo.BusStats_XY;
import com.dlt.pojo.BusTransfer;
import com.dlt.pojo.Coordinates;

public class AibangBus {
	
	private static final String API_KEY = "8f2d8f22e79cffc5dea50a225eedc6cb";
	private static final String ABBUS_LOCATE_URL = "http://openapi.aibang.com/locate";
	private static final String ABBUS_LINE_URL = "http://openapi.aibang.com/bus/lines";
	private static final String ABBUS_STATS_URL = "http://openapi.aibang.com/bus/stats";
	private static final String ABBUS_STATSXY_URL = "http://openapi.aibang.com/bus/stats_xy";
	private static final String ABBUS_TRANSFER_URL = "http://openapi.aibang.com/bus/transfer";
	
	public Map<String, Object> excuteQuery(String uri, Map<String, String> params){
		String content = HttpService.getContentFromURLByGet(uri, params);
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject jsonObj = JsonService.createJSONObj(content);
		JsonService.dfsJsonObj(map, jsonObj);
		return map;
	}
		
	public static Coordinates queryCoordinates(String city, String address){
		Coordinates coordinates = null;
		Map<String, String> map = new HashMap<String, String>();
		map.put("app_key", API_KEY);
		map.put("alt", "json");
		if(city != null){
			map.put("city", city);
		}
		if(address != null){
			map.put("addr", address);
		}
		String content = HttpService.getContentFromURLByGet(ABBUS_LOCATE_URL, map);
		if (content.equals("")) {         //判断
			return coordinates;
		}
		System.out.println(content);
		coordinates = new Coordinates(null, null);
		try {
			JSONObject jsonObj = JsonService.createJSONObj(content);
			coordinates.setLat(jsonObj.getDouble("lat"));
			coordinates.setLng(jsonObj.getDouble("lng"));
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return coordinates;
	}
	
	/**
	 * 查询城市公交线路
	 * @param city
	 * @param q
	 * @param withxys
	 * @return
	 */
	public static List<BusLine> queryBusLines(String city, String q, String with_xys){
		List<BusLine> list = new ArrayList<BusLine>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("app_key", API_KEY);
		map.put("alt", "json");
	//	map.put("with_xys", "1");
		if(city != null){
			map.put("city", city);
		}
		if(q != null){
			map.put("q", q);
		}
		if(with_xys != null){
			map.put("with_xys", with_xys);
		}
		String content = HttpService.getContentFromURLByGet(ABBUS_LINE_URL, map);
//		System.out.println(content);
		if (content.equals("")) {         //判断
			return list;
		}
		try {
			JSONObject jsonObj = JsonService.createJSONObj(content);
			
			int result_num = jsonObj.getInt("result_num");
			System.out.println(result_num);
			JSONArray jsonArray = jsonObj.getJSONObject("lines").getJSONArray("line");
			for (int i = 0; i < result_num; i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
			//	System.out.println(obj.toString());
				Map<String, Object> tMap = new HashMap<String, Object>();
				JsonService.dfsJsonObj(tMap, obj);
			//	System.out.println(tMap.get("stats"));
				BusLine busLine = new BusLine(tMap);
				System.out.println(busLine.toString());
				list.add(busLine);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 查询地点周边公交站
	 * @param city 城市
	 * @param q 地点地址
	 * @return 返回公交站信息列表
	 */
	
	public static List<BusStat> queryBusStats(String city, String q) {
		List<BusStat> list = new ArrayList<BusStat>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("app_key", API_KEY);
		map.put("alt", "json");
		if(city != null){
			map.put("city", city);
		}
		if(q != null){
			map.put("q", q);
		}
		String content = HttpService.getContentFromURLByGet(ABBUS_STATS_URL, map);
		if (content.equals("")) {       //读取失败直接返回
			return list;
		}
		
		try {
			JSONObject jsonObj = JsonService.createJSONObj(content);
			JSONArray jsonArray = jsonObj.getJSONObject("stats").getJSONArray("stat");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				Map<String, Object> tMap = new HashMap<String, Object>();
				JsonService.dfsJsonObj(tMap, obj);
				BusStat busStat = new BusStat(tMap);
				System.out.println(busStat.toString());
				list.add(busStat);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}	
		return list;
	}
	
	/**
	 * 获取给定坐标周边公交站信息
	 * @param city 城市
	 * @param lng 经度
	 * @param lat 维度
	 * @param dist 距离
	 * @return 站点信息对象list
	 */
	
	public static List<BusStats_XY> queryBusStats_XY(String city, Double lng, Double lat, Double dist) {
		List<BusStats_XY> list = new ArrayList<BusStats_XY>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("app_key", API_KEY);
		map.put("alt", "json");
		if(city != null){
			map.put("city", city);
		}
		if(lng != null){
			map.put("lng", lng.toString());
		}
		if(lat != null){
			map.put("lat", lat.toString());
		}
		if(dist != null){
			map.put("dist", dist.toString());
		}
		
		String content = HttpService.getContentFromURLByGet(ABBUS_STATSXY_URL, map);
		
		if (content.equals("")) {
			return list;
		}
		
		try {
			JSONObject jsonObj = JsonService.createJSONObj(content);
			JSONArray jsonArray = jsonObj.getJSONObject("stats").getJSONArray("stat");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				Map<String, Object> tMap = new HashMap<String, Object>();
				JsonService.dfsJsonObj(tMap, obj);
				BusStats_XY busStats_XY = new BusStats_XY(tMap);
				System.out.println(busStats_XY.toString());
				list.add(busStats_XY);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return list;
	}
	
	/**
	 * 
	 * @param city 城市
	 * @param start_addr 起点地址 如：五道口，与start_lng和start_lat不能都为空
	 * @param end_addr 终点地址 如：东直门，与end_lng和end_lat不能都为空
	 * @param start_lng 起点经度
	 * @param start_lat 起点纬度
	 * @param end_lng 终点经度
	 * @param end_lat 终点纬度
	 * @param rc 排序  默认是0，定义如下： 0：综合排序 1：换乘次数 2：步行距离 3：时间 4：距离 5：地铁优先
	 * @param count 最大返回记录数量 默认为10，最大值不能超过10
	 * @param with_xys 是否包含坐标信息 默认为0，不包含各个站点和路线的坐标信息，如果为1，则包含
	 * @return 公交换乘信息对象列表
	 */
	
	public static List<BusTransfer> queryBusTransfers(String city, String start_addr, String end_addr, Float start_lng, Float start_lat, Float end_lng, Float end_lat, Integer rc, Integer count, Integer with_xys) {
		List<BusTransfer> list = new ArrayList<BusTransfer>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("app_key", API_KEY);
		map.put("alt", "json");
		if(city != null){
			map.put("city", city);
		}
		if(start_addr != null){
			map.put("start_addr", start_addr);
		}
		if(end_addr != null){
			map.put("end_addr", end_addr);
		}
		if(start_lng != null){
			map.put("start_lng", start_lng.toString());
		}
		if(start_lat != null){
			map.put("start_lat", start_lat.toString());
		}
		if(end_lng != null){
			map.put("end_lng", end_lng.toString());
		}
		if(end_lat != null){
			map.put("end_lat", end_lat.toString());
		}
		if(rc != null){
			map.put("rc", rc.toString());
		}
		if(count != null){
			map.put("count", count.toString());
		}
		if (with_xys != null) {
			map.put("with_xys", with_xys.toString());
		}
		String content = HttpService.getContentFromURLByGet(ABBUS_TRANSFER_URL, map);
		
		if (content.equals("")) {
			return list;
		}		
		
		try {
			JSONObject jsonObj = JsonService.createJSONObj(content);
			JSONArray jsonArray = jsonObj.getJSONObject("buses").getJSONArray("bus");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				BusTransfer busTransfer = new BusTransfer();
				
				//第一次初始化
				busTransfer.setDist(obj.getInt("dist"));
				busTransfer.setTime(obj.getInt("time"));
				busTransfer.setFoot_dist(obj.getInt("foot_dist"));
				busTransfer.setLast_foot_dist(obj.getInt("last_foot_dist"));
				
				JSONArray segmentArray = obj.getJSONObject("segments").getJSONArray("segment");
				List<BusSegment> segments = new ArrayList<BusSegment>();
				
				for (int j = 0; j < segmentArray.length(); j++) {
					JSONObject segment = segmentArray.getJSONObject(j);
					Map<String, Object> tMap = new HashMap<String, Object>();
					JsonService.dfsJsonObj(tMap, segment);
					BusSegment busSegment = new BusSegment(tMap);
					segments.add(busSegment);
				}
				//第二次初始化
				busTransfer.setBusSegments(segments);
				System.out.println(busTransfer.toString());
				list.add(busTransfer);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}	
		return list;
	}
	
}
