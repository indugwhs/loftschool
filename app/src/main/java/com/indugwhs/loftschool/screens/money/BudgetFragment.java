 package com.indugwhs.loftschool.screens.money;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.indugwhs.loftschool.R;
import com.indugwhs.loftschool.item.Item;
import com.indugwhs.loftschool.item.ItemAdapter;
import com.indugwhs.loftschool.item.ItemAdapterClick;
import com.indugwhs.loftschool.screens.AddMoneyActivity;
import com.indugwhs.loftschool.screens.LoftApp;
import com.indugwhs.loftschool.screens.dashboard.EditModeListener;
import com.indugwhs.loftschool.screens.main.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.indugwhs.loftschool.screens.AddMoneyActivity.ARG_TYPE_BUDGET;

 public class BudgetFragment extends Fragment implements MoneyEditListener{

    private static final String ARG_CURRENT_POSITION = "position";
    public static final int REQUEST_CODE = 0;
    public static final String ARG_BUDGET = "arg_budget";
    public static final String ARG_VALUE = "arg_value";

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FloatingActionButton flButton;
    private ItemAdapter itemAdapter = new ItemAdapter();
    private List<Item> itemList = new ArrayList<>();
    private int currentPosition;
     private BudgetViewModel budgetViewModel;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            currentPosition = getArguments().getInt(ARG_CURRENT_POSITION);
        }

        itemAdapter.setItemAdapterClick(new ItemAdapterClick() {
            @Override
            public void onCellClick(Item item) {
                if (budgetViewModel.isEditMode.getValue()) {
                    item.setSelected(!item.isSelected());
//                    itemAdapter.updateItem(item);
                    budgetViewModel.selectItem(item);
                    checkSelectedCount();}
            }

            @Override
            public void onLongCellClick(Item item) {
                if(!budgetViewModel.isEditMode.getValue())
                    item.setSelected(true);
//                    itemAdapter.updateItem(item);
                budgetViewModel.selectItem(item);
                budgetViewModel.isEditMode.postValue(true);
                checkSelectedCount();
            }
        });
        
    }

     private void onLoadData() {
         budgetViewModel.loadIncomes(((LoftApp) getActivity().getApplication()).moneyApi, currentPosition, getActivity().getSharedPreferences(getString(R.string.app_name), 0));
         itemAdapter.clearItems();
     }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_budget, null);
        return view;
    }



    private void showToast(String message){
        Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        flButton = view.findViewById(R.id.fab);


        configureView(view);
        configureViewModel();
    }

     @Override
     public void onClearSelectedClick() {

         budgetViewModel.isEditMode.postValue(false);
         budgetViewModel.selectedCounter.postValue(-1);
//         itemAdapter.deleteSelectedItems();

         budgetViewModel.removeItems(((LoftApp) getActivity().getApplication()).moneyApi,
                 getActivity().getSharedPreferences(getString(R.string.app_name), 0), budgetViewModel.selectedItems);
/*
         onLoadData();*/
     }


     @Override
     public void onResume() {
         super.onResume();
         onLoadData();
     }


     @Override
     public void onClearEdit(){
         budgetViewModel.isEditMode.postValue(false);
         budgetViewModel.selectedCounter.postValue(-1);

         for (Item moneyItem : itemAdapter.getMoneyItemList()) {
             if (moneyItem.isSelected()) {
                 moneyItem.setSelected(false);
                 itemAdapter.updateItem(moneyItem);
             }
         }
     }

    public static BudgetFragment newInstance(int position) {
        BudgetFragment fragment = new BudgetFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_CURRENT_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

     private void configureView(View view) {

         recyclerView = view.findViewById(R.id.budget_item_List);
         recyclerView.setAdapter(itemAdapter);
         LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
         recyclerView.setLayoutManager(layoutManager);

         DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), layoutManager.getOrientation());
         recyclerView.addItemDecoration(dividerItemDecoration);


         swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
         swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

             @Override
             public void onRefresh() {
                 budgetViewModel.loadIncomes(((LoftApp) getActivity().getApplication()).moneyApi, currentPosition, getActivity().getSharedPreferences(getString(R.string.app_name), 0));
                 swipeRefreshLayout.setRefreshing(false);
             }
         });

         flButton = view.findViewById(R.id.fab);
         flButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(getContext(), AddMoneyActivity.class);
                 intent.putExtra(ARG_TYPE_BUDGET, currentPosition);
                 startActivity(intent);
             }
         });
     }

     private void configureViewModel() {
         budgetViewModel = new ViewModelProvider(this).get(BudgetViewModel.class);
         budgetViewModel.itemList.observe(this, itemAdapter::setData);

         budgetViewModel.isEditMode.observe(this, isEditMode -> {

             flButton.setVisibility(isEditMode ? View.GONE : View.VISIBLE);

             Fragment parentFragment = getParentFragment();
             if (parentFragment instanceof EditModeListener) {
                 ((EditModeListener) parentFragment).onEditModeChanged(isEditMode);
             }
         });

         budgetViewModel.selectedCounter.observe(this, newCount -> {
             Fragment parentFragment = getParentFragment();
             if (parentFragment instanceof EditModeListener) {
                 ((EditModeListener) parentFragment).onCounterChanged(newCount);
             }
         });


         budgetViewModel.messageString.observe(this, message -> {
             if (!message.equals("")) {
                 showToast(message);
             }
         });

         budgetViewModel.messageInt.observe(this, message -> {
             if (message > 0) {
                 showToast((getString(message)));
             }
         });

         budgetViewModel.isNeedLoadData.observe(this, isNeed -> {
             if (isNeed) {
                 onLoadData();
             }
         });

     }

     private void checkSelectedCount() {
         int selectedItemsCount = 0;
         for (Item moneyItem : itemAdapter.getMoneyItemList()) {
             if (moneyItem.isSelected()) {
                 selectedItemsCount++;
             }
             budgetViewModel.selectedCounter.postValue(selectedItemsCount);
         }
     }

 }




   /* private void generateData() {
        String typeRequest;
        if (currentPosition == 0) {
            typeRequest = "expense";
        } else {
            typeRequest = "income";
        }

       Disposable disposable = ((LoftApp) getActivity().getApplication()).moneyApi.getMoneyItems(typeRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(moneyResponse -> {
                    if (moneyResponse.getStatus().equals("success")){
                        List<Item> item = new ArrayList<>();
                        for(MoneyRemoteItem moneyRemoteItem : moneyResponse.getMoneyItemsList()){
                            item.add(new Item(moneyRemoteItem.getName(), String.valueOf(moneyRemoteItem.getPrice()), currentPosition));
                        }
                        itemAdapter.setData(item);
                    } else {
                        Toast.makeText(getActivity(), getString(R.string.connection_lost), Toast.LENGTH_LONG).show();
                    }
                }, throwable -> {
                    Toast.makeText(getActivity(), throwable.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                });

        compositeDisposable.add(disposable);
    }*/
