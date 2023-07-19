package com.nhom22.findhostel.UI.HomeSecond;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.Group;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.nhom22.findhostel.MainActivity;
import com.nhom22.findhostel.Model.UserAccount;
import com.nhom22.findhostel.R;
import com.nhom22.findhostel.Service.StreetsService;
import com.nhom22.findhostel.Service.UserAccountService;
import com.nhom22.findhostel.UI.Account.AccountPageFragment;
import com.nhom22.findhostel.UI.Account.LoginFragment;
import com.nhom22.findhostel.UI.Notification.NotificationPageFragment;
import com.nhom22.findhostel.UI.Search.SearchPageFragment;

import java.io.ByteArrayOutputStream;

public class HomeSecondActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private Button btnPost;

    private final UserAccountService userAccountService = new UserAccountService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_second);


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        btnPost = findViewById(R.id.postBtn);
        LinearLayout screenNot = findViewById(R.id.screen_not);
        LinearLayout screenHavePost = findViewById(R.id.screen_have_post);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        int userId = sharedPreferences.getInt("userId", -1);
        UserAccount user = userAccountService.getUserAccountById(userId);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.nav_menu, navigationView.getMenu());
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View customMenuItemView = layoutInflater.inflate(R.layout.layout_custom_menu, navigationView, false);
        View footerView = layoutInflater.inflate(R.layout.view_navigation_footer, navigationView, false);

        navigationView.addHeaderView(customMenuItemView);
        navigationView.addView(footerView);

        View headerView = navigationView.getHeaderView(0);

        if (headerView != null) {
            ImageView imgAvatar = headerView.findViewById(R.id.AvatarImage);
            TextView txtUsername = headerView.findViewById(R.id.userNameSecond);
            TextView txtPhone = headerView.findViewById(R.id.phoneSecond);
            TextView test = headerView.findViewById(R.id.test);
            if (userId < 0) {
                screenNot.setVisibility(View.VISIBLE);
                screenHavePost.setVisibility(View.GONE);
                btnPost.setOnClickListener(v -> {
                    Intent intent = new Intent(HomeSecondActivity.this, PostOwnerActivity.class);
                    startActivity(intent);
                });

            } else {
                screenNot.setVisibility(View.GONE);
                screenHavePost.setVisibility(View.VISIBLE);
                if (txtUsername != null && txtPhone != null) {
                    txtUsername.setText(user.getUsername());
                    txtPhone.setText(user.getPhone());
                }
                byte[] avt = user.getImage();
                if (avt != null && avt.length > 0) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(avt, 0, avt.length);
                    imgAvatar.setImageBitmap(bitmap);
                } else {

//                    imgAvatar.setImageResource(R.drawable.placeholder_image);
                }
            }

        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.item1) {
                    Intent intent = new Intent(HomeSecondActivity.this, HomeSecondActivity.class);
                    startActivity(intent);
                } else if (itemId == R.id.item2) {
                    Intent intent = new Intent(HomeSecondActivity.this, CreditPageAcitivy.class);
                    startActivity(intent);
                } else if (itemId == R.id.item3) {
                    Intent intent = new Intent(HomeSecondActivity.this, SettingActivity.class);
                    startActivity(intent);
                } else if (itemId == R.id.item4) {
                    Intent intent = new Intent(HomeSecondActivity.this, HelperActivity.class);
                    startActivity(intent);
                } else if (itemId == R.id.item5) {
                    Intent intent = new Intent(HomeSecondActivity.this, MainActivity.class);
                    startActivity(intent);
                } else if (itemId == R.id.menu_logout) {
                    if (userId < 0) {
                        Toast.makeText(getApplicationContext(), "Bạn chưa đăng nhập !! Vui lòng đăng nhập", Toast.LENGTH_SHORT).show();
                    } else {
                        performLogout();
                    }
                } else if (itemId == R.id.menu_login) {

                    Intent intent = new Intent(HomeSecondActivity.this, MainActivity.class);
                    startActivity(intent);
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });


        ImageView gifImageView = findViewById(R.id.imageView);
        Glide.with(this)
                .asGif()
                .load(R.drawable.profile)
                .override(400, 400)
                .into(gifImageView);
    }

    private void performLogout() {
        clearUserSession();
        Intent intent = new Intent(HomeSecondActivity.this, HomeSecondActivity.class);
        startActivity(intent);
    }

    private void clearUserSession() {
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }


}