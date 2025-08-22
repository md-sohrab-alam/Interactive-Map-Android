package qrail.com.interactivemap.database;

import android.database.Cursor;

import qrail.com.interactivemap.database.MetroDbHelper;

public class DtcInfoAPI {
	public static final String TABLE_NAME = "DtcInfo";
	public static final String COLUMN_STATION_ID = "StationID";
	public static final String COLUMN_DTC_BUS_ROUTE_NO = "DTCBusRouteNO";
	public static final String COLUMN_ORIGINATING_FROM = "OriginatingFrom";
	public static final String COLUMN_DESTINATION = "Destination";

	public Cursor getAllDtcInfo(MetroDbHelper mHelper) {
		return mHelper.fetchData(TABLE_NAME, null, null);

	}
}
