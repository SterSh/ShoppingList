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

import java.util.ArrayList;

public class ItemListViewAdapter extends ArrayAdapter<String> {

    ArrayList<String> list;
    Context context;

    public ItemListViewAdapter(Context context, ArrayList<String> items) {
        super(context, R.layout.item_list_row, items);
        this.context = context;
        list = items;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService((Activity.LAYOUT_INFLATER_SERVICE));
            convertView = layoutInflater.inflate(R.layout.item_list_row, null);

            TextView number = convertView.findViewById(R.id.item_number);
            number.setText(position + 1 + ".");

            TextView item = convertView.findViewById(R.id.item_name);
            item.setText(list.get(position));

            ImageView note = convertView.findViewById(R.id.item_note);
            ImageView delete = convertView.findViewById(R.id.item_note);

        }
        return convertView;
    }
}
