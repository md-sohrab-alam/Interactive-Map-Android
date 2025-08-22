package qrail.com.interactivemap.stationroute;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import qrail.com.interactivemap.datahelper.StationHelper;
import qrail.com.interactivemap.dataobjects.Connections;
import qrail.com.interactivemap.dataobjects.DiversionStation;
import qrail.com.interactivemap.dataobjects.Result;
import qrail.com.interactivemap.dataobjects.ResultNode;
import qrail.com.interactivemap.dataobjects.Station;

/**
 * Created by Prasun Jha(prasun.jha@finoit.co.in) on 16/3/18.
 */
public class StationRoutes {

    private String start;
    private String end;
    private ArrayList<Result> resultList;
    private String airportLine = "14";
    private boolean isAirportLine;
    private ArrayList<String> airportStart;
    private ArrayList<String> airportEnd;

    private int seconds;
    private OnFillDataListener mListener;
    private static final int SORT_BY_TIME = 0;
    private static final int SORT_BY_FARE = 1;
    private static final int SORT_BY_SWITCHES = 2;
    private static final int SORT_BY_STOPS = 3;

    private StationHelper mHelper;
    private int runTime;
    private AsyncTask<Void, Void, Void> fillDataAsync;
    private Context mContext;

    public StationRoutes(Context context, String start, String end) {
        mContext = context;
        this.start = start;
        this.end = end;
        resultList = new ArrayList<>();
        airportStart = new ArrayList<>();
        airportEnd = new ArrayList<>();
        mHelper = StationHelper.getInstance(context);
    }

    public ArrayList<Result> getResultList() {
        return resultList;
    }

    public void fillDataAsync(OnFillDataListener listener) {
        this.mListener = listener;
        fillDataAsync = new FillDataTask().execute();
    }

