package qrail.com.interactivemap.dataobjects;

public class StationATM {
	public StationATM(String stationID, String stationName,
			String[] availableATM) {
		super();
		this.stationID = stationID;
		this.stationName = stationName;
		this.availableATM = availableATM;
	}
	private String stationID;
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
	public String[] getAvailableATM() {
		return availableATM;
	}
	public void setAvailableATM(String[] availableATM) {
		this.availableATM = availableATM;
	}
	private String stationName;
	private String[] availableATM;

}
