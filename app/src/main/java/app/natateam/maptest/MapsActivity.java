package app.natateam.maptest;

import android.animation.Animator;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.like.LikeButton;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import app.natateam.maptest.db.Flat;
import app.natateam.maptest.db.RealmHelper;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,GoogleMap.OnMarkerClickListener {
    protected Subscription subscription;
    protected GoogleMap mMap;
    private HashMap<Marker, Flat> flatMap;
    private Realm realm;
    private Flat curentFlat;
    @BindView(R.id.imgFlat)
    ImageView imgFlat;
    @BindView(R.id.btnLike)
    LikeButton likeButton;
    @BindView(R.id.txtCost)
    TextView txtCost;
    @BindView(R.id.txtInfo)
    TextView txtInfo;
    @BindView(R.id.cardFlat)
    CardView cardFlat;
    @BindView(R.id.txtAdress)
    TextView txtAdress;
    @BindView(R.id.progress)
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ButterKnife.bind(this);
        realm = RealmHelper.getInstanse().getRealm();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        flatMap = new HashMap<>();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        LatLng moscow = new LatLng(55.754081, 37.6203273);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(moscow, 12));
        subscription = realm.where(Flat.class).findAllAsync().asObservable().
                flatMap(new Func1<RealmResults<Flat>, Observable<Flat>>() {
                    @Override
                    public Observable<Flat> call(RealmResults<Flat> persons) {
                        return Observable.from(persons);
                    }
                }).
                observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Flat>() {
            @Override
            public void call(Flat flat) {
                Marker marker = mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(flat.getLat(), flat.getLon()))
                        .alpha(0.7f).draggable(false).icon(BitmapDescriptorFactory.fromResource(R.drawable.terminal_icon)));
                flatMap.put(marker, flat);
                mMap.setOnMarkerClickListener(MapsActivity.this);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Flat flat = flatMap.get(marker);
        if (flat != null&&flat!=curentFlat) {
            if (cardFlat.getVisibility() == View.GONE) {
                cardFlat.setVisibility(View.VISIBLE);
                showCard();
            }
            likeButton.setLiked(false);
            progressBar.setVisibility(View.VISIBLE);
            Picasso.with(this).cancelRequest(imgFlat);
            Picasso.with(this).load(flat.getImage()).into(imgFlat, new Callback() {
                @Override
                public void onSuccess() {
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onError() {
                    progressBar.setVisibility(View.GONE);
                }
            });
            txtCost.setText(flat.getCost());
            txtAdress.setText(flat.getAdress());
            String dot = " " + getString(R.string.dot) + " ";
            txtInfo.setText(flat.getRoom_count() + getString(R.string.room) + dot + flat.getSquare() + getString(R.string.m2) +
                    dot + flat.getFloor());
            curentFlat = flat;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (cardFlat.getVisibility() == View.VISIBLE) {
            hideCard();
        } else {
            super.onBackPressed();
        }
    }

    public void showCard(){
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.to_top);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        cardFlat.startAnimation(animation);
    }

    public void  hideCard(){
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.to_bottom);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardFlat.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        cardFlat.startAnimation(animation);
    }
}
