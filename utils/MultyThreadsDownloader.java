package com.dlt.utils;

import java.io.File;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;


/*
 * ��������̶߳ϵ���������
 * */

public class MultyThreadsDownloader {
	private static final Integer TIME_OUT = 5000;   //������ʱ
//	private String fileName = null;
	private final Integer THREAD_NUM = 5;    //�߳�����
	private DownloadThread[] threads = new DownloadThread[THREAD_NUM];  //�����̵߳�����
	private Integer fileSize = null;    //Ҫ���ص��ļ���С
	private Integer readySize = 0;      //�������ļ���С
//	private String savePath = null;
//	private String fileUrl = null;
	private URL url = null;             //�����ļ�·��
	private File file = null;           //���������ļ�
	private File logFile = null;        //����ϵ��ļ�
	
	MultyThreadsDownloader(){
		for (int i = 0; i < THREAD_NUM; i++) {
			threads[i] = new DownloadThread();
		}
	}
	
	/**
	 * ��ȡ�ļ����Ȳ�����ÿһ��thread�������������
	 * @param fileUrl �������ļ�·��
	 * @param savePath �ļ�����·��
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
	 * ����threads��������
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
			System.out.println("�ļ�������");
		}
	}
	
	/**
	 * �����߳���
	 * @author Administrator
	 *
	 */
	private class DownloadThread extends Thread{
		private Integer start = 0;           //��ʼλ��
		private Integer end = 0;			//����λ��
		private Integer pos = 0;			//��ǰλ��
		private boolean isReady = false;	//�Ƿ��������
		private boolean isStoped = false;	//�Ƿ��ⲿֹͣ
		
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
				conn.setRequestProperty("Range", "bytes="+start+"-"+end);         //��ȡ������������
				if (conn.getResponseCode() == 206) {
					RandomAccessFile accessFile = new RandomAccessFile(file, "rwd");    //������д
					accessFile.seek(start);                                 //��λд��λ��
			//		System.out.println(conn.getResponseCode());
					InputStream in = conn.getInputStream();
					int n = 0;
					byte[] bytes = new byte[1024];
					while ((n = in.read(bytes)) > 0 && !isStoped) {
						pos = pos+n;										//���ĵ�ǰ����λ��
						synchronized (readySize) {                          //��������������д��
							readySize = readySize+n;
						}
						accessFile.write(bytes, 0, n);
					}
					accessFile.close();
					in.close();
		//			System.out.println(pos+" "+end);
					if (pos >= end) {                                 //���ؽ���
						isReady = true;
						System.out.println("thread "+start+" complete!");
					}
					else {											//���ⲿֹͣ����δ�������
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
			isStoped = true;                     //�޸�ֹͣ����λ��ֹͣ����
		}
	}
	
	/**
	 * ֹͣ��������,������ϵ�
	 */
	public void stopDownload(){
		for (int i = 0; i < THREAD_NUM; i++) {
			threads[i].stopThread();
		}
		writeLog();
	}
	
	/**
	 * �ظ������ֳ�
	 * @param logPath ����ϵ���ļ�·��
	 */
	
	public void continueDownload(String logPath){
		
		try {
			/*��ʼ����������*/
			Scanner sc = new Scanner(new File(logPath));
			String tUrl = sc.nextLine();
			this.url = new URL(tUrl);   
			String tFilePath = sc.nextLine();
			this.file = new File(tFilePath);
			this.logFile = new File(logPath);
			this.fileSize = sc.nextInt();
			this.readySize = sc.nextInt();
			for (int i = 0; i < THREAD_NUM; i++) {
				//�����߳���Ϣ
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
	 * ��ȡ�������ļ���С
	 * @return �������ļ���С
	 */
	public int getReadySize(){
		return this.readySize;
	}
	
	/*private String getNameFromUrl(String url){
		return url.substring(url.lastIndexOf('/')+1);
	}*/
	
	/**
	 * ���ļ�����·����ȡͬ�ļ����µı���ϵ��ļ�·��
	 * @param path
	 * @return
	 */
	private File getLogFile(String path){
		String tPath = path.substring(0, path.lastIndexOf('.')+1)+"log";
		return new File(tPath);
	}
	
	/**
	 * ���ϵ�����д���ļ�
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
