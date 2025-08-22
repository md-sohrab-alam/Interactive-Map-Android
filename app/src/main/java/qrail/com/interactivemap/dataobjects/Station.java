package qrail.com.interactivemap.dataobjects;

import java.util.ArrayList;

public class Station {

    private String stationID;
    private String isBus;
    private String landlineNo;
    private String mobileNo;
    private String isFeeder;
    private String isParking;
    private String name;
    private String code;
    private String lat;
    private String longt;
    private int xCord;
    private int yCord;
    private String isUnderground = "0";
    private String isJunction = "0";
    private String stationAVM;
    private ArrayList<GatesInfo> gatesInfo;
    private ArrayList<StationsLines> stationLines;
    private ArrayList<Connections> connections;
    private ArrayList<FeederBus> feederInfo;
    private ArrayList<PlatformInfo> platformInfo;
    private ArrayList<DtcInfo> dtcInfo;
    private ArrayList<TrainTimings> trainTimings;
    private ArrayList<ATMMaster> stationATMs;

    private float currentDistance;

    public Station() {

    }

    public Station(String stationID, String isBus, String landlineNo,
                   String mobileNo, String isFeeder, String isParking, String name,
                   String code, String lat, String longt, int xCord, int yCord,
                   String isUnderground, String isJunction,
                   ArrayList<StationsLines> stationLines,
                   ArrayList<Connections> connections,
                   ArrayList<PlatformInfo> platformInfo, ArrayList<DtcInfo> dtcInfo,
                   ArrayList<TrainTimings> trainTimings,
                   ArrayList<GatesInfo> gatesInfo, ArrayList<FeederBus> feederInfo) {

        this.stationID = stationID;
        this.isBus = isBus;
        this.landlineNo = landlineNo;
        this.mobileNo = mobileNo;
        this.isFeeder = isFeeder;
        this.isParking = isParking;
        this.name = name;
        this.code = code;
        this.lat = lat;
        this.longt = longt;
        this.xCord = xCord;
        this.yCord = yCord;
        this.isUnderground = isUnderground;
        this.isJunction = isJunction;
        this.stationLines = stationLines;
        this.connections = connections;
        this.platformInfo = platformInfo;
        this.dtcInfo = dtcInfo;
        this.trainTimings = trainTimings;
        this.gatesInfo = gatesInfo;
        this.feederInfo = feederInfo;
    }


    public Station(String stationID, String isBus, String landlineNo,
                   String mobileNo, String isFeeder, String isParking, String name,
                   String code, String lat, String longt, int xCord, int yCord,
                   String isUnderground, String isJunction) {

        this.stationID = stationID;
        this.isBus = isBus;
        this.landlineNo = landlineNo;
        this.mobileNo = mobileNo;
        this.isFeeder = isFeeder;
        this.isParking = isParking;
        this.name = name;
        this.code = code;
        this.lat = lat;
        this.longt = longt;
        this.xCord = xCord;
        this.yCord = yCord;
        this.isUnderground = isUnderground;
        this.isJunction = isJunction;

    }

    public void setStation(String stationID, String isBus, String landlineNo,
                           String mobileNo, String isFeeder, String isParking, String name,
                           String code, String lat, String longt, int xCord, int yCord,
                           String isUnderground, String isJunction,
                           ArrayList<StationsLines> stationLines,
                           ArrayList<Connections> connections,
                           ArrayList<PlatformInfo> platformInfo, ArrayList<DtcInfo> dtcInfo,
                           ArrayList<TrainTimings> trainTimings,
                           ArrayList<GatesInfo> gatesInfo, ArrayList<FeederBus> feederInfo) {

        this.stationID = stationID;
        this.isBus = isBus;
        this.landlineNo = landlineNo;
        this.mobileNo = mobileNo;
        this.isFeeder = isFeeder;
        this.isParking = isParking;
        this.name = name;
        this.code = code;
        this.lat = lat;
        this.longt = longt;
        this.xCord = xCord;
        this.yCord = yCord;
        this.isUnderground = isUnderground;
        this.isJunction = isJunction;
        this.stationLines = stationLines;
        this.connections = connections;
        this.platformInfo = platformInfo;
        this.dtcInfo = dtcInfo;
        this.trainTimings = trainTimings;
        this.gatesInfo = gatesInfo;
        this.feederInfo = feederInfo;
    }

