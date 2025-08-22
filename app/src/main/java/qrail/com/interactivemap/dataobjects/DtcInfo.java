package qrail.com.interactivemap.dataobjects;

public class DtcInfo {

	private String stationID;
	private String dtcBusRouteNo;
	private String originatingFrom;
	private String destination;

	public DtcInfo() {
	}

	public DtcInfo(String stationID, String dtcBusRouteNo,
			String originatingFrom, String destination) {

		this.stationID = stationID;
		this.dtcBusRouteNo = dtcBusRouteNo;
		this.originatingFrom = originatingFrom;
		this.destination = destination;
	}

	public void setDtcInfo(String stationID, String dtcBusRouteNo,
			String originatingFrom, String destination) {

		this.stationID = stationID;
		this.dtcBusRouteNo = dtcBusRouteNo;
		this.originatingFrom = originatingFrom;
		this.destination = destination;
	}

	public String getStationID() {
		return stationID;
	}

	public String getDtcBusRouteNo() {
		return dtcBusRouteNo;
	}

	public String getOriginatingFrom() {
		return originatingFrom;
	}

	public String getDestination() {
		return destination;
	}

	@Override
	public String toString() {

		return String
				.format("DtcInfo ::\n ID : %s\n dtcBusRouteNo : %s\n OriginatingFrom : %s\n Destination : %s\n",
						stationID, dtcBusRouteNo, originatingFrom, destination);
	}

}
