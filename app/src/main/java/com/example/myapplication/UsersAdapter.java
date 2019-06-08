package com.example.myapplication;


import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.CustomViewHolder> {

    private ArrayList<PersonalData> mList = null;
    private Activity context = null;


    public UsersAdapter(Activity context, ArrayList<PersonalData> list) {
        this.context = context;
        this.mList = list;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView id;
        protected TextView name;
        protected TextView address;
        protected TextView date;
        protected TextView Detail;


        public CustomViewHolder(View view) {
            super(view);
            this.id = (TextView) view.findViewById(R.id.textView_list_id);
            this.name = (TextView) view.findViewById(R.id.textView_list_name);
            this.address = (TextView) view.findViewById(R.id.textView_list_location);
            this.date = (TextView) view.findViewById(R.id.textView_list_date);
            this.Detail = (TextView) view.findViewById(R.id.textView_list_Detail);
        }
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.boitem_list, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {

        viewholder.id.setText(mList.get(position).getMember_id());
        viewholder.name.setText(mList.get(position).getMember_name());
        viewholder.address.setText(mList.get(position).getMember_address());
        viewholder.date.setText(mList.get(position).getMember_date());
        viewholder.Detail.setText(mList.get(position).getMember_Detail());
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}