package com.dlt.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.dlt.pojo.DownloadInfo;

public class HttpSpider {
	
	private String infoRules = null;
	private String searchRules = null;
	private String startUrl = null;
	private String baseUrl = null;
	private List<String> results = null;
	private Queue<String> urlQueue = null;
	private boolean ended = false;
	
	private ExecutorService fixedThreadPool = null;
	
	private HttpDownloader downloader = null;
	
	public HttpSpider(){
		downloader = new HttpDownloader();
	}
	
	public void prepare(String startUrl, String baseUrl, String infoRules, String searchRules) {
		this.startUrl = startUrl;
		this.baseUrl = baseUrl;
		this.infoRules = infoRules;
		this.searchRules = searchRules;
		this.results = new ArrayList<String>();
		this.urlQueue = new LinkedList<String>();
		this.fixedThreadPool = Executors.newFixedThreadPool(5);
		this.ended = false;
	}
	
	public void Grab() {
		writeLog();
		String url = startUrl;
		urlQueue.add(url);
		
		while (!ended || urlQueue.size() > 0) {
			if (urlQueue.size() > 0) {
				url = urlQueue.remove();
		//		System.out.println(url);
				/*GrabThread grabThread = new GrabThread(url, null);
				Thread thread = new Thread(grabThread);
				thread.start();*/
				fixedThreadPool.execute(new GrabThread(url, null));
			}
			else {
				try {
					Thread.sleep(300);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		fixedThreadPool.shutdown();
	}
	
	public String downloadPage(String uri, Map<String, String> params){
		
		HttpService httpService = new HttpService();
		String content = null;
		content = httpService.getContentFromURI(uri, params);
		return content;
	}
	
	public void parsePage(String content) {
		
		XPathService xPathService = new XPathService();
		xPathService.loadResource(content);
		
		List<String> infoList = xPathService.getContentsByEx(infoRules);
		synchronized (results) {	
			for (String tempInfo : infoList) {
				System.out.println(tempInfo);
				results.add(tempInfo);
			}
		}
		
		List<String> urlList = xPathService.getContentsByEx(searchRules);
		if (urlList.size() == 0) {
			ended = true;
		}
		synchronized (urlQueue) {
			for (String tempUrl : urlList) {
				tempUrl = getRealUrl(tempUrl);
				urlQueue.add(tempUrl);
			}
		}
	}
	
	public String getRealUrl(String url) {
		String tUrl = null;
		if(url.startsWith("http://")){
			tUrl = url;
		}
		else if (url.startsWith("/")) {
			tUrl = baseUrl + url;
		}
		else {
			tUrl = startUrl;
			int end = tUrl.lastIndexOf('/');
			tUrl = tUrl.substring(0, end) + "/" + url;
		}
		if(!tUrl.startsWith("http://")){
			tUrl = null;
		}
		return tUrl;
	}
	
	public List<String> getResults() {
		return results;
	}

	public void writeResultsToFile(String filePath) {
		FileService fileService = new FileService(filePath);
		String content = "";
		for (String result : results) {
			content = content + result + "\n";
		}
//		System.out.println(content);
		fileService.writeToFile(content);
	}
	
	class GrabThread implements Runnable{
		
		String uri = null;
		Map<String, String> params = null;
		
		public GrabThread(String uri, Map<String, String> params){
			this.uri = uri;
			this.params = params;
		}
		public void run() {
			// TODO Auto-generated method stub
			String content = downloadPage(uri, params);
			parsePage(content);
		}
	}
	
	public void writeLog() {
		FileService fileService = new FileService("HttpSpider.log");
		String date = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		date = format.format(new Date());
		fileService.appendToFile(date+"\n"+startUrl+"\n"+baseUrl+"\n"+infoRules+"\n"+searchRules+"\n\n");
	}
	
	public void downloadFiles(String fileDir){
		
		String uri = null;
		DownloadInfo info = null;
		List<DownloadInfo> infos = new ArrayList<DownloadInfo>();
		
		for (String result : results) {
			uri = getRealUrl(result);
			if (uri != null) {
				String fileName = FileService.getFileNameFromPath(uri);
				info = new DownloadInfo(uri, fileDir+fileName);
				infos.add(info);
			}
		}
		
		downloader.setDownloadInfos(infos);
		downloader.download();
	}
	
	public static void main(String[] args) {
		HttpSpider spider = new HttpSpider();
		spider.prepare("http://blog.csdn.net/u012629369/article/details/13775439", "http://blog.csdn.net", "//div[@id='article_content']/text()", "//li[@class='prev_article']/a/@href");
		/*spider.Grab();
		spider.writeResultsToFile("results.txt");*/
		System.out.println(spider.getRealUrl("http://12345"));
	}
}
