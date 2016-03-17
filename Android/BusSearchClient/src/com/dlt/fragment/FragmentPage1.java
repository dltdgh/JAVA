package com.dlt.fragment;

import com.dlt.BusSearchClient.R;
import com.dlt.activity.BusLineSearchActivity;
import com.dlt.activity.BusTransferSearchActivity;
import com.dlt.activity.NearBusSearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class FragmentPage1 extends Fragment {
	
	Button busLineSearchButton = null;
	Button busTransferButton = null;
	Button nearBusSearchButton = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_1, null);
		
		ButtonListener listener = new ButtonListener();
		
		busLineSearchButton = (Button)view.findViewById(R.id.busLineSearchBtton);
		busLineSearchButton.setOnClickListener(listener);
		
		busTransferButton = (Button)view.findViewById(R.id.busTransferSearchButton);
		busTransferButton.setOnClickListener(listener);
		
		nearBusSearchButton = (Button)view.findViewById(R.id.nearBusSearchButton);
		nearBusSearchButton.setOnClickListener(listener);
		
		return view;
	}
	
	private class ButtonListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v.getId() == R.id.busLineSearchBtton) {
				Intent intent = new Intent(getActivity(), BusLineSearchActivity.class);
				startActivity(intent);
			}
			else if (v.getId() == R.id.busTransferSearchButton) {
				Intent intent = new Intent(getActivity(), BusTransferSearchActivity.class);
				startActivity(intent);
			}
			else if (v.getId() == R.id.nearBusSearchButton) {
				Intent intent = new Intent(getActivity(), NearBusSearch.class);
				startActivity(intent);
			}
		}
	}
}
