package com.indugwhs.loftschool;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.indugwhs.loftschool.item.Item;
import com.indugwhs.loftschool.item.ItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class BudgetFragment extends Fragment {

    private static final String ARG_CURRENT_POSITION = "position";
    public static final int REQUEST_CODE = 0;
    public static final String ARG_BUDGET = "arg_budget";
    public static final String ARG_VALUE = "arg_value";

    private RecyclerView recyclerView;
    private FloatingActionButton flButton;
    private ItemAdapter itemAdapter = new ItemAdapter();
    private List<Item> itemList = new ArrayList<>();
    private int currentPosition;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            currentPosition = getArguments().getInt(ARG_CURRENT_POSITION);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_budget, null);

        recyclerView = view.findViewById(R.id.budget_item_List);
        flButton = view.findViewById(R.id.call_add_item);

        flButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddItemActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        });

        recyclerView.setAdapter(itemAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            String title = data.getStringExtra(ARG_BUDGET);
            String value = data.getStringExtra(ARG_VALUE);

            itemList.add(new Item(title, value, currentPosition));
            itemAdapter.setData(itemList);
        }
    }

    public static BudgetFragment newInstance(int position) {
        BudgetFragment fragment = new BudgetFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_CURRENT_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }
}