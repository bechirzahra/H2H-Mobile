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
public class ListPrescriptionAdapter extends ArrayAdapter<ParseObject> {

    List<ParseObject> listPrescription;
    Context context;
    LayoutInflater layoutInflater;
    private int resourceId = 0;
    ParseObject item;

   public  ListPrescriptionAdapter (Context context, int resource, List<ParseObject> listPrescription) {
       super(context, resource,listPrescription);
       this.listPrescription = listPrescription;
       this.context = context;
       layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       this.resourceId = resource;
   }

    @Override
    public int getCount() {
        return listPrescription.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(resourceId, parent, false);

        final ListPrescriptionAdapter.ViewHolder holder = new ViewHolder(view);

        item = getItem(position);

        holder.date.setText("Date : "+item.getCreatedAt());
        holder.comment.setText("Comment : "+item.get("comment"));
        System.out.println("Date : "+item.getCreatedAt());

        return view;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView date;
        public TextView comment;


        public ViewHolder(View itemView) {
            super(itemView);
            date = (TextView)itemView.findViewById(R.id.date1);
            comment = (TextView)itemView.findViewById(R.id.txt_commentA);



        }

    }
}
