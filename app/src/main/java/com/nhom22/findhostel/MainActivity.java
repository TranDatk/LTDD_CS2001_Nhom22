package com.nhom22.findhostel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.database.sqlite.SQLiteDatabase;

import com.nhom22.findhostel.UI.Account.AccountPageFragment;
import com.nhom22.findhostel.UI.Extension.ExtensionPageFragment;
import com.nhom22.findhostel.UI.Notification.NotificationPageFragment;
import com.nhom22.findhostel.UI.Save.SavePageFragment;
import com.nhom22.findhostel.UI.Search.SearchPageFragment;
import com.nhom22.findhostel.Data.DatabaseHelper;
import com.nhom22.findhostel.databinding.ActivityMainBinding;

import nl.joery.animatedbottombar.AnimatedBottomBar;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Context context = this;

        DatabaseHelper databaseHelper = new DatabaseHelper(context);

//        long cities = databaseHelper.addCity("Hồ chí minh", 1);
//        long cities1 = databaseHelper.addCity("Hà nội", 1);
//        long cities2 = databaseHelper.addCity("Kon tum", 1);
//
//        long districtId1 = databaseHelper.addDistrict("Quận 1", 1, 1);
//        long districtId2 = databaseHelper.addDistrict("Quận 2", 1, 1);
//        long districtId3 = databaseHelper.addDistrict("Quận 3", 1, 1);

//        long subDistrictsId1 =  databaseHelper.addSubDistricts("Phường 1 Quận 1", 1, 1);
//        long subDistrictsId2 =  databaseHelper.addSubDistricts("Phường 2 Quận 1", 1, 1);
//        long subDistrictsId3 =  databaseHelper.addSubDistricts("Phường 1 Quận 2", 1, 2);
//        long subDistrictsId4 =  databaseHelper.addSubDistricts("Phường 2  Quận 2", 1, 2);
//        long subDistrictsId5 =  databaseHelper.addSubDistricts("Phường 1 Quận 3", 1, 3);
//        long subDistrictsId6 =  databaseHelper.addSubDistricts("Phường 2  Quận 3", 1, 3);
//
//        long streetId1 =  databaseHelper.addStreet("Đường 1 phường 1 quận 1", 1, 1);
//        long streetId2 =  databaseHelper.addStreet("Đường 1 phuờng 2 quận 1", 1, 2);
//        long streetId3 =  databaseHelper.addStreet("Đường 1 phường 1 quận 2", 1, 3);
//        long streetId4 =  databaseHelper.addStreet("Đường 1 phuờng 2 quận 2", 1, 4);


        binding.navigation.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int lastIndex, AnimatedBottomBar.Tab lastTab, int newIndex, AnimatedBottomBar.Tab newTab) {
                Fragment selectedFragment = null;
                if (newTab.getId() == R.id.navigation_notification) {
                    selectedFragment = new NotificationPageFragment();
                } else if (newTab.getId() == R.id.navigation_exten) {
                    selectedFragment = new ExtensionPageFragment();
                } else if (newTab.getId() == R.id.navigation_search) {
                    selectedFragment = new SearchPageFragment();
                } else if (newTab.getId() == R.id.navigation_info) {
                    selectedFragment = new SavePageFragment();
                } else if (newTab.getId() == R.id.navigation_account) {

                    selectedFragment = new AccountPageFragment();
                }

                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_container, selectedFragment)
                            .commit();
                }
            }

            @Override
            public void onTabReselected(int index, AnimatedBottomBar.Tab tab) {
                // Handle tab reselection if needed
            }
        });
        Fragment defaultFragment = new SearchPageFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_container, defaultFragment)
                .commit();
    }
}