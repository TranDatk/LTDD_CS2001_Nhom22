package com.nhom22.findhostel.UI.Search;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nhom22.findhostel.R;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.nhom22.findhostel.Model.Images;

import java.util.List;

public class ImageSliderAdapter extends PagerAdapter {

    private Context context;
    private List<Images> images;

    public ImageSliderAdapter(Context context, List<Images> images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_image_slider, container, false);

        ImageView imageView = view.findViewById(R.id.sliderImage);
        TextView imageIndexText = view.findViewById(R.id.imageIndexText);

        if (images != null && !images.isEmpty()) {
            byte[] image = images.get(position).getImage();
            if (image != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                imageView.setImageBitmap(bitmap);
            }
        } else {
            // Handle the case when no images are available
            // For example, you can set a default image or hide the ImageView
            imageView.setImageDrawable(null); // Set a default image or hide the ImageView
        }

        imageIndexText.setText((position + 1) + "/" + images.size());

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}
