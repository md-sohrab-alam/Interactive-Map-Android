package qrail.com.interactivemap.database;

import android.database.Cursor;

import qrail.com.interactivemap.database.MetroDbHelper;

public class RouteStoreAPI {

    public static final String TABLE_NAME = "RouteStore";
    public static final String SOURCE_STATION_ID = "SourceStationID";
    public static final String DESTINATION_STATION_ID = "DestinationStationID";
    public static final String FARE = "Fare";
    public static final String TIME = "Time";
    public static final String CHANGES = "Changes";
    public static final String LINE_ID = "LinesID";
    public static final String STATION_IDS = "StationID";
    public static final String TOWARDS = "Towards";

    public Cursor getAllMetroRoute(MetroDbHelper mHelper, String sPoint, String ePoint) {
        return mHelper.fetchData(TABLE_NAME, SOURCE_STATION_ID + " = ? AND " + DESTINATION_STATION_ID + " = ? ", new String[]{sPoint, ePoint}, TIME);
    }
}
