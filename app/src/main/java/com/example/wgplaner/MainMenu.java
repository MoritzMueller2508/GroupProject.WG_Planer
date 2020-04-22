package com.example.wgplaner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);

        MenuToCreateWG();
        MenuToAccessWG();
        MenuToLogout();
       // MenuToMyWG();
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

    //public void
}
