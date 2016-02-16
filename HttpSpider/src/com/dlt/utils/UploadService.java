package com.dlt.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

public class UploadService {
	private static final Integer TIME_OUT = 10000;
	private static final String CHARSET = "UTF-8";
	private static final String PREFIX = "--";
	private static final String LINE_END = "\r\n";
	private static final String CONTENT_TYPE = "multipart/form-data";
	
	public static boolean uploadFile(String requestUrl, String filePath){
		String BOUNDARY = UUID.randomUUID().toString();
		File file = new File(filePath);
		URL url;
		try {
			url = new URL(requestUrl);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setReadTimeout(TIME_OUT);
			conn.setConnectTimeout(TIME_OUT);
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Charset", CHARSET);
			conn.setRequestProperty("connection", "keep-alive");
			conn.setRequestProperty("Content-Type", CONTENT_TYPE+";boundary="+BOUNDARY);
			if (file.exists()) {
				OutputStream out = conn.getOutputStream();
				InputStream in = new FileInputStream(file);
				
				StringBuffer sb = new StringBuffer();
				sb.append(PREFIX);
				sb.append(BOUNDARY);
				sb.append(LINE_END);
				sb.append("Content-Disposition: form-data; name=\"file\"; filename=\""  
                        + file.getName() + "\"" + LINE_END);
				sb.append("Content-Type: application/octet-stream; charset="  
                        + CHARSET + LINE_END);
				sb.append(LINE_END);
				
				System.out.println(sb.toString());
				out.write(sb.toString().getBytes());
				
				byte[] bytes = new byte[1024];
				int n = 0;
				while ((n = in.read(bytes)) > 0) {
					out.write(bytes, 0, n);
				}
				in.close();
				out.write((LINE_END+PREFIX+BOUNDARY+PREFIX+LINE_END).getBytes());
				
				out.flush();
				out.close();
				
				if (conn.getResponseCode() == 200) {
					return true;
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return false;
	}
	public static void main(String[] args) {
		System.out.println(UUID.randomUUID().toString());
		/*boolean res = uploadFile("http://localhost:8080/WebTest/TestServlet", "zhahu.jpg");
		System.out.println(res);*/
	}
}
