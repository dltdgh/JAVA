package com.dlt.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.dlt.pojo.DownloadInfo;

public class HttpDownloader {
	
	private HttpService httpService = null;
	private ExecutorService fixedThreadPool = null;
	private List<DownloadInfo> infos = null;
	
	public HttpDownloader(){
	}
	
	public void setDownloadInfos(List<DownloadInfo> infos){
		this.infos = infos;
	}
	
	public void download(){
		fixedThreadPool = Executors.newFixedThreadPool(5);
		for (DownloadInfo info : infos) {
			fixedThreadPool.execute(new DownloadThread(info));
		}
		fixedThreadPool.shutdown();
	}
	
	public List<DownloadInfo> getDownloadInfos() {
		return infos;
	}
	
	private class DownloadThread implements Runnable{
		
		DownloadInfo info = null;
		
		public DownloadThread(DownloadInfo info){
			this.info = info;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			httpService = new HttpService();
			//System.out.println(info.getUri());
			info.setFileLength(httpService.downloadFile(info.getUri(), null, info.getFilePath()));
		}
	}
	
	public static void main(String[] args) throws Exception {
		List<DownloadInfo> infos = new ArrayList<DownloadInfo>();
		DownloadInfo info = null;
		File file = new File("./img");
		file.mkdir();
		info = new DownloadInfo("http://mm.xmeise.com/uploads/allimg/160204/1-160204195029.jpg", "./img/1-160204195029.jpg");
		infos.add(info);
		info = new DownloadInfo("http://mm.xmeise.com/uploads/allimg/160204/1-160204195031.jpg", "./img/1-160204195031.jpg");
		infos.add(info);
		info = new DownloadInfo("http://mm.xmeise.com/uploads/allimg/160204/1-160204195033.jpg", "./img/1-160204195033.jpg");
		infos.add(info);
		info = new DownloadInfo("http://mm.xmeise.com/uploads/allimg/160204/1-160204195034.jpg", "./img/1-160204195034.jpg");
		infos.add(info);
		info = new DownloadInfo("http://mm.xmeise.com/uploads/allimg/160204/1-160204195036.jpg", "./img/1-160204195036.jpg");
		infos.add(info);
		info = new DownloadInfo("http://mm.xmeise.com/uploads/allimg/160204/1-160204195038.jpg", "./img/1-160204195038.jpg");
		infos.add(info);
		info = new DownloadInfo("http://mm.xmeise.com/uploads/allimg/160204/1-160204195040.jpg", "./img/1-160204195040.jpg");
		infos.add(info);
		info = new DownloadInfo("http://mm.xmeise.com/uploads/allimg/160204/1-160204195041.jpg", "./img/1-160204195041.jpg");
		infos.add(info);
		info = new DownloadInfo("http://mm.xmeise.com/uploads/allimg/160204/1-160204195042.jpg", "./img/1-160204195042.jpg");
		infos.add(info);
		info = new DownloadInfo("http://mm.xmeise.com/uploads/allimg/160204/1-160204195044.jpg", "./img/1-160204195044.jpg");
		infos.add(info);
		HttpDownloader downloader = new HttpDownloader();
		downloader.setDownloadInfos(infos);
		downloader.download();
		while (true) {
			if (downloader.fixedThreadPool.isTerminated()) {
				break;
			}
			System.out.println("running...");
			Thread.sleep(300);
		}
		System.out.println("ended!");
	}
}
