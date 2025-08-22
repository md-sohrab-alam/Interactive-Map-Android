package qrail.com.interactivemap.widgets.interactivemap;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;

import qrail.com.interactivemap.datahelper.StationHelper;
import qrail.com.interactivemap.dataobjects.Result;
import qrail.com.interactivemap.dataobjects.ResultNode;
import qrail.com.interactivemap.dataobjects.Station;
import qrail.com.interactivemap.listener.OnMarkerChanged;
import qrail.com.interactivemap.stationroute.OnFillDataListener;
import qrail.com.interactivemap.stationroute.StationRoute;
import qrail.com.interactivemap.util.AppConstants;
import qrail.com.interactivemap.util.LoadBitmap;
import qrail.com.interactivemap.widgets.imageview.ImageViewTouch;

public class InteractiveMapView extends ImageViewTouch implements
        OnMarkerChanged, AppConstants {

    private Context context;
    private ArrayList<ResultNode> routeStationsList;
    private ArrayList<String> routeTerminals = new ArrayList<String>();
    private MapBitmapDrawable drawable;
    private OnRouteSelected mListener;

    private boolean blinkState = false;

    public InteractiveMapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

    }

    public void setRouteStation(ArrayList<ResultNode> routeStationsList) {
        this.routeStationsList = routeStationsList;
        drawable.setRouteStationList(routeStationsList);
        drawable.setMapState(MAP_END_SELECTED);
        adjustPan();
        postInvalidate();
    }

    public void setOnRouteSelectedListener(OnRouteSelected mListener) {
        this.mListener = mListener;

    }

    public void setRouteStation(String startID, String endID) {
        StationRoute route = new StationRoute(context, startID, endID);
        mListener.onStartComputation();
        route.fillDataAsync(new OnFillDataListener() {

            @Override
            public void onComplete(ArrayList<Result> results) {
                routeStationsList = results.get(0).getResultStations();
                setRouteStation(routeStationsList);
                if (mListener != null) {
                    mListener.onSelectRoute(results.get(0));
                }
            }
        });
    }

    public void setImageBitmap(Context context) {

        drawable = new MapBitmapDrawable(LoadBitmap.getMap(), context);
        drawable.setMarkers(LoadBitmap.getmStartMarkerBitmap(),
                LoadBitmap.getmIntermediateMarkerBitmap(),
                LoadBitmap.getmEndMarkerBitmap());
        setImageDrawable(drawable);

    }

    final float[] getPointerCoords(MotionEvent e) {
        final int index = e.getActionIndex();
        final float[] coords = new float[]{e.getX(index), e.getY(index)};
        Matrix matrix = new Matrix();
        this.getImageMatrix().invert(matrix);
        matrix.postTranslate(this.getScrollX(), this.getScrollY());
        matrix.mapPoints(coords);
        return coords;
    }

    private void getStationID(float[] cord) {
        ArrayList<Station> stationList = StationHelper.getInstance(context)
                .getStationsList();
        for (int i = 0; stationList != null && i < stationList.size(); i++) {
            float boundX = Math.abs(stationList.get(i).getxCord() - cord[0]);
            float boundY = Math.abs(stationList.get(i).getyCord() - cord[1]);

            Bitmap mStartMarker = LoadBitmap.getmStartMarkerBitmap();
            if (mStartMarker == null) {
                LoadBitmap.getInstance(context).loadMarkers();
            } else {
                if (boundX < mStartMarker.getWidth()
                        && boundY < LoadBitmap.getmStartMarkerBitmap().getHeight()) {
                    routeTerminals.add(stationList.get(i).getStationID());
                    break;
                }
            }

        }

    }

    public void clearRoute() {
        if (routeStationsList != null)
            routeStationsList.clear();
        routeTerminals.clear();

    }

    public interface OnRouteSelected {

        public void onSelectRoute(Result route);

        public void onSelectStart();

        public void onStartComputation();

    }

    public void onSingleTap(MotionEvent event) {

        float[] cord = getPointerCoords(event);
        getStationID(cord);
        if (routeTerminals.size() == 1) {
            drawable.setStart(StationHelper.getInstance(context)
                    .getStationById(routeTerminals.get(0)));
            drawable.setMapState(MAP_START_SELECTED);
            postInvalidate();
            mListener.onSelectStart();
        }

        if (routeTerminals.size() == 2
                && drawable.getMapState() != MAP_END_SELECTED) {
            if (!routeTerminals.get(0).equals(routeTerminals.get(1))) {

                setRouteStation(routeTerminals.get(0), routeTerminals.get(1));
            } else {
                routeTerminals.remove(routeTerminals.size() - 1);
            }
        }

    }

    private void adjustPan() {

        if (routeStationsList != null) {

            float meanX = 0;
            float meanY = 0;

            int totalX = 0;
            int totalY = 0;

            for (int i = 0; i < routeStationsList.size(); i++) {
                totalX += routeStationsList.get(i).getStation().getxCord();
                totalY += routeStationsList.get(i).getStation().getyCord();
            }

            meanX = (float) totalX / (float) routeStationsList.size();
            meanY = (float) totalY / (float) routeStationsList.size();

            float maxDist = 0;

            for (int i = 0; i < routeStationsList.size(); i++) {

                int x = routeStationsList.get(i).getStation().getxCord();
                int y = routeStationsList.get(i).getStation().getyCord();

                float dist = (float) Math.sqrt((meanX - x) * (meanX - x)
                        + (meanY - y) * (meanY - y));

                if (dist > maxDist) {
                    maxDist = dist;
                }
            }

            float scale = drawable.getIntrinsicWidth() / (2.0f * maxDist);

            System.out.println("scale = " + scale + " max dist :: " + maxDist);

            new Handler().postDelayed(
                    new AdjustPanRunnable(meanX, meanY, scale), 100);

        }
    }

    private class AdjustPanRunnable implements Runnable {

        float x;
        float y;
        float scale;

        public AdjustPanRunnable(float x, float y, float scale) {
            this.x = x;
            this.y = y;
            this.scale = scale;
        }

        @Override
        public void run() {
            float values[] = new float[9];
            getImageMatrix().getValues(values);

            printMatrix(getImageMatrix());

            scrollBy(-(x * values[Matrix.MSCALE_X] + values[Matrix.MTRANS_X])
                            + getWidth() / 2f,
                    -(y * values[Matrix.MSCALE_Y] + values[Matrix.MTRANS_Y])
                            + getHeight() / 2f, 50);

            invalidate();
        }
    }

    public void isInteractiveMap(boolean val) {
        drawable.setIsInteractive(val);
    }

    public void setDrawableMarkers(boolean blink) {

        if (blink)
            drawable.setMarkers(LoadBitmap.getmStartMarkerBitmap(),
                    LoadBitmap.getmIntermediateMarkerBitmap(),
                    LoadBitmap.getmEndMarkerBitmap());
        else {
            drawable.setMarkers(LoadBitmap.getmStartMarkerBitmap(),
                    LoadBitmap.getmIntermediateMarkerBitmapDark(),
                    LoadBitmap.getmEndMarkerBitmap());
        }
        postInvalidate();

    }

    Handler mHandler = new Handler();

    final Runnable runnable = new Runnable() {

        @Override
        public void run() {
            if (drawable.getMapState() == MAP_END_SELECTED) {
                blinkState = !blinkState;
                setDrawableMarkers(blinkState);
                mHandler.postDelayed(this, 500);
            }

        }
    };

    public void blinkStations() {
        mHandler.postDelayed(runnable, 100);
    }

    public void stopBlinking() {
        mHandler.removeCallbacks(runnable);
    }

    @Override
    public void onMarkerChanged() {
        postInvalidate();

    }

}
