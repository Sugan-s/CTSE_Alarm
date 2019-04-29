package com.example.suganya.alarmsystem;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AlarmAdapter extends ArrayAdapter {

    List list = new ArrayList();

    public AlarmAdapter( Context context, int resource) {
        super(context, resource);
    }


    public void add(Alarms object) {
        list.add(object);
        super.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }


    @Override
    public Object getItem(int position) {
        return list.get(position);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        ProductHolder productHolder;
        if(row == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.display_alarm_row,parent,false);
            productHolder = new ProductHolder();
            productHolder.txt_time = row.findViewById(R.id.te_time);
            productHolder.txt_name = row.findViewById(R.id.te_name);
            row.setTag(productHolder);
        }
        else
        {
            productHolder = (ProductHolder) row.getTag();
        }

        Alarms alarms = (Alarms) getItem(position);
        productHolder.txt_time.setText(alarms.getTime().toString());
        productHolder.txt_name.setText(alarms.getName().toString());
        return row;
    }

    static class ProductHolder
    {
        TextView txt_time,txt_name;
    }
}

