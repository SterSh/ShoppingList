package com.example.fromstoretocore;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class GroceryListItemsDAO {

    public static final String TABLE_NAME = "GROCERYLISTITEMS";
    public static final String FIELD_ID = "_id";
    public static final String FIELD_IDGROCERYLIST = "IDGROCERYLIST";
    public static final String FIELD_PRODUCT = "DESCRIPTION";

    public static GroceryListItems insert(Context context, SQLiteDatabase db, GroceryListItems groceryListItems) throws Exception {
        ContentValues cv = new ContentValues();
        cv.put(FIELD_IDGROCERYLIST, groceryListItems.getIdGroceryList());
        cv.put(FIELD_PRODUCT, groceryListItems.getDescription());
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
        try {
            db.insert(TABLE_NAME, null, cv);
            return selectLast(context, db);
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }
    }

    private static GroceryListItems selectLast(Context context, SQLiteDatabase db) throws Exception {
        try {
            Cursor cursor = db.query(TABLE_NAME, new String[] { FIELD_ID, FIELD_IDGROCERYLIST, FIELD_PRODUCT }, null, null, null,null, FIELD_ID + " desc");
            if (cursor.moveToNext()) {
                return returnClassInstace(context, cursor);
            }
            cursor.close();
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }

        return null;
    }

    @SuppressLint("Range")
    public static GroceryListItems returnClassInstace(Context context, Cursor cursor) {
        return new GroceryListItems(cursor.getInt(cursor.getColumnIndex(FIELD_ID)), cursor.getInt(cursor.getColumnIndex(FIELD_IDGROCERYLIST)), cursor.getString(cursor.getColumnIndex(FIELD_PRODUCT)));
    }
}
