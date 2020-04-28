package com.example.wgplaner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CreateAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);

       CreateAccountToMenu();
    }

    public void CreateAccountToMenu(){

        Button navCreateAcc_To_MainMenu = (Button) findViewById(R.id.btn_createAcc);
        navCreateAcc_To_MainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateAccount.this, MainMenu.class));
            }
        });

    }
}
