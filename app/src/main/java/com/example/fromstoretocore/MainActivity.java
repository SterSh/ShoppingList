package com.example.fromstoretocore;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void NewList(View view) {
        Intent intent = new Intent(this, NewList.class);
        //NewList temp = new NewList();
        Toast toast = Toast.makeText(getApplicationContext(), "Creating New Grocery List", Toast.LENGTH_SHORT);
        toast.show();

        startActivity(intent);
    }

    public void LoadList(View view) {
    Intent intent = new Intent(this, LoadList.class);
    //NewList temp = new NewList();
    // Check to see if their are any saved Lists //
    // Placeholder for now, No function for checked added yet
    Toast toast = Toast.makeText(getApplicationContext(), "No Saved Lists Found", Toast.LENGTH_SHORT);
    toast.show();

    startActivity(intent);
    }

    public void OutputDisplay(View view) {

    }
}