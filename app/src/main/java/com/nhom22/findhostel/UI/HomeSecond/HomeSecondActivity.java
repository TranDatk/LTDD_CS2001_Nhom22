package com.nhom22.findhostel.UI.HomeSecond;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.Group;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;


import com.google.android.material.navigation.NavigationView;
import com.nhom22.findhostel.R;
import com.nhom22.findhostel.UI.Search.SearchPageFragment;

public class HomeSecondActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_second);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.nav_menu, navigationView.getMenu());

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View customMenuItemView = layoutInflater.inflate(R.layout.layout_custom_menu, navigationView, false);
        View footerView  = layoutInflater.inflate(R.layout.view_navigation_footer, navigationView, false);

        navigationView.addHeaderView(customMenuItemView);
        navigationView.addView(footerView);

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
                }else if (itemId == R.id.item3) {
                    Intent intent = new Intent(HomeSecondActivity.this, SettingActivity.class);
                    startActivity(intent);
                }else if (itemId == R.id.item4) {
                    Intent intent = new Intent(HomeSecondActivity.this, HelperActivity.class);
                    startActivity(intent);
                }else if (itemId == R.id.item5) {
                    Intent intent = new Intent(HomeSecondActivity.this, SearchPageFragment.class);
                    startActivity(intent);
                } else if (itemId == R.id.menu_logout) {

                    performLogout();
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }



    private void performLogout() {
        // Handle logout action
        // You can implement your logout logic here
    }

}