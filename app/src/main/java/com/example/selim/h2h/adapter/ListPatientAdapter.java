package com.example.selim.h2h.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.selim.h2h.R;
import com.example.selim.h2h.utils.CircleTransformation;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by selim on 10/02/2016.
 */
public class ListPatientAdapter extends ArrayAdapter<ParseUser> {

    List<ParseUser> listPatient;
    Context context;
    LayoutInflater layoutInflater;
    private int resourceId = 0;
    ParseUser item;

    public ListPatientAdapter(Context context, int resource, List<ParseUser> listPatient) {
        super(context, resource,listPatient);
        this.listPatient = listPatient;
        this.context = context;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.resourceId = resource;
    }

    @Override
    public int getCount() {

        return listPatient.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(resourceId, parent, false);

        final ListPatientAdapter.ViewHolder holder = new ViewHolder(view);

        item = getItem(position);

        holder.username.setText(""+item.getUsername());
        holder.adress.setText(""+item.get("adresse"));
        ParseFile image = (ParseFile) item.get("Image");//live url
        if(image != null) {
            Uri imageUri = Uri.parse(image.getUrl());
            Picasso.with(context).load(imageUri.toString()).transform(new CircleTransformation()).into(holder.img);
        }

        return view;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView username;
        public TextView adress;
        public ImageView img;


        public ViewHolder(View itemView) {
            super(itemView);
            username = (TextView)itemView.findViewById(R.id.name_btn);
            adress = (TextView)itemView.findViewById(R.id.adresse_btn);
            img = (ImageView)itemView.findViewById(R.id.img_patient_btn);
        }

    }


}
