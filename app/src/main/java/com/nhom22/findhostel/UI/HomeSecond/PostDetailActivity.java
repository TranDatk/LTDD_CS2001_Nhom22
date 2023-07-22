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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.nhom22.findhostel.Model.Images;
import com.nhom22.findhostel.Model.Posts;
import com.nhom22.findhostel.R;
import com.nhom22.findhostel.Service.Detail_ImageService;
import com.nhom22.findhostel.Service.PostsService;
import com.nhom22.findhostel.UI.Search.ImageSliderAdapter;

import java.text.ParseException;
import java.util.List;
import java.util.Objects;

public class PostDetailActivity extends AppCompatActivity {

    public Posts p;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        TextView tvDescription = findViewById(R.id.tvDescription);
        TextView tvType = findViewById(R.id.tvType);
        TextView tvAddress = findViewById(R.id.tvAddress);
        TextView tvPrice = findViewById(R.id.tvPrice);
        TextView tvPhoneNumber = findViewById(R.id.tvPhoneNumber);
        Button btnCall = findViewById(R.id.btnCall);
        Button btnSms = findViewById(R.id.btnSms);
        ViewPager imageViewPager = findViewById(R.id.imageViewPager);
        ImageView imgAvatar = findViewById(R.id.imgAvatar);

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
            tvDescription.setText(p.getDescription());
            tvType.setText(p.getType().getName());
            tvAddress.setText(p.getAddress().getHouseNumber() + ", " +
                    p.getAddress().getStreets().getName() + ", " +
                    p.getAddress().getSubDistrics().getName() + ", " +
                    p.getAddress().getDistricts().getName() + ", " +
                    p.getAddress().getCities().getName());
            tvPrice.setText(String.valueOf(p.getPrice()));
            tvPhoneNumber.setText(p.getUserAccount().getPhone());

            btnCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + p.getUserAccount().getPhone()));
                    startActivity(intent);
                }
            });

            btnSms.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setData(Uri.parse("smsto:" + p.getUserAccount().getPhone()));
                    startActivity(intent);
                }
            });

            Detail_ImageService detail_imageService = new Detail_ImageService();
            List<Images> images = null;
            try {
                images = detail_imageService.getListImageByPostsId(id);
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