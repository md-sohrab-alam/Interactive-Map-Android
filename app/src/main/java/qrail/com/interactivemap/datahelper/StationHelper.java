package qrail.com.interactivemap.datahelper;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.text.TextUtils;

import qrail.com.interactivemap.database.ATMMasterAPI;
import qrail.com.interactivemap.database.AVMMasterAPI;
import qrail.com.interactivemap.database.ConnectionsAPI;
import qrail.com.interactivemap.database.DtcInfoAPI;
import qrail.com.interactivemap.database.FeederBusAPI;
import qrail.com.interactivemap.database.GatesInfoAPI;
import qrail.com.interactivemap.database.MasterTableAPI;
import qrail.com.interactivemap.database.MetroDbHelper;
import qrail.com.interactivemap.database.MetroLinesAPI;
import qrail.com.interactivemap.database.PlatformInfoAPI;
import qrail.com.interactivemap.database.StationATMAPI;
import qrail.com.interactivemap.database.StationsLinesAPI;
import qrail.com.interactivemap.database.TrainTimingsAPI;
import qrail.com.interactivemap.dataobjects.ATMMaster;
import qrail.com.interactivemap.dataobjects.Connections;
import qrail.com.interactivemap.dataobjects.DtcInfo;
import qrail.com.interactivemap.dataobjects.FeederBus;
import qrail.com.interactivemap.dataobjects.GatesInfo;
import qrail.com.interactivemap.dataobjects.MetroLines;
import qrail.com.interactivemap.dataobjects.PlatformInfo;
import qrail.com.interactivemap.dataobjects.Station;
import qrail.com.interactivemap.dataobjects.StationsLines;
import qrail.com.interactivemap.dataobjects.TrainTimings;

public class StationHelper {

    private ArrayList<Station> stationList;
    private static StationHelper mInstance;
    private HashMap<String, MetroLines> metroLines;
    private OnDataQueryListener mListener;
    private AsyncTask<Void, Void, Void> asyncFill;
    private Context mContext;

    public StationHelper(Context context) {
        mContext = context;
        stationList = new ArrayList<Station>();
        metroLines = new HashMap<String, MetroLines>();
    }

