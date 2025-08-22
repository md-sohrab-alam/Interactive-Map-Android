package qrail.com.interactivemap.widgets.interactivemap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import java.util.ArrayList;
import java.util.HashMap;

import qrail.com.interactivemap.R;
import qrail.com.interactivemap.dataobjects.ResultNode;
import qrail.com.interactivemap.dataobjects.Station;
import qrail.com.interactivemap.util.AppConstants;
import qrail.com.interactivemap.util.AppUtils;
import qrail.com.interactivemap.widgets.imageview.FastBitmapDrawable;

public class MapBitmapDrawable extends FastBitmapDrawable implements
        AppConstants {
    protected Context context;

    // route stations marker

    protected Bitmap mStartMarkerBitmap;
    protected Bitmap mIntermediateMarkerBitmap;
    protected Bitmap mEndMarkerBitmap;

    protected static boolean isInteractive = false;

    private int state;
    private StationCoordiante start;
    private Paint paint;

    private Paint transParentBackground;
    protected int stationsCount;
    private ArrayList<StationCoordiante> cordiantes;

    // route stations list
    protected ArrayList<ResultNode> routeStationList;

    private HashMap<String, StationCoordiante> turnMap;

    private Path path;

    public void setMarkers(Bitmap bmStart, Bitmap bmIntermediate, Bitmap bmEnd) {
        mStartMarkerBitmap = bmStart;
        mIntermediateMarkerBitmap = bmIntermediate;
        mEndMarkerBitmap = bmEnd;
    }

    public void setIsInteractive(boolean value) {
        isInteractive = value;
    }

    public MapBitmapDrawable(Bitmap map, Context context) {
        super(map);
        this.context = context;
    }

    public void setRouteStationList(ArrayList<ResultNode> routeStationsList) {
        this.routeStationList = routeStationsList;
        if (routeStationList != null) {
            stationsCount = routeStationsList.size();
            initPath();
            fillHashTurn();
            initStationCordianates();

        }
        initPaintTransparentBackground();
    }

    private void initPath() {
        path = new Path();
        paint = initPaint();

    }

    public void initPaintTransparentBackground() {
        transParentBackground = new Paint();
        transParentBackground.setColor(context.getResources().getColor(
                R.color.white));
        transParentBackground.setAlpha(170);

    }

    private void initStationCordianates() {
        cordiantes = new ArrayList<MapBitmapDrawable.StationCoordiante>();
        for (int i = 0; i < routeStationList.size(); i++) {
            if (routeStationList.get(i).getStation() == null)
                continue;
            int xCord = routeStationList.get(i).getStation().getxCord();
            int yCord = routeStationList.get(i).getStation().getyCord();

            if (routeStationList.get(i).getStation().getStationID().equals("316")) {
                xCord = 983;
                yCord = 1010;
            }
            int paintColor = 0;
            int colorIndex = 0;
            if (i != 0 && routeStationList.get(i - 1).getColor() != null) {
                if (routeStationList.get(i - 1).getColor().size() == 2) {
                    colorIndex = 1;
                }
                paintColor = getColor(AppUtils.getColor(routeStationList
                        .get(i - 1).getColor().get(colorIndex)));
            }

            if (i != 0) {
                String cID = routeStationList.get(i).getStation()
                        .getStationID();
                String pID = routeStationList.get(i - 1).getStation()
                        .getStationID();
                String key = cID + "," + pID;

                String keyRev = pID + "," + cID;
                if (turnMap.containsKey(key)) {
                    addTurn(key, paintColor);
                    if (ONE_EXTRA_POINT_BETWEEN_AIRPORT_AND_NAFIE.equals(key)) {
                        addOneMoreTurn(paintColor);
                    }
                }
                if (turnMap.containsKey(keyRev)) {
                    if (ONE_EXTRA_POINT_BETWEEN_AIRPORT_AND_NAFIE_REVERSE.equals(key)) {
                        addOneMoreTurn(paintColor);
                    }
                    addTurn(keyRev, paintColor);

                }
            }
            cordiantes.add(new StationCoordiante(xCord, yCord, false,
                    paintColor));

        }
    }

    private static final String ONE_EXTRA_POINT_BETWEEN_AIRPORT_AND_NAFIE_REVERSE = "317,313";
    private static final String ONE_EXTRA_POINT_BETWEEN_AIRPORT_AND_NAFIE = "313,317";

    private void addTurn(String key, int paintColor) {


                StationCoordiante cord = turnMap.get(key);
        cord.color = paintColor;
        cordiantes.add(cord);
        stationsCount++;

    }

    private void addOneMoreTurn(int paintColor) {
        StationCoordiante cord = new StationCoordiante(984, 874, true);
        cord.color = paintColor;
        cordiantes.add(cord);
        stationsCount++;

    }

    private int getColor(int color) {
        if (context != null)
            return context.getResources().getColor(color);
        else
            return 0;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        switch (state) {
            case MAP_START_SELECTED:

                if (mStartMarkerBitmap != null && mPaint != null && start != null)
                    canvas.drawBitmap(mStartMarkerBitmap, start.bmXCord,
                            start.bmYCord, mPaint);
                break;

            case MAP_END_SELECTED:

                canvas.drawPaint(transParentBackground);

                path.moveTo(cordiantes.get(0).xCord, cordiantes.get(0).yCord);
                for (int i = 0; i < cordiantes.size(); i++) {
                    path.lineTo(cordiantes.get(i).xCord, cordiantes.get(i).yCord);
                    paint.setColor(cordiantes.get(i).getColor());
                    canvas.drawPath(path, paint);
                    path.reset();
                    path.moveTo(cordiantes.get(i).xCord, cordiantes.get(i).yCord);

                }

                for (int i = 0; i < cordiantes.size(); i++) {

                    if (i == 0) {
                        canvas.drawBitmap(mStartMarkerBitmap,
                                cordiantes.get(0).bmXCord,
                                cordiantes.get(0).bmYCord, super.mPaint);
                    } else if (i == stationsCount - 1) {
                        canvas.drawBitmap(mEndMarkerBitmap,
                                cordiantes.get(stationsCount - 1).bmXCord,
                                cordiantes.get(stationsCount - 1).bmYCord,
                                super.mPaint);
                    } else if (!cordiantes.get(i).isTurn) {
                        canvas.drawBitmap(mIntermediateMarkerBitmap,
                                cordiantes.get(i).bmXCord,
                                cordiantes.get(i).bmYCord, super.mPaint);
                    }

                }

                break;
            default:
                break;
        }

    }

    private Paint initPaint() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(13);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        return paint;
    }

    public class StationCoordiante {
        private int xCord;
        private int yCord;
        private int bmXCord;
        private int bmYCord;

        private boolean isTurn = false;
        private int color;

        public StationCoordiante(int x, int y, boolean isTurn, int color) {
            xCord = x;
            yCord = y;
            bmXCord = xCord - mStartMarkerBitmap.getWidth() / 2;
            bmYCord = yCord - mStartMarkerBitmap.getHeight() / 2;
            this.isTurn = isTurn;
            this.color = color;
        }

        public StationCoordiante(int x, int y, boolean isTurn) {
            xCord = x;
            yCord = y;
            this.isTurn = isTurn;
        }

        public int getColor() {
            return color;
        }

    }

    public void setStart(Station station) {
        start = new StationCoordiante(station.getxCord(), station.getyCord(),
                false, 0);
    }

    public void setMapState(int state) {
        this.state = state;

    }

    public int getMapState() {
        return state;

    }

    /**
     * Hard Coded turning points coordinates in a HashMap with neighbor stations
     * as key
     */
    public void fillHashTurn() {
        // Green Line
        turnMap = new HashMap<String, StationCoordiante>();
        turnMap.put("104,105", new StationCoordiante(538, 524, true));
        turnMap.put("103,104", new StationCoordiante(431, 463, true));

        turnMap.put("108,110", new StationCoordiante(957, 525, false));

        //Red line
        turnMap.put("301,302", new StationCoordiante(905, 244, true));
        turnMap.put("302,303", new StationCoordiante(982, 293, true));
        turnMap.put("313,317", new StationCoordiante(1010, 889, true));
        turnMap.put("208,110", new StationCoordiante(957, 633, true));

        //Yellow Line
        turnMap.put("204,205", new StationCoordiante(617, 633, true));

    }
}
