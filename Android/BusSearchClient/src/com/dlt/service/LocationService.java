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
		option.setOpenGps(true); //��GPRS  
        option.setAddrType("all");//���صĶ�λ���������ַ��Ϣ  
        option.setCoorType("bd09ll");//���صĶ�λ����ǰٶȾ�γ��,Ĭ��ֵgcj02  
        option.setPriority(LocationClientOption.GpsFirst); // ����GPS����  
        option.setScanSpan(5000); //���÷���λ����ļ��ʱ��Ϊ5000ms  
        option.disableCache(false);//��ֹ���û��涨λ  
        option.setEnableSimulateGps(true);
     //   option.setTimeOut(5000);
      //  option.setLocationMode(LocationMode.Hight_Accuracy);
        client.setLocOption(option);
  //      locationClient.start();
	}
	
	/**
     * �ٶ�locationclient ��������
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
