package com.bluealien99.simplecalc;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class keypadAdapter extends ArrayAdapter<String> {

    private int screenWidth;

    public keypadAdapter(Context context, int resource, String[] objects, int screenWidth) {
        super(context, resource, objects);
        this.screenWidth = screenWidth;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        String string = getItem(position);

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.keypadkey, parent, false);
            view.setMinimumHeight(screenWidth * 2 / 9);
        }

        TextView text = (TextView) view.findViewById(R.id.keypadkeyStr);
        text.setText(string);

        return view;
    }
}
