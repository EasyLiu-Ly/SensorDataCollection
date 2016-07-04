package com.easyliu.sensordatacollection.unity;



public class ItemAccData implements IGestureData{
	private float mAccX;
	private float mAccY;
	private float mAccZ;

	public ItemAccData(float mAccX, float mAccY, float mAccZ) {
		super();
		this.mAccX = mAccX;
		this.mAccY = mAccY;
		this.mAccZ = mAccZ;
	}

	@Override
	public float getX() {
		return mAccX;
	}

	@Override
	public void setX(float xData) {
		this.mAccX=xData;
	}

	@Override
	public float getY() {
		return mAccY;
	}

	@Override
	public void setY(float yData) {
		this.mAccY=yData;
	}

	@Override
	public float getZ() {
		return mAccZ;
	}

	@Override
	public void setZ(float zData) {
		this.mAccZ=zData;
	}
}
