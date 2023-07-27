package com.nhom22.findhostel.UI.HomeSecond;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.nhom22.findhostel.Model.Detail_Furniture;
import com.nhom22.findhostel.Model.Detail_Utilities;
import com.nhom22.findhostel.Model.Images;
import com.nhom22.findhostel.Model.Posts;
import com.nhom22.findhostel.R;
import com.nhom22.findhostel.Service.Detail_FurnitureService;
import com.nhom22.findhostel.Service.Detail_ImageService;
import com.nhom22.findhostel.Service.Detail_UtilitiesService;
import com.nhom22.findhostel.Service.PostsService;
import com.nhom22.findhostel.UI.Search.FurnitureAdapter;
import com.nhom22.findhostel.UI.Search.ImageSliderAdapter;
import com.nhom22.findhostel.UI.Search.UtilitiesAdapter;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class PostDetailActivity extends AppCompatActivity {

    private Posts p;
    private List<Detail_Furniture> furs;
    private List<Detail_Utilities> utis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Bundle dataBundle = getIntent().getExtras();
        if (dataBundle != null) {
            int id = dataBundle.getInt("id");
            PostsService postsService = new PostsService();
            try {
                p = postsService.getPostById(id);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            // Update the post details
            updatePostDetails(p);

            // Update the furniture details
            updateFurnitureDetails(id);

            // Update the utilities details
            updateUtilitiesDetails(id);

            // Update the image details
            updateImageDetails(id);

            Button btnUpdate = findViewById(R.id.btnUpdatePost);
            btnUpdate.setOnClickListener(v -> {
                Intent intent = new Intent(PostDetailActivity.this, UpdatePostActivity.class);
                startActivity(intent);
            });
        }
    }

    @SuppressLint("SetTextI18n")
    private void updatePostDetails(Posts post) {
        TextView tvDescription = findViewById(R.id.tvDescription);
        TextView tvType = findViewById(R.id.tvType);
        TextView tvAddress = findViewById(R.id.tvAddress);
        TextView tvPrice = findViewById(R.id.tvPrice);
        TextView tvPhoneNumber = findViewById(R.id.tvPhoneNumber);
        TextView tvDateCounter = findViewById(R.id.tvDateCounter);

        tvDescription.setText(post.getDescription());
        tvType.setText(post.getType().getName());
        tvAddress.setText(post.getAddress().getHouseNumber() + ", " +
                post.getAddress().getStreets().getName() + ", " +
                post.getAddress().getSubDistrics().getName() + ", " +
                post.getAddress().getDistricts().getName() + ", " +
                post.getAddress().getCities().getName());
        tvPrice.setText(String.valueOf(post.getPrice()) + "đ");
        tvPhoneNumber.setText(post.getUserAccount().getPhone());
        Date from = post.getTimeFrom();
        Date to = post.getTimeTo();
        long diffInMillis = to.getTime() - from.getTime();
        long daysdiff = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);

        tvDateCounter.setText("Bài đăng còn " + String.valueOf(daysdiff) + " ngày");
    }

    private void updateFurnitureDetails(int postId) {
        TextView tvBed = findViewById(R.id.tvBed);
        TextView tvShower = findViewById(R.id.tvShower);
        GridView gvFurniture = findViewById(R.id.gvFurniture);

        Detail_FurnitureService detail_furnitureService = new Detail_FurnitureService();
        try {
            furs = detail_furnitureService.getListDetailFurnitureByPostId(postId);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        if (furs != null && !furs.isEmpty()) {
            for (int x = 0; x < furs.size(); x++) {
                if (furs.get(x).getFurniture().getName().equals("Giường")) {
                    tvBed.setText(String.valueOf(furs.get(x).getQuantity()));
                    tvShower.setText(String.valueOf(furs.get(x).getQuantity()));
                }
            }
            FurnitureAdapterActivity adapter = new FurnitureAdapterActivity(this, furs);
            gvFurniture.setAdapter(adapter);
        }
    }

    private void updateUtilitiesDetails(int postId) {
        GridView gvUtilities = findViewById(R.id.gvUtilities);

        Detail_UtilitiesService detail_utilitiesService = new Detail_UtilitiesService();
        try {
            utis = detail_utilitiesService.getListDetailUtilitiesByPostId(postId);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        if (utis != null && !utis.isEmpty()) {
            UtilitiesAdapterActivity adapter = new UtilitiesAdapterActivity(this, utis);
            gvUtilities.setAdapter(adapter);
        }
    }

    private void updateImageDetails(int postId) {
        ViewPager imageViewPager = findViewById(R.id.imageViewPager);
        ImageView imgAvatar = findViewById(R.id.imgAvatar);

        Detail_ImageService detail_imageService = new Detail_ImageService();
        List<Images> images = null;
        try {
            images = detail_imageService.getListImageByPostsId(postId);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        if (images != null && !images.isEmpty()) {
            ImageSliderAdapter imageSliderAdapter = new ImageSliderAdapter(getApplicationContext(), images);
            imageViewPager.setAdapter(imageSliderAdapter);
        }

        byte[] avatar = p.getUserAccount().getImage();
        if (avatar != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(avatar, 0, avatar.length);
            imgAvatar.setImageBitmap(bitmap);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle ActionBar item clicks here
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // Handle the back button click here
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
