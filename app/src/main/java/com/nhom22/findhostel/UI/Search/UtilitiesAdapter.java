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
        view = inflater.inflate(R.layout.item_post_furniture, null);

        ImageView imgIcon = view.findViewById(R.id.imgIcon);
        TextView tvQuantity = view.findViewById(R.id.tvQuantity);

        switch (items.get(i).getUtilities().getName()) {
            case "Điện":
                imgIcon.setImageResource(R.drawable.lightning);
                tvQuantity.setText(
                        String.valueOf(items.get(i).getPrice()) + "đ/" + items.get(i).getUnit());
                break;
            case "Nước":
                imgIcon.setImageResource(R.drawable.watertap);
                tvQuantity.setText(
                        String.valueOf(items.get(i).getPrice()) + "đ/" + items.get(i).getUnit());
                break;
            case "Giữ xe":
                imgIcon.setImageResource(R.drawable.parking);
                tvQuantity.setText(
                        String.valueOf(items.get(i).getPrice()) + "đ/" + items.get(i).getUnit());
                break;
            case "Rác":
                imgIcon.setImageResource(R.drawable.trash);
                tvQuantity.setText(
                        String.valueOf(items.get(i).getPrice()) + "đ/" + items.get(i).getUnit());
                break;
            case "Mạng":
                imgIcon.setImageResource(R.drawable.colorwifi);
                tvQuantity.setText(
                        String.valueOf(items.get(i).getPrice()) + "đ/" + items.get(i).getUnit());
                break;
            default:
                System.out.println("...");
                break;
        }

        return view;
    }
}
