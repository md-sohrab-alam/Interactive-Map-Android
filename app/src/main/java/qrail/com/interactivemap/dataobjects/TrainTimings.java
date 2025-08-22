package qrail.com.interactivemap.dataobjects;

public class TrainTimings {

	private String id;
	private String towards;
	private String time;
	private String isStartTime;

	public TrainTimings() {
	}

	public TrainTimings(String id, String towards, String time,
			String isStartTime) {

		this.id = id;
		this.towards = towards;
		this.time = time;
		this.isStartTime = isStartTime;
	}

	public void setTrainTimings(String id, String towards, String time,
			String isStartTime) {

		this.id = id;
		this.towards = towards;
		this.time = time;
		this.isStartTime = isStartTime;
	}

	public String getId() {
		return id;
	}

	public String getTowards() {
		return towards;
	}

	public String getTime() {
		return time;
	}

	public String getIsStartTime() {
		return isStartTime;
	}

	@Override
	public String toString() {

		return String
				.format("TrainTimings ::\n ID : %s\n time : %s\n towards : %s\n isStartTime : %s\n",
						id, time, towards, isStartTime);
	}

}
