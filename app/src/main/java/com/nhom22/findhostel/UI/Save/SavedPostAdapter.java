package com.nhom22.findhostel.UI.Save;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nhom22.findhostel.Model.Posts;
import com.nhom22.findhostel.R;

import java.util.List;

public class SavedPostAdapter extends BaseAdapter {

    private List<Posts> items;
    private SavePageFragment fragment;

    public SavedPostAdapter(SavePageFragment fragment, List<Posts> items) {
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

        view = inflater.inflate(R.layout.item_saved_post, null);

        TextView tvAddress = view.findViewById(R.id.tvAddress);
        TextView tvPrice = view.findViewById(R.id.tvPrice);
        Button btnPhoneNumber = view.findViewById(R.id.btnPhoneNumber);
        Button btnUnsave = view.findViewById(R.id.btnUnsave);

        tvAddress.setText(items.get(i).getAddress().getStreets().getName() + ", " +
                items.get(i).getAddress().getSubDistrics().getName() + ", " +
                items.get(i).getAddress().getDistricts().getName() + ", " +
                items.get(i).getAddress().getCities().getName());
        tvPrice.setText(String.valueOf(items.get(i).getPrice()));
        btnPhoneNumber.setText(items.get(i).getUserAccount().getPhone());

        btnPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String np = items.get(i).getUserAccount().getPhone();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + np));
                fragment.startActivity(intent);
            }
        });

        btnUnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "unsave successfully", Toast.LENGTH_SHORT).show();
            }
        });

        /*TextView tvItem = view.findViewById(R.id.tvItem);*/

        /*tvItem.setText(items[i]);*/

        return view;
    }
}
