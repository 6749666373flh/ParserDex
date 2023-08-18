package com.fan.parserdex.utils;

import android.util.Log;

public class LogUtils {

	private static  boolean  enableLog = true;

	public static String TAG = "fan222333";


	public static void e(String message, Object... args) {
		if (!enableLog) {
			return;
		}
		String formattedMessage = String.format(message, args);
		Log.e(TAG, formattedMessage);
	}



}