    public static StationHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new StationHelper(context);
        }
        return mInstance;
    }

    public ArrayList<Station> getStationsList() {
        return stationList;
    }

    public String ifStationExists(String name) {
        for (int i = 0; i < stationList.size(); i++) {

            if (name.contentEquals(stationList.get(i).getName())) {
                return stationList.get(i).getStationID();
            }
        }

        return null;
    }

    public boolean isDiversionOfSameLine(String src, String dest) {
        if (metroLines.get(src).getDiversionOf()
                .equals(metroLines.get(dest).getDiversionOf())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isChildLine(String src, String dest) {
        if (src.equals(metroLines.get(dest).getDiversionOf())) {
            return true;
        } else {
            return false;
        }
    }

    public void fillData() {

        MetroDbHelper.getInstance(mContext).openDataBase();

        MasterTableAPI masterTableAPI = new MasterTableAPI();
        Cursor masterTableCursor = masterTableAPI
                .getAllStationsData(MetroDbHelper.getInstance(mContext));

        // fill masterTableData in all stations
        if (masterTableCursor.moveToFirst()) {

            do {

                String id = masterTableCursor.getString(masterTableCursor
                        .getColumnIndex(MasterTableAPI.COLUMN_ID));
                id = id.trim();
                String name = masterTableCursor.getString(masterTableCursor
                        .getColumnIndex(MasterTableAPI.COLUMN_STATION));
                String isBus = masterTableCursor.getString(masterTableCursor
                        .getColumnIndex(MasterTableAPI.COLUMN_IS_BUS));
                String landLineNo = masterTableCursor
                        .getString(masterTableCursor
                                .getColumnIndex(MasterTableAPI.COLUMN_LANDLINE_NO));
                String mobileNo = masterTableCursor.getString(masterTableCursor
                        .getColumnIndex(MasterTableAPI.COLUMN_MOBILE_NO));
                String lat = masterTableCursor.getString(masterTableCursor
                        .getColumnIndex(MasterTableAPI.COLUMN_LAT));
                String longt = masterTableCursor.getString(masterTableCursor
                        .getColumnIndex(MasterTableAPI.COLUMN_LONG));
                String isFeeder = masterTableCursor.getString(masterTableCursor
                        .getColumnIndex(MasterTableAPI.COLUMN_IS_FEEDER));
                String isParking = masterTableCursor
                        .getString(masterTableCursor
                                .getColumnIndex(MasterTableAPI.COLUMN_IS_PARKING));
                String code = masterTableCursor.getString(masterTableCursor
                        .getColumnIndex(MasterTableAPI.COLUMN_CODE));
                String isJunction = masterTableCursor
                        .getString(masterTableCursor
                                .getColumnIndex(MasterTableAPI.COLUMN_IS_JUCTION));

                int xCord = 0;
                int yCord = 0;

                try {
                    xCord = Integer
                            .parseInt(masterTableCursor.getString(masterTableCursor
                                    .getColumnIndex(MasterTableAPI.COLUMN_XCORD)));
                    yCord = Integer
                            .parseInt(masterTableCursor.getString(masterTableCursor
                                    .getColumnIndex(MasterTableAPI.COLUMN_YCORD)));
                } catch (Exception e) {
                }

                String isUnderground = masterTableCursor
                        .getString(masterTableCursor
                                .getColumnIndex(MasterTableAPI.COLUMN_IS_UNDERGROUNG));

                Station station = new Station(id.trim(), isBus, landLineNo,
                        mobileNo, isFeeder, isParking, name, code, lat, longt,
                        xCord, yCord, isUnderground, isJunction);
                stationList.add(station);
                // insert in hashMap, position of station in arrayList of
                // stations

                // stationHashMapByName.put(name, id);
            } while (masterTableCursor.moveToNext());
            masterTableCursor.close();

        }

        StationsLinesAPI stationsLinesAPI = new StationsLinesAPI();
        Cursor stationLinesCursor = stationsLinesAPI
                .getAllStationLines(MetroDbHelper.getInstance(mContext));
        getStationLines(stationLinesCursor);
        stationLinesCursor.close();

        ConnectionsAPI connectionAPI = new ConnectionsAPI();
        Cursor connectionsCursor = connectionAPI
                .getAllConnections(MetroDbHelper.getInstance(mContext));
        getConnections(connectionsCursor);
        connectionsCursor.close();

        PlatformInfoAPI platformInfoAPI = new PlatformInfoAPI();
        Cursor platformInfoCursor = platformInfoAPI
                .getAllPlatformInfo(MetroDbHelper.getInstance(mContext));
        getPlatforms(platformInfoCursor);
        platformInfoCursor.close();

        DtcInfoAPI dtcInfoAPI = new DtcInfoAPI();
        Cursor dtcInfoCursor = dtcInfoAPI.getAllDtcInfo(MetroDbHelper
                .getInstance(mContext));
        getDtcInfo(dtcInfoCursor);
        dtcInfoCursor.close();

        TrainTimingsAPI trainTimingsAPI = new TrainTimingsAPI();
        Cursor trainTimingCursor = trainTimingsAPI
                .getAllTrainTimings(MetroDbHelper.getInstance(mContext));
        getTrainTimings(trainTimingCursor);
        trainTimingCursor.close();

        MetroLinesAPI metroLinesAPI = new MetroLinesAPI();
        Cursor metroLinesCursor = metroLinesAPI.getAllMetroLines(MetroDbHelper
                .getInstance(mContext));
        getMetroLines(metroLinesCursor);
        metroLinesCursor.close();

        GatesInfoAPI gatesInfoAPI = new GatesInfoAPI();
        Cursor gatesInfoCursor = gatesInfoAPI.getAllGatesInfo(MetroDbHelper
                .getInstance(mContext));
        getGatesInfo(gatesInfoCursor);
        gatesInfoCursor.close();

        FeederBusAPI feederBusAPI = new FeederBusAPI();
        Cursor feederBusCursor = feederBusAPI.getAllFeederBusInfo(MetroDbHelper
                .getInstance(mContext));
        getFeederBus(feederBusCursor);
        feederBusCursor.close();

        ATMMasterAPI ATMMasterAPI = new ATMMasterAPI();
        Cursor ATMMasterAPICursor = ATMMasterAPI.getAllATM(MetroDbHelper
                .getInstance(mContext));
        HashMap<String, ATMMaster> map = getATMMaster(ATMMasterAPICursor);

        StationATMAPI stationATMAPI = new StationATMAPI();
        Cursor cursor = stationATMAPI.getAllStationATM(MetroDbHelper
                .getInstance(mContext));
        getATM(cursor, map);

        AVMMasterAPI AVMMasterAPI = new AVMMasterAPI();
        Cursor AVMMasterAPICursor = AVMMasterAPI.getAllAVM(MetroDbHelper
                .getInstance(mContext));
        getAVM(AVMMasterAPICursor, map);

//		RouteStoreAPI metroRouteAPI = new RouteStoreAPI();
//		Cursor metroRouteCursor = metroRouteAPI.getAllMetroRoute(MetroDbHelper
//				.getInstance(mContext));
//		getMetroRoute(metroRouteCursor);
//		metroRouteCursor.close();

        MetroDbHelper.getInstance(mContext).close();
        // MetroDbHelper.getInstance(mContext).createDataBase();

    }

	/*private void getMetroRoute(Cursor cursor) {

		if (cursor.moveToFirst()) {
			do {
				String stationId = cursor.getString(cursor.getColumnIndex(RouteStoreAPI.TABLE_NAME));

			} while (cursor.moveToNext());
		}
	}*/

    private void getAVM(Cursor cursor, HashMap<String, ATMMaster> map) {

        if (cursor.moveToFirst()) {
            do {
                String stationid = cursor.getString(cursor
                        .getColumnIndex(AVMMasterAPI.COLUMN_STATION_ID));
                String stationName = cursor.getString(cursor
                        .getColumnIndex(AVMMasterAPI.COLUMN_STATION_NAME));
                String totalAVM = cursor.getString(cursor
                        .getColumnIndex(AVMMasterAPI.COLUMN_TOTAL_AVM));

                if (stationid != null && !stationid.equalsIgnoreCase("0")
                        && !stationid.equalsIgnoreCase("1")) {
                    Station station = getStationById(stationid);

                    if (station != null)
                        station.setStationAVM(totalAVM);
                }
            } while (cursor.moveToNext());
        }

    }

    private void getATM(Cursor cursor, HashMap<String, ATMMaster> map) {

        if (cursor.moveToFirst()) {
            do {
                String stationid = cursor.getString(cursor
                        .getColumnIndex(StationATMAPI.COLUMN_STATION_ID));
                String stationName = cursor.getString(cursor
                        .getColumnIndex(StationATMAPI.COLUMN_STATION_NAME));
                String availableATM = cursor.getString(cursor
                        .getColumnIndex(StationATMAPI.COLUMN_AVAILABLE_ATM));

                String[] atms = availableATM.split(",");

                if (stationid != null && !stationid.equalsIgnoreCase("0")
                        && !stationid.equalsIgnoreCase("1")) {
                    Station station = getStationById(stationid);

                    if (station != null) {
                        ArrayList<ATMMaster> atm = station.getStationATMs();

                        if (atm == null) {
                            atm = new ArrayList<ATMMaster>();
                        }

                        for (int i = 0; i < atms.length; i++) {
                            try {
                                int a = Integer.parseInt(atms[i]);
                                atm.add(map.get(atms[i]));
                            } catch (Exception e) {

                            }
                        }

                        station.setStationATMs(atm);
                    }
                }
            } while (cursor.moveToNext());
        }

    }

    private HashMap<String, ATMMaster> getATMMaster(Cursor ATMMasterCursor) {
        HashMap<String, ATMMaster> map = new HashMap<String, ATMMaster>();

        if (ATMMasterCursor.moveToFirst()) {
            do {
                String atmId = ATMMasterCursor.getString(ATMMasterCursor
                        .getColumnIndex(ATMMasterAPI.COLUMN_ID));
                String atmBankName = ATMMasterCursor.getString(ATMMasterCursor
                        .getColumnIndex(ATMMasterAPI.COLUMN_BANK_NAME));
                String atmBankImage = ATMMasterCursor.getString(ATMMasterCursor
                        .getColumnIndex(ATMMasterAPI.COLUMN_BANK_IMAGE_NAME));
                ATMMaster item = new ATMMaster(atmId, atmBankName, atmBankImage);

                map.put(atmId, item);

            } while (ATMMasterCursor.moveToNext());
        }

        return map;
    }

    private void getFeederBus(Cursor feederBusCursor) {
        if (feederBusCursor.moveToFirst()) {
            do {
                String stationid = feederBusCursor.getString(feederBusCursor
                        .getColumnIndex(FeederBusAPI.COLUMN_ID));
                String fromAddress = feederBusCursor.getString(feederBusCursor
                        .getColumnIndex(FeederBusAPI.COLUMN_FROM_ADDRESS));
                String toAddress = feederBusCursor.getString(feederBusCursor
                        .getColumnIndex(FeederBusAPI.COLUMN_TO_ADDRESS));
                String routeNo = feederBusCursor.getString(feederBusCursor
                        .getColumnIndex(FeederBusAPI.COLUMN_ROUTE_NO));
                String via = feederBusCursor.getString(feederBusCursor
                        .getColumnIndex(FeederBusAPI.COLUMN_VIA));

                if (stationid != null && !stationid.equalsIgnoreCase("0")
                        && !stationid.equalsIgnoreCase("1")) {
                    Station station = getStationById(stationid);

                    if (station != null) {
                        ArrayList<FeederBus> feederBus = station.getFeederInfo();

                        if (feederBus == null) {
                            feederBus = new ArrayList<FeederBus>();
                        }
                        feederBus.add(new FeederBus(stationid, fromAddress,
                                toAddress, routeNo, via));
                        station.setFeederInfo(feederBus);
                    }
                }
            } while (feederBusCursor.moveToNext());
        }
    }

    public HashMap<String, MetroLines> getMetroLines() {
        return metroLines;
    }

    public MetroLines getMetroLineByID(String id) {

        return metroLines.get(id);
    }

    public Station getStationById(String id) {
        Station station = null;

        for (int i = 0; i < stationList.size(); i++) {

            if (id.trim().contentEquals(stationList.get(i).getStationID())) {
                station = stationList.get(i);
                return station;
            }
        }

        return station;
    }

    /**
     * read GatesInfo data and fill every gateInfo in appropriate station
     *
     * @param gatesInfoCursor
     */
    private void getGatesInfo(Cursor gatesInfoCursor) {

        if (gatesInfoCursor.moveToFirst()) {
            do {

                String stationid = gatesInfoCursor.getString(gatesInfoCursor
                        .getColumnIndex(GatesInfoAPI.COLUMN_ID));
                String gates = gatesInfoCursor.getString(gatesInfoCursor
                        .getColumnIndex(GatesInfoAPI.COLUMN_GATE));
                String towards = gatesInfoCursor.getString(gatesInfoCursor
                        .getColumnIndex(GatesInfoAPI.COLUMN_TOWARDS));

                Station station = getStationById(stationid);
                if (station != null) {
                    ArrayList<GatesInfo> gatesInfoList = station.getGatesInfo();
                    if (gatesInfoList == null) {
                        gatesInfoList = new ArrayList<GatesInfo>();
                    }

                    gatesInfoList.add(new GatesInfo(stationid, gates, towards));
                    station.setGatesInfo(gatesInfoList);
                }
            } while (gatesInfoCursor.moveToNext());
        }

    }

    /**
     * read TrainTimings data and fill every trainTimings in appropriate station
     *
     * @param trainTimingCursor
     */
    private void getTrainTimings(Cursor trainTimingCursor) {

        if (trainTimingCursor.moveToFirst()) {
            do {

                String stationID = trainTimingCursor
                        .getString(trainTimingCursor
                                .getColumnIndex(TrainTimingsAPI.COLUMN_ID));

                String towards = trainTimingCursor.getString(trainTimingCursor
                        .getColumnIndex(TrainTimingsAPI.COLUMN_TOWARDS));
                String time = trainTimingCursor.getString(trainTimingCursor
                        .getColumnIndex(TrainTimingsAPI.COLUMN_TIME));
                String isStartTime = trainTimingCursor
                        .getString(trainTimingCursor
                                .getColumnIndex(TrainTimingsAPI.COLUMN_IS_START_TIME));

                Station station = getStationById(stationID);
                if (station != null) {
                    ArrayList<TrainTimings> trainTimingsList = station
                            .getTrainTimings();
                    if (trainTimingsList == null) {
                        trainTimingsList = new ArrayList<TrainTimings>();
                    }

                    trainTimingsList.add(new TrainTimings(stationID, towards, time,
                            isStartTime));
                    station.setTrainTimings(trainTimingsList);
                }
            } while (trainTimingCursor.moveToNext());
        }

    }

    /**
     * read DtcInfo data and fill every dtcInfo in appropriate station
     *
     * @param dtcInfoCursor
     */
    private void getDtcInfo(Cursor dtcInfoCursor) {

        if (dtcInfoCursor.moveToFirst()) {
            do {

                String stationid = dtcInfoCursor.getString(dtcInfoCursor
                        .getColumnIndex(DtcInfoAPI.COLUMN_STATION_ID));
                String dtcBusRouteNO = dtcInfoCursor.getString(dtcInfoCursor
                        .getColumnIndex(DtcInfoAPI.COLUMN_DTC_BUS_ROUTE_NO));
                String originatingFrom = dtcInfoCursor.getString(dtcInfoCursor
                        .getColumnIndex(DtcInfoAPI.COLUMN_ORIGINATING_FROM));
                String destination = dtcInfoCursor.getString(dtcInfoCursor
                        .getColumnIndex(DtcInfoAPI.COLUMN_DESTINATION));

                Station station = getStationById(stationid);
                if (station != null) {

                    ArrayList<DtcInfo> dtcInfoList = station.getDtcInfo();

                    if (dtcInfoList == null) {
                        dtcInfoList = new ArrayList<DtcInfo>();
                    }
                    dtcInfoList.add(new DtcInfo(stationid, dtcBusRouteNO,
                            originatingFrom, destination));
                    station.setDtcInfo(dtcInfoList);
                }
            } while (dtcInfoCursor.moveToNext());
        }

    }

    /**
     * read Platforms data and fill every platforms in appropriate station
     *
     * @param platformInfoCursor
     */
    private void getPlatforms(Cursor platformInfoCursor) {

        if (platformInfoCursor.moveToFirst()) {
            do {

                String stationid = platformInfoCursor
                        .getString(platformInfoCursor
                                .getColumnIndex(PlatformInfoAPI.COLUMN_ID));
                String platformId = platformInfoCursor
                        .getString(platformInfoCursor
                                .getColumnIndex(PlatformInfoAPI.COLUMN_PLATFORM_ID));
                String towards = platformInfoCursor
                        .getString(platformInfoCursor
                                .getColumnIndex(PlatformInfoAPI.COLUMN_TOWARDS));

                Station station = getStationById(stationid);
                if (station != null) {

                    ArrayList<PlatformInfo> platformInfoList = station
                            .getPlatformInfo();
                    if (platformInfoList == null) {
                        platformInfoList = new ArrayList<PlatformInfo>();
                    }
                    platformInfoList.add(new PlatformInfo(stationid, platformId,
                            towards));
                    station.setPlatformInfo(platformInfoList);
                }
            } while (platformInfoCursor.moveToNext());
        }
    }

    /**
     * read Connections data and fill every connections in appropriate station
     *
     * @param connectionsCursor
     */
    private void getConnections(Cursor connectionsCursor) {

        if (connectionsCursor.moveToFirst()) {

            do {

                String stationid = connectionsCursor
                        .getString(connectionsCursor
                                .getColumnIndex(ConnectionsAPI.COLUMN_ID));

                String connection = connectionsCursor
                        .getString(connectionsCursor
                                .getColumnIndex(ConnectionsAPI.COLUMN_CONNECTION));

                String distance = connectionsCursor.getString(connectionsCursor
                        .getColumnIndex(ConnectionsAPI.COLUMN_DISTANCE));
                String conectionColor = connectionsCursor
                        .getString(connectionsCursor
                                .getColumnIndex(ConnectionsAPI.COLUMN_CONNECTION_COLOR));
                String towards = connectionsCursor.getString(connectionsCursor
                        .getColumnIndex(ConnectionsAPI.COLUMN_TOWARDS));

                Station station = getStationById(stationid);

                if (station != null) {
                    ArrayList<Connections> connectionsList = station
                            .getConnections();
                    connectionsList.add(new Connections(stationid, connection,
                            distance, conectionColor, towards));
                    // station.setConnections(connectionsList);
                }

            } while (connectionsCursor.moveToNext());

        }
    }

    private void getMetroLines(Cursor metroLinesCursor) {
        if (metroLinesCursor.moveToFirst()) {
            do {

                String id = metroLinesCursor.getString(metroLinesCursor
                        .getColumnIndex(MetroLinesAPI.COLUMN_ID));
                String name = metroLinesCursor.getString(metroLinesCursor
                        .getColumnIndex(MetroLinesAPI.COLUMN_NAME));
                String distance = metroLinesCursor.getString(metroLinesCursor
                        .getColumnIndex(MetroLinesAPI.COLUMN_DISTANCE));

                String stops = metroLinesCursor.getString(metroLinesCursor
                        .getColumnIndex(MetroLinesAPI.COLUMN_STOPS));
                String pTime = metroLinesCursor.getString(metroLinesCursor
                        .getColumnIndex(MetroLinesAPI.COLUMN_PTIME));
                String npTime = metroLinesCursor.getString(metroLinesCursor
                        .getColumnIndex(MetroLinesAPI.COLUMN_NPTIME));

                String sPoint = metroLinesCursor.getString(metroLinesCursor
                        .getColumnIndex(MetroLinesAPI.COLUMN_SPOINT));
                String ePoint = metroLinesCursor.getString(metroLinesCursor
                        .getColumnIndex(MetroLinesAPI.COLUMN_EPOINT));
                String fare = metroLinesCursor.getString(metroLinesCursor
                        .getColumnIndex(MetroLinesAPI.COLUMN_FARE));
                String diversionOf = metroLinesCursor
                        .getString(metroLinesCursor
                                .getColumnIndex(MetroLinesAPI.COLUMN_DIVERSION_OF));

                String diversions = metroLinesCursor.getString(metroLinesCursor
                        .getColumnIndex(MetroLinesAPI.COLUMN_DIVERSIONS));
                String[] div = null;
                if (!TextUtils.isEmpty(diversions)) {
                    div = diversions.split(",");
                }
                metroLines.put(id, new MetroLines(id, name, stops, pTime,
                        npTime, sPoint, ePoint, fare, diversionOf, distance,
                        div));

            } while (metroLinesCursor.moveToNext());
        }
    }

    /**
     * read station_Lines data and fill every stationLine in appropriate station
     *
     * @param stationLinesCursor
     */
    private void getStationLines(Cursor stationLinesCursor) {

        if (stationLinesCursor.moveToFirst()) {
            do {

                String stationid = stationLinesCursor
                        .getString(stationLinesCursor
                                .getColumnIndex(StationsLinesAPI.COLUMN_ID));

                String id_line = stationLinesCursor
                        .getString(stationLinesCursor
                                .getColumnIndex(StationsLinesAPI.COLUMN_ID_LINE));

                Station station = getStationById(stationid);

                ArrayList<StationsLines> stationsLinesList = station != null ? station
                        .getStationLines() : null;

                if (stationsLinesList == null) {
                    stationsLinesList = new ArrayList<StationsLines>();
                }
                stationsLinesList.add(new StationsLines(stationid, id_line));
                if (station != null)
                    station.setStationLines(stationsLinesList);

            } while (stationLinesCursor.moveToNext());
        }
    }

    public void fillDataAsync(OnDataQueryListener listener) {
        this.mListener = listener;
        asyncFill = new FillDataTask().execute();
    }

    private class FillDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            fillData();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if (mListener != null) {
                mListener.onComplete();
            }
        }
    }

    public void cancelFilling() {
        if (asyncFill != null) {
            asyncFill.cancel(false);
        }
    }
}
