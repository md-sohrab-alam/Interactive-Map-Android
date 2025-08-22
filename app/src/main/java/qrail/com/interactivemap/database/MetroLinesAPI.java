package qrail.com.interactivemap.database;

import android.database.Cursor;

import qrail.com.interactivemap.database.MetroDbHelper;

public class MetroLinesAPI {
	public static final String TABLE_NAME = " MetroLines";

	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_NAME = "NAME";
	public static final String COLUMN_STOPS = "STOPS";
	public static final String COLUMN_DISTANCE = "DISTANCE";
	public static final String COLUMN_PTIME = "PTIME";
	public static final String COLUMN_NPTIME = "NPTIME";
	public static final String COLUMN_SPOINT = "SPOINT";
	public static final String COLUMN_EPOINT = "EPOINT";
	public static final String COLUMN_FARE = "FARE";
	public static final String COLUMN_DIVERSION_OF = "DIVERSION_OF";
	public static final String COLUMN_DIVERSIONS = "diversions";

	public Cursor getAllMetroLines(MetroDbHelper mHelper) {
		return mHelper.fetchData(TABLE_NAME, null, null);
	}
}
