package qrail.com.interactivemap.database;

import android.database.Cursor;

import qrail.com.interactivemap.database.MetroDbHelper;

public class PlatformInfoAPI {

	public static final String TABLE_NAME = " PlatformInfo";

	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_PLATFORM_ID = "platformId";
	public static final String COLUMN_TOWARDS = "towards1";

	public Cursor getAllPlatformInfo(MetroDbHelper mHelper) {
		return mHelper.fetchData(TABLE_NAME, null, null);

	}

}
