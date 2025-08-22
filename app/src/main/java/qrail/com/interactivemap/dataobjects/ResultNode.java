package qrail.com.interactivemap.dataobjects;

import java.util.ArrayList;

public class ResultNode {

	private ArrayList<String> color;
	private Station station;

	public ResultNode() {
	}

	public ResultNode(ArrayList<String> color, Station station) {

		this.color = color;
		this.station = station;
	}

	public void setResultNode(ArrayList<String> color, Station station) {
		this.color = color;
		this.station = station;
	}

	public ArrayList<String> getColor() {
		return color;
	}

	public void setColor(String clr) {
		if (color == null) {
			color = new ArrayList<String>();
		}
		color.add(clr);

	}

	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}

}
