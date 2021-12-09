package com.example.fromstoretocore;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.io.IOException;

public class GroceryListDAO {
    public static final String TABLE_NAME = "GROCERYLIST";
    public static final String FIELD_ID = "_id";
    public static final String FIELD_NAME = "NAME";
    private static Context context;

    public GroceryListDAO(Context context) {
        this.context = context;
    }

    public static GroceryList select(Context context, int idShoppingList) throws IOException {
        SQLiteDatabase db = new DatabaseDAO(context).getReadableDatabase();
        try {
            Cursor cursor = db.query(TABLE_NAME, new String[] { FIELD_ID, FIELD_NAME }, FIELD_ID + " = ?", new String[] { String.valueOf(idShoppingList) }, null, null, null);
            if (cursor.moveToFirst()) {
                return returnClassInstance(context, cursor);
            }

            cursor.close();

        } catch (Exception e) {
            throw new IOException(e.getMessage(), e);
        }

        return null;
    }

    public static boolean deleteList(GroceryList list) {
        SQLiteDatabase db = new DatabaseDAO(context).getWritableDatabase();
        String queryString = "DELETE FROM " + TABLE_NAME + " WHERE " + FIELD_ID + " = " + list.getId();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }
    }

    public static Cursor selectAll(Context context) throws Exception {
        try {
            SQLiteDatabase db = new DatabaseDAO(context).getReadableDatabase();
            return db.query(TABLE_NAME, new String[]{FIELD_ID, FIELD_NAME}, null, null, null, null, FIELD_ID + " desc");
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }
    }

    @SuppressLint("Range")
    public static GroceryList returnClassInstance(Context context, Cursor cursor) {
        return new GroceryList(context, cursor.getInt(cursor.getColumnIndex(FIELD_ID)), cursor.getString(cursor.getColumnIndex(FIELD_NAME)));
    }



    public static GroceryList insert(Context context, GroceryList groceryList) throws Exception {
        SQLiteDatabase db = new DatabaseDAO(context).getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(FIELD_NAME, groceryList.getName());
        try {
            db.insertOrThrow(TABLE_NAME, null, cv);
            return selectLast(context, db);
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }

    }

    private static GroceryList selectLast(Context context, SQLiteDatabase db) throws Exception {
        try {
            Cursor cursor = db.query(TABLE_NAME, new String[] { FIELD_ID, FIELD_NAME }, null, null, null, null, FIELD_ID + " desc");
            if (cursor.moveToFirst()) {
                return returnClassInstance(context, cursor);
            }

            cursor.close();

        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }

        return null;
    }
}
