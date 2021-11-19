package com.example.fromstoretocore;

import android.content.Context;
import java.util.Date;

public class GroceryList {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(Context context, String name) {
        this.name = (name.isEmpty() ? context.getString(R.string.untitled) : name);
    }

    public GroceryList(Context context) {
        this(context, 0, context.getString(R.string.untitled));
    }

    public GroceryList(Context context, int id, String name) {
        setId(id);
        setName(context, name);
    }
}
