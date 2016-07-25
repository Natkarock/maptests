package app.natateam.maptest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by macbook on 25/07/ 15.
 */
public class MainActivity extends AppCompatActivity {
    @BindView(R.id.btnMarker)Button btnMarker;
    @BindView(R.id.btnTracking)Button btnTracking;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnMarker)
    public void startMarker(){
        startActivity(MapsActivity.class);
    }

    @OnClick(R.id.btnTracking)
    public void startTracking(){
        startActivity(MapTrackingActivity.class);
    }

    private void startActivity(Class<? extends Activity> activityClass) {
        startActivity(new Intent(this, activityClass));
    }
}
