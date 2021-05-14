package com.indugwhs.loftschool.screens.main;

import android.content.SharedPreferences;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.indugwhs.loftschool.item.Item;
import com.indugwhs.loftschool.remote.MoneyApi;
import com.indugwhs.loftschool.remote.MoneyRemoteItem;
import com.indugwhs.loftschool.screens.LoftApp;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends ViewModel {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    public MutableLiveData<List<Item>> itemList = new MutableLiveData<>();
    public MutableLiveData<String> messageString = new MutableLiveData<>("");
    public MutableLiveData<Integer> messageInt = new MutableLiveData<>(-1);
    public MutableLiveData<Boolean> isFinishRefresh = new MutableLiveData<>();

   /* @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }


    public void loadIncomes(MoneyApi moneyApi, int currentPosition, SharedPreferences sharedPreferences){
        String authToken = sharedPreferences.getString(LoftApp.AUTH_KEY, "");

        String typeRequest;
        if (currentPosition == 0) {
            typeRequest = "expense";
        } else {
            typeRequest = "income";
        }

        isFinishRefresh.postValue(false);
        compositeDisposable.add(moneyApi.getMoneyItems(typeRequest, authToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(moneyRemoteItems -> {
                    isFinishRefresh.postValue(true);
                    List<Item> moneyItemModels = new ArrayList<>();

                    for (MoneyRemoteItem moneyRemoteItem : moneyRemoteItems) {
                        moneyItemModels.add(new Item(moneyRemoteItem.getName(), String.valueOf(moneyRemoteItem.getPrice()), currentPosition));
                    }
                    itemList.postValue(moneyItemModels);
                }, throwable -> {
                    isFinishRefresh.postValue(true);
                    messageString.postValue(throwable.getLocalizedMessage());
                }));
    }*/
}
