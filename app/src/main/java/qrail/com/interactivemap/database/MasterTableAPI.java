package qrail.com.interactivemap.database;

import android.database.Cursor;

import qrail.com.interactivemap.database.MetroDbHelper;

public class MasterTableAPI {

	public static final String TABLE_NAME = "MasterTable";

	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_IS_BUS = "isBus";
	public static final String COLUMN_LANDLINE_NO = "LandlineNo";
	public static final String COLUMN_MOBILE_NO = "MobileNumber";
	public static final String COLUMN_IS_FEEDER = "isFeeder";
	public static final String COLUMN_IS_PARKING = "isParking";
	public static final String COLUMN_STATION = "STATION";
	public static final String COLUMN_CODE = "Code";
	public static final String COLUMN_LONG = "Long";
	public static final String COLUMN_LAT = "Lat";
	public static final String COLUMN_IS_JUCTION = "isJunction";
	public static final String COLUMN_XCORD = "xCord";
	public static final String COLUMN_YCORD = "yCord";
	public static final String COLUMN_IS_UNDERGROUNG = "isUnderGround";

	public Cursor getAllStationsData(MetroDbHelper mHelper) {
		return mHelper.fetchData(TABLE_NAME, null, null);

	}

}
