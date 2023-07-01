package com.nhom22.findhostel.ui.Save;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nhom22.findhostel.R;

public class SavedPostAdapter extends BaseAdapter {

    private String[] items;
    private SavePageFragment fragment;

    public SavedPostAdapter(SavePageFragment fragment, String[] items) {
        this.fragment = fragment;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public Object getItem(int i) {
        return items[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = fragment.getLayoutInflater();

        view = inflater.inflate(R.layout.item_saved_post, null);

        TextView tvItem = view.findViewById(R.id.tvItem);

        tvItem.setText(items[i]);

        return view;
    }
}
