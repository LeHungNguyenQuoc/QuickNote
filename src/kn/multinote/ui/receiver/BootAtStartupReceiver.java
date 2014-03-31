package kn.multinote.ui.receiver;

import kn.multinote.ui.services.PopupNote;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootAtStartupReceiver extends BroadcastReceiver {
	static final String TAG = "BootAtStartupReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
			Log.i(TAG, "*** onReceive ACTION_BOOT_COMPLETED");
			context.startService(new Intent(context, PopupNote.class));
		}

		Log.i(TAG, "*** onReceive");
	}

}
