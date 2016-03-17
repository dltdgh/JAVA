package com.dlt.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import android.os.Environment;

public class FileService {
	/**
	 * ���������ݹ�ģ��С�ļ���ȡ
	 * @param in ������
	 * @return �ַ���
	 */
	public static String getStringFromInputstream(InputStream in, String charset){
		String temp = "";
		Scanner sc = null;
		System.out.println(charset);
		if (charset != null) {
			sc = new Scanner(in, charset);
		}
		else {
			sc = new Scanner(in);
		}
		while (sc.hasNext()) {
			temp = temp + sc.nextLine() + "\n";
		}
		sc.close();
		return temp;
	}
	
	/**
	 * ��������д���ļ�
	 * @param in
	 * @param dir �����SD����Ŀ¼·��(/..��)
	 * @param fileName
	 */
	
	public static void writeToSDcard(InputStream in, String dir, String fileName){
		//context.gete
		String sdDir = Environment.getExternalStorageDirectory().getAbsolutePath();
		File fileDir = new File(sdDir+dir);
		if (!fileDir.exists()) {
			fileDir.mkdir();
		}
		File file = new File(fileDir.getAbsolutePath(), fileName);
		System.out.println(file.getAbsolutePath());
		if (!file.exists()) {
			try {
				file.createNewFile();
				file.setWritable(true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			FileOutputStream fos = new FileOutputStream(file);
			int n;
			byte[] bytes = new byte[1024];
			while ((n = in.read(bytes)) > 0) {
				fos.write(bytes, 0, n);
			}
			fos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * ���ַ���д��SD���ļ�
	 * @param str
	 * @param dir �����SD����Ŀ¼��·��(/..��)
	 * @param fileName
	 */
	
	
	public static void writeToSDcard(String str, String dir, String fileName){
		//context.gete
		String sdDir = Environment.getExternalStorageDirectory().getAbsolutePath();
		File fileDir = new File(sdDir+dir);
		if (!fileDir.exists()) {
			fileDir.mkdir();
		}
		File file = new File(fileDir.getAbsolutePath(), fileName);
		System.out.println(file.getAbsolutePath());
		if (!file.exists()) {
			try {
				file.createNewFile();
				file.setWritable(true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(str.getBytes());
			fos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
