package com.nhom22.findhostel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.nhom22.findhostel.databinding.ActivityMainBinding;
import com.nhom22.findhostel.ui.Account.AccountPageFragment;
import com.nhom22.findhostel.ui.Extension.ExtensionPageFragment;
import com.nhom22.findhostel.ui.Home.HomePageFragment;
import com.nhom22.findhostel.ui.Save.SavePageFragment;
import com.nhom22.findhostel.ui.Search.SearchPageFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Fragment selectedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.navigation.setOnItemSelectedListener(item -> {
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
    }
}