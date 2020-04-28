package com.example.wgplaner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CreateWg extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_wg);

        CreateWg_To_MyWG();

    }


    public void CreateWg_To_MyWG(){

         Button navCreateWG_To_MyWg = (Button) findViewById(R.id.btn_createWg);
         navCreateWG_To_MyWg.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(CreateWg.this, MyWg.class));
             }
         });

    }
}
