package com.indugwhs.loftschool.remote;

import com.google.gson.annotations.SerializedName;

public class MoneyRemoteItem {
    @SerializedName("id")
    private String itemId;

    @SerializedName("name")
    private String name;

    @SerializedName("price")
    private double price;

    @SerializedName("type")
    private String type;

    @SerializedName("data")
    private String data;


    public String getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    public String getData() {
        return data;
    }


}
