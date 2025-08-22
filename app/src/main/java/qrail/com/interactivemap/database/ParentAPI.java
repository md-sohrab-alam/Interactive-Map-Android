package qrail.com.interactivemap.database;

import android.database.Cursor;

import qrail.com.interactivemap.database.MetroDbHelper;

public abstract class ParentAPI {

	public abstract Cursor getRecord(MetroDbHelper mHelper, String sPoint,
									 String ePoint);

}
