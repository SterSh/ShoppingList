package com.example.fromstoretocore;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class GroceryListItemsDAO {

    public static final String TABLE_NAME = "GROCERYLISTITEMS";
    public static final String FIELD_ID = "_id";
    public static final String FIELD_IDGROCERYLIST = "IDGROCERYLIST";
    public static final String FIELD_PRODUCT = "DESCRIPTION";
    public static final String FIELD_QUANTITY = "QUANTITY";
    public static final String FIELD_ITEMPRICE = "ITEMPRICE";
    public static final String FIELD_TOTALPRICE = "TOTALPRICE";
    public static final String FIELD_CHECKED = "CHECKED";
    public static final String DATABASE_NAME = "GroceryList";

    private static Context context;


    public GroceryListItemsDAO(Context context) {
        this.context = context;
    }

    // This will be the insert function to insert into a previous list //
    public static GroceryListItems insert(Context context, SQLiteDatabase db, GroceryListItems groceryListItems) throws Exception {
        ContentValues cv = new ContentValues();
        cv.put(FIELD_IDGROCERYLIST, groceryListItems.getIdGroceryList());
        cv.put(FIELD_PRODUCT, groceryListItems.getDescription());
        // Add cv.put for the rest of the fields using getters for each variable
        try {
            db.insert(TABLE_NAME, null, cv);
            return selectLast(context, db);
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }
    }

    public static GroceryListItems insert(Context context, GroceryListItems groceryListItems) throws Exception {
        SQLiteDatabase db = new DatabaseDAO(context).getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(FIELD_IDGROCERYLIST, groceryListItems.getIdGroceryList());
        cv.put(FIELD_PRODUCT, groceryListItems.getDescription());
        // Add cv.put for the rest of the fields using getters for each variable
        try {
            db.insert(TABLE_NAME, null, cv);
            return selectLast(context, db);
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }
    }

    private static GroceryListItems selectLast(Context context, SQLiteDatabase db) throws Exception {
        try {
            // Add the rest of the fields inside the { }
            Cursor cursor = db.query(TABLE_NAME, new String[] { FIELD_ID, FIELD_IDGROCERYLIST, FIELD_PRODUCT }, null, null, null,null, FIELD_ID + " desc");
            if (cursor.moveToNext()) {
                return returnClassInstance(context, cursor);
            }
            cursor.close();
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }

        return null;
    }

    public static ArrayList<GroceryListItems> getListItems(int listID) {
        ArrayList<GroceryListItems> groceryListItems = new ArrayList<>();
        SQLiteDatabase db = new DatabaseDAO(context).getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME + " WHERE " + FIELD_IDGROCERYLIST + " = " + listID, null);

        while (res.moveToNext()) {
            int id = res.getInt(0);
            int idShoppingList = res.getInt(1);
            String name = res.getString(2);

            GroceryListItems listItems = new GroceryListItems(id, idShoppingList, name);

            groceryListItems.add(listItems);
        }
        return groceryListItems;
    }

    public static boolean deleteItem(GroceryListItems groceryListItem) {
        SQLiteDatabase db = new DatabaseDAO(context).getWritableDatabase();
        String queryString = "DELETE FROM " + TABLE_NAME + " WHERE " + FIELD_ID + " = " + groceryListItem.getId();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }

    }

    @SuppressLint("Range")
    public static GroceryListItems returnClassInstance(Context context, Cursor cursor) {
        return new GroceryListItems(cursor.getInt(cursor.getColumnIndex(FIELD_ID)), cursor.getInt(cursor.getColumnIndex(FIELD_IDGROCERYLIST)), cursor.getString(cursor.getColumnIndex(FIELD_PRODUCT)));
    }
}
