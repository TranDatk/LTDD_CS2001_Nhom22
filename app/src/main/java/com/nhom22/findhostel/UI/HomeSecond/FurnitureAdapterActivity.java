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
                imgIcon.setImageResource(R.drawable.colorwifi);
                tvQuantity.setText(furnitureItem.getFurniture().getName());
                break;
            case "Tủ quần áo":
                imgIcon.setImageResource(R.drawable.colorcloset);
                tvQuantity.setText(furnitureItem.getFurniture().getName());
                break;
            case "Giường":
                imgIcon.setImageResource(R.drawable.colorbed);
                tvQuantity.setText(furnitureItem.getFurniture().getName());
                break;
            case "Giá phơi đồ":
                imgIcon.setImageResource(R.drawable.clothesrack);
                tvQuantity.setText(furnitureItem.getFurniture().getName());
                break;
            case "Bàn":
                imgIcon.setImageResource(R.drawable.colortable);
                tvQuantity.setText(furnitureItem.getFurniture().getName());
                break;
            case "Ghế":
                imgIcon.setImageResource(R.drawable.colorchair);
                tvQuantity.setText(furnitureItem.getFurniture().getName());
                break;
            case "Điều hòa":
                imgIcon.setImageResource(R.drawable.colorairconditioner);
                tvQuantity.setText(furnitureItem.getFurniture().getName());
                break;
            case "Ban công":
                imgIcon.setImageResource(R.drawable.colorbalcony);
                tvQuantity.setText(furnitureItem.getFurniture().getName());
                break;
            case "Gác lửng":
                imgIcon.setImageResource(R.drawable.promotion);
                tvQuantity.setText(furnitureItem.getFurniture().getName());
                break;
            case "Cửa sổ":
                imgIcon.setImageResource(R.drawable.colorwindow);
                tvQuantity.setText(furnitureItem.getFurniture().getName());
                break;
            case "Bếp":
                imgIcon.setImageResource(R.drawable.colorkitchen);
                tvQuantity.setText(furnitureItem.getFurniture().getName());
                break;
            case "Camera an ninh":
                imgIcon.setImageResource(R.drawable.camera);
                tvQuantity.setText(furnitureItem.getFurniture().getName());
                break;
            case "Chỗ giữ xe":
                imgIcon.setImageResource(R.drawable.parking);
                tvQuantity.setText(furnitureItem.getFurniture().getName());
                break;
            case "Thang máy":
                imgIcon.setImageResource(R.drawable.colorelevator);
                tvQuantity.setText(furnitureItem.getFurniture().getName());
                break;
            case "Khóa từ, vân tay":
                imgIcon.setImageResource(R.drawable.colorlock);
                tvQuantity.setText(furnitureItem.getFurniture().getName());
                break;
            case "Sân thượng":
                imgIcon.setImageResource(R.drawable.colorrooftop);
                tvQuantity.setText(furnitureItem.getFurniture().getName());
                break;
            default:
                System.out.println("...");
                break;
        }
        return convertView;
    }


}
