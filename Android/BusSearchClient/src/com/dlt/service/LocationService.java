package com.dlt.service;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

public class LocationService extends Service {

	LocationClient client = null;
	BDLocation bdLocation = null;
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		SDKInitializer.initialize(getApplicationContext());
		client = new LocationClient(getApplicationContext());
		client.registerLocationListener(new LocationListener());
		initLocationClient(client);
		client.start();
		System.out.println("hehe");
	}
	
	private void initLocationClient(LocationClient client){
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true); //打开GPRS  
        option.setAddrType("all");//返回的定位结果包含地址信息  
        option.setCoorType("bd09ll");//返回的定位结果是百度经纬度,默认值gcj02  
        option.setPriority(LocationClientOption.GpsFirst); // 设置GPS优先  
        option.setScanSpan(5000); //设置发起定位请求的间隔时间为5000ms  
        option.disableCache(false);//禁止启用缓存定位  
        option.setEnableSimulateGps(true);
     //   option.setTimeOut(5000);
      //  option.setLocationMode(LocationMode.Hight_Accuracy);
        client.setLocOption(option);
  //      locationClient.start();
	}
	
	/**
     * 百度locationclient 监听对象
     * @author Administrator
     *
     */
    private class LocationListener implements BDLocationListener{

		@Override
		public void onReceiveLocation(BDLocation location) {
			// TODO Auto-generated method stub
			bdLocation = location;
			Intent intent = new Intent();
			intent.setAction("com.dlt.locationbroadcast");
			Bundle bundle = new Bundle();
			bundle.putDouble("lat", location.getLatitude());
			bundle.putDouble("lng", location.getLongitude());
			intent.putExtra("location", bundle);
			sendBroadcast(intent);
		}
    }
}
