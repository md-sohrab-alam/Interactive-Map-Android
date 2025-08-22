package qrail.com.interactivemap.database;

import android.database.Cursor;


public class TrainTimingsAPI {

	public static final String TABLE_NAME = "TrainTimings";

	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_TOWARDS = "towards";
	public static final String COLUMN_TIME = "time";
	public static final String COLUMN_IS_START_TIME = "isStartTime";

	public Cursor getAllTrainTimings(MetroDbHelper mHelper) {
		return mHelper.fetchData(TABLE_NAME, null, null);

	}

}
