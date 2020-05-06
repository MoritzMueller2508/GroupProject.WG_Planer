package com.example.wgplaner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;

public class MainMenu extends AppCompatActivity {
    private AWSAppSyncClient mAWSAppSyncClient;
    public MainActivity firstLayout = new MainActivity();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);

        final Toolbar toolbar = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());

        MenuToCreateWG();
        MenuToAccessWG();
        MenuToLogout();
        MenuToMyWg();

    }



    public void MenuToCreateWG(){

        Button navMainMenu_To_CreateWG = (Button) findViewById(R.id.btn_createWg_menu);
        navMainMenu_To_CreateWG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenu.this, CreateWg.class));
            }
        });

    }

    public void MenuToAccessWG(){

        Button navMainMenu_To_AccesWG = (Button) findViewById(R.id.btn_accessWg_menu);
        navMainMenu_To_AccesWG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenu.this, AccessWg.class));
            }
        });
    }

    public void MenuToLogout(){

        Button navMainMenu_To_Logout = (Button) findViewById(R.id.btn_logout_menu);
        navMainMenu_To_Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenu.this, MainActivity.class));
            }
        });

    }

    public void MenuToMyWg(){

        Button navMainMenu_To_MyWg = (Button) findViewById(R.id.btn_myWg_menu);
        navMainMenu_To_MyWg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firstLayout.access == 1) {
                startActivity(new Intent(MainMenu.this, MyWg.class));
                } else {
                    return;
                }
            }
        });

    }
    }
