package com.indugwhs.loftschool.screens;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.indugwhs.loftschool.R;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AddMoneyActivity extends AppCompatActivity {

    public static final String ARG_TYPE_BUDGET = "type_budget";
    private EditText moneyNameView;
    private EditText moneyPriceView;
    private Button moneyAddView;
    private String mPrice;
    private String mName;
    private int currentFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money);

        Intent intent = getIntent();
        currentFragment = intent.getIntExtra(ARG_TYPE_BUDGET, 0);

        moneyNameView = findViewById(R.id.name_edittext);
        moneyNameView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                mName = s.toString();
                checkEditTextHasText();
            }
        });
        moneyAddView = findViewById(R.id.add_button);
        moneyPriceView = findViewById(R.id.price_edittext);

        moneyPriceView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mPrice = s.toString();
                checkEditTextHasText();
            }
        });
        configureButton();
    }

    private void configureButton() {

        moneyAddView.setOnClickListener(v -> {
            if (moneyNameView.getText().equals("") || moneyPriceView.getText().equals("")) {
                Toast.makeText(getApplicationContext(), getString(R.string.fill_fields), Toast.LENGTH_LONG).show();
                return;
            }

            String typeBudget;
            if (currentFragment == 0) {
                typeBudget = "expense";
            } else {
                typeBudget = "income";
            }

            Disposable disposable = ((LoftApp) getApplication()).moneyApi.postMoney(
                    Integer.parseInt(moneyPriceView.getText().toString()),
                    moneyNameView.getText().toString(), typeBudget,
                    getSharedPreferences(getString(R.string.app_name), 0).getString(LoftApp.AUTH_KEY, "")
            )
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> {
                        Toast.makeText(getApplicationContext(), getString(R.string.success_added), Toast.LENGTH_LONG).show();
                        finish();
                    }, throwable -> {
                        Toast.makeText(getApplicationContext(), throwable.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    });
        });
    }

    public void checkEditTextHasText() {
        moneyAddView.setEnabled(!TextUtils.isEmpty(mName) && !TextUtils.isEmpty(mPrice));
    }
}
