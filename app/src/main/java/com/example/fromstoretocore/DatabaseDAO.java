package com.example.fromstoretocore;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

public class DatabaseDAO extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "GroceryList";
    private Context context;

    public DatabaseDAO(Context context) {
        super(context, DATABASE_NAME, null,1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE GROCERYLIST(_id INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT);");
        db.execSQL("CREATE TABLE GROCERYLISTITEMS(_id INTEGER PRIMARY KEY AUTOINCREMENT, IDGROCERYLIST INTEGER, DESCRIPTION TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        try {
            db.beginTransaction();
            db.execSQL("CREATE TABLE GROCERYLISTX(_id INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT);");
            db.execSQL("INSERT INTO GROCERYLISTX(_id,NAME) SELECT ID,NAME FROM GROCERYLIST;");
            db.execSQL("DROP TABLE IF EXISTS GROCERYLIST;");


            db.execSQL("CREATE TABLE GROCERYLISTITEMSX(_id INTEGER PRIMARY KEY AUTOINCREMENT, IDGROCERYLIST INTEGER, DESCRIPTION TEXT);");
            db.execSQL("INSERT INTO GROCERYLISTITEMSX(_id,IDSHOPPINGLIST,DESCRIPTION) SELECT ID,IDGROCERYLIST,DESCRIPTION FROM GROCERYLISTITEMS;");
            db.execSQL("DROP TABLE IF EXISTS GROCERYLISTITEMS;");

            onCreate(db);
            db.execSQL("INSERT INTO GROCERYLIST(_id,NAME) SELECT _id,NAME FROM GROCERYLISTX;");
            db.execSQL("INSERT INTO GROCERYLISTITEMS(_id,IDSHOPPINGLIST,DESCRIPTION) SELECT _id,IDSHOPPINGLIST,DESCRIPTION FROM GROCERYLISTITEMSX;");
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            db.endTransaction();
        }
    }
}
