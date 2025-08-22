package qrail.com.interactivemap.database;

import android.database.Cursor;

import qrail.com.interactivemap.database.MetroDbHelper;

public class FeederBusAPI {

	public static final String TABLE_NAME = "FeederBus";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_FROM_ADDRESS = "fromAddress";
	public static final String COLUMN_TO_ADDRESS = "toAddress";
	public static final String COLUMN_ROUTE_NO = "routeNo";
	public static final String COLUMN_VIA = "via";

	public Cursor getAllFeederBusInfo(MetroDbHelper mHelper) {
		return mHelper.fetchData(TABLE_NAME, null, null);

	}

}
