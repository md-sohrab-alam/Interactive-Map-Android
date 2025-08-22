package qrail.com.interactivemap.database;

import android.database.Cursor;

import qrail.com.interactivemap.database.MetroDbHelper;

public class StationsLinesAPI {

	public static final String TABLE_NAME = "Stations_Lines";

	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_ID_LINE = "id_line";

	public Cursor getAllStationLines(MetroDbHelper mHelper) {
		return mHelper.fetchData(TABLE_NAME, null, null);

	}

}
