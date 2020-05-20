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

    private List<ListShoppingListsQuery.Item> mData = new ArrayList<>();;
    private LayoutInflater mInflater;


    // data is passed into the constructor
    MyAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.view_holder_shop_list, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData(mData.get(position));
        //holder.deleteBtn.setOnClickListener(view -> removeItem(position));
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
    /**private void removeItem(int position) {
        mShopItems.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mShopItems.size());
    }**/

    // stores and recycles views as they are scrolled off screen
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_name;
        TextView txt_quantity;

        ViewHolder(View itemView) {
            super(itemView);
            txt_name = itemView.findViewById(R.id.main_line_title);
            txt_quantity = itemView.findViewById(R.id.main_line_quantity);
        }

        void bindData(ListShoppingListsQuery.Item item) {
            txt_name.setText(item.itemName());
            txt_quantity.setText(item.value());
        }
    }
}