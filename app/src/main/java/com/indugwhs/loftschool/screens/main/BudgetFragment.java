 package com.indugwhs.loftschool.screens.main;

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

import com.indugwhs.loftschool.R;
import com.indugwhs.loftschool.item.Item;
import com.indugwhs.loftschool.item.ItemAdapter;
import com.indugwhs.loftschool.remote.MoneyRemoteItem;
import com.indugwhs.loftschool.screens.LoftApp;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BudgetFragment extends Fragment {

    private static final String ARG_CURRENT_POSITION = "position";
    public static final int REQUEST_CODE = 0;
    public static final String ARG_BUDGET = "arg_budget";
    public static final String ARG_VALUE = "arg_value";

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    //private FloatingActionButton flButton;
    private ItemAdapter itemAdapter = new ItemAdapter();
    private List<Item> itemList = new ArrayList<>();
    private int currentPosition;
    //private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MainViewModel mainViewModel;

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
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);

        swipeRefreshLayout.setOnRefreshListener(() -> loadItems());

        /*flButton = view.findViewById(R.id.call_add_item);

        flButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddMoneyActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        });*/
        recyclerView.setAdapter(itemAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(layoutManager);
        //generateData();
        configureViews();
        configureViewModel();
        return view;
    }

    private void configureViewModel() {
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.itemList.observe(this, items -> {
            itemAdapter.setData(items);
        });
        mainViewModel.messageString.observe(this, message -> {
            if (!message.equals("")) {
                showToast(message);
            }
        });

        mainViewModel.messageInt.observe(this, message -> {
            if(message > 0) {
                showToast((getString(message)));}
        });

        mainViewModel.isFinishRefresh.observe(this, isFinish -> {
            if(isFinish) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void showToast(String message){
        Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onResume(){
        super.onResume();
        loadItems();

    }

    private void loadItems() {
        mainViewModel.loadIncomes(((LoftApp) getActivity().getApplication()).moneyApi ,
                currentPosition, getActivity().getSharedPreferences(getString(R.string.app_name), 0));
    }
  /* @Override
    public void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }*/

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

    private void configureViews(){

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
    }
*/
}