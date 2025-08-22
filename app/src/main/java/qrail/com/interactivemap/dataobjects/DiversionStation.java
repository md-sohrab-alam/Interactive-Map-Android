package qrail.com.interactivemap.dataobjects;

public class DiversionStation {
	private String name;
	private String destColor;
	private String sourceColor;
	private String notSelectedColor;

	public DiversionStation() {

	}

	public DiversionStation(String name, String currentColor, String lastColor,
			String notSelectedColor) {
		super();
		this.name = name;
		this.destColor = currentColor;
		this.sourceColor = lastColor;
		this.notSelectedColor = notSelectedColor;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDestColor() {
		return destColor;
	}

	public void setDestColor(String destColor) {
		this.destColor = destColor;
	}

	public String getSourceColor() {
		return sourceColor;
	}

	public void setSourceColor(String sourceColor) {
		this.sourceColor = sourceColor;
	}

	public String getNotSelectedColor() {
		return notSelectedColor;
	}

	public void setNotSelectedColor(String notSelectedColor) {
		this.notSelectedColor = notSelectedColor;
	}

}
