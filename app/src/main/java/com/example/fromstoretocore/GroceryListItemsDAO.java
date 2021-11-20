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

    public static GroceryListItems select(Context context, int idItemShoppingList) throws Exception {
        try {
            SQLiteDatabase db = new DatabaseDAO(context).getReadableDatabase();

            Cursor cursor = db.query(TABLE_NAME, new String[] { FIELD_ID, FIELD_IDGROCERYLIST, FIELD_PRODUCT }, FIELD_ID + " = ?", new String[] { String.valueOf(idItemShoppingList) }, null, null, null);

            if (cursor.moveToNext()) {

                return returnClassInstace(context, cursor);
            }

            cursor.close();

        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }
        return null;
    }

    public static GroceryListItems findLastInserted(Context context, String description) throws Exception {
        try {
            SQLiteDatabase db = new DatabaseDAO(context).getReadableDatabase();

            Cursor cursor = db.query(TABLE_NAME, new String[] { FIELD_ID, FIELD_IDGROCERYLIST, FIELD_PRODUCT }, FIELD_PRODUCT + " = ?", new String[] { String.valueOf(description) }, null, null, FIELD_ID + " DESC");
            cursor.moveToFirst();
            if (cursor.moveToFirst()) {

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

    @SuppressLint("Range")
    public static ArrayAdapter<String> selectAutoComplete(Context context, String[] descriptionFilters) throws Exception {
        ArrayList<String> list = new ArrayList<String>();

        try {
            SQLiteDatabase db = new DatabaseDAO(context).getReadableDatabase();

            String selection = null;

            if (descriptionFilters.length > 0) {
                selection = FIELD_PRODUCT + " not in (" + numOfParameters(descriptionFilters.length) + ")";
            }

            Cursor cursor = db.query(TABLE_NAME, new String[] { FIELD_PRODUCT }, selection, descriptionFilters, FIELD_PRODUCT, null, FIELD_PRODUCT + " asc");
            while (cursor.moveToNext()) {
                list.add(cursor.getString(cursor.getColumnIndex(FIELD_PRODUCT)));
            }
            cursor.close();
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }

        return new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, list);
    }

    public static void update(Context context, GroceryListItems groceryListItems) throws Exception {
        SQLiteDatabase db = new DatabaseDAO(context).getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(FIELD_PRODUCT, groceryListItems.getDescription());
        try {
            db.update(TABLE_NAME, cv, FIELD_ID + " = ?", new String[] { String.valueOf(groceryListItems.getId()) });
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }
    }

    public static Cursor selectAll(Context context, String filter, int idShoppingList) throws Exception {
        try {
            String orderBy = "";
            /*
            if (!UserPreferences.getItemListCheckedOrdenation(context).isEmpty()) {
                orderBy = FIELD_CHECKED + " " + UserPreferences.getItemListCheckedOrdenation(context) + ",";
            }

            if (!UserPreferences.getItemListAlphabeticalOrdenation(context).isEmpty()) {
                orderBy += FIELD_DESCRIPTION + " " + UserPreferences.getItemListAlphabeticalOrdenation(context) + ",";
            }
            */

            orderBy +=  FIELD_ID + " DESC ";

            String where = "";
            if (filter != null){
                where = " AND " + FIELD_PRODUCT + " LIKE '%" + filter + "%'";
            }

            SQLiteDatabase db = new DatabaseDAO(context).getReadableDatabase();
            return db.query(TABLE_NAME, new String[] { FIELD_ID, FIELD_IDGROCERYLIST, FIELD_PRODUCT }, FIELD_IDGROCERYLIST + " = ?" + where, new String[] { String.valueOf(idShoppingList) }, null, null, orderBy);
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }
    }
    public static String toString(Context context, int idShoppingList) throws Exception {
        String result = "";
        try {
            Cursor c = selectAll(context, null, idShoppingList);
            while (c.moveToNext()) {
                GroceryListItems item = returnClassInstace(context, c);
            }
            c.close();

            /*
            if (!result.isEmpty()) {
                result = result + "\n\n" + context.getString(R.string.total) + " : " + CustomFloatFormat.getMonetaryMaskedValue(context, totalValue);
            }

             */

        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }

        return result;
    }

    private static String numOfParameters(int num) {
        String result = "";
        for (int i = 0; i < num; i++) {
            result = result + "?,";
        }
        return result.substring(0, result.length() - 1);

    }
}
