package com.dlt.fragment;


import com.dlt.BusSearchClient.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class FragmentPage4 extends Fragment {
	
	SharedPreferences preferences = null;
	
	EditText cityText = null;
	EditText numText = null;
	EditText rcText = null;
	EditText radiusText = null;
	
	Button saveButton = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		Context context = getActivity().getApplicationContext();
		preferences = context.getSharedPreferences("init", Context.MODE_PRIVATE);
		
		View view = inflater.inflate(R.layout.fragment_4, null);
		cityText = (EditText)view.findViewById(R.id.cityText);
		numText = (EditText)view.findViewById(R.id.numText);
		rcText = (EditText)view.findViewById(R.id.rcText);
		radiusText = (EditText)view.findViewById(R.id.radiusText);
		
		saveButton = (Button)view.findViewById(R.id.saveButton);
		saveButton.setOnClickListener(new MyOnClickListener());
		
		System.out.println("init()--start");
		init();
		System.out.println("init()--end");
		return view;	
	}
	
	private void init(){
		String city = preferences.getString("city", "Заµє");
		int count = preferences.getInt("count", 10);
		int rc = preferences.getInt("rc", 0);
		String radius = preferences.getString("radius", "500.0");
		System.out.println(city+" "+count+" "+rc+" "+radius);
		cityText.setText(city);
		System.out.println("city--");
		numText.setText(count+"");
		System.out.println("num--");
		rcText.setText(rc+"");
		System.out.println("rc--");
		radiusText.setText(radius);
		System.out.println("radius--");
	}
	
	private void savePreferences(){
		Editor editor = preferences.edit();
		
		String city = cityText.getText().toString();
		int count = Integer.parseInt(numText.getText().toString());
		int rc = Integer.parseInt(rcText.getText().toString());
		String radius = radiusText.getText().toString();
		
		editor.putString("city", city);
		editor.putInt("count", count);
		editor.putInt("rc", rc);
		editor.putString("radius", radius);
		editor.commit();
	}
	
	private class MyOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v.getId() == R.id.saveButton) {
				savePreferences();
			}
		}
		
	}
}
