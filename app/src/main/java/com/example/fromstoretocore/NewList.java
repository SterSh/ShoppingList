package com.example.fromstoretocore;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;


public class NewList extends AppCompatActivity {

    private ArrayList<String> items = new ArrayList<>();
    private ArrayList<GroceryListItems> groceryItems = new ArrayList<>();
    private ListView lv_groceryList;
    private GroceryList grocerylist;

    ItemListViewAdapter listAdapter;

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

        //displays the name of the grocery list in the header
        this.setTitle(grocerylist.getName() + ": Grocery List");
        //Query the database for all the items associated with the list
        GroceryListItemsDAO groceryListItemsDAO = new GroceryListItemsDAO(this);
        groceryItems = groceryListItemsDAO.getListItems(grocerylist.getId());

        //Log.d("Tag", "" + groceryItems);


        lv_groceryList = findViewById(R.id.lv_groceryList);

        listAdapter = new ItemListViewAdapter(this, groceryItems);
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

            try {
                GroceryListItemsDAO groceryListItemsDAO = new GroceryListItemsDAO(this);
                groceryItems = groceryListItemsDAO.getListItems(grocerylist.getId());
                listAdapter = new ItemListViewAdapter(this, groceryItems);
                lv_groceryList.setAdapter(listAdapter);
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
