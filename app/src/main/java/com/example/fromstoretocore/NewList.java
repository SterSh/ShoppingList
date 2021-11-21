package com.example.fromstoretocore;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class NewList extends AppCompatActivity {

    private List<String> items = new ArrayList<>();
    private EditText itemInput;
    private ListView lv_groceryList;
    private String Filename;
    private GroceryList grocerylist;
    private GroceryListItems groceryListItems;
    DatabaseHelper database;

    ArrayAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_list);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        database = new DatabaseHelper(this);
        try {
            grocerylist = GroceryListDAO.select(this, getIntent().getExtras().getInt((getString(R.string.id_shopping_list))));
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        this.setTitle(grocerylist.getName() + ": Grocery List");

        itemInput = findViewById(R.id.itemInput);

        lv_groceryList = findViewById(R.id.lv_groceryList);

        listAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, items);
        lv_groceryList.setAdapter(listAdapter);


    }

    public void AddItem(View view) {
        String newItem = itemInput.getText().toString();
        items.add(newItem);

        listAdapter.notifyDataSetChanged();
        Toast toast = Toast.makeText(getApplicationContext(), "Item Added: " + newItem, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void SaveList(View view) throws IOException {
        Filename = grocerylist.getName();
        if (Filename.matches("")) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No filename detected, Please name Grocery List", Toast.LENGTH_SHORT);
            toast.show();
        } else {

            Gson gson = new Gson();


            String inputString= gson.toJson(items);

            System.out.println("inputString= " + inputString);

            database.insert(Filename, inputString);
            /*
            FileOutputStream fos = null;
            fos = openFileOutput(Filename, Context.MODE_PRIVATE);

            ObjectOutputStream oos = null;
            oos = new ObjectOutputStream(fos);
            oos.writeObject(items);
            oos.close();

            Toast toast = Toast.makeText(getApplicationContext(),
                    "Successfully saved List: " + Filename, Toast.LENGTH_SHORT);
            toast.show();

             */
        }
    }

    // this event will enable the back
    // function to the button on press
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
