package com.example.fromstoretocore;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper database;
    ArrayList arrayList1, arrayList2;
    ArrayAdapter arrayAdapter1, arrayAdapter2;
    GroceryList groceryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void NewList(View view) {
        /*
        Intent intent = new Intent(this, NewList.class);
        //NewList temp = new NewList();
        Toast toast = Toast.makeText(getApplicationContext(), "Creating New Grocery List", Toast.LENGTH_SHORT);
        toast.show();

        startActivity(intent);

         */
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(getString(R.string.save));
        alert.setMessage(getString(R.string.title_new));

        final EditText edName = new EditText(this);
        alert.setView(edName);

        alert.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                groceryList = new GroceryList(MainActivity.this);
                groceryList.setName(MainActivity.this, edName.getText().toString());

                //arrayList1.add(edName.getText().toString());

                try {
                    startActivity(new Intent(MainActivity.this, NewList.class).putExtra(getString(R.string.id_shopping_list),
                            GroceryListDAO.insert(MainActivity.this, groceryList).getId()));
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });

        alert.setNegativeButton(android.R.string.cancel, null);
        alert.show();
    }

    public void LoadList(View view) throws Exception {
        //groceryList = new GroceryList(MainActivity.this);

        if (groceryList.getName() == null) {
            Toast toast = Toast.makeText(getApplicationContext(), "No Saved Lists Found", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            Intent intent = new Intent(this, LoadList.class).putExtra(getString(R.string.id_shopping_list),
                    GroceryListDAO.insert(MainActivity.this, groceryList).getId());
            startActivity(intent);
        }

    }

    public void OutputDisplay() {
    }
}