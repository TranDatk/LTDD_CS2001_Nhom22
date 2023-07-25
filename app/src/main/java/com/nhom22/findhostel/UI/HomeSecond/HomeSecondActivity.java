package com.nhom22.findhostel.UI.HomeSecond;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.nhom22.findhostel.MainActivity;
import com.nhom22.findhostel.Model.Posts;
import com.nhom22.findhostel.Model.UserAccount;
import com.nhom22.findhostel.R;
import com.nhom22.findhostel.Service.PostsService;
import com.nhom22.findhostel.Service.UserAccountService;

import java.text.ParseException;
import java.util.List;

public class HomeSecondActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    List<Posts> items = null;
    PostAdapter adapter;

    private final UserAccountService userAccountService = new UserAccountService();
    private final PostsService postsService = new PostsService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_second);


        ListView lvPost = (ListView) findViewById(R.id.lvPost);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        Button btnPost = findViewById(R.id.postBtn);
        FloatingActionButton btnPost2 = (FloatingActionButton) findViewById(R.id.postBtn2);
        LinearLayout screenNot = findViewById(R.id.screen_not);
        RelativeLayout screenHavePost = findViewById(R.id.screen_have_post);

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

            if (user != null) {
                txtUsername.setText(user.getUsername());
                txtPhone.setText(user.getPhone());

                byte[] avt = user.getImage();
                if (avt != null && avt.length > 0) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(avt, 0, avt.length);
                    imgAvatar.setImageBitmap(bitmap);
                } else {
                    imgAvatar.setImageResource(R.drawable.ic_account);
                }
            } else {
                Toast.makeText(getApplicationContext(), "Bạn chưa đăng nhập!!!", Toast.LENGTH_SHORT).show();
            }
        }

        try {
            items = postsService.getAllPost();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        adapter = new PostAdapter(this, items);
        lvPost.setAdapter(adapter);


        int countPost = 0;
        List<Posts> postsList;
        try {
            postsList = postsService.getPostsByOwnerId(userId);
            if (postsList != null && !postsList.isEmpty()) {
                screenNot.setVisibility(View.GONE);
                screenHavePost.setVisibility(View.VISIBLE);
                btnPost2.setOnClickListener(v -> {
                    Intent intent = new Intent(HomeSecondActivity.this, PostOwnerActivity.class);
                    startActivity(intent);
                });
            } else {
                Toast.makeText(this, "Không có bài viết nào", Toast.LENGTH_SHORT).show();
                screenNot.setVisibility(View.VISIBLE);
                screenHavePost.setVisibility(View.GONE);
                btnPost.setOnClickListener(v -> {
                    Intent intent = new Intent(HomeSecondActivity.this, PostOwnerActivity.class);
                    startActivity(intent);
                });
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


        lvPost.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", items.get(i).getId());

                Intent intent = new Intent(HomeSecondActivity.this, PostDetailActivity.class);
                intent.putExtras(dataBundle);
                startActivity(intent);
            }
        });

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
                    Intent intent = getIntent();
                    finish();
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
                    showConfirmationDialog();
                } else if (itemId == R.id.menu_logout) {
                    performLogout();
                } else if (itemId == R.id.menu_login) {
                    if (userId > 0) {
                        Toast.makeText(getApplicationContext(), "Bạn đã đăng nhập rồi !!!", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(HomeSecondActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
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
        Intent intent = new Intent(HomeSecondActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void clearUserSession() {
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeSecondActivity.this);
        builder.setTitle("Bạn có muốn đổi vai trò người thuê không?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(HomeSecondActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
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

}