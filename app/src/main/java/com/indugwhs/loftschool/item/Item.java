package com.indugwhs.loftschool.item;

public class Item {
    private String id;
    private String title;
    private String value;
    private int position;
    private boolean isSelected;

    public Item(String id, String title, String value, int position ) {
        this.id = id;
        this.title = title;
        this.value = value;
        this.position = position;
        this.isSelected = false;
    }

    public String getId() { return id; }

    public String getTitle() {
        return title;
    }

    public String getValue() {
        return value;
    }

    public int getPosition() {
        return position;
    }

    public boolean isSelected() { return isSelected; }

    public void setSelected(boolean selected){ isSelected = selected; }

}