package com.dlt.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.dlt.BusSearchClient.R;
import com.dlt.pojo.BusTransfer;
import com.dlt.pojo.BusTransferPlan;
import com.dlt.utils.AibangBus;

public class BusTransferSearchActivity extends Activity {
	
	EditText startPosText = null;
	EditText endPosText = null; 
	
	String city = null;
	int count = 10;
	int rc = 0;
	
	MyHandler handler = new MyHandler();
	SimpleAdapter adapter = null;
	
	List<BusTransfer> busTransfers = null;
	List<Map<String, String>> data = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		initPreference();            //由preferences初始化数据
		
		setContentView(R.layout.activity_bustransfersearch);
		startPosText = (EditText)findViewById(R.id.startPos);
		endPosText = (EditText)findViewById(R.id.endPos);
		ListView listView = (ListView)findViewById(R.id.busTransferAns);
		ImageButton button = (ImageButton)findViewById(R.id.transferSearchButton);
		
		button.setOnClickListener(new OnclickListener());
		
		data = new ArrayList<Map<String, String>>();		
		adapter = new SimpleAdapter(getApplicationContext(), data, R.layout.bustransfer_item, new String[]{"id", "desc", "stat_num", "time_cost", "total_dis", "walk_dis"}, new int[]{R.id.bustransfer_plan_num, R.id.bustransfer_info, R.id.bustransfer_stat_num, R.id.bustransfer_time, R.id.bustransfer_distance, R.id.bustransfer_walk});
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new MyOnItemClickListener());
	}
	
	private void initPreference(){
		SharedPreferences preferences = getSharedPreferences("init", MODE_PRIVATE);
		city = preferences.getString("city", "青岛");
		count = preferences.getInt("count", 10);
		rc = preferences.getInt("rc", 0);
	}
	
	private class LoadInfoThread implements Runnable{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			String start_addr = startPosText.getText().toString();
			String end_addr = endPosText.getText().toString();
			List<BusTransfer> busTransfers = AibangBus.queryBusTransfers(city, start_addr, end_addr, null, null, null, null, rc, count, 1);
			Message msg = new Message();
			msg.obj = busTransfers;
			handler.sendMessage(msg);
		}
		
	}
	
	private class OnclickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Thread loadThread = new Thread(new LoadInfoThread());
			loadThread.start();
		}
		
	}
	
	@SuppressLint("HandlerLeak")
	private class MyHandler extends Handler{
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			
			busTransfers = (List<BusTransfer>)msg.obj;
			updateListView(busTransfers);
			super.handleMessage(msg);
		}
		
		private void updateListView(List<BusTransfer> list){
			data.clear();
			for (int i = 0; i < list.size(); i++) {
				BusTransfer busTransfer = list.get(i);
				System.out.println(busTransfer.toString());
				
				BusTransferPlan plan = new BusTransferPlan(i, busTransfer);
				Map<String, String> map = new HashMap<String, String>();
				map.put("id", plan.id.toString());
				map.put("desc", plan.desc);
				map.put("stat_num", "途径"+plan.stat_num+"个站点");
				map.put("time_cost", "耗时约"+plan.time_cost+"分钟");
				map.put("total_dis", "总行程约"+plan.total_dis+"米");
				map.put("walk_dis", "步行约"+plan.walk_dis+"米");
				data.add(map);
			}
			adapter.notifyDataSetChanged();
		}
	}
	
	private class MyOnItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> adapterView, View view, int position,
				long arg3) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(getApplicationContext(), ShowBusTransferActivity.class);
			intent.putExtra("bustransfer", busTransfers.get(position));
			String info = ((TextView)view.findViewById(R.id.bustransfer_info)).getText().toString();
			System.out.println("now debug------>"+info);
			intent.putExtra("bustransferinfo", info);
			startActivity(intent);
		}
		
	}
}
