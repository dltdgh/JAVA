package com.dlt.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileService {
	
	private File file = null;
	
	public FileService(String filePath){
		/*if (filePath.contains("/")) {
			prepareFile(filePath);
		}*/
		file = new File(filePath);
	}
	
	public boolean writeToFile(String content){
		
		try {
			FileOutputStream fos = new FileOutputStream(file);
			PrintWriter pw = new PrintWriter(fos);
			pw.write(content);
			pw.flush();
			pw.close();
			fos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public boolean appendToFile(String content){
		
		try {
			FileOutputStream fos = new FileOutputStream(file, true);
			PrintWriter pw = new PrintWriter(fos);
			pw.write(content);
			pw.flush();
			pw.close();
			fos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public String readFile() {
		
		String content = "";
		FileInputStream fis;
		
		try {
			fis = new FileInputStream(file);
			Scanner sc = new Scanner(fis);
			while (sc.hasNext()) {
				content = content + sc.nextLine() + "\n";
			}
			sc.close();
			fis.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return content;
	}
	
	public static String getFileNameFromPath(String filePath){
		String fileName = new File(filePath).getName();
		return fileName;
	}
	
	public static String getFileDirFromPath(String filePath){
		String dir = null;
		dir = filePath.substring(0, filePath.lastIndexOf('/'));
		return dir;
	}
	
	public static void mkdir(String fileDir){
		File dir = new File(fileDir);
		dir.mkdir();
	}
	
	public static void prepareFile(String filePath){
		if(filePath.contains("/")){
			String dir = getFileDirFromPath(filePath);
			mkdir(dir);
		}
	}
	
	public static void main(String[] args) throws Exception {
	//	FileService fileService = new FileService("d://dir/5596.txt");
	//	fileService.writeToFile("xixihaha\n");
		/*fileService.appendToFile("hohohshs");*/
		System.out.println(FileService.getFileDirFromPath("d://dir/5596.txt"));
	}
}
