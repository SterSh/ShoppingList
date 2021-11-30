package com.example.fromstoretocore;

import android.content.Context;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class GroceryListItems implements Serializable {
    private int id;
    private int idShoppingList;
    private String description;
    private float quantity;
    private float totalPrice;
    private float itemPrice;

    private boolean checked;

    public float getQuantity() { return quantity; }

    public void setQuantity(float quantity) { this.quantity = quantity; }

    public float getTotalPrice() { return totalPrice; }

    public void setTotalPrice(float totalPrice) { this.totalPrice = totalPrice; }

    public float getItemPrice() { return itemPrice; }

    public void setItemPrice(float itemPrice) { this.itemPrice = itemPrice; }

    public boolean isChecked() { return checked; }

    public void setChecked(boolean checked) { this.checked = checked; }

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
        // Add Set for totalPrice, itemPrice, Checked, and quantity //

    }

    @NonNull
    @Override
    public String toString() {
        return "" + description;
    }
}
