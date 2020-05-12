package com.example.wgplaner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.amazonaws.amplify.generated.graphql.ListShoppingListsQuery;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<ListShoppingListsQuery.Item> mData = new ArrayList<>();
    private LayoutInflater mInflater;


    MyAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txt_itemName;
        public TextView txt_value;

        public ViewHolder(View itemView) {
            super(itemView);
            txt_itemName = itemView.findViewById(R.id.txt_name);
            txt_value = itemView.findViewById(R.id.txt_value);
        }

        void bindData(ListShoppingListsQuery.Item item) {
            txt_itemName.setText(item.itemName());
            txt_value.setText(item.value());
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(ArrayList myDataset) {
        mData = myDataset;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData(mData.get(position));
    }
    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // resets the list with a new set of data
    public void setItems(List<ListShoppingListsQuery.Item> items) {
        mData = items;
    }

}