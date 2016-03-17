package com.dlt.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dlt.BusSearchClient.R;
import com.dlt.pojo.BusLine;
import com.dlt.pojo.BusStation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ShowBusLineActivity extends Activity {
	
	BusLine busLine = null;
	List<BusStation> stations = null;
	LayoutInflater inflater = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_showbusline);
		busLine = (BusLine)getIntent().getSerializableExtra("buslineinfo");
		stations = parseBusLine(busLine);
		inflater = getLayoutInflater();
		
		TextView descView = (TextView)findViewById(R.id.showbusline_desc);
		descView.setText(busLine.getName()+"\n"+busLine.getInfo());
		ListView listView = (ListView)findViewById(R.id.showbusline_list);
		MyAdapter adapter = new MyAdapter(stations);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new MyItemClickListener());
	}
	
	private class MyItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> adapterView, View view, int position,
				long arg3) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(ShowBusLineActivity.this, ShowMapActivity.class);
			intent.putExtra("flag", 1);
			intent.putExtra("markpoint", position);
			intent.putExtra("busline", (Serializable)busLine);
			startActivity(intent);
		}
		
	}
	
	private class MyAdapter extends BaseAdapter{
		
		List<BusStation> list = null;
		
		public MyAdapter(List<BusStation> list) {
			// TODO Auto-generated constructor stub
			this.list = list;
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int index) {
			// TODO Auto-generated method stub
			return list.get(index);
		}

		@Override
		public long getItemId(int index) {
			// TODO Auto-generated method stub
			return index;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			TextView numView = null;
			TextView nameView = null;
			ImageView topView = null;
			ImageView bottomView = null;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.busline_pos_item, null);
				numView = (TextView)convertView.findViewById(R.id.busLineItemNum);
				nameView = (TextView)convertView.findViewById(R.id.busLineItemName);
				topView = (ImageView)convertView.findViewById(R.id.busLineItemTop);
				bottomView = (ImageView)convertView.findViewById(R.id.busLineItemBottom);
				LayoutCache layoutCache = new LayoutCache(numView, nameView, topView, bottomView);
				convertView.setTag(layoutCache);
			}
			else {
				LayoutCache cache = (LayoutCache)convertView.getTag();
				numView = cache.numView;
				nameView = cache.nameView;
				topView = cache.topView;
				bottomView = cache.bottomView;
			}
			
			
			numView.setText((list.get(position).getNum()+1)+""); //list.get(position).getNum().toString()
			nameView.setText(list.get(position).getStationName());
			
			return convertView;
		}
		
		private class LayoutCache{
			TextView numView = null;
			TextView nameView = null;
			ImageView topView = null;
			ImageView bottomView = null;
			public LayoutCache(TextView numView, TextView nameView, ImageView topView, ImageView bottomView) {
				// TODO Auto-generated constructor stub
				this.numView = numView;
				this.nameView = nameView;
				this.topView = topView;
				this.bottomView = bottomView;
			}
		}
		
	}
	
	private List<BusStation> parseBusLine(BusLine line){
		List<BusStation> stations = new ArrayList<BusStation>();
		String[] names = line.getStats().split(";");
		String[] xys = line.getStat_xys().split(";");
		System.out.println(names.length+" "+xys.length);
		if (names.length == xys.length) {
			for (int i = 0; i < names.length; i++) {
				BusStation station = new BusStation(names[i], i, Double.parseDouble(xys[i].split(",")[1]), Double.parseDouble(xys[i].split(",")[0]));
				System.out.println(station.toString());
				stations.add(station);
			}
		}
		return stations;
	}
}
