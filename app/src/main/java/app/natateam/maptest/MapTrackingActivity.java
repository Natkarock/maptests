package app.natateam.maptest;

import android.graphics.Point;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by macbook on 25/07/ 15.
 */
public class MapTrackingActivity extends FragmentActivity implements OnMapReadyCallback {
    protected GoogleMap mMap;
    protected Subscription subscription;
    final LatLng startPont = new LatLng(55.740638,37.653022);
    final LatLng endPoint = new LatLng(55.7697003,37.5942693);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    private  Marker marker;
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        marker= addMarker(startPont);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(startPont,12));
        setSubscription();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mMap!=null){
            setSubscription();
        }
    }

    public void setSubscription(){
        Observable<Long> observable = Observable.interval(500,TimeUnit.MILLISECONDS);
        final int point_count = 20;
        subscription =  observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        long inCount = aLong%point_count;
                        double t = 1-inCount*(1/(double)point_count);
                        double ax = startPont.latitude;
                        double ay = startPont.longitude;
                        double bx = endPoint.latitude;
                        double by = endPoint.longitude;
                        addMarker(getIntermediatePoint(ax,ay,bx,by,t));
                    }
                });
    }

    public Marker addMarker(LatLng latLng){
        if (marker!=null){
            marker.remove();
        }
         marker= mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .alpha(0.7f).draggable(false).icon(BitmapDescriptorFactory.fromResource(R.drawable.terminal_icon)));
        return marker;
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (subscription!=null) {
            subscription.unsubscribe();
        }
    }




    public static LatLng getIntermediatePoint (double alatRad,
                            double alonRad,
                            double blatRad,
                            double blonRad,
                            double t) {
        // finding point C coordinate
        double x = (1 - t) * alatRad + t * blatRad;
        double y = (1 - t) * alonRad + t * blonRad;
        return new LatLng(x,y);


    }
}
