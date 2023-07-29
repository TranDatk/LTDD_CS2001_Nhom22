package com.nhom22.findhostel.UI.Notification;

import static android.content.Context.MODE_PRIVATE;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nhom22.findhostel.Data.DatabaseHelper;
import com.nhom22.findhostel.Data.PostsDAO;
import com.nhom22.findhostel.Model.Address;
import com.nhom22.findhostel.Model.Districts;
import com.nhom22.findhostel.Model.Posts;
import com.nhom22.findhostel.Model.UserAccount;
import com.nhom22.findhostel.R;
import com.nhom22.findhostel.Service.AddressService;
import com.nhom22.findhostel.Service.NotificationService;
import com.nhom22.findhostel.Service.PostsService;
import com.nhom22.findhostel.Service.TypeService;
import com.nhom22.findhostel.Service.UserAccountService;
import com.nhom22.findhostel.UI.Extension.ItemPostsHostelAdapter;
import com.nhom22.findhostel.UI.Search.PostDetailFragment;
import com.nhom22.findhostel.YourApplication;
import com.nhom22.findhostel.databinding.FragmentNotificationPageBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class NotificationPageFragment extends Fragment {

    FragmentNotificationPageBinding binding;
    public PostsService postsService = new PostsService();

    private PostsDAO postsDAO = new PostsDAO(YourApplication.getInstance().getApplicationContext());
    private UserAccountService userAccountService = new UserAccountService();
    private AddressService addressService = new AddressService();
    private TypeService typeService = new TypeService();

    private int userId = 0 ;
    public static DatabaseHelper dataBase;
    ListView lsvItem;
    List<com.nhom22.findhostel.Model.Notification> arrItem, arrItemOverThirtyDays;
    List<Posts> lsPostsDistrics;
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


        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        userId = sharedPreferences.getInt("userId", -1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentNotificationPageBinding binding = FragmentNotificationPageBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        Date currentDate = new Date();
        if(userId > 0){
            binding.gifImgNoti.setVisibility(View.GONE);

            dataBase = new DatabaseHelper(YourApplication.getInstance().getApplicationContext());

            lsvItem = binding.lvNotification;

            UserAccount userAccount = userAccountService.getUserAccountById(userId);
            Districts address = userAccount.getAddress().getDistricts();

            try {
                lsPostsDistrics = postsService.getPostsByDistricsAndNotUserId(address, userAccount);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            for (int i = 0; i < lsPostsDistrics.size(); i++){
                com.nhom22.findhostel.Model.Notification notification = null;
                try {
                    notification = notificationService.getANotificationByPostsIdAndUserId(lsPostsDistrics.get(i).getId(), userId);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                if(notification == null){
                    notification = new com.nhom22.findhostel.Model.Notification(1, lsPostsDistrics.get(i).getId(),userId, currentDate);
                    try {
                        notificationService.addNotification(notification);
                        sendNotification(lsPostsDistrics.get(i).getAddress().getDistricts().getName());
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            try {
                arrItem = notificationService.getNotificationByDistrictsPostsAndUserId(address, userAccount);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            itemAdapter = new ItemNotificationAdapter(YourApplication.getInstance().getApplicationContext(), R.layout.item_noti_layout, arrItem);
            lsvItem.setAdapter(itemAdapter);


            itemAdapter.notifyDataSetChanged();
        } else {
            ImageView gifImageView = binding.gifImgNoti;
            Glide.with(this)
                    .asGif()
                    .load(R.drawable.no_notification)
                    .into(gifImageView);
            binding.gifImgNoti.setVisibility(View.VISIBLE);
        }

        lsvItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Tạo Bundle và chuyển dữ liệu cần truyền vào
                Bundle dataBundle = new Bundle();

                dataBundle.putInt("id", arrItem.get(i).getPostsId());

                // Tạo Fragment mới và gắn Bundle vào Fragment
                PostDetailFragment postDetailFragment = new PostDetailFragment();
                postDetailFragment.setArguments(dataBundle);

                // Thực hiện thay thế Fragment hiện tại bằng Fragment mới có dữ liệu được truyền
                replaceFragment(postDetailFragment);
            }
        });


        return view;
    }

    private void sendNotification(String content){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

        android.app.Notification notification = new NotificationCompat.Builder(getContext(), YourApplication.CHANNEL_ID)
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

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}