package qrail.com.interactivemap.database;

import android.database.Cursor;

public class AVMMasterAPI {

	public static final String TABLE_NAME = "AVMMaster";
	public static final String COLUMN_STATION_ID = "StationId";
	public static final String COLUMN_STATION_NAME = "StationName";
	public static final String COLUMN_TOTAL_AVM = "TotalAVM";

	public Cursor getAllAVM(MetroDbHelper mHelper) {
		return mHelper.fetchData(TABLE_NAME, null, null);

	}

}
