package com.dlt.fragment;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.dlt.BusSearchClient.R;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentPage2 extends Fragment {
	
	Context context = null;
	MapView mapView = null;
	BaiduMap map = null;
//    LocationManager locationManager = null;                     //使用手机自带定位时解开注释
    String provider = null;
    LocationClient locationClient = null;
    boolean isSetedFocus = false;
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		context = getActivity().getApplicationContext();
		SDKInitializer.initialize(context); 
	//	locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);  //使用手机自带定位时解开注释
//        provider = getProvider();  //使用手机自带定位时解开注释
        locationClient = new LocationClient(context);
        locationClient.registerLocationListener(new LocationListener());
        initLocationClient();
        locationClient.start();   //开启定位服务
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View view = inflater.inflate(R.layout.fragment_2, null);
		mapView = (MapView)view.findViewById(R.id.bmapView);		
		map = mapView.getMap();
		initMap();
		
	//	locate(locationClient.getLastKnownLocation());
		return view;
	}
	
	private void initLocationClient(){
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
        locationClient.setLocOption(option);
  //      locationClient.start();
	}
	
	private void initMap(){
		map.setMapType(BaiduMap.MAP_TYPE_NORMAL);
		map.setMyLocationEnabled(true); //开启定位图层
		//locate();
		//locationManager.requestLocationUpdates(provider, 1000, 10, new LocationListener());  //使用手机自带定位时解开注释
	}
	
	
	private void locate(BDLocation location){
	
    	MyLocationData locationData = new MyLocationData.Builder()
    	.accuracy(location.getRadius())
    	.latitude(location.getLatitude())
    	.longitude(location.getLongitude())
    	.build();
    	map.setMyLocationData(locationData);
    //	BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory.fromResource(R.drawable.icon_locate); //使用手机自带定位时解开注释
    //	MyLocationConfiguration config = new MyLocationConfiguration(LocationMode.NORMAL, true, mCurrentMarker);  //使用手机自带定位时解开注释
    //	mBaiduMap.setMyLocationConfigeration(config); //使用手机自带定位时解开注释
    	
    	//mBaiduMap.setMyLocationEnabled(false); //使用手机自带定位时解开注释
    }
	
	
	/**
	 * 设置地图焦点
	 * @param location baidu location对象
	 */
	private void setFocus(BDLocation location){
    	//设置初始显示坐标
        LatLng cenpt = new LatLng(location.getLatitude(),location.getLongitude());
    	MapStatus mMapStatus = new MapStatus.Builder().target(cenpt).zoom(18).build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        map.setMapStatus(mMapStatusUpdate);
        //markPoint(mBaiduMap, cenpt);
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
			System.out.println("listener: "+location.toString());
			if (!isSetedFocus) {              //已经设置焦点则不用再次设定
				setFocus(location);
				isSetedFocus = true;
			}
	//		setFocus(location);
			locate(location);
		//	locationClient.requestLocation();
		}
    	
    }
    
    @Override  
    public void onDestroy() {  
        super.onDestroy();  
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理  
        System.out.println("---destroy---");
        mapView.onDestroy();
        locationClient.stop();              //将locationclient服务关闭
    }  
    
    @Override  
    public void onResume() {  
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理  
        System.out.println("---resume---");
   //     locationClient.start();
     //   System.out.println(locationClient.isStarted());
        mapView.onResume(); 
        isSetedFocus = false;                       //在每次激活界面时重新设置地图焦点
    }  
    
    @Override  
    public void onPause() {  
        super.onPause();  
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理  
        System.out.println("---pause---");
        mapView.onPause(); 
      //  locationClient.
    }
}


/*此段代码是使用手机自带gps定位代码
private String getProvider(){
Criteria criteria = new Criteria();
criteria.setAccuracy(Criteria.ACCURACY_FINE);
criteria.setAltitudeRequired(false);
criteria.setBearingRequired(false);
criteria.setCostAllowed(true);
criteria.setPowerRequirement(Criteria.POWER_LOW);
String provider = locationManager.getBestProvider(criteria, true);
return provider;
}

private Location getLocation() {
// TODO Auto-generated method stub   	
Location location = locationManager.getLastKnownLocation(provider);
System.out.println(location);
return location;
}

private void setLocation(Location location){
//设置初始显示坐标
LatLng cenpt = new LatLng(location.getLatitude(),location.getLongitude());
MapStatus mMapStatus = new MapStatus.Builder().target(cenpt).zoom(18).build();
MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
map.setMapStatus(mMapStatusUpdate);
//markPoint(mBaiduMap, cenpt);
}

private void markPoint(LatLng point){
BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding);
OverlayOptions options = new MarkerOptions().position(point).icon(bitmapDescriptor);
map.addOverlay(options);
}

private void locate(){
Location location = getLocation();

setLocation(location);   //设置地图

MyLocationData locationData = new MyLocationData.Builder()
.accuracy(location.getAccuracy())
.latitude(location.getLatitude())
.longitude(location.getLongitude())
.build();
map.setMyLocationData(locationData);
//	BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory.fromResource(R.drawable.icon_locate);
//	MyLocationConfiguration config = new MyLocationConfiguration(LocationMode.NORMAL, true, mCurrentMarker);
//	mBaiduMap.setMyLocationConfigeration(config);

//mBaiduMap.setMyLocationEnabled(false);
}

private class MyLocationListener implements LocationListener{

private BaiduMap mBaiduMap = null;

public MyLocationListener(BaiduMap mBaiduMap){
	this.mBaiduMap = mBaiduMap;
}

@Override
public void onLocationChanged(Location location) {
	// TODO Auto-generated method stub
	System.out.println(location);
	setLocation(location);
	locate();
}

@Override
public void onProviderDisabled(String provider) {
	// TODO Auto-generated method stub
	System.out.println(provider);
}

@Override
public void onProviderEnabled(String provider) {
	// TODO Auto-generated method stub
	System.out.println(provider);
}

@Override
public void onStatusChanged(String provider, int status, Bundle extras) {
	// TODO Auto-generated method stub
	System.out.println(provider+" "+status);
}

}

*/