package qrail.com.interactivemap.dataobjects;

public class CabInfo {

	private String mName;
	private int mTime;
	private String mProductId;

	public CabInfo(String name, int time) {

		mName = name;		
		mTime = time;

	}

	

	public String getName() {
		return mName;
	}	

	public int getTime() {
		return mTime;
	}
	
	public void setProductId(String productId) {
		mProductId = productId;
	}
	
	public String getProductId() {
		return mProductId;
	}

}
