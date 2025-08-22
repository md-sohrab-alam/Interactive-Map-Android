package qrail.com.interactivemap.database;

import android.database.Cursor;

import qrail.com.interactivemap.database.MetroDbHelper;
import qrail.com.interactivemap.database.ParentAPI;

/**
 * Created by vikaskumar on 20/11/17.
 */

public class RapidMetroFareAPI extends ParentAPI {
    public static final String TABLE_NAME = "RapidMetroFare";
    public static final String COLUMN_STATION_ID = "StationId";


    public Cursor getRecord(MetroDbHelper mHelper, String sPoint, String ePoint) {
        ePoint = '"' + ePoint + '"';
        return mHelper.fetchData(TABLE_NAME, new String[]{ePoint},
                COLUMN_STATION_ID + "='" + sPoint + "'");
    }
}
