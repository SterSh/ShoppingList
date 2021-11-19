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

    public static final String TABLE_NAME = "ITEMSHOPPINGLIST";
    public static final String FIELD_ID = "_id";
    public static final String FIELD_IDSHOPPINGLIST = "IDSHOPPINGLIST";
    public static final String FIELD_DESCRIPTION = "DESCRIPTION";
    public static final String FIELD_UNITVALUE = "UNITVALUE";
    public static final String FIELD_QUANTITY = "QUANTITY";
    public static final String FIELD_CHECKED = "CHECKED";

    public static GroceryListItems insert(Context context, SQLiteDatabase db, GroceryListItems groceryListItems) throws Exception {
        ContentValues cv = new ContentValues();
        //cv.put(FIELD_IDSHOPPINGLIST, GroceryListItems.getIdGroceryList());
        //cv.put(FIELD_DESCRIPTION, GroceryListItems.getDescription());
        //cv.put(FIELD_UNITVALUE, GroceryListItems.getUnitValue());
        //cv.put(FIELD_QUANTITY, GroceryListItems.getQuantity());
        //cv.put(FIELD_CHECKED, String.valueOf(groceryListItems.isChecked()));

        try {
            db.insert(TABLE_NAME, null, cv);
            return selectLast(context, db);
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }
    }

    public static GroceryListItems insert(Context context, GroceryListItems itemShoppingList) throws Exception {
        SQLiteDatabase db = new DatabaseDAO(context).getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(FIELD_IDSHOPPINGLIST, itemShoppingList.getIdGroceryList());
        cv.put(FIELD_DESCRIPTION, itemShoppingList.getDescription());
        cv.put(FIELD_UNITVALUE, itemShoppingList.getUnitValue());
        cv.put(FIELD_QUANTITY, itemShoppingList.getQuantity());
        cv.put(FIELD_CHECKED, String.valueOf(itemShoppingList.isChecked()));

        try {
            db.insert(TABLE_NAME, null, cv);
            return selectLast(context, db);
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }
    }

    private static GroceryListItems selectLast(Context context, SQLiteDatabase db) throws Exception {
        try {
            Cursor cursor = db.query(TABLE_NAME, new String[] { FIELD_ID, FIELD_IDSHOPPINGLIST, FIELD_DESCRIPTION, FIELD_UNITVALUE, FIELD_QUANTITY, FIELD_CHECKED }, null, null, null, null, FIELD_ID + " desc");

            if (cursor.moveToNext()) {
                return returnClassInstace(context, cursor);
            }

            cursor.close();
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }

        return null;
    }

    public static void checkAllItens(Context context, int idShoppingList, boolean selected) throws Exception {
        SQLiteDatabase db = new DatabaseDAO(context).getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(FIELD_CHECKED, String.valueOf(selected));
        try {
            db.update(TABLE_NAME, cv, FIELD_IDSHOPPINGLIST + " = ?", new String[]{String.valueOf(idShoppingList)});
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }
    }

    public static void delete(Context context, int idItemShoppingList) throws Exception {
        try {
            SQLiteDatabase db = new DatabaseDAO(context).getWritableDatabase();
            db.delete(TABLE_NAME, FIELD_ID + " = ? ", new String[] { String.valueOf(idItemShoppingList) });
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }
    }

    public static void deleteAllLista(Context context, int idShoppingList, boolean onlyCheckeds) throws Exception {
        try {
            SQLiteDatabase db = new DatabaseDAO(context).getWritableDatabase();
            String whereClause = FIELD_IDSHOPPINGLIST + " = ? ";
            String[] whereArgs = new String[] { String.valueOf(idShoppingList) };

            if (onlyCheckeds) {
                whereClause = whereClause + " AND " + FIELD_CHECKED + " = ? ";
                whereArgs = new String[] { String.valueOf(idShoppingList), String.valueOf(onlyCheckeds) };
            }

            db.delete(TABLE_NAME, whereClause, whereArgs);
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }
    }

    public static GroceryListItems select(Context context, int idItemShoppingList) throws Exception {
        try {
            SQLiteDatabase db = new DatabaseDAO(context).getReadableDatabase();

            Cursor cursor = db.query(TABLE_NAME, new String[] { FIELD_ID, FIELD_IDSHOPPINGLIST, FIELD_DESCRIPTION, FIELD_UNITVALUE, FIELD_QUANTITY, FIELD_CHECKED }, FIELD_ID + " = ?", new String[] { String.valueOf(idItemShoppingList) }, null, null, null);

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

            Cursor cursor = db.query(TABLE_NAME, new String[] { FIELD_ID, FIELD_IDSHOPPINGLIST, FIELD_DESCRIPTION, FIELD_UNITVALUE, FIELD_QUANTITY, FIELD_CHECKED }, FIELD_DESCRIPTION + " = ?", new String[] { String.valueOf(description) }, null, null, FIELD_ID + " DESC");
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
        return new GroceryListItems(cursor.getInt(cursor.getColumnIndex(FIELD_ID)), cursor.getInt(cursor.getColumnIndex(FIELD_IDSHOPPINGLIST)), cursor.getString(cursor.getColumnIndex(FIELD_DESCRIPTION)), cursor.getFloat(cursor.getColumnIndex(FIELD_UNITVALUE)), cursor.getFloat(cursor
                .getColumnIndex(FIELD_QUANTITY)), Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(FIELD_CHECKED))));
    }

    @SuppressLint("Range")
    public static ArrayAdapter<String> selectAutoComplete(Context context, String[] descriptionFilters) throws Exception {
        ArrayList<String> list = new ArrayList<String>();

        try {
            SQLiteDatabase db = new DatabaseDAO(context).getReadableDatabase();

            String selection = null;

            if (descriptionFilters.length > 0) {
                selection = FIELD_DESCRIPTION + " not in (" + numOfParameters(descriptionFilters.length) + ")";
            }

            Cursor cursor = db.query(TABLE_NAME, new String[] { FIELD_DESCRIPTION }, selection, descriptionFilters, FIELD_DESCRIPTION, null, FIELD_DESCRIPTION + " asc");
            while (cursor.moveToNext()) {
                list.add(cursor.getString(cursor.getColumnIndex(FIELD_DESCRIPTION)));
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
        //cv.put(FIELD_DESCRIPTION, groceryListItems.getDescription());
        //cv.put(FIELD_UNITVALUE, groceryListItems.getUnitValue());
       // cv.put(FIELD_QUANTITY, groceryListItems.getQuantity());
       // cv.put(FIELD_CHECKED, String.valueOf(GroceryListItems.isChecked()));
        try {
            //db.update(TABLE_NAME, cv, FIELD_ID + " = ?", new String[] { String.valueOf(GroceryListItems.getId()) });
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
                where = " AND " + FIELD_DESCRIPTION + " LIKE '%" + filter + "%'";
            }

            SQLiteDatabase db = new DatabaseDAO(context).getReadableDatabase();
            return db.query(TABLE_NAME, new String[] { FIELD_ID, FIELD_IDSHOPPINGLIST, FIELD_DESCRIPTION, FIELD_UNITVALUE, FIELD_QUANTITY, FIELD_CHECKED }, FIELD_IDSHOPPINGLIST + " = ?" + where, new String[] { String.valueOf(idShoppingList) }, null, null, orderBy);
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }
    }
    public static String toString(Context context, int idShoppingList) throws Exception {
        String result = "";
        float totalValue = 0;
        try {
            Cursor c = selectAll(context, null, idShoppingList);
            while (c.moveToNext()) {
                GroceryListItems item = returnClassInstace(context, c);
                totalValue = totalValue + item.getUnitValue() * item.getQuantity();
                result = result + " \n" + item.getDescription() + " - " + CustomFloatFormat.getSimpleFormatedValue(item.getQuantity()) + " x " + CustomFloatFormat.getMonetaryMaskedValue(context, item.getUnitValue());
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
