package com.dlt.utils;

import java.io.File;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;


/*
 * 单任务多线程断点续传下载
 * */

public class MultyThreadsDownloader {
	private static final Integer TIME_OUT = 5000;   //连接延时
//	private String fileName = null;
	private final Integer THREAD_NUM = 5;    //线程数量
	private DownloadThread[] threads = new DownloadThread[THREAD_NUM];  //保存线程的数组
	private Integer fileSize = null;    //要下载的文件大小
	private Integer readySize = 0;      //已下载文件大小
//	private String savePath = null;
//	private String fileUrl = null;
	private URL url = null;             //下载文件路径
	private File file = null;           //保存下载文件
	private File logFile = null;        //保存断点文件
	
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
			logFile = getLogFile(savePath);
			
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
	
	/**
	 * 调用threads进行下载
	 */
	
	public void download() {
		if (fileSize > 0) {	
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
		else {
			System.out.println("文件不存在");
		}
	}
	
	/**
	 * 下载线程类
	 * @author Administrator
	 *
	 */
	private class DownloadThread extends Thread{
		private Integer start = 0;           //起始位置
		private Integer end = 0;			//结束位置
		private Integer pos = 0;			//当前位置
		private boolean isReady = false;	//是否下载完成
		private boolean isStoped = false;	//是否被外部停止
		
		public Integer getEnd() {
			return end;
		}

		public Integer getPos() {
			return pos;
		}

		public boolean isReady() {
			return isReady;
		}
		
		public void setReady(boolean isReady) {
			this.isReady = isReady;
		}
		
		public void setInfos(int start, int end) {
			this.start = start;
			this.end = end;
			this.pos = this.start;
		}
		
		/*public boolean getReady(){
			return isReady;
		}*/
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				HttpURLConnection conn = (HttpURLConnection)url.openConnection();
				conn.setRequestMethod("GET");
				conn.setConnectTimeout(TIME_OUT);
				conn.setRequestProperty("Range", "bytes="+start+"-"+end);         //获取需求区间内容
				if (conn.getResponseCode() == 206) {
					RandomAccessFile accessFile = new RandomAccessFile(file, "rwd");    //立即读写
					accessFile.seek(start);                                 //定位写入位置
			//		System.out.println(conn.getResponseCode());
					InputStream in = conn.getInputStream();
					int n = 0;
					byte[] bytes = new byte[1024];
					while ((n = in.read(bytes)) > 0 && !isStoped) {
						pos = pos+n;										//更改当前下载位置
						synchronized (readySize) {                          //完成数量互斥访问写入
							readySize = readySize+n;
						}
						accessFile.write(bytes, 0, n);
					}
					accessFile.close();
					in.close();
		//			System.out.println(pos+" "+end);
					if (pos >= end) {                                 //下载结束
						isReady = true;
						System.out.println("thread "+start+" complete!");
					}
					else {											//由外部停止，且未下载完成
						System.out.println("thread "+start+" stoped!");
					}
				}
				else {
					System.out.println("thread "+start+" failed!");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		
		public void stopThread() {
			isStoped = true;                     //修改停止进程位，停止进程
		}
	}
	
	/**
	 * 停止各个进程,并保存断点
	 */
	public void stopDownload(){
		for (int i = 0; i < THREAD_NUM; i++) {
			threads[i].stopThread();
		}
		writeLog();
	}
	
	/**
	 * 回复下载现场
	 * @param logPath 保存断点的文件路径
	 */
	
	public void continueDownload(String logPath){
		
		try {
			/*初始化类内数据*/
			Scanner sc = new Scanner(new File(logPath));
			String tUrl = sc.nextLine();
			this.url = new URL(tUrl);   
			String tFilePath = sc.nextLine();
			this.file = new File(tFilePath);
			this.logFile = new File(logPath);
			this.fileSize = sc.nextInt();
			this.readySize = sc.nextInt();
			for (int i = 0; i < THREAD_NUM; i++) {
				//设置线程信息
				threads[i].setReady(sc.nextBoolean());
				int tStart = sc.nextInt();
				int tEnd = sc.nextInt();
				threads[i].setInfos(tStart, tEnd);
				if (!threads[i].isReady) {
					threads[i].start();
				}
			}
			sc.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取已下载文件大小
	 * @return 已下载文件大小
	 */
	public int getReadySize(){
		return this.readySize;
	}
	
	/*private String getNameFromUrl(String url){
		return url.substring(url.lastIndexOf('/')+1);
	}*/
	
	/**
	 * 由文件保存路径获取同文件夹下的保存断点文件路径
	 * @param path
	 * @return
	 */
	private File getLogFile(String path){
		String tPath = path.substring(0, path.lastIndexOf('.')+1)+"log";
		return new File(tPath);
	}
	
	/**
	 * 将断点数据写入文件
	 */
	
	public void writeLog(){
		try {
			PrintWriter pw = new PrintWriter(logFile);
			pw.println(url.toString());
			pw.println(file.getAbsolutePath());
			pw.println(fileSize+" "+readySize);
			for (int i = 0; i < THREAD_NUM; i++) {
				pw.println(threads[i].isReady()+" "+threads[i].getPos()+" "+threads[i].getEnd());
			}
			pw.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MultyThreadsDownloader downloader = new MultyThreadsDownloader();
		downloader.setDownloadInfo("http://localhost:8080/WebTest/test.mp3", "test.mp3");
		downloader.download();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(downloader.getReadySize());
		downloader.stopDownload();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		downloader = new MultyThreadsDownloader();
		downloader.continueDownload("test.log");
		try {
			Thread.sleep(10000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		downloader.writeLog();
		/*try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		downloader.stopDownload();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		downloader = new MultyThreadsDownloader();
		downloader.continueDownload("test.log");
		downloader.writeLog();*/
	}
}
