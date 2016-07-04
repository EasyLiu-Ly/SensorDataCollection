package com.easyliu.sensordatacollection.unity;


public class ItemMagData implements IGestureData {
	private float mMagX;
	private float mMagY;
	private float mMagZ;

	public ItemMagData(float mMagX, float mMagY, float mMagZ) {
		super();
		this.mMagX = mMagX;
		this.mMagY = mMagY;
		this.mMagZ = mMagZ;
	}

	@Override
	public float getX() {
		return mMagX;
	}

	@Override
	public void setX(float xData) {
		mMagX = xData;
	}

	@Override
	public float getY() {
		return mMagY;
	}

	@Override
	public void setY(float yData) {
		this.mMagY = yData;
	}

	@Override
	public float getZ() {
		return mMagZ;
	}

	@Override
	public void setZ(float zData) {
		mMagZ = zData;
	}

}
