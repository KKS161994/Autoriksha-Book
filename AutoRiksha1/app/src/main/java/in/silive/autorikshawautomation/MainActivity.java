package in.silive.autorikshawautomation;

import in.silive.Config;
import in.silive.CustomClasses.CustomDialogClass;
import in.silive.adapter.NavDrawerAdapter;
import in.silive.listener.NetworkResponseListener;
import in.silive.model.NavDrawerItem;
import in.silive.network.ConnectivityCheck;
import in.silive.network.DownloadDATApost;
import in.silive.network.KeyValues;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

import com.google.android.gms.maps.model.LatLng;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends ActionBarActivity implements NetworkResponseListener {
    private ActionBar mActionBar;
    private TypedArray mDrawerIcons;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerListView;
    private String[] mDrawerNames;
    private ArrayList<NavDrawerItem> navDrawerItems = new ArrayList<NavDrawerItem>();
    private FragmentManager fm;
    private FragmentTransaction ft;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private Resources mResources;
    ProgressDialog progressDialog;
    private final static String TAG_EVENTS = "event tag";
    private String tag;
    private Fragment frag;
    private ProgressBar pBar;
    JSONObject jObject;
    private String previousTag, previousTitle;
    private boolean isDrawerOpen = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActionBar = getSupportActionBar();
        mResources = getResources();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerListView = (ListView) findViewById(R.id.drawer_list);
        mDrawerNames = mResources.getStringArray(R.array.listItems);
        mDrawerIcons = getResources().obtainTypedArray(R.array.listIcons);
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        for (int i = 0; i < mDrawerNames.length; i = i + 1) {
            navDrawerItems.add(new NavDrawerItem(mDrawerNames[i], mDrawerIcons.getResourceId(i, -1)));
        }
        mDrawerIcons.recycle();
        NavDrawerAdapter mDrawerAdapter = new NavDrawerAdapter(this, navDrawerItems);
        mDrawerListView.setAdapter(mDrawerAdapter);
        mDrawerListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Log.d("MA", "onItemSelected");
                mDrawerLayout.closeDrawers();
                try {
                    itemSelected(position);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.hello_world, R.string.hello_world) {
            @Override
            public void onDrawerOpened(View view) {
                previousTitle = (String) getTitle();
                setTitle("AutoRikshawAutomation");
                Fragment frag = getSupportFragmentManager().findFragmentById(
                        R.id.content_frame);
                if (frag != null)
                    previousTag = frag.getTag();
                isDrawerOpen = true;
                super.onDrawerOpened(view);
            }

            @Override
            public void onDrawerClosed(View view) {
                String current_tag = "";
                current_tag = getSupportFragmentManager().findFragmentById(
                        R.id.content_frame).getTag();
                isDrawerOpen = false;
                if (current_tag.equals(previousTag)) {
                    setTitle(previousTitle);
                }
            }
        };
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
        //method to check connectivity
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.setCustomAnimations(android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
        ft.add(R.id.content_frame, new MapFragment(), TAG_EVENTS)
                .addToBackStack(null).commit();
    }

    private void itemSelected(int pos) throws InterruptedException, MalformedURLException, JSONException {
        switch (pos) {

            case -1:
                frag = null;
                Toast.makeText(this, "Fuck Off", Toast.LENGTH_SHORT).show();
                break;

            case 0:
                frag = new MapFragment();
                tag = "Home";
                break;
            case 1:
                frag = null;
                CustomDialogClass cdc = new CustomDialogClass(this, "", "", null, null);
                cdc.setTitle("Book Vehicle");
                cdc.show();
                break;
            case 2:
                frag = new BookStatusFragment();
                tag = "Know Status";
                break;
            case 3:
                frag = null;
                DownloadDATApost.setNRL(this);
                LatLng pLocation = presentLocation();

                JSONObject jsonOject = new JSONObject();
                jsonOject.put("DangerLocationLatitude", pLocation.latitude + "");
                jsonOject.put("DangerLocationLongitude", pLocation.longitude + "");
                jsonOject.put("CustomerId", "!");
                jsonOject.put("TransportId", "13");
                DownloadDATApost.setJSON(jsonOject);
                DownloadDATApost.setUrl(new URL(Config.createdangerAPI));
                new DownloadDATApost().execute();
                break;
            case 6:
                frag = null;
                showLogoutBox();
                break;
            default:
                frag = null;
                Toast.makeText(this, "Clicked On default", Toast.LENGTH_SHORT).show();
                break;
        }

        switch (ConnectivityCheck.check(this)) {
            case "TRUE":
                if (frag != null) {
                    fm.findFragmentById(R.id.content_frame).getTag();
                    {
                        ft = fm.beginTransaction();
                        ft.setCustomAnimations(android.R.anim.slide_in_left,
                                android.R.anim.slide_out_right);
                        ft.replace(R.id.content_frame, frag, tag).addToBackStack(null)
                                .commit();
                    }
                }
                break;
            case "GPS":
                showBox("GPS");
                break;
            case "INTERNET":
                showBox("INTERNET");
                break;
        }
    }

    public void showLogoutBox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(
                "Are You sure You Want to logout")
                .setCancelable(false)
                .setPositiveButton("Logout",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                ProgressDialog pDialog = ProgressDialog.show(MainActivity.this, "LogOut", "Logging Out");

                                Intent i = new Intent(MainActivity.this, SignupAndLogin.class);
                                startActivity(i);

                                pDialog.dismiss();
                            }
                        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).setTitle("Logout");
        AlertDialog alert = builder.create();
        alert.show();
    }
    public LatLng presentLocation() {
boolean isGPSEnabled=false,isNetworkEnabled=false;
        LocationManager lManager = null;
        //Location location;
        isGPSEnabled = lManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnabled = lManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        lManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        /*if(isGPSEnabled)
        location=lManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        else
        location=lManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        */LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = service.getBestProvider(criteria, false);
        Location location = service.getLastKnownLocation(provider);
        LatLng presentLocation = new LatLng(location.getLatitude(), location.getLongitude());
        return presentLocation;
    }

    public void showBox(final String error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(
                "Your GPS or INTERNET seems to be disabled \nDo you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                if (error.equals("GPS"))
                                    startActivity(new Intent(
                                            android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                                else
                                    startActivity(new Intent(WifiManager.ACTION_PICK_WIFI_NETWORK));

                            }
                        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).setTitle("Error");
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mActionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (isDrawerOpen)
            mDrawerLayout.closeDrawers();
        else {
            //	addtoB
            try {
                if (fm.getBackStackEntryCount() == 1)
                    showLogoutBox();
                else
                    itemSelected(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void onPreExecute() {
progressDialog=ProgressDialog.show(MainActivity.this,"Danger","Sending request");
    }
    @Override
    public void onPostExecute(String data) throws JSONException {
        progressDialog.dismiss();
if(DownloadDATApost.getResultCode()== HttpURLConnection.HTTP_OK)
    Toast.makeText(MainActivity.this,"Your result has been successfully sent",Toast.LENGTH_SHORT).show();
 else
    Toast.makeText(MainActivity.this,"Some Problem in sending results please try again later",Toast.LENGTH_SHORT).show();
    }
}