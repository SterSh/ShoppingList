package com.example.fromstoretocore;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class NewList extends AppCompatActivity {

    private List<String> items = new ArrayList<>();
    private EditText itemInput;
    private ListView lv_groceryList;

    ArrayAdapter listAdapter;

    public NewList() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_list);

        itemInput = findViewById(R.id.itemInput);


        lv_groceryList = findViewById(R.id.lv_groceryList);

        listAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1 , items);
        lv_groceryList.setAdapter(listAdapter);


    }

    public void AddItem(View view) {
        String newItem = itemInput.getText().toString();
        items.add(newItem);

        listAdapter.notifyDataSetChanged();

        Toast toast = Toast.makeText(getApplicationContext(), "Item Added" + newItem, Toast.LENGTH_SHORT);
        toast.show();
    }
}
