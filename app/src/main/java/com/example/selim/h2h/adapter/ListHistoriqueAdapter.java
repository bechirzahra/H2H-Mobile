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
 * Created by selim on 23/02/2016.
 */
public class ListHistoriqueAdapter extends ArrayAdapter<ParseObject> {

    List<ParseObject> listHistorique;
    Context context;
    LayoutInflater layoutInflater;
    private int resourceId = 0;
    ParseObject item;

    public ListHistoriqueAdapter(Context context, int resource, List<ParseObject> listHistorique) {
        super(context, resource, listHistorique);
        this.listHistorique = listHistorique;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.resourceId = resource;
    }

    @Override
    public int getCount() {
        return listHistorique.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(resourceId, parent, false);

        final ListHistoriqueAdapter.ViewHolder holder = new ViewHolder(view);

        item = getItem(position);

        holder.date.setText("Date : " + item.getCreatedAt());

       

        return view;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView date;


        public ViewHolder(View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.date_historique_txt);



        }

    }
}