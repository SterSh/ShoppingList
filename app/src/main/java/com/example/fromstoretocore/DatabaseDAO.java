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

    public static final String DATABASE_NAME = "GroceryList_1";
    public static final String TABLE1 = "table1";
    public static final String TABLE2 = "table2";
    private Context context;

    public DatabaseDAO(Context context) {
        super(context, DATABASE_NAME, null,1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*
        String table1 = "CREATE TABLE " +TABLE1+ "(_id INTEGER PRIMARY KEY,NAME TEXT,DATELIST INTEGER)";
        String table2 = "CREATE TABLE " +TABLE1+ "(_id INTEGER PRIMARY KEY,product TEXT)";

        db.execSQL(table1);
        db.execSQL(table2);

         */
        db.execSQL("CREATE TABLE GROCERYLIST(_id INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT);");
        db.execSQL("CREATE TABLE GROCERYLISTITEMS(_id INTEGER PRIMARY KEY AUTOINCREMENT, IDSHOPPINGLIST INTEGER, DESCRIPTION TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.beginTransaction();
        db.execSQL("CREATE TABLE GROCERYLISTX(_id INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT);");
        db.execSQL("INSERT INTO GROCERYLISTX(_id,NAME) SELECT ID,NAME FROM GROCERYLIST;");
        db.execSQL("DROP TABLE IF EXISTS GROCERYLIST;");


        db.execSQL("CREATE TABLE GROCERYLISTITEMSX(_id INTEGER PRIMARY KEY AUTOINCREMENT, IDSHOPPINGLIST INTEGER, DESCRIPTION TEXT);");
        db.execSQL("INSERT INTO GROCERYLISTITEMSX(_id,IDSHOPPINGLIST,DESCRIPTION) SELECT ID,IDSHOPPINGLIST,DESCRIPTION FROM GROCERYLISTITEMS;");
        db.execSQL("DROP TABLE IF EXISTS GROCERYLISTITEMS;");

        onCreate(db);
        db.execSQL("INSERT INTO SHOPPINGLIST(_id,NAME,DATELIST) SELECT _id,NAME,DATELIST FROM GROCERYLISTX;");
        db.execSQL("INSERT INTO ITEMSHOPPINGLIST(_id,IDSHOPPINGLIST,DESCRIPTION,UNITVALUE,QUANTITY,CHECKED) SELECT _id,IDSHOPPINGLIST,DESCRIPTION FROM GROCERYLISTITEMSX;");
        db.setTransactionSuccessful();

        /*
        //Drop Existing Table
        db.execSQL("DROP TABLE IF EXISTS "+TABLE1);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE2);

        onCreate(db);

         */
    }

    /*
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
        sqLiteDatabase.insert(TABLE1,null,values2);

        return true;
    }

    @SuppressLint("Range")
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

     */

}
