package com.indugwhs.loftschool.screens.balance;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.indugwhs.loftschool.R;
import com.indugwhs.loftschool.screens.LoftApp;

import java.util.Random;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class BalanceFragment extends Fragment {

    private TextView balance;
    private TextView expense;
    private TextView income;
    private BalanceView balanceView;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_balance, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        balance = view.findViewById(R.id.txtBalanceFinanceValue);
        expense = view.findViewById(R.id.txtBalanceCostsValue);
        income = view.findViewById(R.id.txtBalanceIncomeValue);
        BalanceView balanceView = view.findViewById(R.id.balanceView);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_balance);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            loadBalance();
            swipeRefreshLayout.setRefreshing(false);
        });
        loadBalance();
    }


    public void loadBalance() {
        String authToken = getActivity().getSharedPreferences(getString(R.string.app_name), 0).getString(LoftApp.AUTH_KEY, "");
        Disposable disposable = ((LoftApp) getActivity().getApplication()).getMoneyApi().getBalance(authToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(balanceResponse -> {
                    String balanceString = String.valueOf(balanceResponse.getTotalIncome() - balanceResponse.getTotalExpenses());
                    balance.setText(balanceString + "₽");
                    income.setText(String.valueOf(balanceResponse.getTotalIncome()) + "₽");
                    expense.setText(String.valueOf(balanceResponse.getTotalExpenses()) + "₽");
                }, throwable -> Toast.makeText(getActivity().getApplicationContext(), throwable.getLocalizedMessage(), Toast.LENGTH_LONG).show());
        compositeDisposable.add(disposable);
    }
}