package qrail.com.interactivemap.activity;

import android.app.Dialog;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import qrail.com.interactivemap.R;
import qrail.com.interactivemap.database.MetroDbHelper;
import qrail.com.interactivemap.datahelper.StationHelper;
import qrail.com.interactivemap.dataobjects.Result;
import qrail.com.interactivemap.util.LoadBitmap;
import qrail.com.interactivemap.widgets.imageview.ImageViewTouch;
import qrail.com.interactivemap.widgets.interactivemap.InteractiveMapView;
import qrail.com.interactivemap.widgets.interactivemap.MapBitmapDrawable;

import static qrail.com.interactivemap.util.AppConstants.MAP_END_SELECTED;
import static qrail.com.interactivemap.util.AppConstants.PLAIN_MAP;

public class InteractiveMapActivity extends AppCompatActivity implements View.OnClickListener {

    private InteractiveMapView mapView;
    private ImageView cancel;
    private ProgressBar progressBar;
    public static boolean routeSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
        setContentView(R.layout.fragment_interactive_map);
        initViews();
        LoadBitmap.getInstance(this).loadMarkers();
        LoadBitmap.getInstance(this).setOnAssetsLoadedListener(
                new LoadBitmap.onAsstesLoaded() {
                    @Override
                    public void onAssetsLoaded() {
                        if (mapView != null) {
                            mapView.setImageBitmap(InteractiveMapActivity.this);
                            mapView.isInteractiveMap(true);
                        }
                        if (progressBar != null) {
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }

    private void initViews() {
        try {
            mapView = findViewById(R.id.interactive_map_view);
            cancel = findViewById(R.id.cancel);
            progressBar = findViewById(R.id.progressbar);

            if (mapView == null) {
                throw new IllegalStateException("mapView not found");
            }

            if (cancel != null) {
                cancel.setOnClickListener(this);
            }

            mapView.setDoubleTapEnabled(false);

            mapView.setSingleTapListener(new ImageViewTouch.OnImageViewTouchSingleTapListener() {
                @Override
                public void onSingleTapConfirmed(MotionEvent e) {
                    if (mapView != null)
                        mapView.onSingleTap(e);
                }
            });

            mapView.setOnRouteSelectedListener(new InteractiveMapView.OnRouteSelected() {
                @Override
                public void onSelectRoute(Result route) {
                    hidePorogressDialog();
                    if (mapView != null) {
                        mapView.blinkStations();
                    }
                    routeSelected = true;
                }

                @Override
                public void onSelectStart() {
                    // Commented code remains the same
                }

                @Override
                public void onStartComputation() {
                    showProgressDialog();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.cancel) {
            clearRoute();
        }
    }

    private void clearRoute() {
        if (mapView != null && mapView.getDrawable() != null) {
            MapBitmapDrawable drawable = (MapBitmapDrawable) mapView.getDrawable();
            mapView.stopBlinking();
            mapView.clearRoute();
            
            if (cancel != null && cancel.getVisibility() == View.VISIBLE) {
                setAnimation(cancel, R.anim.view_exit_top);
            }

            drawable.setMapState(PLAIN_MAP);
            mapView.postInvalidate();
            routeSelected = false;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mapView != null && mapView.getDrawable() != null) {
            mapView.isInteractiveMap(true);
            MapBitmapDrawable mapD = (MapBitmapDrawable) mapView.getDrawable();
            if (routeSelected)
                mapD.setMapState(MAP_END_SELECTED);
            mapView.postInvalidate();
        }
    }

    private void setAnimation(final View view, int anim) {
        if (view == null) return;
        
        try {
            Animation animation = AnimationUtils.loadAnimation(this, anim);

            if (view == cancel) {
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        if (cancel != null) {
                            int pVis = cancel.getVisibility();
                            cancel.setVisibility((pVis == View.VISIBLE) ? View.GONE : View.VISIBLE);
                            cancel.setEnabled(false);
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        if (cancel != null) {
                            cancel.setEnabled(true);
                        }
                    }
                });
            }

            view.startAnimation(animation);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        if (routeSelected) {
            clearRoute();
        } else {
            super.onBackPressed();
        }
    }

    private void getData() {
        try {
            MetroDbHelper.getInstance(this).createDataBase();
            StationHelper.getInstance(this).fillDataAsync(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Dialog progressDialog;

    public void showProgressDialog() {
        if (progressDialog == null)
            createDialoge();
        if (progressDialog != null) {
            progressDialog.show();
        }
    }

    private void createDialoge() {
        try {
            progressDialog = new Dialog(this, R.style.CustamDialogProgress);
            progressDialog.setContentView(R.layout.dialog_progress);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void destroyProgressDialog() {
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
                progressDialog = null;
            }
        }
    }

    @Override
    protected void onDestroy() {
        destroyProgressDialog();
        try {
            super.onDestroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hidePorogressDialog() {
        if (progressDialog != null)
            progressDialog.hide();
    }
}
