package com.example.mp3player;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Random;

import android.app.Activity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class PlayerActivity extends Activity{
	
	ImageButton beginButton = null;
	ImageButton pauseButton = null;
	ImageButton stopButton = null;
	private TextView textView = null;
	private TextView showTime = null;	
	private Mp3Info mp3Info = null;
	private TextView showTimeLeft = null;
	private TextView lrcText = null;	
	private SeekBar seekBar = null;
	private boolean isPlaying = false;
	private IntentFilter intentFilter = null;
	private BroadcastReceiver receiverMsg = null;
	private BroadcastReceiver receiverTime = null;
	private Random random = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.player);
		Intent intent = getIntent();
		mp3Info = (Mp3Info)intent.getSerializableExtra("mp3Info");
//		System.out.println(mp3Info.toString());
		beginButton = (ImageButton)findViewById(R.id.begin);
		pauseButton = (ImageButton)findViewById(R.id.pause);
		stopButton = (ImageButton)findViewById(R.id.stop);
		textView = (TextView)findViewById(R.id.textView);
		textView.setText(mp3Info.getMp3Name());
		lrcText = (TextView)findViewById(R.id.lrcText);
		showTime = (TextView)findViewById(R.id.showTime);
		showTimeLeft = (TextView)findViewById(R.id.showTimeLeft);
		seekBar = (SeekBar)findViewById(R.id.seekBar);
//		seekBar.setProgress(50);
		random = new Random();
		
		beginButton.setOnClickListener(new BeginButtonListener());
		pauseButton.setOnClickListener(new PauseButtonListener());
		stopButton.setOnClickListener(new StopButtonListener());
		seekBar.setOnSeekBarChangeListener(new SeekBarListener());
	}
	
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		receiverMsg = new LrcMessageBroadcastReceiver();
		receiverTime = new TimeMessageBroadcastReceiver();
		registerReceiver(receiverMsg, getMessageIntentFilter());
		registerReceiver(receiverTime, getTimeIntentFilter());
		super.onResume();
	}


	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		unregisterReceiver(receiverMsg);
		unregisterReceiver(receiverTime);
		super.onPause();
	}

	
	class BeginButtonListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			Intent intent = new Intent();
			intent.setClass(PlayerActivity.this, PlayerService.class);
			intent.putExtra("mp3Info", mp3Info);
			intent.putExtra("MSG", AppConstant.PlayerMsg.PLAY_MSG);

			// System.out.println(mp3Info.getLrcName());
			
			startService(intent);
			//isPlaying = true;
		}
	}
	
	class PauseButtonListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			System.out.println("Pause Button Clicked!");
			Intent intent = new Intent();
			intent.setClass(PlayerActivity.this, PlayerService.class);
			intent.putExtra("mp3Info", mp3Info);
			intent.putExtra("MSG", AppConstant.PlayerMsg.PAUSE_MSG);
			startService(intent);
		}	
	}
	
	class StopButtonListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		
			System.out.println("Stop Button Clicked!");
			
			Intent intent = new Intent();
			intent.setClass(PlayerActivity.this, PlayerService.class);
			intent.putExtra("mp3Info", mp3Info);
			intent.putExtra("MSG", AppConstant.PlayerMsg.STOP_MSG);
			startService(intent);
			
		}	
	}
	
	class SeekBarListener implements OnSeekBarChangeListener{

		int prog;
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			// TODO Auto-generated method stub
			prog = progress;
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			seekBar.setProgress(prog);
			Intent intent = new Intent();
			intent.setClass(PlayerActivity.this, PlayerService.class);
//			intent.putExtra("mp3Info", null);
			intent.putExtra("proPos", prog);
			intent.putExtra("MSG", AppConstant.PlayerMsg.CHANGE_MSG);
			startService(intent);
		}
		
	}
	
	private IntentFilter getMessageIntentFilter(){
		
		intentFilter = new IntentFilter();
		intentFilter.addAction(AppConstant.LRC_MESSAGE_ACTION);
		return intentFilter;
	}
	
	private IntentFilter getTimeIntentFilter(){
		
		intentFilter = new IntentFilter();
		intentFilter.addAction(AppConstant.TIME_MESSAGE_ACTION);
		return intentFilter;
	}
	
	class LrcMessageBroadcastReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String lrcMessage = intent.getStringExtra("lrcMessage");
			
			if(lrcMessage != null)
			{
				lrcText.setTextColor(Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
				lrcText.setText(lrcMessage);
			}
		}
		
	}
	
	class TimeMessageBroadcastReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if (intent.hasExtra("timeMessage")) {
				String timeMessage = intent.getStringExtra("timeMessage");
				showTime.setText(timeMessage);
			}
			if (intent.hasExtra("timeRmind")) {
				long timeLeft = intent.getLongExtra("timeRmind", 0);
				showTimeLeft.setText(longToTime(timeLeft).toString());
			}
//			System.out.println(timeLeftMsg);
			if(intent.hasExtra("progressMessage")){
				int tPos = intent.getIntExtra("progressMessage", 0);
				System.out.println(tPos);
				seekBar.setProgress(tPos);
			}
		}
		
	}
	
	public Time longToTime(long offset){
		Time time = new Time(0);
		offset /= 1000;
		long hour = 0, min = 0, sec = 0;
		sec = offset % 60;
		min = offset / 60 % 60;
		hour = offset / 3600;
		time.setHours((int)hour);
		time.setMinutes((int)min);
		time.setSeconds((int)sec);
		return time;
	}
}
