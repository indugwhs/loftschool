package com.indugwhs.loftschool.item;

public class Item {
    private String title;
    private String value;
    private int position;

    public Item(String title, String value, int position) {
        this.title = title;
        this.value = value;
        this.position = position;
    }

    public String getTitle() {
        return title;
    }

    public String getValue() {
        return value;
    }

    public int getPosition() {
        return position;
    }
}