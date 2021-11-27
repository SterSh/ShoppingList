package com.example.fromstoretocore;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class NewList extends AppCompatActivity {

    private ArrayList<String> items = new ArrayList<>();
    private ListView lv_groceryList;
    private GroceryList grocerylist;

    static ItemListViewAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        try {
            grocerylist = GroceryListDAO.select(this, getIntent().getExtras().getInt("groceryList"));
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }


        this.setTitle(grocerylist.getName() + ": Grocery List");




        lv_groceryList = findViewById(R.id.lv_groceryList);

        listAdapter = new ItemListViewAdapter(this, items);
        lv_groceryList.setAdapter(listAdapter);


    }

    public void AddItem(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("New Item");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", (dialog, which) -> {
            String newItem = input.getText().toString();
            items.add(newItem);

            try {
                GroceryListItemsDAO.insert(this, new GroceryListItems(0, grocerylist.getId(), newItem));
            } catch (Exception e) {
                e.printStackTrace();
            }
            Toast toast = Toast.makeText(getApplicationContext(), "Item Added: " + newItem, Toast.LENGTH_SHORT);
            toast.show();


        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    public void SaveList(View view) throws Exception {
        /*
        Filename = grocerylist.getName();
        if (Filename.matches("")) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No filename detected, Please name Grocery List", Toast.LENGTH_SHORT);
            toast.show();
        } else {

            Gson gson = new Gson();


            String inputString= gson.toJson(items);

            System.out.println("inputString= " + inputString);

            //database.insert(Filename, inputString);

            FileOutputStream fos = null;
            fos = openFileOutput(Filename, Context.MODE_PRIVATE);

            ObjectOutputStream oos = null;
            oos = new ObjectOutputStream(fos);
            oos.writeObject(items);
            oos.close();

            Toast toast = Toast.makeText(getApplicationContext(),
                    "Successfully saved List: " + Filename, Toast.LENGTH_SHORT);
            toast.show();


        }
         */
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
