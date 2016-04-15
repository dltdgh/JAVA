package com.dlt.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class ImageUtils {
	
	public static void loadRemoteImage(String urlStr, ImageView view, int width, int height){
		RemoteImageLoadTask task = new RemoteImageLoadTask(view, width, height);
		task.execute(urlStr);
	}
	
	public static void loadLocalImage(String filePath, ImageView view, int width, int height){
		LocalImageLoadTask task = new LocalImageLoadTask(view, width, height);
		task.execute(filePath);
	}
	
	private static class RemoteImageLoadTask extends AsyncTask<String, Void, Bitmap>{

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
	
	private static class LocalImageLoadTask extends AsyncTask<String, Void, Bitmap>{
		
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
	}
	
	private static Bitmap getBitmapByUrl(String urlStr, int width, int height){
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
		opts.inSampleSize = calcScale(width, height, opts.outWidth, opts.outHeight);
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
	
	private static Bitmap getBitmapByFile(String filePath, int width, int height){
		Bitmap bitmap = null;
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, opts);
		opts.inJustDecodeBounds = false;
		opts.inSampleSize = calcScale(width, height, opts.outWidth, opts.outHeight);
		bitmap = BitmapFactory.decodeFile(filePath, opts);
		System.out.println(bitmap.getWidth()+","+bitmap.getHeight());
		return bitmap;
	}
	
	private static int calcScale(int targetWidth, int targetHeight, int srcWidth, int srcHeight){
		int scaleWidth = srcWidth/targetWidth;
		int scaleHeight = srcHeight/targetHeight;
		int scale = scaleWidth > scaleHeight ? scaleWidth : scaleHeight;
		if (scale <= 0) {
			scale = 1;
		}
		return scale;
	}
	
	private static InputStream excuteGet(String urlStr){
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
