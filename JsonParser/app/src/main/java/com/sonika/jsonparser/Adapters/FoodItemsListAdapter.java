package com.sonika.jsonparser.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sonika.jsonparser.Pojo.FoodDetails;
import com.sonika.jsonparser.R;

import java.util.ArrayList;
import java.util.List;

public class FoodItemsListAdapter extends ArrayAdapter<FoodDetails> {

    Context context;
    int layoutResourceId;
    List<FoodDetails> taskList = new ArrayList<>();
    int i=0;

    public FoodItemsListAdapter(Context context, int layoutResourceId, List<FoodDetails> taskList) {
        super(context, layoutResourceId, taskList);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.taskList = taskList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        TaskHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new TaskHolder();
            holder.price = (TextView)row.findViewById(R.id.fooditemadapter_price);
            holder.desc = (TextView)row.findViewById(R.id.fooditemadapter_descriptionfooditem);
            holder.img = (ImageView)row.findViewById(R.id.fooditemadapter_imgfooditem);
            holder.name = (TextView)row.findViewById(R.id.fooditemadapter_name);



            row.setTag(holder);
        }
        else
        {
            holder = (TaskHolder)row.getTag();
        }

        FoodDetails foodInfo = taskList.get(position);
        holder.price.setText("Price:"+ " " +foodInfo.getPrice());
        holder.desc.setText(foodInfo.getDescription());
        holder.name.setText(foodInfo.getName());

        Glide.with(getContext()).load(foodInfo.getImage()).into(holder.img);


        return row;
    }

    static class TaskHolder
    {
        TextView price, desc, name;
        ImageView img;

    }
}

