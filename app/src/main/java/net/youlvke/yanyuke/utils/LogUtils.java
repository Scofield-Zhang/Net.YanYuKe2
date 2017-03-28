package net.youlvke.yanyuke.utils;

import android.util.Log;


public class LogUtils  {
	private static final boolean ENABLE = true;
	
	public static void egg(String msg){
		if (ENABLE) {
			Log.i("egg", msg);
		}
	}
	public static void duckEgg(String msg){
		if (ENABLE) {
			Log.i("duckEgg", msg);
		}
	}

}
