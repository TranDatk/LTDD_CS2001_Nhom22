package com.nhom22.findhostel.UI.HomeSecond;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nhom22.findhostel.Model.Detail_Utilities;
import com.nhom22.findhostel.R;


import java.util.List;

public class UtilitiesAdapterActivity extends BaseAdapter {
    private List<Detail_Utilities> items;

    PostDetailActivity activity;
    private LayoutInflater inflater;

    public UtilitiesAdapterActivity(PostDetailActivity activity, List<Detail_Utilities> items) {
        inflater = LayoutInflater.from(activity);
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_post_utilities, parent, false);
            viewHolder = new ViewHolder();

            viewHolder.imgIcon = convertView.findViewById(R.id.imgIcon);
            viewHolder.tvPrice = convertView.findViewById(R.id.tvPrice);
            viewHolder.imgUnit = convertView.findViewById(R.id.imgUnit);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Detail_Utilities utility = items.get(position);

        // Make sure that tvPrice is not null before setting the text
        if (viewHolder.tvPrice != null) {
            viewHolder.tvPrice.setText(String.valueOf(utility.getPrice()) + "đ/");
        }

        switch (utility.getUtilities().getName()) {
            case "Điện":
                viewHolder.imgIcon.setImageResource(R.drawable.lightning);
                break;
            case "Nước":
                viewHolder.imgIcon.setImageResource(R.drawable.watertap);
                break;
            case "Giữ xe":
                viewHolder.imgIcon.setImageResource(R.drawable.parking);
                break;
            case "Rác":
                viewHolder.imgIcon.setImageResource(R.drawable.trash);
                break;
            case "Mạng":
                viewHolder.imgIcon.setImageResource(R.drawable.colorwifi);
                break;
            default:
                viewHolder.imgIcon.setImageResource(R.drawable.default_ic);
                break;
        }

        switch (utility.getUnit()) {
            case "1 người":
                viewHolder.imgUnit.setImageResource(R.drawable.person);
                break;
            case "1 phòng":
                viewHolder.imgUnit.setImageResource(R.drawable.room);
                break;
            default:
                viewHolder.imgUnit.setImageResource(R.drawable.default_ic);
                break;
        }

        return convertView;
    }

    static class ViewHolder {
        ImageView imgIcon;
        TextView tvPrice;
        ImageView imgUnit;
    }
}


