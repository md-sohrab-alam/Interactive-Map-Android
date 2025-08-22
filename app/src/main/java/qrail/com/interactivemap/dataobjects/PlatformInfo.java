package qrail.com.interactivemap.dataobjects;

public class PlatformInfo {

	private String id;
	private String platformId;
	private String towards;

	public PlatformInfo() {

	}

	public PlatformInfo(String id, String platformId, String towards) {
		this.id = id;
		this.platformId = platformId;
		this.towards = towards;
	}

	public void setPlatformInfo(String id, String platformId, String towards) {
		this.id = id;
		this.platformId = platformId;
		this.towards = towards;
	}

	public String getId() {
		return id;
	}

	public String getPlatformId() {
		return platformId;
	}

	public String getTowards() {
		return towards;
	}

	@Override
	public String toString() {
		return String
				.format("PlatfromInfo ::\n ID : %s\n PlatformId : %s\n Towards : %s\n ",
						id, platformId, towards);

	}

}
