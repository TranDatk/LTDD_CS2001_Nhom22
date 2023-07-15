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

import com.nhom22.findhostel.Model.Images;
import com.nhom22.findhostel.Model.Posts;
import com.nhom22.findhostel.R;
import com.nhom22.findhostel.Service.Detail_ImageService;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

public class SearchPageAdapter extends BaseAdapter {

    private List<Posts> items;

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

        Detail_ImageService detail_imageService = new Detail_ImageService();
        List<Images> images = null;
        try {
            images = detail_imageService.getListImageByPostsId(1);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

//        if (!images.isEmpty()) {
//            byte[] image = images.get(0).getImage();
//            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
//            imgMain.setImageBitmap(bitmap);
//        }

        if (images != null && !images.isEmpty()) {
            byte[] image = images.get(0).getImage();
            if (image != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                imgMain.setImageBitmap(bitmap);
            }
        } else {
            // Handle the case when no images are available
            // For example, you can set a default image or hide the ImageView
            imgMain.setImageDrawable(null); // Set a default image or hide the ImageView
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
