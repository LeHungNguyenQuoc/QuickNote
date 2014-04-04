package kn.multinote.ui.activity;

import kn.multinote.defines.MessageConstant;
import kn.multinote.ui.controller.MainController;
import kn.multinote.ui.services.PopupNote;
import kn.multinote.ui.services.QuickNoteService;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.Menu;

public class MainActivity extends Activity implements Callback {

	private MainController mController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_main);
		PopupNote.widthTruct = getWindowManager().getDefaultDisplay()
				.getWidth();
		PopupNote.heightTruct = getWindowManager().getDefaultDisplay()
				.getHeight();
		QuickNoteService.widthTruct = getWindowManager().getDefaultDisplay()
				.getWidth();
		QuickNoteService.heightTruct = getWindowManager().getDefaultDisplay()
				.getHeight();

		mController = new MainController();
		mController.addOutboxHandler(new Handler(this));
		mController.handleMessage(MessageConstant.MESSAGE_START_SERVICE);
		
		startActivity(new Intent(getApplicationContext(),
				ShownoteActivity.class));
		
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	private boolean isMyServiceRunning() {
		ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		for (RunningServiceInfo service : manager
				.getRunningServices(Integer.MAX_VALUE)) {
			if (QuickNoteService.class.getName().equals(
					service.service.getClassName())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// Intent reLaunchMain = new Intent(this, MainActivity.class);
		// reLaunchMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		// reLaunchMain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		// startActivity(reLaunchMain);
	}

	@Override
	public boolean handleMessage(Message msg) {
		switch (msg.what) {
		case MessageConstant.MESSAGE_START_SHOWNOTE:
			startActivity(new Intent(getApplicationContext(),
					ShownoteActivity.class));
			break;

		default:
			break;
		}
		return false;
	}

}
