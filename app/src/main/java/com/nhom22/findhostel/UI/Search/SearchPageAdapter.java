package com.nhom22.findhostel.UI.Search;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.nhom22.findhostel.Model.Furniture;
import com.nhom22.findhostel.Model.Images;
import com.nhom22.findhostel.Model.Posts;
import com.nhom22.findhostel.R;
import com.nhom22.findhostel.Service.Detail_FurnitureService;
import com.nhom22.findhostel.Service.Detail_ImageService;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

public class SearchPageAdapter extends BaseAdapter {

    private List<Posts> items;

    private List<Furniture> furs;

    private SearchPageFragment fragment;

    public SearchPageAdapter(SearchPageFragment fragment, List<Posts> items) {
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

        view = inflater.inflate(R.layout.item_search_post, null);
        ImageView imgMain = view.findViewById(R.id.imgMain);
        TextView tvPrice = view.findViewById(R.id.tvPrice);
        TextView tvType = view.findViewById(R.id.tvType);
        TextView tvAddress = view.findViewById(R.id.tvAddress);
        TextView tvBed = view.findViewById(R.id.tvBed);
        TextView tvShower = view.findViewById(R.id.tvShower);
        ViewPager imageViewPager = view.findViewById(R.id.imageViewPager);

        Detail_ImageService detail_imageService = new Detail_ImageService();
        List<Images> images = null;
        try {
            images = detail_imageService.getListImageByPostsId(items.get(i).getId());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        if (images != null && !images.isEmpty()) {
            ImageSliderAdapter imageSliderAdapter = new ImageSliderAdapter(fragment.getContext(), images);
            imageViewPager.setAdapter(imageSliderAdapter);
//            }
        } else {
            imgMain.setImageDrawable(null);
        }

        Detail_FurnitureService detail_furnitureService = new Detail_FurnitureService();
        try {
            furs = detail_furnitureService.getListFurnitureByPostsId(items.get(i).getId());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        if (furs != null && !furs.isEmpty()) {
        }

        tvPrice.setText(String.valueOf(items.get(i).getPrice()) + "đ");
        tvType.setText(items.get(i).getType().getName());
        tvAddress.setText(items.get(i).getAddress().getHouseNumber() + ", " +
                items.get(i).getAddress().getStreets().getName() + ", " +
                items.get(i).getAddress().getSubDistrics().getName() + ", " +
                items.get(i).getAddress().getDistricts().getName() + ", " +
                items.get(i).getAddress().getCities().getName());

        return view;
    }
}
