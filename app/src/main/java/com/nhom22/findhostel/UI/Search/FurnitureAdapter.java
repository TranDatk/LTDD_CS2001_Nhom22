package com.nhom22.findhostel.UI.Search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nhom22.findhostel.Model.Detail_Furniture;
import com.nhom22.findhostel.R;

import java.util.List;

public class FurnitureAdapter extends BaseAdapter {

    List<Detail_Furniture> items;
    PostDetailFragment fragment;

    public FurnitureAdapter(PostDetailFragment fragment, List<Detail_Furniture> items) {
        this.items = items;
        this.fragment = fragment;
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

        view = inflater.inflate(R.layout.item_post_furniture, null);

        ImageView imgIcon = view.findViewById(R.id.imgIcon);
        TextView tvQuantity = view.findViewById(R.id.tvQuantity);

        switch (items.get(i).getFurniture().getName()) {
            case "Wifi":
                imgIcon.setImageResource(R.drawable.wifi);
                tvQuantity.setText(String.valueOf(items.get(i).getQuantity()));
                break;
            case "Tủ quần áo":
                imgIcon.setImageResource(R.drawable.closet);
                tvQuantity.setText(String.valueOf(items.get(i).getQuantity()));
                break;
            case "Giường":
                imgIcon.setImageResource(R.drawable.bed);
                tvQuantity.setText(String.valueOf(items.get(i).getQuantity()));
                break;
            case "Giá phơi đồ":
                imgIcon.setImageResource(R.drawable.tshirt);
                tvQuantity.setText(String.valueOf(items.get(i).getQuantity()));
                break;
            case "Bàn":
                imgIcon.setImageResource(R.drawable.table);
                tvQuantity.setText(String.valueOf(items.get(i).getQuantity()));
                break;
            case "Ghế":
                imgIcon.setImageResource(R.drawable.chair);
                tvQuantity.setText(String.valueOf(items.get(i).getQuantity()));
                break;
            case "Điều hòa":
                imgIcon.setImageResource(R.drawable.airconditioner);
                tvQuantity.setText(String.valueOf(items.get(i).getQuantity()));
                break;
            case "Ban công":
                imgIcon.setImageResource(R.drawable.balcony);
                tvQuantity.setText(String.valueOf(items.get(i).getQuantity()));
                break;
            case "Gác lửng":
                imgIcon.setImageResource(R.drawable.stairs);
                tvQuantity.setText(String.valueOf(items.get(i).getQuantity()));
                break;
            case "Cửa sổ":
                imgIcon.setImageResource(R.drawable.window);
                tvQuantity.setText(String.valueOf(items.get(i).getQuantity()));
                break;
            case "Bếp":
                imgIcon.setImageResource(R.drawable.kitchen);
                tvQuantity.setText(String.valueOf(items.get(i).getQuantity()));
                break;
            case "Camera an ninh":
                imgIcon.setImageResource(R.drawable.camera);
                tvQuantity.setText(String.valueOf(items.get(i).getQuantity()));
                break;
            case "Chỗ giữ xe":
                imgIcon.setImageResource(R.drawable.motorcycleparking);
                tvQuantity.setText(String.valueOf(items.get(i).getQuantity()));
                break;
            case "Thang máy":
                imgIcon.setImageResource(R.drawable.elevator);
                tvQuantity.setText(String.valueOf(items.get(i).getQuantity()));
                break;
            case "Khóa từ, vân tay":
                imgIcon.setImageResource(R.drawable.doorhandle);
                tvQuantity.setText(String.valueOf(items.get(i).getQuantity()));
                break;
            case "Sân thượng":
                imgIcon.setImageResource(R.drawable.rooftop);
                tvQuantity.setText(String.valueOf(items.get(i).getQuantity()));
                break;
            default:
                System.out.println("...");
                break;
        }

        return view;
    }
}
