package com.example.wgplaner;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.amazonaws.mobileconnectors.cognitoauth.Auth;


public class MainMenu extends AppCompatActivity {
    private AWSAppSyncClient mAWSAppSyncClient;
    public MainActivity firstLayout = new MainActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);

        AWSMobileClient.getInstance().initialize(getApplicationContext(), new Callback<UserStateDetails>() {

                    @Override
                    public void onResult(UserStateDetails userStateDetails) {
                        Log.i("INIT", "onResult: " + userStateDetails.getUserState());
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e("INIT", "Initialization error.", e);
                    }
                }
        );

        final Toolbar toolbar = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());

        MenuToCreateWG();
        MenuToAccessWG();
        MenuToLogout();
        MenuToMyWg();
    }



    public void MenuToCreateWG(){

        ImageButton navMainMenu_To_CreateWG = (ImageButton) findViewById(R.id.btn_createWg_menu);
        navMainMenu_To_CreateWG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenu.this, CreateWg.class));
            }
        });

    }

    public void MenuToAccessWG(){

        ImageButton navMainMenu_To_AccesWG = (ImageButton) findViewById(R.id.btn_accessWg_menu);
        navMainMenu_To_AccesWG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenu.this, AccessWg.class));
            }
        });
    }

    public void MenuToLogout(){

        ImageButton navMainMenu_To_Logout = (ImageButton) findViewById(R.id.btn_logout_menu);
        navMainMenu_To_Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AWSMobileClient.getInstance().signOut();
                Log.e("Sign out", "User is now signed out");
                startActivity(new Intent(MainMenu.this, MainActivity.class));
            }
        });

    }

    public void MenuToMyWg(){

        ImageButton navMainMenu_To_MyWg = (ImageButton) findViewById(R.id.btn_myWg_menu);
        navMainMenu_To_MyWg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AWSMobileClient.getInstance().initialize(getApplicationContext(), new Callback<UserStateDetails>() {

                            @Override
                            public void onResult(UserStateDetails userStateDetails) {
                                Log.i("INIT", "onResult: " + userStateDetails.getUserState());
                                startActivity(new Intent(MainMenu.this, MyWg.class));
                            }

                            @Override
                            public void onError(Exception e) {
                                Log.e("INIT", "Initialization error.", e);
                            }
                        }
                );
            }
        });

    }

}
