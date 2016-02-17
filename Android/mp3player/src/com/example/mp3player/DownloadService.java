package com.example.mp3player;

import com.example.mp3player.R.string;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class DownloadService extends Service{

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Mp3Info mp3Info = (Mp3Info)intent.getSerializableExtra("mp3Info");
//		System.out.println("service->"+mp3Info.toString());
		DownloadThread downloadThread = new DownloadThread(mp3Info);
		Thread thread = new Thread(downloadThread);
		thread.start();
		return super.onStartCommand(intent, flags, startId);
	}
	
	class DownloadThread implements Runnable{
		
		private Mp3Info mp3Info = null;
		public DownloadThread(Mp3Info tmp3Info) {
			// TODO Auto-generated constructor stub
			this.mp3Info = tmp3Info;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			String mp3UrlStr = AppConstant.URL.BASE_URL+mp3Info.getMp3Name();
			String lrcUrlStr = AppConstant.URL.BASE_URL+mp3Info.getLrcName();
			HttpDownloader httpDownloader = new HttpDownloader();
			int mp3Result = httpDownloader.downloadFile(mp3UrlStr, "mp3/", mp3Info.getMp3Name());		
			int lrcResult = httpDownloader.downloadFile(lrcUrlStr, "mp3/", mp3Info.getLrcName());
			
			
//			System.out.println(mp3Result+" "+lrcResult);
			//String resultMessage = null;
			
			/*if(result == -1){
				resultMessage = "下载失败！";
			}
			else if(result == 0){
				resultMessage = "文件下载成功！";
			}
			else if(result == 1){
				resultMessage = "文件已存在！";
			}*/
//			System.out.println(resultMessage);		
//			Toast.makeText(, resultMessage, Toast.LENGTH_SHORT);
		}
		
	}
}
