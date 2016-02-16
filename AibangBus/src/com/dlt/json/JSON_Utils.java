package com.dlt.json;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.dlt.http.AibangBus;
import com.dlt.http.HTTP_Utils;
import com.dlt.pojo.BusSegment;
import com.dlt.pojo.BusTransfer;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JSON_Utils {
	
	public static JSONObject createJsonObj(String jsonStr){
		JSONObject jsonObj = JSONObject.fromObject(jsonStr);
		return jsonObj;
	}
	
	/*
	 * 深搜遍历jsonobj 并将查询到的结果设置到map里
	 */
	
	public static void dfsJson(Map<String, String> map, JSONObject jsonObj){
		@SuppressWarnings("unchecked")
		Iterator<String> iterator = jsonObj.keys();
		while (iterator.hasNext()) {
			String key = iterator.next();
			Object object = jsonObj.get(key);
			if(object instanceof JSONObject){
				dfsJson(map, (JSONObject)object);
			}
			else if(object instanceof JSONArray){
				JSONArray jsonArray = (JSONArray)object;
				
				for(int i = 0; i < jsonArray.size(); i++){
					Object obj = jsonArray.get(i);
					if(obj instanceof String){
			//			System.out.println(key+": "+(String)obj);
						if(map.get(key) == null){
							map.put(key, obj.toString());
						}
						else {
							map.put(key, map.get(key)+","+obj.toString());
						}
					}
					else if(obj instanceof JSONObject){
						dfsJson(map, (JSONObject)obj);
					}
					else{
						if(map.get(key) == null){
							map.put(key, obj.toString());
						}
						else {
							map.put(key, map.get(key)+","+obj.toString());
						}
			//			System.out.println(obj.toString());
					}
				}
			}
			else{
			//	System.out.println(key+": "+object.toString());
				if(map.get(key) == null){
					map.put(key, object.toString());
				}
				else {
					map.put(key, map.get(key)+","+object.toString());
				}
			}
		}
	}
	public static void main(String[] args) {
		HTTP_Utils utils = new HTTP_Utils();
		String json = null;
		try {
			json = utils.getStringFromInputstream(new FileInputStream(new File("test.json")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject jsonObj = createJsonObj(json);
		if (jsonObj == null) {
			System.out.println("null");
		}
		else {
			System.out.println(jsonObj.getString("message"));
		}
	}
}
