package qrail.com.interactivemap.dataobjects;

import java.util.ArrayList;

import qrail.com.interactivemap.dataobjects.DiversionStation;

public class Result {

	private String fare = "";
	private String distance ="";
	private String runTime = "";
	private String stops = "";
	private int switches ;
	private ArrayList<ResultNode> resultStations;
	private ArrayList<DiversionStation> diversions;

	public Result() {
	}

	public Result(String fare, String distance, String runTime, String stops,
			int switches, ArrayList<ResultNode> resultStations) {

		this.fare = fare;
		this.distance = distance;
		this.runTime = runTime;
		this.stops = stops;
		this.switches = switches;
		this.setResultStations(resultStations);
	}

	public void setResult(String fare, String distance, String runTime,
			String stops, int switches, ArrayList<ResultNode> resultStations) {

		this.fare = fare;
		this.distance = distance;
		this.runTime = runTime;
		this.stops = stops;
		this.switches = switches;
		this.setResultStations(resultStations);

	}

	public String getFare() {
		return fare;
	}

	public void setFare(String fare) {
		this.fare = fare;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getRunTime() {
		return runTime;
	}

	public void setRunTime(String runTime) {
		this.runTime = runTime;
	}

	public String getStops() {
		return stops;
	}

	public void setStops(String stops) {
		this.stops = stops;
	}

	public int getSwitches() {
		return switches;
	}

	public void setSwitches(int switches) {
		this.switches = switches;
	}

	public ArrayList<ResultNode> getResultStations() {
		return resultStations;
	}

	public void setResultStations(ArrayList<ResultNode> resultStations) {
		this.resultStations = resultStations;
	}

	public ArrayList<DiversionStation> getDiversions() {
		return diversions;
	}

	public void addDiversions(DiversionStation diversions) {
		if (this.diversions == null) {
			this.diversions = new ArrayList<DiversionStation>();
		}
		this.diversions.add(diversions);
	}
}
