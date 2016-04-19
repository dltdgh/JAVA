package com.dlt.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;

public class AsyncImageLoader {
	
	private int MAX_SIZE;
	private static LruCache<String, Bitmap> bitmapCache;
	private String cachePath;
	private boolean hasCache;
	
	/**
	 * 无参构造方法默认无文件cache
	 */
	public AsyncImageLoader(){
		hasCache = false;
		if (bitmapCache == null) {
			MAX_SIZE = (int)Runtime.getRuntime().maxMemory()/1024/8;
			bitmapCache = new LruCache<String, Bitmap>(MAX_SIZE);
		}
	}
	
	/**
	 * 带有文件cache的构造方法
	 * @param cachePath 文件cache路径
	 */
	
	public AsyncImageLoader(String cachePath){
		hasCache = true;
		this.cachePath = cachePath;
		initCacheDir();
		if (bitmapCache == null) {
			MAX_SIZE = (int)Runtime.getRuntime().maxMemory()/1024/8;
			bitmapCache = new LruCache<String, Bitmap>(MAX_SIZE);
		}
	}
	
	/**
	 * 若cache文件路径不存在则创建文件路径
	 */
	private void initCacheDir(){	
		File dir = new File(cachePath);
		if (!dir.exists()) {
			dir.mkdir();
		}
	}
	
	/**
	 *设置image长宽的图片加载方法 
	 * @param urlStr 网络图片路径（格式 XXX/XXX.jpeg）
	 * @param view  图片要加载到的imageView控件
	 * @param width  图片显示宽度
	 * @param height  图片显示高度
	 */
	
	public void loadImage(String urlStr, ImageView view, int width, int height){
		LoadImageTask task = new LoadImageTask(view);
		task.execute(urlStr, String.valueOf(width), String.valueOf(height));
	}
	
	/**
	 * 不设置image长宽的图片加载方法
	 * @param urlStr 网络图片路径
	 * @param view	图片显示imageView
	 */
	
	
	public void loadImage(String urlStr, ImageView view){
		LoadImageTask task = new LoadImageTask(view);
		task.execute(urlStr);
	}
	
	/**
	 * 异步加载类
	 * @author Administrator
	 *
	 */
	
	private class LoadImageTask extends AsyncTask<String, Void, Bitmap>{
		
		private ImageView view;
		
		LoadImageTask(ImageView view){
			this.view = view;
		}
		
		@Override
		protected Bitmap doInBackground(String... params) {
			// TODO Auto-generated method stub
			String urlStr = params[0];
			int width = 0, height = 0;
			if (params.length == 3) {
				width = Integer.parseInt(params[1]);
				height = Integer.parseInt(params[2]);
			}
			
			String imageFileName = urlStr.substring(urlStr.lastIndexOf('/')+1);
			String imageFilePath = cachePath+"/"+imageFileName;
			Log.d("ImageLoader", imageFileName);
			
			System.out.println(bitmapCache);
			
			Bitmap bitmap = bitmapCache.get(imageFileName);
			
			if (bitmap == null) {
				if (hasCache) {                      //若支持文件缓存则先从文件中读取，再到网络上读取
					File imageFile = new File(imageFilePath);
					if (imageFile.exists()) {			//若cache里没有则到缓存文件路径寻找
						bitmap = getBitmapByFile(imageFilePath, width, height);
						Log.d("ImageLoader", "load by file");
					}
					else {								//文件中找不到则去网络加载
						bitmap = getBitmapByUrlWithCache(urlStr, width, height);
						Log.d("ImageLoader", "load by url with cache");
					}
				}
				else{
					bitmap = getBitmapByUrl(urlStr, width, height);	//无文件缓存则直接从网络获取
					Log.d("ImageLoader", "load by url");
				}
				if (bitmap != null) {                   //将获取到的bitmap加入内存缓存
					bitmapCache.put(imageFileName, bitmap);
				}
			}
			
			return bitmap;
		}
		
		@Override
		protected void onPostExecute(Bitmap result) {
			// TODO Auto-generated method stub
			if (result != null) {
				view.setImageBitmap(result);
			}
		}
	}
	
	/**
	 * 直接从网络获得图片，不下载原文件
	 * @param urlStr
	 * @param width
	 * @param height
	 * @return	bitmap
	 */
	