    private class FillDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            fetchData();
            fillData();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            Collections.sort(resultList, new ResultComparator(0));
            filterRoute();
            if (mListener != null) {
                mListener.onComplete(resultList);
            }
        }

        private void filterRoute() {

            /**
             * This commented code block removes the entry if fare is ">=" between two routes & shows the best route
             */
//            Result first = resultList.get(0);
//            for (int i = 1; i < resultList.size(); i++) {
//                Result temp = resultList.get(i);
//                if (Integer.parseInt(temp.getRunTime())
//                        - Integer.parseInt(first.getRunTime()) > 10) {
//
//                    if (Integer.parseInt(temp.getFare()) >= Integer
//                            .parseInt(first.getFare())) {
//                        resultList.remove(i);
//                        i = i - 1;
//                    }
//                }
//            }


            if ((Integer.parseInt(start) >= 401 && Integer.parseInt(start) <= 404)
                    || (Integer.parseInt(end) >= 401 && Integer.parseInt(start) <= 404)) {

                Collections.sort(resultList, new ResultComparator(SORT_BY_STOPS));

            } else {
                Collections.sort(resultList, new ResultComparator(SORT_BY_SWITCHES));
            }

            if (resultList.size() > 2) {
                if (resultList.get(0).getFare()
                        .equals(resultList.get(1).getFare())
                        && resultList.get(0).getRunTime()
                        .equals(resultList.get(1).getRunTime())
                        && resultList.get(0).getSwitches() == resultList.get(1)
                        .getSwitches()) {
                    Collections.sort(resultList, new ResultComparator(
                            SORT_BY_STOPS));

                }
                for (int i = 2; i < resultList.size(); i++) {
                    resultList.remove(i);
                }
            }
        }
    }

    public void fillData() {
        String fromStation = resultList.get(0).getResultStations().get(0).getStation().getName();
        // iterate through list of all possible routes
        for (int i = 0; i < resultList.size(); i++) {
            runTime = 0;
            seconds = 0;
            if (!airportStart.isEmpty())
                airportStart.clear();
            if (!airportEnd.isEmpty())
                airportEnd.clear();
            isAirportLine = false;
            // extract individual routes
            Result result = resultList.get(i);
            // extraction list of stations of every route
            ArrayList<ResultNode> stationList = result.getResultStations();
            String lastColor = "";
            // iterate through list of all stations of a route
            for (int j = 0; j < stationList.size() - 1; j++) {
                ResultNode node = stationList.get(j);
                // extract every station
                Station currentStation = node.getStation();

                if (currentStation == null)
                    continue;
                // extract next station in list
                Station nextStation = stationList.get(j + 1).getStation();
                // extract connections of every station
                if (nextStation == null)
                    continue;;

                ArrayList<Connections> connections = currentStation.getConnections();

                String currentColor = "";

                // iterate through list of all connections of station
                for (int k = 0; k < connections.size(); k++) {
                    // extract current connection
                    Connections connection = connections.get(k);
                    // check current connection is identical to next station in
                    // route
                    if (nextStation != null && connection.getConnection().contentEquals(nextStation.getStationID())) {
                        currentColor = connection.getConnectionColor();

                        if (!currentStation.getIsJunction().equals("0")) {
                            // not the first station of route and traveling on
                            // same color line
                            String lstClrDiv;
                            String curClrDiv = mHelper.getMetroLines().get(currentColor).getDiversionOf();

                            if (j != 0 && !lastColor.equals(currentColor)) {
                                // both colors are different as well as not
                                // directing from parent color line to child
                                // color line
                                lstClrDiv = mHelper.getMetroLines().get(lastColor).getDiversionOf();

                                if (!(lastColor.equals(curClrDiv) || currentColor
                                        .equals(lstClrDiv))) {
                                    // add last color to station
                                    node.setColor(lastColor);
                                    // increase switches of route
                                    fromStation = currentStation.getName();
                                }

                                if (lastColor.equals(curClrDiv)
                                        || (((!lstClrDiv.equals("-1") && !curClrDiv
                                        .equals("-1"))) && lstClrDiv
                                        .equals(curClrDiv))
                                        || (lastColor.equals("") && currentStation
                                        .getIsJunction().equals("2"))) {

                                    DiversionStation divStation = new DiversionStation();
                                    divStation.setName(fromStation);
                                    divStation.setSourceColor(lastColor);
                                    divStation.setDestColor(currentColor);
                                    result.addDiversions(divStation);
                                }
                            }
                        }

                        if (currentColor.contentEquals(airportLine)) {
                            isAirportLine = true;

                            if (!lastColor.contentEquals(airportLine)) {
                                airportStart.add(connection.getId());
                            }
                            if (connection.getId().equals(end)
                                    || connection.getConnection().equals(end)) {
                                airportEnd.add(end);
                            }
                        } else {
                            if (lastColor.contentEquals(airportLine)) {

                                airportEnd.add(connection.getId());
                            }
                        }
                        // add current connection color to station
                        node.setColor(currentColor);
                        // second last node in list
                        if (j == stationList.size() - 2) {
                            // set current color to the last station
                            stationList.get(j + 1).setColor(currentColor);
                        }
                        lastColor = currentColor;
                    }
                }
            }
        }
    }

    private void fetchData() {

      /*  MetroDbHelper.getInstance(mContext).openDataBase();
        Cursor cursor = new RouteStoreAPI().getAllMetroRoute(MetroDbHelper.getInstance(mContext), start, end);

        if (cursor != null && cursor.moveToFirst()) {

            do {
                int switches = -2;
                String switchStr = cursor.getString(cursor.getColumnIndex(RouteStoreAPI.CHANGES));

                // To calculate the number of switches
                for (int i = 0; i < switchStr.length(); i++)
                    if (switchStr.charAt(i) == '1')
                        switches++;

                String time = String.valueOf(Integer.parseInt(cursor.getString(cursor.getColumnIndex(RouteStoreAPI.TIME))) / 60);

                ArrayList<ResultNode> resultNodeList = new ArrayList<>();
                String routeList = cursor.getString(cursor.getColumnIndex(RouteStoreAPI.STATION_IDS));
                String[] station = routeList.split(",");
                for (int k = 0; k < station.length; k++) {
                    ResultNode resultNode = new ResultNode();
                    resultNode.setStation(mHelper.getStationById(station[k]));
                    resultNodeList.add(resultNode);
                }

                String fare = cursor.getString(cursor.getColumnIndex(RouteStoreAPI.FARE));
                if (AppUtils.getFareChoice().equalsIgnoreCase(mContext.getResources().getString(2131492865))) { // R.string.holiday
                    int _fare = Integer.parseInt(cursor.getString(cursor.getColumnIndex(RouteStoreAPI.FARE)));
                    _fare = _fare < 20 ? 10 : _fare - 10;
                    fare = String.valueOf(_fare);
                }
                if (fare.equalsIgnoreCase("0"))
                    fare = "N/A";

                Result result = new Result(fare, null, time, String.valueOf(station.length - 1), switches, resultNodeList);
                resultList.add(result);

            } while (cursor.moveToNext());
        }*/
    }

    public class ResultComparator implements Comparator<Result> {

        private int sortType;

        public ResultComparator(int type) {
            this.sortType = type;
        }

        @Override
        public int compare(Result lhs, Result rhs) {
            int ValueLHS = 0, valueRHS = 0;
            switch (sortType) {

                case SORT_BY_TIME:
                    ValueLHS = Integer.parseInt(lhs.getRunTime());
                    valueRHS = Integer.parseInt(rhs.getRunTime());
                    break;

                case SORT_BY_FARE:
                    ValueLHS = Integer.parseInt(lhs.getFare());
                    valueRHS = Integer.parseInt(rhs.getFare());
                    break;

                case SORT_BY_SWITCHES:
                    ValueLHS = lhs.getSwitches();
                    valueRHS = rhs.getSwitches();
                    break;

                case SORT_BY_STOPS:
                    ValueLHS = Integer.parseInt(lhs.getStops());
                    valueRHS = Integer.parseInt(rhs.getStops());
                    break;

                default:
                    ValueLHS = Integer.parseInt(lhs.getRunTime());
                    valueRHS = Integer.parseInt(rhs.getRunTime());
                    break;
            }

            if (ValueLHS < valueRHS) {
                return -1;
            } else if (ValueLHS > valueRHS) {
                return 1;
            }
            return 0;
        }

    }

    public void cancelAsync() {
        if (fillDataAsync != null) {
            fillDataAsync.cancel(false);
        }
    }
}
