package com.dlt.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonService {
	
	/**
	 * ����json�ַ�������json����
	 * @param jsonStr
	 * @return
	 */
	
	public static JSONObject createJSONObj(String jsonStr){
		JSONObject object = null;
		try {
			object = new JSONObject(jsonStr);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return object;
	}
	
	/**
	 * ���ѱ���jsonobj ������ѯ���Ľ�����õ�map��
	 * @param map
	 * @param jsonObj
	 */
	
	public static void dfsJsonObj(Map<String, Object> map, JSONObject jsonObj){
		try {
			@SuppressWarnings("unchecked")
			Iterator<String> iterator = jsonObj.keys();
			while (iterator.hasNext()) {
				String key = iterator.next();	
				Object object = jsonObj.get(key);
				if (object instanceof JSONObject) {
					dfsJsonObj(map, (JSONObject)object);
				}
				else if (object instanceof JSONArray) {
					JSONArray array = (JSONArray)object;
					map.put(key, array);
				}
				else {
					map.put(key, object.toString());
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param array ������jsonarray
	 * @return �����ݷ���list(size=0)
	 */
	
	public static List<Object> parseJsonArray(JSONArray array) {
		List<Object> list = new ArrayList<Object>();
		try {
			for (int i = 0; i < array.length(); i++) {
				Object obj = array.get(i);
				if (obj instanceof String) {
					list.add(obj);
				}
				else if(obj instanceof JSONObject){
					list.add((JSONObject)obj);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}		
		return list;
	}
}
