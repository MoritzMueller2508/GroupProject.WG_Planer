package com.example.wgplaner;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShoppingList extends AppCompatActivity {


    private TextInputLayout textInput;
    private EditText deleteInput;
    private ListView list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());

        textInput = findViewById(R.id.ti_addItem);
        deleteInput = findViewById(R.id.editText);

        list =findViewById(R.id.list_shoppingList);


        /*
        mAWSAppSyncClient = AWSAppSyncClient.builder()
                .context(getApplicationContext())
                .awsConfiguration(new AWSConfiguration(getApplicationContext()))
                .build();
        runMutation();
        AddShoppingListItem();*/


        // Initializing a new String Array
        String[] shoppinglist = new String[] {
                "Toilettenpapier",
                "Desinfektionsmittel"
        };

        // Create a List from String Array elements
        final List<String> shopping_list = new ArrayList<String>(Arrays.asList(shoppinglist));

        // Create an ArrayAdapter from List
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, shopping_list);

        // DataBind ListView with items from ArrayAdapter
        list.setAdapter(arrayAdapter);



        Button add = (Button) findViewById(R.id.btn_addShoppingListItem);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = textInput.getEditText().getText().toString();
                    shopping_list.add(input);
                  textInput.getEditText().getText().clear();
                arrayAdapter.notifyDataSetChanged();
                 }
        });

        Button delete = (Button) findViewById(R.id.btn_deleteItem);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputDelete = deleteInput.getText().toString();
                String dItem= inputDelete;

                Delete(shopping_list, dItem);
                arrayAdapter.notifyDataSetChanged();
            }
        });
    }

    private void Delete(List shopping_list, String dItem) {
        for(int i=0; i<20; i++) {
            if(shopping_list.get(i).equals(dItem)){
                shopping_list.remove(i);
                deleteInput.getText().clear();
            }
        }
        }


    /*public void runMutation() {
        CreateShoppingListInput createShoppingListInput = CreateShoppingListInput.builder().
                itemName("Milch").
                value(1).
                build();

        mAWSAppSyncClient.mutate(CreateShoppingListMutation.builder().input(createShoppingListInput).build())
                .enqueue(mutationCallback);
    }

    private GraphQLCall.Callback<CreateShoppingListMutation.Data> mutationCallback = new GraphQLCall.Callback<CreateShoppingListMutation.Data>() {
        @Override
        public void onResponse(@Nonnull Response<CreateShoppingListMutation.Data> response) {
            Log.i("Results", "Added item");
        }

        @Override
        public void onFailure(@Nonnull ApolloException e) {
            Log.e("Error", e.toString());
        }

    };

    public void runQuery() {
        mAWSAppSyncClient.query(ListShoppingListsQuery.builder().build())
                .responseFetcher(AppSyncResponseFetchers.CACHE_AND_NETWORK)
                .enqueue(shoppingListCallback);
    }

    private GraphQLCall.Callback<ListShoppingListsQuery.Data> shoppingListCallback = new GraphQLCall.Callback<ListShoppingListsQuery.Data>() {

        @Override
        public void onResponse(@Nonnull Response<ListShoppingListsQuery.Data> response) {
            Log.i("Results", response.data().listShoppingLists().items().toString());

        }

        @Override
        public void onFailure(@Nonnull ApolloException e) {
            Log.e("ERROR", e.toString());
        }
    };


    public void AddShoppingListItem(){

        Button addShoppingListItem = (Button) findViewById(R.id.btn_addShoppingListItem);
        addShoppingListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runQuery();
            }
        });
    }*/
}
