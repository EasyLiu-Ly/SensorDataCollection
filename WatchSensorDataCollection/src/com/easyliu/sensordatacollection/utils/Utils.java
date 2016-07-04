package com.easyliu.sensordatacollection.utils;

import android.content.Context;
import android.os.Handler;
import android.os.PowerManager;

public class Utils {
	public static final String GESTURE_ACC_PATH = "ACC_DATA";
	public static final String GESTURE_GYRO_PATH = "GYRO_DATA";
	public static final String DATA_SEPERATOR = "     ";
	public static final int INVALID_GESTURE_LENGTH = 20;//��Ч���ݳ���
    public static final int MAX_ROLL_ABS=10;            //ROLLƽ��ֵ����ֵ���ֵ
	/**
	 * ������Ļ
	 * 
	 * @param context
	 */
	public static void wakeScreen(Context context, int delayMillis) {
		// ��ȡ��Դ����������
		PowerManager pm = (PowerManager) context
				.getSystemService(Context.POWER_SERVICE);
		// ��ȡPowerManager.WakeLock����,����Ĳ���|��ʾͬʱ��������ֵ,������LogCat���õ�Tag
		final PowerManager.WakeLock wl = pm.newWakeLock(
				PowerManager.ACQUIRE_CAUSES_WAKEUP
						| PowerManager.SCREEN_DIM_WAKE_LOCK, "bright");
		// ������Ļ
		wl.acquire();
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				wl.release(); // �ͷ�
			}
		}, delayMillis);
	}
}
