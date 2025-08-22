package qrail.com.interactivemap.database;

import android.database.Cursor;

public class AirportRunTimeAPI extends ParentAPI {
	public static final String TABLE_NAME = "AirportRunTime";
	public static final String COLUMN_STATION_ID = "station_id";

	public Cursor getAllAirportStationTime(MetroDbHelper mHelper) {
		return mHelper.fetchData(TABLE_NAME, null, null);
	}

	public Cursor getRecord(MetroDbHelper mHelper, String sPoint, String ePoint) {
		ePoint = '"' + ePoint + '"';
		return mHelper.fetchData(TABLE_NAME, new String[] { ePoint },
				COLUMN_STATION_ID + "='" + sPoint + "'");

	}
}
