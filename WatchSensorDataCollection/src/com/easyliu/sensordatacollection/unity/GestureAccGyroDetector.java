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
	private Sensor mAccSensor; // ���ٶȴ�����
	private Sensor mGyroSensor;// ���ٶȴ�����
	private Sensor mMagSensor; // ������
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
	 * ��ʼ
	 */
	public void start() {
		Toast.makeText(mContext.getApplicationContext(), "��ʼ�ɼ�����",
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
	 * ֹͣ
	 */
	@SuppressLint("SimpleDateFormat")
	public void stop() {
		if (mSensorManager != null) {
			// ��������
			// ��ǰ����
			String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
					.format(new Date());
			saveDataToSD(mContext, mAccDataOriginal, timeStamp, "ACC");
			saveDataToSD(mContext, mGyroDataOriginal, timeStamp, "GYRO");
			saveDataToSD(mContext, mMagDataOriginal, timeStamp, "Mag");
			Toast.makeText(mContext.getApplicationContext(), "�ļ��Ѿ�д�����",
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
		// ���ٶ�
		if (arg0.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
			mAccelerometerValues = arg0.values;
			if (mTimestamp != 0) {
				final float dT = (arg0.timestamp - mTimestamp) * NS2S;
				System.out.println((int) (1 / dT));// time interval ������
				// ���ٶ�����
				ItemAccData itemAccData = new ItemAccData(
						mAccelerometerValues[0], mAccelerometerValues[1],
						mAccelerometerValues[2]);
				mAccDataOriginal.add(itemAccData);
			}
			mTimestamp = arg0.timestamp;
		}
		// ���ٶ�
		if (arg0.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
			mGyroValues = arg0.values;
			// ���ٶ�����
			ItemGyroData itemGyroData = new ItemGyroData(mGyroValues[0],
					mGyroValues[1], mGyroValues[2]);
			mGyroDataOriginal.add(itemGyroData);
		}
		if (arg0.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
			mMagValues = arg0.values;
			// ������
			ItemMagData itemMagData = new ItemMagData(mMagValues[0],
					mMagValues[1], mMagValues[2]);
			mMagDataOriginal.add(itemMagData);
		}
	}

	/**
	 * ��������
	 * 
	 * @param context
	 * @param itemDatas
	 * @param fileName
	 */
	public static void saveDataToSD(Context context,
			ArrayList<? extends IGestureData> itemDatas, String fileName,
			String fileType) {
		String accDataFileName = fileType + "_" + fileName + ".txt";
		// �½��ļ�
		File file = new File(SDCardUtils.getAlbumStorageDir(
				context.getApplicationContext(), fileType), accDataFileName);
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			// ��������
			int count = 0;
			for (IGestureData itemAccData : itemDatas) {
				count++;// ����
				bw.write(count + Utils.DATA_SEPERATOR + itemAccData.getX()
						+ Utils.DATA_SEPERATOR + itemAccData.getY()
						+ Utils.DATA_SEPERATOR + itemAccData.getZ() + "\r\n");// ����
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
