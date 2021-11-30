package com.example.fromstoretocore;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ItemListViewAdapter extends ArrayAdapter<GroceryListItems> {

    public static ArrayList<GroceryListItems> items;
    Context context;

    public ItemListViewAdapter(Context context, ArrayList<GroceryListItems> items) {
        super(context, R.layout.item_list_row, items);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        GroceryListItems items = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_list_row, parent, false);
        }

            TextView number = (TextView) convertView.findViewById(R.id.item_number);
            number.setText(position + 1 + ".");

            TextView itemName = (TextView) convertView.findViewById(R.id.item_name);
            itemName.setText(items.getDescription());

            ImageView note = convertView.findViewById(R.id.item_note);
            ImageView delete = convertView.findViewById(R.id.item_note);


        return convertView;
    }
}
