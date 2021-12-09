package com.example.fromstoretocore;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoadList extends AppCompatActivity {
    // Initialize Private Variables
    private ListView saved_lists;
    private List<String> items = new ArrayList<>();
    GroceryList grocerylist;
    private ArrayAdapter listAdapter;
    ArrayList<GroceryList> groceryList;
    GroceryListDAO groceryListDAO = new GroceryListDAO(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load_lists);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        try {
            grocerylist = (GroceryList) GroceryListDAO.selectAll(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Call DatabaseDAO
        DatabaseDAO databaseDAO = new DatabaseDAO(this);
        groceryList = databaseDAO.getListNames();

        //database
        saved_lists = findViewById(R.id.saved_lists);
        listAdapter = new ListsListViewAdapter(this, groceryList);

        saved_lists.setAdapter(listAdapter);

        saved_lists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GroceryList clickedList = (GroceryList) parent.getItemAtPosition(position);

                Intent intent = new Intent(LoadList.this, NewList.class);
                intent.putExtra("groceryList", clickedList.getId());
                startActivity(intent);
            }
        });
    }

    protected void onResume() {
        refreshListView();
        super.onResume();
        saved_lists.setAdapter(listAdapter);
    }

    private void refreshListView() {
        listAdapter.notifyDataSetChanged();
    }

    public static void deleteList(GroceryList item) {
        GroceryListDAO.deleteList(item);
    }

    public static void deleteListItems(GroceryList item) {
        GroceryListItemsDAO.deleteListItems(item);
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
