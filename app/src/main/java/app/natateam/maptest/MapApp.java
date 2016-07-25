package app.natateam.maptest;

import android.app.Application;
import android.widget.Toast;

/**
 * Created by macbook on 25/07/ 15.
 */
public class MapApp extends Application {
    private static  MapApp mapApp;
    private Toast toast;
    @Override
    public void onCreate() {
        super.onCreate();
        mapApp = this;
    }

    public static MapApp getInstanse(){
        if (mapApp ==null){
            mapApp = new MapApp();
            mapApp.onCreate();
        }
        return mapApp;
    }

}
