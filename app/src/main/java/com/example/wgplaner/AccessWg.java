package com.example.wgplaner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AccessWg extends AppCompatActivity {

    private EditText textInputCode;
    public MainActivity firstLayout = new MainActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.access_wg);


        textInputCode = findViewById(R.id.et_wgCode);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());

        AccessWgToMyWg();


    }


    private boolean validateEmail() {

        String codeInput = textInputCode.getText().toString().trim();
        if (codeInput.equals("#ABCDEF")) {
            textInputCode.setError(null);
            return true;
        } else {
            textInputCode.setError("Code is wrong!");
            return false;
        }
    }


    public void AccessWgToMyWg() {

        Button navAccessWg_To_MyWg = (Button) findViewById(R.id.btn_searchForWg);
        navAccessWg_To_MyWg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateEmail()) {
                    startActivity(new Intent(AccessWg.this, MyWg.class));
                    firstLayout.setAccess(1);
                } else {
                    return;
                }
            }
        });

    }}

