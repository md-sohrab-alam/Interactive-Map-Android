package qrail.com.interactivemap.dataobjects;

import java.util.ArrayList;

import qrail.com.interactivemap.dataobjects.CabInfo;

public class Cabs {

	private int mId;
	private ArrayList<CabInfo> mCabInfos;

	public Cabs(int id) {

		mId = id;

	}

	public void setId(int id) {
		mId = id;
	}

	public int getId() {
		return mId;
	}

	/*
	 * public void setName(String name) { mName = name; }
	 * 
	 * public String getName() { return mName; }
	 */

	public void setCabInfos(CabInfo cabInfo) {

		if (mCabInfos == null)
			mCabInfos = new ArrayList<CabInfo>();
		mCabInfos.add(cabInfo);
	}

	public ArrayList<CabInfo> getCabInfos() {
		return mCabInfos;

	}
	
}
