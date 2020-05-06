package com.example.wgplaner;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.amazonaws.mobileconnectors.appsync.sigv4.CognitoUserPoolsAuthProvider;
import com.google.android.material.textfield.TextInputLayout;


public class LoginScreen extends AppCompatActivity {

    private AWSAppSyncClient mAWSAppSyncClient;
    private TextInputLayout textInputEmail;
    private TextInputLayout textInputPassword;
    public int a=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);

        textInputEmail = findViewById(R.id.ti_logInMail);
        textInputPassword = findViewById(R.id.ti_logInPwd);


        final Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());

        mAWSAppSyncClient = AWSAppSyncClient.builder()
                .context(getApplicationContext())
                .awsConfiguration(new AWSConfiguration(getApplicationContext()))
                .cognitoUserPoolsAuthProvider(new CognitoUserPoolsAuthProvider() {
                    @Override
                    public String getLatestAuthToken() {
                        try {
                            return AWSMobileClient.getInstance().getTokens().getIdToken().getTokenString();
                        } catch (Exception e) {
                            Log.e("APPSYNC_ERROR", e.getLocalizedMessage());
                            return e.getLocalizedMessage();
                        }
                    }
                }).build();
        logInToMenu();
    }


    private boolean validateEmail() {

        String emailInput = textInputEmail.getEditText().getText().toString().trim();
        if (emailInput.equals("testStudent@dhbw.com")){
            textInputEmail.setError(null);
            return true;
        }else{
            textInputEmail.setError("Email is wrong!");
            return false;
        }
    }

    private boolean validatePassword(){
        String passwordInput = textInputPassword.getEditText().getText().toString().trim();
        if (passwordInput.equals("TINF19AI1")){
            textInputPassword.setError(null);
            return true;
        }else{
             textInputPassword.setError("Password is wrong!");
            return false;
        }
    }



    public void logInToMenu(){

        Button navLogIn_To_Menu = (Button) findViewById(R.id.btn_logIn);
        navLogIn_To_Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validateEmail() & validatePassword()){
                    startActivity(new Intent(LoginScreen.this, MainMenu.class));
                }else{
                    return;
                }

            }
        });


    }
}
