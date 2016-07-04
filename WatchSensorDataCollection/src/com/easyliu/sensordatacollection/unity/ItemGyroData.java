package com.easyliu.sensordatacollection.unity;



public class ItemGyroData implements IGestureData {
	private float mGyroX;
	private float mGyroY;
	private float mGyroZ;

	public ItemGyroData(float mGyroX, float mGyroY, float mGyroZ) {
		super();
		this.mGyroX = mGyroX;
		this.mGyroY = mGyroY;
		this.mGyroZ = mGyroZ;
	}

	@Override
	public float getX() {
		return mGyroX;
	}

	@Override
	public void setX(float xData) {
		this.mGyroX = xData;
	}

	@Override
	public float getY() {
		return mGyroY;
	}

	@Override
	public void setY(float yData) {
		this.mGyroY = yData;
	}

	@Override
	public float getZ() {
		return mGyroZ;
	}

	@Override
	public void setZ(float zData) {
		this.mGyroZ = zData;
	}

}
