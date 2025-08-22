package qrail.com.interactivemap.dataobjects;

public class MetroLines {

	private String id;
	private String name;
	private String stops;
	private String distance;
	private String pTime;
	private String npTime;
	private String sPoint;
	private String ePoint;
	private String fare;
	private String diversionOf;
	private String[] diversions;

	public MetroLines() {
	}

	public MetroLines(String id, String name, String stops, String pTime,
			String npTime, String sPoint, String ePoint, String fare,
			String diversionOf, String distance, String[] diversions) {
		this.id = id;
		this.name = name;
		this.stops = stops;
		this.pTime = pTime;
		this.npTime = npTime;
		this.sPoint = sPoint;
		this.ePoint = ePoint;
		this.fare = fare;
		this.diversionOf = diversionOf;
		this.diversions = diversions;
		this.setDistance(distance);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStops() {
		return stops;
	}

	public void setStops(String stops) {
		this.stops = stops;
	}

	public String getpTime() {
		return pTime;
	}

	public void setpTime(String pTime) {
		this.pTime = pTime;
	}

	public String getNpTime() {
		return npTime;
	}

	public void setNpTime(String npTime) {
		this.npTime = npTime;
	}

	public String getsPoint() {
		return sPoint;
	}

	public void setsPoint(String sPoint) {
		this.sPoint = sPoint;
	}

	public String getePoint() {
		return ePoint;
	}

	public void setePoint(String ePoint) {
		this.ePoint = ePoint;
	}

	public String getFare() {
		return fare;
	}

	public void setFare(String fare) {
		this.fare = fare;
	}

	public String getDiversionOf() {
		return diversionOf;
	}

	public void setDiversionOf(String diversionOf) {
		this.diversionOf = diversionOf;
	}

	public void setMetroLines(String id, String name, String stops,
			String pTime, String npTime, String sPoint, String ePoint,
			String fare, String diversionOf, String distance) {
		this.id = id;
		this.name = name;
		this.stops = stops;
		this.pTime = pTime;
		this.npTime = npTime;
		this.sPoint = sPoint;
		this.ePoint = ePoint;
		this.fare = fare;
		this.diversionOf = diversionOf;
		this.setDistance(distance);
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String[] getDiversions() {
		return diversions;
	}

	public void setDiversions(String[] diversions) {
		this.diversions = diversions;
	}

}
