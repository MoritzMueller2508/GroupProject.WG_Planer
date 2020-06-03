package com.example.wgplaner;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amazonaws.amplify.generated.graphql.CreateShoppingListMutation;
import com.amazonaws.amplify.generated.graphql.ListShoppingListsQuery;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.amazonaws.mobileconnectors.appsync.fetcher.AppSyncResponseFetchers;
import com.amazonaws.mobileconnectors.appsync.sigv4.BasicCognitoUserPoolsAuthProvider;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.apollographql.apollo.GraphQLCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import javax.annotation.Nonnull;

import type.CreateShoppingListInput;
import type.ModelIDInput;
import type.ModelShoppingListFilterInput;

import static com.example.wgplaner.AccessWg.wgCode;

public class ShoppingList extends AppCompatActivity {

    private TextInputEditText textInput;
    private EditText deleteInput;
    private ListView list;
    RecyclerView mRecyclerView;
    private AWSAppSyncClient mAWSAppSyncClient;
    MyAdapter mAdapter;
    private ArrayList<ListShoppingListsQuery.Item> mItems;
    public static ModelIDInput wgCodeIdInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);
        AWSMobileClient.getInstance().initialize(getApplicationContext(), new Callback<UserStateDetails>() {

            @Override
            public void onResult(UserStateDetails userStateDetails) {
                Log.i("INIT", "onResult: " + userStateDetails.getUserState());
            }

            @Override
            public void onError(Exception e) {
                Log.e("INIT", "Initialization error.", e);
            }
        });
        CognitoUserPool mCognitoUserPool = new CognitoUserPool(getApplicationContext(), new AWSConfiguration(getApplicationContext()));
        mAWSAppSyncClient = AWSAppSyncClient.builder()
                .context(getApplicationContext())
                .awsConfiguration(new AWSConfiguration(getApplicationContext()))
                .cognitoUserPoolsAuthProvider(new BasicCognitoUserPoolsAuthProvider(mCognitoUserPool))
                .build();

        final Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());

        mRecyclerView = findViewById(R.id.shopping_list_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MyAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        Button btnAddItem = (Button) findViewById(R.id.btn_addShoppingListItem);
        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runMutation();

            }
        });

       /** ImageButton btnDeleteItem = (ImageButton) findViewById(R.id.btn_main_line_delete);
        btnDeleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });**/
    }

    @Override
    public void onResume() {
        super.onResume();
        // Query list data when we return to the screen
        runQuery();
    }

    public void runMutation(){
        final String name = ((TextInputEditText) findViewById(R.id.ti_addItem)).getText().toString();
        final String value = ((TextInputEditText) findViewById(R.id.ti_addValue)).getText().toString();
        CreateShoppingListInput createShoppingListInput = CreateShoppingListInput.builder()
                .wgID(wgCode)
                .itemName(name)
                .value(value)
                .build();

        mAWSAppSyncClient.mutate(CreateShoppingListMutation.builder().input(createShoppingListInput).build())
                .enqueue(mutationCallback);

        textInput.getText().clear();


    }

    private GraphQLCall.Callback<CreateShoppingListMutation.Data> mutationCallback = new GraphQLCall.Callback<CreateShoppingListMutation.Data>() {
        @Override
        public void onResponse(@Nonnull Response<CreateShoppingListMutation.Data> response) {
            Log.i("Results", "Added Item");
            runQuery();
        }

        @Override
        public void onFailure(@Nonnull ApolloException e) {
            Log.e("Error", e.toString());
        }
    };

    public void runQuery(){
        wgCodeIdInput = ModelIDInput.builder().contains(wgCode).build();
        mAWSAppSyncClient.query(ListShoppingListsQuery.builder()
                .filter(ModelShoppingListFilterInput.builder().wgID(wgCodeIdInput).build())
                .build())
                .responseFetcher(AppSyncResponseFetchers.CACHE_AND_NETWORK)
                .enqueue(queryCallback);
    }

    private GraphQLCall.Callback<ListShoppingListsQuery.Data> queryCallback = new GraphQLCall.Callback<ListShoppingListsQuery.Data>() {
        @Override
        public void onResponse(@Nonnull Response<ListShoppingListsQuery.Data> response) {
            mItems = new ArrayList<>(response.data().listShoppingLists().items());
            Log.i("Results", mItems.toString());

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mAdapter.setItems(mItems);
                    mAdapter.notifyDataSetChanged();
                }
            });
        }

        @Override
        public void onFailure(@Nonnull ApolloException e) {
            Log.e("ERROR", e.toString());
        }
    };


}

