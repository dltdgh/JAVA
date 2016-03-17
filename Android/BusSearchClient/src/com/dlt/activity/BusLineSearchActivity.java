package com.dlt.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baidu.mapapi.search.core.CityInfo;
import com.dlt.BusSearchClient.R;
import com.dlt.pojo.BusLine;
import com.dlt.utils.AibangBus;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class BusLineSearchActivity extends Activity {
	
	private ListView searchAnsView = null;
	private AutoCompleteTextView searchText = null;
	private ImageButton searchButton = null;
	private MyHandler handler = new MyHandler(); 
	private String city = null;
	
	List<BusLine> busLines = null;
	List<Map<String, String>> data = null;
	SimpleAdapter adapter = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		initPreference();
		
		setContentView(R.layout.activity_buslinesearch);
		searchAnsView = (ListView)findViewById(R.id.busLineSearchAns);
		searchText = (AutoCompleteTextView)findViewById(R.id.searchContent);
		searchButton = (ImageButton)findViewById(R.id.lineSearchButton);
		searchButton.setOnClickListener(new MyButtonListener());
		data = new ArrayList<Map<String,String>>();
		adapter = new SimpleAdapter(getApplicationContext(), data, R.layout.busline_item, new String[]{"name", "info"}, new int[]{R.id.busline_item_name, R.id.busline_item_info});
		searchAnsView.setAdapter(adapter);
		searchAnsView.setOnItemClickListener(new MyOnItemClickListener());
	}
	
	private void initPreference(){
		SharedPreferences preferences = getSharedPreferences("init", MODE_PRIVATE);
		city = preferences.getString("city", "Заµє");
	}
	
	private class MyButtonListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//System.out.println("hello");
			Thread loadThread = new Thread(new LoadInfoThread());
			loadThread.start();
		}
		
	}
	
	private class MyOnItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> adapterView, View v, int position,
				long arg3) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(getApplicationContext(), ShowBusLineActivity.class);
			intent.putExtra("buslineinfo", busLines.get(position));
			startActivity(intent);
		}
		
	}
	
	private class LoadInfoThread implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			String searchContent = searchText.getText().toString();
			List<BusLine> busLines = AibangBus.queryBusLines(city, searchContent, "1");
			Message msg = new Message();
			msg.obj = busLines;
			handler.sendMessage(msg);
		}
		
	}
	
	@SuppressLint("HandlerLeak")
	private class MyHandler extends Handler{
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			
			busLines = (List<BusLine>)msg.obj;
			updateListView(busLines);
			super.handleMessage(msg);
		}
		
		private void updateListView(List<BusLine> list){
			data.clear();
			for (int i = 0; i < list.size(); i++) {
				BusLine busLine = list.get(i);
				System.out.println(busLine.toString());
				Map<String, String> map = new HashMap<String, String>();
				map.put("name", busLine.getName());
				map.put("info", busLine.getInfo());
				data.add(map);
			}
			adapter.notifyDataSetChanged();
		}
	}
}
