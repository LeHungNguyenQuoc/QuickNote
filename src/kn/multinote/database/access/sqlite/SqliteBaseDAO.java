package kn.multinote.database.access.sqlite;

import kn.multinote.app.MultiNoteApp;
import kn.multinote.database.helper.DatabaseHelper;
import kn.supportrelax.database.tracking.DatabaseTracking;
import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;

public class SqliteBaseDAO<T> extends DatabaseTracking<T> {
	@SuppressWarnings("unused")
	private static final String TAG = SqliteBaseDAO.class.getName();

	protected DatabaseHelper dbHelper;
	protected Context context;

	protected DatabaseTracking<T> tracking;

	public SqliteBaseDAO() {
		this.context = MultiNoteApp.getContext();

		getHelper();

		// registry(); // registry tracking database change
	}

	protected DatabaseHelper getHelper() {
		if (dbHelper == null) {
			dbHelper = OpenHelperManager.getHelper(this.context,
					DatabaseHelper.class);
		}
		return dbHelper;
	}

	public void release() {
		if (dbHelper != null) {
			OpenHelperManager.releaseHelper();
			dbHelper = null;
		}
	}
}
