package qrail.com.interactivemap.dataobjects;

public class AVMMaster {
	public AVMMaster(String stationID, String stationName, String totalATM) {
		super();
		this.stationID = stationID;
		this.stationName = stationName;
		this.totalATM = totalATM;
	}
	private String stationID;
	private String stationName;
	private String totalATM;
	public String getStationID() {
		return stationID;
	}
	public void setStationID(String stationID) {
		this.stationID = stationID;
	}
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	public String getTotalATM() {
		return totalATM;
	}
	public void setTotalATM(String totalATM) {
		this.totalATM = totalATM;
	}
}
