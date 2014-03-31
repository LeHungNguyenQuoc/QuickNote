package kn.multinote.ui.activity;

import kn.multinote.ui.services.PopupNote;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		PopupNote.widthTruct = getWindowManager().getDefaultDisplay()
				.getWidth();
		PopupNote.heightTruct = getWindowManager().getDefaultDisplay()
				.getHeight();
//		if (!isMyServiceRunning())
//			startService(new Intent(this, PopupNote.class));
//		else {
//			startActivity(new Intent(this, NoteStorageActivity.class));
//		}
//		finish();
	}

	@Override
	public void onResume() {
		super.onResume();
//		if (isMyServiceRunning()) {
//			stopService(new Intent(this, PopupNote.class));
//		}
//		if (startService(new Intent(this, PopupNote.class)) != null) {
			// Toast.makeText(getBaseContext(), "Service is already running",
			// Toast.LENGTH_SHORT).show();
			// } else {
			// Toast.makeText(getBaseContext(),
			// "There is no service running, starting service..",
			// Toast.LENGTH_SHORT).show();
//		}
	}

	private boolean isMyServiceRunning() {
		ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		for (RunningServiceInfo service : manager
				.getRunningServices(Integer.MAX_VALUE)) {
			if (PopupNote.class.getName()
					.equals(service.service.getClassName())) {
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
//		Intent reLaunchMain = new Intent(this, MainActivity.class);
//		reLaunchMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//		reLaunchMain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		startActivity(reLaunchMain);
	}

}
