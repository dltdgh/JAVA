package com.dlt.utils;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/*
 * 单任务多线程断点续传下载
 * */

public class MultyThreadsDownloader {
	private static final Integer TIME_OUT = 5000;
//	private String fileName = null;
	private final Integer THREAD_NUM = 5;
	private DownloadThread[] threads = new DownloadThread[THREAD_NUM];
	private Integer fileSize = null;
//	private String savePath = null;
//	private String fileUrl = null;
	private URL url = null;
	private File file = null;
	
	MultyThreadsDownloader(){
		for (int i = 0; i < THREAD_NUM; i++) {
			threads[i] = new DownloadThread();
		}
	}
	
	/**
	 * 获取文件长度并设置每一个thread对象的下载区间
	 * @param fileUrl 服务器文件路径
	 * @param savePath 文件保存路径
	 */
	
	public void setDownloadInfo(String fileUrl, String savePath){
		
	//	this.fileUrl = fileUrl;
	//	this.savePath = savePath;
		
		try {
			url = new URL(fileUrl);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setConnectTimeout(TIME_OUT);
			conn.setRequestMethod("GET");
			conn.setDoOutput(true);
			fileSize = conn.getContentLength();
			//System.out.println(fileSize);
	//		fileName = getNameFromUrl(fileUrl);
			
			file = new File(savePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int blockSize = (fileSize % THREAD_NUM == 0) ? (fileSize/THREAD_NUM) : (fileSize/THREAD_NUM+1);
		
		for(int i = 0; i < THREAD_NUM; i++){
			int tStart = i*blockSize;
			int tEnd = tStart+blockSize-1;
			if (tEnd > fileSize) {
				tEnd = fileSize;
			}
			threads[i].setInfos(tStart, tEnd);
		}
	}
	
	/*
	 * 调用threads进行下载
	 */
	
	public void download() {
		try {
			RandomAccessFile accessFile = new RandomAccessFile(file, "rwd");
			accessFile.setLength(fileSize);
			accessFile.close();
			for (int i = 0; i < THREAD_NUM; i++) {
				threads[i].start();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 下载线程类
	 * @author Administrator
	 *
	 */
	private class DownloadThread extends Thread{
		private Integer start = 0;
		private Integer end = 0;
//		private Integer pos = 0;
		
		public void setInfos(int start, int end) {
			this.start = start;
			this.end = end;
//			this.pos = this.start;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				HttpURLConnection conn = (HttpURLConnection)url.openConnection();
				conn.setRequestMethod("GET");
				conn.setConnectTimeout(TIME_OUT);
				conn.setRequestProperty("Range", "bytes="+start+"-"+end);
				if (conn.getResponseCode() == 206) {
					RandomAccessFile accessFile = new RandomAccessFile(file, "rwd");
					accessFile.seek(start);
			//		System.out.println(conn.getResponseCode());
					InputStream in = conn.getInputStream();
					int n = 0;
					byte[] bytes = new byte[1024];
					while ((n = in.read(bytes)) > 0) {
						accessFile.write(bytes, 0, n);
					}
					accessFile.close();
					in.close();
					System.out.println("thread "+start+" complete!");
				}
				else {
					System.out.println("thread "+start+" failed!");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	/*private String getNameFromUrl(String url){
		return url.substring(url.lastIndexOf('/')+1);
	}*/
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MultyThreadsDownloader downloader = new MultyThreadsDownloader();
		downloader.setDownloadInfo("http://localhost:8080/WebTest/test.mp3", "test.mp3");
		downloader.download();
	}
}