	private Bitmap getBitmapByUrl(String urlStr, int width, int height){
		Bitmap bitmap = null;
		InputStream in = excuteGet(urlStr);
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = false;
		
		BitmapFactory.decodeStream(in, null, opts);
		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		opts.inJustDecodeBounds = false;
		
		if (width > 0 && height > 0) {
			opts.inSampleSize = calcScale(width, height, opts.outWidth, opts.outHeight);
		}
		else {
			opts.inSampleSize = 1;
		}
		
		System.out.println(opts.inSampleSize);
		in = excuteGet(urlStr);
		bitmap = BitmapFactory.decodeStream(in, null, opts);
		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(bitmap.getWidth()+","+bitmap.getHeight());
		return bitmap;
	}
	
	/**
	 * 从网络获得图片文件并缓存到本地
	 * @param urlStr
	 * @param width
	 * @param height
	 * @return bitmap
	 */
	
	private Bitmap getBitmapByUrlWithCache(String urlStr, int width, int height){
		
		Bitmap bitmap = null;
		try {
			InputStream in = excuteGet(urlStr);
			String imageFilePath = cachePath+"/"+urlStr.substring(urlStr.lastIndexOf('/')+1);
			File imageFile = new File(imageFilePath);
			FileOutputStream fos = new FileOutputStream(imageFile);
			int n = 0;
			byte[] bytes = new byte[1024];
			while ((n = in.read(bytes)) > 0) {
				fos.write(bytes, 0, n);
			}
			fos.close();
			in.close();
			bitmap = getBitmapByFile(imageFilePath, width, height);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return bitmap;
	}
	
	/**
	 * 
	 * @param filePath
	 * @param width
	 * @param height
	 * @return
	 */
	
	private Bitmap getBitmapByFile(String filePath, int width, int height){
		Bitmap bitmap = null;
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, opts);
		opts.inJustDecodeBounds = false;
		if (width > 0 && height > 0) {
			opts.inSampleSize = calcScale(width, height, opts.outWidth, opts.outHeight);
		}
		else {
			opts.inSampleSize = 1;
		}
		bitmap = BitmapFactory.decodeFile(filePath, opts);
		System.out.println(bitmap.getWidth()+","+bitmap.getHeight());
		return bitmap;
	}
	
	/**
	 * 计算图片缩放比例
	 * @param targetWidth
	 * @param targetHeight
	 * @param srcWidth
	 * @param srcHeight
	 * @return
	 */
	
	private int calcScale(int targetWidth, int targetHeight, int srcWidth, int srcHeight){
		int scaleWidth = srcWidth/targetWidth;
		int scaleHeight = srcHeight/targetHeight;
		int scale = scaleWidth > scaleHeight ? scaleWidth : scaleHeight;
		if (scale <= 0) {
			scale = 1;
		}
		return scale;
	}
	
	/**
	 * 访问url获得输入流
	 * @param urlStr
	 * @return
	 */
	
	private InputStream excuteGet(String urlStr){
		InputStream in = null;
		try {
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			if (conn.getResponseCode() == 200) {
				in = conn.getInputStream();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return in;
	}
}

//先前版本
/*public void loadRemoteImage(String urlStr, ImageView view, int width, int height){
RemoteImageLoadTask task = new RemoteImageLoadTask(view, width, height);
task.execute(urlStr);
}

public void loadLocalImage(String filePath, ImageView view, int width, int height){
LocalImageLoadTask task = new LocalImageLoadTask(view, width, height);
task.execute(filePath);
}

private class RemoteImageLoadTask extends AsyncTask<String, Void, Bitmap>{

ImageView view = null;

int width = Integer.MAX_VALUE;
int height = Integer.MAX_VALUE;

RemoteImageLoadTask(ImageView view, int width, int height){
	this.view = view;
	this.width = width;
	this.height = height;
}

@Override
protected Bitmap doInBackground(String... params) {
	// TODO Auto-generated method stub
	return getBitmapByUrl(params[0], width, height);
}

@Override
protected void onPostExecute(Bitmap result) {
	// TODO Auto-generated method stub
	view.setImageBitmap(result);
}
}

private class LocalImageLoadTask extends AsyncTask<String, Void, Bitmap>{

ImageView view = null;
int width = Integer.MAX_VALUE;
int height = Integer.MAX_VALUE;

LocalImageLoadTask(ImageView view, int width, int height){
	this.view = view;
	this.width = width;
	this.height = height;
}

@Override
protected Bitmap doInBackground(String... params) {
	// TODO Auto-generated method stub
	return getBitmapByFile(params[0], width, height);
}

@Override
protected void onPostExecute(Bitmap result) {
	// TODO Auto-generated method stub
	view.setImageBitmap(result);
}
}*/
