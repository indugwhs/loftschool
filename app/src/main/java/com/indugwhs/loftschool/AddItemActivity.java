package com.indugwhs.loftschool;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static com.indugwhs.loftschool.BudgetFragment.ARG_BUDGET;
import static com.indugwhs.loftschool.BudgetFragment.ARG_VALUE;
import static com.indugwhs.loftschool.BudgetFragment.REQUEST_CODE;

public class AddItemActivity extends AppCompatActivity{

    private EditText etTitle;
    private EditText etValue;
    private Button addButton;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        etTitle = findViewById(R.id.item_title_view);
        etValue = findViewById(R.id.item_value_view);

        addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra(ARG_BUDGET, etTitle.getText().toString());
            intent.putExtra(ARG_VALUE, etValue.getText().toString());
            setResult(REQUEST_CODE, intent);
            finish();
        });
    }
}
