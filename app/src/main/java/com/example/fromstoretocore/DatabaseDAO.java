package com.example.fromstoretocore;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

public class DatabaseDAO extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "GROCERYLIST";
    public static final String ITEM_TABLE_NAME = "GROCERYLISTITEMS";

    public static final String DATABASE_NAME = "GroceryList";
    private Context context;

    public DatabaseDAO(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Put in the rest of the Static Fields from GroceryListItemsDAO into GROCERYLISTITEMS TABLE as you initialize them into //
        // the code, will break program I put them in now. Must be in all caps like it is now.                                   //
        db.execSQL("CREATE TABLE GROCERYLIST(_id INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT);");
        db.execSQL("CREATE TABLE GROCERYLISTITEMS(_id INTEGER PRIMARY KEY AUTOINCREMENT, IDGROCERYLIST INTEGER, DESCRIPTION TEXT, CHECKED VARCHAR(1));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        try {
            db.beginTransaction();
            db.execSQL("CREATE TABLE GROCERYLIST(_id INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT);");
            db.execSQL("INSERT INTO GROCERYLIST(_id,NAME) SELECT ID,NAME FROM GROCERYLIST;");
            db.execSQL("DROP TABLE IF EXISTS GROCERYLIST;");

            // Put in the rest of the Static Fields from GroceryListItemsDAO into GROCERYLISTITEMS TABLE as you initialize them into //
            // the code, will break program I put them in now. Must be in all caps like it is now.                                   //
            db.execSQL("CREATE TABLE GROCERYLISTITEMS(_id INTEGER PRIMARY KEY AUTOINCREMENT, IDGROCERYLIST INTEGER, DESCRIPTION TEXT, CHECKED VARCHAR(1));");
            db.execSQL("INSERT INTO GROCERYLISTITEMS(_id,IDSHOPPINGLIST,DESCRIPTION,CHECKED) SELECT ID,IDGROCERYLIST,DESCRIPTION, CHECKED FROM GROCERYLISTITEMS;");
            db.execSQL("DROP TABLE IF EXISTS GROCERYLISTITEMS;");

            // Put in the rest of the Static Fields from GroceryListItemsDAO into GROCERYLISTITEMS TABLE as you initialize them into //
            // the code, will break program I put them in now. Must be in all caps like it is now.                                   //
            onCreate(db);
            db.execSQL("INSERT INTO GROCERYLIST(_id,NAME) SELECT _id,NAME FROM GROCERYLISTX;");
            db.execSQL("INSERT INTO GROCERYLISTITEMS(_id,IDSHOPPINGLIST,DESCRIPTION, CHECKED) SELECT _id,IDSHOPPINGLIST,DESCRIPTION, CHECKED FROM GROCERYLISTITEMSX;");
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            db.endTransaction();
        }
    }

    // Sterling can do this for the rest of the items like he did for the names       //
    // May require another function like this one, for when we reload a previous list //
    public ArrayList<GroceryList> getListNames() {
        ArrayList<GroceryList> groceryLists = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);

        while (res.moveToNext()) {
            int id = res.getInt(0);
            String name = res.getString(1);

            GroceryList newList = new GroceryList(id, name);
            groceryLists.add(newList);
        }
        return groceryLists;
    }
    //Method to get all the items for a given list.


}
