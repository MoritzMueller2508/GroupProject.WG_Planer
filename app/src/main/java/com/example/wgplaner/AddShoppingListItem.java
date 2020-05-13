package com.example.wgplaner;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.amazonaws.amplify.generated.graphql.CreateShoppingListMutation;
import com.apollographql.apollo.GraphQLCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import javax.annotation.Nonnull;

import type.CreateShoppingListInput;

public class AddShoppingListItem extends AppCompatActivity {

        private static final String TAG = AddShoppingListItem.class.getSimpleName();

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_shopping_item);

            Button btnAddItem = findViewById(R.id.btn_save);
            btnAddItem.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    save();
                }
            });
        }

        private void save() {
            final String name = ((EditText) findViewById(R.id.editTxt_name)).getText().toString();
            final String value = ((EditText) findViewById(R.id.editTxt_value)).getText().toString();

            CreateShoppingListInput input = CreateShoppingListInput.builder()
                    .itemName(name)
                    .value(value)
                    .build();

            CreateShoppingListMutation addShoppingListMutation = CreateShoppingListMutation.builder()
                    .input(input)
                    .build();
            ClientFactory.appSyncClient().mutate(addShoppingListMutation).enqueue(mutateCallback);
        }

        // Mutation callback code
        private GraphQLCall.Callback<CreateShoppingListMutation.Data> mutateCallback = new GraphQLCall.Callback<CreateShoppingListMutation.Data>() {
            @Override
            public void onResponse(@Nonnull final Response<CreateShoppingListMutation.Data> response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AddShoppingListItem.this, "Added item", Toast.LENGTH_SHORT).show();
                        AddShoppingListItem.this.finish();
                    }
                });
            }

            @Override
            public void onFailure(@Nonnull final ApolloException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e(TAG, "Failed to perform AddPetMutation", e);
                        Toast.makeText(AddShoppingListItem.this, "Failed to add item", Toast.LENGTH_SHORT).show();
                        AddShoppingListItem.this.finish();
                    }
                });
            }
        };
    }

