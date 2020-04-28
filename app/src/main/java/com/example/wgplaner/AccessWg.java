package com.example.wgplaner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AccessWg extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.access_wg);

        AccessWG_To_MyWG();
    }

    public void AccessWG_To_MyWG(){

        Button navAccess_To_MyWG = (Button) findViewById(R.id.btn_searchForWg);
        navAccess_To_MyWG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccessWg.this, MyWg.class));
            }
        });

    }
}
