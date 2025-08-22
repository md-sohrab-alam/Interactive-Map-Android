package qrail.com.interactivemap.database;

import android.database.Cursor;

public class DistanceAPI extends ParentAPI {
	public static final String TABLE_NAME = "NewDistance";
	public static final String COLUMN_STATION_ID = "StationID";

	public Cursor getAllAirportStationFare(MetroDbHelper mHelper) {
		return mHelper.fetchData(TABLE_NAME, null, null);
	}

	@Override
	public Cursor getRecord(MetroDbHelper mHelper, String sPoint, String ePoint) {
		ePoint = '"' + ePoint + '"';
		return mHelper.fetchData(TABLE_NAME, new String[] { ePoint },
				COLUMN_STATION_ID + "='" + sPoint + "'");
	}
}
