package com.nhom22.findhostel.UI.Notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nhom22.findhostel.Data.DatabaseHelper;
import com.nhom22.findhostel.Model.Posts;
import com.nhom22.findhostel.R;
import com.nhom22.findhostel.Service.NotificationService;
import com.nhom22.findhostel.Service.PostsService;
import com.nhom22.findhostel.UI.Extension.ItemPostsHostelAdapter;
import com.nhom22.findhostel.YourApplication;
import com.nhom22.findhostel.databinding.FragmentNotificationPageBinding;

import java.text.ParseException;
import java.util.Date;
import java.util.List;


public class NotificationPageFragment extends Fragment {

    FragmentNotificationPageBinding binding;
    public PostsService postsService = new PostsService();

    public static DatabaseHelper dataBase;
    ListView lsvItem;
    List<com.nhom22.findhostel.Model.Notification> arrItem, arrItemOverThirtyDays;
    ItemNotificationAdapter itemAdapter;
    private final NotificationService notificationService = new NotificationService();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            arrItemOverThirtyDays = notificationService.getNotificationOverThirtyDay();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        for (int i = 0; i < arrItemOverThirtyDays.size(); i++){
            notificationService.deleteNotificationById(arrItemOverThirtyDays.get(i).getId());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentNotificationPageBinding binding = FragmentNotificationPageBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        dataBase = new DatabaseHelper(YourApplication.getInstance().getApplicationContext());

        lsvItem = binding.lvNotification;

        try {
            arrItem = notificationService.getAllNotification();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        itemAdapter = new ItemNotificationAdapter(YourApplication.getInstance().getApplicationContext(), R.layout.item_noti_layout, arrItem);
        lsvItem.setAdapter(itemAdapter);


        itemAdapter.notifyDataSetChanged();

        if(arrItem.size() > 0){
            binding.gifImgNoti.setVisibility(View.GONE);
        }else {
            ImageView gifImageView = binding.gifImgNoti;
            Glide.with(this)
                    .asGif()
                    .load(R.drawable.no_notification)
                    .into(gifImageView);
            binding.gifImgNoti.setVisibility(View.VISIBLE);
        }
        Toast.makeText(getContext(), String.valueOf(arrItem.size()), Toast.LENGTH_SHORT).show();

        return view;
    }

    private void sendNotification(String content){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

        Notification notification = new NotificationCompat.Builder(getContext(), YourApplication.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_search)
                .setContentTitle("Thông báo phòng trọ mới")
                .setContentText("Hiện đang có 1 phòng trọ mới xung quanh khu vực " + content + " của bạn")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT).build();

        NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(getContext().NOTIFICATION_SERVICE);
        if(notificationManager != null)
            notificationManager.notify(getNotificationId(), notification);
    }

    private int getNotificationId(){
        return (int) new Date().getTime();
    }



}