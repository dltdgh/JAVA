package com.dlt.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapLongClickListener;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.dlt.BusSearchClient.R;

public class PeekPointActivity extends Activity {
	
	MapView mapView = null;
	BaiduMap map = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_peekpoint);
        mapView = (MapView)findViewById(R.id.baiduMapPeekView);
		map = mapView.getMap();
		map.setMapType(BaiduMap.MAP_TYPE_NORMAL);
		map.setMyLocationEnabled(false); //�رն�λͼ��
		map.setOnMapLongClickListener(new MyLongClickListener());
	}
	
	private class MyLongClickListener implements OnMapLongClickListener{

		@Override
		public void onMapLongClick(LatLng point) {
			// TODO Auto-generated method stub
			System.out.println(point.latitude+" "+point.longitude);
			Intent intent = new Intent();
			Bundle bundle = new Bundle();
			bundle.putDouble("lat", point.latitude);
			bundle.putDouble("lng", point.longitude);
			intent.putExtra("point", bundle);
			setResult(1, intent);
			finish();
		}
		
	}
	
	@Override  
    public void onDestroy() {  
        super.onDestroy();  
        //��activityִ��onDestroyʱִ��mMapView.onDestroy()��ʵ�ֵ�ͼ�������ڹ��� 
        System.out.println("---destroy---");
        mapView.onDestroy();
    }  
    
    @Override  
    public void onResume() {  
        super.onResume();
        //��activityִ��onResumeʱִ��mMapView. onResume ()��ʵ�ֵ�ͼ�������ڹ���  
        System.out.println("---resume---");
        mapView.onResume(); 
    }     
    
    @Override  
    public void onPause() {
        super.onPause();  
        //��activityִ��onPauseʱִ��mMapView. onPause ()��ʵ�ֵ�ͼ�������ڹ���  
        System.out.println("---pause---");
        mapView.onPause();
    }
}
