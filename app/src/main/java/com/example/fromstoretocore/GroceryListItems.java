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

    //Function to check if the item is crossed off or not
    public boolean isChecked() { return checked; }

    //Crosses item off
    public void setChecked(boolean checked) { this.checked = checked; }

    //Getter and setter for Description variable
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    //Getter and setter for Id variable
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //Getter and setter for IdGroceryList variable
    public int getIdGroceryList() {
        return idShoppingList;
    }

    public void setIdGroceryList(int idShoppingList) {
        this.idShoppingList = idShoppingList;
    }

    //Creates GroceryList objects containing Id, IdGroceryList, and Description variables
    public GroceryListItems(int id, int idShoppingList, String description, boolean b) {
        setId(id);
        setIdGroceryList(idShoppingList);
        setDescription(description);
        // Add Set for totalPrice, itemPrice, Checked, and quantity //

    }

    //Converts item to string and adds to description
    @NonNull
    @Override
    public String toString() {
        return "" + description;
    }

    //UNUSED FUNCTIONS

    //Getter and setter for Quantity variable
    public float getQuantity() { return quantity; }

    public void setQuantity(float quantity) { this.quantity = quantity; }

    //Getter and setter for TotalPrice variable
    public float getTotalPrice() { return totalPrice; }

    public void setTotalPrice(float totalPrice) { this.totalPrice = totalPrice; }

    //Getter and setter for ItemPrice variable
    public float getItemPrice() { return itemPrice; }

    public void setItemPrice(float itemPrice) { this.itemPrice = itemPrice; }

    public GroceryListItems(Context context) {
        this(0, 0, context.getString(R.string.no_description), false);
    }
}
