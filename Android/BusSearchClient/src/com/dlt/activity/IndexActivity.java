package com.dlt.activity;

import com.dlt.BusSearchClient.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;

public class IndexActivity extends Activity {
	
	private static final int JUMP_FLAG = 1;
	
	MyHandler handler = new MyHandler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//设置去除标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//设置不显示状态栏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_index);
		
		//分配线程加载所需资源
		new Thread(new LoadThread()).start();
	}
	
	/*
	 * 加载线程
	 */
	private class LoadThread implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Message msg = new Message();
			msg.arg1 = JUMP_FLAG;
			handler.sendMessage(msg);
		}
	}
	
	/**
	 * 加载完成后实现跳转
	 * @author Administrator
	 *
	 */
	
	@SuppressLint("HandlerLeak")
	private class MyHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if (msg.arg1 == JUMP_FLAG) {
				Intent intent = new Intent(getApplicationContext(), MainActivity.class);
				startActivity(intent);
				finish();
			}
		}
	}
}
