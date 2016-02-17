package com.example.mp3player;

import android.R.anim;
import android.R.integer;

public interface AppConstant {
	public class PlayerMsg{
		public static final int PLAY_MSG = 1;
		public static final int PAUSE_MSG = 2;
		public static final int STOP_MSG = 3;
		public static final int CHANGE_MSG = 4;
	}
	
	public class URL{
		public static final String BASE_URL = "http://192.168.41.47:8080/mp3/";
	}
	
	public static final String LRC_MESSAGE_ACTION = "lrc_message_filter";
	public static final String TIME_MESSAGE_ACTION = "lrc_message_filter";
}
