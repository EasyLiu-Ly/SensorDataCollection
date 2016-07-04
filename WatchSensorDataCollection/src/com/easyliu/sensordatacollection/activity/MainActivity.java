package com.easyliu.sensordatacollection.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

import com.easyliu.sensordatacollection.R;
import com.easyliu.sensordatacollection.unity.GestureAccGyroDetector;

public class MainActivity extends Activity {
	private GestureAccGyroDetector mGestureAccGyroDetector;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initView();
		mGestureAccGyroDetector = new GestureAccGyroDetector(
				getApplicationContext());
	}

	private void initView() {
		findViewById(R.id.btn_start_shake).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						mGestureAccGyroDetector.start();
					}
				});
		findViewById(R.id.btn_stop_shake).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						mGestureAccGyroDetector.stop();
					}
				});
	}
}
