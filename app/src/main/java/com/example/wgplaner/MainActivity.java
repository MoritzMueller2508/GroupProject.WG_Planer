package com.example.wgplaner;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.SignInUIOptions;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.mobile.client.UserStateListener;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Creating", "onCreate: Creating");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_please_wait_redirection);

        AWSMobileClient.getInstance().initialize(getApplicationContext(), new Callback<UserStateDetails>() {

            @Override
            public void onResult(UserStateDetails userStateDetails) {
                Log.i("INIT", "onResult: " + userStateDetails.getUserState());

                switch (userStateDetails.getUserState()){
                    case SIGNED_IN:
                        startActivity(new Intent(MainActivity.this, MainMenu.class));
                        break;
                    case SIGNED_OUT:
                        showSignIn();
                        break;
                    default:
                        AWSMobileClient.getInstance().signOut();
                        showSignIn();
                        break;
                }
            }


            @Override
            public void onError(Exception e) {
                Log.e("INIT", e.toString());
            }
        });


        /**Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        **/



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
/**
    public void loginScreenButton_HomeMenu(){

        Button navToLoginScreen = (Button) findViewById(R.id.btn_logInHome);
        navToLoginScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginScreen.class));
            }
        });


    }

    public void registerScreenButton_HomeMenu(){

        Button navToRegisterScreen = (Button) findViewById(R.id.btn_register);
        navToRegisterScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CreateAccount.class));
            }
        });
    }
     **/


    public void showSignIn() {

        try{
            AWSMobileClient.getInstance().showSignIn(this,
                    SignInUIOptions.builder().
                            nextActivity(MainMenu.class)
                            .canCancel(true)
                            .build());
        } catch (Exception e) {

            Log.e(TAG,e.toString());

        }


    }



}
