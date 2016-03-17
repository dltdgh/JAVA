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

import com.dlt.BusSearchClient.R;
import com.dlt.pojo.BusStats_XY;
import com.dlt.pojo.Coordinates;
import com.dlt.utils.AibangBus;

public class NearBusSearch extends Activity {
	
	private ListView searchAnsView = null;
	private EditText searchText = null;
	private ImageButton searchButton = null;
	private MyHandler handler = new MyHandler(); 
	private String city = "青岛";
	private Double distance = 600.0;
	
	List<BusStats_XY> busStats_XYs = null;
	List<Map<String, String>> data = null;
	SimpleAdapter adapter = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		initPreference();
		
		setContentView(R.layout.acticity_nearbussearch);
		searchAnsView = (ListView)findViewById(R.id.nearBusSearchAns);
		searchText = (EditText)findViewById(R.id.searchText);
		
		searchButton = (ImageButton)findViewById(R.id.nearBusSearchButton);
		searchButton.setOnClickListener(new MyButtonListener());
		
		data = new ArrayList<Map<String,String>>();
		adapter = new SimpleAdapter(getApplicationContext(), data, R.layout.busstat_item, new String[]{"name", "dis"}, new int[]{R.id.busstat_item_name, R.id.busstat_item_distance});
		searchAnsView.setAdapter(adapter);
		searchAnsView.setOnItemClickListener(new MyOnItemClickListener());
	}
	
	private void initPreference(){
		SharedPreferences preferences = getSharedPreferences("init", MODE_PRIVATE);
		city = preferences.getString("city", "青岛");
		distance = Double.valueOf(preferences.getString("distance", "600.0"));
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
	
	private class LoadInfoThread implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			String address = searchText.getText().toString();
			Coordinates coordinates = AibangBus.queryCoordinates(city, address);
	//		System.out.println(coordinates);
			if (coordinates != null) {
				List<BusStats_XY> list = AibangBus.queryBusStats_XY(city, coordinates.getLng(), coordinates.getLat(), distance);
				Message msg = new Message();
				msg.obj = list;
				handler.sendMessage(msg);
			}		
		}
		
	}
	
	private class MyOnItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> adapterView, View v, int position,
				long arg3) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(getApplicationContext(), ShowBusStatActivity.class);
			intent.putExtra("busstat", busStats_XYs.get(position));
			startActivity(intent);		
		}
		
	}
	
	@SuppressLint("HandlerLeak")
	private class MyHandler extends Handler{
		
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			busStats_XYs = (List<BusStats_XY>)msg.obj;
			updateListView(busStats_XYs);
		}
		private void updateListView(List<BusStats_XY> list){
			data.clear();
			for (int i = 0; i < list.size(); i++) {
				BusStats_XY busStats_XY = list.get(i);
		//		System.out.println(busLine.toString());
				Map<String, String> map = new HashMap<String, String>();
				map.put("name", "站点名称: "+busStats_XY.getName());
				map.put("dis", "距离: "+busStats_XY.getDist()+" 米");
				data.add(map);
			}
			adapter.notifyDataSetChanged();
		}
	}
}

/*private class MyOnLongClickListener implements OnLongClickListener{

@Override
public boolean onLongClick(View v) {
	// TODO Auto-generated method stub
//	Intent intent = new Intent(getApplicationContext(), PeekPointActivity.class);
//	startActivityForResult(intent, 1);
	return false;
}	
}

@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
// TODO Auto-generated method stub
super.onActivityResult(requestCode, resultCode, data);
if (resultCode == 1) {
	Bundle bundle = data.getBundleExtra("point");
	System.out.println("nearbussearch activity: "+bundle.getDouble("lat")+" "+bundle.getDouble("lng"));
	GeoCoder geocoder = GeoCoder.newInstance();
	geocoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
		
		@Override
		public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
			// TODO Auto-generated method stub
			searchText.setText(result.getAddress());
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Coordinates coordinates = AibangBus.queryCoordinates(city, searchText.getText().toString());
					if (coordinates != null) {
						point = new LatLng(coordinates.getLat(), coordinates.getLng());
						System.out.println("hehe: "+point.toString());
					}
				}
			}).start();
		}
		
		@Override
		public void onGetGeoCodeResult(GeoCodeResult result) {
			// TODO Auto-generated method stub
			
		}
	});
	geocoder.reverseGeoCode(new ReverseGeoCodeOption().location(new LatLng(bundle.getDouble("lat"), bundle.getDouble("lng"))));
}

}*/