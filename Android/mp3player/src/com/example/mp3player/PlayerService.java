package com.example.mp3player;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Random;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;

public class PlayerService extends Service {
	
	private boolean isPlaying = false;
	private boolean isPause = false;
	private boolean isReleased = false;
	private MediaPlayer mediaPlayer = null;	
	private long begin = 0;
	private long nextTimeMill = 0;
	private long currentTimeMill = 0;
	private long maxTime = 0;
	private long nowTime = 0;
	private String message = null;
	private long pauseTimeMills = 0;
	private ArrayList<Queue> queues = null;
	private Handler handler = new Handler();
	private UpdateTimeCallBack updateTimeCallBack = null;
	private boolean isHaveLrc = false;
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Mp3Info mp3Info = (Mp3Info)intent.getSerializableExtra("mp3Info");
		int MSG = intent.getIntExtra("MSG", 0);
//		System.out.println(MSG);
		if(mp3Info != null){
			if(MSG == AppConstant.PlayerMsg.PLAY_MSG){
				play(mp3Info);
			}
			else {
				if (MSG == AppConstant.PlayerMsg.PAUSE_MSG) {
					pause();
				} else if (MSG == AppConstant.PlayerMsg.STOP_MSG) {
					stop();
				}
			}
		}
		else {
			if(MSG == AppConstant.PlayerMsg.CHANGE_MSG){
	//			System.out.println("Change received!");
				proChange(intent.getIntExtra("proPos", 0));
			}
		}
		
		return super.onStartCommand(intent, flags, startId);
	}
	
	private void proChange(int pos){
		if (mediaPlayer != null) {
			int tPos = (int)(pos*1.0/100*maxTime);
	//		System.out.println(tPos);
			mediaPlayer.seekTo(tPos);
			begin = System.currentTimeMillis()-tPos;
		}
	}
	
	private void play(Mp3Info mp3Info){
//		System.out.println("player play");
		if(!isPlaying){
			if (mediaPlayer != null) {
				mediaPlayer.release();
			}
			String path = getMp3Path(mp3Info);
			mediaPlayer = MediaPlayer.create(this,Uri.parse("file://"+path));
			maxTime = mediaPlayer.getDuration();
			
			mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
				
				@Override
				public void onCompletion(MediaPlayer mp) {
					// TODO Auto-generated method stub
					isPlaying = false;
					isPause = false;
					isReleased = true;
					begin = 0;
					handler.removeCallbacks(updateTimeCallBack);
				
					Intent intent = new Intent();
					intent.putExtra("lrcMessage", "播放完成");
					intent.setAction(AppConstant.LRC_MESSAGE_ACTION);
					sendBroadcast(intent);
					
					Intent tIntent = new Intent();
					tIntent.setAction(AppConstant.TIME_MESSAGE_ACTION);
					tIntent.putExtra("timeMessage", longToTime(0).toString());
					tIntent.putExtra("progressMessage", 0);
					sendBroadcast(tIntent);
				
					mp.release();
				}
			});
			
			if(mp3Info.getLrcName() != null) {
				
				isHaveLrc = true;
				prepareLrc(mp3Info.getLrcName());
				currentTimeMill = 0;
				begin = System.currentTimeMillis();
				handler.postDelayed(updateTimeCallBack, 5);
				
			}
			else{
				isHaveLrc = false;
				begin = System.currentTimeMillis();
				updateTimeCallBack = new UpdateTimeCallBack(null);
				handler.postDelayed(updateTimeCallBack, 5);
				
			}
			
			mediaPlayer.setLooping(false);
			mediaPlayer.start();
			isPlaying = true;
			isReleased = false;
			isPause = false;
		}
	}
	
	private void pause(){
//		System.out.println("player pause");
		if (mediaPlayer != null) {
			if (!isReleased) {
				if (!isPause) {
					handler.removeCallbacks(updateTimeCallBack);
					pauseTimeMills = System.currentTimeMillis();
					mediaPlayer.pause();
					isPause = true;
					isPlaying = false;
				}
				else {
					handler.postDelayed(updateTimeCallBack, 5);
					begin = System.currentTimeMillis()-pauseTimeMills+begin;
					mediaPlayer.start();
					isPause = false;
					isPlaying = true;
				}
			}
		}
	}
	
	private void stop(){
//		System.out.println("player stop");
		if (mediaPlayer != null) {
			if(isPlaying){
				if(!isReleased){
					handler.removeCallbacks(updateTimeCallBack);
					mediaPlayer.stop();
					mediaPlayer.release();
					mediaPlayer = null;
					isPlaying = false;
					isReleased = true;
					begin = 0;
					
					Intent intent = new Intent();
					intent.putExtra("lrcMessage", "播放完成");
					intent.setAction(AppConstant.LRC_MESSAGE_ACTION);
					sendBroadcast(intent);
					
					Intent tIntent = new Intent();
					tIntent.setAction(AppConstant.TIME_MESSAGE_ACTION);
					tIntent.putExtra("timeMessage", longToTime(0).toString());
					tIntent.putExtra("progressMessage", 0);		
					sendBroadcast(tIntent);
				}
			}
		}
	}
	
	private void prepareLrc(String lrcName){
		try {
			InputStream inputStream = new FileInputStream(Environment.getExternalStorageDirectory().getAbsoluteFile() +File.separator + "mp3/" + lrcName);
	//		System.out.println(Environment.getExternalStorageDirectory().getAbsoluteFile() +File.separator + "mp3/" + lrcName);
			LrcProcessor lrcProcessor = new LrcProcessor();
	//		System.out.println(inputStream);
			queues = lrcProcessor.process(inputStream);
			
			updateTimeCallBack = new UpdateTimeCallBack(queues);
			
			begin = 0;
			nextTimeMill = 0;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}	
	}
	
	private String getMp3Path(Mp3Info mp3Info){
		
		String SDCardRoot = Environment.getExternalStorageDirectory().getAbsolutePath(); 
		String path = SDCardRoot+"/mp3/"+mp3Info.getMp3Name();
		return path;
	}
	
	class UpdateTimeCallBack implements Runnable{

		Queue times = null;
		Queue messages = null;
		
		public UpdateTimeCallBack(ArrayList<Queue> queues){
			if(queues != null){
				times = queues.get(0);
				messages = queues.get(1);
			}
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			long offset = System.currentTimeMillis()-begin;
			if(isHaveLrc){
				if(currentTimeMill == 0){
					nextTimeMill = (long)times.poll();
					message = (String)messages.poll();
				}
				if (offset >= nextTimeMill) {
					Intent intent = new Intent();
					intent.putExtra("lrcMessage", message);
					intent.setAction(AppConstant.LRC_MESSAGE_ACTION);
					sendBroadcast(intent);
					
					if (!times.isEmpty() && !messages.isEmpty()) {
						nextTimeMill = (long) times.poll();
						message = (String) messages.poll();
					}
					else {
						message = null;
					}
				}
				
			}
			
			Time time = null;
			time = longToTime(offset);
			
			Intent tIntent = new Intent();
			tIntent.setAction(AppConstant.TIME_MESSAGE_ACTION);
			tIntent.putExtra("timeMessage", time.toString());

			tIntent.putExtra("timeRmind", maxTime-offset);
			
			nowTime = mediaPlayer.getCurrentPosition();
//			System.out.println((int)nowTime*100/maxTime);
			int tPos = (int)(nowTime*100/maxTime);
			
//			System.out.println(tPos);
			
			tIntent.putExtra("progressMessage", tPos);
			
			sendBroadcast(tIntent);

			
			currentTimeMill += 100;
			handler.postDelayed(updateTimeCallBack, 100);
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
