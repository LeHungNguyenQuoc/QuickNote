package kn.multinote.database.helper;

import java.sql.SQLException;

import kn.multinote.dto.SystemSettingDto;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	private static final String DATABASE_NAME = "dbline98" + ".db";
	private static final int DATABASE_VERSION = 4;
	private final String LOG_NAME = getClass().getName();

	public static String dbName;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		super.close();
	}

	@Override
	public void onCreate(SQLiteDatabase sqLiteDatabase,
			ConnectionSource connectionSource) {
		try {
			TableUtils.createTable(connectionSource, SystemSettingDto.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase,
			ConnectionSource connectionSource, int oldVersion, int newVersion) {
		try {
			TableUtils
					.dropTable(connectionSource, SystemSettingDto.class, true);
			onCreate(sqLiteDatabase, connectionSource);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}