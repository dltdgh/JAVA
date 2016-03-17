package com.dlt.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.dlt.BusSearchClient.R;
import com.dlt.fragment.FragmentPage1;
import com.dlt.fragment.FragmentPage2;
import com.dlt.fragment.FragmentPage3;
import com.dlt.fragment.FragmentPage4;

public class MainActivity extends FragmentActivity {
	private FragmentTabHost tabHost = null;
	private LayoutInflater inflater = null;
	private Class<?> fragmentArray[] = {FragmentPage1.class, FragmentPage2.class, FragmentPage3.class, FragmentPage4.class};
	private String textViewArray[] = {"查询", "定位", "周边", "设置"};
	private int imageViewArray[] = {R.drawable.map_icon_fangan, R.drawable.map_icon_route, R.drawable.map_icon_nearby, R.drawable.map_icon_poilist}; 
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_main);
		inflater = getLayoutInflater();
		tabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
		initView();
		
		/*Editor editor = getSharedPreferences("init", Context.MODE_PRIVATE).edit();
		editor.putString("hehe", "hoho");
		editor.commit();*/
	}
	
	/**
	 * 初始化tabhost
	 */
	
	private void initView() {
		// TODO Auto-generated method stub
		tabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
		int count = fragmentArray.length;
		for (int i = 0; i < count; i++) { 
			TabSpec tabSpec = tabHost.newTabSpec(textViewArray[i]).setIndicator(getTabItemView(i));
			tabHost.addTab(tabSpec, fragmentArray[i], null);
		}
		
	}

	private View getTabItemView(final int index) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.tab_item_view, null);
		ImageView imageView = (ImageView)view.findViewById(R.id.imageView);
		imageView.setImageResource(imageViewArray[index]);
		TextView textView = (TextView)view.findViewById(R.id.textView);
	//	System.out.println(textViewArray[index]);
		textView.setText(textViewArray[index]);
		
		return view;
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
	//	Intent intent = new Intent(getApplicationContext(), LocationService.class);
	//	stopService(intent);
		super.onDestroy();
	}
}
