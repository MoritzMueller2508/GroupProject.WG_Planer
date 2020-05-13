package com.example.wgplaner;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;

public class MyWg extends AppCompatActivity {
    private AWSAppSyncClient mAWSAppSyncClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_wg);
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyWg.this, MainMenu.class));
            }
        });

        TextView wgCode = (TextView) findViewById(R.id.wgCode);
        wgCode.setText(AccessWg.idWgCode);


        MyWgToShoppingList();
        MyWgToDevices();
        MyWgToCalendar();
        MyWgToNews();
    }

    private void MyWgToNews() {
        ImageButton navMyWg_To_News = (ImageButton) findViewById(R.id.btn_news);
        navMyWg_To_News.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyWg.this, News.class));
            }
        });
    }

    private void MyWgToCalendar() {
        ImageButton navMyWg_To_Calendar = (ImageButton) findViewById(R.id.btn_calendar);
        navMyWg_To_Calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyWg.this, Calendar.class));
            }
        });
    }

    private void MyWgToDevices() {
        ImageButton navMyWg_To_Devices = (ImageButton) findViewById(R.id.btn_devices);
        navMyWg_To_Devices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyWg.this, HouseholdDevices.class));
            }
        });
    }

    public void MyWgToShoppingList(){

        ImageButton navMyWg_To_ShoppingList = (ImageButton) findViewById(R.id.btn_shoppingList);
        navMyWg_To_ShoppingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyWg.this, ShoppingList.class));
            }
        });

    }

}
