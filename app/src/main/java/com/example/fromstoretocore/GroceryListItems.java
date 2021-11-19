package com.example.fromstoretocore;

import android.content.Context;

public class GroceryListItems {
    private int id;
    private int idShoppingList;
    private String description;
    private float unitValue;
    private float quantity;

    private boolean checked;

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

    public float getUnitValue() {
        return unitValue;
    }

    public void setUnitValue(float unitValue) {
        this.unitValue = unitValue;
    }

    public float getQuantity() {
        return quantity;
    }


    public float getTotal() {
        return quantity * unitValue;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public GroceryListItems(Context context) {
        this(0, 0, context.getString(R.string.no_description), 0, 0, false);
    }

    public GroceryListItems(int id, int idShoppingList, String description, float unitValue, float quantity, boolean checked) {
        setId(id);
        setIdGroceryList(idShoppingList);
        setUnitValue(unitValue);
        setQuantity(quantity);
        setChecked(checked);
    }
}
