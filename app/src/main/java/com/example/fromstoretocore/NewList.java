package com.example.fromstoretocore;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class NewList extends AppCompatActivity {
    private ArrayList<EditText> items = new ArrayList<>();

    public NewList() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_list);
    }

    public void AddItem(View view) {
        EditText newItem = findViewById(R.id.itemInput);
        items.add(newItem);
        Toast toast = Toast.makeText(getApplicationContext(), "Item Added", Toast.LENGTH_SHORT);
        toast.show();
    }
}
