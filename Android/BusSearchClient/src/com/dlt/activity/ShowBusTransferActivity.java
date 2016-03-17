package com.dlt.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dlt.BusSearchClient.R;
import com.dlt.pojo.BusSegment;
import com.dlt.pojo.BusTransfer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

public class ShowBusTransferActivity extends Activity {
	
	ExpandableListView listView = null;
	TextView infoView = null;
	
	List<Map<String, String>> parent;
	List<List<Map<String, String>>> child;
	BusTransfer transfer = null;
	
	LayoutInflater inflater = null;
	
	MyAdapter adapter = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_showbustransfer);
		
		inflater = getLayoutInflater();
		
		listView = (ExpandableListView)findViewById(R.id.showBusTransfer_list);
		infoView = (TextView)findViewById(R.id.showBusTransfer_info);
		
		String transferInfo = getIntent().getStringExtra("bustransferinfo");
		infoView.setText(transferInfo);
		
		infoView.setOnClickListener(new MyOnClickListener());
		
		transfer = (BusTransfer)getIntent().getSerializableExtra("bustransfer");
		
		initData();
		adapter = new MyAdapter();
		listView.setAdapter(adapter);
	}
	
	/**
	 * 加载expandableListView中的数据
	 */
	
	private void initData() {
		// TODO Auto-generated method stub
		parent = new ArrayList<Map<String,String>>();
		child = new ArrayList<List<Map<String,String>>>();
		
		for (BusSegment segment : transfer.getBusSegments()) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("busname", segment.getLine_name());
			map.put("distance", "距离 "+segment.getLine_dist()+" 米");
			
			
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			String[] stats = segment.getStats().split(";");
			
			map.put("stat_num", "车站数 "+stats.length+" 个");
			parent.add(map);
			
			for (int i = 0; i < stats.length; i++) {
				Map<String, String> tMap = new HashMap<>();
				tMap.put("name", stats[i]);
				list.add(tMap);
			}
			child.add(list);
		}
	}	
	
	private class MyOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(getApplicationContext(), ShowMapActivity.class);
			intent.putExtra("flag", 2);
			intent.putExtra("bustransfer", transfer);
			startActivity(intent);
		}
		
	}
	
	private class MyAdapter extends BaseExpandableListAdapter{

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return child.get(groupPosition).get(childPosition);
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return childPosition;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.showbustransfer_child_item, null);
			}
			
			TextView statView = (TextView)convertView.findViewById(R.id.showbustransfer_child_item_stat);
			statView.setText(child.get(groupPosition).get(childPosition).get("name"));
			
			return convertView;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			// TODO Auto-generated method stub
			return child.get(groupPosition).size();
		}

		@Override
		public Object getGroup(int groupPosition) {
			// TODO Auto-generated method stub
			return parent.get(groupPosition);
		}

		@Override
		public int getGroupCount() {
			// TODO Auto-generated method stub
			return parent.size();
		}

		@Override
		public long getGroupId(int groupPosition) {
			// TODO Auto-generated method stub
			return groupPosition;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup tParent) {
			// TODO Auto-generated method stub
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.showbustransfer_parent_item, null);
			}
			
			TextView statView = (TextView)convertView.findViewById(R.id.busTransferItemName);
			TextView statNumView = (TextView)convertView.findViewById(R.id.bustransfer_parent_item_stat_num);
			TextView distanceView = (TextView)convertView.findViewById(R.id.bustransfer_parent_item_distance);
			
			statView.setText(parent.get(groupPosition).get("busname"));
			statNumView.setText(parent.get(groupPosition).get("stat_num"));
			distanceView.setText(parent.get(groupPosition).get("distance"));
			
			return convertView;
		}

		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return false;
		}
		
	}
}
