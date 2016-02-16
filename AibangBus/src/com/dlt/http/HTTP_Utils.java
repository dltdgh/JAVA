package com.dlt.http;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;


public class HTTP_Utils {
	
	private HttpClient client = null;
	
	public HTTP_Utils() {
		this.client = createHttpClient();
	}
	
	public HttpClient createHttpClient(){
		HttpClient client = null;
		HttpClientBuilder builder = HttpClientBuilder.create();
		client = builder.build();
		return client;
	}
	
	public HttpGet createHttpGet(String uri, Map<String, String> params){
		
		String url = uri+"?";
		for(Map.Entry<String, String> entry : params.entrySet()){
			if(url.endsWith("?")){
				url = url+entry.getKey()+"="+entry.getValue();
			}
			else {
				url = url+"&"+entry.getKey()+"="+entry.getValue();
			}
		}
		System.out.println(url);
		HttpGet httpGet = new HttpGet(url);
		return httpGet;
	}
	
	public HttpGet createHttpGet(String uri){
		HttpGet httpGet = new HttpGet(uri);
		return httpGet;
	}
	
	public HttpResponse excuteGet(HttpGet httpGet) {
		HttpResponse response = null;
		try {
			 response = client.execute(httpGet);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
	
	public String getContentFromResponse(HttpResponse response) {
		HttpEntity entity = response.getEntity();
		String temp = null;
		try {
			temp = getStringFromInputstream(entity.getContent());
		} catch (UnsupportedOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return temp;
	}
	
	public String getContentFromHttpGet(HttpGet httpGet){
		HttpResponse response = excuteGet(httpGet);
		String content = getContentFromResponse(response);
		return content;
	}
	
	public String getContentFromURI(String uri){
		HttpGet httpGet = createHttpGet(uri);
		String temp = getContentFromHttpGet(httpGet);
		return temp;
	}
	
	public String getContentFromURI(String uri, Map<String, String> params){
		HttpGet httpGet = createHttpGet(uri, params);
		String temp = getContentFromHttpGet(httpGet);
		return temp;
	}
	
	public String getStringFromInputstream(InputStream in){
		StringBuffer sb = new StringBuffer();
		try {
			Scanner sc = new Scanner(in);
			while (sc.hasNext()) {
				sb.append(sc.nextLine()+"\n");
			}
			sc.close();
		} catch (UnsupportedOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		HTTP_Utils utils = new HTTP_Utils();
		String API_KEY = "8f2d8f22e79cffc5dea50a225eedc6cb";
		Map<String, String> params = new HashMap<String, String>();
		params.put("app_key", API_KEY);
		params.put("city", "青岛");
		params.put("q", "227");
		params.put("alt", "json");
		String content = utils.getContentFromURI("http://openapi.aibang.com/bus/lines", params);
		System.out.println(content);
	}
}
