package com.nhom22.findhostel.UI.Search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nhom22.findhostel.Model.Detail_Furniture;
import com.nhom22.findhostel.Model.Detail_Utilities;
import com.nhom22.findhostel.R;

import java.util.List;

public class UtilitiesAdapter extends BaseAdapter {

    List<Detail_Utilities> items;
    PostDetailFragment fragment;

    public UtilitiesAdapter(PostDetailFragment fragment, List<Detail_Utilities> items) {
        this.fragment = fragment;
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = fragment.getLayoutInflater();
        view = inflater.inflate(R.layout.item_post_utilities, null);

        ImageView imgIcon = view.findViewById(R.id.imgIcon);
        TextView tvPrice = view.findViewById(R.id.tvPrice);
        ImageView imgUnit = view.findViewById(R.id.imgUnit);

        switch (items.get(i).getUtilities().getName()) {
            case "Điện":
                imgIcon.setImageResource(R.drawable.lightning);
                tvPrice.setText(String.valueOf(items.get(i).getPrice()) + "đ/");
                switch (items.get(i).getUnit()) {
                    case "1 người":
                        imgUnit.setImageResource(R.drawable.person);
                        break;
                    case "1 phòng":
                        imgUnit.setImageResource(R.drawable.room);
                        break;
                    default:
                        tvPrice.setText(String.valueOf(items.get(i).getPrice()) + "đ/" + items.get(i).getUnit());
                        break;
                }
                break;
            case "Nước":
                imgIcon.setImageResource(R.drawable.watertap);
                tvPrice.setText(String.valueOf(items.get(i).getPrice()) + "đ/");
                switch (items.get(i).getUnit()) {
                    case "1 người":
                        imgUnit.setImageResource(R.drawable.person);
                        break;
                    case "1 phòng":
                        imgUnit.setImageResource(R.drawable.room);
                        break;
                    default:
                        tvPrice.setText(String.valueOf(items.get(i).getPrice()) + "đ/");
                        break;
                }
                break;
            case "Giữ xe":
                imgIcon.setImageResource(R.drawable.parking);
                tvPrice.setText(String.valueOf(items.get(i).getPrice()) + "đ/");
                switch (items.get(i).getUnit()) {
                    case "1 người":
                        imgUnit.setImageResource(R.drawable.person);
                        break;
                    case "1 phòng":
                        imgUnit.setImageResource(R.drawable.room);
                        break;
                    default:
                        tvPrice.setText(String.valueOf(items.get(i).getPrice()) + "đ/");
                        break;
                }
                break;
            case "Rác":
                imgIcon.setImageResource(R.drawable.trash);
                tvPrice.setText(String.valueOf(items.get(i).getPrice()) + "đ/");
                switch (items.get(i).getUnit()) {
                    case "1 người":
                        imgUnit.setImageResource(R.drawable.person);
                        break;
                    case "1 phòng":
                        imgUnit.setImageResource(R.drawable.room);
                        break;
                    default:
                        tvPrice.setText(String.valueOf(items.get(i).getPrice()) + "đ/");
                        break;
                }
                break;
            case "Mạng":
                imgIcon.setImageResource(R.drawable.colorwifi);
                tvPrice.setText(String.valueOf(items.get(i).getPrice()) + "đ/");
                switch (items.get(i).getUnit()) {
                    case "1 người":
                        imgUnit.setImageResource(R.drawable.person);
                        break;
                    case "1 phòng":
                        imgUnit.setImageResource(R.drawable.room);
                        break;
                    default:
                        tvPrice.setText(String.valueOf(items.get(i).getPrice()) + "đ/");
                        break;
                }
                break;
            default:
                System.out.println("...");
                break;
        }

        return view;
    }
}
