package com.nhom22.findhostel;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.nhom22.findhostel.Data.DatabaseHelper;
import com.nhom22.findhostel.Firebase.CitiesFirebase;
import com.nhom22.findhostel.Model.Cities;
import com.nhom22.findhostel.Service.CitiesService;
import com.nhom22.findhostel.UI.Account.AccountPageFragment;
import com.nhom22.findhostel.UI.Extension.ExtensionPageFragment;
import com.nhom22.findhostel.UI.Notification.NotificationPageFragment;
import com.nhom22.findhostel.UI.Save.SavePageFragment;
import com.nhom22.findhostel.UI.Search.SearchPageFragment;
import com.nhom22.findhostel.databinding.ActivityMainBinding;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Context context = this;

        DatabaseHelper databaseHelper = new DatabaseHelper(context);

        binding.navigation.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            if (item.getItemId() == R.id.navigation_notification) {
                selectedFragment = new NotificationPageFragment();
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


        CitiesFirebase citiesFirebase = new CitiesFirebase();
        citiesFirebase.getCities(new CitiesFirebase.CitiesCallback() {
            @Override
            public void onCityLoaded(List<Cities> cities) {
                for (Cities city : cities) {
                    CitiesService citiesService = new CitiesService();
                    citiesService.addCities(city);
                    // Thực hiện các xử lý khác với citiesService
                }
            }

            @Override
            public void onError(String errorMessage) {
                // Xử lý khi có lỗi xảy ra trong truy vấn
            }
        });

    }



}