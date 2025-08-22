package qrail.com.interactivemap.dataobjects;

public class FeederBus {

	private String id;
	private String fromAddress;
	private String toAddress;
	private String routeNo;
	private String via;

	public FeederBus() {
	}

	public FeederBus(String id, String fromAddress, String toAddress,
			String routeNo, String via) {

		this.id = id;
		this.fromAddress = fromAddress;
		this.toAddress = toAddress;
		this.routeNo = routeNo;
		this.via = via;
	}

	public void setFeederBus(String id, String fromAddress, String toAddress,
			String routeNo, String via) {

		this.id = id;
		this.fromAddress = fromAddress;
		this.toAddress = toAddress;
		this.routeNo = routeNo;
		this.via = via;
	}

	public String getRouteNo() {
		return routeNo;
	}

	public void setRouteNo(String routeNo) {
		this.routeNo = routeNo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public String getToAddress() {
		return toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	public String getVia() {
		return via;
	}

	public void setVia(String via) {
		this.via = via;
	}

}
