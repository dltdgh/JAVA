package com.dlt.http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.dlt.json.JSON_Utils;
import com.dlt.pojo.BusLine;
import com.dlt.pojo.BusSegment;
import com.dlt.pojo.BusStat;
import com.dlt.pojo.BusStats_XY;
import com.dlt.pojo.BusTransfer;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AibangBus extends HTTP_Utils {
	
	private String API_KEY = "8f2d8f22e79cffc5dea50a225eedc6cb";
	private String ABBUS_LINE_URL = "http://openapi.aibang.com/bus/lines";
	private String ABBUS_STATS_URL = "http://openapi.aibang.com/bus/stats";
	private String ABBUS_STATSXY_URL = "http://openapi.aibang.com/bus/stats_xy";
	private String ABBUS_TRANSFER_URL = "http://openapi.aibang.com/bus/transfer";
	
	public Map<String, String> excuteQuery(String uri, Map<String, String> params){
		String content = getContentFromURI(uri, params);
		Map<String, String> map = new HashMap<String, String>();
		JSONObject jsonObj = JSON_Utils.createJsonObj(content);
		JSON_Utils.dfsJson(map, jsonObj);
		return map;
	}
	
	public List<BusLine> queryBusLines(String city, String q, String withxys){
		List<BusLine> list = new ArrayList<BusLine>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("app_key", API_KEY);
		map.put("alt", "json");
		if(city != null){
			map.put("city", city);
		}
		if(q != null){
			map.put("q", q);
		}
		if(withxys != null){
			map.put("withxys", withxys);
		}
		String content = getContentFromURI(ABBUS_LINE_URL, map);
		JSONObject jsonObj = JSON_Utils.createJsonObj(content);
		if(!jsonObj.containsKey("lines") || jsonObj.getInt("result_num") == 0){
			return list;
		}
		JSONArray jsonArray = jsonObj.getJSONObject("lines").getJSONArray("line");
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject obj = jsonArray.getJSONObject(i);
			Map<String, String> tMap = new HashMap<String, String>();
			JSON_Utils.dfsJson(tMap, obj);
			BusLine busLine = new BusLine(tMap);
			list.add(busLine);
		}
		return list;
	}
	
	public List<BusStat> queryBusStats(String city, String q) {
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
		String content = getContentFromURI(ABBUS_STATS_URL, map);
		JSONObject jsonObj = JSON_Utils.createJsonObj(content);
		if(!jsonObj.containsKey("stats")){
			return list;
		}
		JSONArray jsonArray = jsonObj.getJSONObject("stats").getJSONArray("stat");
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject obj = jsonArray.getJSONObject(i);
			Map<String, String> tMap = new HashMap<String, String>();
			JSON_Utils.dfsJson(tMap, obj);
			BusStat busStat = new BusStat(tMap);
			list.add(busStat);
		}
		return list;
	}
	
	public List<BusStats_XY> queryBusStats_XY(String city, Float lng, Float lat, Float dist) {
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
		String content = getContentFromURI(ABBUS_STATSXY_URL, map);
		JSONObject jsonObj = JSON_Utils.createJsonObj(content);
		if(!jsonObj.containsKey("stats")){
			return list;
		}
		JSONArray jsonArray = jsonObj.getJSONObject("stats").getJSONArray("stat");
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject obj = jsonArray.getJSONObject(i);
			Map<String, String> tMap = new HashMap<String, String>();
			JSON_Utils.dfsJson(tMap, obj);
			BusStats_XY busStats_XY = new BusStats_XY(tMap);
			list.add(busStats_XY);
		}
		return list;
	}
	
	public List<BusTransfer> queryBusTransfers(String city, String start_addr, String end_addr, Float start_lng, Float start_lat, Float end_lng, Float end_lat, Integer rc, Integer count, Integer with_xys) {
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
		String content = getContentFromURI(ABBUS_TRANSFER_URL, map);
		JSONObject jsonObj = JSON_Utils.createJsonObj(content);
		if(!jsonObj.containsKey("buses")){
			return list;
		}
		JSONArray jsonArray = jsonObj.getJSONObject("buses").getJSONArray("bus");
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject obj = jsonArray.getJSONObject(i);
			BusTransfer busTransfer = new BusTransfer();
			busTransfer.setDist(obj.getInt("dist"));
			busTransfer.setTime(obj.getInt("time"));
			busTransfer.setFoot_dist(obj.getInt("foot_dist"));
			busTransfer.setLast_foot_dist(obj.getInt("last_foot_dist"));
			JSONArray segmentArray = obj.getJSONObject("segments").getJSONArray("segment");
			List<BusSegment> segments = new ArrayList<BusSegment>();
			for (int j = 0; j < segmentArray.size(); j++) {
				JSONObject segment = segmentArray.getJSONObject(j);
				Map<String, String> tMap = new HashMap<String, String>();
				JSON_Utils.dfsJson(tMap, segment);
				BusSegment busSegment = new BusSegment(tMap);
				segments.add(busSegment);
			}
			busTransfer.setBusSegments(segments);
			System.out.println(busTransfer.toString());
			list.add(busTransfer);
		}
		return list;
	}
	
	public static void main(String[] args) {
		AibangBus aibangBus = new AibangBus();
		Scanner sc = new Scanner(System.in);
		String city = sc.next();
		
		/*String start_addr = sc.next();
		String end_addr = sc.next();*/	
		String q = sc.next();
		sc.close();
		
		List<BusLine> list = aibangBus.queryBusLines(city, q, null);
		for (int i = 0; i < list.size(); i++) {
			BusLine busLine = list.get(i);
			System.out.println(busLine.getName()+"\n"+busLine.getInfo()+"\n"+busLine.getStats());
		}
		
		/*List<BusStat> list = aibangBus.queryBusStats(city, q);
		for (int i = 0; i < list.size(); i++) {
			BusStat busStat = list.get(i);
			System.out.println(busStat.g;etName()+"\n"+busStat.getXy()+"\n"+busStat.getLine_names());
		}*/
		
		/*
		List<BusStats_XY> list = aibangBus.queryBusStats_XY(city, lng, lat, dist);
		for (int i = 0; i < list.size(); i++) {
			BusStats_XY busStats_XY = list.get(i);
			System.out.println(busStats_XY.getName()+"\n"+busStats_XY.getXy()+"\n"+busStats_XY.getDist()+"\n"+busStats_XY.getLine_names());
		}
		*/
		/*List<BusTransfer> list = aibangBus.queryBusTransfers(city, start_addr, end_addr, null, null, null, null, null, null, null);
		for (int i = 0; i < list.size(); i++) {
			BusTransfer busTransfer = list.get(i);
			System.out.println(busTransfer.toString());
		}*/
	}
}
