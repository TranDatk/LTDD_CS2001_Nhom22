package com.nhom22.findhostel.UI.Search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nhom22.findhostel.R;

public class SearchPageAdapter extends BaseAdapter {

    private String[] items;

    private SearchPageFragment fragment;

    public SearchPageAdapter(SearchPageFragment fragment, String[] items) {
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

        view = inflater.inflate(R.layout.item_search_post, null);
        return view;
    }
}
