package qrail.com.interactivemap.database;

import android.content.ContentValues;
import android.database.Cursor;

import qrail.com.interactivemap.database.MetroDbHelper;
import qrail.com.interactivemap.database.ParentAPI;

public class FareAPI extends ParentAPI {

	public static final String TABLE_NAME = "FareTable";
//	public static final String TABLE_NAME = "NewMetroFare";
	public static final String COLUMN_STATION_ID = "station_id";

	public Cursor getAllStationsFare(MetroDbHelper mHelper) {
		return mHelper.fetchData(TABLE_NAME, null, null);
	}


	public Cursor getRecord(MetroDbHelper mHelper, String sPoint, String ePoint) {
		ePoint = '"' + ePoint + '"';
		return mHelper.fetchData(TABLE_NAME, new String[] { ePoint },
				COLUMN_STATION_ID + "='" + sPoint + "'");
	}

	public Cursor getRow(MetroDbHelper mHelper, String id) {
		return mHelper.fetchData(TABLE_NAME, null, COLUMN_STATION_ID + "='"
				+ id + "'");
	}

	public long updateRow(MetroDbHelper mHelper, ContentValues value,
			String id, String[] whereArgs) {
		return mHelper.updateData(TABLE_NAME, value, COLUMN_STATION_ID + "='"
				+ id + "'", whereArgs);
	}
}
