package com.easyliu.sensordatacollection.utils;

import java.io.File;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

public class SDCardUtils {
	private static final String TAG = SDCardUtils.class.getSimpleName();

	private SDCardUtils() {
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	/**
	 * �ж�SDCard�Ƿ����
	 * 
	 * @return
	 */
	public static boolean isSDCardEnable() {
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);

	}

	/* Checks if external storage is available for read and write */
	public static boolean isExternalStorageWritable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		}
		return false;
	}

	/* Checks if external storage is available to at least read */
	public static boolean isExternalStorageReadable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)
				|| Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			return true;
		}
		return false;
	}

	/**
	 * ��ȡSD��·��
	 * 
	 * @return
	 */
	public static String getSDCardPath() {
		return Environment.getExternalStorageDirectory().getAbsolutePath()
				+ File.separator;
	}

	/**
	 * ��ȡSD����ʣ������ ��λbyte
	 * 
	 * @return
	 */
	public static long getSDCardAllSize() {
		if (isSDCardEnable()) {
			StatFs stat = new StatFs(getSDCardPath());
			// ��ȡ���е����ݿ������
			long availableBlocks = (long) stat.getAvailableBlocks() - 4;
			// ��ȡ�������ݿ�Ĵ�С��byte��
			long freeBlocks = stat.getAvailableBlocks();
			return freeBlocks * availableBlocks;
		}
		return 0;
	}

	/**
	 * ��ȡָ��·�����ڿռ��ʣ����������ֽ�������λbyte
	 * 
	 * @param filePath
	 * @return �����ֽ� SDCard���ÿռ䣬�ڲ��洢���ÿռ�
	 */
	public static long getFreeBytes(String filePath) {
		// �����sd�����µ�·�������ȡsd����������
		if (filePath.startsWith(getSDCardPath())) {
			filePath = getSDCardPath();
		} else {// ������ڲ��洢��·�������ȡ�ڴ�洢�Ŀ�������
			filePath = Environment.getDataDirectory().getAbsolutePath();
		}
		StatFs stat = new StatFs(filePath);
		long availableBlocks = (long) stat.getAvailableBlocks() - 4;
		return stat.getBlockSize() * availableBlocks;
	}

	/**
	 * ��ȡϵͳ�洢·��
	 * 
	 * @return
	 */
	public static String getRootDirectoryPath() {
		return Environment.getRootDirectory().getAbsolutePath();
	}

	/**
	 * �ⲿ˽���ļ���·��
	 * 
	 * @param context
	 * @param albumName
	 * @return
	 */
	public static File getAlbumStorageDir(Context context, String albumName) {
		// Get the directory for the app's private pictures directory.
		File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), albumName);
		if (!file.exists()) {
			if (!file.mkdirs()) {
				Log.e(TAG, "Directory not created");
			}
		}
		return file;
	}
}
