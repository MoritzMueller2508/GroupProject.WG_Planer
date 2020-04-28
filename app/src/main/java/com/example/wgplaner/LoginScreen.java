package com.example.wgplaner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);

        LogInToMenu();


    }

    public void LogInToMenu(){
        Button navLogIn_To_Menu = (Button) findViewById(R.id.btn_logIn);
        navLogIn_To_Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginScreen.this, MainMenu.class));
            }
        });



    }
}
