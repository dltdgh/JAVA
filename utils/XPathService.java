package com.dlt.utils;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

public class XPathService {
	private HtmlCleaner cleaner = null;
	private TagNode root = null;
	
	public XPathService(){
		cleaner = new HtmlCleaner();
		root = new TagNode("");
	}
	
	public boolean loadResource(File file){
		try {
			root = cleaner.clean(file);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean loadResource(URL url){
		try {
			root = cleaner.clean(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean loadResource(String content){
		try {
			root = cleaner.clean(content);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public List<String> getContentsByEx(String expression) {
		List<String> list = new ArrayList<String>();
		try {
			Object[] objs = root.evaluateXPath(expression);
			for (Object obj : objs) {
				list.add(obj.toString());
			}
		} catch (XPatherException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public static void main(String[] args) {
		XPathService service = new XPathService();
		HttpService httpService = new HttpService();
		String content = httpService.getContentFromURI("http://mm.xmeise.com/rufang/xinggan/5596.html", null);
		service.loadResource(content);
//		System.out.println(content);
//		service.loadResource(new File("te.html"));
		List<String> list = service.getContentsByEx("//div[@id='big-pic']/a/img[@alt='推女神萝莉美女苏糯米海滩秀性感美乳高清图片']/@src");
		for (String temp : list) {
			System.out.println(temp);
		}
	}
}
