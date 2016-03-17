package com.dlt.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.dlt.BusSearchClient.R;
import com.dlt.pojo.BusLine;
import com.dlt.pojo.BusStats_XY;
import com.dlt.utils.AibangBus;

public class ShowBusStatActivity extends Activity {
	
	TextView infoView = null;
	ListView statsView = null;
	BusStats_XY busStats_XY = null;
	private  String city = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		initPreference();
		
		setContentView(R.layout.activity_showbusstat);
		infoView = (TextView)findViewById(R.id.showbusstat_desc);
		infoView.setOnClickListener(new MyOnClickListener());
		
		statsView = (ListView)findViewById(R.id.showbusstat_list);
		statsView.setOnItemClickListener(new MyOnItemClickListener());
		busStats_XY = (BusStats_XY)getIntent().getSerializableExtra("busstat");
		init();
	}
	
	private void initPreference(){
		SharedPreferences preferences = getSharedPreferences("init", MODE_PRIVATE);
		city = preferences.getString("city", "青岛");
	}
	
	private void init(){
		infoView.setText("经过 "+busStats_XY.getName()+" 的公交");
		String[] names = busStats_XY.getLine_names().split(";");
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		for (int i = 0; i < names.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("name", names[i]);
			list.add(map);
		}
		SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), list, R.layout.showbusstat_item, new String[]{"name"}, new int[]{R.id.showbusstat_item_name});
		statsView.setAdapter(adapter);
	}
	
	private class MyOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v.getId() == R.id.showbusstat_desc) {
				Intent intent = new Intent(getApplicationContext(), ShowMapActivity.class);
				intent.putExtra("flag", 3);
				intent.putExtra("busstat", busStats_XY);
				startActivity(intent);
			}
		}
		
	}
	
	private class MyOnItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> adapterView, View view, int position,
				long arg3) {
			// TODO Auto-generated method stub
			TextView textView = (TextView)view.findViewById(R.id.showbusstat_item_name);
			final String name = textView.getText().toString();
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					List<BusLine> list = AibangBus.queryBusLines(city, name, "1");
					if (list != null) {
						Intent intent = new Intent(getApplicationContext(), ShowBusLineActivity.class);
						intent.putExtra("buslineinfo", list.get(0));
						startActivity(intent);
					}	
				}
			}).start();
			
		}
	}
}
