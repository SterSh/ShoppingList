package com.example.fromstoretocore;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoadList extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, DialogInterface.OnDismissListener, View.OnClickListener {
    // Initialize Private Variables
    private ListView saved_lists;
    private List<String> items = new ArrayList<>();
    GroceryList grocerylist;
    private ArrayAdapter listAdapter;
    private String Filename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load_lists);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();
        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        try {
            grocerylist = GroceryListDAO.select(this, getIntent().getExtras().getInt((getString(R.string.id_shopping_list))));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Filename = grocerylist.getName();
        items.add(Filename);

        //database
        saved_lists = findViewById(R.id.saved_lists);
        listAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, items);

        saved_lists.setAdapter(listAdapter);
    }
    protected void onResume() {
        saved_lists.setOnItemClickListener(this);
        saved_lists.setOnItemLongClickListener(this);

        refreshListView();
        super.onResume();

        saved_lists.setAdapter(listAdapter);

		/*//Initializing the pollfish monetarization
        PollFish.init(this, getString(R.string.pollfish_api_key), Position.BOTTOM_LEFT, 30);
        adView.resume();*/
        
    }

    private void refreshListView() {
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return false;
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
