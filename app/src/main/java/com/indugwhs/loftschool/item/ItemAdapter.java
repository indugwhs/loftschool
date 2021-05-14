package com.indugwhs.loftschool.item;

import android.content.Context;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.indugwhs.loftschool.R;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private List<Item> itemList = new ArrayList<>();

    public ItemAdapterClick itemAdapterClick;

    public void setData(List<Item> Item) {
        itemList.clear();
        itemList.addAll(Item);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdItem = R.layout.item;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate((layoutIdItem), parent, false);
        return new ItemViewHolder(view, itemAdapterClick);
    }

    public void setItemAdapterClick(ItemAdapterClick itemAdapterClick) {
        this.itemAdapterClick = itemAdapterClick;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.bind(itemList.get(position));
    }

    @Override
    public int getItemCount() {

        return itemList.size();
    }

    public void updateItem(Item item) {
        int itemPosition = itemList.indexOf(item);
        itemList.set(itemPosition, item);
        notifyItemChanged(itemPosition);
    }

    public List<Item> getMoneyItemList() {
        return itemList;
    }

    public void deleteSelectedItems() {
        List<Item> selectedItems = new ArrayList<>();
        for(Item item : itemList){
            if(item.isSelected()){
                selectedItems.add(item);
            }
        }
        itemList.removeAll(selectedItems);
        notifyDataSetChanged();
    }


    public void clearItems() {
        itemList.clear();
        notifyDataSetChanged();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView value;
        private  ItemAdapterClick itemAdapterClick;

        public ItemViewHolder(@NonNull View itemView, ItemAdapterClick itemAdapterClick) {

            super(itemView);
            this.itemAdapterClick = itemAdapterClick;
            title = itemView.findViewById(R.id.itemTitleView);
            value = itemView.findViewById(R.id.itemValueView);

        }

        public void bind(Item item) {

            title.setText(item.getTitle());
            value.setText(new SpannableString(item.getValue()  + " \u20BD"));
            if (item.getPosition() == 0) {
                value.setTextColor(ContextCompat.getColor(title.getContext(), R.color.colorPrimary));
            } else if (item.getPosition() == 1) {
                value.setTextColor(ContextCompat.getColor(title.getContext(), R.color.green));
            }


            itemView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), item.isSelected() ? R.color.cellSelectionColor : android.R.color.white));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemAdapterClick != null){
                        itemAdapterClick.onCellClick(item);
                }
            }});

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (itemAdapterClick != null) {
                        itemAdapterClick.onLongCellClick(item);
                    }
                    return true;
                }
            });
        }
    }
}
