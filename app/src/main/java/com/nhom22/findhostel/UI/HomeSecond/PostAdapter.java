package com.nhom22.findhostel.UI.HomeSecond;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

import com.nhom22.findhostel.MainActivity;
import com.nhom22.findhostel.Model.Furniture;
import com.nhom22.findhostel.Model.Images;
import com.nhom22.findhostel.Model.Posts;
import com.nhom22.findhostel.R;
import com.nhom22.findhostel.Service.Detail_FurnitureService;
import com.nhom22.findhostel.Service.Detail_ImageService;
import com.nhom22.findhostel.Service.PostsService;
import com.nhom22.findhostel.UI.Search.ImageSliderAdapter;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class PostAdapter extends BaseAdapter {
    private Context context;
    private List<Posts> postsList;
    private CreditReducer creditReducer;

    private final PostsService postsService = new PostsService();

    public PostAdapter(Context context, List<Posts> list, CreditReducer creditReducer) {
        super();
        this.context = context;
        this.postsList = list;
        this.creditReducer = creditReducer;
    }

    @Override
    public int getCount() {
        return postsList.size();
    }

    @Override
    public Object getItem(int i) {
        return postsList.get(i);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"ViewHolder", "SetTextI18n"})
    @Override
    public View getView(int i, View view, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.item_search_post_2, parent, false);

        // Find views
        ImageView imgMain = view.findViewById(R.id.imgMain);
        TextView tvPrice = view.findViewById(R.id.tvPrice);
        TextView tvType = view.findViewById(R.id.tvType);
        TextView tvAddress = view.findViewById(R.id.tvAddress);
        TextView tvBed = view.findViewById(R.id.tvBed);
        TextView tvShower = view.findViewById(R.id.tvShower);
        ViewPager imageViewPager = view.findViewById(R.id.imageViewPager);
        LinearLayout layoutPost = view.findViewById(R.id.layoutPost);

        // Get the current post
        Posts currentPost = postsList.get(i);

        Date postExpirationDate = currentPost.getTimeTo();
        long currentTimeMillis = System.currentTimeMillis();
        Date currentDate = new Date(currentTimeMillis);

        if (postExpirationDate.after(currentDate)) {
            layoutPost.setBackgroundColor(Color.LTGRAY);
        } else {
            Button btnExpired = new Button(context);
            btnExpired.setText("Gia hạn bài viết");
            btnExpired.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showConfirmationDialog(currentPost.getUserAccount().getId(), currentPost);
                }
            });

            layoutPost.addView(btnExpired);
            layoutPost.setBackgroundColor(Color.GRAY);
            TextView tvExpired = view.findViewById(R.id.tvExpired);
            tvExpired.setVisibility(View.VISIBLE);
            tvExpired.setText("Đã hết hạn");
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

    private void showConfirmationDialog(final int userId, Posts posts) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Bạn có muốn gia hạn bài viết không?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (creditReducer != null) {
                    creditReducer.reduceCredit(userId);
                    long currentTimeMillis = System.currentTimeMillis();
                    Date currentDate = new Date(currentTimeMillis);

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(currentDate);
                    calendar.add(Calendar.MONTH, 1);
                    Date futureDate = calendar.getTime();

                    posts.setTimeFrom(currentDate);
                    posts.setTimeTo(futureDate);

                    postsService.updatePost(posts);

                }
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public interface CreditReducer {
        void reduceCredit(int userId);
    }
}
