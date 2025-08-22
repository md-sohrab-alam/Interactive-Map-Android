package qrail.com.interactivemap.database;

import android.database.Cursor;

public class ConnectionsAPI {
	public static final String TABLE_NAME = "Connections";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_CONNECTION = "connection";
	public static final String COLUMN_DISTANCE = "distance";
	public static final String COLUMN_CONNECTION_COLOR = "connection_color";
	public static final String COLUMN_TOWARDS = "towards";

	public Cursor getAllConnections(MetroDbHelper mHelper) {
		return mHelper.fetchData(TABLE_NAME, null, null);
	}

}
