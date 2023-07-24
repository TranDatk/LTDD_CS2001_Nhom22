package com.nhom22.findhostel.UI.HomeSecond;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nhom22.findhostel.Model.Detail_Furniture;
import com.nhom22.findhostel.R;
import com.nhom22.findhostel.UI.Search.PostDetailFragment;

import java.util.List;

public class FurnitureAdapterActivity extends BaseAdapter {
    List<Detail_Furniture> items;
    PostDetailActivity activity;

    LayoutInflater inflater;

    public FurnitureAdapterActivity(PostDetailActivity activity, List<Detail_Furniture> items) {
        this.items = items;
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
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
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_post_furniture, parent, false);
        }

        ImageView imgIcon = convertView.findViewById(R.id.imgIcon);
        TextView tvQuantity = convertView.findViewById(R.id.tvQuantity);

        Detail_Furniture furnitureItem = items.get(position);

        switch (furnitureItem.getFurniture().getName()) {
            case "Wifi":
                imgIcon.setImageResource(R.drawable.wifi);
                tvQuantity.setText(furnitureItem.getFurniture().getName());
                break;
            case "Tủ quần áo":
                imgIcon.setImageResource(R.drawable.closet);
                tvQuantity.setText(furnitureItem.getFurniture().getName());
                break;
            case "Giường":
                imgIcon.setImageResource(R.drawable.bed);
                tvQuantity.setText(furnitureItem.getFurniture().getName());
                break;
            case "Giá phơi đồ":
                imgIcon.setImageResource(R.drawable.tshirt);
                tvQuantity.setText(furnitureItem.getFurniture().getName());
                break;
            case "Bàn":
                imgIcon.setImageResource(R.drawable.table);
                tvQuantity.setText(furnitureItem.getFurniture().getName());
                break;
            case "Ghế":
                imgIcon.setImageResource(R.drawable.chair);
                tvQuantity.setText(furnitureItem.getFurniture().getName());
                break;
            case "Điều hòa":
                imgIcon.setImageResource(R.drawable.airconditioner);
                tvQuantity.setText(furnitureItem.getFurniture().getName());
                break;
            case "Ban công":
                imgIcon.setImageResource(R.drawable.balcony);
                tvQuantity.setText(furnitureItem.getFurniture().getName());
                break;
            case "Gác lửng":
                imgIcon.setImageResource(R.drawable.stairs);
                tvQuantity.setText(furnitureItem.getFurniture().getName());
                break;
            case "Cửa sổ":
                imgIcon.setImageResource(R.drawable.window);
                tvQuantity.setText(furnitureItem.getFurniture().getName());
                break;
            case "Bếp":
                imgIcon.setImageResource(R.drawable.kitchen);
                tvQuantity.setText(furnitureItem.getFurniture().getName());
                break;
            case "Camera an ninh":
                imgIcon.setImageResource(R.drawable.camera);
                tvQuantity.setText(furnitureItem.getFurniture().getName());
                break;
            case "Chỗ giữ xe":
                imgIcon.setImageResource(R.drawable.motorcycleparking);
                tvQuantity.setText(furnitureItem.getFurniture().getName());
                break;
            case "Thang máy":
                imgIcon.setImageResource(R.drawable.elevator);
                tvQuantity.setText(furnitureItem.getFurniture().getName());
                break;
            case "Khóa từ, vân tay":
                imgIcon.setImageResource(R.drawable.doorhandle);
                tvQuantity.setText(furnitureItem.getFurniture().getName());
                break;
            case "Sân thượng":
                imgIcon.setImageResource(R.drawable.rooftop);
                tvQuantity.setText(furnitureItem.getFurniture().getName());
                break;
            default:
                System.out.println("...");
                break;
        }
        return convertView;
    }


}
