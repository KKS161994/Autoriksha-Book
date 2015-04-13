package in.silive.autorikshawautomation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.regex.Pattern;

public class SignupAndLogin extends Activity {
    Button login_btn, signup_btn;
    Intent i;
    EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginandsplash);
        email = (EditText) findViewById(R.id.login_name);
        password = (EditText) findViewById(R.id.loginpassword);
        login_btn = (Button) findViewById(R.id.login);
        signup_btn = (Button) findViewById(R.id.sign_up);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String emailRegexString = "\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";

                if ((password.getText().toString()).equals("") || ((email.getText().toString()).equals("")) || (!Pattern.matches(emailRegexString, email.getText().toString()))) {
                    if ((password.getText().toString()).equals(""))
                        password.setError("Please Enter a suitable password");
                    if ((email.getText().toString()).equals(""))
                        email.setError("Please Enter a suitable email");
                    if (!Pattern.matches(emailRegexString, email.getText().toString()))
                        email.setError("Email Format Exception");
                } else
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            RadioGroup usertypeRadioGroup = (RadioGroup) findViewById(R.id.typeOfUser);
                            final int user_type = usertypeRadioGroup.getCheckedRadioButtonId();

                            if (user_type == R.id.loginuser) {
                                i = new Intent(SignupAndLogin.this,
                                        MainActivity.class);
                                Toast.makeText(SignupAndLogin.this, "User Clicked" + user_type, Toast.LENGTH_SHORT).show();
                            } else {
                                i = new Intent(SignupAndLogin.this,
                                        Driver_Class.class);
                                Toast.makeText(SignupAndLogin.this, "Driver Clicked", Toast.LENGTH_SHORT).show();

                            }
                            startActivity(i);
                            overridePendingTransition(android.R.anim.fade_in,
                                    android.R.anim.fade_out);
                            finish();
                        }
                    }, 1000);
            }
        });
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        i = new Intent(SignupAndLogin.this,
                                SignUp.class);
                        startActivity(i);
                        overridePendingTransition(android.R.anim.fade_in,
                                android.R.anim.fade_out);
                        finish();
                    }
                }, 1000);
            }
        });
    }
}