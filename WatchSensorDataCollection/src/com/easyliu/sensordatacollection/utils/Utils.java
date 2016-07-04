package com.easyliu.sensordatacollection.utils;

import android.content.Context;
import android.os.Handler;
import android.os.PowerManager;

public class Utils {
	public static final String GESTURE_ACC_PATH = "ACC_DATA";
	public static final String GESTURE_GYRO_PATH = "GYRO_DATA";
	public static final String DATA_SEPERATOR = "     ";
	public static final int INVALID_GESTURE_LENGTH = 20;//有效数据长度
    public static final int MAX_ROLL_ABS=10;            //ROLL平均值绝对值最大值
	/**
	 * 唤醒屏幕
	 * 
	 * @param context
	 */
	public static void wakeScreen(Context context, int delayMillis) {
		// 获取电源管理器对象
		PowerManager pm = (PowerManager) context
				.getSystemService(Context.POWER_SERVICE);
		// 获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
		final PowerManager.WakeLock wl = pm.newWakeLock(
				PowerManager.ACQUIRE_CAUSES_WAKEUP
						| PowerManager.SCREEN_DIM_WAKE_LOCK, "bright");
		// 点亮屏幕
		wl.acquire();
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				wl.release(); // 释放
			}
		}, delayMillis);
	}
}
