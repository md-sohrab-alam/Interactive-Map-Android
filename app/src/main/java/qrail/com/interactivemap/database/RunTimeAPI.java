package qrail.com.interactivemap.database;

import android.database.Cursor;

public class RunTimeAPI extends ParentAPI {

	public static final String TABLE_NAME = "RunTime";
	public static final String COLUMN_STATION_NO = "Sno";

	public Cursor getAllAirportStationFare(MetroDbHelper mHelper) {
		return mHelper.fetchData(TABLE_NAME, null, null);
	}

	@Override
	public Cursor getRecord(MetroDbHelper mHelper, String sPoint, String ePoint) {
		ePoint = "\"" + ePoint + "\"";
		return mHelper.fetchData(TABLE_NAME, new String[] { ePoint },
				COLUMN_STATION_NO + "='" + sPoint + "'");
	}

	public Cursor getRow(MetroDbHelper mHelper, String id) {
		return mHelper.fetchData(TABLE_NAME, null, COLUMN_STATION_NO + " = '"
				+ id + "'");
	}

	public Cursor getRow1(MetroDbHelper mHelper, String id) {
		return mHelper.fetchData(TABLE_NAME, null, COLUMN_STATION_NO + " = "
				+ id);
	}
}
