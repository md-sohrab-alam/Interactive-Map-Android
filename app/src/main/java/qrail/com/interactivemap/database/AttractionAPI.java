package qrail.com.interactivemap.database;

import android.database.Cursor;

public class AttractionAPI {

	public static final String TABLE_NAME = "Attractions";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_IMAGE_FILE = "imageFile";
	public static final String COLUMN_NEARBY_STATIONS = "nearByStation";
	public static final String COLUMN_CATEGORY_TYPE = "categoryType";
	public static final String COLUMN_LAT = "lat";
	public static final String COLUMN_LONG = "long";
	public static final String COLUMN_CONTENT = "content";
	public static final String COLUMN_DISTEANCE = "distance";
	public static final String COLUMN_ADDRESS = "Address";

	public Cursor getAllAttractions(MetroDbHelper mHelper) {
		return mHelper.fetchData(TABLE_NAME, null, null);

	}

}
