package in.silive.Service;

import android.app.Service;
import android.content.Intent;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

/**
 * Created by Kartikay on 08-Apr-15.
 */
public class LocationUpdateService extends Service implements LocationListener {
    boolean isGPSEnabled = false, isNetworkEnabled = false;
    LocationManager lManager;
    long minTime = 2000, minDistance = 2;
    private Location currentLocation;

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onCreate() {
        lManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        isGPSEnabled = lManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnabled = lManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//        getCurrentLocation();
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        locationfunction();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onLocationChanged(Location location) {
locationfunction();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    public void locationfunction() {
        Location presentLocation = null;
        if (!isGPSEnabled && !isNetworkEnabled) {
            Toast.makeText(LocationUpdateService.this, "Please Connect to a network", Toast.LENGTH_SHORT).show();

        } else {
                if (isGPSEnabled)
                lManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, this);
                if (lManager != null)
                    presentLocation = lManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if(lManager==null||presentLocation==null) {
                    lManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minTime, minDistance, this);
                    presentLocation = lManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }
                if (presentLocation != null) {
                    Toast.makeText(this, "Receiving Latitude" + presentLocation.getLatitude() + " Longitue=" + presentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(this, "Receiving Null Location", Toast.LENGTH_SHORT).show();
            }
        }
    }