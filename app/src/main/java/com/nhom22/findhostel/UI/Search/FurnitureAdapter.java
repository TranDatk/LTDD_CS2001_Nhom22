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
                imgIcon.setImageResource(R.drawable.colorwifi);
                tvQuantity.setText(String.valueOf(items.get(i).getFurniture().getName()));
                break;
            case "Tủ quần áo":
                imgIcon.setImageResource(R.drawable.colorcloset);
                tvQuantity.setText(String.valueOf(items.get(i).getFurniture().getName()));
                break;
            case "Giường":
                imgIcon.setImageResource(R.drawable.colorbed);
                tvQuantity.setText(String.valueOf(items.get(i).getFurniture().getName()));
                break;
            case "Giá phơi đồ":
                imgIcon.setImageResource(R.drawable.clothesrack);
                tvQuantity.setText(String.valueOf(items.get(i).getFurniture().getName()));
                break;
            case "Bàn":
                imgIcon.setImageResource(R.drawable.colortable);
                tvQuantity.setText(String.valueOf(items.get(i).getFurniture().getName()));
                break;
            case "Ghế":
                imgIcon.setImageResource(R.drawable.colorchair);
                tvQuantity.setText(String.valueOf(items.get(i).getFurniture().getName()));
                break;
            case "Điều hòa":
                imgIcon.setImageResource(R.drawable.colorairconditioner);
                tvQuantity.setText(String.valueOf(items.get(i).getFurniture().getName()));
                break;
            case "Ban công":
                imgIcon.setImageResource(R.drawable.colorbalcony);
                tvQuantity.setText(String.valueOf(items.get(i).getFurniture().getName()));
                break;
            case "Gác lửng":
                imgIcon.setImageResource(R.drawable.promotion);
                tvQuantity.setText(String.valueOf(items.get(i).getFurniture().getName()));
                break;
            case "Cửa sổ":
                imgIcon.setImageResource(R.drawable.colorwindow);
                tvQuantity.setText(String.valueOf(items.get(i).getFurniture().getName()));
                break;
            case "Bếp":
                imgIcon.setImageResource(R.drawable.colorkitchen);
                tvQuantity.setText(String.valueOf(items.get(i).getFurniture().getName()));
                break;
            case "Camera an ninh":
                imgIcon.setImageResource(R.drawable.cctvcamera);
                tvQuantity.setText(String.valueOf(items.get(i).getFurniture().getName()));
                break;
            case "Chỗ giữ xe":
                imgIcon.setImageResource(R.drawable.parking);
                tvQuantity.setText(String.valueOf(items.get(i).getFurniture().getName()));
                break;
            case "Thang máy":
                imgIcon.setImageResource(R.drawable.colorelevator);
                tvQuantity.setText(String.valueOf(items.get(i).getFurniture().getName()));
                break;
            case "Khóa từ, vân tay":
                imgIcon.setImageResource(R.drawable.colorlock);
                tvQuantity.setText(String.valueOf(items.get(i).getFurniture().getName()));
                break;
            case "Sân thượng":
                imgIcon.setImageResource(R.drawable.colorrooftop);
                tvQuantity.setText(String.valueOf(items.get(i).getFurniture().getName()));
                break;
            default:
                System.out.println("...");
                break;
        }

        return view;
    }
}
