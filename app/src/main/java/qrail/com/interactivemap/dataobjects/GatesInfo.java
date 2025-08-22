package qrail.com.interactivemap.dataobjects;

public class GatesInfo {

	private String id;
	private String gate;
	private String towards;

	public GatesInfo() {

	}

	public GatesInfo(String id, String gate, String towards) {

		this.id = id;
		this.gate = gate;
		this.towards = towards;
	}

	public void setGatesInfo(String id, String gate, String towards) {

		this.id = id;
		this.gate = gate;
		this.towards = towards;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGate() {
		return gate;
	}

	public void setGate(String gate) {
		this.gate = gate;
	}

	public String getTowards() {
		return towards;
	}

	public void setTowards(String towards) {
		this.towards = towards;
	}

	@Override
	public String toString() {

		return String.format(
				"GatesInfo ::\n ID : %s\n Gate : %s\n Towards : %s\n ", id,
				gate, towards);
	}
}
