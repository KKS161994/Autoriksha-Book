package in.silive.network;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by Kartikay on 07-Apr-15.
 */
public class ConnectivityCheck {
    public static String check(Context context) {
        Toast.makeText(context, "Reachec connectivity check", Toast.LENGTH_SHORT).show();
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = (NetworkInfo) cm.getActiveNetworkInfo();
        boolean status = false;
        if (netInfo != null && netInfo.isConnected()) {
            LocationManager loc = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            if (!loc.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                Toast.makeText(context, "Returnin gps", Toast.LENGTH_SHORT).show();
                return "GPS";
            } else {
                Toast.makeText(context, "Returnin true", Toast.LENGTH_SHORT).show();
                return "TRUE";
            }
        }
        Toast.makeText(context, "Returnin Internet", Toast.LENGTH_SHORT).show();
        return "INTERNET";
    }
}