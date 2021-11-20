package com.example.fromstoretocore;

import android.content.Context;

public class GroceryListItems {
    private int id;
    private int idShoppingList;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdGroceryList() {
        return idShoppingList;
    }

    public void setIdGroceryList(int idShoppingList) {
        this.idShoppingList = idShoppingList;
    }

    public GroceryListItems(Context context) {
        this(0, 0, context.getString(R.string.no_description));
    }

    public GroceryListItems(int id, int idShoppingList, String description) {
        setId(id);
        setIdGroceryList(idShoppingList);
       setDescription(description);
    }
}
