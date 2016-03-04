package com.dlt.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;

public class HttpService {
	
	private HttpClient client = null;
	
	private static final Integer TIME_OUT = 10000;
	private static final String CHARSET = "UTF-8";
	private static final String PREFIX = "--";
	private static final String LINE_END = "\r\n";
	private static final String CONTENT_TYPE = "multipart/form-data";
	
	
	public HttpService(){
		this.client = createHttpClient();
	}
	
	/*public static HttpService getInstance() {
		if (httpService == null) {
			httpService = new HttpService();
		}
		return httpService;
	}
	 */	
	public HttpClient createHttpClient(){
		HttpClient client = null;
		client = HttpClientBuilder.create().build();
		return client;
	}
	
	public HttpGet createHttpGet(String uri, Map<String, String> params) {
		String url = uri;
		if(params != null){
			uri = uri+"?";
			for (Map.Entry<String, String> entry : params.entrySet()) {
				if (!url.endsWith("?")) {
					url = url + "&";
				}
				url = url + entry.getKey() + "=" + entry.getValue();
			}
		}
		System.out.println(url);
		HttpGet httpGet = new HttpGet(url);
		httpGet = addHeaderToHttpGet(httpGet, null);
		return httpGet;
	}
	
	public HttpGet addHeaderToHttpGet(HttpGet httpGet, Map<String, String> headers) {
		if (headers == null) {
			httpGet.setHeader("Accept", "Accept text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
	        httpGet.setHeader("Accept-Charset", "utf-8;q=0.7,*;q=0.7");
	        httpGet.setHeader("Accept-Encoding", "gzip, deflate");
	        httpGet.setHeader("Accept-Language", "zh-cn,zh;q=0.5");
	        httpGet.setHeader("Connection", "keep-alive");
	        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.97 Safari/537.36");
		}
		return httpGet;
	}
	
	public HttpResponse excuteHttpGet(HttpGet httpGet) {
		HttpResponse response = null;
		try {
			response = client.execute(httpGet);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return response;
	}
	
	public String getContentFromResponse(HttpResponse response){
		String content = null;
		//String charset = "utf-8";
		HttpEntity entity = response.getEntity();
		ContentType contentType = ContentType.getOrDefault(entity);
//		System.out.println(content);
		Charset charset = contentType.getCharset();
		if (charset == null) {
			charset = Charset.defaultCharset();
		}
//		System.out.println(entity.getContentEncoding()+" "+entity.getContentType()+" "+charset.toString());
		try {
			content = getStringFromInputstream(entity.getContent(), charset.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return content;
	}
	
	public InputStream getInputStreamFromResponse(HttpResponse response){
		InputStream in= null;
		HttpEntity entity = response.getEntity();
		ContentType contentType = ContentType.getOrDefault(entity);
		Charset charset = contentType.getCharset();
		if (charset == null) {
			charset = Charset.defaultCharset();
		}
		try {
			in = entity.getContent();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return in;
	}
	
	public String getContentFromHttpGet(HttpGet httpGet) {
		HttpResponse response = excuteHttpGet(httpGet);
		String content = getContentFromResponse(response);
		return content;
	}
	
	public String getContentFromURI(String uri, Map<String, String> params) {
		HttpGet httpGet = createHttpGet(uri, params);
		String content = getContentFromHttpGet(httpGet);
		return content;
	}
	
	public String getStringFromInputstream(InputStream in, String charset) {
		Scanner scanner = new Scanner(in, charset);
		String temp = "";
		while (scanner.hasNext()) {
			temp = temp + scanner.nextLine() + "\n";
		}
		scanner.close();
		/*try {
			temp = new String(temp.getBytes(), Charset.forName("utf-8"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return temp;
	}
	
	public int downloadFile(String uri, Map<String, String> params, String filePath) {
		HttpGet httpGet = createHttpGet(uri, params);
		HttpResponse response = excuteHttpGet(httpGet);
		InputStream in = getInputStreamFromResponse(response);
		int len = 0;
		try {
			/*FileService.prepareFile(filePath);*/
			FileOutputStream fos = new FileOutputStream(new File(filePath));
			byte[] buffer = new byte[1024];
			int n = 0;
			while ((n = in.read(buffer)) > 0) {
				len += n;
				fos.write(buffer, 0, n);
			}
			fos.close();
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return len;
	}
	
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
		HttpService service = new HttpService();
		
		//String content = service.getContentFromURI("http://mm.xmeise.com/rufang/xinggan/5596.html", null);
		//System.out.println(content);
		System.out.println(service.downloadFile("http://mm.xmeise.com/rufang/xinggan/5596.html", null, "d://5596.html"));
	}
}
