package com.nhom22.findhostel.UI.HomeSecond;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
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
import com.nhom22.findhostel.UI.Search.ImageSliderAdapter;

import java.text.ParseException;
import java.util.List;


public class PostAdapter extends BaseAdapter {
    private Context context;
    private List<Posts> postsList;
    private List<Furniture> furs;


    public PostAdapter(Context context, List<Posts> list) {
        super();
        this.context = context;
        this.postsList = list;
    }

    @Override
    public int getCount() {
        return postsList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"ViewHolder", "SetTextI18n"})
    @Override
    public View getView(int i, View view, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.item_search_post_2, null);

        // Find views
        ImageView imgMain = view.findViewById(R.id.imgMain);
        TextView tvPrice = view.findViewById(R.id.tvPrice);
        TextView tvType = view.findViewById(R.id.tvType);
        TextView tvAddress = view.findViewById(R.id.tvAddress);
        TextView tvBed = view.findViewById(R.id.tvBed);
        TextView tvShower = view.findViewById(R.id.tvShower);
        ViewPager imageViewPager = view.findViewById(R.id.imageViewPager);

        // Get the current post
        Posts currentPost = postsList.get(i);

        // Set the background color based on the active status
        if (currentPost.getActivePost() == 1) {
            view.setBackgroundColor(Color.LTGRAY);
        } else {
            view.setBackgroundColor(Color.GRAY);
            TextView tvExpired = view.findViewById(R.id.tvExpired);
            tvExpired.setVisibility(View.VISIBLE);
            tvExpired.setText("Expired");
        }

        // Set other views' data
        tvPrice.setText(String.valueOf(currentPost.getPrice()) + "đ");
        tvType.setText(currentPost.getType().getName());
        tvAddress.setText(currentPost.getAddress().getHouseNumber() + ", " +
                currentPost.getAddress().getStreets().getName() + ", " +
                currentPost.getAddress().getSubDistrics().getName() + ", " +
                currentPost.getAddress().getDistricts().getName() + ", " +
                currentPost.getAddress().getCities().getName());

        // Load and display images
        Detail_ImageService detail_imageService = new Detail_ImageService();
        List<Images> images = null;
        try {
            images = detail_imageService.getListImageByPostsId(currentPost.getId());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        if (images != null && !images.isEmpty()) {
            ImageSliderAdapter imageSliderAdapter = new ImageSliderAdapter(context, images);
            imageViewPager.setAdapter(imageSliderAdapter);
        } else {
            imgMain.setImageDrawable(null);
        }

        return view;
    }
}
