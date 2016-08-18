package com.example.selim.h2h.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.selim.h2h.R;
import com.parse.ParseObject;

import java.util.List;

/**
 * Created by selim on 03/02/2016.
 */

public class ListAppointmentsAdapter extends ArrayAdapter<ParseObject> {
    List<ParseObject> listAppointements;
    Context context;
    LayoutInflater layoutInflater;
    private int resourceId = 0;
    ParseObject item;

    public ListAppointmentsAdapter(Context context, int resource, List<ParseObject> listAppointments) {
        super(context, resource,listAppointments);
        this.listAppointements = listAppointments;
        this.context = context;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.resourceId = resource;
    }

    @Override
    public int getCount() {
        return listAppointements.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(resourceId, parent, false);

        final ListAppointmentsAdapter.ViewHolder holder = new ViewHolder(view);

        item = getItem(position);

        holder.date.setText("Date : "+item.getDate("DateRendezVous"));
        holder.critique.setText("Critique : "+item.get("Critique"));


        return view;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView date;
        public TextView critique;


        public ViewHolder(View itemView) {
            super(itemView);
            date = (TextView)itemView.findViewById(R.id.date);
            critique = (TextView)itemView.findViewById(R.id.critique);



                    }

    }
}

