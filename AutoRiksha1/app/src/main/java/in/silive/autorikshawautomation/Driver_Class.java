package in.silive.autorikshawautomation;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import in.silive.Service.LocationUpdateService;
/**
 * Created by kartikey on 4/3/15.
 */
public class Driver_Class extends ActionBarActivity implements View.OnClickListener {
    Button available, notavailable, booked, checkstatus;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.driver);
        //Initialise Buttons variable
        available = (Button) findViewById(R.id.driverAvailable);
        notavailable = (Button) findViewById(R.id.driverNotAvailable);
        booked = (Button) findViewById(R.id.driverBooked);
        available.setOnClickListener(this);
        notavailable.setOnClickListener(this);
        booked.setOnClickListener(this);
        checkstatus = (Button) findViewById(R.id.driverCheckStatus);
        checkstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        stopService(new Intent(getBaseContext(),LocationUpdateService.class));
                        Intent i = new Intent(Driver_Class.this,
                                DriverStatus.class);
                        startActivity(i);
                        overridePendingTransition(android.R.anim.fade_in,
                                android.R.anim.fade_out);
                        finish();
                    }
                }, 1000);
            }
        });
       //intent = new Intent(Driver_Class.this,LocationUpdateService.class);
        //startActivity(intent);
  //startService(new Intent(Driver_Class.this,LocationUpdateService.class));
        Toast.makeText(this,"Service started",Toast.LENGTH_SHORT);
        startService(new Intent(this,LocationUpdateService.class));
        super.onCreate(savedInstanceState);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public void onBackPressed() {
    showLogoutBox();
    }
    public void showLogoutBox(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(
                "Are You sure You Want to logout")
                .setCancelable(false)
                .setPositiveButton("Logout",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                ProgressDialog pDialog = ProgressDialog.show(Driver_Class.this, "LogOut", "Logging Out");
                                try {
                                    Thread.sleep(3000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                stopService(new Intent(getBaseContext(),LocationUpdateService.class));
                                Intent i = new Intent(Driver_Class.this, SignupAndLogin.class);
                                startActivity(i);

                                pDialog.dismiss();
                            }
                        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).setTitle("Error");
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onClick(View v) {
        String text;
        //Setting text variable to a keyword so that it can be appended with url to change driver status on api
        switch (v.getId()) {
            case R.id.driverNotAvailable:
                stopService(new Intent(getBaseContext(),LocationUpdateService.class));
                Toast.makeText(this, "Service stopped", Toast.LENGTH_SHORT);
                text = "not%20available";
                break;
            case R.id.driverAvailable:
                startService(new Intent(this,LocationUpdateService.class));
                Toast.makeText(Driver_Class.this,"Service Started",Toast.LENGTH_SHORT).show();
                text = "available";
                break;
            case R.id.driverBooked:
                text = "booked";
                break;
        }
    }
}