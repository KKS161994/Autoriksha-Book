package in.silive.autorikshawautomation;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
public class SignUp extends ActionBarActivity {
    Button onSignUpClick;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        getSupportActionBar();
        setContentView(R.layout.sign_up);
        onSignUpClick = (Button) findViewById(R.id.registersignup);
        onSignUpClick.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        Intent i = new Intent(SignUp.this,
                                MainActivity.class);
                        startActivity(i);
                        overridePendingTransition(android.R.anim.fade_in,
                                android.R.anim.fade_out);
                        finish();
                    }
                }, 1000);
            }
        });
    }
    public void onBackPressed(){
    Intent i=new Intent(SignUp.this,SignupAndLogin.class);
    startActivity(i);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
}