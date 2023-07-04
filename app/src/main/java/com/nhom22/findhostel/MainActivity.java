package com.nhom22.findhostel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.database.sqlite.SQLiteDatabase;

import com.nhom22.findhostel.databinding.ActivityMainBinding;
import com.nhom22.findhostel.UI.Account.AccountPageFragment;
import com.nhom22.findhostel.UI.Extension.ExtensionPageFragment;
import com.nhom22.findhostel.UI.Home.HomePageFragment;
import com.nhom22.findhostel.UI.Save.SavePageFragment;
import com.nhom22.findhostel.UI.Search.SearchPageFragment;
import com.nhom22.findhostel.Data.DatabaseHelper;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.navigation.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            if (item.getItemId() == R.id.navigation_home) {
                selectedFragment = new HomePageFragment();
            } else if (item.getItemId() == R.id.navigation_exten) {
                selectedFragment = new ExtensionPageFragment();
            } else if (item.getItemId() == R.id.navigation_search) {
                selectedFragment = new SearchPageFragment();
            } else if (item.getItemId() == R.id.navigation_info) {
                selectedFragment = new SavePageFragment();
            } else if (item.getItemId() == R.id.navigation_account) {
                selectedFragment = new AccountPageFragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_container, selectedFragment)
                        .commit();
                return true;
            }

            return false;
        });

        // Set the initial selected fragment
        binding.navigation.setSelectedItemId(R.id.navigation_search);
        databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
    }

}