package com.dlt.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;

import com.dlt.utils.AibangBus;
import com.dlt.utils.HttpService;
import com.dlt.utils.JsonService;

import android.test.AndroidTestCase;
import android.util.Log;

public class UnitTest extends AndroidTestCase {
	
	public static final String TAG = "UnitTest";
	
	public void testJson(){
		String str = "{\"上海\":[\"浦东\"],\"四川\":[\"成都\",\"攀枝花\"],\"福建\":[\"福州\",\"厦门\",\"泉州\"]}";
		Map<String, Object> map = new HashMap<String, Object>();
		JsonService.dfsJsonObj(map, JsonService.createJSONObj(str));
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			String key = entry.getKey();
			Log.d(TAG, key+": ");
			Object obj = entry.getValue();
			if (obj instanceof JSONArray) {
				JSONArray array = (JSONArray)obj;
				List<Object> list = JsonService.parseJsonArray(array);
				for (int i = 0; i < list.size(); i++) {
					Log.d(TAG, list.get(i).toString());
				}
			}
		}
	}
	
	public void testGetContentFromURI(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				AibangBus.queryBusLines("青岛", "227", null);
			}
		}).start();
	}
}
