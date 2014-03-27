package kn.multinote.app;

import kn.multinote.ui.services.PopupNote;
import android.app.ActivityManager;
import android.app.Application;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

public class MultiNoteApp extends Application {
	private static final String TAG = MultiNoteApp.class.getName();
	private static MultiNoteApp instance;
	public static View mView;
	public static View mViewNext;
	public static WindowManager.LayoutParams mParams;
	public static WindowManager.LayoutParams mParamNexts;
	public static WindowManager mWindowManager;

	@Override
	public void onCreate() {
		super.onCreate();
		Log.i(TAG, "[onCreate]");
		instance = this;
	}

	public static Context getContext() {
		return instance.getApplicationContext();
	}
}
