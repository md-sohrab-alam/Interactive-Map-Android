package qrail.com.interactivemap.stationroute;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import qrail.com.interactivemap.database.AirportFareAPI;
import qrail.com.interactivemap.database.AirportRunTimeAPI;
import qrail.com.interactivemap.database.FareAPI;
import qrail.com.interactivemap.database.HolidayFareAPI;
import qrail.com.interactivemap.database.MetroDbHelper;
import qrail.com.interactivemap.database.ParentAPI;
import qrail.com.interactivemap.database.RunTimeAPI;
import qrail.com.interactivemap.datahelper.StationHelper;
import qrail.com.interactivemap.dataobjects.Connections;
import qrail.com.interactivemap.dataobjects.DiversionStation;
import qrail.com.interactivemap.dataobjects.Result;
import qrail.com.interactivemap.dataobjects.ResultNode;
import qrail.com.interactivemap.dataobjects.Station;

public class StationRoute {
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

    public StationRoute(Context context, String start, String end) {
        mContext = context;
        this.start = start;
        this.end = end;
        airportStart = new ArrayList<String>();
        airportEnd = new ArrayList<String>();
        mHelper = StationHelper.getInstance(context);
    }

    public void calculateRoute() {
        LinkedList<String> visited = new LinkedList<String>();
        if (resultList == null)
            resultList = new ArrayList<Result>();
        else
            resultList.clear();
        visited.add(start);
        traverseStations(visited, end);
    }

    public ArrayList<Result> getResultList() {
        return resultList;
    }

