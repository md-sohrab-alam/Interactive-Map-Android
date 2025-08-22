package qrail.com.interactivemap.util;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.AsyncTask;

public class LoadBitmap {

	private static LoadBitmap mInstance;
	private static Bitmap map;
	private static Bitmap mStartMarkerBitmap;
	private onAsstesLoaded mListener;

	public static Bitmap getMap() {
		return map;
	}

	public static void setMap(Bitmap map) {
		LoadBitmap.map = map;
	}

	public static Bitmap getmStartMarkerBitmap() {
		return mStartMarkerBitmap;
	}

	public static Bitmap getmIntermediateMarkerBitmap() {
		return mIntermediateMarkerBitmap;
	}

	public static Bitmap getmIntermediateMarkerBitmapDark() {
		return mIntermediateMarkerBitmapDark;
	}

	public static Bitmap getmEndMarkerBitmap() {
		return mEndMarkerBitmap;
	}

	public static void setmEndMarkerBitmap(Bitmap mEndMarkerBitmap) {
		LoadBitmap.mEndMarkerBitmap = mEndMarkerBitmap;
	}

	public void setOnAssetsLoadedListener(onAsstesLoaded listener) {
		mListener = listener;
	}

	private static Bitmap mIntermediateMarkerBitmap;
	private static Bitmap mIntermediateMarkerBitmapDark;
	private static Bitmap mEndMarkerBitmap;
	private Context context;

	public LoadBitmap(Context context) {
		this.context = context;
	}

	public static LoadBitmap getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new LoadBitmap(context);
		}
		return mInstance;
	}

	public void loadMarkers() {
		new LoadAssets().execute();
	}

	private class LoadAssets extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			initBitmap(context);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			mListener.onAssetsLoaded();
		}

	}

	/**
	 * Initializing Bitmaps
	 * 
	 * @param context
	 * @return
	 */

	private static void initBitmap(Context context) {

//		String mapPath = "map_sharp_turn.png";
		String mapPath = "qrail_metro_image.png";

		String startMarkerPath = "ic_dot_green.png";
		String IntermediateMarkerPath = "ic_dot_black.png";
		String IntermediateMarkerDarkPath = "ic_dot_gray.png";
		String endMarkerPath = "ic_dot_red.png";

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inDither = false;
		options.inPurgeable = true;
		options.inPreferredConfig = Config.RGB_565;

		if (map == null || map.isRecycled())
			map = getBitmapFromAsset(context, mapPath, options);
		if (mStartMarkerBitmap == null || mStartMarkerBitmap.isRecycled())
			mStartMarkerBitmap = getBitmapFromAsset(context, startMarkerPath,
					options);
		if (mIntermediateMarkerBitmap == null
				|| mIntermediateMarkerBitmap.isRecycled())
			mIntermediateMarkerBitmap = getBitmapFromAsset(context,
					IntermediateMarkerPath, options);
		if (mIntermediateMarkerBitmapDark == null
				|| mIntermediateMarkerBitmapDark.isRecycled())
			mIntermediateMarkerBitmapDark = getBitmapFromAsset(context,
					IntermediateMarkerDarkPath, options);
		if (mEndMarkerBitmap == null || mEndMarkerBitmap.isRecycled())
			mEndMarkerBitmap = getBitmapFromAsset(context, endMarkerPath,
					options);

	}

	/**
	 * creates bitmap from assets
	 * 
	 * @param context
	 * @param strName
	 * @return
	 */
	public static Bitmap getBitmapFromAsset(Context context, String strName,
			Options options) {
		AssetManager assetManager = context.getAssets();

		InputStream istr;
		Bitmap bitmap = null;
		try {
			istr = assetManager.open(strName);
			bitmap = BitmapFactory.decodeStream(istr, null, options);
		} catch (IOException e) {
			return null;
		}

		return bitmap;
	}

	public void recycleAssets() {
		map.recycle();
		mStartMarkerBitmap.recycle();
		mEndMarkerBitmap.recycle();
		mIntermediateMarkerBitmap.recycle();
		mIntermediateMarkerBitmapDark.recycle();
	}

	public interface onAsstesLoaded {
		public void onAssetsLoaded();
	}
}
