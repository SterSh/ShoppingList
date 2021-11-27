package com.example.fromstoretocore;

import android.content.Context;

import androidx.annotation.NonNull;

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
        this.name = (name.isEmpty() ? null : name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public GroceryList(Context context) {

    }

    public GroceryList(Context context, int id, String name) {
        setId(id);
        setName(context, name);
    }

    public GroceryList( int id, String name) {
        setId(id);
        setName(name);
    }

    //This is what the Array adapter is using to
    //display the name of the item list object
    @NonNull
    @Override
    public String toString() {
        return "" + getName();
    }
}
