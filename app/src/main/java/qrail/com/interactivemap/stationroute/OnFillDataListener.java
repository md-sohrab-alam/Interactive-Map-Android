package qrail.com.interactivemap.stationroute;

import java.util.ArrayList;


import qrail.com.interactivemap.dataobjects.Result;

public interface OnFillDataListener {

	public void onComplete(ArrayList<Result> results);
}