    private void traverseStations(LinkedList<String> visited, String end) {
        ArrayList<Connections> connections = mHelper.getStationById(
                visited.getLast()).getConnections();

        // Examine Adjacent Nodes
        for (int i = 0; i < connections.size(); i++) {

            if (visited.contains(connections.get(i).getConnection()))
                continue;

            if (connections.get(i).getConnection().contentEquals(end)) {
                visited.add(connections.get(i).getConnection());
                Result result = new Result();
                ArrayList<ResultNode> resultNodeList = new ArrayList<ResultNode>();
                // Get Path

                for (int k = 0; k < visited.size(); k++) {

                    ResultNode resultNode = new ResultNode();
                    resultNode.setStation(mHelper.getStationById(visited.get(k)));
                    resultNodeList.add(resultNode);
                }
                result.setResultStations(resultNodeList);
                result.setStops(String.valueOf(resultNodeList.size()));
                if (resultNodeList.size() <= 60)
                    resultList.add(result);


                visited.removeLast();
                break;
            }
        }

        for (int i = 0; i < connections.size(); i++) {
            if (visited.contains(connections.get(i).getConnection())
                    || connections.get(i).getConnection().contentEquals(end)) {
                continue;
            }
            visited.addLast(connections.get(i).getConnection());
            traverseStations(visited, end);
            visited.removeLast();
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

                // extract next station in list
                Station nextStation = stationList.get(j + 1).getStation();
                // extract connections of every station

                ArrayList<Connections> connections = currentStation.getConnections();

                String currentColor = "";

                // iterate through list of all connections of station
                for (int k = 0; k < connections.size(); k++) {
                    // extract current connection
                    Connections connection = connections.get(k);
                    // check current connection is identical to next station in
                    // route
                    if (connection.getConnection().contentEquals(nextStation.getStationID())) {
                        currentColor = connection.getConnectionColor();

                        String temp = lastColor;

                        if (j == 0 || j == stationList.size() - 1) {
                            lastColor = currentColor;
                        }

                        runTime += Integer.parseInt(calcualteTime(
                                currentStation.getStationID(),
                                nextStation.getStationID(), currentStation,
                                nextStation, currentColor, lastColor));

                        lastColor = temp;

                        if (!currentStation.getIsJunction().equals("0")) {
                            // not the first station of route and traveling on
                            // same color line
                            String lstClrDiv;
                            String curClrDiv = mHelper.getMetroLines()
                                    .get(currentColor).getDiversionOf();

                            if (j != 0 && !lastColor.equals(currentColor)) {
                                // both colors are different as well as not
                                // directing from parent color line to child
                                // color line
                                lstClrDiv = mHelper.getMetroLines()
                                        .get(lastColor).getDiversionOf();
                                if (!(lastColor.equals(curClrDiv) || currentColor
                                        .equals(lstClrDiv))) {
                                    // add last color to station
                                    node.setColor(lastColor);
                                    // increase switches of route
                                    fromStation = currentStation.getName();
                                    result.setSwitches(result.getSwitches() + 1);
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

                            } else {
                                /*
                                 * if (j == 0) { if (!curClrDiv.equals("-1")) {
                                 * DiversionStation divStation = new
                                 * DiversionStation();
                                 * divStation.setName(fromStation);
                                 * divStation.setSourceColor(lastColor);
                                 * divStation.setDestColor(currentColor);
                                 * result.addDiversions(divStation); } }
                                 */
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
            }/*
            if (result.getSwitches() > 4) {
                resultList.remove(i);
                i = i - 1;
                runTime = 0;
                seconds = 0;
            } else {
                runTime += seconds / 60 + result.getSwitches() * 3;
                if (runTime >= 120) {
                    resultList.remove(i);
                    i = i - 1;
                    runTime = 0;
                    seconds = 0;
                } else {
                    result.setRunTime(runTime + "");
                    result.setStops(stationList.size() - 1 + "");
                    calculateFareDistanceTime(start, end, result);
                }
            }*/
        }
    }

    private String calcualteTime(String sPoint, String ePoint,
                                 Station currentStation, Station nextStation, String currentColor,
                                 String lastColor) {

        System.out.println(currentStation + " " + nextStation);
        MetroDbHelper.getInstance(mContext).openDataBase();
        String value = "0";
        Cursor cursor = new RunTimeAPI().getRecord(
                MetroDbHelper.getInstance(mContext), sPoint, ePoint);

        if (cursor != null && !cursor.moveToFirst()) {

            if (cursor.getCount() == 0) {
                cursor.close();
                cursor = new AirportRunTimeAPI().getRecord(
                        MetroDbHelper.getInstance(mContext), sPoint, ePoint);

                if (cursor != null && cursor.moveToFirst()) {
                    value = cursor.getString(0);
                }
            }

        } else if (cursor != null && cursor.moveToFirst() && cursor.getCount() > 0) {

            value = cursor.getString(0);
            if (value.equals(ePoint)) {
                cursor.close();
                cursor = new AirportRunTimeAPI().getRecord(
                        MetroDbHelper.getInstance(mContext), sPoint, ePoint);
                if (cursor.moveToNext() && cursor.getCount() > 0)

                    value = cursor.getString(0);
            } else {
                int time = 2;
                value = time + "";
            }
        }
        cursor.close();
        MetroDbHelper.getInstance(mContext).close();
        return isTime(value);
    }



    private String calculateValue(ParentAPI parentAPI, String sPoint,
                                  String ePoint) {
        Cursor cursor = parentAPI.getRecord(
                MetroDbHelper.getInstance(mContext), sPoint, ePoint);
        String value = "";
        if (cursor.moveToFirst()) {
            value = cursor.getString(0);
        } else {
            System.out.println();
        }
        cursor.close();

        return isTime(value);
    }

    private String isTime(String value) {
        if (value.indexOf(":") == -1) {
            return value;
        } else {
            int min = 0;
            String[] time = value.split(":");
            min = Integer.parseInt(time[0]) * 60 + Integer.parseInt(time[1]);
            seconds += Integer.parseInt(time[2]);
            return min + "";
        }

    }

    public void fillDataAsync(OnFillDataListener listener) {
        this.mListener = listener;

        fillDataAsync = new FillDataTask().execute();
    }

    private class FillDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            calculateRoute();
            fillData();
            //fetchData();
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
           /* Result first = resultList.get(0);
            for (int i = 1; i < resultList.size(); i++) {
                Result temp = resultList.get(i);
                if (Integer.parseInt(temp.getRunTime())
                        - Integer.parseInt(first.getRunTime()) > 10) {

                    if (Integer.parseInt(temp.getFare()) >= Integer
                            .parseInt(first.getFare())) {
                        resultList.remove(i);
                        i = i - 1;
                    }
                }
            }
*/
            Collections.sort(resultList,
                    new ResultComparator(SORT_BY_STOPS));
            Collections.sort(resultList, new ResultComparator(
                    SORT_BY_SWITCHES));


           /* if (start.charAt(0) == '4' || end.charAt(0) == '4') {
                Collections.sort(resultList,
                        new ResultComparator(SORT_BY_STOPS));
            } else {
                Collections.sort(resultList, new ResultComparator(
                        SORT_BY_SWITCHES));
            }
*/
            if (resultList.size() > 2) {
                if (resultList.get(0).getFare()
                        .equals(resultList.get(1).getFare())
                        && resultList.get(0).getRunTime()
                        .equals(resultList.get(1).getRunTime())
                        && resultList.get(0).getSwitches() == resultList.get(1)
                        .getSwitches()) {
                    Collections.sort(resultList, new ResultComparator(SORT_BY_STOPS));

                }
                for (int i = 2; i < resultList.size(); ) {
                    resultList.remove(i);
                }
            }
        }
    }

    public class ResultComparator implements Comparator<Result> {

        private int sortType;

        public ResultComparator(int type) {
            this.sortType = type;
        }

        @Override
        public int compare(Result lhs, Result rhs) {
            int ValueLHS = 0, valueRHS = 0;

            try {
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
            }catch (NumberFormatException e) {
                e.printStackTrace();
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
