package com.example.fromstoretocore;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    public static final String DATABASE_NAME = "GroceryList_1";
    public static final String TABLE1 = "table1";
    public static final String TABLE2 = "table2";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null,1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String table1 = "CREATE TABLE " +TABLE1+ "(_id INTEGER PRIMARY KEY,NAME TEXT,DATELIST INTEGER)";
        String table2 = "CREATE TABLE " +TABLE2+ "(_id INTEGER PRIMARY KEY,product TEXT)";

        db.execSQL(table1);
        db.execSQL(table2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop Existing Table
        db.execSQL("DROP TABLE IF EXISTS "+TABLE1);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE2);

        onCreate(db);
    }

    public boolean insert(String product) {
        //Get Writeable Database
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        //Create Content Values
        ContentValues values2 = new ContentValues();
        values2.put("product", product);
        //Insert Data into Database
        sqLiteDatabase.insert(TABLE2,null,values2);

        return true;
    }
    public boolean insert(String name, String product) {
        //Get Writeable Database
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        //Create Content Values
        ContentValues values1 = new ContentValues();
        values1.put("name", name);
        //Insert Data into Database
        sqLiteDatabase.insert(TABLE1,null,values1);

        //Create Content Values
        ContentValues values2 = new ContentValues();
        values2.put("product", product);
        //Insert Data into Database
        sqLiteDatabase.insert(TABLE2,null,values2);

        return true;
    }

    public ArrayList getProduct() {
        //get Readable Database
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ArrayList<String> arrayList = new ArrayList<>();

        //Perform RawQuery
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE2, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            arrayList.add(cursor.getString(cursor.getColumnIndex("product")));
            cursor.moveToNext();
        }
        return arrayList;
    }

    public ArrayList getName() {
        //get Readable Database
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ArrayList<String> arrayList = new ArrayList<>();

        //Perform RawQuery
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE1, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            arrayList.add(cursor.getString(cursor.getColumnIndex("name")));
            cursor.moveToNext();
        }
        return arrayList;
    }
}
