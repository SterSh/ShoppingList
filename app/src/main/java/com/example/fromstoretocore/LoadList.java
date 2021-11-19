package com.example.fromstoretocore;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoadList extends AppCompatActivity {
    private ListView saved_lists;
    DatabaseHelper database;
    ArrayList<String> Names = new ArrayList<>();
    GroceryList grocerylist;
    ArrayAdapter listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load_lists);
        try {
            grocerylist = GroceryListDAO.select(this, getIntent().getExtras().getInt((getString(R.string.id_shopping_list))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //database

        Gson gson = new Gson();


        ArrayList<String> outputArray = database.getName();

        saved_lists = findViewById(R.id.saved_lists);
        listAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, outputArray);

        saved_lists.setAdapter(listAdapter);
    }
}
