package com.example.mp3player;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.R.integer;

public class LrcProcessor {
	public ArrayList<Queue> process(InputStream inputStream){
		Queue<Long> timeMills = new LinkedList<Long>();
		Queue<String> messages = new LinkedList<String>();
		ArrayList<Queue> queues = new ArrayList<Queue>();
		try {
			InputStreamReader inputReader = new InputStreamReader(inputStream);
			BufferedReader bufferedReader = new BufferedReader(inputReader);
			String temp = null;
			Pattern p = Pattern.compile("\\[([^\\]]+)\\]");
			String result = null;
			boolean b = true;
			while ((temp = bufferedReader.readLine()) != null) {

	//			System.out.println(temp);
	//			System.out.println(result);
				Matcher m = p.matcher(temp);
				if(m.find()){
					if(result != null){
						messages.add(result);
					}
					String timeStr = m.group();
					long timeMill = timeToLong(timeStr.substring(1, timeStr.length()-1));
					if(b){
						timeMills.offer(timeMill);
					}
					
					String msg = temp.substring(10);
					if(msg.charAt(0) == ']'){
						msg = msg.substring(1);
					}
					result = msg+"\n";
				}
				else {
					result = result+temp+"\n";
				}
			}
			messages.add(result);
			queues.add(timeMills);
			queues.add(messages);
		} catch (Exception e) {
			// TODO: handle exception
			//System.out.println("wornning");
			e.printStackTrace();
		}
		
		return queues;
	}
	
	public Long timeToLong(String timeStr){
		String[] s = timeStr.split(":");
		int min = Integer.parseInt(s[0]);
		String ss[] = s[1].split("\\.");
		int sec = Integer.parseInt(ss[0]);
		int mill = Integer.parseInt(ss[1]);
		return min*60*1000+sec*1000+mill*10L;
	}
}
