package com.example.mp3player;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.entity.InputStreamEntity;

public class HttpDownloader {
	
	private URL url = null;
	
	public String download(String urlStr){
//		System.out.println(urlStr);
		StringBuffer sb = new StringBuffer();
		String line = null;
		BufferedReader buffer = null;
		try {
			URL url = new URL(urlStr);
			HttpURLConnection urlconn = (HttpURLConnection)url.openConnection();
			
			urlconn.connect();
			
			buffer = new BufferedReader(new InputStreamReader(urlconn.getInputStream()));
			
			while((line = buffer.readLine()) != null){
				sb.append(line);
//				System.out.println(line);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally{
			try {
				buffer.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
	public int downloadFile(String urlStr, String path, String fileName){
		
		InputStream inputStream = null;
		try {
			FileUtils fileUtils = new FileUtils();
			if(fileUtils.isFileExist(fileName, path)){
				return 1;
			}else {
				inputStream = getInputStreamFromUrl(urlStr);
				File resultFile = fileUtils.writeToSDFromInput(path, fileName, inputStream);
				if(resultFile == null){
					return -1;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return -1;
		}
		
		return 0;
	}
	
	public InputStream getInputStreamFromUrl(String urlStr){
		try {
			url = new URL(urlStr);
			HttpURLConnection urlConn = (HttpURLConnection)url.openConnection();
			InputStream inputStream = urlConn.getInputStream();
			return inputStream;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
}
