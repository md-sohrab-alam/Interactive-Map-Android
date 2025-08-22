package qrail.com.interactivemap.datahelper;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import java.util.ArrayList;

import qrail.com.interactivemap.database.MetroDbHelper;
import qrail.com.interactivemap.database.TrainTimingsAPI;
import qrail.com.interactivemap.dataobjects.TrainTimings;

public class TrainTimingsHelper {

	private ArrayList<TrainTimings> trainTimingsList;
	private TrainTimingsAPI trainTimingsAPI;

	private OnDataQueryListener mListener;
	private Context mContext;

	public TrainTimingsHelper(Context context) {
		mContext = context;
		trainTimingsAPI = new TrainTimingsAPI();
		trainTimingsList = new ArrayList<TrainTimings>();
	}

	public ArrayList<TrainTimings> getTrainTimingsList() {
		return trainTimingsList;
	}

	public void fillTrainTimings() {
		MetroDbHelper.getInstance(mContext).openDataBase();
		Cursor cursor = trainTimingsAPI.getAllTrainTimings(MetroDbHelper.getInstance(mContext));

		if (cursor.moveToFirst()) {
			do {
				String stationID = cursor.getString(cursor
						.getColumnIndex("_id"));

				String towards = cursor.getString(cursor
						.getColumnIndex("towards"));

				String time = cursor.getString(cursor.getColumnIndex("time"));

				String isStartTime = cursor.getString(cursor
						.getColumnIndex("isStartTime"));

				TrainTimings TrainTimings = new TrainTimings(stationID,
						towards, time, isStartTime);
				trainTimingsList.add(TrainTimings);

			} while (cursor.moveToNext());
		}
		cursor.close();
		MetroDbHelper.getInstance(mContext).close();

	}

	public void fillTrainTimingsAsync(OnDataQueryListener listener) {
		this.mListener = listener;
		new fillTrainTimingsTask().execute();
	}

	private class fillTrainTimingsTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			fillTrainTimings();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			if (mListener != null) {
				mListener.onComplete();
			}

		}

	}
}
