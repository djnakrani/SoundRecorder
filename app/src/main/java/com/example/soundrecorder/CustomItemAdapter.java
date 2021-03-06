package com.example.soundrecorder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomItemAdapter extends BaseAdapter {

    private Context conItems;
    private List<Items> lstItems;

    public CustomItemAdapter(Context conItems, List<Items> lstItems) {
        this.conItems = conItems;
        this.lstItems = lstItems;
    }

    @Override
    public int getCount() {
        return lstItems.size();
    }

    @Override
    public Object getItem(int position) {
        return lstItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(conItems,R.layout.item_listview,null);
        TextView tvName = v.findViewById(R.id.txtname);
        TextView tvDesc = v.findViewById(R.id.txtDesc);
        TextView tvTime = v.findViewById(R.id.txtTime);

        tvName.setText(lstItems.get(position).getName());
        tvDesc.setText(lstItems.get(position).getDesc());
        tvTime.setText(lstItems.get(position).getTime());
        return v;
    }
}
