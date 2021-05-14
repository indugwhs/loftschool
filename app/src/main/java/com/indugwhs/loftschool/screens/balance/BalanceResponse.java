package com.indugwhs.loftschool.screens.balance;

import com.google.gson.annotations.SerializedName;

public class BalanceResponse {
    @SerializedName("status")
    private String status;

    @SerializedName("total_expenses")
    private float totalExpenses;

    @SerializedName("total_income")
    private float totalIncome;

    public String getStatus() {
        return status;
    }

    public float getTotalExpenses() {
        return totalExpenses;
    }

    public float getTotalIncome() {
        return totalIncome;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTotalExpenses(float totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public void setTotalIncome(float totalIncome) {
        this.totalIncome = totalIncome;
    }
}