    public ArrayList<FeederBus> getFeederInfo() {
        return feederInfo;
    }

    public void setFeederInfo(ArrayList<FeederBus> feederInfo) {
        this.feederInfo = feederInfo;
    }

    public String getStationID() {
        return stationID;
    }

    public void setStationID(String stationID) {
        this.stationID = stationID;
    }

    public String getIsBus() {
        return isBus;
    }

    public void setIsBus(String isBus) {
        this.isBus = isBus;
    }

    public String getLandlineNo() {
        return landlineNo;
    }

    public void setLandlineNo(String landlineNo) {
        this.landlineNo = landlineNo;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getIsFeeder() {
        return isFeeder;
    }

    public void setIsFeeder(String isFeeder) {
        this.isFeeder = isFeeder;
    }

    public String getIsParking() {
        return isParking;
    }

    public void setIsParking(String isParking) {
        this.isParking = isParking;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLongt() {
        return longt;
    }

    public void setLongt(String longt) {
        this.longt = longt;
    }

    public int getxCord() {
        return xCord;
    }

    public void setxCord(int xCord) {
        this.xCord = xCord;
    }

    public int getyCord() {
        return yCord;
    }

    public void setyCord(int yCord) {
        this.yCord = yCord;
    }

    public String getIsUnderground() {
        return isUnderground == null ? "0" : isUnderground;
    }

    public void setIsUnderground(String isUnderground) {
        this.isUnderground = isUnderground;
    }

    public String getIsJunction() {
        return isJunction == null ? "0" : isJunction;
    }

    public void setIsJunction(String isJunction) {
        this.isJunction = isJunction;
    }

    public ArrayList<GatesInfo> getGatesInfo() {
        return gatesInfo;
    }

    public void setGatesInfo(ArrayList<GatesInfo> gatesInfo) {
        this.gatesInfo = gatesInfo;
    }

    public ArrayList<StationsLines> getStationLines() {
        return stationLines;
    }

    public void setStationLines(ArrayList<StationsLines> stationLines) {
        this.stationLines = stationLines;
    }

    public ArrayList<Connections> getConnections() {
        if (connections == null) {
            connections = new ArrayList<Connections>();
        }
        return connections;
    }

    public void setConnections(ArrayList<Connections> connections) {
        this.connections = connections;
    }

    public ArrayList<PlatformInfo> getPlatformInfo() {
        return platformInfo;
    }

    public void setPlatformInfo(ArrayList<PlatformInfo> platformInfo) {
        this.platformInfo = platformInfo;
    }

    public ArrayList<DtcInfo> getDtcInfo() {
        return dtcInfo;
    }

    public void setDtcInfo(ArrayList<DtcInfo> dtcInfo) {
        this.dtcInfo = dtcInfo;
    }

    public ArrayList<TrainTimings> getTrainTimings() {
        return trainTimings;
    }

    public void setTrainTimings(ArrayList<TrainTimings> trainTimings) {
        this.trainTimings = trainTimings;
    }


    @Override
    public String toString() {
        return name;
    }

    public float getCurrentDistance() {
        return currentDistance;
    }

    public void setCurrentDistance(float currentDistance) {
        this.currentDistance = currentDistance;
    }

    public String getStationAVM() {
        return stationAVM;
    }

    public void setStationAVM(String stationAVM) {
        this.stationAVM = stationAVM;
    }

    public ArrayList<ATMMaster> getStationATMs() {
        return stationATMs;
    }

    public void setStationATMs(ArrayList<ATMMaster> stationATMs) {
        this.stationATMs = stationATMs;
    }
}
