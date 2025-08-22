package qrail.com.interactivemap.database;

import android.database.Cursor;

import qrail.com.interactivemap.database.MetroDbHelper;

public class GatesInfoAPI {

	public static final String TABLE_NAME = "GatesInfo";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_GATE = "gate";
	public static final String COLUMN_TOWARDS = "towards";

	public Cursor getAllGatesInfo(MetroDbHelper mHelper) {
		return mHelper.fetchData(TABLE_NAME, null, null);

	}

}
