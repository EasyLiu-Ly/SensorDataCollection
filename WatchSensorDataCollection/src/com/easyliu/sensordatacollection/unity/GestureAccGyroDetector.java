package com.easyliu.sensordatacollection.unity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.easyliu.sensordatacollection.utils.SDCardUtils;
import com.easyliu.sensordatacollection.utils.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Toast;

/**
 * 
 * @author v_easyliu
 * 
 */
public class GestureAccGyroDetector implements SensorEventListener {
	private SensorManager mSensorManager;
	private Sensor mAccSensor; // 加速度传感器
	private Sensor mGyroSensor;// 角速度传感器
	private Sensor mMagSensor; // 磁力计
	private Context mContext;
	private float[] mAccelerometerValues = new float[3];
	private float[] mGyroValues = new float[3];
	private float[] mMagValues = new float[3];
	private float mTimestamp;
	private ArrayList<ItemAccData> mAccDataOriginal = new ArrayList<ItemAccData>();
	private ArrayList<ItemGyroData> mGyroDataOriginal = new ArrayList<ItemGyroData>();
	private ArrayList<ItemMagData> mMagDataOriginal = new ArrayList<ItemMagData>();
	private static final float NS2S = 1.0f / 1000000000.0f;

	public GestureAccGyroDetector(Context context) {
		this.mContext = context;
	}

	/**
	 * 开始
	 */
	public void start() {
		Toast.makeText(mContext.getApplicationContext(), "开始采集数据",
				Toast.LENGTH_SHORT).show();
		mSensorManager = (SensorManager) mContext
				.getSystemService(Context.SENSOR_SERVICE);
		mAccSensor = mSensorManager
				.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
		mGyroSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
		mMagSensor = mSensorManager
				.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		mSensorManager.registerListener(this, mAccSensor,
				SensorManager.SENSOR_DELAY_GAME);
		mSensorManager.registerListener(this, mGyroSensor,
				SensorManager.SENSOR_DELAY_GAME);
		mSensorManager.registerListener(this, mMagSensor,
				SensorManager.SENSOR_DELAY_GAME);
	}

	/**
	 * 停止
	 */
	@SuppressLint("SimpleDateFormat")
	public void stop() {
		if (mSensorManager != null) {
			// 保存数据
			// 当前日期
			String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
					.format(new Date());
			saveDataToSD(mContext, mAccDataOriginal, timeStamp, "ACC");
			saveDataToSD(mContext, mGyroDataOriginal, timeStamp, "GYRO");
			saveDataToSD(mContext, mMagDataOriginal, timeStamp, "Mag");
			Toast.makeText(mContext.getApplicationContext(), "文件已经写入完成",
					Toast.LENGTH_SHORT).show();
			mAccDataOriginal.clear();
			mGyroDataOriginal.clear();
			mSensorManager.unregisterListener(this);
		}
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {

	}

	@Override
	public void onSensorChanged(SensorEvent arg0) {
		// 加速度
		if (arg0.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
			mAccelerometerValues = arg0.values;
			if (mTimestamp != 0) {
				final float dT = (arg0.timestamp - mTimestamp) * NS2S;
				System.out.println((int) (1 / dT));// time interval 采样率
				// 加速度数据
				ItemAccData itemAccData = new ItemAccData(
						mAccelerometerValues[0], mAccelerometerValues[1],
						mAccelerometerValues[2]);
				mAccDataOriginal.add(itemAccData);
			}
			mTimestamp = arg0.timestamp;
		}
		// 角速度
		if (arg0.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
			mGyroValues = arg0.values;
			// 角速度数据
			ItemGyroData itemGyroData = new ItemGyroData(mGyroValues[0],
					mGyroValues[1], mGyroValues[2]);
			mGyroDataOriginal.add(itemGyroData);
		}
		if (arg0.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
			mMagValues = arg0.values;
			// 磁力计
			ItemMagData itemMagData = new ItemMagData(mMagValues[0],
					mMagValues[1], mMagValues[2]);
			mMagDataOriginal.add(itemMagData);
		}
	}

	/**
	 * 保存数据
	 * 
	 * @param context
	 * @param itemDatas
	 * @param fileName
	 */
	public static void saveDataToSD(Context context,
			ArrayList<? extends IGestureData> itemDatas, String fileName,
			String fileType) {
		String accDataFileName = fileType + "_" + fileName + ".txt";
		// 新建文件
		File file = new File(SDCardUtils.getAlbumStorageDir(
				context.getApplicationContext(), fileType), accDataFileName);
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			// 保存数据
			int count = 0;
			for (IGestureData itemAccData : itemDatas) {
				count++;// 个数
				bw.write(count + Utils.DATA_SEPERATOR + itemAccData.getX()
						+ Utils.DATA_SEPERATOR + itemAccData.getY()
						+ Utils.DATA_SEPERATOR + itemAccData.getZ() + "\r\n");// 换行
				bw.flush();
			}
			bw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
