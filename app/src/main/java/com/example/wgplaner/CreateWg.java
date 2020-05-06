package com.example.wgplaner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class CreateWg extends AppCompatActivity {

    public MainActivity firstLayout = new MainActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_wg);


        final Toolbar toolbar = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());

        CreateWgToMyWg();
    }

    public void CreateWgToMyWg(){

        Button navCreateWg_To_MyWg = (Button) findViewById(R.id.btn_createWg);
        navCreateWg_To_MyWg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstLayout.setAccess(1);
                startActivity(new Intent(CreateWg.this, MyWg.class));
            }
        });

    }
}
