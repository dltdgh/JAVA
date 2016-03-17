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
//    LocationManager locationManager = null;                     //ʹ���ֻ��Դ���λʱ�⿪ע��
    String provider = null;
    LocationClient locationClient = null;
    boolean isSetedFocus = false;
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		context = getActivity().getApplicationContext();
		SDKInitializer.initialize(context); 
	//	locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);  //ʹ���ֻ��Դ���λʱ�⿪ע��
//        provider = getProvider();  //ʹ���ֻ��Դ���λʱ�⿪ע��
        locationClient = new LocationClient(context);
        locationClient.registerLocationListener(new LocationListener());
        initLocationClient();
        locationClient.start();   //������λ����
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
		option.setOpenGps(true); //��GPRS  
        option.setAddrType("all");//���صĶ�λ���������ַ��Ϣ  
        option.setCoorType("bd09ll");//���صĶ�λ����ǰٶȾ�γ��,Ĭ��ֵgcj02  
        option.setPriority(LocationClientOption.GpsFirst); // ����GPS����  
        option.setScanSpan(5000); //���÷���λ����ļ��ʱ��Ϊ5000ms  
        option.disableCache(false);//��ֹ���û��涨λ  
        option.setEnableSimulateGps(true);
     //   option.setTimeOut(5000);
      //  option.setLocationMode(LocationMode.Hight_Accuracy);
        locationClient.setLocOption(option);
  //      locationClient.start();
	}
	
	private void initMap(){
		map.setMapType(BaiduMap.MAP_TYPE_NORMAL);
		map.setMyLocationEnabled(true); //������λͼ��
		//locate();
		//locationManager.requestLocationUpdates(provider, 1000, 10, new LocationListener());  //ʹ���ֻ��Դ���λʱ�⿪ע��
	}
	
	
	private void locate(BDLocation location){
	
    	MyLocationData locationData = new MyLocationData.Builder()
    	.accuracy(location.getRadius())
    	.latitude(location.getLatitude())
    	.longitude(location.getLongitude())
    	.build();
    	map.setMyLocationData(locationData);
    //	BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory.fromResource(R.drawable.icon_locate); //ʹ���ֻ��Դ���λʱ�⿪ע��
    //	MyLocationConfiguration config = new MyLocationConfiguration(LocationMode.NORMAL, true, mCurrentMarker);  //ʹ���ֻ��Դ���λʱ�⿪ע��
    //	mBaiduMap.setMyLocationConfigeration(config); //ʹ���ֻ��Դ���λʱ�⿪ע��
    	
    	//mBaiduMap.setMyLocationEnabled(false); //ʹ���ֻ��Դ���λʱ�⿪ע��
    }
	
	
	/**
	 * ���õ�ͼ����
	 * @param location baidu location����
	 */
	private void setFocus(BDLocation location){
    	//���ó�ʼ��ʾ����
        LatLng cenpt = new LatLng(location.getLatitude(),location.getLongitude());
    	MapStatus mMapStatus = new MapStatus.Builder().target(cenpt).zoom(18).build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        map.setMapStatus(mMapStatusUpdate);
        //markPoint(mBaiduMap, cenpt);
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
			System.out.println("listener: "+location.toString());
			if (!isSetedFocus) {              //�Ѿ����ý��������ٴ��趨
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
        //��activityִ��onDestroyʱִ��mMapView.onDestroy()��ʵ�ֵ�ͼ�������ڹ���  
        System.out.println("---destroy---");
        mapView.onDestroy();
        locationClient.stop();              //��locationclient����ر�
    }  
    
    @Override  
    public void onResume() {  
        super.onResume();
        //��activityִ��onResumeʱִ��mMapView. onResume ()��ʵ�ֵ�ͼ�������ڹ���  
        System.out.println("---resume---");
   //     locationClient.start();
     //   System.out.println(locationClient.isStarted());
        mapView.onResume(); 
        isSetedFocus = false;                       //��ÿ�μ������ʱ�������õ�ͼ����
    }  
    
    @Override  
    public void onPause() {  
        super.onPause();  
        //��activityִ��onPauseʱִ��mMapView. onPause ()��ʵ�ֵ�ͼ�������ڹ���  
        System.out.println("---pause---");
        mapView.onPause(); 
      //  locationClient.
    }
}


/*�˶δ�����ʹ���ֻ��Դ�gps��λ����
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
//���ó�ʼ��ʾ����
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

setLocation(location);   //���õ�ͼ

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