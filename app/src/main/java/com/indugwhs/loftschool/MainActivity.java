 package com.indugwhs.loftschool;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.indugwhs.loftschool.item.item;
import com.indugwhs.loftschool.item.itemAdapter;
import com.indugwhs.loftschool.item.itemClick;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity{

   private RecyclerView itemsView;
   private itemAdapter ItemAdapter = new itemAdapter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureRecyclerView();
        generateItem();

    }

    private void generateItem(){
        List<item> Items = new ArrayList<>();
        Items.add(new item("PS5", "30000P"));
        Items.add(new item("Salary", "300000P"));

        ItemAdapter.setData(Items);
    }

    private void configureRecyclerView(){

        itemsView = findViewById(R.id.itemsView);
        itemsView.setAdapter(ItemAdapter);


                ItemAdapter.ItemClick = new itemClick() {
            @Override
            public void onItemClick(item Item) {
                Toast.makeText(getApplicationContext(), "Cell clicked" + Item.getValue(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onTitleClick() {
                Toast.makeText(getApplicationContext(), "Title clicked", Toast.LENGTH_LONG).show();
            }
        };

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false);

        DividerItemDecoration dividerItemDecoration= new DividerItemDecoration (this, DividerItemDecoration.VERTICAL);
        itemsView.addItemDecoration(dividerItemDecoration);

        itemsView.setLayoutManager(layoutManager);

    }



}