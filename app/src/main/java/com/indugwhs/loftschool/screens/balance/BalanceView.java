package com.indugwhs.loftschool.screens.balance;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class BalanceView extends View {

    private float expenses;
    private float incomes;

    public BalanceView(Context context) {
        super(context);
    }

    public BalanceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BalanceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BalanceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float total = expenses + incomes;


    }
}