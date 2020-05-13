package com.example.wgplaner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.wgplaner.R.id.list_news;

public class News extends AppCompatActivity {
    public ListView newsList;
    public EditText titleInputET, textInputET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news);

        final Toolbar toolbar = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());


        titleInputET= findViewById(R.id.ti_title);
        textInputET= findViewById(R.id.ti_text);
        newsList = findViewById(list_news);




        String title1="Lob vom Hausmeister";
        String title2="Brandanschlag in der Küche";




        String text1="Der Hausmeister hat uns für unsere super saubere WG gelobt!";
        String text2="In unserer Küche habe ich heute eine schwarze Wand vorgefunden. Wer muss sich verantworten? Bitte lass nichts anbrennen und melde dich bei uns! :)";




       //String[] newslist = new String[10];
        // Initializing a new String Array
        String[] newslist = new String[] {
                ""
        };


        // Create a List from String Array elements
        final List<String> news_list = new ArrayList<String>(Arrays.asList(newslist));

        // Create an ArrayAdapter from List
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, news_list);

        // DataBind ListView with items from ArrayAdapter
        newsList.setAdapter(arrayAdapter);

        news_list.add(title1 + "\n \n" + text1+ "\n");
        news_list.add(title2 + "\n \n" + text2+ "\n");




        Button add = (Button) findViewById(R.id.btn_createNews);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titleInput = titleInputET.getText().toString();
                String textInput = textInputET.getText().toString();
                news_list.add(titleInput + "\n \n" + textInput + "\n");
                titleInputET.getText().clear();
                textInputET.getText().clear();
            }
        });
    }
    }
