package com.example.myapplication.Board;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.ArrayList;


public class BoAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<BoSt> data; //Item 목록을 담을 배열
    private int layout;

    public BoAdapter(Context context, int layout, ArrayList<BoSt> data) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
        this.layout = layout;
    }

    @Override
    public int getCount() { //리스트 안 Item의 개수를 센다.
        return data.size();
    }

    @Override
    public String getItem(int position) {
        return data.get(position).getMember_id();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(layout, parent, false);
        }
        BoSt friendsItem = data.get(position);


        TextView profile =  convertView.findViewById(R.id.id);
        profile.setText(friendsItem.getMember_id());


        TextView info = (TextView) convertView.findViewById(R.id.name);
        info.setText(friendsItem.getMember_name());

        TextView phone = (TextView) convertView.findViewById(R.id.location);
        phone.setText(friendsItem.getMember_address());


        return convertView;
    }
}