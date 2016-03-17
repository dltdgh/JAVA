package com.dlt.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.baidu.mapapi.utils.CoordinateConverter.CoordType;
import com.dlt.BusSearchClient.R;
import com.dlt.pojo.BusLine;
import com.dlt.pojo.BusSegment;
import com.dlt.pojo.BusStats_XY;
import com.dlt.pojo.BusTransfer;



public class ShowMapActivity extends Activity{
	
	MapView mapView = null;
	BaiduMap map = null;
 //   LocationClient locationClient = null;
    boolean isSetedFocus = false;
    BusLine busLine = null;
    int flag = 0;
    //RoutePlanSearch rSearch = null;
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_showmap);
        mapView = (MapView)findViewById(R.id.baiduMapView);
		map = mapView.getMap();
		map.setMapType(BaiduMap.MAP_TYPE_NORMAL);
		map.setMyLocationEnabled(false); //�رն�λͼ��
		
		/*rSearch = RoutePlanSearch.newInstance();          //��ȡ·�߼�������
		rSearch.setOnGetRoutePlanResultListener(new MyRoutePlanListener());*/
		
		flag = getIntent().getIntExtra("flag", 0);
		if (flag == 1) {
			busLine = (BusLine)getIntent().getSerializableExtra("busline");
			Integer markindex = getIntent().getIntExtra("markpoint", 0);
			List<LatLng> latLngs = initPoints(busLine);
			List<LatLng> points = getPoints(busLine);
			initMap1(markindex, latLngs, points);
		}
		else if (flag == 2) {
			BusTransfer transfer = (BusTransfer)getIntent().getSerializableExtra("bustransfer");
			List<List<LatLng>> list = initPoints(transfer);
			List<LatLng> points = getPoints(transfer);
			initMap2(list, points);
		}
		else if(flag == 3){
			BusStats_XY busStats_XY = (BusStats_XY)getIntent().getSerializableExtra("busstat");
			String[] strs = busStats_XY.getXy().split(",");
			LatLng latLng = convertLatLng(new LatLng(Double.valueOf(strs[1]), Double.valueOf(strs[0])));
			initMap3(latLng);
		}
		
	}

	/**
	 * �� busline ���������б�
	 * @param list
	 * @return
	 */
	private List<LatLng> initPoints(BusLine busLine){
		List<LatLng> tLatLngs = new ArrayList<LatLng>();
		String[] strs = busLine.getXys().split(";");
		for (int i = 0; i < strs.length; i++) {
			tLatLngs.add(convertLatLng(new LatLng(Double.valueOf(strs[i].split(",")[1]), Double.valueOf(strs[i].split(",")[0]))));
		}
		return tLatLngs;
	}
	
	/**
	 * ��busline ��ȡ��վ����
	 */
	
	private List<LatLng> getPoints(BusLine busLine){
		List<LatLng> tLatLngs = new ArrayList<LatLng>();
		String[] strs = busLine.getStat_xys().split(";");
		for (int i = 0; i < strs.length; i++) {
			tLatLngs.add(convertLatLng(new LatLng(Double.valueOf(strs[i].split(",")[1]), Double.valueOf(strs[i].split(",")[0]))));
		}
		return tLatLngs;
	}
	
	/**
	 * ���� bustransfer ��ʼ�������б�
	 * @param transfer
	 * @return
	 */
	private List<List<LatLng>> initPoints(BusTransfer transfer){
		List<List<LatLng>> list = new ArrayList<List<LatLng>>();
		for (BusSegment segment : transfer.getBusSegments()) {
			String[] strs = segment.getLine_xys().split(";");
			List<LatLng> latLngs = new ArrayList<LatLng>();
			for (int j = 0; j < strs.length; j++) {
				latLngs.add(convertLatLng(new LatLng(Double.valueOf(strs[j].split(",")[1]), Double.valueOf(strs[j].split(",")[0]))));
			}
			list.add(latLngs);
		}
		return list;
	}
	
	private List<LatLng> getPoints(BusTransfer transfer){
		List<LatLng> list = new ArrayList<LatLng>();
		for (BusSegment segment : transfer.getBusSegments()) {
			String[] strs = segment.getStat_xys().split(";");
			for (int j = 0; j < strs.length; j++) {
				list.add(convertLatLng(new LatLng(Double.valueOf(strs[j].split(",")[1]), Double.valueOf(strs[j].split(",")[0]))));
			}
		}
		return list;
	}
	 
	/**
	 * ����showbuslineactivity��������Ϣ
	 */
	
	private void initMap1(Integer markindex, List<LatLng> latLngs, List<LatLng> stats){
		
		LatLng latLng = stats.get(markindex);
		
		//latLng = convertLatLng(latLng);
		
		setFocus(latLng);
		
		drawLineOnMap(latLngs, 8, Color.GREEN, true);
		
	//	markPoint(latLng, R.drawable.icon_gcoding);
		
		markPoints(stats, markindex);
		
		/*PlanNode stNode = PlanNode.withLocation(convertLatLng(new LatLng(stations.get(0).getLat(), stations.get(0).getLng())));
		PlanNode enNode = PlanNode.withLocation(convertLatLng(new LatLng(stations.get(5).getLat(), stations.get(5).getLng())));
		rSearch.drivingSearch(new DrivingRoutePlanOption().from(stNode).to(enNode));*/
	}
	
	private void initMap2(List<List<LatLng>> list, List<LatLng> points) {
		// TODO Auto-generated method stub
		setFocus(list.get(0).get(0));
		for (int i = 0; i < list.size(); i++) {
			if (i > 0) {
				LatLng pre = list.get(i-1).get(list.get(i-1).size()-1);
				LatLng now = list.get(i).get(0);
				List<LatLng> temp = new ArrayList<LatLng>();
				temp.add(pre);
				temp.add(now);
				drawLineOnMap(temp, 8, Color.YELLOW, true);
			}
			drawLineOnMap(list.get(i), 8, Color.BLUE, true);
		}
		markPoints(points);
	}
	
	private void initMap3(LatLng latLng) {
		// TODO Auto-generated method stub
		setFocus(latLng);
		markPoint(latLng, R.drawable.icon_gcoding);
	}
	
	/**
	 * ����ͨ����תΪ�ٶ�����
	 * @param latLng
	 * @return
	 */
	
	private LatLng convertLatLng(LatLng latLng){
		CoordinateConverter converter = new CoordinateConverter();
		converter.from(CoordType.COMMON);
		converter.coord(new LatLng(latLng.latitude, latLng.longitude));
		latLng = converter.convert();
		return latLng;
	}
	
	/*
	private Double transLatLng(double degree){
		String degreeStr = String.valueOf(degree);
		Double part0 = Double.valueOf(degreeStr.substring(0, degreeStr.lastIndexOf('.')));  //��������
		degreeStr = degreeStr.substring(degreeStr.lastIndexOf('.')+1);      //С������
		Double part1 = Double.valueOf(degreeStr.substring(0, 2));                     //ǰ��λС��
		Double part2 = Double.valueOf(degreeStr.substring(2));                 //����С��
		System.out.println(part0+" "+part1+" "+part2);
		return part0+part1/60+part2/36000;                    //ת���������γ��
	}*/
	
	/**
	 * �ڵ�ͼ�ϱ�ǵ�
	 * @param latLng ��γ��
	 * @param res ���ͼ��id
	 */
	
	private void markPoint(LatLng latLng, Integer resId){
		BitmapDescriptor marker = BitmapDescriptorFactory.fromResource(resId);
		OverlayOptions option = null;
		option = new MarkerOptions().position(latLng).icon(marker);
		map.addOverlay(option);
	}
	
	/**
	 * ��ǵ�ͼ�ϵĵ㣬��һ���������Ĳ�ͬ
	 * @param list
	 * @param markindex  �����ǵĵ�
	 */
	
	private void markPoints(List<LatLng> list, int markindex) {
		for (int i = 0; i < list.size(); i++) {
			if (i == markindex) {
				markPoint(list.get(i), R.drawable.icon_mark_single);
			}
			else {
				markPoint(list.get(i), R.drawable.icon_gcoding);
			}		
		}
	}
	
	/**
	 * �ڵ�ͼ��ͳһ���һ���
	 * @param list
	 */
	private void markPoints(List<LatLng> list){
		for (int i = 0; i < list.size(); i++) {
			markPoint(list.get(i), R.drawable.icon_gcoding);
		}
	}
	
	/**
	 * ���õ�ͼ����
	 * @param latLng ��γ��
	 */
	private void setFocus( LatLng cenpt){
    	//���ó�ʼ��ʾ����
    	MapStatus mMapStatus = new MapStatus.Builder().target(cenpt).zoom(16).build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        map.setMapStatus(mMapStatusUpdate);
	}
    
	/**
	 * �ڵ�ͼ�ϻ���������·
	 */
	private void drawLineOnMap(List<LatLng> list, int width, int color, Boolean dotted) {
		
		/*List<Integer> colors = new ArrayList<Integer>();
		for (int i = 0; i < stations.size(); i++) {
			colors.add(Color.GREEN);
		}*/
		OverlayOptions ooPolyLine = new PolylineOptions().width(width).color(color).points(list).dottedLine(dotted);
		map.addOverlay(ooPolyLine);
	}
	
	/*private class MyRoutePlanListener implements OnGetRoutePlanResultListener{

		@Override
		public void onGetBikingRouteResult(BikingRouteResult result) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onGetDrivingRouteResult(DrivingRouteResult result) {
			// TODO Auto-generated method stub
			if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
				Toast.makeText(getApplicationContext(), "��Ǹ��δ�ҵ����", Toast.LENGTH_SHORT).show();
			}
			if (result.error == SearchResult.ERRORNO.NO_ERROR) {
				DrivingRouteOverlay overlay = new DrivingRouteOverlay(map);
				overlay.setData(result.getRouteLines().get(0));
				overlay.addToMap();
				overlay.zoomToSpan();
			}
		}

		@Override
		public void onGetTransitRouteResult(TransitRouteResult result) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onGetWalkingRouteResult(WalkingRouteResult result) {
			// TODO Auto-generated method stub
			
		}
		
	}*/
	
	/**
	 * �ڵ�ͼ����ʾ�Ի���ť
	 */
	
	/*private void setPopUpMark(LatLng latLng, String text){
		Button button = new Button(getApplicationContext());
		button.setText(text);
		InfoWindow infoWindow = new InfoWindow(button, latLng, -47);
		map.showInfoWindow(infoWindow);
	}*/
	
    @Override  
    public void onDestroy() {  
        super.onDestroy();  
        //��activityִ��onDestroyʱִ��mMapView.onDestroy()��ʵ�ֵ�ͼ�������ڹ��� 
        System.out.println("---destroy---");
        mapView.onDestroy();
      //  locationClient.stop();              //��locationclient����ر�
    }  
    
    @Override  
    public void onResume() {  
        super.onResume();
        //��activityִ��onResumeʱִ��mMapView. onResume ()��ʵ�ֵ�ͼ�������ڹ���  
        System.out.println("---resume---");
        mapView.onResume(); 
        isSetedFocus = false;                       //��ÿ�μ������ʱ�������õ�ͼ����
    }     
    
    @Override  
    public void onPause() {
        super.onPause();  
        //��activityִ��onPauseʱִ��mMapView. onPause ()��ʵ�ֵ�ͼ�������ڹ���  
        System.out.println("---pause---");
        mapView.onPause();
    }
}
