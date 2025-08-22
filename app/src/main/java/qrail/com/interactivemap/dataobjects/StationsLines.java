package qrail.com.interactivemap.dataobjects;

public class StationsLines {

	private String _id;
	private String id_lines;

	public StationsLines(String _id, String id_lines) {

		this._id = _id;
		this.id_lines = id_lines;
	}

	public void setStaionsLines(String _id, String id_lines) {

		this._id = _id;
		this.id_lines = id_lines;
	}

	public String get_id() {
		return _id;
	}

	public String getId_lines() {
		return id_lines;
	}

	@Override
	public String toString() {
		return String.format("StationLines ::\n ID : %s\n IdLine : %s\n", _id,
				id_lines);
	}

}
