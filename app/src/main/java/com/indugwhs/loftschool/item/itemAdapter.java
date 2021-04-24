package com.indugwhs.loftschool.item;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.indugwhs.loftschool.R;

import java.util.ArrayList;
import java.util.List;

public class itemAdapter extends RecyclerView.Adapter<itemAdapter.ItemViewHolder> {

    private List<item> itemList = new ArrayList<>();
    public itemClick ItemClick;

    public void setData(List<item> items){
        itemList.clear();
        itemList.addAll(items);

        notifyDataSetChanged();;
    }

    public void setItemClick(itemClick ItemClick) {
        this.ItemClick = ItemClick;

    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new ItemViewHolder(layoutInflater.inflate(R.layout.item, parent, false), ItemClick);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.bind(itemList.get(position));
    }

    @Override
    public int getItemCount() {

        return itemList.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView titleTextView;
        private TextView valueTextView;
        private itemClick ItemClick;

        public ItemViewHolder(@NonNull View itemView, itemClick ItemClick) {

            super(itemView);

            this.ItemClick = ItemClick;

            titleTextView = itemView.findViewById(R.id.itemTitleView);
            valueTextView = itemView.findViewById(R.id.itemValueView);

        }

        public void bind(item item) {
            titleTextView.setText(item.getTitle());
            valueTextView.setText(item.getValue());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ItemClick!=null) {
                        ItemClick.onItemClick(item);
                    }
                }
            });

            titleTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ItemClick != null){
                        ItemClick.onTitleClick();
                    }
                }
            });
        }
    }
}