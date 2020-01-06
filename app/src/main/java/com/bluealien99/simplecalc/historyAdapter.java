package com.bluealien99.simplecalc;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class historyAdapter extends ArrayAdapter<String> {

    public historyAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        String string = getItem(position);

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.hisitem, parent, false);
        }

        TextView text = (TextView) view.findViewById(R.id.hisitem);
        text.setText(string);

        return view;
    }
}
