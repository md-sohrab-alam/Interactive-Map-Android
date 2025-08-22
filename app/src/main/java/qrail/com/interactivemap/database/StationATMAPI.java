package qrail.com.interactivemap.database;

import android.database.Cursor;

import qrail.com.interactivemap.database.MetroDbHelper;

public class StationATMAPI {

	public static final String TABLE_NAME = "StationATM";
	public static final String COLUMN_STATION_ID = "StationId";
	public static final String COLUMN_STATION_NAME = "StationName";
	public static final String COLUMN_AVAILABLE_ATM = "ATMAvailable";

	public Cursor getAllStationATM(MetroDbHelper mHelper) {
		return mHelper.fetchData(TABLE_NAME, null, null);

	}

}
