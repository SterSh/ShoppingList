package com.example.fromstoretocore;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoadList extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, DialogInterface.OnDismissListener, View.OnClickListener {
    // Initialize Private Variables
    private ListView saved_lists;
    GroceryList grocerylist;
    private ArrayAdapter listAdapter;

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
        saved_lists = findViewById(R.id.saved_lists);
        listAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);

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
}
