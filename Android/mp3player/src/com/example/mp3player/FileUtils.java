package com.example.mp3player;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.transform.Templates;

import android.os.Environment;

public class FileUtils {
	private String SDCardRoot = null;
	public FileUtils(){
		SDCardRoot = Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator;
	}
	
	public File createFileInSDCard(String fileName, String dir) throws IOException{
		File file = new File(SDCardRoot+dir+fileName);
		file.createNewFile();
		return file;
	}
	
	public File createSDDir(String dir){
		File dirFile = new File(SDCardRoot+dir+File.separator);
		dirFile.mkdirs();
		return dirFile;
	}
	
	public boolean isFileExist(String fileName, String path){
		File file = new File(SDCardRoot+path+File.separator+fileName);
		return file.exists();
	}
	
	public File writeToSDFromInput(String path, String fileName, InputStream input){
		File file = null;
		OutputStream output = null;
		try {
			createSDDir(path);
			file = createFileInSDCard(fileName, path);
			output = new FileOutputStream(file);
			byte[] buffer = new byte[4096];
			int temp;
			while((temp = input.read(buffer)) > 0){
				output.write(buffer, 0, temp);
			}
			output.flush();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally{
			try {
				output.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return file;
	}
	
	public List<Mp3Info> getMp3Files(String path){
		List<Mp3Info> mp3Infos = new ArrayList<Mp3Info>();
		File file = new File(SDCardRoot+File.separator+path);
		File[] files = file.listFiles();
		FileUtils fileUtils = new FileUtils();
		for(int i = 0; i < files.length; i++){
			if(files[i].getName().endsWith("mp3")){
				
				Mp3Info mp3Info = new Mp3Info();
				mp3Info.setMp3Name(files[i].getName());
				mp3Info.setMp3Size(files[i].length()+"");
				String[] tStr = mp3Info.getMp3Name().split("\\.");
				String tlrcName = tStr[0]+".lrc";
				if(fileUtils.isFileExist(tlrcName, "/mp3")){
					mp3Info.setLrcName(tlrcName);
				}
				else {
					mp3Info.setLrcName(null);
				}
				mp3Infos.add(mp3Info);
			}
		}
		return mp3Infos;
	}
}
